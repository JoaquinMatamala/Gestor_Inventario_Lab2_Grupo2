package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public List<Map<String, Object>> getUserActionRanking() {
        return auditRepository.getUserActionRanking();
    }

    public List<Map<String, Object>> getUserLogs(Long userId) {
        return auditRepository.getUserLogs(userId);
    }
}
