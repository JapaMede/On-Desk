package com.example.ondesk.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ondesk.R;
import com.example.ondesk.Service.ColaboradorService;
import com.example.ondesk.model.Colaborador;


public class CadastroColab extends AppCompatActivity {

    ImageButton btnVoltar;
    EditText inputNome, inputEmail, inputSenha, inputConfSenha;
    Button btnContinuar;
    String nome, email, senha, confSenha;
    Colaborador c;
    ColaboradorService cS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_colab);

        cS = new ColaboradorService();

        btnVoltar = findViewById(R.id.btnVoltarCadColab);
        btnContinuar = findViewById(R.id.btnContinuarColab);

        inputNome = findViewById(R.id.txtNomeCad);
        inputEmail = findViewById(R.id.txtEmailCad);
        inputSenha = findViewById(R.id.txtPassCad);
        inputConfSenha = findViewById(R.id.txtComfimPass);


        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    nome = inputNome.getText().toString();
                    email = inputEmail.getText().toString();
                    senha = inputSenha.getText().toString();
                    confSenha = inputConfSenha.getText().toString();
                    if(email.equals("") || senha.equals("") || confSenha.equals("")){
                        return;
                    }else{
                        c = resgata();
                        if(c != null){
                            cS.CriarLogin(c);

                        }
                        continuar();
                    }
                }catch(RuntimeException ex){
                    ex.getMessage();
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        });
    }
    private Colaborador resgata(){
        if(confSenha.equals(senha)){
            Colaborador cc = new Colaborador();
            cc.setNome(nome);
            cc.setEmail(email);
            cc.setSenha(senha);
            Log.i("Confirm", "passou");
            return cc;
        }else{
            Log.i("Confirm", "Nao passou");
        }
        return null;
    }

    private void continuar() {
        Intent continuar = new Intent(CadastroColab.this, LoadActivity.class);
        startActivity(continuar);
        finish();
    }
    private void voltar() {
        Intent voltar = new Intent(CadastroColab.this, LoginColabActivity.class);
        startActivity(voltar);
        finish();
    }
}
