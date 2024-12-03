package com.example.Lab1TBD.persistence.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Map;

@Repository
public class AuditRepositoryImp implements AuditRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Map<String, Object>> getUserActionRanking() {
        String sql = "SELECT * FROM generate_user_action_report()"; // Procedimiento almacenado para el ranking
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetchTable()
                    .asList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el ranking de acciones de usuarios: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getUserLogs(Long userId) {
        String sql = "SELECT * FROM get_user_logs(:userId)"; // Procedimiento almacenado para los logs de un usuario
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeAndFetchTable()
                    .asList();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los logs del usuario: " + e.getMessage(), e);
        }
    }
}
