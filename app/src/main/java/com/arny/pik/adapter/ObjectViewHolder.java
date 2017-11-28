package com.arny.pik.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.arnylib.utils.DateTimeUtils;
import com.arny.arnylib.utils.MathUtils;
import com.arny.pik.R;
import com.arny.pik.models.Img;
import com.arny.pik.models.Pikobject;
import com.bumptech.glide.Glide;
public class ObjectViewHolder extends BindableViewHolder<Pikobject> implements View.OnClickListener {

	private int position;
	private SimpleActionListener simpleActionListener;

	public ObjectViewHolder(View itemView) {
		super(itemView);
	}

	@SuppressLint("DefaultLocale")
	@Override
	public void bindView(Context context, int position, Pikobject item, ActionListener actionListener) {
		super.bindView(context, position, item, actionListener);
		this.position = position;
		simpleActionListener = (SimpleActionListener) actionListener;
		TextView tvTitle = itemView.findViewById(R.id.tv_title);
		String name = item.getName();
		tvTitle.setText(name);
		TextView tvInfo = itemView.findViewById(R.id.tv_info);
		Integer nearSettlementDate = item.getNearSettlementDate() == null ? -1 : item.getNearSettlementDate();
		long ts = (long) nearSettlementDate * 1000;
		String dateTime = nearSettlementDate == -1 ? "-" : DateTimeUtils.getDateTime(ts, "dd MMM yyyy");
		String s = "заселение:" + dateTime;
		String status = item.getSticker() == null ? s :
				"\n" + item.getSticker().getText() + "\n" + s;
		int minPrice = item.getMinPrice();
		int all = item.getFlatsAll();
		int free = item.getFlatsFree();
		int reserve = item.getFlatsReserved();
		double price = MathUtils.round((double) minPrice / 1000000, 3);
		tvInfo.setText(String.format("%s\nОт:%s млн.%s \n[Все:%d/свободно:%d/резерв:%d]", item.getLocation(), String.valueOf(price), status, all, free, reserve));
		ImageView imObjPreview = itemView.findViewById(R.id.iv_object_preview);
        Img img = item.getImg();
        if (img != null) {
            String imageMap = img.getMain();
            Glide.with(context.getApplicationContext())
                    .load(imageMap)
                    .into(imObjPreview);
        }
		itemView.findViewById(R.id.imgbtn_openmap).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imgbtn_openmap:
				if (simpleActionListener != null) {
					simpleActionListener.openMap(position);
				}
				break;
		}
	}

	public interface SimpleActionListener extends ActionListener {
		void openMap(int position);
	}
}