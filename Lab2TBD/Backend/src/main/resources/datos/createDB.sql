-- Creación de la base de datos
CREATE DATABASE Lab1TBD;

-- Creación de tablas
DROP TABLE IF EXISTS client;
CREATE TABLE client (
                        client_id serial PRIMARY KEY,
                        client_name VARCHAR(255),
                        address VARCHAR(255),
                        email VARCHAR(100),
                        password TEXT,
                        phone_number VARCHAR(20)
);

DROP TABLE IF EXISTS category;
CREATE TABLE category (
                          category_id serial PRIMARY KEY,
                          category_name VARCHAR(100)
);

DROP TABLE IF EXISTS product;
CREATE TABLE product (
                         product_id serial PRIMARY KEY,
                         product_name VARCHAR(255),
                         description TEXT,
                         price DECIMAL(10, 2),
                         stock INT,
                         product_status VARCHAR(50),
                         category_id INT,
                         FOREIGN KEY (category_id) REFERENCES category(category_id)
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
                         order_id serial PRIMARY KEY,
                         date TIMESTAMP,
                         status VARCHAR(50),
                         total DECIMAL(10, 2),
                         client_id INT,
                         FOREIGN KEY (client_id) REFERENCES client(client_id)
);

DROP TABLE IF EXISTS order_detail;
CREATE TABLE order_detail (
                              order_detail_id serial PRIMARY KEY,
                              quantity INT,
                              price DECIMAL(10, 2),
                              order_id INT,
                              product_id INT,
                              FOREIGN KEY (order_id) REFERENCES orders(order_id),
                              FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- Poblado de las tablas
INSERT INTO client (client_name, address, email, password, phone_number) VALUES
                                                                             ('Juan Perez', 'Victor Hugo #801', 'jp@gmail.com', '123ahbz#2', '+56987654321'),
                                                                             ('Maria Rodriguez', 'Avenida de las Americas #123', 'maria@gmail.com', '456ahbz#2', '+56987654350'),
                                                                             ('Pedro Gomez', 'Calle del Sol #456', 'pedro@gmail.com', '789ahbz#2', '+56987667321'),
                                                                             ('Ana Martinez', 'Calle de la Luna #789', 'ana@gmail.com', '123ahbz#2', '+56984447321'),
                                                                             ('Carlos Sanchez', 'Calle del Cielo #321', 'carlos@gmail.com', '456ahbz#2', '+56987654891');

INSERT INTO category (category_name) VALUES
                                         ('Electrodomésticos'),
                                         ('Ropa'),
                                         ('Juguetes'),
                                         ('Hogar'),
                                         ('Bebidas');

INSERT INTO product (product_name, description, price, stock, product_status, category_id) VALUES
                                                                                               ('Televisor', 'Televisor Sony', 500000, 10, 'Disponible', 1),
                                                                                               ('Refrigerador', 'Refrigerador LG', 300000, 5, 'Disponible', 1),
                                                                                               ('Lavadora', 'Lavadora LG', 400000, 8, 'Disponible', 1),
                                                                                               ('Plancha', 'Plancha LG', 15000, 20, 'Disponible', 1),
                                                                                               ('Zapatillas', 'Zapatillas Nike', 54500, 30, 'Disponible', 2),
                                                                                               ('Pelota', 'Pelota de fútbol', 9000, 15, 'Disponible', 3),
                                                                                               ('Sofá', 'Sofá de lujo', 50000, 8, 'Disponible', 4),
                                                                                               ('Agua', 'Agua mineral', 500, 25, 'Disponible', 5),
                                                                                               ('Vino', 'Vino tinto', 3000, 15, 'Disponible', 5),
                                                                                               ('Cerveza', 'Corona', 2000, 10, 'Disponible', 5),
                                                                                               ('Purple Kush', 'Fineza', 20000, 5, 'Disponible', 5);

INSERT INTO orders (date, status, total, client_id) VALUES
                                                         ('2023-06-01', 'Pendiente', 200000, 1),
                                                         ('2023-06-02', 'Entregado', 300000, 2),
                                                         ('2023-06-03', 'Pendiente', 150000, 3),
                                                         ('2023-06-04', 'Pendiente', 250000, 4),
                                                         ('2023-06-05', 'Pendiente', 180000, 5);

INSERT INTO order_detail (quantity, price, order_id, product_id) VALUES
                                                                     (2, 500000, 1, 1),
                                                                     (1, 300000, 2, 2),
                                                                     (3, 150000, 3, 3),
                                                                     (2, 250000, 4, 4),
                                                                     (1, 180000, 5, 5);

-- Consulta para calcular los ingresos por producto agrupados por categoría y porcentaje de ventas
WITH TotalIncome AS (
    SELECT SUM(o.total) AS total_income
    FROM orders o
    WHERE o.status != 'Pendiente' -- Solo incluir órdenes completadas
    ),
    ProductIncome AS (
SELECT
    p.product_id,
    p.product_name,
    c.category_name,
    SUM(od.quantity * od.price) AS product_income
FROM order_detail od
    JOIN orders o ON od.order_id = o.order_id
    JOIN product p ON od.product_id = p.product_id
    JOIN category c ON p.category_id = c.category_id
WHERE o.status != 'Pendiente' -- Solo incluir órdenes completadas
GROUP BY p.product_id, p.product_name, c.category_name
    )
SELECT
    pi.category_name,
    pi.product_name,
    pi.product_income,
    ROUND((pi.product_income / (SELECT total_income FROM TotalIncome) * 100), 2) AS percentage_of_sales
FROM ProductIncome pi
ORDER BY pi.category_name, pi.product_income DESC;
