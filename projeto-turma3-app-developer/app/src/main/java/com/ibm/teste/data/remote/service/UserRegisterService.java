package com.ibm.teste.data.remote.service;

import com.ibm.teste.data.remote.Users.LoginObject;
import com.ibm.teste.data.remote.Users.UserRequest;
import com.ibm.teste.data.remote.Users.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserRegisterService {
    @POST("register-users/")
    Call<UserResponse> saveUsers(@Body UserRequest Request);

    @POST("register-users/login/")
    Call<UserResponse> getUser(@Body LoginObject loginObject);
}
