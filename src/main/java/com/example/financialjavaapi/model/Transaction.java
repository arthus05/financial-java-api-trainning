package com.example.financialjavaapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.financialjavaapi.enumeration.TransactionTypeEnum;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Transaction {

    private Long id;
    private String nsu;
    private String autorizationNumber;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private TransactionTypeEnum type;

    public Transaction(TransactionTypeEnum type){
        this.type = type;
    }

}
