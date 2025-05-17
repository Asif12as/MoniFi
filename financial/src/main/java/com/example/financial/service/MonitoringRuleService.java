package com.example.financial.service;

import com.example.financial.entity.MonitoringRule;
import com.example.financial.repository.MonitoringRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonitoringRuleService {
    
    @Autowired
    private MonitoringRuleRepository monitoringRuleRepository;
    
    public MonitoringRule saveRule(MonitoringRule rule) {
        return monitoringRuleRepository.save(rule);
    }
    
    public Optional<MonitoringRule> findById(Long id) {
        return monitoringRuleRepository.findById(id);
    }
    
    public List<MonitoringRule> findAllRules() {
        return monitoringRuleRepository.findAll();
    }
    
    public List<MonitoringRule> findActiveRules() {
        return monitoringRuleRepository.findByIsActiveTrue();
    }
    
    public List<MonitoringRule> findByRuleType(String ruleType) {
        return monitoringRuleRepository.findByRuleType(ruleType);
    }
    
    public List<MonitoringRule> findBySeverity(String severity) {
        return monitoringRuleRepository.findBySeverity(severity);
    }
    
    public MonitoringRule activateRule(Long id) {
        MonitoringRule rule = monitoringRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        
        rule.setIsActive(true);
        return monitoringRuleRepository.save(rule);
    }
    
    public MonitoringRule deactivateRule(Long id) {
        MonitoringRule rule = monitoringRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        
        rule.setIsActive(false);
        return monitoringRuleRepository.save(rule);
    }
    
    public void deleteRule(Long id) {
        monitoringRuleRepository.deleteById(id);
    }
}