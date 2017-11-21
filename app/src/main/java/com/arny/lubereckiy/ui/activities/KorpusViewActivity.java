package com.arny.lubereckiy.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.arny.arnylib.adapters.OGArrayAdapter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.arnylib.utils.Utility;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.adapter.FlatDialog;
import com.arny.lubereckiy.adapter.FlatsAdapter;
import com.arny.lubereckiy.api.API;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.Flat;
import com.arny.lubereckiy.models.GridViewItem;
import com.arny.lubereckiy.models.KorpusSection;
import com.arny.lubereckiy.models.Status;

import java.util.List;

public class KorpusViewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinSection;
    private List<KorpusSection> sections;
    private SectionAdapter spinSectionsAdapter;
    private ProgressBar progressDraw;
    private String korpus;
    private String objectTitle;
    private GridView gridView;
    private FlatsAdapter adapter;
    private ListView lvFloors;
    private LVFloorsAdapter lvFloorsAdapter;

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
        adapter.clear();
        adapter.addAll( Local.getSectionFlatsArray(sections.get(position)));
        gridView.setNumColumns( sections.get(position).getMaxFlatsOnFloor()+1);
//        lvFloorsAdapter.clear();
//        lvFloorsAdapter.addAll(Local.getSectionFloorsArray(sections.get(position)));
        setTitle(objectTitle + " " + korpus + " " + sections.get(position).getName());
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
        progressDraw = findViewById(R.id.progress_draw);
        gridView = findViewById(R.id.gridSection);
        lvFloors = findViewById(R.id.lv_floors);
        spinSection = findViewById(R.id.spin_section);
        spinSectionsAdapter = new SectionAdapter(this, R.layout.section_spinner_item);
        spinSection.setAdapter(spinSectionsAdapter);
        spinSection.setOnItemSelectedListener(this);
    }

    private void fillUI(String url, String id) {
        Utility.mainThreadObservable(API.getListkorpuses(url, id))
                .doOnSubscribe(disposable -> {
                    progressDraw.setVisibility(View.VISIBLE);
                    spinSection.setVisibility(View.GONE);
                })
                .subscribe(
                        this::setUIByData,
                        throwable -> {
                            progressDraw.setVisibility(View.GONE);
                            spinSection.setVisibility(View.GONE);
                            throwable.printStackTrace();
                            ToastMaker.toastError(this, "Ошибка:" + throwable.getMessage());
                        });
    }

    private void setUIByData(List<KorpusSection> korpusSections) {
        this.sections = korpusSections;
        setTitle(objectTitle + " " + korpus + " " + sections.get(0).getName());
        spinSectionsAdapter.addAll(sections);
        adapter = new FlatsAdapter(this,R.layout.flat_item);
        adapter.clear();
        adapter.addAll( Local.getSectionFlatsArray(sections.get(0)));
        gridView.setAdapter(adapter);
        gridView.setNumColumns( sections.get(0).getMaxFlatsOnFloor()+1);
	    progressDraw.setVisibility(View.GONE);
	    spinSection.setVisibility(View.VISIBLE);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            GridViewItem item = adapter.getItem(position);
            if (item != null && item.getFlat() != null) {
                Flat flat = item.getFlat();
	            Status status = flat.getStatus();
	            if (status != null) {
		            String url = status.getUrl();
		            if (url.equals("sold")) {
			            ToastMaker.toastError(KorpusViewActivity.this, "Продана");
			            return;
		            }
		            if (!url.equals("unavailable")) {
			            new FlatDialog(this, item).show();
		            } else {
			            ToastMaker.toastError(KorpusViewActivity.this, "Нет данных");
		            }
	            }else{
		            if (item.getType().equals(Local.GridItemType.floorNum)) {
                        new FlatDialog(this, item).show();
			            return;
		            }
		            ToastMaker.toastError(KorpusViewActivity.this, "Нет данных");
	            }
            }
        });

    }

}














