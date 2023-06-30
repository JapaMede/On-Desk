package com.example.ondesk.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ondesk.R;
import com.example.ondesk.Service.LeitorService;
import com.example.ondesk.Service.UsuarioService;
import com.example.ondesk.Util.AuthStateListener;
import com.example.ondesk.Util.FirebaseAuthService;

public class LoginLeitorActivity extends AppCompatActivity implements AuthStateListener {

    UsuarioService uS;
    LeitorService lS;
    EditText inputUser, inputPass;
    ImageButton btnVoltar;
    Button btnCadLeitor, btnEntrarLeitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_leitor);

        FirebaseAuthService.initialize(this);
        FirebaseAuthService.setAuthStateListener(this);

        uS = new UsuarioService();
        lS = new LeitorService();


        inputUser = findViewById(R.id.inputUserLeitor);
        inputPass = findViewById(R.id.inputPassLeitor);

        btnVoltar = findViewById(R.id.btnVoltarLeitor);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        });

        btnCadLeitor = findViewById(R.id.btnCadLeitor);
        btnCadLeitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

        btnEntrarLeitor = findViewById(R.id.btnEntrarLeitor);
        btnEntrarLeitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        String txtUser = inputUser.getText().toString();
        String txtPass = inputPass.getText().toString();
        if(txtUser.equals("") || txtPass.equals("")){
            return;
        }else{
            lS.login(txtUser, txtPass, this);
        }

    }

    public void voltar(){
        Intent voltar = new Intent(LoginLeitorActivity.this, HomeActivity.class);
        startActivity(voltar);
        FirebaseAuthService.release();
        finish();
    }

    private void cadastrar() {
        Intent cad = new Intent(LoginLeitorActivity.this, Cadastro1Leitor.class);
        startActivity(cad);
        FirebaseAuthService.release();
        finish();
    }

    private void entrar(){
        Intent i = new Intent(LoginLeitorActivity.this, LoadActivity.class);
        startActivity(i);
        FirebaseAuthService.release();
        finish();
    }

    @Override
    public void onAuthStateChanged(boolean isUserLoggedIn) {
        if (isUserLoggedIn){
            entrar();
        }
    }
}