package com.ibm.teste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ibm.teste.data.adapter.MyAdapter;
import com.ibm.teste.data.model.MyModel;
import com.ibm.teste.data.repository.TripRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    private List<MyModel> myModelList;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;


    private String userMenuId,userName, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        showTrips();

        userMenuId = getIntent().getStringExtra("UserId");
        userName = getIntent().getStringExtra("nameUser");
        userEmail = getIntent().getStringExtra("emailUser");


        ImageView favorites = findViewById(R.id.ic_favorites);
        ImageView profile = findViewById(R.id.ic_profile);

        //mtodo para ir para janela de favoritos
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, FavoritesActivity.class);
                intent.putExtra("favUserId", userMenuId);
                intent.putExtra("nameUser", userName);
                intent.putExtra("emailUser", userEmail);
                startActivity(intent);
            }
        });


        //metodo para ir para janela de perfil do usuario
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                intent.putExtra("profId", userMenuId);
                intent.putExtra("nameUser", userName);
                intent.putExtra("emailUser", userEmail);
                startActivity(intent);
            }
        });

    }


    //metodo para carregar todos os cars no recyclerView
    public void showTrips(){
        Call<List<MyModel>> call= TripRepository.getTripService().getAllTrips();
        call.enqueue(new Callback<List<MyModel>>() {
            @Override
            public void onResponse(Call<List<MyModel>> call, Response<List<MyModel>> response) {
                if(response.isSuccessful()){
                    myModelList = response.body();
                    for(MyModel item : myModelList) item.setUserId(Integer.parseInt(userMenuId));
                    myAdapter = new MyAdapter(myModelList, getApplicationContext());
                    recyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<MyModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.falha_ao_carregar_as_imagens, Toast.LENGTH_SHORT).show();
            }
        });
    }

}