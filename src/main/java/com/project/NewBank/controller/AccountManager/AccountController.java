package com.project.NewBank.controller.AccountManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.NewBank.Security.Response.AccountResponse;
import com.project.NewBank.Security.Response.TransactionResponse;
import com.project.NewBank.Security.request.AccountsManipulation.AccountCreationRequest;
import com.project.NewBank.Security.request.AccountsManipulation.TransferRequest;
import com.project.NewBank.Service.Accounting.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts(Authentication authentication) {
        String username = authentication.getName();
        List<AccountResponse> accounts = accountService.getUserAccounts(username);
        
        return ResponseEntity.ok(accounts);
    }
    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(AccountCreationRequest req, Authentication authentication) {
        String username = authentication.getName();
        AccountResponse accounts = accountService.createAccount(req, username);
        
        return ResponseEntity.ok(accounts);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(){
        
        return ResponseEntity.ok().build();
    }
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(){
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(
            accountService.transfer(request, authentication.getName())
        );
    }

    @PostMapping("/transactions/{accountNumber}")
    public ResponseEntity<TransactionResponse> getTransactionByAccount(){
        return ResponseEntity.ok().build();
    }
}
