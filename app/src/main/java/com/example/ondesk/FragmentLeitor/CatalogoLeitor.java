package com.example.ondesk.FragmentLeitor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ondesk.Activity.detalheLivroActivity;
import com.example.ondesk.Adapter.LivroAdapter;
import com.example.ondesk.Listener.RecyclerItemClickListener;
import com.example.ondesk.R;
import com.example.ondesk.Service.LivroService;
import com.example.ondesk.model.Livro;

import java.util.List;


public class CatalogoLeitor extends Fragment {

    RecyclerView rvLivros;
    LivroService lService;
    LivroAdapter lAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_catalogo_leitor, container, false);
        rvLivros = v.findViewById(R.id.rvIndex);
        rvLivros.setLayoutManager(new LinearLayoutManager(getContext()));


        lService = new LivroService();
        lAdapter = LivroService.getlAdapterEmprestimo();
        rvLivros.setAdapter(lAdapter);

        List<Livro> l = LivroService.getLivrosEmprestimo();

        rvLivros.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                Livro liv = l.get(position);
                Intent i = new Intent(getContext(), detalheLivroActivity.class);
                i.putExtra("Livro", liv);
                startActivity(i);
            }
        }));

        return v;
    }
}