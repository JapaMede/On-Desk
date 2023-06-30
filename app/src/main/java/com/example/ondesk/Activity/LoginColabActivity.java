package com.example.ondesk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ondesk.R;
import com.example.ondesk.Service.ColaboradorService;
import com.example.ondesk.Util.AuthStateListener;
import com.example.ondesk.Util.FirebaseAuthService;

public class LoginColabActivity extends AppCompatActivity implements AuthStateListener {

    EditText inputUser, inputPass;
    ImageButton btnVoltar;
    ColaboradorService CS;
    Button btnCadColab, btnEntrarColab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_colaborador);

        FirebaseAuthService.initialize(this);
        FirebaseAuthService.setAuthStateListener(this);


        inputUser = findViewById(R.id.inputUserColab);
        inputPass = findViewById(R.id.inputPassColab);

        btnVoltar = findViewById(R.id.btnVoltarColab);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        });


        btnCadColab = findViewById(R.id.btnCadColab);
        btnCadColab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastre();
            }
        });

        btnEntrarColab = findViewById(R.id.btnEntrarColab);
        btnEntrarColab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        CS = new ColaboradorService();
        String txtUser = inputUser.getText().toString();
        String txtPass = inputPass.getText().toString();
        if (txtUser.equals("") || txtPass.equals("")){
            return;
        }else{
            CS.login(txtUser, txtPass, this);
        }
    }

    public void cadastre(){
        Intent i = new Intent(LoginColabActivity.this, CadastroColab.class);
        startActivity(i);
        finish();
    }

    private void entrar(){
        Intent i = new Intent(LoginColabActivity.this, LoadActivity.class);
        startActivity(i);
        FirebaseAuthService.release();
        finish();
    }
    private void voltar() {
        Intent voltar = new Intent(LoginColabActivity.this, HomeActivity.class);
        startActivity(voltar);
        finish();
    }

    @Override
    public void onAuthStateChanged(boolean isUserLoggedIn) {
        if (isUserLoggedIn){
            entrar();
        }
    }
}
