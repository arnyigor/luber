package com.arny.lubereckiy.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.network.ApiFactory;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.api.API;
import com.arny.lubereckiy.api.PikKorpusService;
import com.arny.lubereckiy.api.PikObjectsService;
import com.arny.lubereckiy.api.PikobjectgenplanService;
import com.arny.lubereckiy.models.*;
import com.arny.lubereckiy.ui.activities.KorpusViewActivity;
import com.arny.lubereckiy.ui.activities.ObjectDetailActivity;
import com.arny.lubereckiy.ui.graphics.FlatView;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import java.util.*;
public class Local {

	public static Observable<List<Pikobject>> loadPikObjects() {
		return ApiFactory.getInstance()
				.createService(PikObjectsService.class, API.BASE_API_URL)
				.getObjects();
	}

	public static Observable<List<GenPlan>> loadGenplan(String object) {
		if (object.startsWith("/")) {
			object = object.substring(1);
		}
		return ApiFactory.getInstance()
				.createService(PikobjectgenplanService.class, API.BASE_URL)
				.getObjectGenPlan(object);
	}

	public static Observable<List<KorpusSection>> getListkorpuses(String object, String id) {
		if (object.startsWith("/")) {
			object = object.substring(1);
		}
		return ApiFactory.getInstance()
				.createService(PikKorpusService.class, API.BASE_URL)
				.getKorpus(object, id)
				.flatMap(korpusData -> Observable.create((ObservableOnSubscribe<List<KorpusSection>>) e -> {
					e.onNext(korpusData.getSections());
					e.onComplete();
				}))
				.map(Local::getNotEmptySections);
	}

	public static void showObjectMap(Context context, Pikobject pikobject) {
		String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(pikobject.getLatitude()), Double.parseDouble(pikobject.getLongitude()));
		navigateTo(context, new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
	}

	public static int getMaxFloors(JsonObject items, int min) {
		int max = 0;
		while (true) {
			if (max <= min) {
				max++;
			} else if (max > min && items.has(String.valueOf(max))) {
				max++;
			} else {
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
			} else {
				min++;
			}
		}
		return min;
	}

	public static void viewObjectGenPlan(Context context, Pikobject pikobject) {
		Intent intent = new Intent(context, ObjectDetailActivity.class);
		intent.putExtra("url", pikobject.getUrl());
		intent.putExtra("title", pikobject.getName());
		navigateTo(context, intent);
	}

	public static void viewKorpus(Context context, Korpus korpus, String object) {
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

	public static List<KorpusSection> getNotEmptySections(List<KorpusSection> korpusSections) {
		ArrayList<KorpusSection> sections = new ArrayList<>();
		for (KorpusSection korpusSection : korpusSections) {
			if (korpusSection.getFloors().size() > 0) {
				sections.add(korpusSection);
			}
		}
		return sections;
	}

	public static ArrayList<FlatView> getSectionRects(Canvas canvas, KorpusSection section, Paint paint, int width, int height) {
		int x = section.getMaxFlatsOnFloor();
		LinkedHashMap<Integer, Floor> floors = section.getFloors();
		int y = floors.size();
		width -= (int)((float) width * 0.05) * 2;
		int block = (int) ((float) width / x);
		int border = (int) ((float) block * 0.1);
		int flatBlock = block - border;
		System.out.println(section.getName() + " x:" + x + " y:" + y + "screen:" + width + "/" + height);
		int left = border;
		int top = border;
		int x_inc = flatBlock + (border * 2);
		int y_inc = flatBlock + (border * 2);
		int right = left + flatBlock;
		int bottom = top + flatBlock;
		paint.setColor(Color.GRAY);
		ArrayList<FlatView> flatViews = new ArrayList<>();
		for (int i = 1; i <= y; i++) {
			Floor floor = floors.get(i);
			if (floor != null) {
				List<Flat> flats = floor.getFlats();
				for (int j = 0; j < flats.size(); j++) {
					paint.setStrokeWidth(3);
					paint.setTextSize(20);
					Flat flat = flats.get(j);
					FlatView e = new FlatView(left, top, right, bottom, i - 1, j);
					e.setFlat(flat);
					paint.setColor(Color.GRAY);
					canvas.drawText(flat.getRoomQuantity(), (float) e.centerX() - 10, (float) e.centerY() + 10, paint);
					flatViews.add(e);
					paint.setColor(Color.GRAY);
					if (flat.ge)//// TODO: 20.09.2017  
					canvas.drawRect(left, top, right, bottom,);

					canvas.drawLine(left, top, right, top, paint);
					canvas.drawLine(right, top, right, bottom, paint);
					canvas.drawLine(right, bottom, left, bottom, paint);
					canvas.drawLine(left, bottom, left, top, paint);
					left += x_inc;
					right += x_inc;
				}
			}
			top += y_inc;
			bottom += y_inc;
			left = border;
			right = left + flatBlock;
		}
		return flatViews;
	}

	public enum FilterObjectsType {
		defaultType,
		minPrice
	}

	public static List<Pikobject> filterObjects(List<Pikobject> objects, FilterObjectsType type) {
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

	private static class RandomRect {
		private int left;
		private int top;
		private int right;
		private int bottom;

		public int getLeft() {
			return left;
		}

		public int getTop() {
			return top;
		}

		public int getRight() {
			return right;
		}

		public int getBottom() {
			return bottom;
		}

		public RandomRect invoke() {
			int min = MathUtils.randInt(0, 200);
			int max = MathUtils.randInt(min, 200);
			left = MathUtils.randInt(min, max);
			top = MathUtils.randInt(min, max);
			right = MathUtils.randInt(left, 200);
			bottom = MathUtils.randInt(0, top);
			return this;
		}
	}
}
