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

/**
 * This Fragment manages a single background task and retains
 * itself across configuration changes.
 */
public class WorkerFragment extends Fragment {

    /**
     * Callback interface through which the fragment will report the
     * task's progress and results back to the Activity.
     */
    interface TaskCallbacks {
        void onPreExecute();
        void onCancelled();
        void onPostExecute(String response);
    }

    private TaskCallbacks mCallbacks;
    private DataFetchingTask mTask;

    public WorkerFragment() {
    }

    /**
     * Hold a reference to the parent Activity so we can report the
     * task's current progress and results. The Android framework
     * will pass us a reference to the newly created Activity after
     * each configuration change.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks) activity;
    }

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        // Create and execute the background task.
        mTask = new DataFetchingTask();
        mTask.execute();
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    /**
     * A data fetching task that performs background work and
     * proxies progress updates and results back to the Activity.
     *
     * Note that we need to check if the callbacks are null in each
     * method in case they are invoked after the Activity's and
     * Fragment's onDestroy() method have been called.
     */
    private class DataFetchingTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            if (mCallbacks != null) {
                mCallbacks.onPreExecute();
            }
        }

        /**
         * Note that we do NOT call the callback object's methods
         * directly from the background thread, as this could result
         * in a race condition.
         */
        @Override
        protected String doInBackground(String... ignore) {
            makePostRequest();
          return parseResponse();
        }

        private String parseResponse() {
            String response= "";
            try {
                response = HttpClientUtility.readSingleLineRespone();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onCancelled() {
            if (mCallbacks != null) {
                mCallbacks.onCancelled();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (mCallbacks != null) {
                mCallbacks.onPostExecute(response);
            }
        }

        private void makePostRequest() {
            JSONObject inner = new JSONObject();
            JSONObject outer = new JSONObject();
            try {
                inner.put("uid",APICallsConstants.USER_ID);
                inner.put("token",APICallsConstants.AUTH_TOKEN);
                inner.put("api-token",APICallsConstants.API_TOKEN);
                inner.put("json-strict-mode",true);
                inner.put("json-verbose-response",true);

                outer.put("args",inner);

                try {
                    HttpClientUtility.makePostRequest(APICallsConstants.GET_ALL_TRANSACTIONS_API_URL,outer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
