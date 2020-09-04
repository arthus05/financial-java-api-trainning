package com.example.financialjavaapi.enumeration;

// Enum that classifies the transaction's payment type.
public enum TransactionTypeEnum {

    CARD("CARD"), MONEY("MONEY");

    private final String value;

    TransactionTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
