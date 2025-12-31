package com.project.NewBank.controller.AccountManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.NewBank.Security.Response.AccountResponse;
import com.project.NewBank.Security.Response.TransactionResponse;
import com.project.NewBank.Service.Accounting.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    @Autowired
    AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        
        
        return ResponseEntity.ok().build();
    }
    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount() {
        // Implementation to create a new account
        return ResponseEntity.ok().build();
    }
    @PostMapping("/accounts/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(){
        
        return ResponseEntity.ok().build();
    }
    @PostMapping("/accounts/deposit")
    public ResponseEntity<TransactionResponse> deposit(){
        return ResponseEntity.ok().build();
    }
    @PostMapping("/accounts/transfer")
    public ResponseEntity<TransactionResponse> transfer(){
        return ResponseEntity.ok().build();
    }
    @PostMapping("/accounts/transactions")
    public ResponseEntity<TransactionResponse> getTransactionByAccount(){
        return ResponseEntity.ok().build();
    }
}
