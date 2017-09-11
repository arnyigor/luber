package com.arny.lubereckiy.home.presenter;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.network.ApiFactory;
import com.arny.lubereckiy.api.API;
import com.arny.lubereckiy.api.PikobjectService;
import com.arny.lubereckiy.home.view.StartView;
import com.arny.lubereckiy.models.Pikobject;
import com.google.common.collect.Collections2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@InjectViewState
public class StartPresenter extends MvpPresenter<StartView> {

    public enum FilterObjectsType {
        defaultType,
        minPrice
    }
	public void loadAllObjects(){
		getViewState().showLoadProgress();
        ApiFactory.getInstance().createService(PikobjectService.class, API.BASE_API_URL).getObjects().enqueue(new Callback<List<Pikobject>>() {
			@Override
			public void onResponse(Call<List<Pikobject>> call, Response<List<Pikobject>> response) {
				Log.i(StartPresenter.class.getSimpleName(), "onResponse: call = " + call);
				Log.i(StartPresenter.class.getSimpleName(), "onResponse: body = " + response);
				if (response == null) {
					getViewState().hideLoadProgress();
					getViewState().showError("Error response");
					return;
				}
				if (response.code() != 200) {
					getViewState().hideLoadProgress();
					getViewState().showError("Error:" + response.message());
					return;
				}
                getViewState().hideLoadProgress();
                List<Pikobject> body = response.body();
                getViewState().setAdapterData(body);
			}

			@Override
			public void onFailure(Call<List<Pikobject>> call, Throwable t) {
			    t.printStackTrace();
                getViewState().hideLoadProgress();
				getViewState().showError("Error:" + t.getMessage());
			}
		});
	}
	public void showObjectMap(Pikobject pikobject){
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(pikobject.getLatitude()), Double.parseDouble(pikobject.getLongitude()));
        getViewState().goToObjectMap(uri);
    }


    public void filterObjects(List<Pikobject> objects,FilterObjectsType type){
        switch (type) {
            case minPrice:
                Collections.sort(objects, (o1, o2) -> o1.getMinPrice().compareTo(o2.getMinPrice()));
                getViewState().setFilteredData(objects);
                break;
            case defaultType:
                Log.i(StartPresenter.class.getSimpleName(), "filterObjects: objects = " +objects.get(0));
                getViewState().setFilteredData(objects);
                break;
        }
    }
}
