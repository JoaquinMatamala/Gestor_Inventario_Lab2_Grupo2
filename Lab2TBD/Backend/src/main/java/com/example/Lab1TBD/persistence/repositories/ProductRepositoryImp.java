package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    @Autowired
    private Sql2o sql2o;

    // Buscar producto por ID
    @Override
    public ProductEntity findProductById(Long product_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE product_id = :product_id")
                    .addParameter("product_id", product_id)
                    .executeAndFetchFirst(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductEntity> findAllProducts(){
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM product")
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar producto por nombre
    @Override
    public List<ProductEntity> findProductByName(String product_name) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE product_name LIKE :product_name")
                    .addParameter("product_name", "%" + product_name + "%")
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar productos por estado
    @Override
    public List<ProductEntity> findProductsByStatus(String product_status) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE product_status = :product_status")
                    .addParameter("product_status", product_status)
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar productos por stock
    @Override
    public List<ProductEntity> findProductByStock(int stock) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE stock = :stock")
                    .addParameter("stock", stock)
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Buscar productos por descripción
    @Override
    public List<ProductEntity> findProductByDescription(String description) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE description ILIKE :description")
                    .addParameter("description", "%" + description + "%") // Búsqueda parcial
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Buscar productos por precio
    @Override
    public List<ProductEntity> findProductByPrice(float price) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE price = :price")
                    .addParameter("price", price)
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Buscar productos por categoría
    @Override
    public List<ProductEntity> findProductsByCategoryId(Long category_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM product WHERE category_id = :category_id")
                    .addParameter("category_id", category_id)
                    .executeAndFetch(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Guardar un nuevo producto
    @Override
    public void saveProduct(ProductEntity product) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO product (product_name, description, price, stock, product_status, category_id) " +
                            "VALUES (:product_name, :description, :price, :stock, :product_status, :category_id)")
                    .addParameter("product_name", product.getProduct_name())
                    .addParameter("description", product.getDescription())
                    .addParameter("price", product.getPrice())
                    .addParameter("stock", product.getStock())
                    .addParameter("product_status", product.getProduct_status())
                    .addParameter("category_id", product.getCategory_id())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar un producto por ID
    @Override
    public void updateProduct(ProductEntity product) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("UPDATE product " +
                            "SET product_name = :product_name, description = :description, price = :price, " +
                            "stock = :stock, product_status = :product_status, category_id = :category_id " +
                            "WHERE product_id = :product_id")
                    .addParameter("product_name", product.getProduct_name())
                    .addParameter("description", product.getDescription())
                    .addParameter("price", product.getPrice())
                    .addParameter("stock", product.getStock())
                    .addParameter("product_status", product.getProduct_status())
                    .addParameter("category_id", product.getCategory_id())
                    .addParameter("product_id", product.getProduct_id())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borrar un producto por ID
    @Override
    public void deleteProductById(Long product_id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("DELETE FROM product WHERE product_id = :product_id")
                    .addParameter("product_id", product_id)
                    .executeUpdate();
        } catch (Exception e) {
            // Registrar el error en lugar de solo imprimir
            throw new RuntimeException("Error al eliminar el producto con ID " + product_id, e);
        }
    }

    @Override
    public void updateProductStock(Long orderId) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            // Verificar si hay suficiente stock para todos los productos en la orden
            String verifyStockQuery = """
        SELECT COUNT(*)
        FROM order_detail od
        JOIN product p ON od.product_id = p.product_id
        WHERE od.order_id = :orderId
          AND od.quantity > p.stock
        """;

            Long count = con.createQuery(verifyStockQuery)
                    .addParameter("orderId", orderId)
                    .executeScalar(Long.class);

            // Si algún producto no tiene suficiente stock, lanzamos una excepción
            if (count > 0) {
                throw new IllegalStateException("El stock es insuficiente para uno o más productos de la orden.");
            }

            // Actualizar el stock de todos los productos relacionados con la orden
            String updateStockQuery = """
        UPDATE product
        SET stock = stock - (
            SELECT od.quantity
            FROM order_detail od
            WHERE od.product_id = product.product_id
              AND od.order_id = :orderId
        )
        WHERE product_id IN (
            SELECT product_id
            FROM order_detail
            WHERE order_id = :orderId
        )
        """;

            int rowsUpdated = con.createQuery(updateStockQuery)
                    .addParameter("orderId", orderId)
                    .executeUpdate()
                    .getResult();

            if (rowsUpdated == 0) {
                throw new IllegalStateException("No se pudo actualizar el stock. Verifica el stock disponible.");
            }

            // Insertar el orderId en la tabla paid_orders
            String insertPaidOrderQuery = """
        INSERT INTO paid_orders (order_id)
        VALUES (:orderId)
        """;

            con.createQuery(insertPaidOrderQuery)
                    .addParameter("orderId", orderId)
                    .executeUpdate();

            // Confirmar la transacción
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el stock: " + e.getMessage());
        }
    }




    @Override
    public int getProductStockById(Long productId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            String query = "SELECT stock FROM product WHERE product_id = :productId";
            return con.createQuery(query)
                    .addParameter("productId", productId)
                    .executeScalar(Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el stock del producto: " + productId);
        }
    }


}