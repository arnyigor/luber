package com.arny.lubereckiy.home.view;

import com.arny.lubereckiy.base.view.BaseView;
import com.arny.lubereckiy.models.Pikobject;

import java.util.List;
public interface StartView extends BaseView {

	void setAdapterData(List<Pikobject> data);

    void setFilteredData(List<Pikobject> data);
}
