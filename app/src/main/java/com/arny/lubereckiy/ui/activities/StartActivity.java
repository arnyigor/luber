package com.arny.lubereckiy.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.view.Menu;
import android.view.MenuItem;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.adapter.ObjectViewHolder;
import com.arny.lubereckiy.common.Local;
import com.arny.lubereckiy.models.Pikobject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class StartActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private SimpleBindableAdapter adapter;
    private List<Pikobject> objects;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        loadPlans();
    }

    private void loadPlans() {
//		if (!DroidUtils.isConnected(this)) {
//			ToastMaker.toastError(this,"Нет интернет соединения");
// 			return;
//		}
        Local.loadPikObjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mSwipeRefreshLayout.setRefreshing(true))
                .subscribe(
                        pikobjects -> {
                            objects = pikobjects;
                            setAdapterData(pikobjects, false);
                        },
                        throwable -> {
                            mSwipeRefreshLayout.setRefreshing(false);
                            ToastMaker.toastError(StartActivity.this, throwable.getMessage());
                        },
                        () -> mSwipeRefreshLayout.setRefreshing(false));
    }

    private void initUI() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_all_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_all_objects);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, OrientationHelper.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new SimpleBindableAdapter<>(this, R.layout.pikobject_list_item, ObjectViewHolder.class);
        adapter.setActionListener(new ObjectViewHolder.SimpleActionListener() {
            @Override
            public void openMap(int position) {
                Local.showObjectMap(StartActivity.this, objects.get(position));
            }

            @Override
            public void OnItemClickListener(int position, Object Item) {
				Local.viewObjectGenPlan(StartActivity.this, objects.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_filter_min_price:
                List<Pikobject> pikobjects = Local.filterObjects(objects, Local.FilterObjectsType.minPrice);
                setAdapterData(pikobjects, false);
                break;
            case R.id.action_filter_default:
                List<Pikobject> pikobjects1 = Local.filterObjects(objects, Local.FilterObjectsType.defaultType);
                objects = pikobjects1;
                setAdapterData(pikobjects1, false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoadProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void hideLoadProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showError(String error) {
        ToastMaker.toastError(this, error);
    }

    public void navigateTo(Intent intent) {
        startActivity(intent);
    }

    private void setAdapterData(List<Pikobject> data, boolean update) {
        Local.setAdapterData(adapter, data, recyclerView, update);
    }

    @Override
    public void onRefresh() {
        loadPlans();
    }
}
