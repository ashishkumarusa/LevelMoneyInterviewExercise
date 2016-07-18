package com.example.coding.ashishkumar.levelmoneyinterviewexercise.utilities;

import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.GetAllTransactionResponse;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.Transaction;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.DisplayTransaction;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ashishkumar on 7/16/16.
 */
public class Utilities {
    public static Object parseResponse(String response,Class responseClass) {
        Gson gson = new Gson();
        return gson.fromJson(response, responseClass);
    }

    public static Map<String,DisplayTransaction> getTransactionsToDisplay(GetAllTransactionResponse responseObj, boolean ignoreDonuts) {
        List<Transaction> allTransactionsList;
        allTransactionsList = Arrays.asList(responseObj.getTransactions());

        Map<String, DisplayTransaction> displayTransactionsMap = new HashMap<>();

        for (Transaction transaction : allTransactionsList) {
            DisplayTransaction displayTransaction;
            if (displayTransactionsMap.containsKey(transaction.getTransactionTime())) {
                displayTransaction = displayTransactionsMap.get(transaction.getTransactionTime());
            } else {
                displayTransaction = new DisplayTransaction(0, 0);
            }
            if (transaction.getAmount() > 0) {
                long income = displayTransaction.getIncome();
                long amount = transaction.getAmount();
                displayTransaction.setIncome(income + amount);

            } else {
                long spent = displayTransaction.getSpent();
                long amount = transaction.getAmount();
                if (!(isDonutsSpending(transaction) && ignoreDonuts)) {
                    displayTransaction.setSpent(spent + Math.abs(amount));
                }
            }

            displayTransactionsMap.put(transaction.getTransactionTime(), displayTransaction);
        }
        return displayTransactionsMap;
    }

    private static boolean isDonutsSpending(Transaction transaction) {
        // I am assuming if merchant fields contains either DONUTS OR DUNKIN it will be a donut spending.
        return transaction.getMerchant().toUpperCase().contains("DONUTS")
                || transaction.getMerchant().toUpperCase().contains("DUNKIN");
    }
}
