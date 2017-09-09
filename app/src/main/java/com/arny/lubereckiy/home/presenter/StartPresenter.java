package com.arny.lubereckiy.home.presenter;

import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.network.ApiFactory;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.lubereckiy.api.API;
import com.arny.lubereckiy.api.PikobjectService;
import com.arny.lubereckiy.home.view.StartView;
import com.arny.lubereckiy.models.Pikobject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
@InjectViewState
public class StartPresenter extends MvpPresenter<StartView> {
	public PikobjectService pikobjectService = ApiFactory.getInstance().createService(PikobjectService.class, API.BASE_API_URL);
	public void loadAllObjects(){
		getViewState().showLoadProgress();
		pikobjectService.getObjects().enqueue(new Callback<List<Pikobject>>() {
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
				getViewState().setAdapterData(response.body());
			}

			@Override
			public void onFailure(Call<List<Pikobject>> call, Throwable t) {
				getViewState().hideLoadProgress();
				getViewState().showError("Error:" + t.getMessage());
			}
		});
	}
}
