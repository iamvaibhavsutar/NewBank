package com.project.NewBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.NewBank.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Object> {
    
}
