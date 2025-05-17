package com.example.financial.service;

import com.example.financial.entity.Transaction;
import com.example.financial.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    public Transaction createTransaction(Transaction transaction) {
        // Generate a unique transaction reference if not provided
        if (transaction.getTransactionRef() == null || transaction.getTransactionRef().isEmpty()) {
            transaction.setTransactionRef(UUID.randomUUID().toString());
        }
        
        return transactionRepository.save(transaction);
    }
    
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }
    
    public Optional<Transaction> findByTransactionRef(String transactionRef) {
        return transactionRepository.findByTransactionRef(transactionRef);
    }
    
    public List<Transaction> findByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
    
    public List<Transaction> findByStatus(String status) {
        return transactionRepository.findByStatus(status);
    }
    
    public List<Transaction> getRecentTransactions(Long accountId, int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return transactionRepository.findRecentTransactions(accountId, since);
    }
    
    public int countRecentTransactions(Long accountId, int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return transactionRepository.countRecentTransactions(accountId, since);
    }
    
    public BigDecimal getTotalTransactionAmount(Long accountId, String type, int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        BigDecimal total = transactionRepository.sumTransactionsByType(accountId, type, since);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public Transaction updateTransactionStatus(Long id, String newStatus) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        transaction.setStatus(newStatus);
        return transactionRepository.save(transaction);
    }
}