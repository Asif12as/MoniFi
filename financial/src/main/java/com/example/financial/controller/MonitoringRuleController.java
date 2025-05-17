package com.example.financial.controller;

import com.example.financial.entity.MonitoringRule;
import com.example.financial.service.MonitoringRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class MonitoringRuleController {

    @Autowired
    private MonitoringRuleService ruleService;
    
    @PostMapping
    public ResponseEntity<MonitoringRule> createRule(@Valid @RequestBody MonitoringRule rule) {
        MonitoringRule savedRule = ruleService.saveRule(rule);
        return new ResponseEntity<>(savedRule, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MonitoringRule> getRuleById(@PathVariable Long id) {
        return ruleService.findById(id)
                .map(rule -> new ResponseEntity<>(rule, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping
    public ResponseEntity<List<MonitoringRule>> getAllRules() {
        List<MonitoringRule> rules = ruleService.findAllRules();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<MonitoringRule>> getActiveRules() {
        List<MonitoringRule> rules = ruleService.findActiveRules();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }
    
    @GetMapping("/type/{ruleType}")
    public ResponseEntity<List<MonitoringRule>> getRulesByType(@PathVariable String ruleType) {
        List<MonitoringRule> rules = ruleService.findByRuleType(ruleType);
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }
    
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<MonitoringRule>> getRulesBySeverity(@PathVariable String severity) {
        List<MonitoringRule> rules = ruleService.findBySeverity(severity);
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MonitoringRule> updateRule(@PathVariable Long id, @Valid @RequestBody MonitoringRule rule) {
        return ruleService.findById(id)
                .map(existingRule -> {
                    // Update rule fields
                    existingRule.setRuleName(rule.getRuleName());
                    existingRule.setDescription(rule.getDescription());
                    existingRule.setRuleType(rule.getRuleType());
                    existingRule.setThresholdValue(rule.getThresholdValue());
                    existingRule.setSeverity(rule.getSeverity());
                    
                    // Save updated rule
                    MonitoringRule updatedRule = ruleService.saveRule(existingRule);
                    return new ResponseEntity<>(updatedRule, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<MonitoringRule> activateRule(@PathVariable Long id) {
        try {
            MonitoringRule activatedRule = ruleService.activateRule(id);
            return new ResponseEntity<>(activatedRule, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<MonitoringRule> deactivateRule(@PathVariable Long id) {
        try {
            MonitoringRule deactivatedRule = ruleService.deactivateRule(id);
            return new ResponseEntity<>(deactivatedRule, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        return ruleService.findById(id)
                .map(rule -> {
                    ruleService.deleteRule(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}