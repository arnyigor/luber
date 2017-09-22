package com.arny.lubereckiy.api;

import com.arny.lubereckiy.models.KorpusData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface PikKorpusService {
    @Headers({
            "Upgrade-Insecure-Requests: 1",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36"
    })
    @GET(API.API_SINGLE_CORPUS)
    Observable<KorpusData> getKorpus(
            @Path("object") String url,
            @Query("id") String id);
}
