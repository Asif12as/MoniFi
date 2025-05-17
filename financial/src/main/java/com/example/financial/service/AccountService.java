package com.example.financial.service;

import com.example.financial.entity.Account;
import com.example.financial.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
    
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }
    
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    
    public List<Account> findByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }
    
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }
    
    public Account updateBalance(Long accountId, BigDecimal newBalance) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }
    
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}