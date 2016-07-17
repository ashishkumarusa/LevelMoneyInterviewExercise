package com.example.coding.ashishkumar.levelmoneyinterviewexercise.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashishkumar on 7/15/16.
 */
public class Transaction {
    @SerializedName("transaction-id")
    private String transactionId ;

    @SerializedName("account-id")
    private String accountId ;

    @SerializedName("raw-merchant")
    private String rawMerchant ;

    private String merchant;

    @SerializedName("is-pending")
    private boolean isPending ;

    @SerializedName("transaction-time")
    private String transactionTime;

    private long amount;

    @SerializedName("previous-transaction-id")
    private String prevTransId ;

    private String categorization ;


    @SerializedName("memo-only-for-testing")
    private String memo ;

    @SerializedName("payee-name-only-for-testing")
    private String payeeName ;

    @SerializedName("clear-date")
    private String clearDate ;

    private int year;

    private int month;

    private String printedYearMonth;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRawMerchant() {
        return rawMerchant;
    }

    public void setRawMerchant(String rawMerchant) {
        this.rawMerchant = rawMerchant;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public String getTransactionTime() {

       return format(transactionTime);

    }

    public void setTransactionTime(String transactionTime) {
        Log.v("Ashish","setTransactionTime()");
        format(transactionTime);
        this.transactionTime = transactionTime;
    }

    private String format(String transactionTime) {
        String[] entries = transactionTime.split("-",2);
        year = Integer.parseInt(entries[0]);
        month = Integer.parseInt(entries[1]);
        printedYearMonth = new StringBuilder().append(year).append("-").append(month).toString();
        return printedYearMonth;

    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPrevTransId() {
        return prevTransId;
    }

    public void setPrevTransId(String prevTransId) {
        this.prevTransId = prevTransId;
    }

    public String getCategorization() {
        return categorization;
    }

    public void setCategorization(String categorization) {
        this.categorization = categorization;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getClearDate() {
        return clearDate;
    }

    public void setClearDate(String clearDate) {
        this.clearDate = clearDate;
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
