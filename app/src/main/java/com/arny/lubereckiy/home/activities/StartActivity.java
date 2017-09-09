package com.arny.lubereckiy.home.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.adapter.AllObjectsViewHolder;
import com.arny.lubereckiy.home.presenter.StartPresenter;
import com.arny.lubereckiy.home.view.StartView;
import com.arny.lubereckiy.models.Pikobject;

import java.util.List;

public class StartActivity  extends MvpAppCompatActivity implements StartView {
	@InjectPresenter
	StartPresenter mPresenter;
	private RecyclerView recyclerView;
	private ProgressBar progressLoad;
	private TextView tvLoadInfo;
	private SimpleBindableAdapter adapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
	    initUI();
		mPresenter.loadAllObjects();
    }

	private void initUI() {
		recyclerView = (RecyclerView) findViewById(R.id.rv_all_objects);
		recyclerView.setLayoutManager(new GridLayoutManager(this,2, OrientationHelper.VERTICAL,false));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		progressLoad = (ProgressBar) findViewById(R.id.progress_load);
		tvLoadInfo = (TextView) findViewById(R.id.tv_load_info);
		adapter = new SimpleBindableAdapter<>(this, R.layout.pikobject_list_item, AllObjectsViewHolder.class);
		adapter.setActionListener((AllObjectsViewHolder.SimpleActionListener) (position, Item) ->
						Log.i(StartActivity.class.getSimpleName(), "initAdapter: item = " + position));
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void showLoadProgress() {
		progressLoad.setVisibility(View.VISIBLE);
		tvLoadInfo.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideLoadProgress() {
		progressLoad.setVisibility(View.GONE);
		tvLoadInfo.setVisibility(View.GONE);
	}

	@Override
	public void showError(String error) {
		ToastMaker.toastError(this,error);
	}

	@Override
	public void setAdapterData(List<Pikobject> data) {
		adapter.clear();
		adapter.addAll(data);
	}
}
