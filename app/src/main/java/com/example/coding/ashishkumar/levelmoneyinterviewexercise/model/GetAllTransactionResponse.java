package com.example.coding.ashishkumar.levelmoneyinterviewexercise.model;

/**
 * Created by ashishkumar on 7/15/16.
 */
public class GetAllTransactionResponse implements WebResposneInterface {
    private String error;
    private Transaction[] transactions;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
}
