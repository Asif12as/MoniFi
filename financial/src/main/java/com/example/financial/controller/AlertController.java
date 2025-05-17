package com.example.financial.controller;

import com.example.financial.entity.Alert;
import com.example.financial.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Alert> getAlertById(@PathVariable Long id) {
        return alertService.findById(id)
                .map(alert -> new ResponseEntity<>(alert, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<List<Alert>> getAllAlerts() {
        List<Alert> alerts = alertService.findAllAlerts();
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }
    
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<List<Alert>> getAlertsByTransactionId(@PathVariable Long transactionId) {
        List<Alert> alerts = alertService.findByTransactionId(transactionId);
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }
    
    @GetMapping("/rule/{ruleId}")
    public ResponseEntity<List<Alert>> getAlertsByRuleId(@PathVariable Long ruleId) {
        List<Alert> alerts = alertService.findByRuleId(ruleId);
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Alert>> getAlertsByStatus(@PathVariable String status) {
        List<Alert> alerts = alertService.findByStatus(status);
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }
    
    @GetMapping("/level/{alertLevel}")
    public ResponseEntity<List<Alert>> getAlertsByLevel(@PathVariable String alertLevel) {
        List<Alert> alerts = alertService.findByAlertLevel(alertLevel);
        return new ResponseEntity<>(alerts, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/resolve")
    public ResponseEntity<Alert> resolveAlert(
            @PathVariable Long id, 
            @RequestParam(required = false) String notes) {
        try {
            Alert resolvedAlert = alertService.resolveAlert(id, notes);
            return new ResponseEntity<>(resolvedAlert, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        return alertService.findById(id)
                .map(alert -> {
                    alertService.deleteAlert(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}