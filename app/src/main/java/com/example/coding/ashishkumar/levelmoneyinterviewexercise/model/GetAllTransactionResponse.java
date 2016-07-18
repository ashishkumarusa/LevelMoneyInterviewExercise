package com.example.coding.ashishkumar.levelmoneyinterviewexercise.model;

/**
 * Created by ashishkumar on 7/15/16.
 */
public class GetAllTransactionResponse {
    private String error;
    private Transaction[] transactions;

    public String getError() {
        return error;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }
}
