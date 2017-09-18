package com.arny.lubereckiy.api;

import com.arny.lubereckiy.models.GenPlan;
import io.reactivex.Observable;
import org.json.JSONArray;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.util.List;
public interface PikobjectgenplanService {
    @Headers({
            "Upgrade-Insecure-Requests: 1",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
    })
	@GET(API.API_GENPLAN)
    Observable<List<GenPlan>> getObjectGenPlan(@Path("object") String object);
}
