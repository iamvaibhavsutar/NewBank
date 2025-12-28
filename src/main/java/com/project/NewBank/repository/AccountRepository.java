package com.project.NewBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.NewBank.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);

    Account findByUserId(Long userId);

    Account findByAccountType(String accountType);

    Account findByBalance(Double balance);
    
    Account findByAccountTypeAndUserId(String accountType, Long userId);

    boolean existsByAccountNumber(String accountNumber);

}
