package com.arny.lubereckiy.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.network.ApiFactory;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.api.API;
import com.arny.lubereckiy.api.PikObjectsService;
import com.arny.lubereckiy.api.PikobjectgenplanService;
import com.arny.lubereckiy.models.GenPlan;
import com.arny.lubereckiy.models.Pikobject;
import com.arny.lubereckiy.ui.activities.ObjectDetailActivity;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
public class Local {

	public static Observable<List<Pikobject>> loadPikObjects(){
		return ApiFactory.getInstance()
				.createService(PikObjectsService.class, API.BASE_API_URL)
				.getObjects();
	}

	public static Observable<List<GenPlan>> loadGenplan(String object){
		return ApiFactory.getInstance()
				.createService(PikobjectgenplanService.class, API.BASE_URL)
				.getObjectGenPlan(object);
	}

	public static void showObjectMap(Context context, Pikobject pikobject){
		String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(pikobject.getLatitude()), Double.parseDouble(pikobject.getLongitude()));
		navigateTo(context, new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
	}

	public static void viewObjectGenPlan(Context context,String url){
		Intent intent = new Intent(context, ObjectDetailActivity.class);
		intent.putExtra("url", url);
		navigateTo(context, intent);
	}

	private static void navigateTo(Context context, Intent intent) {
		context.startActivity(intent);
	}

	public static <T> void setAdapterData(SimpleBindableAdapter adapter, List<? extends T> items, RecyclerView recyclerView) {
		adapter.clear();
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
