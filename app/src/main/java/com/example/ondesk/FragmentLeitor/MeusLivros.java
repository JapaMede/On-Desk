package com.example.ondesk.FragmentLeitor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ondesk.Adapter.EmprestimoAdapter;
import com.example.ondesk.Adapter.EmprestimoAdapterLeitor;
import com.example.ondesk.R;
import com.example.ondesk.Service.EmprestimoService;
import com.example.ondesk.Service.UsuarioService;
import com.example.ondesk.model.Emprestimo;
import com.example.ondesk.model.Leitor;

import java.util.List;


public class MeusLivros extends Fragment {
    EmprestimoService es;
    RecyclerView rv;
    List<Emprestimo> listEm;
    EmprestimoAdapterLeitor adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meus_livros, container, false);
        es = new EmprestimoService();
        adapter = EmprestimoService.getEmAdapter();

        listEm = EmprestimoService.getEmprestimos();

        rv = v.findViewById(R.id.rvMeusLivros);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        return v;
    }
}