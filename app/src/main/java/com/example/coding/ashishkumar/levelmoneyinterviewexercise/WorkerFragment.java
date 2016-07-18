package com.example.coding.ashishkumar.levelmoneyinterviewexercise;

/**
 * Created by ashishkumar on 7/15/16.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WorkerFragment extends Fragment {


    interface TaskCallbacks {
        void onPostExecute(String response);
    }

    private TaskCallbacks mCallbacks;

    public WorkerFragment() {
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment across configuration changes.
        setRetainInstance(true);

        // Create and execute the background task.
        DataFetchingTask mTask = new DataFetchingTask();
        mTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * A data fetching task that performs background work and
     * proxies progress updates and results back to the Activity.
     * <p/>
     * Note that we need to check if the callbacks are null in each
     * method in case they are invoked after the Activity's and
     * Fragment's onDestroy() method have been called.
     */
    private class DataFetchingTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... ignore) {
            return callAPIEndpoint();
        }
        @Override
        protected void onPostExecute(String response) {
            if (mCallbacks != null) {
                mCallbacks.onPostExecute(response);
            }
        }

        private String callAPIEndpoint() {
            String response = "";
            JSONObject inner = new JSONObject();
            JSONObject outer = new JSONObject();
            try {
                inner.put("uid", APICallsConstants.USER_ID);
                inner.put("token", APICallsConstants.AUTH_TOKEN);
                inner.put("api-token", APICallsConstants.API_TOKEN);
                inner.put("json-strict-mode", true);
                inner.put("json-verbose-response", false);

                outer.put("args", inner);

                try {
                    response = HttpClientUtility.getTransactionsResponse(APICallsConstants.GET_ALL_TRANSACTIONS_API_URL, outer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

}
