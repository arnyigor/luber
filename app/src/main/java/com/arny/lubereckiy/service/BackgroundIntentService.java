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
import com.arny.lubereckiy.network.onApiResult;
import com.arny.lubereckiy.utils.Config;
import com.arny.lubereckiy.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

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
	private Thread threadFloors[];
	private Thread threadSections[];

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
		setOperation(intent.getIntExtra(EXTRA_KEY_OPERATION_CODE, 0));
		switch (getOperation()) {
			case OPERATION_PARSE_GSON:
				this.start = System.currentTimeMillis();
				getKorpuses();
				break;
		}
	}


	private void getKorpuses() {
		API.requestGenPlan(new onApiResult() {
			@Override
			public void parseResultApi(String method, String result) {
				JSONArray genPlanArray = null;
				try {
					genPlanArray = new JSONArray(result);
					JSONObject genPlanObj = new JSONObject(genPlanArray.get(0).toString()).getJSONObject("data");
					JSONArray kopruses = new JSONArray(genPlanObj.get("sets_of_pathes").toString());
					parseKorpuses(kopruses);
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
			API.getAPIKorpus(korpusGlobal,korpusObject);
			DBProvider.updateOrInsertKorpus(getApplicationContext(), korpusGlobal);
			API.requestSinglePage(korpusGlobal.getKorpusID(), new onApiResult() {
				@Override
				public void parseResultApi(String method, String result) {
					try {
						JSONArray sections = API.getApiSections(result);
						parseSections(sections, korpusGlobal);
						Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
						for (Thread thread :threadSet) {
							Log.i(BackgroundIntentService.class.getSimpleName(), "parseResultApi: "  +  thread.getName() + "\nIs Daemon " + thread.isDaemon() + "\nIs Alive " + thread.isAlive());
						}
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
	}

	private void parseSections(final JSONArray sections, final Korpus korpus) throws JSONException {
		for (int i = 0; i < sections.length(); i++) {
			final int finalI = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						JSONObject sectionJsonObject = new JSONObject(String.valueOf(sections.get(finalI)));
						API.parceAPISection(sectionGlobal, sectionJsonObject, korpus);
						DBProvider.updateOrInsertSection(getApplicationContext(), sectionGlobal);
						JSONObject floorsObject = new JSONObject(sectionJsonObject.getString("floors"));
						int min = API.getMinItems(floorsObject);
						int max = API.getMaxItems(floorsObject, min);
						parseFloors(sectionGlobal, floorsObject, min, max, korpus);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	private void parseFloors(final Section section, final JSONObject floorsObject, int min, int max, final Korpus korpus) throws JSONException {
		for (int j = min; j < max; j++) {
			final int finalJ = j;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						JSONObject floorObject = new JSONObject(String.valueOf(floorsObject.get(String.valueOf(finalJ))));
						API.parseAPIFloor(floorGlobal, section, finalJ, floorObject, korpus);
						DBProvider.updateOrInsertFloor(getApplicationContext(), floorGlobal);
						JSONArray flatsArray = new JSONArray(floorObject.getString("flats"));
						parseFlats(section, floorGlobal, flatsArray, korpus);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	private void parseFlats(Section section, Floor floor, JSONArray flatsArray, Korpus korpus) throws JSONException {
		for (int k = 0; k < flatsArray.length(); k++) {
			JSONObject flatJsonObject = new JSONObject(String.valueOf(flatsArray.get(k)));
			API.parseAPIFlat(flatGlobal,section, floor, flatJsonObject, korpus);
			DBProvider.updateOrInsertFlat(getApplicationContext(), flatGlobal);
		}
	}
}