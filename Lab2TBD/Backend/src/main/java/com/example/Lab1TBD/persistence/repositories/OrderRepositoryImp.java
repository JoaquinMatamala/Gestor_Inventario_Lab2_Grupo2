package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class OrderRepositoryImp implements OrderRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public OrderEntity findByOrderId(Long order_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders WHERE order_id = :order_id")
                    .addParameter("order_id", order_id)
                    .executeAndFetchFirst(OrderEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderEntity> findAllOrders() {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders")
                    .executeAndFetch(OrderEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderEntity> findByClientId(Long clientId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders WHERE client_id = :client_id")
                    .addParameter("client_id", clientId)
                    .executeAndFetch(OrderEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderEntity> findByStatus(String status) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM orders WHERE status = :status")
                    .addParameter("status", status)
                    .executeAndFetch(OrderEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long saveOrder(OrderEntity order) {
        String insertQuery = "INSERT INTO orders (date, status, total, client_id) " +
                "VALUES (:date, :status, :total, :client_id)";

        // Usamos RETURN_GENERATED_KEYS para obtener el ID generado
        try (org.sql2o.Connection connection = sql2o.open()) {
            Long generatedId = connection.createQuery(insertQuery, true)
                    .addParameter("date", order.getDate())
                    .addParameter("status", order.getStatus())
                    .addParameter("total", order.getTotal())
                    .addParameter("client_id", order.getClient_id())
                    .executeUpdate()
                    .getKey(Long.class); // Obtenemos el ID generado como Long
            return generatedId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar la orden en la base de datos", e);
        }
    }

    @Override
    public void updateOrder(OrderEntity order) {
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery("UPDATE orders SET " +
                            "date = :date, status = :status, total = :total, client_id = :client_id " +
                            "WHERE order_id = :order_id")
                    .addParameter("date", order.getDate())
                    .addParameter("status", order.getStatus())
                    .addParameter("total", order.getTotal())
                    .addParameter("client_id", order.getClient_id())
                    .addParameter("order_id", order.getOrder_id())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderById(Long orderId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM orders WHERE order_id = :orderId")
                    .addParameter("orderId", orderId)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void updateOrderStatus(Long orderId, String status) {
        String query = """
            UPDATE orders
            SET status = :status
            WHERE order_id = :orderId
        """;

        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("orderId", orderId)
                    .addParameter("status", status)
                    .executeUpdate();
        }
    }

}
