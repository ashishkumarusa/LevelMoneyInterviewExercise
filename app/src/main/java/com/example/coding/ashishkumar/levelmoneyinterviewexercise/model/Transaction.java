package com.example.coding.ashishkumar.levelmoneyinterviewexercise.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashishkumar on 7/15/16.
 */
public class Transaction {
    @SerializedName("transaction-id")
    private String transactionId;

    @SerializedName("account-id")
    private String accountId;

    @SerializedName("raw-merchant")
    private String rawMerchant;

    private String merchant;

    @SerializedName("is-pending")
    private boolean isPending;

    @SerializedName("transaction-time")
    private String transactionTime;

    private long amount;

    @SerializedName("previous-transaction-id")
    private String prevTransId;

    private String categorization;


    @SerializedName("memo-only-for-testing")
    private String memo;

    @SerializedName("payee-name-only-for-testing")
    private String payeeName;

    @SerializedName("clear-date")
    private String clearDate;

    private int year;

    private int month;

    private String printedYearMonth;

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getRawMerchant() {
        return rawMerchant;
    }

    public String getMerchant() {
        return merchant;
    }

    public boolean isPending() {
        return isPending;
    }

    public String getTransactionTime() {

        return format(transactionTime);
    }

    private String format(String transactionTime) {
        String[] entries = transactionTime.split("-");
        year = Integer.parseInt(entries[0]);
        month = Integer.parseInt(entries[1]);
        printedYearMonth = String.valueOf(year) + "-" + month;
        return printedYearMonth;

    }

    public long getAmount() {
        return amount;
    }

    public String getPrevTransId() {
        return prevTransId;
    }

    public String getCategorization() {
        return categorization;
    }

    public String getMemo() {
        return memo;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public String getClearDate() {
        return clearDate;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getPrintedYearMonth() {
        return printedYearMonth;
    }
}
