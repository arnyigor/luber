package com.arny.lubereckiy.service;

import android.util.Log;

import com.android.volley.VolleyError;
import com.arny.lubereckiy.db.DBProvider;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.Floor;
import com.arny.lubereckiy.models.Korpus;
import com.arny.lubereckiy.models.Section;
import com.arny.lubereckiy.network.API;
import com.arny.lubereckiy.network.onApiResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Operations extends AbstractIntentService {
    public static final int OPERATION_GET_GEN_PLAN = 101;
    public static final int OPERATION_PARSE_GEN_PLAN = 102;
    public static final int OPERATION_PARSE_KORPUSES = 103;
    public static final int OPERATION_PARSE_FLATS = 104;

    public Operations() {
        super();
    }
    @Override
    protected void runOperation(final OperationProvider provider, final OnOperationResult operationResult) {
        switch (provider.getId()) {
            case OPERATION_GET_GEN_PLAN:
                API.requestGenPlan(getApplicationContext(), new onApiResult() {
                    @Override
                    public void parseResultApi(String method, String result) {
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("result", result);
                        onStartOperation(getApplicationContext(),Operations.EXTRA_KEY_TYPE_ASYNC,OPERATION_PARSE_GEN_PLAN,data);
                    }
                    @Override
                    public void parseResultApi(String method, VolleyError error) {
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("method", method);
                        provider.setOperationData(data);
                        provider.setResult(error.toString());
                        operationResult.resultFail(provider);
                    }
                });
                break;
            case OPERATION_PARSE_GEN_PLAN:
                HashMap<String, Object> apiResult = provider.getOperationData();
                try {
                    JSONArray kopruses = API.parseAPIKorpuses(apiResult);
                    for (int i = 0; i < kopruses.length(); i++) {
                        JSONObject korpusObject = new JSONObject(kopruses.get(i).toString());
                        Korpus korpus = API.getAPIKorpus(korpusObject);
                        HashMap<String, Object> korpusData = new HashMap<>();
                        korpusData.put("korpus", korpusObject.toString());
                        onStartOperation(getApplicationContext(),Operations.EXTRA_KEY_TYPE_ASYNC,OPERATION_PARSE_KORPUSES,korpusData);
//                        DBProvider.addKorpus(getApplicationContext(), korpus);
                    }
                    operationResult.resultSuccess(provider);
                } catch (JSONException e) {
                    e.printStackTrace();
                    operationResult.resultFail(provider);
                }
                break;
            case OPERATION_PARSE_KORPUSES:
                HashMap<String, Object> apiKorpusesResult = provider.getOperationData();
                JSONObject korpusObject = null;
                try {
                    korpusObject = new JSONObject(apiKorpusesResult.get("korpus").toString());
                    final Korpus korpus = API.getAPIKorpus(korpusObject);
                    API.requestSinglePage(getApplicationContext(), korpus.getKorpusID(), new onApiResult() {
                        @Override
                        public void parseResultApi(String method, String result) {
                            try {
                               JSONArray sections =  API.getApiSections(result);
                                for (int i = 0; i < sections.length(); i++) {
                                    JSONObject sectionJsonObject = new JSONObject(String.valueOf(sections.get(i)));
                                    Section section = API.parceAPISection(sectionJsonObject, korpus);
//                                    DBProvider.addSection(getApplicationContext(), section);
                                    JSONObject floorsObject = new JSONObject(sectionJsonObject.getString("floors"));
                                    int min = API.getMinItems(floorsObject);
                                    int max = API.getMaxItems(floorsObject, min);
                                    for (int j = min; j < max; j++) {
                                        JSONObject floorObject = new JSONObject(String.valueOf(floorsObject.get(String.valueOf(j))));
                                        Floor floor = API.parseAPIFloor(section, j, floorObject,korpus);
//                                        DBProvider.addFloor(getApplicationContext(), floor);
                                        JSONArray flatsArray = new JSONArray(floorObject.getString("flats"));
                                        for (int k = 0; k < flatsArray.length(); k++) {
                                            JSONObject flatJsonObject = new JSONObject(String.valueOf(flatsArray.get(k)));
                                            Flat flat = API.parseAPIFlat(section, floor, flatJsonObject, korpus);
                                            Log.i(Operations.class.getSimpleName(), "parseResultApi: flat = " + flat.getKorpusID() + " " + flat.getSectionName() + " " + flat.getFloorNumber() + " " + flat.getStatus());
//                                            DBProvider.addFlat(getApplicationContext(), flat);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                operationResult.resultFail(provider);
                            }
                        }



                        @Override
                        public void parseResultApi(String method, VolleyError error) {
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("method", method);
                            provider.setOperationData(data);
                            provider.setResult(error.toString());
                            operationResult.resultFail(provider);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }




}