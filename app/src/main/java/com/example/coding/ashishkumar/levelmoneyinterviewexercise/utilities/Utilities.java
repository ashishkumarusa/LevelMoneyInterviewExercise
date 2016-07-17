package com.example.coding.ashishkumar.levelmoneyinterviewexercise.utilities;

import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.GetAllTransactionResponse;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.Transaction;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.TransactionToBeShown;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by ashishkumar on 7/16/16.
 */
public class Utilities {
    public static Object parseResponse(String response,String className) {

        // create new class JsonParser.
        Gson gson = new Gson();
        switch (className) {
            case "GetAllTransactionResponse":
                return gson.fromJson(response, GetAllTransactionResponse.class);
            default:
                break;
        }

        return null;
    }

    public static HashMap<String,TransactionToBeShown> getTransactionsToBeShown(GetAllTransactionResponse responseObj) {

      List<Transaction> transactionArrayList = Arrays.asList(responseObj.getTransactions());

        ListIterator<Transaction> listIterator = transactionArrayList.listIterator();

        HashMap<String,ArrayList<Transaction>> map = new HashMap<>();

        while (listIterator.hasNext()) {
            Transaction transaction = listIterator.next();
            String key = transaction.getPrintedYearMonth();
            if (map.get(key) == null) {
                map.put(key,new ArrayList<Transaction>());
            }
            map.get(key).add(transaction);

        }

        return  createAndReturnTranscToBeShown(map);
    }

    private static HashMap<String,TransactionToBeShown> createAndReturnTranscToBeShown(HashMap<String, ArrayList<Transaction>> map) {
        HashMap<String,TransactionToBeShown> hasmap = new HashMap<>();

        for (Map.Entry<String, ArrayList<Transaction>> entry : map.entrySet()) {

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            String key = entry.getKey();
            ArrayList<Transaction> transactions = entry.getValue();
            long spending =0,income = 0;
            TransactionToBeShown transactionToBeShown = new TransactionToBeShown();

            ListIterator<Transaction> listIterator =transactions.listIterator();
            while (listIterator.hasNext()) {
                Transaction transaction = listIterator.next();
                if (transaction.getAmount() >= 0) {
                    income += transaction.getAmount();
                }
                else {
                    spending += Math.abs(transaction.getAmount());
                }
            }

            transactionToBeShown.setIncome(income);
            transactionToBeShown.setSpent(spending);
            if (hasmap.get(key)== null) {
                hasmap.put(key,transactionToBeShown);
            }
        }

        return hasmap;
    }


}
