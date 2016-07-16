package com.example.coding.ashishkumar.levelmoneyinterviewexercise;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    private Button mLoadAllTransButton = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mLoadAllTransButton = (Button) findViewById(R.id.load_all_transactions_button);

        mContext = this;

       mLoadAllTransButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //check if internet connection is available

               if(!checkIfInternetConnectionAvailable()) {
                  Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
               }
               else {
                   // load user's all transactions and display the result in desired format to next activity.
                   // put required request parameters in bundle object and pass it when starting new activity.

                   Intent allTransactionsActivityIntent = new Intent(mContext,UserAllTransactionsActivity.class);
                   startActivity(allTransactionsActivityIntent);

               }
           }
       });
    }

    private boolean checkIfInternetConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
