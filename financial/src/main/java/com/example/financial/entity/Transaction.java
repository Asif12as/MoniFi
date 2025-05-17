package com.example.financial.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "transaction_ref", nullable = false, unique = true)
    private String transactionRef;
    
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;
    
    private String description;
    
    @Column(nullable = false)
    private String status = "PENDING";
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    private String location;
    
    @Column(name = "device_info")
    private String deviceInfo;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}