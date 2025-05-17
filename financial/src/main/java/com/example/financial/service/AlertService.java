package com.example.financial.service;

import com.example.financial.entity.Alert;
import com.example.financial.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {
    
    @Autowired
    private AlertRepository alertRepository;
    
    public Alert saveAlert(Alert alert) {
        return alertRepository.save(alert);
    }
    
    public Optional<Alert> findById(Long id) {
        return alertRepository.findById(id);
    }
    
    public List<Alert> findAllAlerts() {
        return alertRepository.findAll();
    }
    
    public List<Alert> findByTransactionId(Long transactionId) {
        return alertRepository.findByTransactionId(transactionId);
    }
    
    public List<Alert> findByRuleId(Long ruleId) {
        return alertRepository.findByRuleId(ruleId);
    }
    
    public List<Alert> findByStatus(String status) {
        return alertRepository.findByStatus(status);
    }
    
    public List<Alert> findByAlertLevel(String alertLevel) {
        return alertRepository.findByAlertLevel(alertLevel);
    }
    
    public Alert resolveAlert(Long id, String notes) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        
        alert.setStatus("RESOLVED");
        alert.setNotes(notes);
        alert.setResolvedAt(LocalDateTime.now());
        
        return alertRepository.save(alert);
    }
    
    public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }
}