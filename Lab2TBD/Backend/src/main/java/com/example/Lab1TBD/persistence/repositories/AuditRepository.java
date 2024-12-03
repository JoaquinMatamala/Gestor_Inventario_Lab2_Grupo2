package com.example.Lab1TBD.persistence.repositories;

import java.util.List;
import java.util.Map;

public interface AuditRepository {
    List<Map<String, Object>> getUserActionRanking(); // Llama al procedimiento para el ranking
    List<Map<String, Object>> getUserLogs(Long userId); // Llama al procedimiento para los logs de un usuario
}
