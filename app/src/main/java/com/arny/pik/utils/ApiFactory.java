package com.arny.pik.utils;


import com.arny.pik.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiFactory {
    private static ApiFactory instance = new ApiFactory();
    private long timeout;
    private HttpLoggingInterceptor.Level level;

    public static ApiFactory getInstance() {
        return instance;
    }

    private ApiFactory() {
    }

    private Retrofit getRetrofit(String baseUrl) {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT);
            logging.setLevel(level);
            httpClient.addNetworkInterceptor(logging);
        } else {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            httpClient.addNetworkInterceptor(logging);
        }
        httpClient.hostnameVerifier((hostname, session) -> true);
        httpClient.connectTimeout(timeout, TimeUnit.SECONDS);
        httpClient.readTimeout(timeout, TimeUnit.SECONDS);
        httpClient.followRedirects(true);
/*        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder();
            builder.header("", "");
            builder.method(original.method(), original.body());
            Request build = builder.build();
            return chain.proceed(build);
        });*/
        httpClient.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        httpClient.networkInterceptors().add(new StethoInterceptor());
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
		        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public <S> S createService(Class<S> serviceClass, String baseUrl) {
        level = HttpLoggingInterceptor.Level.HEADERS;
        timeout = 60;
        return getRetrofit(baseUrl).create(serviceClass);
    }

    public <S> S createService(Class<S> serviceClass, String baseUrl, long timeout, HttpLoggingInterceptor.Level level) {
        this.timeout = timeout;
        this.level = level;
        return getRetrofit(baseUrl).create(serviceClass);
    }


    public static RequestBody createStringRequestBody(String params){
        return RequestBody.create(MediaType.parse("text/json"), params);
    }


}
