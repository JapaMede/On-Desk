package com.example.ondesk.FragmentLeitor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ondesk.Activity.Cadastro2Leitor;
import com.example.ondesk.Activity.LoadActivity;
import com.example.ondesk.R;
import com.example.ondesk.Service.EmprestimoService;
import com.example.ondesk.Service.LeitorService;
import com.example.ondesk.Service.UsuarioService;
import com.example.ondesk.model.Emprestimo;
import com.example.ondesk.model.Leitor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class UsuarioLeitor extends Fragment {

    TextView lblNome, lblEmail, lblAtrasados, lblEmprestados;
    Leitor leitor;
    LeitorService service;
    List<Emprestimo> emprestimos;
    Calendar dataAtual, dataEntega;
    List<Emprestimo> atrasados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_usuario_leitor, container, false);

        lblNome = v.findViewById(R.id.labelNomeColab);
        lblEmail = v.findViewById(R.id.labelEmailColab);
        lblAtrasados = v.findViewById(R.id.labelAtrasadosColab);
        lblEmprestados = v.findViewById(R.id.labelCadastrados);

        service = new LeitorService();
        leitor = service.getInfoLeitorLogado();

        emprestimos = EmprestimoService.getEmprestimos();

        dataAtual = Calendar.getInstance();
        dataEntega = Calendar.getInstance();

        lblNome.setText(leitor.getNome());
        lblEmail.setText(leitor.getEmail());

        lblEmprestados.setText(String.valueOf(emprestimos.size()));
        atrasados = new ArrayList<>();
        for (int i = 0; i < emprestimos.size(); i++){
            Emprestimo e = emprestimos.get(i);
            dataEntega.setTimeInMillis(e.getDataPrevista());
            if (dataAtual.after(dataEntega)){
                atrasados.add(e);
            }
        }

        lblAtrasados.setText(String.valueOf(atrasados.size()));


        Button btnSair = v.findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsuarioService us = new UsuarioService();
                us.logout();
                startActivity(new Intent(getContext(), LoadActivity.class));
                requireActivity().finish();
            }
        });

        Button btnVerificar = v.findViewById(R.id.btnVerificar);
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Cadastro2Leitor.class);
                i.putExtra("onde", "s");
                startActivity(i);
            }
        });
        return v;
    }
}