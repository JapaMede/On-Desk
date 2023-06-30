package com.example.ondesk.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ondesk.R;
import com.example.ondesk.Service.LivroService;
import com.example.ondesk.Util.DataUtils;
import com.example.ondesk.model.Livro;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class LivroFormActivity extends AppCompatActivity {
    ImageButton btnVoltar, btnImg;
    Button btnSalvar;
    EditText inputNameLivro, inputAutor, inputCategoria, inputDataPubli, inputSinopse;
    Context t = this;
    LivroService lS;
    String nome, autor, categoria, dataPubli, sinopse;
    Long dataSelecionadaInMilis;
    Livro l;
    Uri selectedImageUri, imageUri;
    DataUtils du;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_livro);

        du = new DataUtils();

        inputNameLivro = findViewById(R.id.inputNameLivro);
        inputAutor = findViewById(R.id.inputAutor);
        inputCategoria = findViewById(R.id.inputCategoria);
        inputDataPubli = findViewById(R.id.inputDataPubli);
        inputSinopse = findViewById(R.id.inputSinopse);
        btnImg = findViewById(R.id.btnImg);
        btnVoltar = findViewById(R.id.btnVoltarCadLivro);


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        l = new Livro();
        Intent i = getIntent();
        if (i.getSerializableExtra("Livro") != null){

            l = (Livro) i.getSerializableExtra("Livro");
            inputNameLivro.setText(l.getNome());
            inputAutor.setText(l.getAutor());
            inputCategoria.setText(l.getCategoria());
            inputSinopse.setText(l.getSinopse());

            String dataFormatada = du.formatData(l.getDataPublicacao());
            Log.i("data", dataFormatada);

            inputDataPubli.setText(dataFormatada);
            dataSelecionadaInMilis = l.getDataPublicacao();

            Uri uri = Uri.parse(l.getUrlImage());
            btnImg.setTag(uri);

            Picasso.get().load(uri)
                    .fit()
                    .centerInside()
                    .into(btnImg);
        }

        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            nome = inputNameLivro.getText().toString();
            autor = inputAutor.getText().toString();
            categoria = inputCategoria.getText().toString();
            dataPubli = inputDataPubli.getText().toString();
            sinopse = inputSinopse.getText().toString();

            if (btnImg.getTag() == null ){
                return;
            }else{
                imageUri = (Uri) btnImg.getTag();
                Log.i("IMG", imageUri.toString());
            }

            if(nome.equals("") || autor.equals("") || categoria.equals("") || dataPubli.equals("")){
                return;
            }else {
                lS = new LivroService();
                l.setNome(nome);
                l.setAutor(autor);
                l.setCategoria(categoria);
                l.setDataPublicacao(dataSelecionadaInMilis);
                l.setSinopse(sinopse);
                lS.salvaLivro(l, selectedImageUri);
                finish();
            }
            }
        });
        inputDataPubli.setShowSoftInputOnFocus(false);
        inputDataPubli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendario = Calendar.getInstance();

                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        t,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                inputDataPubli.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                Calendar dataSelecionada = Calendar.getInstance();
                                dataSelecionada.set(Calendar.YEAR, year);
                                dataSelecionada.set(Calendar.MONTH, monthOfYear);
                                dataSelecionada.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                dataSelecionadaInMilis = dataSelecionada.getTimeInMillis();
                            }
                        },
                        ano, mes, dia);
                datePickerDialog.show();
            }
        });


        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            btnImg.setImageURI(selectedImageUri);
            btnImg.setTag(selectedImageUri);
        }
    }
}
