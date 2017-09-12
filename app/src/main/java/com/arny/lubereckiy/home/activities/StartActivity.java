package com.arny.lubereckiy.home.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.adapter.ObjectViewHolder;
import com.arny.lubereckiy.home.presenter.StartPresenter;
import com.arny.lubereckiy.home.view.StartView;
import com.arny.lubereckiy.models.Pikobject;

import java.util.List;

public class StartActivity  extends MvpAppCompatActivity implements StartView, SwipeRefreshLayout.OnRefreshListener {
	@InjectPresenter
	StartPresenter mPresenter;
	private RecyclerView recyclerView;
	private SimpleBindableAdapter adapter;
    private List<Pikobject> objects;
    private SwipeRefreshLayout mSwipeRefreshLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
	    initUI();
        if (savedInstanceState == null) {
            mPresenter.loadAllObjects();
        }
    }

	private void initUI() {
        mSwipeRefreshLayout  = (SwipeRefreshLayout) findViewById(R.id.srl_all_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
		recyclerView = (RecyclerView) findViewById(R.id.rv_all_objects);
		recyclerView.setLayoutManager(new GridLayoutManager(this,1, OrientationHelper.VERTICAL,false));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		adapter = new SimpleBindableAdapter<>(this, R.layout.pikobject_list_item, ObjectViewHolder.class);
		adapter.setActionListener(new ObjectViewHolder.SimpleActionListener() {
            @Override
            public void openMap(int position) {
                mPresenter.showObjectMap(objects.get(position));
            }

            @Override
            public void OnItemClickListener(int position, Object Item) {
                mPresenter.viewObjectGenPlan(StartActivity.this,objects.get(position).getUrl());
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
                mPresenter.filterObjects(objects, StartPresenter.FilterObjectsType.minPrice);
                break;
            case R.id.action_filter_default:
                mPresenter.filterObjects(objects, StartPresenter.FilterObjectsType.defaultType);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void showLoadProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
	}

	@Override
	public void hideLoadProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void showError(String error) {
		ToastMaker.toastError(this,error);
	}

    @Override
    public void navigateTo(Intent intent) {
        startActivity(intent);
    }

    @Override
	public void setAdapterData(List<Pikobject> data) {
		adapter.clear();
        objects = data;
        adapter.addAll(objects);
        DroidUtils.runLayoutAnimation(recyclerView,R.anim.layout_animation_fall_down);
	}

    @Override
    public void setFilteredData(List<Pikobject> data) {
        Log.i(StartActivity.class.getSimpleName(), "setFilteredData: data = " +data.get(0));
        adapter.clear();
        adapter.addAll(data);
        DroidUtils.runLayoutAnimation(recyclerView,R.anim.layout_animation_fall_down);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadAllObjects();
    }
}
