package com.arny.lubereckiy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.Utility;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.models.Korpus;
public class KorpusesViewHolder extends BindableViewHolder<Korpus> implements View.OnClickListener {

    private int position;
    private SimpleActionListener simpleActionListener;
    private TextView tvTitle;
    private TextView tvDateOfMovingIn;
    private TextView tvBusy;
    private TextView tvFree;
    private TextView tvFinishing;
    private TextView tvSold;
    private TextView tvMinprice1;
    private TextView tvMinprice2;
    private TextView tvMinprice3;

    public KorpusesViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindView(Context context, int position, Korpus item, ActionListener actionListener) {
        super.bindView(context, position, item, actionListener);
        this.position = position;
        simpleActionListener = (SimpleActionListener) actionListener;
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvTitle.setText(item.getTitle());
        tvDateOfMovingIn = (TextView) itemView.findViewById(R.id.tv_dateOfMovingIn);
        tvDateOfMovingIn.setText(String.format("Заселение:%s", Utility.empty(item.getDateOfMovingIn()) ? "Нет данных" : item.getDateOfMovingIn()));
        tvBusy = (TextView) itemView.findViewById(R.id.tv_busy);
        tvBusy.setText(String.format("занято:%s", item.getBusy()));
        tvFree = (TextView) itemView.findViewById(R.id.tv_free);
        tvFree.setText(String.format("свободно:%s", item.getFree()));
        tvFinishing = (TextView) itemView.findViewById(R.id.tv_finishing);
        String finishing = item.getFinishing();
        tvFinishing.setText(finishing != null ? finishing.equals("1") ? "С отделкой" : "без отделки" : "Нет данных");
        tvSold = (TextView) itemView.findViewById(R.id.tv_sold);
        tvSold.setText(String.format("Продано:%s", item.getSold()));
        tvMinprice1 = (TextView) itemView.findViewById(R.id.tv_minprice_1);
        double price1 = MathUtils.round(Double.parseDouble(item.getMinprice1()!=null?item.getMinprice1():"0") / 1000000, 3);
        double price2 = MathUtils.round(Double.parseDouble(item.getMinprice2()!=null?item.getMinprice2():"0") / 1000000, 3);
        double price3 = MathUtils.round(Double.parseDouble(item.getMinprice3()!=null?item.getMinprice3():"0") / 1000000, 3);
        tvMinprice1.setText(String.format("1к:%s млн",String.valueOf(price1)));
        tvMinprice2 = (TextView) itemView.findViewById(R.id.tv_minprice_2);
        tvMinprice2.setText(String.format("2к:%s млн",String.valueOf(price2)));
        tvMinprice3 = (TextView) itemView.findViewById(R.id.tv_minprice_3);
        tvMinprice3.setText(String.format("3к:%s млн",String.valueOf(price3)));
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