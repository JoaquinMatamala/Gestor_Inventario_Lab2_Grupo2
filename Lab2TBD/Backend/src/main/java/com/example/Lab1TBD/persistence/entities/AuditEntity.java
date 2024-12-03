package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntity {
    private Long audit_id;          // Identificador único para el registro de auditoría
    private Long user_id;           // ID del usuario que realizó la acción
    private String action_type;     // Tipo de acción (INSERT, UPDATE, DELETE)
    private String table_name;      // Nombre de la tabla afectada
    private String executed_query;  // Consulta SQL ejecutada
    private String action_timestamp; // Fecha y hora de la acción
}
