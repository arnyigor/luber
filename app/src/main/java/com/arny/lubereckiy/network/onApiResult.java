package com.arny.lubereckiy.network;

import com.android.volley.VolleyError;

public interface onApiResult {
    void parseResultApi(String method, String result);
    void parseResultApi(String method, VolleyError error);
}
