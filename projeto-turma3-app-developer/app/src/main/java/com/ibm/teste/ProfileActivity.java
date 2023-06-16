package com.ibm.teste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private String userProfId, userName, userEmail;
    TextView name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        userProfId = getIntent().getStringExtra("profId");
        userName = getIntent().getStringExtra("nameUser");
        userEmail = getIntent().getStringExtra("emailUser");



        name = findViewById(R.id.tvNome);
        email = findViewById(R.id.tvEmail);
        ImageView favorite = findViewById(R.id.ic_favorites);
        Button btn_menu = findViewById(R.id.btn_planes);


        //atribuindo aos textView os valores vindos do menu que vieram do banco apos o login
        name.setText(userName);
        email.setText(userEmail);


        //metodo pra ir para janela de favoritos
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FavoritesActivity.class);
                intent.putExtra("favUserId", userProfId);
                intent.putExtra("nameUser", userName);
                intent.putExtra("emailUser", userEmail);
                startActivity(intent);
                finish();
            }
        });


        //metodo para ir para menu
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
                intent.putExtra("UserId", userProfId);
                intent.putExtra("nameUser", userName);
                intent.putExtra("emailUser", userEmail);
                startActivity(intent);
                finish();
            }
        });
    }
}