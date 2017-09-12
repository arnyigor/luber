package com.arny.lubereckiy.object.ui;

import android.content.Intent;
import android.os.Bundle;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.lubereckiy.R;
import com.arny.lubereckiy.object.presenter.ObjectGenplanPresenter;
import com.arny.lubereckiy.object.view.ObjectGenPlanView;

public class ObjectDetailActivity extends MvpAppCompatActivity implements ObjectGenPlanView {
    @InjectPresenter
    ObjectGenplanPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_detail);
        if (getIntent() != null) {
            Intent intent = getIntent();
            String url =intent.getStringExtra("url");
            mPresenter.loadAllObjects(url);
        }
    }

    @Override
    public void showLoadProgress() {

    }

    @Override
    public void hideLoadProgress() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void navigateTo(Intent intent) {
        startActivity(intent);
    }
}
