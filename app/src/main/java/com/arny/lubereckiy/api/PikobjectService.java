package com.arny.lubereckiy.api;

import com.arny.lubereckiy.models.Pikobject;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;
public interface PikobjectService {
	@GET(API.API_ALL_OBJECTS)
	Call<List<Pikobject>> getObjects();
}
