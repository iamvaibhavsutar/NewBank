package com.project.NewBank.Service.Accounting;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.NewBank.Security.Response.AccountResponse;
import com.project.NewBank.Security.Response.TransactionResponse;
import com.project.NewBank.Security.request.AccountsManipulation.AccountCreationRequest;
import com.project.NewBank.Security.request.AccountsManipulation.WithdrawRequest;
import com.project.NewBank.model.Account;
import com.project.NewBank.model.Transaction;
import com.project.NewBank.model.User;
import com.project.NewBank.repository.AccountRepository;
import com.project.NewBank.repository.UserRepository;
import com.project.NewBank.repository.TransactionRepository;
import com.project.NewBank.model.Enum.AccountStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

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
                .user(user)
                .currency(request.getCurrency())
                .build();
        account.setBalance(request.getInitialDeposit());
        Account savedAccount = accountRepository.save(account);
        log.info("Account created successfully for user: {}", username);

        return mapToAccountResponse(savedAccount);
    }

/////////////////////////////////////////////////
    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = "ACC" + System.currentTimeMillis() + UUID.randomUUID().toString();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        
        return accountNumber;
    }
/////////////////////////////////////////////////
    private AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .build();
    }
///////////////////////////////////////////////
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByUserName(String username) {
        // to get account by user name
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        List<Account> accounts = accountRepository.findByUser(user);
        if (accounts == null || accounts.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        return accounts.stream().map(this::mapToAccountResponse).toList();
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(String accountNumber, String username) {
        // to get account by account number
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null || !account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Account not found");
        }
        return mapToAccountResponse(account);
    }














    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request,String username) {
        // to withdraw amount from account
        Account account = findAndValidateAccount(request.getAccountNumber(), username);
        
        validateWithdrawal(request.getAmount(), account.getBalance());
        
        BigDecimal balanceBefore = account.getBalance();

        Transaction transaction = createTransaction(
            account,                                
            null,                                   
            request.getAmount(),
            Transaction.TransactionType.WITHDRAWAL
        );
        transaction.setBalanceBefore(balanceBefore);
        transaction.setStatus(Transaction.TransactionStatus.PROCESSING);

        try{
            account.setBalance(account.getBalance().subtract(request.getAmount()));
            
            accountRepository.save(account);
            
            // 7. Complete transaction
            transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
            transaction.setBalanceAfter(account.getBalance());
            Transaction savedTransaction = transactionRepository.save(transaction);

            return mapToTransactionResponse(savedTransaction);
        }catch(Exception e){
            transaction.setStatus(Transaction.TransactionStatus.FAILED);
            transaction.setFailureReason(e.getMessage());
            transactionRepository.save(transaction); 
            
            throw new RuntimeException("Withdrawal failed: " + e.getMessage(), e);
        }

    }
    
    
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void validateWithdrawal( BigDecimal amount, BigDecimal balance) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(
                String.format("Insufficient funds. Balance:%s Requested:%s",balance,amount)
            );
        }
    }
















    public void deposit(Long accountId, Double amount) {
        // to deposit amount to account
    }














    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        // to transfer amount between accounts
    }















    private Account findAndValidateAccount(String accountNumber, String username) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if(account == null||!account.getUser().getUsername().equals(username)){
            throw new UsernameNotFoundException("Account not found: ");
        }
        
        // Verify account is active
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active: " + account.getStatus());
        }
        
        return account;
    }

    private Transaction createTransaction(
            Account fromAccount,
            Account toAccount,
            BigDecimal amount,
            Transaction.TransactionType type) {
        
        return Transaction.builder()
                .transactionId(generateTransactionId())
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .type(type)
                .status(Transaction.TransactionStatus.PENDING)
                .referenceNumber(UUID.randomUUID().toString())
                .build();
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getType())
                .transactionAmount(transaction.getAmount())
                .status(transaction.getStatus())
                .description(transaction.getDescription())
                .fromAccountNumber(transaction.getFromAccount() != null ? 
                        transaction.getFromAccount() : null)
                .toAccountNumber(transaction.getToAccount() != null ? 
                        transaction.getToAccount() : null)
                        .balanceBefore(transaction.getBalanceBefore())
                .balanceAfter(transaction.getBalanceAfter())
                .transactionDate(transaction.getTransactionDate())
                .referenceNumber(transaction.getReferenceNumber())
                .build();
    }
}
