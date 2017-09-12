package com.arny.lubereckiy.base.view;

import android.content.Intent;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface BaseView extends MvpView {
    void showLoadProgress();

    void hideLoadProgress();

    void showError(String error);

    void navigateTo(Intent intent);
}
