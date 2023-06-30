package com.example.ondesk.FragmentColab;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.ondesk.Activity.LivroFormActivity;
import com.example.ondesk.Adapter.LivroAdapter;
import com.example.ondesk.Listener.RecyclerItemClickListener;
import com.example.ondesk.R;
import com.example.ondesk.Service.LivroService;
import com.example.ondesk.model.Livro;

import java.util.List;

public class CatalogoColab extends Fragment {
    RecyclerView rvLivros;
    LivroService lService;
    LivroAdapter lAdapter;
    ImageButton btnAdicionarLivro;
    List<Livro> livros;
    public void setlAdapter(LivroAdapter lAdapter) {
        this.lAdapter = lAdapter;
    }


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_catalogo_colab, container, false);
        rvLivros = v.findViewById(R.id.rvIndexColab);
        lService = new LivroService();
        setlAdapter(lService.getlAdapter());


        btnAdicionarLivro = v.findViewById(R.id.btnAdicionarLivro);
        btnAdicionarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), LivroFormActivity.class);
                startActivity(i);
            }
        });

        rvLivros.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLivros.setAdapter(lAdapter);

        List<Livro> l = LivroService.getLivrosCatalogo();

        rvLivros.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                Livro liv = l.get(position);
                if (liv != null) {
                    Intent i = new Intent(getContext(), LivroFormActivity.class);
                    i.putExtra("Livro", liv);
                    startActivity(i);

                }
            }
        }));

        return v;
    }
}