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
CREATE TABLE IF NOT EXISTS delivery_zone (
    zone_id SERIAL PRIMARY KEY,
    delivery_establishment_id BIGINT,
    delivery_polygon GEOMETRY(POLYGON, 4326)
);

CREATE TABLE IF NOT EXISTS restricted_zone (
    zone_id SERIAL PRIMARY KEY,
    restricted_address VARCHAR(255),
    restricted_polygon GEOMETRY(POLYGON, 4326),
    rating FLOAT
    );

------------------------------------------

-- Poblado de las tablas
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




------------ POSTGIS

-- Consulta para determinar si un cliente se encuentra dentro de una zona de reparto
WITH client_location AS (
    -- Obtener la geometría (position) de la ubicación del cliente
    SELECT
        l.position AS client_position
    FROM
        client c
            JOIN
        location l ON c.home_location = l.location_id
    WHERE
        c.client_id = 5 -- Reemplazar por id del cliente a evaluar
)
SELECT
    CASE
        WHEN EXISTS (
            SELECT 1
            FROM delivery_zone dz, client_location cl
            WHERE ST_Within(cl.client_position, dz.delivery_polygon)
        )
            THEN 'Dentro de zona de reparto'
        ELSE 'Fuera de zona de reparto'
        END AS zone_status;

-- Listar los repartidores que han entregado dentro de una zona de reparto (a partir de un establishment)

WITH delivery_polygon AS (
    -- Obtener el polígono de la zona de reparto asociada al establecimiento
    SELECT dz.delivery_polygon
    FROM delivery_zone dz
    WHERE dz.delivery_establishment_id = 3
),
     valid_delivery_points AS (
         -- Buscar delivery_points que tengan rating y deliveryman_id válidos
         SELECT dp.deliveryman_id
         FROM delivery_point dp
                  JOIN location l ON dp.delivery_location_point = l.location_id
                  CROSS JOIN delivery_polygon dz
         WHERE dp.rating IS NOT NULL
           AND dp.deliveryman_id IS NOT NULL
           AND ST_Within(l.position, dz.delivery_polygon) -- Verificar que la ubicación está dentro del polígono
     )
SELECT DISTINCT dm.deliveryman_id AS repartidor_id, c.client_name AS repartidor_nombre
FROM valid_delivery_points vdp
         JOIN delivery_man dm ON vdp.deliveryman_id = dm.deliveryman_id
         JOIN client c ON dm.client_id = c.client_id;


-- Query para saber si una dirección de entrega se encuentra en una zona restringida
SELECT
    dp.delivery_point_id,
    l.address AS delivery_address,
    CASE
        WHEN EXISTS (
            SELECT 1
            FROM restricted_zone rz
            WHERE ST_Within(l.position, rz.restricted_polygon)
        ) THEN 'Dentro de zona restringida'
        ELSE 'Fuera de zona restringida'
        END AS restriction_status
FROM delivery_point dp
         JOIN location l ON dp.delivery_location_point = l.location_id
WHERE dp.delivery_point_id = 5;

-- Queries para obtener geojson de las zonas de reparto y zonas restringidas
SELECT ST_AsGeoJSON(restricted_polygon) AS geojson
FROM restricted_zone;

SELECT ST_AsGeoJSON(delivery_polygon) AS geojson
FROM delivery_zone;