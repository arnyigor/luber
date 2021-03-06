package com.arny.pik.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arny.pik.R;
import com.arny.pik.data.Local;
import com.arny.pik.data.models.Flat;
import com.arny.pik.data.models.GridViewItem;
import com.arny.pik.data.models.Planing;
import com.arny.pik.utils.MathUtils;
import com.arny.pik.utils.ToastMaker;
import com.arny.pik.utils.adapters.AbstractDialogBuilder;
import com.bumptech.glide.Glide;

public class FlatDialog extends AbstractDialogBuilder {
    private final Flat flat;
    private final GridViewItem item;
    private Context context;
    private ImageView imgFlatView;
    private TextView tvFlatStatus;
    private TextView tvFloorNum;
    private TextView tvFlatPrice;
    private TextView tvFlatArea;

    public FlatDialog(Context context, GridViewItem item) {
        super(context);
        this.context = context;
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
        view.findViewById(R.id.btn_flat_ok).setOnClickListener(v -> mAlertDialog.dismiss());
        imgFlatView.setOnClickListener(view1 -> {
            Log.i(FlatDialog.class.getSimpleName(), "initUI: flat:" + flat);
            Planing planing = flat.getPlaning();
            if (planing != null && planing.getSrcLayout() != null) {
                Local.viewTouchImage(planing.getSrcLayout(), context);
            } else {
                ToastMaker.toastError(context,"Изображение не найдено");
            }
        });
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
                Glide.with(context.getApplicationContext())
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
                Glide.with(context.getApplicationContext())
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




