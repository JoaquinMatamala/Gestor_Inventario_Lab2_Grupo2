-- Creación de la base de datos
CREATE DATABASE Lab2TBD;

CREATE EXTENSION IF NOT EXISTS plpgsql;
CREATE EXTENSION IF NOT EXISTS postgis;

-- Tabla: Delivery Point
CREATE TABLE IF NOT EXISTS delivery_point (
    delivery_point_id serial PRIMARY KEY,
    delivery_point_name VARCHAR(255),
    status_point BOOLEAN,
    rating FLOAT,
    comment VARCHAR(255),
    delivery_location_point BIGINT,
    deliveryman_id BIGINT,
    client_id BIGINT
    );

-- Tabla: Establishment
CREATE TABLE IF NOT EXISTS establishment (
    establishment_id serial PRIMARY KEY,
    establishment_data VARCHAR(255),
    region_data VARCHAR(255),
    location_id BIGINT
    );

-- Tabla: Location
CREATE TABLE IF NOT EXISTS location (
    location_id serial PRIMARY KEY,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    position GEOMETRY(Point, 4326), -- Para almacenar la ubicación en formato geométrico
    address VARCHAR(255),
    location_type VARCHAR(255)
    );

-- Tabla: Order Detail
CREATE TABLE IF NOT EXISTS order_detail (
    order_detail_id SERIAL PRIMARY KEY,
    product_id BIGINT,
    quantity INT,
    price FLOAT,
    order_id BIGINT
);

-- Tabla: Orders
CREATE TABLE IF NOT EXISTS orders (
    order_id SERIAL PRIMARY KEY,
    date TIMESTAMP,
    status VARCHAR(255),
    total FLOAT,
    delivery_point_id BIGINT,
    client_id BIGINT
    );

-- Tabla: Product
CREATE TABLE IF NOT EXISTS product (
   product_id SERIAL PRIMARY KEY,
   product_name VARCHAR(255),
    description VARCHAR(255),
    price FLOAT,
    stock INT,
    product_status VARCHAR(255),
    category_id BIGINT
    );

-- Tabla: Category
CREATE TABLE IF NOT EXISTS category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255)
    );

-- Tabla: Client
CREATE TABLE IF NOT EXISTS client (
    client_id serial PRIMARY KEY,
    client_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    phone_number VARCHAR(20),
    home_location BIGINT,
    role VARCHAR(255)
    );

-- Tabla: Delivery Man
CREATE TABLE IF NOT EXISTS delivery_man (
    deliveryman_id SERIAL PRIMARY KEY,
    client_id BIGINT,
    establishment_id BIGINT
);

-- Tabla: Rating
CREATE TABLE IF NOT EXISTS rating (
    rating_id SERIAL PRIMARY KEY,
    address VARCHAR(255),
    rating FLOAT
);



------------------------------------------------------------- nuevo

create table if not exists establishment
(
    establishment_id                        BIGINT,
    establishmet_data                       text,
    region_data                             text,
    latitude                                text,
    longitude                               text
);

alter table establishment
    owner to postgres;

create table if not exists pos_establishments
(
    establishment_id      BIGINT not null primary key,
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

------------------------------------------

-- Poblado de las tablas
INSERT INTO client (client_name, email, password, phone_number,home_location,role) VALUES
                                                                                       ('Juan Perez', 'jp@gmail.com', '123ahbz#2', '+56987654321', 0,'CLIENTE'),
                                                                                       ('Maria Rodriguez', 'maria@gmail.com', '456ahbz#2', '+56987654350',1,'CLIENTE'),
                                                                                       ('Pedro Gomez', 'pedro@gmail.com', '789ahbz#2', '+56987667321',2,'ADMIN'),
                                                                                       ('Ana Martinez', 'ana@gmail.com', '123ahbz#2', '+56984447321',3,'ADMIN'),
                                                                                       ('Carlos Sanchez', 'carlos@gmail.com', '456ahbz#2', '+56987654891',4,'CLIENTE');

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

------------ datos para el posgis

CREATE TABLE IF NOT EXISTS areas_geograficas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    area GEOMETRY(POLYGON, 4326) -- Representación geométrica del área
);

INSERT INTO areas_geograficas (nombre, area)
VALUES (
           'Zona de Entrega 1',
           ST_GeomFromText('POLYGON((-99.2 19.4, -99.1 19.4, -99.1 19.3, -99.2 19.3, -99.2 19.4))', 4326)
);

---------- verifica que el punto esta dentro del area -----

SELECT
    dp.delivery_point_id,
    dp.delivery_point_name,
    dp.comment,
    l.position,
    ag.nombre AS area_nombre,
    ST_Within(l.position, ag.area) AS dentro_del_area
FROM
    delivery_point dp
        JOIN
    location l ON dp.delivery_location_point = l.location_id
        JOIN
    areas_geograficas ag ON ST_Within(l.position, ag.area)
WHERE
    dp.status_point = TRUE; -- Filtrar solo puntos activos


--------- verifica si un establecimiento esta den del area

SELECT
    ve.establishment_id,
    ve.establishment_data,
    ve.region_data,
    ag.nombre AS area_nombre,
    ST_Within(ve.position, ag.area) AS dentro_del_area
FROM
    view_establishment ve
        JOIN
    areas_geograficas ag ON ST_Within(ve.position, ag.area);

-------------------------------------------------- indices

CREATE INDEX idx_location_position ON location USING GIST (position);
CREATE INDEX idx_areas_geograficas_area ON areas_geograficas USING GIST (area);
CREATE INDEX idx_pos_establishments_geom ON pos_establishments USING GIST (geom);

------------------------ Calcular la distancia a los puntos fuera del área

SELECT
    dp.delivery_point_id,
    dp.delivery_point_name,
    dp.comment,
    l.position,
    ag.nombre AS area_nombre,
    ST_Distance(l.position, ag.area) AS distancia_al_area
FROM
    delivery_point dp
        JOIN
    location l ON dp.delivery_location_point = l.location_id
        JOIN
    areas_geograficas ag ON NOT ST_Within(l.position, ag.area)
WHERE
    dp.status_point = TRUE -- Solo puntos activos
ORDER BY
    distancia_al_area ASC
    LIMIT 5; -- Obtener los 5 puntos más cercanos