package com.example.financialjavaapi.factory.impl;

import com.example.financialjavaapi.enumeration.TransactionTypeEnum;
import com.example.financialjavaapi.factory.TransactionFactory;
import com.example.financialjavaapi.model.Transaction;

public class TransactionFactoryImpl implements TransactionFactory {
    @Override
    public Transaction createTransaction(String type) {
        if(TransactionTypeEnum.MONEY.getValue().equals(type)) {
            return new Transaction(TransactionTypeEnum.MONEY);
        }
        return new Transaction((TransactionTypeEnum.CARD));
    }
}
