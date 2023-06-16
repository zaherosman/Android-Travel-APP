package com.ibm.teste.data.remote.service;

import com.ibm.teste.data.model.MyModel;
import com.ibm.teste.data.remote.Trips.TripObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TripRegisterService {
    @GET("register-trips/allTrips")
    Call<List<MyModel>> getAllTrips();

    @POST("register-trips/requestByTripId")
    Call<MyModel> getTrips(@Body TripObject tripObject);
}
