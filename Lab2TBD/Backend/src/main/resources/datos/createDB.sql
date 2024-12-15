-- Creación de la base de datos
CREATE DATABASE Lab2TBD;
CREATE EXTENSION plpgsql;
CREATE EXTENSION postgis;


-- Tables creation
CREATE TABLE IF NOT EXISTS delivery_point (
    delivery_point_id BIGINT PRIMARY KEY,
    delivery_point_name VARCHAR(255),
    status_point BOOLEAN,
    rating FLOAT,
    comment VARCHAR(255),
    delivery_location_point BIGINT,
    deliveryman_id BIGINT,
    client_id BIGINT

);

CREATE TABLE IF NOT EXISTS establishment (
    establishment_id serial PRIMARY KEY,
    establishment_data VARCHAR(255),
    region_data VARCHAR(255),
    location_id BIGINT
);

CREATE TABLE IF NOT EXISTS location (
    location_id BIGINT PRIMARY KEY,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    position POINT,
    address VARCHAR(255),
    location_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS order_detail (
    order_detail_id serial PRIMARY KEY,
    product_id BIGINT,
    quantity INT,
    price FLOAT,
    order_id BIGINT
);

CREATE TABLE IF NOT EXISTS orders (
    order_id serial PRIMARY KEY,
    date TIMESTAMP,
    status VARCHAR(255),
    total FLOAT,
    client_id BIGINT
);


CREATE TABLE IF NOT EXISTS product (
    product_id serial PRIMARY KEY,
    product_name VARCHAR(255),
    description VARCHAR(255),
    price FLOAT,
    stock INT,
    product_status VARCHAR(255),
    category_id BIGINT
);

CREATE TABLE IF NOT EXISTS category (
    category_id serial PRIMARY KEY,
    category_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS client (
    client_id serial PRIMARY KEY,
    client_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    phone_number VARCHAR(20),
    home_location BIGINT,
    role VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS delivery_man (
    deliveryman_id serial PRIMARY KEY,
    client_id BIGINT,
    establishment_id BIGINT
);


------------------------------------------------------------- nuevo

create table if not exists establishment
(
    establishment_id                        integer,
    establishmet_data                       text,
    establecimientocodigoantiguo            text,
    establecimientocodigomadreantiguo       text,
    establecimientocodigomadrenuevo         DOUBLE PRECISION,
    region_id                               integer,
    region_data                             text,
    seremisaludcodigo_serviciodesaludcodigo DOUBLE PRECISION,
    seremisaludglosa_serviciodesaludglosa   text,
    tipopertenenciaestabglosa               text,
    tipoestablecimientoglosa                text,
    ambitofuncionamiento                    text,
    certificacion                           text,
    dependenciaadministrativa               text,
    nivelatencionestabglosa                 text,
    comunacodigo                            text,
    comunaglosa                             text,
    tipoviaglosa                            text,
    numero                                  text,
    nombrevia                               text,
    telefonomovil_telefonofijo              text,
    fechainiciofuncionamientoestab          text,
    tieneserviciourgencia                   text,
    tipourgencia                            text,
    clasificaciontiposapu                   text,
    latitude                                text,
    longitude                               text,
    tiposistemasaludglosa                   text,
    estadofuncionamiento                    text,
    nivelcomplejidadestabglosa              text,
    tipoatencionestabglosa                  text,
    fechaincorporacion                      text
);

alter table establishment
    owner to postgres;

create table if not exists pos_establishments
(
    establishment_id      integer not null primary key,
    latitude              DOUBLE PRECISION,
    longitude             DOUBLE PRECISION,
    geom                  geometry(Point, 4326)
    );

alter table pos_establishments
    owner to postgres;

create view view_establishment (establishment_id, establishment_data, region_data, latitude, longitude, position) as
SELECT e.establishment_id,
       e.establishment_data,
       e.region_data,
       pe.latitude,
       pe.longitude,
       pe.geom AS position
FROM establishment e
         LEFT JOIN pos_establishments pe ON e.establishment_id = pe.establishment_id;

alter table view_establishment
    owner to postgres;

-----------------------------------------

DROP TABLE IF EXISTS delivery_point;
CREATE TABLE IF NOT EXISTS delivery_point (
    delivery_point_id serial PRIMARY KEY,
    delivery_point_name TEXT,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    position TEXT,
    status_point BOOL,
    rating INT,
    FOREIGN KEY (client_id) REFERENCES client(client_id)
)

------------------------------------------

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

INSERT INTO establishment (establishment_data, region_data, location_id) VALUES
                                                                            ('Preunic', 'Metropolitana', 1),
                                                                            ('Calzados beba', 'Valparaiso', 2),
                                                                            ('Fruna', 'Coquimbo', 3);

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
