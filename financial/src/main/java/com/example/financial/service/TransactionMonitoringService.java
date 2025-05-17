package com.example.financial.service;

import com.example.financial.entity.Alert;
import com.example.financial.entity.MonitoringRule;
import com.example.financial.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionMonitoringService {

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private MonitoringRuleService ruleService;
    
    @Autowired
    private AlertService alertService;
    
    @Autowired
    private NotificationService notificationService;
    
    /**
     * Monitor a transaction against all active rules
     */
    public void monitorTransaction(Transaction transaction) {
        List<MonitoringRule> activeRules = ruleService.findActiveRules();
        
        for (MonitoringRule rule : activeRules) {
            if (evaluateRule(transaction, rule)) {
                Alert alert = createAlert(transaction, rule);
                notificationService.sendAlertNotification(alert);
            }
        }
    }
    
    /**
     * Evaluate if a transaction violates a rule
     */
    private boolean evaluateRule(Transaction transaction, MonitoringRule rule) {
        switch (rule.getRuleType()) {
            case "LARGE_AMOUNT":
                return evaluateLargeAmountRule(transaction, rule);
            case "HIGH_FREQUENCY":
                return evaluateHighFrequencyRule(transaction, rule);
            case "UNUSUAL_LOCATION":
                return evaluateUnusualLocationRule(transaction, rule);
            default:
                return false;
        }
    }
    
    /**
     * Check if transaction amount exceeds threshold
     */
    private boolean evaluateLargeAmountRule(Transaction transaction, MonitoringRule rule) {
        return transaction.getAmount().compareTo(rule.getThresholdValue()) >= 0;
    }
    
    /**
     * Check if account has unusually high transaction frequency
     */
    private boolean evaluateHighFrequencyRule(Transaction transaction, MonitoringRule rule) {
        int recentCount = transactionService.countRecentTransactions(
            transaction.getAccount().getId(), 24); // Check last 24 hours
        return recentCount >= rule.getThresholdValue().intValue();
    }
    
    /**
     * Check if transaction location is unusual
     */
    private boolean evaluateUnusualLocationRule(Transaction transaction, MonitoringRule rule) {
        // This is a simplified example
        // In a real application, you would compare with known locations or use geolocation services
        
        if (transaction.getLocation() == null || transaction.getLocation().isEmpty()) {
            return false;
        }
        
        // Example: Flag transactions from specific countries or regions
        String location = transaction.getLocation().toLowerCase();
        String[] suspiciousLocations = {"unknown", "anonymous", "suspicious-region"};
        
        for (String suspiciousLocation : suspiciousLocations) {
            if (location.contains(suspiciousLocation)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Create an alert for a flagged transaction
     */
    private Alert createAlert(Transaction transaction, MonitoringRule rule) {
        Alert alert = new Alert();
        alert.setTransaction(transaction);
        alert.setRule(rule);
        alert.setAlertLevel(rule.getSeverity());
        alert.setStatus("NEW");
        
        return alertService.saveAlert(alert);
    }
}