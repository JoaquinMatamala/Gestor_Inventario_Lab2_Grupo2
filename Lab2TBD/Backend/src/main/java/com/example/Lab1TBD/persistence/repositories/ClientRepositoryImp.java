package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class ClientRepositoryImp implements ClientRepository {

    @Autowired
    private Sql2o sql2o;

    // Encontrar cliente por ID
    @Override
    public ClientEntity findClientById(Long id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM client WHERE client_id = :client_id")
                    .addParameter("client_id", id)
                    .executeAndFetchFirst(ClientEntity.class); // Obtener un solo cliente
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo básico en caso de error
        }
    }

    // Encontrar cliente por nombre
    @Override
    public ClientEntity findClientByName(String name) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM client WHERE client_name = :client_name")
                    .addParameter("client_name", name)
                    .executeAndFetchFirst(ClientEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Encontrar cliente por email
    @Override
    public ClientEntity findClientByEmail(String email) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM client WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetchFirst(ClientEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Guardar cliente
    @Override
    public void saveClient(ClientEntity client) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO client (client_name, home_location, email, password, phone_number, role) " +
                            "VALUES (:client_name, :home_location, :email, :password, :phone_number, :role)")
                    .addParameter("client_name", client.getClient_name())
                    .addParameter("home_location", client.getHome_location())
                    .addParameter("email", client.getEmail())
                    .addParameter("password", client.getPassword())
                    .addParameter("phone_number", client.getPhone_number())
                    .addParameter("role", client.getRole())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar cliente por ID
    @Override
    public void updateClient(ClientEntity client) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("UPDATE client SET client_name = :client_name, home_location = :home_location, " +
                            "email = :email, password = :password, phone_number = :phone_number WHERE client_id = :client_id")
                    .addParameter("client_id", client.getClient_id())
                    .addParameter("client_name", client.getClient_name())
                    .addParameter("home_location", client.getHome_location())
                    .addParameter("email", client.getEmail())
                    .addParameter("password", client.getPassword())
                    .addParameter("phone_number", client.getPhone_number())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borrar cliente por ID
    @Override
    public void deleteClientById(Long id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("DELETE FROM client WHERE client_id = :client_id")
                    .addParameter("client_id", id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void logUserLogin(Long userId) {
        String sql = """
        INSERT INTO audit_log (user_id, action_type, table_name, executed_query, action_timestamp)
        VALUES (:userId, 'Login', 'audit_log', 'Login', CURRENT_TIMESTAMP)
    """;

        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el login en audit_log: " + e.getMessage(), e);
        }
    }

    @Override
    public void logUserRegistration(Long userId) {
        String sql = """
        INSERT INTO audit_log (user_id, action_type, table_name, executed_query, action_timestamp)
        VALUES (:userId, 'Register', 'audit_log', 'Register', CURRENT_TIMESTAMP)
    """;

        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el registro en audit_log: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateHomeLocation(Long clientId, Long locationId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery("UPDATE client SET home_location = :locationId WHERE client_id = :clientId")
                    .addParameter("locationId", locationId)
                    .addParameter("clientId", clientId)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long findClientHomeLocation(Long clientId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT home_location FROM client WHERE client_id = :clientId")
                    .addParameter("clientId", clientId)
                    .executeScalar(Long.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





}
