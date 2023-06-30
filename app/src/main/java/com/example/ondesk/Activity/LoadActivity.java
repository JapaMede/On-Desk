package com.example.ondesk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ondesk.Util.FirebaseUtils;
import com.example.ondesk.R;
import com.example.ondesk.Service.UsuarioService;

public class LoadActivity extends AppCompatActivity {

    ProgressBar loadBar;
    int p;
    UsuarioService uService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);
        loadBar = findViewById(R.id.LoadBar);
        Handler hd = new Handler();
        uService = new UsuarioService();

        for(p = 1; p<=100; p++){
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadBar.setProgress(p, true);
                }
            },1500);
        }
        if(uService.checkUser()){
            Logado();
        }else{
            naoLogado();
        }
    }

    private void naoLogado() {
        Intent proxTela = new Intent(LoadActivity.this, HomeActivity.class);
        startActivity(proxTela);
        finish();
    }
    private void Logado() {
        FirebaseUtils.searchForEmailInTable(new FirebaseUtils.OnEmailFoundListener() {
            @Override
            public void onEmailFound(boolean found) {
                if(found){
                    Intent proxTela = new Intent(LoadActivity.this, Indexcolaborador.class);
                    uService.loadColab();
                    startActivity(proxTela);
                    finish();
                }else{
                    Intent proxTela = new Intent(LoadActivity.this, Indexleitor.class);
                    uService.loadLeitor();
                    startActivity(proxTela);
                    finish();
                }
            }
        });
    }
}
