package com.arny.lubereckiy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.arny.arnylib.adapters.ADBuilder;
import com.arny.arnylib.utils.MathUtils;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.Planing;
import com.bumptech.glide.Glide;
public class FlatDialog extends ADBuilder {
    private final Flat flat;
    private final int floorNum;
    private Context mContext;
    private ImageView imgFlatView;
    private TextView tvFlatStatus;
    private TextView tvFloorNum;
    private TextView tvFlatPrice;
    private TextView tvFlatArea;

    public FlatDialog(Context context, Flat flat, int floorNum) {
        super(context);
        mContext = context;
        this.flat = flat;
        this.floorNum = floorNum;
    }

	@Override
	protected void initUI(View view) {
		imgFlatView = (ImageView) view.findViewById(R.id.img_flat_view);
		tvFlatStatus = (TextView) view.findViewById(R.id.tv_flat_status);
		tvFloorNum = (TextView) view.findViewById(R.id.tv_floor_num);
		tvFlatPrice = (TextView) view.findViewById(R.id.tv_flat_price);
		tvFlatArea = (TextView) view.findViewById(R.id.tv_flat_area);
		Planing planing = flat.getPlaning();
		if (planing != null && planing.getSrcLayout() !=null) {
			Glide.with(mContext.getApplicationContext())
					.load(planing.getSrcLayout())
					.into(imgFlatView);
		}
		tvFlatStatus.setText(flat.getStatus().getTitle());
		tvFloorNum.setText(String.format("%s-й этаж", String.valueOf(floorNum)));
		double price = MathUtils.round(Double.parseDouble(flat.getWholePrice()) / 1000000, 3);
		double area = MathUtils.round(Double.parseDouble(flat.getWholeAreaBti()), 3);
		tvFlatPrice.setText(String.format("%s млн.", String.valueOf(price)));
		tvFlatArea.setText(String.format("%s кв.м.", String.valueOf(area)));
		view.findViewById(R.id.btn_flat_ok).setOnClickListener(v -> mAlertDialog.dismiss());
	}

	@Override
	protected String getTitle() {
		return Local.getFlatRoomCount(flat.getRoomQuantity());
	}

	@Override
	protected View getView() {
		return LayoutInflater.from(getContext()).inflate(R.layout.flat_dialog_layout, null);
	}
}




