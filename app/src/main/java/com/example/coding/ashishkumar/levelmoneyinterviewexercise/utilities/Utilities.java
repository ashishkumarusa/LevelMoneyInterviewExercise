package com.example.coding.ashishkumar.levelmoneyinterviewexercise.utilities;

import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.GetAllTransactionResponse;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.Transaction;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.TransactionToBeShown;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.WebResposneInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by ashishkumar on 7/16/16.
 */
public class Utilities {
    public static Object parseResponse(String response, WebResposneInterface interface) {

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

    public static void formatData(GetAllTransactionResponse responseObj) {

    }

    public static ArrayList<TransactionToBeShown> getTransactionsToBeShown(GetAllTransactionResponse responseObj) {

        ArrayList<Transaction> transactionArrayList = (ArrayList<Transaction>) Arrays.asList(responseObj.getTransactions());

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

        createAndReturnTranscToBeShown(map);
       return null;
    }

    private static void createAndReturnTranscToBeShown(HashMap<String, ArrayList<Transaction>> map) {
        for (Map.Entry<String, ArrayList<Transaction>> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            String key = entry.getKey();
            ArrayList<Transaction> transactions = entry.getValue();
            long spending =0,income = 0;

            ListIterator<Transaction> listIterator =transactions.listIterator();
            while (listIterator.hasNext()) {
                Transaction transaction = listIterator.next();
                if (transaction.getAmount() >= 0) {
                    income += transaction.getAmount();
                }
                else {
                    spending += Math.abs(transaction.getAmount())
                }
            }

        }

    }

}
