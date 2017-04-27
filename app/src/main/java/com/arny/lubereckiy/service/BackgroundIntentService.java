package com.arny.lubereckiy.service;
import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.db.DBProvider;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.Floor;
import com.arny.lubereckiy.models.Korpus;
import com.arny.lubereckiy.models.Section;
import com.arny.lubereckiy.network.API;
import com.arny.lubereckiy.network.ApiSendService;
import com.arny.lubereckiy.network.onApiResult;
import com.arny.lubereckiy.network.onApiResultCallback;
import com.arny.lubereckiy.network.onApiResultError;
import com.arny.lubereckiy.utils.Config;
import com.arny.lubereckiy.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.arny.lubereckiy.network.API.*;

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
	private Korpus korpusGlobal;
	private Section sectionGlobal;
	private Floor floorGlobal;
	private Flat flatGlobal;
	private JSONObject jObject;
	private JSONArray jArray;

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
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy: time = " + Utility.getDateTime());
		mIsStopped = true;
		sendBroadcastIntent(getResultNotif());
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
		korpusGlobal = new Korpus();
		sectionGlobal = new Section();
		floorGlobal = new Floor();
		flatGlobal = new Flat();
		switch (getOperation()) {
			case OPERATION_PARSE_GSON:
				this.start = System.currentTimeMillis();
				try {
					Log.i(BackgroundIntentService.class.getSimpleName(), "onHandleIntent: ");
					parseKorpuses();
				} catch (JSONException | InterruptedException e) {
					e.printStackTrace();
				}
				break;
		}
	}


	private void getKorpuses() {
		API.requestGenPlan(getApplicationContext(),new onApiResult() {
			@Override
			public void parseResultApi(String method, String result) {
//				try {
//					jArray = new JSONArray(result);
//					jObject = new JSONObject(jArray.get(0).toString()).getJSONObject("data");
//					jArray = new JSONArray(jObject.get("sets_of_pathes").toString());
//					Log.d(BackgroundIntentService.class.getSimpleName(), "getKorpuses: time " + (System.currentTimeMillis() - start));
//					parseKorpuses(jArray);
//				} catch (JSONException e) {
//					e.printStackTrace();
//					hashMap.put("error", e.getMessage());
//					mIsSuccess = false;
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}

			@Override
			public void parseResultApi(String method, VolleyError error) {
				hashMap.put("error", error.toString());
				mIsSuccess = false;
			}
		});
	}

	private void parseKorpuses() throws JSONException, InterruptedException {
		Log.i(BackgroundIntentService.class.getSimpleName(), "parseKorpuses: ");
		for (int i = 0; i < 20; i++) {
//			jObject = new JSONObject(korpuses.get(i).toString());
//			API.getAPIKorpus(korpusGlobal,jObject);
//			DBProvider.updateOrInsertKorpus(getApplicationContext(), korpusGlobal);
		ApiSendService.hostRequest(0, getApplicationContext(), "http://jsonplaceholder.typicode.com/users", new JSONObject(), new onApiResultCallback() {
			@Override
			public void callback(JSONObject response) {
//				JSONArray sections = null;
//				try {
//					sections = API.getApiSections(response.toString());
//
////					parseSections(sections, korpusGlobal);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
				Log.d(BackgroundIntentService.class.getSimpleName(), "parseResultApi: time = " + (System.currentTimeMillis() - start) + " response = " + response.toString());
			}
		}, new onApiResultError() {
			@Override
			public void callback(String message, int position) {
				hashMap.put("error", message);
				mIsSuccess = false;
			}
		});
		}
		Config.setString(Config.LAST_UPDATE, Utility.getDateTime(System.currentTimeMillis(),"HH:mm dd MM yyyy"),getApplicationContext());
		mIsSuccess = true;
	}

	private void parseSections(final JSONArray sections, final Korpus korpus) throws JSONException {
		for (int i = 0; i < sections.length(); i++) {
			try {
				jObject = new JSONObject(String.valueOf(sections.get(i)));
				API.parceAPISection(sectionGlobal, jObject, korpus);
//						DBProvider.updateOrInsertSection(getApplicationContext(), sectionGlobal);
				jObject = new JSONObject(jObject.getString("floors"));
				int min = API.getMinItems(jObject);
				int max = API.getMaxItems(jObject, min);
				parseFloors(sectionGlobal, jObject, min, max, korpus);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void parseFloors(final Section section, final JSONObject floorsObject, int min, int max, final Korpus korpus) throws JSONException {
		for (int j = min; j < max; j++) {
			final int finalJ = j;
					try {
						jObject = new JSONObject(String.valueOf(floorsObject.get(String.valueOf(finalJ))));
						API.parseAPIFloor(floorGlobal, section, finalJ, jObject, korpus);
//						DBProvider.updateOrInsertFloor(getApplicationContext(), floorGlobal);
						jArray = new JSONArray(jObject.getString("flats"));
						parseFlats(section, floorGlobal, jArray, korpus);
					} catch (JSONException e) {
						e.printStackTrace();
					}
		}
	}

	private void parseFlats(Section section, Floor floor, JSONArray flatsArray, Korpus korpus) throws JSONException {
		for (int k = 0; k < flatsArray.length(); k++) {
			jObject = new JSONObject(String.valueOf(flatsArray.get(k)));
			API.parseAPIFlat(flatGlobal,section, floor, jObject, korpus);
//			DBProvider.updateOrInsertFlat(getApplicationContext(), flatGlobal);
		}
	}
}