package com.example.ondesk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.ondesk.R;
import com.example.ondesk.model.Livro;
import com.squareup.picasso.Picasso;

public class detalheLivroActivity extends AppCompatActivity {

    TextView lblNome, lblAutor, lblCategoria, lblSinopse;
    ImageView image;
    ImageButton btnVolter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_livros);

        lblNome = findViewById(R.id.labelNomeDetalhe);
        lblAutor = findViewById(R.id.labelAutorDetalhe);
        lblCategoria = findViewById(R.id.labelCategoriaDetalhe);
        lblSinopse = findViewById(R.id.labelSinopseDetalhe);

        image = findViewById(R.id.image);

        btnVolter = findViewById(R.id.btnVoltarDetalhe);
        btnVolter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent i = getIntent();
        if (i.hasExtra("Livro")){
            Livro l = (Livro) i.getSerializableExtra("Livro");
            lblNome.setText(l.getNome());
            lblAutor.setText(l.getAutor());
            lblCategoria.setText(l.getCategoria());
            lblSinopse.setText(l.getSinopse());

            Picasso.get().load(l.getUrlImage())
                    .fit()
                    .centerInside()
                    .into(image);
        }

    }
}
