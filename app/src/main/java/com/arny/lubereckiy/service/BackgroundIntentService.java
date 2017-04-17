package com.arny.lubereckiy.service;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.arny.lubereckiy.R;
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

public class BackgroundIntentService extends IntentService {
	/*Extras*/
	public static final String ACTION = "com.arny.flightlogbook.models.BackgroundIntentService";
	public static final String EXTRA_KEY_OPERATION_CODE = "BackgroundIntentService.operation.code";
	public static final String EXTRA_KEY_OPERATION_RESULT = "BackgroundIntentService.operation.result";
	public static final String EXTRA_KEY_FINISH = "BackgroundIntentService.operation.finish";
	public static final String EXTRA_KEY_FINISH_SUCCESS = "BackgroundIntentService.operation.success";
	public static final String EXTRA_KEY_OPERATION_DATA = "BackgroundIntentService.operation.data";
	/*Opearations*/
	public static final int OPERATION_PARSE_GSON = 100;
	/*other*/
	private static final String TAG = BackgroundIntentService.class.getName();
	private int operation;
	private boolean mIsSuccess;
	private boolean mIsStopped;
	private HashMap<String, String> hashMap;
	private long start;

	private static BackgroundIntentService instance = null;

	public static boolean isInstanceCreated() {
		return instance != null;
	}

	public int getOperation(){
		return operation;
	}

	private void setOperation(int operation){
		this.operation = operation;
	}

	public BackgroundIntentService() {
		super("BackgroundIntentService");
		mIsSuccess = false;
		mIsStopped = false;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy: ");
		mIsStopped = true;
		sendBroadcastIntent(getResultNotif());
		instance = null;
		super.onDestroy();
	}

	private void onServiceDestroy() {
		mIsStopped = true;
		sendBroadcastIntent(getResultNotif());
	}

	private String getResultNotif() {
		String notice;
		if (mIsSuccess) {
			notice = getApplicationContext().getString(R.string.service_operation_success);
		} else {
			notice = getApplicationContext().getString(R.string.service_operation_fail);
		}
		return notice;
	}

	private void sendBroadcastIntent(String result) {
		Intent intent = initProadcastIntent();
		intent.putExtra(EXTRA_KEY_FINISH, mIsStopped);
		intent.putExtra(EXTRA_KEY_FINISH_SUCCESS, mIsSuccess);
		intent.putExtra(EXTRA_KEY_OPERATION_CODE, operation);
		intent.putExtra(EXTRA_KEY_OPERATION_RESULT, result);
		intent.putExtra(EXTRA_KEY_OPERATION_DATA, hashMap);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	@NonNull
	private Intent initProadcastIntent() {
		Intent intent = new Intent();
		intent.setAction(ACTION);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		return intent;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		hashMap = new HashMap<>();
		setOperation(intent.getIntExtra(EXTRA_KEY_OPERATION_CODE, 0));
		switch (getOperation()) {
			case OPERATION_PARSE_GSON:
				this.start = System.currentTimeMillis();
				getKorpuses();
				Log.i(BackgroundIntentService.class.getSimpleName(), "onHandleIntent: time = " +  +( System.currentTimeMillis() - start));
				break;
		}
	}

	private void getKorpuses() {
		API.requestGenPlan(getApplicationContext(), new onApiResult() {
			@Override
			public void parseResultApi(String method, String result) {
				JSONArray genPlanArray = null;
				try {
					genPlanArray = new JSONArray(result);
					JSONObject genPlanObj = new JSONObject(genPlanArray.get(0).toString()).getJSONObject("data");
					JSONArray kopruses = new JSONArray(genPlanObj.get("sets_of_pathes").toString());
					Log.i(BackgroundIntentService.class.getSimpleName(), "parseResultApi1: time = " +( System.currentTimeMillis() - start));
					parseKorpuses(kopruses);
					Log.i(BackgroundIntentService.class.getSimpleName(), "parseResultApi2: time = " +( System.currentTimeMillis() - start));
				} catch (JSONException e) {
					e.printStackTrace();
					hashMap.put("error", e.getMessage());
					mIsSuccess = false;
				}
			}

			@Override
			public void parseResultApi(String method, VolleyError error) {
				hashMap.put("error", error.toString());
				mIsSuccess = false;
			}
		});
	}

	private void parseKorpuses(final JSONArray korpuses) throws JSONException {
		for (int i = 0; i < korpuses.length(); i++) {
			JSONObject korpusObject = new JSONObject(korpuses.get(i).toString());
			final Korpus korpus = API.getAPIKorpus(korpusObject);
			Log.i(TAG, "parseKorpuses: korpus = " + korpus.getTitle());
//			DBProvider.updateOrInsertKorpus(getApplicationContext(), korpus);
			final int finalI = i;
			API.requestSinglePage(getApplicationContext(), korpus.getKorpusID(), new onApiResult() {
				@Override
				public void parseResultApi(String method, String result) {
					try {
						JSONArray sections = API.getApiSections(result);
						parseSections(sections, korpus);
						Log.i(BackgroundIntentService.class.getSimpleName(), "parseResultApi: parseKorpuses finalI = " + finalI);
					} catch (JSONException e) {
						e.printStackTrace();
						hashMap.put("error", e.getMessage());
						mIsSuccess = false;
					}
				}

				@Override
				public void parseResultApi(String method, VolleyError error) {
					hashMap.put("error", error.toString());
					mIsSuccess = false;
				}
			});
		}
		Config.setString(Config.LAST_UPDATE, Utility.getDateTime(System.currentTimeMillis(),"HH:mm dd MM yyyy"),getApplicationContext());
		mIsSuccess = true;
		Log.i(TAG, "parseKorpuses result time: " +( System.currentTimeMillis() - start));
	}

	private void parseSections(JSONArray sections, Korpus korpus) throws JSONException {
		for (int i = 0; i < sections.length(); i++) {
			JSONObject sectionJsonObject = new JSONObject(String.valueOf(sections.get(i)));
			Section section = API.parceAPISection(sectionJsonObject, korpus);
//			DBProvider.updateOrInsertSection(getApplicationContext(), section);
			JSONObject floorsObject = new JSONObject(sectionJsonObject.getString("floors"));
			int min = API.getMinItems(floorsObject);
			int max = API.getMaxItems(floorsObject, min);
			parseFloors(section, floorsObject, min, max, korpus);
		}

		onDestroy();
	}

	private void parseFloors(Section section, JSONObject floorsObject, int min, int max, Korpus korpus) throws JSONException {
		for (int j = min; j < max; j++) {
			JSONObject floorObject = new JSONObject(String.valueOf(floorsObject.get(String.valueOf(j))));
			Floor floor = API.parseAPIFloor(section, j, floorObject, korpus);
//			DBProvider.updateOrInsertFloor(getApplicationContext(), floor);
			JSONArray flatsArray = new JSONArray(floorObject.getString("flats"));
			parseFlats(section, floor, flatsArray, korpus);
		}
	}

	private void parseFlats(Section section, Floor floor, JSONArray flatsArray, Korpus korpus) throws JSONException {
		for (int k = 0; k < flatsArray.length(); k++) {
			JSONObject flatJsonObject = new JSONObject(String.valueOf(flatsArray.get(k)));
			Flat flat = API.parseAPIFlat(section, floor, flatJsonObject, korpus);
//			DBProvider.updateOrInsertFlat(getApplicationContext(), flat);
		}
	}

}