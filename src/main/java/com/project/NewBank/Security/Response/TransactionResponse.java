package com.project.NewBank.Security.Response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private BigDecimal transactionAmount;
    private String transactionType;
    private BigDecimal balance;
}
