package com.arny.lubereckiy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.GridViewItem;
import com.arny.lubereckiy.models.Status;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<GridViewItem> {
    private ArrayList<GridViewItem> flats;
    private Context context;

    public CustomAdapter(Context context, ArrayList<GridViewItem> items) {
        super(context, 0);
        this.flats = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return flats.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public GridViewItem getItem(int position) {
        return flats.get(position);
    }

    // Класс для сохранения во внешний класс и для ограничения доступа
    // из потомков класса
    static class ViewHolder {
        public TextView textView;
    }
    @SuppressLint("DefaultLocale")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.flat_item, parent, false);
            TextView textViewTitle = (TextView) row.findViewById(R.id.info);
            Flat flat = flats.get(position).getFlat();
            if (flat != null) {
                Status status = flat.getStatus();
                if (status != null && status.getUrl() != null) {
                    Local.setCell(position, textViewTitle, flat, context, flats.get(position).getFlat().getStatus().getUrl());
                }
            } else {
                row.setClickable(false);
            }
        }
        return row;
    }

}