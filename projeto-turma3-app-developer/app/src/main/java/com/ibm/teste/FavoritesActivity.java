package com.ibm.teste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ibm.teste.data.adapter.MyAdapter;
import com.ibm.teste.data.model.MyModel;
import com.ibm.teste.data.remote.Favorite.FavoriteObject;
import com.ibm.teste.data.remote.Trips.TripObject;
import com.ibm.teste.data.repository.FavoriteReposity;
import com.ibm.teste.data.repository.TripRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {


    private String userFavoriteID, name, email;
    private Integer id;

    private List<MyModel> myModelList;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;


    List<MyModel> myModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);




        name = getIntent().getStringExtra("nameUser");
        email = getIntent().getStringExtra("emailUser");

        userFavoriteID = (getIntent().getStringExtra("favUserId"));
        id = Integer.parseInt(userFavoriteID);
        requestTripsByUserId();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));


        Button menu = findViewById(R.id.btn_planes);
        ImageView profile = findViewById(R.id.ic_profile);


        //metodo para voltar a janela de menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this, MenuActivity.class);
                intent.putExtra("UserId", userFavoriteID);
                intent.putExtra("nameUser", name);
                intent.putExtra("emailUser", email);
                startActivity(intent);
                finish();
            }
        });

        //metodo para ir para janela de perfil do usuario
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FavoritesActivity.this, ProfileActivity.class);
                intent.putExtra("profId", userFavoriteID);
                intent.putExtra("nameUser", name);
                intent.putExtra("emailUser", email);
                startActivity(intent);
                finish();
            }
        });
    }

    public void requestTripsByUserId(){
        Call<List<MyModel>> call= FavoriteReposity.getfavoriteService().getAllFavorites(new FavoriteObject(id));
        call.enqueue(new Callback<List<MyModel>>() {
            @Override
            public void onResponse(Call<List<MyModel>> call, Response<List<MyModel>> response) {
                if(response.isSuccessful()){
                    myModelList = response.body();
                    for(MyModel item : myModelList)item.setUserId(Integer.parseInt(userFavoriteID));
                    showTrips(myModelList);


                }
            }

            @Override
            public void onFailure(Call<List<MyModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "daqui sera?", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showTrips(List<MyModel> list){

        for(int i= 0; i< myModelList.size(); i++) {
            Call<MyModel> call = TripRepository.getTripService().getTrips(new TripObject(list.get(i).getTripId()));
            call.enqueue(new Callback<MyModel>() {
                @Override
                public void onResponse(Call<MyModel> call, Response<MyModel> response) {
                    if (response.isSuccessful()) {
                        MyModel myModel = new MyModel();
                        myModel.setName(response.body().getName());
                        myModel.setImg(response.body().getImg());
                        myModel.setCountry(response.body().getCountry());

                        myModels.add(myModel);
                        myAdapter = new MyAdapter(myModels, getApplicationContext());
                        recyclerView.setAdapter(myAdapter);
                    }
                }

                @Override
                public void onFailure(Call<MyModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), R.string.falha_ao_carregar_as_imagens, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}