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
import com.arny.lubereckiy.models.GridViewItem;
import com.arny.lubereckiy.models.Planing;
import com.bumptech.glide.Glide;
public class FlatDialog extends ADBuilder {
    private final Flat flat;
    private final GridViewItem item;
    private Context mContext;
    private ImageView imgFlatView;
    private TextView tvFlatStatus;
    private TextView tvFloorNum;
    private TextView tvFlatPrice;
    private TextView tvFlatArea;

    public FlatDialog(Context context, GridViewItem item) {
        super(context);
        mContext = context;
        this.flat = item.getFlat();
        this.item = item;
    }

	@Override
	protected void initUI(View view) {
		imgFlatView = view.findViewById(R.id.img_flat_view);
		tvFlatStatus = view.findViewById(R.id.tv_flat_status);
		tvFloorNum = view.findViewById(R.id.tv_floor_num);
		tvFlatPrice = view.findViewById(R.id.tv_flat_price);
		tvFlatArea = view.findViewById(R.id.tv_flat_area);
        view.findViewById(R.id.btn_flat_ok).setOnClickListener(v -> getDialog().dismiss());

	}

	@Override
	protected String getTitle() {
        if (item.getType().equals(Local.GridItemType.flat)) {
            return Local.getFlatRoomCount(flat.getRoomQuantity());
        }else{
            return String.format("%s-й этаж", String.valueOf(item.getFloorNum()));
        }
	}

	@Override
	protected View getView() {
		return LayoutInflater.from(getContext()).inflate(R.layout.flat_dialog_layout, null);
	}

    @Override
    protected void updateDialogView() {
        if (item.getType().equals(Local.GridItemType.flat)) {
            Planing planing = flat.getPlaning();
            if (planing != null && planing.getSrcLayout() !=null) {
                Glide.with(mContext.getApplicationContext())
                        .load(planing.getSrcLayout())
                        .into(imgFlatView);
            }
            tvFlatStatus.setText(flat.getStatus().getTitle());
            tvFloorNum.setText(String.format("%s-й этаж", String.valueOf(item.getFloorNum())));
            double price = MathUtils.round(Double.parseDouble(flat.getWholePrice()) / 1000000, 3);
            double area = MathUtils.round(Double.parseDouble(flat.getWholeAreaBti()), 3);
            tvFlatPrice.setText(String.format("%s млн.", String.valueOf(price)));
            tvFlatArea.setText(String.format("%s кв.м.", String.valueOf(area)));
        }else{
            Planing planing = flat.getPlaning();
            if (planing != null && planing.getSrcLayout() !=null) {
                Glide.with(mContext.getApplicationContext())
                        .load(planing.getSrcLayout())
                        .into(imgFlatView);
            }
            tvFlatStatus.setVisibility(View.GONE);
            tvFlatPrice.setVisibility(View.GONE);
            tvFlatArea.setVisibility(View.GONE);
            tvFloorNum.setVisibility(View.GONE);
        }
    }
}




