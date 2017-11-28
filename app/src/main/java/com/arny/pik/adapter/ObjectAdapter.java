package com.arny.pik.adapter;

import android.content.Context;
import android.view.View;
import com.arny.arnylib.adapters.FilterBindableAdapter;
import com.arny.pik.models.Pikobject;
public class ObjectAdapter extends FilterBindableAdapter<Pikobject, ObjectViewHolder> {
    private ObjectViewHolder.SimpleActionListener simpleActionListener;
    private int layout;

    public ObjectAdapter(Context context, int layout, ObjectViewHolder.SimpleActionListener simpleActionListener) {
        this.context = context;
        this.simpleActionListener = simpleActionListener;
        this.layout = layout;
    }

    @Override
    protected int layoutId(int type) {
        return layout;
    }

    @Override
    protected void onBindItemViewHolder(ObjectViewHolder viewHolder, final int position, int type,boolean issel) {
        viewHolder.bindView(context, position, getItem(position), simpleActionListener);
    }

    @Override
    protected ObjectViewHolder viewHolder(View view, int type) {
        return new ObjectViewHolder(view);
    }

    @Override
    protected String itemToString(Pikobject item) {
        return item.getName();
    }
}