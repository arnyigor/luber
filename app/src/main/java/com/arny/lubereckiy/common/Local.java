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
import com.arny.arnylib.adapters.RecyclerBindableAdapter;
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

    public static Observable<List<KorpusSection>> getListkorpuses(String url, String id) {
        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        return ApiFactory.getInstance()
                .createService(PikKorpusService.class, API.BASE_URL)
                .getKorpus(url, id)
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

    public static void viewObjectGenPlan(Context context, Pikobject pikobject) {
        Intent intent = new Intent(context, ObjectDetailActivity.class);
        intent.putExtra("url", pikobject.getUrl());
        intent.putExtra("title", pikobject.getName());
        navigateTo(context, intent);
    }

    public static void viewKorpus(Context context, Korpus korpus, String url, String titleObject) {
        Intent intent = new Intent(context, KorpusViewActivity.class);
        intent.putExtra("id", korpus.getId());
        intent.putExtra("korpus", korpus.getTitle());
        intent.putExtra("url", url);
        intent.putExtra("object_title", titleObject);
        navigateTo(context, intent);
    }

    private static void navigateTo(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static <T> void setAdapterData(RecyclerBindableAdapter adapter, List<? extends T> items, RecyclerView recyclerView, boolean update) {
        if (!update) {
            adapter.clear();
        }
        adapter.addAll(items);
        DroidUtils.runLayoutAnimation(recyclerView, R.anim.layout_animation_slide_right);
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

    public enum GridItemType{
        floorNum,
        flat
    }

    public static ArrayList<GridViewItem> getSectionFlatsArray(KorpusSection section) {
        LinkedHashMap<Integer, Floor> floors = section.getFloors();
        ArrayList<GridViewItem> flatViews = new ArrayList<>();
        ListIterator<Map.Entry<Integer, Floor>> iterator = new ArrayList<>(floors.entrySet()).listIterator(floors.size());
        while (iterator.hasPrevious()) {
            Map.Entry<Integer, Floor> floorEntry = iterator.previous();
            Floor floor = floorEntry.getValue();
            List<Flat> flats = floor.getFlats();
            for (int j = flats.size() - 1; j >= 0; j--) {
                Flat flat = getStagedFlat(flats, j);
                GridViewItem item = new GridViewItem();
                item.setType(GridItemType.flat);
                item.setFlat(flat);
                flatViews.add(item);
            }
        }
        return flatViews;
    }

    /**
     * Рисуем квартиры
     *
     * @param canvas
     * @param section
     * @param paint
     * @param width
     * @param height
     * @return
     */
    public static ArrayList<FlatView> getSectionRects(Canvas canvas, KorpusSection section, Paint paint, int width, int height) {
        int x = section.getMaxFlatsOnFloor();
        LinkedHashMap<Integer, Floor> floors = section.getFloors();
        int y = floors.size();
        int startBorder = (int) ((float) width * 0.1);
        int width0 = width;
        width -= startBorder * 2;
        int block = (int) ((float) width / x);
        int border = (int) ((float) block * 0.05);
        int flatBlock = block - border;
        System.out.println(section.getName() + " x:" + x + " y:" + y + "screen:" + width + "/" + height);
        int left = startBorder;
        int top = startBorder;
        int x_inc = flatBlock + (border * 2);
        int y_inc = flatBlock + (border * 2);
        int right = left + flatBlock;
        int bottom = top + flatBlock;
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(3);
        paint.setTextSize(20);
        ArrayList<FlatView> flatViews = new ArrayList<>();
        int stopX = width0 - border;
        int stopY = (y * block) + (startBorder * 2);
        canvas.drawLine(border, border, stopX, border, paint);
        canvas.drawLine(stopX, border, stopX, stopY, paint);
        canvas.drawLine(stopX, stopY, border, stopY, paint);
        canvas.drawLine(border, stopY, border, border, paint);
        ListIterator<Map.Entry<Integer, Floor>> iterator = new ArrayList<>(floors.entrySet()).listIterator(floors.size());
        while (iterator.hasPrevious()) {
            Map.Entry<Integer, Floor> entry = iterator.previous();
            Integer key = entry.getKey();
            Floor value = entry.getValue();
            List<Flat> flats = value.getFlats();
            paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(key), (float) startBorder - 25, (float) top + ((float) flatBlock / 2), paint);
            for (int j = flats.size() - 1; j >= 0; j--) {
                drawFlat(canvas, paint, left, top, right, bottom, flatViews, key, flats, j);
                left += x_inc;
                right += x_inc;

            }
            top += y_inc;
            bottom += y_inc;
            left = startBorder;
            right = left + flatBlock;
        }
        return flatViews;
    }

    private static Flat getStagedFlat(List<Flat> flats, int pos) {
        for (Flat flat : flats) {
            if ((pos + 1) == flat.getStageNumber1()) {
                return flat;
            }
        }
        return flats.get(pos);
    }

    private static void drawFlat(Canvas canvas, Paint paint, int left, int top, int right, int bottom, ArrayList<FlatView> flatViews, int i, List<Flat> flats, int j) {
        Flat flat = getStagedFlat(flats, j);
        FlatView e = new FlatView(left, top, right, bottom, i - 1, j);
        e.setFlat(flat);
        paint.setColor(Color.GRAY);
        flatViews.add(e);
        Status status = flat.getStatus();
        if (status != null && status.getUrl() != null) {
            switch (status.getUrl()) {
                case "unavailable":
                    paint.setColor(Color.GRAY);
                    canvas.drawLine(left, top, right, top, paint);
                    canvas.drawLine(right, top, right, bottom, paint);
                    canvas.drawLine(right, bottom, left, bottom, paint);
                    canvas.drawLine(left, bottom, left, top, paint);
                    break;
                case "free":
                    if (flat.getDiscount().equals("false")) {
                        paint.setColor(Color.GREEN);
                        canvas.drawRect(left, top, right, bottom, paint);
                        paint.setColor(Color.BLACK);
                        canvas.drawText(flat.getRoomQuantity(), (float) e.centerX() - 10, (float) e.centerY() + 10, paint);
                    } else {
                        paint.setColor(Color.RED);
                        canvas.drawRect(left, top, right, bottom, paint);
                        paint.setColor(Color.WHITE);
                        canvas.drawText(flat.getRoomQuantity(), (float) e.centerX() - 10, (float) e.centerY() + 10, paint);
                    }
                    break;
                case "reserve":
                    paint.setColor(Color.parseColor("#ffe0af"));
                    canvas.drawRect(left, top, right, bottom, paint);
                    paint.setColor(Color.BLACK);
                    canvas.drawText(flat.getRoomQuantity(), (float) e.centerX() - 10, (float) e.centerY() + 10, paint);
                    break;
                case "sold":
                    paint.setColor(Color.GRAY);
                    canvas.drawLine(left, top, right, top, paint);
                    canvas.drawLine(right, top, right, bottom, paint);
                    canvas.drawLine(right, bottom, left, bottom, paint);
                    canvas.drawLine(left, bottom, left, top, paint);
                    canvas.drawText(flat.getRoomQuantity(), (float) e.centerX() - 10, (float) e.centerY() + 10, paint);
                    break;
            }
        }
    }

    @NonNull
    public static ArrayList<KorpusSection> filterEmptySections(List<KorpusSection> korpusSections) {
        ArrayList<KorpusSection> newMap = new ArrayList<>();
        boolean sold, unavalable, free, reserve;
        boolean wsold, wunavalable, wfree, wreserve;
        for (KorpusSection korpusSection : korpusSections) {
            for (Map.Entry<Integer, Floor> integerFloorEntry : korpusSection.getFloors().entrySet()) {
                for (Flat flat : integerFloorEntry.getValue().getFlats()) {
                    sold = flat.getStatus().getUrl().equals("sold");
                    free = flat.getStatus().getUrl().equals("free");
                    reserve = flat.getStatus().getUrl().equals("reserve");
                    if (!sold) {
                        newMap.add(korpusSection);
                        break;
                    }
                }
            }

        }
        return newMap;
    }

    public enum FilterObjectsType {
        defaultType,
        minPrice
    }

    public static List<Pikobject> sortObjects(List<Pikobject> objects, FilterObjectsType type) {
        switch (type) {
            case minPrice:
                List<Pikobject> sorted = new ArrayList<>();
                sorted.addAll(objects);
                Collections.sort(sorted, (o1, o2) -> o1.getMinPrice().compareTo(o2.getMinPrice()));
                return sorted;
            case defaultType:
                return objects;
        }
        return objects;
    }
}
