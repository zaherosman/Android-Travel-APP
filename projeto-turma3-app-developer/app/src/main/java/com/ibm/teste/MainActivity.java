package com.ibm.teste;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import com.ibm.teste.data.repository.UserRepository;
import com.ibm.teste.data.remote.Users.LoginObject;
import com.ibm.teste.data.remote.Users.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        TextInputEditText tietemail, tietpass;

        tietemail = findViewById(R.id.tiet_email);
        tietpass = findViewById(R.id.tiet_pass);

        Button button_sin = findViewById(R.id.btn_sin);
        TextView tv_sup = findViewById(R.id.tv_sup);

        //metodo responsavel para ir para tela de cadastro clicando em signup
        tv_sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        //metodo para logar
        button_sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = tietemail.getText().toString().trim();
                senha = tietpass.getText().toString().trim();


                if (email.isEmpty() | senha.isEmpty()) {  //se campos vazios
                    Toast.makeText(getApplicationContext(),
                            R.string.preencha_campos,
                            Toast.LENGTH_LONG).show();


                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {  //se email nao contiver @ e .com
                    Toast.makeText(getApplicationContext(),
                            R.string.email_invalido,
                            Toast.LENGTH_LONG).show();


                }else{//se campos correspondendes e usuario existente, chamar metodo de login
                    getUser(email, senha);
                }
            }
        });
    }



    //metodo de login
    public void getUser(String email, String password){

        Call<UserResponse> registerResponseCall = UserRepository.getUserService().getUser(new LoginObject(email, password));
        registerResponseCall.enqueue(new Callback <UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){

                    //Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);

                        //recebe o informaçoes do usuario que por sinal esta exposto no body
                        String id = response.body().getId_user().toString();
                        String name = response.body().getName();
                        String email = response.body().getEmail();

                        intent.putExtra("UserId", id);
                        intent.putExtra("nameUser", name);
                        intent.putExtra("emailUser", email);
                        startActivity(intent);

                }else{ //mensagem de usuario nao existente
                    dialog();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.falha_no_cadastro + t.getLocalizedMessage() + ";" + t.getCause(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void dialog(){
        new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.ATENCAO)
                .setMessage(R.string.voce_nao_possui_cadastro_deseja_cadastrar)

                .setPositiveButton(R.string.Sim, new DialogInterface.OnClickListener() {
                    //se clicando no botao sim para criar conta
                    //metodo responsavel por transportar email e senha para preencher de forma
                    //automatica os campos ja escritos na tela anterior
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which){
                        Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                        intent.putExtra("KeyEmail", email);
                        intent.putExtra("KeySenha", senha);
                        startActivity(intent);
                    }
                })

                //se clicando no botao nao para criar conta
                //fechar janela de mensagem e nao liberar acesso
                .setNegativeButton(R.string.Nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whitch) {
                        Toast.makeText(getApplicationContext(),
                                R.string.acesso_Bloqueado,
                                Toast.LENGTH_LONG).show();
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
