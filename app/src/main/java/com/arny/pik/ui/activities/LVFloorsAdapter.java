package com.arny.pik.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.arny.arnylib.adapters.OGArrayAdapter;
import com.arny.pik.R;
import com.arny.pik.models.Floor;
class LVFloorsAdapter extends OGArrayAdapter<Floor> {

    LVFloorsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    protected String getObjectName(Floor obj) {
        return String.valueOf(obj.getNum());
    }

    private class ViewHolder {
        TextView textView;
    }

    @Override
    public View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        // ViewHolder буферизирует оценку различных полей шаблона элемента
        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
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
        holder.textView.setText(getObjectName(getItem(position)));
        return rowView;
    }
}
