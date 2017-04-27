package com.arny.lubereckiy.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class ApiSendService extends IntentService {
    protected static RequestQueue requestQueue;
    public static RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }
    public ApiSendService() {
        super("ApiSendService");
    }
    /**
     * Запрос по API
     * @param apiMethod
     * @param context
     * @param url
     * @param params
     * @param successCallback
     * @param errorCallback
     */
    public static void hostRequest(int apiMethod, final Context context, String url, final JSONObject params, final onApiResultCallback successCallback, final onApiResultError errorCallback) {
        Log.i("api", " >> Api Request: " + url + " with params: " + params.toString() + " method: " + apiMethod);
        HttpAsyncRequest httpRequest = new HttpAsyncRequest(apiMethod,url, params, new onApiResultCallback() {
            @Override
            public void callback(JSONObject response) {
//                Log.i(ApiSendService.class.getSimpleName(), "callback: response = " + response.toString());
                    successCallback.callback(response);
            }
        }, new onApiResultError() {
            @Override
            public void callback(String error, int position) {
                Log.e("api", " << Api Response Error: " + error);
                errorCallback.callback(error, 0);
            }
        }, context);
        httpRequest.execute((Void) null);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
    }
    public static class HttpAsyncRequest extends AsyncTask<Void, Void, Boolean> {
        onApiResultCallback onSuccess;
        onApiResultError onError;
        String url;
        JSONObject params;
        Context context;
        int apiMethod;
        public HttpAsyncRequest(String requestUrl, JSONObject requestParams, final onApiResultCallback successCallback, final onApiResultError errorCallback, Context context) {
            onSuccess = successCallback;
            onError = errorCallback;
            params = requestParams;
            url = requestUrl;
            this.apiMethod = Request.Method.POST;
            this.context = context;
        }
        public HttpAsyncRequest(int apiMethod,String requestUrl, JSONObject requestParams, final onApiResultCallback successCallback, final onApiResultError errorCallback, Context context) {
            onSuccess = successCallback;
            onError = errorCallback;
            params = requestParams;
            url = requestUrl;
            this.apiMethod = apiMethod;
            this.context = context;
        }
        @Override
        protected Boolean doInBackground(Void... p) {
            RequestQueue queue = getRequestQueue(context);
            JsonObjectRequest stringRequest = new JsonObjectRequest(apiMethod, url, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            onSuccess.callback(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("http", "Error processing HTTP request", error);
                    onError.callback(error.getMessage(), 0);
                }
            });
            stringRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4;
                }
                @Override
                public int getCurrentRetryCount() {
                    return DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
                }
                @Override
                public void retry(VolleyError error) throws VolleyError {
                    Log.e("http", "HTTP Retry Error:", error);
                    onError.callback(error.getMessage(), 0);
                }
            });
            queue.add(stringRequest);
            return true;
        }
    }
}