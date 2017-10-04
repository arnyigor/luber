package com.arny.lubereckiy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.GridViewItem;

import java.util.ArrayList;
public class FlatsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<GridViewItem> flats;

    public FlatsAdapter(Context context, ArrayList<GridViewItem> items) {
        mContext = context;
        flats = items;
    }

    @Override
    public int getCount() {
        return flats.size(); // adapter inflates the row according to the count of data given
    }

    @Override
    public GridViewItem getItem(int i) {
        return flats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.flat_item, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.info);
        GridViewItem item = getItem(position);
        System.out.println("item:" + item);
        System.out.println("tv:" + textView);
        if ( textView != null) {
            if (item != null && item.getFlat() != null) {
                System.out.println("flat:" +item.getFlat());
                if (getItem(position).getFlat().getStatus().getUrl().equalsIgnoreCase("unavailable")) {
                    System.out.println(position + " unavailable");
                } else if (getItem(position).getFlat().getStatus().getUrl().equalsIgnoreCase("free")) {
                    System.out.println(position + " free");
                    if (getItem(position).getFlat().getDiscount().equals("false")) {
                        textView.setTextColor(Color.BLACK);
                        textView.setBackgroundColor(Color.GREEN);
                        textView.setText(getItem(position).getFlat().getRoomQuantity());
                    } else {
                        textView.setTextColor(Color.WHITE);
                        textView.setBackgroundColor(Color.RED);
                        textView.setText(getItem(position).getFlat().getRoomQuantity());
                    }
                } else if (getItem(position).getFlat().getStatus().getUrl().equalsIgnoreCase("reserve")) {
                    System.out.println(position + " reserve");
                    textView.setTextColor(Color.BLACK);
                    textView.setBackgroundColor(Color.parseColor("#ffe0af"));
                    textView.setText(getItem(position).getFlat().getRoomQuantity());
                }else if (getItem(position).getFlat().getStatus().getUrl().equalsIgnoreCase("sold")){
                    System.out.println(position + " sold");
                    textView.setTextColor(Color.GRAY);
                    textView.setText(getItem(position).getFlat().getRoomQuantity());
                    textView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.flat_bg));
                }
            }
        }
        return convertView;
    }

}