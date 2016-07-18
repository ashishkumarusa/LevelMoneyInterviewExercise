package com.example.coding.ashishkumar.levelmoneyinterviewexercise;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.GetAllTransactionResponse;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.DisplayTransaction;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class UserAllTransactionsActivity extends AppCompatActivity implements WorkerFragment.TaskCallbacks {

    private static final String TAG_DATA_FETCHING_TASK_FRAGMENT = "data_fetching_task_fragment";

    private TextView outputTv = null;
    private ProgressDialog mProgressDialog = null;
    String jsonResponseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all_transactions);
        outputTv = (TextView)findViewById(R.id.output_tv) ;
        Switch filterSwitch = (Switch) findViewById(R.id.filter_switch);

        filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               displayTransactions(isChecked);
            }
        });

        mProgressDialog = ProgressDialog.show(this, "Loading", "", true, false, null);

        FragmentManager fm = getSupportFragmentManager();
        WorkerFragment mWorkerFragment = (WorkerFragment) fm.findFragmentByTag(TAG_DATA_FETCHING_TASK_FRAGMENT);

        if (mWorkerFragment == null) {
            mWorkerFragment = new WorkerFragment();
            fm.beginTransaction().add(mWorkerFragment, TAG_DATA_FETCHING_TASK_FRAGMENT).commit();
        }
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(String response) {
        this.jsonResponseString = response;

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        displayTransactions(true);


    }

    private void displayTransactions(boolean ignoreDonuts) {
        GetAllTransactionResponse responseObj = (GetAllTransactionResponse) Utilities.parseResponse(
                jsonResponseString,GetAllTransactionResponse.class);

        Map<String, DisplayTransaction> transactionsToBeShownMap = Utilities.getTransactionsToDisplay(responseObj, ignoreDonuts);
        getFormattedTransactions(transactionsToBeShownMap);
        outputTv.setText(getFormattedTransactions(transactionsToBeShownMap));
    }

    private String getFormattedTransactions(Map<String, DisplayTransaction> transactionsToBeShownMap) {

        JSONArray jsonArray = new JSONArray();
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
            jsonArray.put(outerJson);
        }
        return jsonArray.toString();
    }

    private String formatCurrency(long spent, Locale currentLocale) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
        return currencyFormatter.format(spent);
    }
}
