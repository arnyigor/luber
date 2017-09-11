package com.arny.lubereckiy.home.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.arny.lubereckiy.models.Pikobject;

import java.util.List;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface StartView extends MvpView {

	void showLoadProgress();

	void hideLoadProgress();

	void showError(String error);

	void setAdapterData(List<Pikobject> data);

    void goToObjectMap(String uri);

    void setFilteredData(List<Pikobject> data);
}
