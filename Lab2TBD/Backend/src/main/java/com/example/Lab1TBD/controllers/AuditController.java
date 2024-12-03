package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/ranking")
    public ResponseEntity<List<Map<String, Object>>> getUserActionRanking() {
        return ResponseEntity.ok(auditService.getUserActionRanking());
    }

    @GetMapping("/logs/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getUserLogs(@PathVariable Long userId) {
        return ResponseEntity.ok(auditService.getUserLogs(userId));
    }
}
