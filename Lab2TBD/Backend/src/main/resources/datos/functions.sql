-- Crear la tabla de auditoría para registrar los cambios en las tablas
CREATE TABLE audit_log (
                           audit_id SERIAL PRIMARY KEY,      -- Identificador único para cada registro de auditoría
                           user_id BIGINT,                   -- ID del usuario que realizó la acción
                           action_type TEXT,                 -- Tipo de acción: INSERT, UPDATE o DELETE
                           table_name TEXT,                  -- Nombre de la tabla afectada
                           executed_query TEXT,              -- Consulta SQL que se ejecutó
                           action_timestamp TIMESTAMP        -- Marca de tiempo en que se realizó la acción
);

-- Crear la tabla "paid_orders" que registra las órdenes que han sido pagadas
CREATE TABLE paid_orders (
                             order_id INT PRIMARY KEY           -- ID de la orden pagada
);

-- Función para actualizar el estado de una orden a 'Pagada' al insertarla en la tabla "paid_orders"
CREATE OR REPLACE FUNCTION update_order_status()
    RETURNS TRIGGER AS $$
BEGIN
    -- Actualizar el estado de la orden en la tabla "orders"
    UPDATE orders
    SET status = 'Enviada'
    WHERE order_id = NEW.order_id;

    -- Retornar la nueva fila insertada en "paid_orders"
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear un trigger que invoque la función "update_order_status" al insertar una nueva orden pagada
CREATE TRIGGER trg_update_order_status
    AFTER INSERT ON paid_orders  -- Se activa después de insertar en "paid_orders"
    FOR EACH ROW
EXECUTE FUNCTION update_order_status();

-- Función para calcular el total de una orden en base a sus detalles
CREATE OR REPLACE FUNCTION calculate_recent_order_total()
    RETURNS TRIGGER AS $$
DECLARE
    order_total DECIMAL(10, 2); -- Variable para almacenar el total calculado
BEGIN
    -- Sumar el total basado en la cantidad y precio de los detalles de la orden
    SELECT SUM(od.quantity * od.price)
    INTO order_total
    FROM order_detail od
    WHERE od.order_id = NEW.order_id;

    -- Actualizar el total calculado en la tabla "orders"
    UPDATE orders
    SET total = order_total
    WHERE order_id = NEW.order_id;

    -- Mensaje para verificar el cálculo (opcional para depuración)
    RAISE NOTICE 'Total actualizado para order_id %: %', NEW.order_id, order_total;

    -- Retornar la nueva fila procesada
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear un trigger que invoque la función "calculate_recent_order_total" al actualizar el estado de una orden
CREATE TRIGGER trg_update_order_total
    AFTER UPDATE OF status ON orders -- Se activa después de actualizar el estado en "orders"
    FOR EACH ROW
    WHEN (NEW.status IS NOT NULL)
EXECUTE FUNCTION calculate_recent_order_total();

-- Función genérica para registrar cualquier cambio en tablas específicas
CREATE OR REPLACE FUNCTION log_table_changes()
    RETURNS TRIGGER AS $$
DECLARE
    last_user_id BIGINT; -- Variable para almacenar el ID del último usuario que inició sesión
BEGIN
    -- Obtener el último usuario loggeado desde "audit_log" basado en la acción de 'Login'
    SELECT al.user_id
    INTO last_user_id
    FROM audit_log al
    WHERE al.action_type = 'Login'
    ORDER BY al.action_timestamp DESC
    LIMIT 1;

    -- Insertar un registro en la tabla de auditoría "audit_log"
    INSERT INTO audit_log (
        user_id,
        action_type,
        table_name,
        executed_query,
        action_timestamp
    )
    VALUES (
               COALESCE(last_user_id, 0), -- Usar 0 si no hay un usuario loggeado
               TG_OP,                    -- Tipo de operación (INSERT, UPDATE, DELETE)
               TG_TABLE_NAME,            -- Nombre de la tabla donde se realizó la acción
               current_query(),          -- Consulta ejecutada
               NOW()                     -- Marca de tiempo actual
           );

    -- Retornar la fila afectada
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear triggers para registrar auditorías en tablas específicas

