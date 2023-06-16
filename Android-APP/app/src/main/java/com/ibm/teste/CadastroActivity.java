package com.ibm.teste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ibm.teste.data.repository.UserRepository;
import com.ibm.teste.data.remote.Users.UserRequest;
import com.ibm.teste.data.remote.Users.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    TextInputEditText tietname, tietemail, tietpass, tietcpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initializeComponentes();

        TextView tv_sin = findViewById(R.id.tv_sin);
        ImageView back_icon = findViewById(R.id.iv_back);



        tietemail.setText(getIntent().getStringExtra("KeyEmail"));
        tietpass.setText(getIntent().getStringExtra("KeySenha"));

        Button btnCadastrar = findViewById(R.id.btn_sin);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = tietname.getText().toString().trim();
                String email = tietemail.getText().toString().trim();
                String senha = tietpass.getText().toString().trim();
                String senhaConfirm = tietcpass.getText().toString().trim();

                if(name.isEmpty() | email.isEmpty() | senha.isEmpty() | senhaConfirm.isEmpty()) { //se campos vazios
                    Toast.makeText(getApplicationContext(),
                            R.string.preencha_campos,
                            Toast.LENGTH_SHORT).show();

                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ //se email nao conter @ nem ".com"

                    Toast.makeText(getApplicationContext(),
                            R.string.email_invalido,
                            Toast.LENGTH_SHORT).show();

                }else if (senha.length() < 8){ //se senha menor que 8 caracteres

                    Toast.makeText(getApplicationContext(),
                            R.string.a_senha_deve_conter_no_minimo_8_caracteres,
                            Toast.LENGTH_SHORT).show();

                }else if(senha.equals(senhaConfirm)) { //se senhas iguais

                    salveUser(createdRequest());


                } else { //se senhas diferentes
                    Toast.makeText(getApplicationContext(),
                            R.string.senhas_diferentes,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //metodo responsavel para retornar a aba de login clicando na escrita de signup
        tv_sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //metodo responsavel para retornar a aba de login pelo icone superior
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //mapeamento dos objetos
    public void initializeComponentes(){
        tietname = findViewById(R.id.tiet_name);
        tietemail = findViewById(R.id.tiet_email);
        tietpass = findViewById(R.id.tiet_pass);
        tietcpass = findViewById(R.id.tiet_cpass);
    }
    // criaçao das requests
    public UserRequest createdRequest(){
        UserRequest request = new UserRequest();
        request.setName(tietname.getText().toString());
        request.setEmail(tietemail.getText().toString());
        request.setPass(tietpass.getText().toString());

        return request;
    }


    //metodo post para cadastrar novo usuario
    public void salveUser(UserRequest userRequest){
        Call<UserResponse> registerResponseCall = UserRepository.getUserService().saveUsers(userRequest);
        registerResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), R.string.cadastro_realizado_com_sucesso, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), R.string.eMail_ja_utilizado, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.falha_no_cadastro, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
