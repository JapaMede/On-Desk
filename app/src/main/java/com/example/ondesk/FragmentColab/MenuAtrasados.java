package com.example.ondesk.FragmentColab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ondesk.Adapter.EmprestimoAdapter;
import com.example.ondesk.Listener.RecyclerItemClickListener;
import com.example.ondesk.R;
import com.example.ondesk.Service.EmprestimoService;
import com.example.ondesk.Service.LivroService;
import com.example.ondesk.model.Emprestimo;
import com.example.ondesk.model.Livro;

import java.util.List;

public class MenuAtrasados extends Fragment {

    RecyclerView rv;
    EmprestimoAdapter adapter;
    EmprestimoService service;
    List<Emprestimo> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu_atrasados, container, false);

        rv = v.findViewById(R.id.rvAtrasados);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        service = new EmprestimoService();
        adapter = service.getAtrasadoAsapter();

        list = EmprestimoService.getAtrasados();

        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Emprestimo e = list.get(position);
                Log.i("atrasado", e.getNomeLivro());
            }
        }));
        return v;
    }
}