package com.arny.lubereckiy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.models.Korpus;
public class GenPlanViewHolder extends BindableViewHolder<Korpus> implements View.OnClickListener {

    private int position;
    private SimpleActionListener simpleActionListener;

    public GenPlanViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindView(Context context, int position, Korpus item, ActionListener actionListener) {
        super.bindView(context, position, item, actionListener);
        this.position = position;
        simpleActionListener = (SimpleActionListener) actionListener;
        TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvTitle.setText(item.getTitle());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_openmap:
                if (simpleActionListener != null) {
                }
                break;
        }
    }

    public interface SimpleActionListener extends ActionListener {
    }
}