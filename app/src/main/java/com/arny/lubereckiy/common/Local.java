package com.arny.lubereckiy.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.arny.arnylib.adapters.RecyclerBindableAdapter;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.models.*;
import com.arny.lubereckiy.ui.activities.KorpusViewActivity;
import com.arny.lubereckiy.ui.activities.ObjectDetailActivity;

import java.util.*;
public class Local {

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
        DroidUtils.runLayoutAnimation(recyclerView, R.anim.layout_animation_fall_down);
    }

    public static String getFlatRoomCount(String cnt) {
        switch (cnt) {
            case "С":
                return "Студия";
            case "1":
                return "Однокомнатная квартира";
            case "2":
                return "Двухкомнатная квартира";
            case "3":
                return "Трехкомнатная квартира";
            case "4":
                return "Четырехкомнатная квартира";
            default:
                return "Нет данных";
        }
    }

    public enum GridItemType {
        floorNum,
        flat
    }

    public static ArrayList<GridViewItem> getSectionFlatsArray(KorpusSection section) {
        LinkedHashMap<Integer, Floor> floors = section.getFloors();
        ListIterator<Map.Entry<Integer, Floor>> iterator = new ArrayList<>(floors.entrySet()).listIterator(floors.size());
        ArrayList<GridViewItem> flatViews = new ArrayList<>();
        while (iterator.hasPrevious()) {
            Map.Entry<Integer, Floor> floorEntry = iterator.previous();
            Integer floorNum = floorEntry.getKey();
            System.out.println("floorNum:" +floorNum);
            Floor floor = floorEntry.getValue();
            List<Flat> flats = floor.getFlats();
            flatViews.add(getPreFlatItem(floorNum, floor));
            for (int j = flats.size() - 1; j >= 0; j--) {
                Flat flat = getStagedFlat(flats, j);
                GridViewItem item = new GridViewItem();
                item.setType(GridItemType.flat);
                item.setFlat(flat);
                item.setFloorNum(floorNum);
                flatViews.add(item);
            }
        }
        return flatViews;
    }

    /**
     * Номер этажа
     * @param floorNum
     * @param floor
     * @return
     */
    @NonNull
    private static GridViewItem getPreFlatItem(Integer floorNum, Floor floor) {
        GridViewItem preItem = new GridViewItem();
        preItem.setType(GridItemType.floorNum);
        Flat fl = new Flat();
        Planing planing = new Planing();
        planing.setSrcLayout(floor.getPlan());
        fl.setPlaning(planing);
        preItem.setFlat(fl);
        preItem.setFloorNum(floorNum);
        return preItem;
    }

    /**
     * Нужная квартира на этаже
     * @param flats
     * @param pos
     * @return
     */
    public static Flat getStagedFlat(List<Flat> flats, int pos) {
        for (Flat flat : flats) {
            if ((pos + 1) == flat.getStageNumber1()) {
                return flat;
            }
        }
        return flats.get(pos);
    }

    @NonNull
    public static ArrayList<KorpusSection> filterEmptySections(List<KorpusSection> korpusSections) {
        ArrayList<KorpusSection> newMap = new ArrayList<>();
        boolean sold, unavalable, free, reserve, hasFlats;
        for (KorpusSection korpusSection : korpusSections) {
            hasFlats = false;
            if (korpusSection.getFloors().size() > 0) {
                for (Map.Entry<Integer, Floor> integerFloorEntry : korpusSection.getFloors().entrySet()) {
                    if (hasFlats) {
                        break;
                    }
                    List<Flat> flats = integerFloorEntry.getValue().getFlats();
                    for (Flat flat : flats) {
                        String status = flat.getStatus().getUrl();
                        sold = status.equals("sold");
                        free = status.equals("free");
                        reserve = status.equals("reserve");
                        unavalable = status.equals("unavailable");
                        if ((!unavalable && !sold) || free || reserve) {
                            newMap.add(korpusSection);
                            hasFlats = true;
                            break;
                        }
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
