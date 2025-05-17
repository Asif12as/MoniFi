package com.example.financial.repository;

import com.example.financial.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
    
    Optional<Transaction> findByTransactionRef(String transactionRef);
    
    List<Transaction> findByStatus(String status);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.id = ?1 AND t.createdAt >= ?2")
    List<Transaction> findRecentTransactions(Long accountId, LocalDateTime since);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.account.id = ?1 AND t.createdAt >= ?2")
    int countRecentTransactions(Long accountId, LocalDateTime since);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account.id = ?1 AND t.transactionType = ?2 AND t.createdAt >= ?3")
    BigDecimal sumTransactionsByType(Long accountId, String type, LocalDateTime since);
}