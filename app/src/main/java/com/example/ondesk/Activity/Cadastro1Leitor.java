package com.example.ondesk.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ondesk.R;
import com.example.ondesk.Util.AuthStateListener;
import com.example.ondesk.Util.FirebaseAuthService;
import com.example.ondesk.model.Leitor;

import com.example.ondesk.Service.LeitorService;

public class Cadastro1Leitor extends AppCompatActivity implements AuthStateListener {

    ImageButton btnVoltar;
    EditText inputEmail, inputSenha, inputConfSenha, inputNome;
    Button btnContinuar;
    String email, senha, confSenha, nome;
    LeitorService uS;
    Leitor u;

    Context t = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro1_leitor);

        FirebaseAuthService.initialize(this);
        FirebaseAuthService.setAuthStateListener(this);

        uS = new LeitorService();

        inputEmail = findViewById(R.id.txtEmailCad);
        inputSenha = findViewById(R.id.txtPassCad);
        inputConfSenha = findViewById(R.id.txtComfimPass);
        inputNome = findViewById(R.id.inputNomeLeitor);

        btnVoltar = findViewById(R.id.btnVoltarCadLivro);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        });

        btnContinuar = findViewById(R.id.btnContinuarLeitor);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    email = inputEmail.getText().toString();
                    senha = inputSenha.getText().toString();
                    confSenha = inputConfSenha.getText().toString();
                    nome = inputNome.getText().toString();
                    if(email.equals("") || senha.equals("") || confSenha.equals("") || nome.equals("")){
                        return;
                    }else{
                        u = resgata();
                        if(u != null){
                            uS.CriarLogin(u, t);
                        }else{
                            showAlert("Falha no cadastro", "Senhas não coincidem");
                            return;
                        }
                    }
                }catch(RuntimeException ex){
                    ex.getMessage();
                }
            }
        });
    }

    private Leitor resgata(){
        if(confSenha.equals(senha)){
            Leitor uu = new Leitor();
            uu.setEmail(email);
            uu.setSenha(senha);
            uu.setNome(nome);
            Log.i("Confirm", "passou");
            return uu;
        }else{
            Log.i("Confirm", "Nao passou");
        }
        return null;
    }


    private void voltar() {
        Intent voltar = new Intent(Cadastro1Leitor.this, LoginLeitorActivity.class);
        startActivity(voltar);
        finish();
    }

    private void continuar() {
        Intent continuar = new Intent(Cadastro1Leitor.this, LoadActivity.class);
        startActivity(continuar);
        finish();
    }

    private void verificar(){
        Intent veri = new Intent(Cadastro1Leitor.this, Cadastro2Leitor.class);
        startActivity(veri);
        finish();
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verificaçao!")
                .setMessage("deseja se tornar verificado?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        verificar();
                    }
                }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        continuar();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onAuthStateChanged(boolean isUserLoggedIn) {
        if (isUserLoggedIn){
            showDialog();
        }
    }
}
