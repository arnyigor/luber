package com.arny.lubereckiy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.GridViewItem;

import java.util.ArrayList;
public class FlatsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<GridViewItem> flats;

    public FlatsAdapter(Context c, ArrayList<GridViewItem> items) {
        mContext = c;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        NewHolder holder = null;
        if (convertView == null) {//if convert view is null then only inflate the row
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.flat_item, viewGroup, false);
            holder = new NewHolder();
            //find views in item row
            holder.textView = (TextView) convertView.findViewById(R.id.tv_info);
            convertView.setTag(holder);
        } else { //otherwise get holder from tag
            holder = (NewHolder) convertView.getTag();
        }
        GridViewItem gridViewItem = flats.get(position);
        if (gridViewItem != null && gridViewItem.getFlat()!=null) {
            Flat flat = gridViewItem.getFlat();
            TextView textView = holder.textView;
            if (textView != null) {
                Local.setCell(position, textView, flat, mContext, flat.getStatus().getUrl());
            }
        }

        return convertView;
    }

    public static class NewHolder {
        TextView textView;
    }

}