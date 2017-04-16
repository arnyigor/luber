package com.arny.lubereckiy.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.Floor;
import com.arny.lubereckiy.models.Korpus;
import com.arny.lubereckiy.models.Section;
import com.arny.lubereckiy.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class API {
    private static final String API_BASE_URL = "https://pik.ru/luberecky/";
    private static final String API_FLAT_BASE_URL = "https://api.pik.ru/v1/flat?flat_id=";
    private static final String API_URL_GEN_PLAN = "datapages?data=GenPlan";
    private static final String API_URL_SINGLE_PAGE = "singlepage?data=ChessPlan&format=json&domain=pik.ru&id=";
    private static final String REQUEST_TAG = "VolleyAPI";
    public static final String GEN_PLAN = "GEN_PLAN";
    public static final String SINGLE_PAGE = "SINGLE_PAGE";
    private static RequestQueue mQueue;

    private static void request(Context context, String urlParams, final String method, final onApiResult onApiResult){
        mQueue = VolleyRequestQueue.getInstance(context).getRequestQueue();
        String URL = API_BASE_URL + urlParams;
        Log.i(API.class.getSimpleName(), "request: URL = " + URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i(API.class.getSimpleName(), "onResponse: " + response);
                onApiResult.parseResultApi(method,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.i(API.class.getSimpleName(), "onErrorResponse: " + volleyError);
                onApiResult.parseResultApi(method,volleyError);
            }
        });
        stringRequest.setTag(REQUEST_TAG);
        mQueue.add(stringRequest);
    }

    public static void requestGenPlan(Context context,onApiResult onApiResult){
        request(context,API_URL_GEN_PLAN, GEN_PLAN, onApiResult);
    }

    public static void requestSinglePage(Context context,String korpusId,onApiResult onApiResult){
        request(context,API_URL_SINGLE_PAGE + korpusId, SINGLE_PAGE, onApiResult);
    }

    public static void stopAllRequests(){
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    public static JSONArray parseAPIKorpuses(HashMap<String, Object> data) throws JSONException {
        JSONArray genPlanArray = new JSONArray(data.get("result").toString());
        JSONObject genPlanObj = new JSONObject(genPlanArray.get(0).toString()).getJSONObject("data");
        return new JSONArray(genPlanObj.get("sets_of_pathes").toString());
    }

    public static Korpus getAPIKorpus(JSONObject korpusObject) throws JSONException {
        Korpus korpus = new Korpus();
        korpus.setTitle(korpusObject.getString("title"));
        korpus.setKorpusID(korpusObject.getString("id"));
        korpus.setFree(Integer.parseInt(checkNullValue(korpusObject, "free", "0")));
        korpus.setFinishing(korpusObject.getBoolean("finishing"));
        korpus.setBusy(Integer.parseInt(checkNullValue(korpusObject, "busy", "0")));
        korpus.setMinprice_1(Integer.parseInt(Utility.trimInside(checkHasValue(korpusObject,"minprice_1","-1"))));
        korpus.setMinprice_2(Integer.parseInt(Utility.trimInside(checkHasValue(korpusObject,"minprice_2","-1"))));
        korpus.setDateOfMovingIn(korpusObject.getString("dateOfMovingIn"));
        return korpus;
    }

    private static String checkNullValue(JSONObject korpusObject, String apiParsed, String defaultVal) throws JSONException {
        String result;
        if (Utility.empty(korpusObject.getString(apiParsed))) {
            result = defaultVal;
        }else {
            result = korpusObject.getString(apiParsed);
        }
        return result;
    }
    private static String checkHasValue(JSONObject korpusObject, String apiParsed, String defaultVal) throws JSONException {
        String result;
        if (korpusObject.has(apiParsed)) {
            result = korpusObject.getString(apiParsed);
        }else{
            result = defaultVal;
        }
        return result;
    }

    public static JSONArray getApiSections(String result) throws JSONException {
        JSONObject korpus = new JSONObject(result);
        return new JSONArray(korpus.get("sections").toString());
    }

    public static Section parceAPISection(JSONObject sectionJsonObject, Korpus korpus) throws JSONException {
        Section section = new Section();
        section.setKorpusID(korpus.getKorpusID());
        section.setName(sectionJsonObject.getString("sectionName"));
        section.setQuantity(sectionJsonObject.getInt("quantity"));
        section.setFirstFloorNumber(Integer.parseInt(checkNullValue(sectionJsonObject,"firstFloorNumber","0")));
        section.setFlatsDirection(sectionJsonObject.getInt("flatsDirection"));
        return section;
    }

    public static Floor parseAPIFloor(Section section, int j, JSONObject floorObject,Korpus korpus) throws JSONException {
        Floor floor = new Floor();
        floor.setKorpusID(korpus.getKorpusID());
        floor.setSectionName(section.getName());
        floor.setFloorNumber(j);
        floor.setFloorPlan(floorObject.getString("plan"));
        return floor;
    }

    public static Flat parseAPIFlat(Section section, Floor floor, JSONObject flatJsonObject, Korpus korpus) throws JSONException {
        Flat flat = new Flat();
        flat.setKorpusID(korpus.getKorpusID());
        flat.setSectionName(section.getName());
        flat.setFloorNumber(floor.getFloorNumber());
        flat.setFlatID(flatJsonObject.getString("id"));
        flat.setRoomQuantity(flatJsonObject.getInt("roomQuantity"));
        JSONObject statusObject = new JSONObject(flatJsonObject.getString("status"));
        flat.setStatus(statusObject.getString("title"));
        flat.setDiscount(flatJsonObject.getBoolean("discount"));
        flat.setWholeAreaBti(flatJsonObject.getDouble("wholeAreaBti"));
        flat.setWholePrice(flatJsonObject.getInt("wholePrice"));
        flat.setOfficePrice(flatJsonObject.getInt("wholePrice"));
        return flat;
    }

    public static int getMaxItems(JSONObject items, int min) {
        int max = 0;
        while (true) {
            if (max <= min) {
                max++;
            }else if (max>min && items.has(String.valueOf(max))) {
                max++;
            }else{
                break;
            }
        }
        return max - 1;
    }

    public static int getMinItems(JSONObject items) {
        int min = 0;
        while (true) {
            if (items.has(String.valueOf(min))) {
                break;
            }else{
                min++;
            }
        }
        return min;
    }

}
