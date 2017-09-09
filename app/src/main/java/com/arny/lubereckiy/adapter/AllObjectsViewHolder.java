package com.arny.lubereckiy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.models.Pikobject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
public class AllObjectsViewHolder extends BindableViewHolder<Pikobject> implements View.OnClickListener {

	private int position;
    private SimpleActionListener simpleActionListener;

    public AllObjectsViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindView(Context context, int position, Pikobject item, ActionListener actionListener) {
        super.bindView(context,position, item, actionListener);
        this.position = position;
        simpleActionListener = (SimpleActionListener) actionListener;
	    TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
	    tvTitle.setText(item.getName());
	    TextView tvInfo = (TextView) itemView.findViewById(R.id.tv_info);
	    tvInfo.setText(String.format("От:%d \n Статус:%s", item.getMinPrice(), item.getSticker().getText()));
	    ImageView imObjPreview = (ImageView) itemView.findViewById(R.id.iv_object_preview);
	    Glide.with(context)
			    .load(item.getNewDesign().getImageMap())
			    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
			    .into(imObjPreview);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}
	}

	public interface SimpleActionListener extends ActionListener {
    }
}