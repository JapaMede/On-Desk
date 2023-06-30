package com.example.ondesk.Service;


import android.util.Log;

import com.example.ondesk.Adapter.EmprestimoAdapter;
import com.example.ondesk.Adapter.EmprestimoAdapterLeitor;
import com.example.ondesk.Adapter.LeitorAdapter;
import com.example.ondesk.Adapter.LivroAdapter;
import com.example.ondesk.model.Emprestimo;
import com.example.ondesk.model.Livro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;


public class UsuarioService {

    FirebaseAuth mAuth;
    DatabaseReference data;

    public boolean checkUser(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            return true;
        }else{
            return false;
        }
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }


    public String getIdLeitorLogado(){
        FirebaseAuth au = FirebaseAuth.getInstance();
        FirebaseUser u = au.getCurrentUser();
        return u.getUid();
    }

    public void loadLeitor(){
        try {
            LivroAdapter a = LivroService.getlAdapterEmprestimo();
            EmprestimoAdapterLeitor e = EmprestimoService.getEmAdapter();
            LeitorService ls = new LeitorService();
            ls.getEndeFromLeitor();
        }catch (RuntimeException ex){
            Log.i("erro", ex.getMessage());
        }
    }

    public void loadColab(){
        LivroAdapter a = LivroService.getlAdapter();
        LivroAdapter a2 = LivroService.getlAdapterEmprestimo();
        LivroAdapter a3 = LivroService.getlAdapterRua();
        EmprestimoAdapter e = EmprestimoService.getAtrasadoAsapter();
        LeitorAdapter l = LeitorService.getAdapterEmprestimo();
    }
}