-- Trigger para registrar auditorías en la tabla "order_detail"
CREATE TRIGGER audit_order_detail
    AFTER INSERT OR UPDATE OR DELETE  -- Se activa después de una de estas operaciones
    ON order_detail
    FOR EACH ROW
EXECUTE FUNCTION log_table_changes();

-- Trigger para registrar auditorías en la tabla "orders"
CREATE TRIGGER audit_orders
    AFTER INSERT OR DELETE            -- Solo registra inserciones y eliminaciones
    ON orders
    FOR EACH ROW
EXECUTE FUNCTION log_table_changes();

-- Trigger para registrar auditorías en la tabla "product"
CREATE TRIGGER audit_product
    AFTER INSERT OR UPDATE OR DELETE  -- Se activa para cualquier cambio
    ON product
    FOR EACH ROW
EXECUTE FUNCTION log_table_changes();

-- Función para generar un informe resumido de las acciones realizadas por cada usuario
CREATE OR REPLACE FUNCTION public.generate_user_action_report()
    RETURNS TABLE(user_id BIGINT, insert_count BIGINT, update_count BIGINT, delete_count BIGINT)
    LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY
        SELECT
            al.user_id,                                  -- ID del usuario
            COUNT(*) FILTER (WHERE al.action_type = 'INSERT') AS insert_count, -- Conteo de inserciones
            COUNT(*) FILTER (WHERE al.action_type = 'UPDATE') AS update_count, -- Conteo de actualizaciones
            COUNT(*) FILTER (WHERE al.action_type = 'DELETE') AS delete_count  -- Conteo de eliminaciones
        FROM audit_log al
        GROUP BY al.user_id                             -- Agrupar por usuario
        ORDER BY insert_count DESC, update_count DESC, delete_count DESC; -- Ordenar por acciones
END;
$function$;

-- Función para obtener los registros de auditoría de un usuario específico
CREATE OR REPLACE FUNCTION public.get_user_logs(user_id_input BIGINT)
    RETURNS TABLE(
                     audit_id INTEGER,
                     action_type TEXT,
                     table_name TEXT,
                     executed_query TEXT,
                     action_timestamp TIMESTAMP WITHOUT TIME ZONE
                 )
    LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY
        SELECT
            al.audit_id,          -- ID del registro de auditoría
            al.action_type,       -- Tipo de acción
            al.table_name,        -- Tabla afectada
            al.executed_query,    -- Consulta ejecutada
            al.action_timestamp   -- Marca de tiempo
        FROM audit_log al
        WHERE al.user_id = user_id_input; -- Filtrar por el ID del usuario
END;
$function$;

-- Conceder permisos para ejecutar las funciones al usuario de la base de datos
GRANT EXECUTE ON FUNCTION generate_user_action_report() TO postgres;
GRANT EXECUTE ON FUNCTION get_user_logs(BIGINT) TO postgres;

-- Crear la función que actualiza el estado del producto
CREATE OR REPLACE FUNCTION update_product_status()
    RETURNS TRIGGER AS $$
BEGIN
    -- Verificar si el stock llegó a 0
    IF NEW.stock = 0 THEN
        -- Actualizar el estado del producto a "Agotado"
UPDATE product
SET product_status = 'Agotado'
WHERE product_id = NEW.product_id;
ELSIF NEW.stock > 0 THEN
        -- Asegurarse de que el estado vuelva a "Disponible" si el stock aumenta
UPDATE product
SET product_status = 'Disponible'
WHERE product_id = NEW.product_id;
END IF;

    -- Retornar la fila procesada
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger que llama a la función
CREATE TRIGGER trg_update_product_status
    AFTER UPDATE OF stock ON product -- Se activa después de actualizar el stock
    FOR EACH ROW
    EXECUTE FUNCTION update_product_status();
