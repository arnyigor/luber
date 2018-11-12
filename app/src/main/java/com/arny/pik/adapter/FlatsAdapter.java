package com.arny.pik.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arny.pik.R;
import com.arny.pik.data.Local;
import com.arny.pik.data.models.GridViewItem;
import com.arny.pik.utils.adapters.AbstractArrayAdapter;

public class FlatsAdapter extends AbstractArrayAdapter<GridViewItem> {

    public FlatsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    protected String getItemTitle(GridViewItem item) {
        return item.getFlat().toString();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.flat_item, parent, false);
        TextView text = convertView.findViewById(R.id.info);
        drawText(position, text);
        return convertView;
    }

    private void drawText(int position, TextView textView) {
        if ( textView != null) {
            if (getItem(position) != null ) {
                if (getItem(position).getType() == Local.GridItemType.floorNum) {
                    textView.setTextColor(Color.BLACK);
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setText(String.format("%s", String.valueOf(getItem(position).getFloorNum())));
                }else
                if (getItem(position).getFlat() != null) {
                    if (getItem(position).getFlat().getStatus().getUrl().equalsIgnoreCase("unavailable")) {
                        textView.setBackgroundColor(Color.parseColor("#e7e7e7"));
                    } else if (getItem(position).getFlat().getStatus().getUrl().equalsIgnoreCase("free")) {
                        if (getItem(position).getFlat().getDiscount().equals("false")) {
                            textView.setTextColor(Color.BLACK);
                            textView.setBackgroundColor(Color.parseColor("#00c127"));
                            textView.setText(getItem(position).getFlat().getRoomQuantity());
                        } else {
                            textView.setTextColor(Color.WHITE);
                            textView.setBackgroundColor(Color.RED);
                            textView.setText(getItem(position).getFlat().getRoomQuantity());
                        }
                    } else if (getItem(position).getFlat().getStatus().getUrl().equalsIgnoreCase("reserve")) {
                        textView.setTextColor(Color.BLACK);
                        textView.setBackgroundColor(Color.parseColor("#ffe0af"));
                        textView.setText(getItem(position).getFlat().getRoomQuantity());
                    }else if (getItem(position).getFlat().getStatus().getUrl().equalsIgnoreCase("sold")){
                    textView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.flat_bg));
                    }
                }
            }
        }
    }

}







