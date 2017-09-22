package com.arny.lubereckiy.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.arny.arnylib.adapters.OGArrayAdapter;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.Stopwatch;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.adapter.CustomAdapter;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.GridViewItem;
import com.arny.lubereckiy.models.KorpusSection;
import com.arny.lubereckiy.ui.graphics.DrawView;
import com.arny.lubereckiy.ui.graphics.FlatView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class KorpusViewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinSection;
	private DrawView drawView;
    private List<KorpusSection> sections;
    private SectionAdapter spinSectionsAdapter;
    private ProgressBar progressDraw;
    private String korpus;
    private String objectTitle;
    private GridView gridView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korpus_view);
        initUI();
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            String id = intent.getStringExtra("id");
            objectTitle = intent.getStringExtra("object_title");
            korpus = intent.getStringExtra("korpus");
            fillUI(url, id);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        adapter = new CustomAdapter(this, Local.getSectionFlatsArray(sections.get(position)));
        gridView.setAdapter(adapter);
        gridView.setNumColumns(sections.get(position).getMaxFlatsOnFloor());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SectionAdapter extends OGArrayAdapter<KorpusSection> {
        public SectionAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        protected String getObjectName(KorpusSection obj) {
            return obj.getName();
        }
    }


    private void initUI() {
        progressDraw = (ProgressBar) findViewById(R.id.progress_draw);
        gridView = (GridView) findViewById(R.id.gridSection);
        spinSection = (Spinner) findViewById(R.id.spin_section);
        spinSectionsAdapter = new SectionAdapter(this, R.layout.section_spinner_item);
        spinSection.setAdapter(spinSectionsAdapter);
        spinSection.setOnItemSelectedListener(this);
    }

    private void fillUI(String url, String id) {
        Local.getListkorpuses(url, id)
//                .map(Local::filterEmptySections)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDraw.setVisibility(View.VISIBLE);
                    spinSection.setVisibility(View.GONE);
                })
                .subscribe(
                        this::setUIByData,
                        throwable -> ToastMaker.toastError(this, throwable.getMessage()));
    }

    private void setUIByData(List<KorpusSection> korpusSections) {
        this.sections = korpusSections;
        setTitle(objectTitle + " " + korpus);
        spinSectionsAdapter.addAll(sections);
        adapter = new CustomAdapter(this, Local.getSectionFlatsArray(sections.get(0)));
        gridView.setAdapter(adapter);
        gridView.setNumColumns(sections.get(0).getMaxFlatsOnFloor());
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            GridViewItem item = adapter.getItem(position);
            if (item != null && item.getFlat() != null) {
                Flat flat = item.getFlat();
                if (!flat.getStatus().equals("unavailable")) {
                    String wholePrice = flat.getWholePrice();
                    double price = MathUtils.round(Double.parseDouble(wholePrice) / 1000000, 3);
                    String format = String.format("%s млн. Площадь:%f кв.м. ", String.valueOf(price), Float.parseFloat(flat.getWholeAreaBti()));
                    ToastMaker.toastSuccess(KorpusViewActivity.this, format);
                }
            }
        });
        progressDraw.setVisibility(View.GONE);
        spinSection.setVisibility(View.VISIBLE);
    }

    @SuppressLint("StaticFieldLeak")
    private void drawUI(KorpusSection section) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
//	    drawView = new DrawView(KorpusViewActivity.this, section, width, height);
//	    drawView.setBackgroundColor(Color.argb(255,255,255,255));
//        drawView.setOnTouchListener(drawListener);
        System.out.println("drawUI:" + stopwatch.getElapsedTimeMili() + "ms");
    }

    View.OnTouchListener drawListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float touchX = event.getRawX();
            float touchY = event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("touch:x" + touchX + " y:" + touchY);
                    for (FlatView flatView : drawView.getFlats()) {
                        int touchX1 = (int) touchX;
                        int touchY1 = (int) touchY;
                        if (flatView.contains(touchX1, touchY1)) {
                            System.out.println("rec:x" + flatView.centerX() + " y:" + flatView.centerY());
                            Toast.makeText(KorpusViewActivity.this, flatView.getFlat().getWholePrice(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }
            return true;
        }
    };

}













