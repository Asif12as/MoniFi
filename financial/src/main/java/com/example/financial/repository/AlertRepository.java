package com.example.financial.repository;

import com.example.financial.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByTransactionId(Long transactionId);
    
    List<Alert> findByRuleId(Long ruleId);
    
    List<Alert> findByStatus(String status);
    
    List<Alert> findByAlertLevel(String alertLevel);
}