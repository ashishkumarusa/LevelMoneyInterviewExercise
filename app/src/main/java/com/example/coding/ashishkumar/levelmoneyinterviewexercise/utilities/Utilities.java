package com.example.coding.ashishkumar.levelmoneyinterviewexercise.utilities;

import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.DisplayTransaction;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.GetAllTransactionResponse;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.Transaction;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ashishkumar on 7/16/16.
 */
public class Utilities {
    public static Object parseResponse(String response, Class responseClass) {
        Gson gson = new Gson();
        return gson.fromJson(response, responseClass);
    }

    public static Map<String, DisplayTransaction> getTransactionsToDisplay(GetAllTransactionResponse responseObj,boolean ignoreDonuts) {

        List<Transaction> allTransactionsList;

        if (responseObj != null && responseObj.getTransactions() != null && responseObj.getTransactions().length > 0) {
            allTransactionsList = Arrays.asList(responseObj.getTransactions());

            Map<String, DisplayTransaction> displayTransactionsMap = new LinkedHashMap<>();

            long totalSpent = 0, totalIncome = 0;
            long totalSpentTransactionsCount = 0, totalIncomeTransactionsCount = 0;

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

                    totalIncome += transaction.getAmount();
                    totalIncomeTransactionsCount++;

                } else {
                    long spent = displayTransaction.getSpent();
                    long amount = transaction.getAmount();
                    if (!(isDonutsSpending(transaction) && ignoreDonuts)) {
                        displayTransaction.setSpent(spent + Math.abs(amount));

                        totalSpent += Math.abs(transaction.getAmount());
                        totalSpentTransactionsCount++;
                    }
                }

                displayTransactionsMap.put(transaction.getTransactionTime(), displayTransaction);
            }

            displayTransactionsMap.put("average", new DisplayTransaction
                    (totalSpent / totalSpentTransactionsCount, totalIncome / totalIncomeTransactionsCount));
            return displayTransactionsMap;
        }
        return null;
    }

    private static boolean isDonutsSpending(Transaction transaction) {
        return transaction != null && (transaction.getMerchant().equalsIgnoreCase("Krispy Kreme Donuts")
                || transaction.getMerchant().equalsIgnoreCase("DUNKIN #336784"));
    }
}
