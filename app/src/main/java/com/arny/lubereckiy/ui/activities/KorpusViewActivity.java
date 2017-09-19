package com.arny.lubereckiy.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.arny.arnylib.utils.MathUtils;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.KorpusSection;
import com.arny.lubereckiy.ui.graphics.DrawView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class KorpusViewActivity extends AppCompatActivity {
    private ArrayList<Rect> rectangles;
    private FrameLayout tvData;
    private Spinner spinSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korpus_view);
        initUI();
        Intent intent = getIntent();
        if (intent != null) {
            String object = intent.getStringExtra("object");
            String id = intent.getStringExtra("id");
            fillUI(object, id);
        }
    }

    private void initUI() {
        tvData = (FrameLayout) findViewById(R.id.tv_data);
        spinSection = (Spinner) findViewById(R.id.spin_section);
    }

    private void fillUI(String object, String id) {
        Local.getListkorpuses(object, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        korpusSections -> {
                            drawUI(korpusSections);
                        },
                        Throwable::printStackTrace,
                        () -> {

                        });
    }

    @SuppressLint("StaticFieldLeak")
    private void drawUI(List<KorpusSection> korpusSections) {
        new AsyncTask<Void, Void, ArrayList<Rect>>() {
            @Override
            protected ArrayList<Rect> doInBackground(Void... voids) {
                return Local.getKorpusRects(korpusSections.get(MathUtils.randInt(0, korpusSections.size() - 1)));
            }

            @Override
            protected void onPostExecute(ArrayList<Rect> rects) {
                super.onPostExecute(rects);
                rectangles = rects;
                DrawView drawView = new DrawView(KorpusViewActivity.this, rects);
                drawView.setOnTouchListener(drawListener);
                drawView.setBackgroundColor(Color.WHITE);
                tvData.addView(drawView);
            }
        }.execute();
    }

    View.OnTouchListener drawListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float touchX = event.getX();
            float touchY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("Touching down!");
                    for (Rect rect : rectangles) {
                        System.out.println("rec:x" + rect.centerX() + " y:" + rect.centerY());
                        int touchX1 = (int) touchX;
                        int touchY1 = (int) touchY;
                        if (rect.contains(touchX1, touchY1)) {
                            Toast.makeText(KorpusViewActivity.this, "touchX1:" + touchX1 + " touchY1:" + touchY1, Toast.LENGTH_SHORT).show();
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














