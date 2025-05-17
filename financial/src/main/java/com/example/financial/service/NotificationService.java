package com.example.financial.service;

import com.example.financial.entity.Alert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${n8n.api.url}")
    private String n8nBaseUrl;
    
    @Value("${n8n.api.transaction-alert-webhook}")
    private String alertWebhook;
    
    /**
     * Send alert notification to n8n workflow
     */
    public void sendAlertNotification(Alert alert) {
        try {
            String webhookUrl = n8nBaseUrl + alertWebhook;
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> payload = createAlertPayload(alert);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            
            // Send to n8n webhook
            restTemplate.postForObject(webhookUrl, request, String.class);
            
            System.out.println("Alert notification sent to n8n: " + alert.getId());
        } catch (Exception e) {
            System.err.println("Failed to send alert notification: " + e.getMessage());
            // In a production system, you would log this error properly
        }
    }
    
    /**
     * Create payload for n8n webhook
     */
    private Map<String, Object> createAlertPayload(Alert alert) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("alertId", alert.getId());
        payload.put("transactionId", alert.getTransaction().getId());
        payload.put("transactionRef", alert.getTransaction().getTransactionRef());
        payload.put("amount", alert.getTransaction().getAmount());
        payload.put("accountId", alert.getTransaction().getAccount().getId());
        payload.put("accountNumber", alert.getTransaction().getAccount().getAccountNumber());
        payload.put("ruleName", alert.getRule().getRuleName());
        payload.put("ruleType", alert.getRule().getRuleType());
        payload.put("alertLevel", alert.getAlertLevel());
        payload.put("timestamp", alert.getCreatedAt().toString());
        
        return payload;
    }
}