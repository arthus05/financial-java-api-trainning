package com.example.financialjavaapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.stream.Collectors;

import com.example.financialjavaapi.enumeration.TransactionTypeEnum;
import com.example.financialjavaapi.factory.impl.TransactionFactoryImpl;
import com.example.financialjavaapi.model.Transaction;
import com.example.financialjavaapi.factory.TransactionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private TransactionFactory factory;
    private List<Transaction> transactions;

    // Method to create TransactionFactory
    public void createFactory() {
        if(factory == null) {
            factory = new TransactionFactoryImpl();
        }
    }

    // Method to create the transaction list
    public void createTransactionList() {
        if(transactions == null) {
            transactions = new ArrayList<>();
        }
    }

    // Method that check if JSON is valid
    public boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    // Method to parse the id field
    private long parseId(JSONObject transaction) {
        return Long.valueOf((int) transaction.get("id"));
    }

    // Method to parse the amount field
    private BigDecimal parseAmount(JSONObject transaction) {
        return new BigDecimal((String) transaction.get("amount"));
    }

    // Method to parse the transactionDate field
    private LocalDateTime parseTransactionDate(JSONObject transaction) {
        var transactionDate = (String) transaction.get("transactionDate");
        return ZonedDateTime.parse(transactionDate).toLocalDateTime();
    }

    // Method that check if the transaction is being created in the future
    public boolean isTransactionInFuture(Transaction transaction) {
        return transaction.getTransactionDate().isAfter(LocalDateTime.now());
    }

    // Method to fullfil the transaction object
    private void setTransactionValues(JSONObject jsonTransaction, Transaction transaction) {

        String autorizationNumber = (String) jsonTransaction.get("autorizationNumber");
        String nsu = (String) jsonTransaction.get("nsu");

        transaction.setAmount(jsonTransaction.get("amount") != null ? parseAmount(jsonTransaction) : transaction.getAmount());
        transaction.setTransactionDate(jsonTransaction.get("transactionDate") != null ?
                parseTransactionDate(jsonTransaction) : transaction.getTransactionDate());
        transaction.setAutorizationNumber(TransactionTypeEnum.CARD.getValue().equals(transaction.getType().getValue()) ? autorizationNumber : null);
        transaction.setNsu(nsu != null ? nsu : transaction.getNsu());
    }

    // Method to create a transaction
    public Transaction create(JSONObject jsonTransaction) {

        createFactory();

        Transaction transaction = factory.createTransaction((String) jsonTransaction.get("type"));
        transaction.setId(parseId(jsonTransaction));
        setTransactionValues(jsonTransaction, transaction);

        return transaction;
    }

    // Method to update a transaction
    public Transaction update(Transaction transaction, JSONObject jsonTransaction) {

        setTransactionValues(jsonTransaction, transaction);
        return transaction;
    }

    // Method that add a transaction
    public void add(Transaction transaction) {
        createTransactionList();
        transactions.add(transaction);
    }

    // Method that get all transactions
    public List<Transaction> find() {
        createTransactionList();
        return transactions;
    }

    // Method that get a transaction by id
    public Transaction findById(long id) {
        return transactions.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
    }

    // Method that deletes the transactions created
    public void delete() {
        transactions.clear();
    }

    // Method to clean objects
    public void clearObjects() {
        transactions = null;
        factory = null;
    }
}

