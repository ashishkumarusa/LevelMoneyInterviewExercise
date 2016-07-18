package com.example.coding.ashishkumar.levelmoneyinterviewexercise.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashishkumar on 7/15/16.
 */
public class Transaction {
    private String merchant;

    @SerializedName("transaction-time")
    private String transactionTime;

    private long amount;

    public String getMerchant() {
        return merchant;
    }

    public String getTransactionTime() {

        return format(transactionTime);
    }

    private String format(String transactionTime) {
        String[] entries = transactionTime.split("-");
        int year = Integer.parseInt(entries[0]);
        int month = Integer.parseInt(entries[1]);
        String printedYearMonth = String.valueOf(year) + "-" + month;
        return printedYearMonth;

    }

    public long getAmount() {
        return amount;
    }
}
