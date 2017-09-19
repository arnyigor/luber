package com.arny.lubereckiy.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.network.ApiFactory;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.api.API;
import com.arny.lubereckiy.api.PikKorpusService;
import com.arny.lubereckiy.api.PikObjectsService;
import com.arny.lubereckiy.api.PikobjectgenplanService;
import com.arny.lubereckiy.models.*;
import com.arny.lubereckiy.ui.activities.KorpusViewActivity;
import com.arny.lubereckiy.ui.activities.ObjectDetailActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
public class Local {

	public static Observable<List<Pikobject>> loadPikObjects(){
		return ApiFactory.getInstance()
				.createService(PikObjectsService.class, API.BASE_API_URL)
				.getObjects();
	}

	public static Observable<List<GenPlan>> loadGenplan(String object){
        if (object.startsWith("/")){
            object = object.substring(1);
        }
        return ApiFactory.getInstance()
                .createService(PikobjectgenplanService.class, API.BASE_URL)
                .getObjectGenPlan(object);
	}

	public static void getFloors(KorpusSection korpusSection) throws JSONException {
        LinkedHashMap<Integer, Floor> floors = korpusSection.getFloors();
        for (Map.Entry<Integer, Floor> integerFloorEntry : floors.entrySet()) {
            Integer floornumber = integerFloorEntry.getKey();
            Floor floor = integerFloorEntry.getValue();
            System.out.println(floor);
        }
    }

    public static Observable<KorpusData> loadKorpus(String object, String id){
        if (object.startsWith("/")){
            object = object.substring(1);
        }
        return ApiFactory.getInstance()
                .createService(PikKorpusService.class, API.BASE_URL)
                .getKorpus(object, id);
    }

	public static void showObjectMap(Context context, Pikobject pikobject){
		String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(pikobject.getLatitude()), Double.parseDouble(pikobject.getLongitude()));
		navigateTo(context, new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
	}

    public static int getMaxFloors(JsonObject items, int min) {
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

    public static int getMinFloors(JsonObject items) {
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

	public static void viewObjectGenPlan(Context context, Pikobject pikobject){
		Intent intent = new Intent(context, ObjectDetailActivity.class);
		intent.putExtra("url", pikobject.getUrl());
		intent.putExtra("title", pikobject.getName());
		navigateTo(context, intent);
	}

    public static void viewKorpus(Context context, Korpus korpus,String object){
        Intent intent = new Intent(context, KorpusViewActivity.class);
        intent.putExtra("id", korpus.getId());
        intent.putExtra("object", object);
        navigateTo(context, intent);
    }

	private static void navigateTo(Context context, Intent intent) {
		context.startActivity(intent);
	}

	public static <T> void setAdapterData(SimpleBindableAdapter adapter, List<? extends T> items, RecyclerView recyclerView, boolean update) {
        if (!update) {
            adapter.clear();
        }
		adapter.addAll(items);
		DroidUtils.runLayoutAnimation(recyclerView, R.anim.layout_animation_fall_down);
	}

	public enum FilterObjectsType {
		defaultType,
		minPrice
	}

	public static List<Pikobject> filterObjects(List<Pikobject> objects, FilterObjectsType type){
		switch (type) {
			case minPrice:
				List<Pikobject> filtered = new ArrayList<>();
				filtered.addAll(objects);
				Collections.sort(filtered, (o1, o2) -> o1.getMinPrice().compareTo(o2.getMinPrice()));
				return filtered;
			case defaultType:
				return objects;
		}
		return objects;
	}
}
