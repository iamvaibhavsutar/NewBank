package com.project.NewBank.Service.Accounting;

import java.math.BigDecimal;
import java.util.UUID;


import org.springframework.stereotype.Service;

import com.project.NewBank.Security.Response.AccountResponse;
import com.project.NewBank.Security.request.AccountsManipulation.AccountCreationRequest;
import com.project.NewBank.model.Account;
import com.project.NewBank.model.User;
import com.project.NewBank.repository.AccountRepository;
import com.project.NewBank.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountResponse createAccount(AccountCreationRequest request, String username) {
        // to create new account for user
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Create and save the account
        Account account = Account.builder()
                .accountType(request.getAccountTypeEnum().name())
                .accountNumber(generateAccountNumber())
                .balance(request.getInitialDeposit())
                .user(user)
                .currency(request.getCurrency())
                .build();
        accountRepository.save(account);

        return new AccountResponse();
    }
    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = "ACC" + System.currentTimeMillis() + UUID.randomUUID().toString();;
        } while (accountRepository.existsByAccountNumber(accountNumber));
        
        return accountNumber;
    }

    public AccountResponse getAccountByUserId(Long userId) {
        // to get account by user ID

        return new AccountResponse();
    }

    public void withdraw(Long accountId, Double amount) {
        // to withdraw amount from account
    }

    public void deposit(Long accountId, Double amount) {
        // to deposit amount to account
    }

    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        // to transfer amount between accounts
    }

    
}
