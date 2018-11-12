package com.arny.pik.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arny.pik.R;
import com.arny.pik.data.models.Floor;
import com.arny.pik.utils.adapters.AbstractArrayAdapter;

class LVFloorsAdapter extends AbstractArrayAdapter<Floor> {

    LVFloorsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    private class ViewHolder {
        TextView textView;
    }

    @Override
    protected String getItemTitle(Floor item) {
        return String.valueOf(item.getNum());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.floor_item, null, true);
            holder = new ViewHolder();
            holder.textView = rowView.findViewById(R.id.tv_floor);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.textView.setText(getItemTitle(getItem(position)));
        return rowView;
    }
}
