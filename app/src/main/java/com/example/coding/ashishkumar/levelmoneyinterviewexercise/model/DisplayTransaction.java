package com.example.coding.ashishkumar.levelmoneyinterviewexercise.model;

/**
 * Created by ashishkumar on 7/16/16.
 */
public class DisplayTransaction {
    private long spent;
    private long income;

    public DisplayTransaction(long spent, long income) {
        this.spent = spent;
        this.income = income;
    }

    public long getSpent() {
        return spent;
    }

    public void setSpent(long spent) {
        this.spent = spent;
    }

    public long getIncome() {
        return income;
    }

    public void setIncome(long income) {
        this.income = income;
    }
}
