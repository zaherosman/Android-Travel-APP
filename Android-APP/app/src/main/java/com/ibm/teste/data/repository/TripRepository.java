package com.ibm.teste.data.repository;

import com.ibm.teste.data.remote.service.TripRegisterService;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripRepository {
    public static Retrofit retrofit;

    private static Retrofit getClient(){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            return retrofit;
    }

    public static TripRegisterService getTripService() {
        TripRegisterService tripRegisterService = getClient().create(TripRegisterService.class);

        return tripRegisterService;
    }
}
