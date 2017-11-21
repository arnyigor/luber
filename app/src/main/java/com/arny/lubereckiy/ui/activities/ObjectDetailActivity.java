package com.arny.lubereckiy.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.adapter.KorpusesViewHolder;
import com.arny.lubereckiy.api.API;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.GenPlan;
import com.arny.lubereckiy.models.Korpus;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class ObjectDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private SimpleBindableAdapter adapter;
    private List<Korpus> objects;
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detail);
        initUI();
        if (getIntent() != null) {
            Intent intent = getIntent();
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");
            setTitle(title);
            loadObject();
        }
    }

    private void loadObject() {
        if (url != null) {
            API.loadGenplan(url)
                    .flatMap((Function<List<GenPlan>, ObservableSource<List<Korpus>>>) genPlans -> Observable.create(e -> {
                        e.onNext(genPlans.get(0).getData().getKorpuses());
                        e.onComplete();
                    }))
                    .map(korpuses -> {
                        List<Korpus> list = new ArrayList<>();
                        for (Korpus korpus : korpuses) {
                            if (korpus.getType().equals("flats")) {
                                list.add(korpus);
                            }
                        }
                        return list;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> mSwipeRefreshLayout.setRefreshing(true))
                    .subscribe(korpuses -> {
                                objects = korpuses;
                                setAdapterData(korpuses);
                            },
                            throwable -> {
                                mSwipeRefreshLayout.setRefreshing(false);
                                ToastMaker.toastError(ObjectDetailActivity.this, throwable.getMessage());
                            },
                            () -> mSwipeRefreshLayout.setRefreshing(false));
        }
    }

    private void initUI() {
        mSwipeRefreshLayout = findViewById(R.id.srl_genplan_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.rv_object_genplans);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, OrientationHelper.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new SimpleBindableAdapter<>(this, R.layout.genplan_item, KorpusesViewHolder.class);
        adapter.setActionListener((KorpusesViewHolder.SimpleActionListener) (position, Item) -> {
            Local.viewKorpus(ObjectDetailActivity.this, objects.get(position),url,title);
        });
        recyclerView.setAdapter(adapter);
    }

    public void showLoadProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void hideLoadProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void showError(String error) {
        ToastMaker.toastError(this, error);
    }

    public void navigateTo(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadObject();
    }

    public void setAdapterData(List<Korpus> data) {
        objects = data;
        Local.setAdapterData(adapter, data, recyclerView, false);
    }

    public void setFilteredData(List<Korpus> data) {

    }
}
