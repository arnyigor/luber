package com.arny.pik.api;

import com.arny.pik.models.GenPlan;
import com.arny.pik.models.KorpusData;
import com.arny.pik.models.Pikobject;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
public interface PikApiService {

    @Headers({
            "Upgrade-Insecure-Requests: 1",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
    })
    @GET(API.API_SINGLE_CORPUS)
    Observable<KorpusData> getKorpus(
            @Path("object") String url,
            @Query("id") String id);

    @Headers({
            "Upgrade-Insecure-Requests: 1",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
    })
    @GET(API.API_GENPLAN)
    Observable<List<GenPlan>> getObjectGenPlan(@Path("object") String object);

    @Headers({
            "Upgrade-Insecure-Requests: 1",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
    })
    @GET(API.API_ALL_OBJECTS)
    Observable<List<Pikobject>> getObjects();
}
