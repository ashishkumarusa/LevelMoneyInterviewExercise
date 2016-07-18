package com.example.coding.ashishkumar.levelmoneyinterviewexercise;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.DisplayTransaction;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.GetAllTransactionResponse;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class UserAllTransactionsActivity extends AppCompatActivity implements WorkerFragment.TaskCallbacks {

    private static final String TAG_DATA_FETCHING_TASK_FRAGMENT = "data_fetching_task_fragment";
    private static final String TAG = "UserAllTransactionsActi";

    private ListView mListView = null;
    private ProgressDialog mProgressDialog = null;
    String jsonResponseString;
    boolean ignoreDonuts = false;
    private ArrayList<String> formattedTransactionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all_transactions);
        mListView = (ListView) findViewById(R.id.list_view);
        Switch filterSwitch = (Switch) findViewById(R.id.filter_switch);

        filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ignoreDonuts = isChecked;
                displayTransactions();

            }
        });


        if (savedInstanceState != null && savedInstanceState.getString("result") != null) {
            mListView.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, formattedTransactionsList));
            return;
        }

        mProgressDialog = ProgressDialog.show(this, "Loading", "", true, true, null);

        FragmentManager fm = getSupportFragmentManager();
        WorkerFragment mWorkerFragment = (WorkerFragment) fm.findFragmentByTag(TAG_DATA_FETCHING_TASK_FRAGMENT);

        if (mWorkerFragment == null) {
            mWorkerFragment = new WorkerFragment();
            fm.beginTransaction().add(mWorkerFragment, TAG_DATA_FETCHING_TASK_FRAGMENT).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("result", formattedTransactionsList);
    }

    @Override
    public void onPostExecute(String response) {
        this.jsonResponseString = response;

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        displayTransactions();
    }

    private void displayTransactions() {
        Object object = Utilities.parseResponse(
                jsonResponseString, GetAllTransactionResponse.class);
        GetAllTransactionResponse responseObj;
        if (object instanceof GetAllTransactionResponse) {
            responseObj = (GetAllTransactionResponse) Utilities.parseResponse(
                    jsonResponseString, GetAllTransactionResponse.class);
        } else {
            Toast.makeText(this, "OOPS !class cast exception occurred.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "class cast exception occurred.");
            return;
        }
        if (responseObj.getError() != null && !responseObj.getError().equalsIgnoreCase("no-error")) {
            Toast.makeText(this, responseObj.getError(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "error ==" + responseObj.getError());
            return;
        }

        Map<String, DisplayTransaction> transactionsToBeShownMap = Utilities.getTransactionsToDisplay(responseObj, this.ignoreDonuts);
        if (transactionsToBeShownMap != null) {
            getFormattedTransactions(transactionsToBeShownMap);
            this.formattedTransactionsList = getFormattedTransactions(transactionsToBeShownMap);
            mListView.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, formattedTransactionsList));

        } else {
            Toast.makeText(this, "OOPS ! Seems some problem in loading server data", Toast.LENGTH_LONG).show();
            Log.i(TAG, "transactionsToBeShownMap is null");
        }
    }

    private ArrayList<String> getFormattedTransactions(Map<String, DisplayTransaction> transactionsToBeShownMap) {
        ArrayList<String> transactionsList = new ArrayList<>();

        for (Map.Entry<String, DisplayTransaction> entry : transactionsToBeShownMap.entrySet()) {
            JSONObject outerJson = new JSONObject();
            JSONObject innerJson = new JSONObject();
            try {
                innerJson.put("spent", formatCurrency(entry.getValue().getSpent(), Locale.US));
                innerJson.put("income", formatCurrency(entry.getValue().getIncome(), Locale.US));
                outerJson.put(entry.getKey(), innerJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            transactionsList.add(outerJson.toString());
        }

        return transactionsList;
    }

    private String formatCurrency(long spent, Locale currentLocale) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
        return currencyFormatter.format(spent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
    }
}
