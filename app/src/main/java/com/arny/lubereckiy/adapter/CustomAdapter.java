package com.arny.lubereckiy.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.GridViewItem;
import com.arny.lubereckiy.models.Status;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    private ArrayList<GridViewItem> flats;
    private Context context;
    private LayoutInflater inflater = null;

    public CustomAdapter(Context context, ArrayList<GridViewItem> items) {
        super(context, 0);
        this.flats = items;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return flats.size();
    }

    @Override
    public GridViewItem getItem(int position) {
        return flats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

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
                    switch (status.getUrl()) {
                        case "unavailable":
                            System.out.println(position + " unavailable");
                            row.setClickable(false);
                            break;
                        case "free":
                            if (flat.getDiscount().equals("false")) {
                                textViewTitle.setTextColor(Color.BLACK);
                                textViewTitle.setBackgroundColor(Color.GREEN);
                                textViewTitle.setText(flat.getRoomQuantity());
                            } else {
                                textViewTitle.setTextColor(Color.WHITE);
                                textViewTitle.setBackgroundColor(Color.RED);
                                textViewTitle.setText(flat.getRoomQuantity());
                            }
                            break;
                        case "reserve":
                            textViewTitle.setTextColor(Color.BLACK);
                            textViewTitle.setBackgroundColor(Color.parseColor("#ffe0af"));
                            textViewTitle.setText(flat.getRoomQuantity());
                            break;
                        case "sold":
                            textViewTitle.setTextColor(Color.GRAY);
                            textViewTitle.setText(flat.getRoomQuantity());
                            textViewTitle.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.flat_bg));
                            break;
                    }
                }
            }else{
                row.setClickable(false);
            }
        }
        return row;
    }

}