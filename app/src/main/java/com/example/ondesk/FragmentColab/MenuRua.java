package com.example.ondesk.FragmentColab;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ondesk.Activity.EmprestimoLivroActivity;
import com.example.ondesk.Adapter.LivroAdapter;
import com.example.ondesk.Listener.RecyclerItemClickListener;
import com.example.ondesk.R;
import com.example.ondesk.Service.LivroService;
import com.example.ondesk.model.Livro;

import java.util.List;

public class MenuRua extends Fragment {

    RecyclerView rvLivros;
    LivroService lService;
    LivroAdapter lAdapter;
    List<Livro> livros;
    ImageButton btnNovoEmprestimo;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_menu_rua, container, false);
        rvLivros = v.findViewById(R.id.rvRua);
        rvLivros.setLayoutManager(new LinearLayoutManager(getContext()));
        lService = new LivroService();
        lAdapter = lService.getlAdapterRua();
        rvLivros.setAdapter(lAdapter);

        livros  = LivroService.getLivrosRua();
        rvLivros.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Livro l = livros.get(position);
                Baixa(l);
            }
        }));

        btnNovoEmprestimo = v.findViewById(R.id.btnEmprestaNovo);
        btnNovoEmprestimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EmprestimoLivroActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

    public void Baixa(Livro l){
        builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        final View popUp = getLayoutInflater().inflate(R.layout.dialog_efetuar_baixa, null);
        builder.setView(popUp);
        dialog = builder.create();
        dialog.show();

        Button btnSim = popUp.findViewById(R.id.btnSim);
        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            LivroService ls = new LivroService();
            ls.efetuarBaixa(l);
            dialog.dismiss();
            }
        });

        Button btnNao = popUp.findViewById(R.id.btnNao);
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}