package com.example.financial.controller;

import com.example.financial.entity.Transaction;
import com.example.financial.service.TransactionMonitoringService;
import com.example.financial.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private TransactionMonitoringService monitoringService;
    
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        // Create and save the transaction
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        
        // Trigger monitoring for the new transaction
        monitoringService.monitorTransaction(savedTransaction);
        
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/reference/{transactionRef}")
    public ResponseEntity<Transaction> getTransactionByReference(@PathVariable String transactionRef) {
        return transactionService.findByTransactionRef(transactionRef)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.findByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/recent/{accountId}/{hours}")
    public ResponseEntity<List<Transaction>> getRecentTransactions(
            @PathVariable Long accountId, 
            @PathVariable int hours) {
        List<Transaction> transactions = transactionService.getRecentTransactions(accountId, hours);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @PathVariable Long id, 
            @RequestParam String status) {
        try {
            Transaction updatedTransaction = transactionService.updateTransactionStatus(id, status);
            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}