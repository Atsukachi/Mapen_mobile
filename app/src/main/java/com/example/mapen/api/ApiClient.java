package com.example.mapen.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String url = "http://172.10.12.153/mapen/index.php/";
    public static final String imageUrl = "http://172.10.12.153/mapen/assets/images/users/";
    private static ApiClient clientObject;
    private static Retrofit retrofit;

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance()
    {
        if(clientObject == null)
            clientObject = new ApiClient();
        return clientObject;
    }

    public MapenService getApi() {
        return retrofit.create(MapenService.class);
    }
}