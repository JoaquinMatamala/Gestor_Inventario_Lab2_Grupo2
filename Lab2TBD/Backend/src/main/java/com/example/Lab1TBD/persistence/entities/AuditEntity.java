package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntity {
    private Long audit_id;          // Unique ID
    private Long user_id;           // User ID
    private String action_type;     // Action type (INSERT, UPDATE, DELETE)
    private String table_name;      // Table affected name
    private String executed_query;  // Executed query
    private String action_timestamp; // Action timestamp
}
