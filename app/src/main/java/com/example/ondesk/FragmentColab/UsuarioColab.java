package com.example.ondesk.FragmentColab;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ondesk.Activity.LoadActivity;
import com.example.ondesk.R;
import com.example.ondesk.Service.ColaboradorService;
import com.example.ondesk.Service.EmprestimoService;
import com.example.ondesk.Service.LivroService;
import com.example.ondesk.Service.UsuarioService;
import com.example.ondesk.model.Colaborador;
import com.example.ondesk.model.Emprestimo;
import com.example.ondesk.model.Livro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

public class UsuarioColab extends Fragment {

    TextView lblNome, lblEmail, lblAtrasados, lblCadastrados, lblAtivos;
    ColaboradorService service;
    Colaborador colaborador;
    List<Emprestimo> atrasados;
    List<Livro> ativos, cadastrados;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_usuario_colab, container, false);

        lblNome = v.findViewById(R.id.labelNomeColab);
        lblEmail = v.findViewById(R.id.labelEmailColab);
        lblAtrasados = v.findViewById(R.id.labelAtrasadosColab);
        lblCadastrados = v.findViewById(R.id.labelCadastrados);
        lblAtivos = v.findViewById(R.id.labelAtivos);

        service = new ColaboradorService();
        colaborador = service.getInfoColabLogado();

        lblNome.setText(colaborador.getNome());
        lblEmail.setText(colaborador.getEmail());

        atrasados = EmprestimoService.getAtrasados();
        cadastrados = LivroService.getLivrosCatalogo();
        ativos = LivroService.getLivrosEmprestimo();

        lblCadastrados.setText(String.valueOf(cadastrados.size()));
        lblAtivos.setText(String.valueOf(ativos.size()));
        lblAtrasados.setText(String.valueOf(atrasados.size()));


        Button b = v.findViewById(R.id.btnSairColab);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsuarioService us = new UsuarioService();
                us.logout();
                startActivity(new Intent(getContext(), LoadActivity.class));
                requireActivity().finish();
            }
        });
        return v;
    }
}