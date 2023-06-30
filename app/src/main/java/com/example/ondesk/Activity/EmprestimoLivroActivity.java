package com.example.ondesk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ondesk.Adapter.LivroAdapter;
import com.example.ondesk.Listener.RecyclerItemClickListener;
import com.example.ondesk.R;
import com.example.ondesk.Service.LivroService;
import com.example.ondesk.model.Livro;

import java.util.List;

public class EmprestimoLivroActivity extends AppCompatActivity {

    RecyclerView rvLivros;
    LivroService lService;
    LivroAdapter lAdapter;
    ImageButton btnVoltar;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emprestimo_livro);

        btnVoltar = findViewById(R.id.btnVoltarEmpLiv);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rvLivros = findViewById(R.id.rvSelecionaLivro);
        rvLivros.setLayoutManager(new LinearLayoutManager(this));

        lService = new LivroService();
        lAdapter = lService.getlAdapterEmprestimo();
        rvLivros.setAdapter(lAdapter);

        List<Livro> l = LivroService.getLivrosEmprestimo();

        rvLivros.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Livro liv = l.get(position);
                if(liv != null){
                    Intent i = new Intent(EmprestimoLivroActivity.this, EmprestimoLeitorActivity.class);
                    i.putExtra("Livro", liv);
                    startActivity(i);
                    finish();
                }
            }
        }));


    }
}
