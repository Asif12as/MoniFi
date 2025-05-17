package com.example.financial.repository;

import com.example.financial.entity.MonitoringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringRuleRepository extends JpaRepository<MonitoringRule, Long> {
    List<MonitoringRule> findByIsActiveTrue();
    
    List<MonitoringRule> findByRuleType(String ruleType);
    
    List<MonitoringRule> findBySeverity(String severity);
}