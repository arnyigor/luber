package com.arny.lubereckiy.object.presenter;

import android.util.Log;
import com.arellomobile.mvp.MvpPresenter;
import com.arny.arnylib.network.ApiFactory;
import com.arny.arnylib.network.NetworkService;
import com.arny.lubereckiy.api.API;
import com.arny.lubereckiy.api.PikobjectgenplanService;
import com.arny.lubereckiy.models.GenPlan;
import com.arny.lubereckiy.object.view.ObjectGenPlanView;
import org.json.JSONArray;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
public class ObjectGenplanPresenter extends MvpPresenter<ObjectGenPlanView> {

    public void loadAllObjects(String object){
        Call<List<GenPlan>> objectGenPlan = ApiFactory.getInstance().createService(PikobjectgenplanService.class, API.BASE_URL).getObjectGenPlan(object.substring(1));
        objectGenPlan.enqueue(new Callback<List<GenPlan>>() {
            @Override
            public void onResponse(Call<List<GenPlan>> call, Response<List<GenPlan>> response) {
                Log.i(ObjectGenplanPresenter.class.getSimpleName(), "onResponse: response = " + response);
                Log.i(ObjectGenplanPresenter.class.getSimpleName(), "onResponse: response = " + response.body());
            }

            @Override
            public void onFailure(Call<List<GenPlan>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
