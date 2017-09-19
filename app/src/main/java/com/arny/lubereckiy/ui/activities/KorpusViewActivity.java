package com.arny.lubereckiy.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.KorpusData;
import com.arny.lubereckiy.models.KorpusSection;
import com.google.gson.JsonArray;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.json.JSONArray;

import java.util.List;

public class KorpusViewActivity extends AppCompatActivity {
    private TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korpus_view);
        tvData = (TextView) findViewById(R.id.tv_data);
        Intent intent = getIntent();
        if (intent != null) {
            String object = intent.getStringExtra("object");
            String id = intent.getStringExtra("id");
            Observable<KorpusData> korpusDataObservable = Local.loadKorpus(object, id);
            Observable<KorpusSection> korpusSectionObservable = korpusDataObservable.flatMap(korpusData -> Observable.fromIterable(korpusData.getSections()));
            korpusSectionObservable
                    .doOnNext(Local::getFloors)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            System.out::println,
                            Throwable::printStackTrace,
                            () -> System.out.println("Complete"));
        }
    }
}
