package com.example.ondesk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ondesk.R;

import java.util.concurrent.ExecutionException;

import com.example.ondesk.Service.LeitorService;
import com.example.ondesk.model.CEP;
import com.example.ondesk.Service.HttpService;

public class Cadastro2Leitor extends AppCompatActivity {

    EditText inputCep, inputLog, inputCid, inputEs, inputBai, inputNum;
    String cep;
    Button btnFinalizarLeitor;
    LeitorService lService;
    ImageButton btnVoltar;
    CEP ende;
    Intent prox = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.cadastro2_leitor);
        super.onCreate(savedInstanceState);

        inputCep = findViewById(R.id.inputCep);
        inputNum = findViewById(R.id.inputNumero);
        inputLog = findViewById(R.id.inputLog);
        inputCid = findViewById(R.id.inputCidade);
        inputEs = findViewById(R.id.inputEstado);
        inputBai = findViewById(R.id.inputBairro);
        btnFinalizarLeitor = findViewById(R.id.btnFinalizarLeitor);
        ende = new CEP();
        lService = new LeitorService();
        Intent i = getIntent();
        if (i.hasExtra("onde")){
            lService = new LeitorService();
            lService.getEndeFromLeitor();
            try {
                int num = LeitorService.getNum();
                ende = LeitorService.getEndereco();

                inputCep.setText(ende.getCep());
                inputNum.setText(String.valueOf(num));
                inputLog.setText(ende.getLogradouro());
                inputCid.setText(ende.getLocalidade());
                inputEs.setText(ende.getUf());
                inputBai.setText(ende.getBairro());

            }catch (RuntimeException ex){
                Log.i("erro", ex.getMessage());
            }
        }else{
            prox = new Intent(Cadastro2Leitor.this, LoadActivity.class);
        }

        inputCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                cep = inputCep.getText().toString();
                buscar(cep);
            }
        });
        btnVoltar = findViewById(R.id.btnVoltarCad2);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnFinalizarLeitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CEP endereco = new CEP();
                endereco.setCep(cep);
                endereco.setLogradouro(inputLog.getText().toString());
                endereco.setBairro(inputBai.getText().toString());
                endereco.setUf(inputEs.getText().toString());
                endereco.setLocalidade(inputCid.getText().toString());

                int num = Integer.parseInt(inputNum.getText().toString());

                lService.validaLeitor(endereco, num);
                if (prox != null){
                    startActivity(prox);
                }
                finish();
            }
        });
    }
    public void buscar(String e){
        if (e.length() > 0 && !e.equals("") && e.length() == 8){
            try {
                CEP ende = new HttpService(e).execute().get();
                inputLog.setText(ende.getLogradouro());
                inputCid.setText(ende.getLocalidade());
                inputEs.setText(ende.getUf());
                inputBai.setText(ende.getBairro());
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
