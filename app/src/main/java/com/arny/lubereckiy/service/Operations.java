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

import com.arny.lubereckiy.utils.Config;
import com.arny.lubereckiy.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Operations extends AbstractIntentService {
	public static final int OPERATION_GET_GEN_PLAN = 101;
	private static final String TAG = Operations.class.getSimpleName();

	public Operations() {
		super();
	}

	@Override
	protected void runOperation(final OperationProvider provider, final OnOperationResult operationResult) {
		switch (provider.getId()) {
			case OPERATION_GET_GEN_PLAN:
				getKorpuses(provider, operationResult);
				break;
		}
	}

	private void getKorpuses(final OperationProvider provider, final OnOperationResult operationResult) {
		API.requestGenPlan(getApplicationContext(), new onApiResult() {
			@Override
			public void parseResultApi(String method, String result) {
				JSONArray genPlanArray = null;
				try {
					genPlanArray = new JSONArray(result);
					JSONObject genPlanObj = new JSONObject(genPlanArray.get(0).toString()).getJSONObject("data");
					JSONArray kopruses = new JSONArray(genPlanObj.get("sets_of_pathes").toString());
					parseKorpuses(kopruses, operationResult, provider);
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
	}

	private void parseKorpuses(JSONArray kopruses, final OnOperationResult operationResult, final OperationProvider provider) throws JSONException {
		for (int i = 0; i < kopruses.length(); i++) {
			JSONObject korpusObject = new JSONObject(kopruses.get(i).toString());
			final Korpus korpus = API.getAPIKorpus(korpusObject);
//						DBProvider.updateOrInsertKorpus(getApplicationContext(), korpus);
			API.requestSinglePage(getApplicationContext(), korpus.getKorpusID(), new onApiResult() {
				@Override
				public void parseResultApi(String method, String result) {
					try {
						JSONArray sections = API.getApiSections(result);
						parseSections(sections, korpus);
//							Config.setString("lastUpdate", Utility.getDateTime(System.currentTimeMillis(),"HH:ss dd MM yyyy"),getApplicationContext());
						operationResult.resultSuccess(provider);
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
		}
	}

	private static void parseSections(JSONArray sections, Korpus korpus) throws JSONException {
		for (int i = 0; i < sections.length(); i++) {
			JSONObject sectionJsonObject = new JSONObject(String.valueOf(sections.get(i)));
			Section section = API.parceAPISection(sectionJsonObject, korpus);
//												DBProvider.updateOrInsertSection(getApplicationContext(), section);
			JSONObject floorsObject = new JSONObject(sectionJsonObject.getString("floors"));
			int min = API.getMinItems(floorsObject);
			int max = API.getMaxItems(floorsObject, min);
			parseFloors(section, floorsObject, min, max, korpus);
		}
	}

	private static void parseFloors(Section section, JSONObject floorsObject, int min, int max, Korpus korpus) throws JSONException {
		for (int j = min; j < max; j++) {
			JSONObject floorObject = new JSONObject(String.valueOf(floorsObject.get(String.valueOf(j))));
			Floor floor = API.parseAPIFloor(section, j, floorObject, korpus);
//										DBProvider.updateOrInsertFloor(getApplicationContext(), floor);
			JSONArray flatsArray = new JSONArray(floorObject.getString("flats"));
			parseFlats(section, floor, flatsArray, korpus);
		}
	}

	private static void parseFlats(Section section, Floor floor, JSONArray flatsArray, Korpus korpus) throws JSONException {
		for (int k = 0; k < flatsArray.length(); k++) {
			JSONObject flatJsonObject = new JSONObject(String.valueOf(flatsArray.get(k)));
			Flat flat = API.parseAPIFlat(section, floor, flatJsonObject, korpus);
//											DBProvider.updateOrInsertFlat(getApplicationContext(), flat);
		}
	}
}