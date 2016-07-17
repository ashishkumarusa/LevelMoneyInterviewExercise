package com.example.coding.ashishkumar.levelmoneyinterviewexercise;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.GetAllTransactionResponse;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.model.TransactionToBeShown;
import com.example.coding.ashishkumar.levelmoneyinterviewexercise.utilities.Utilities;

import java.util.HashMap;

public class UserAllTransactionsActivity extends AppCompatActivity implements WorkerFragment.TaskCallbacks {

    private static final String TAG_DATA_FETCHING_TASK_FRAGMENT = "data_fetching_task_fragment";
    private WorkerFragment mWorkerFragment;

    private ListView mListView = null;
    private ProgressDialog mProgressDialog = null;
    private HashMap<String,TransactionToBeShown> transactionsMapToBeShown = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all_transactions);
        mListView = (ListView) findViewById(R.id.transactions_listView);
        mProgressDialog = ProgressDialog.show(this, "Loading", "", true, false, null);

        FragmentManager fm = getSupportFragmentManager();
        mWorkerFragment = (WorkerFragment) fm.findFragmentByTag(TAG_DATA_FETCHING_TASK_FRAGMENT);

        if (mWorkerFragment == null) {
            mWorkerFragment = new WorkerFragment();
            fm.beginTransaction().add(mWorkerFragment, TAG_DATA_FETCHING_TASK_FRAGMENT).commit();
        }

        //call the GetAllTransactions API endpoint
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute(String response) {
        Log.v("Ashish", "onPostExecute()");
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        GetAllTransactionResponse responseObj = (GetAllTransactionResponse) Utilities.parseResponse(
                response, GetAllTransactionResponse.class.getSimpleName());

        transactionsMapToBeShown = Utilities.getTransactionsToBeShown(responseObj);
        print(transactionsMapToBeShown);


    }

    private void print(HashMap<String,TransactionToBeShown> transactionsToBeShown) {
        for (String key : transactionsToBeShown.keySet()) {
           Log.v("Ashish","key: " + key + " income: " + transactionsToBeShown.get(key).getIncome());
            Log.v("Ashish","key: " + key + " spendings: " + transactionsToBeShown.get(key).getSpent());
        }

    }

}
