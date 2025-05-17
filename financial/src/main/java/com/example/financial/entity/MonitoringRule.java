package com.example.financial.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitoring_rules")
@Data
public class MonitoringRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rule_name", nullable = false)
    private String ruleName;
    
    private String description;
    
    @Column(name = "rule_type", nullable = false)
    private String ruleType;
    
    @Column(name = "threshold_value")
    private BigDecimal thresholdValue;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private String severity = "MEDIUM";
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}