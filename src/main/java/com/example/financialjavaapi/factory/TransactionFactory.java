package com.example.financialjavaapi.factory;

import com.example.financialjavaapi.model.Transaction;

// Interface that provides method for manipulate a transaction.
public interface TransactionFactory {
    Transaction createTransaction(String type);
}
