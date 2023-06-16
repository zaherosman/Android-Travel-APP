package com.ibm.teste.data.remote.service;

import com.ibm.teste.data.model.MyModel;
import com.ibm.teste.data.remote.Favorite.FavoriteObject;
import com.ibm.teste.data.remote.Favorite.FavoriteRequest;
import com.ibm.teste.data.remote.Favorite.FavoriteResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FavoriteRegisterService {
    @POST("register-favorites/")
    Call<FavoriteResponse> saveFavorite(@Body FavoriteRequest Request);

    @POST("register-favorites/requestByUserId/")
    Call<List<MyModel>> getAllFavorites(@Body FavoriteObject favoriteObject);

}
