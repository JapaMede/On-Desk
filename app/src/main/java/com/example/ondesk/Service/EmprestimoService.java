package com.example.ondesk.Service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ondesk.Adapter.EmprestimoAdapter;
import com.example.ondesk.Adapter.EmprestimoAdapterLeitor;
import com.example.ondesk.configFirebase.config;
import com.example.ondesk.model.Emprestimo;
import com.example.ondesk.model.Leitor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmprestimoService {

    static DatabaseReference dbRef;

    static EmprestimoAdapterLeitor emAdapter;
    static List<Emprestimo> emprestimos;

    static List<Emprestimo> atrasados;
    static EmprestimoAdapter atrasadoAsapter;



    public void empresta(Emprestimo e){
        dbRef = config.getFirebase();

        dbRef.child("Emprestimo")
                .child(e.getIdLivro())
                .setValue(e);

        dbRef.child("Livro")
                .child(e.getIdLivro())
                .child("status")
                .setValue(false);
    }

    public static void listEmprestimoFromLeitor(){
        dbRef = config.getFirebase();
        UsuarioService us = new UsuarioService();
        String idLeitor = us.getIdLeitorLogado();

        dbRef.child("Emprestimo")
                .orderByChild("idLeitor")
                .equalTo(idLeitor)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        emprestimos = new ArrayList<>();
                        for (DataSnapshot dh:snapshot.getChildren()){
                            Emprestimo e = dh.getValue(Emprestimo.class);
                            emprestimos.add(e);
                            emAdapter = new EmprestimoAdapterLeitor(emprestimos);
                        }
                        setEmprestimos(emprestimos);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static EmprestimoAdapterLeitor getEmAdapter() {
        listEmprestimoFromLeitor();
        return emAdapter;
    }

    public static List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public static void setEmprestimos(List<Emprestimo> emp) {
        emprestimos = emp;
    }



    public static void listAtrasados(){

        Calendar dataEntega, dataAtual;

        dbRef = config.getFirebase();
        Query q = dbRef.child("Emprestimo");

        dataAtual = Calendar.getInstance();
        dataEntega = Calendar.getInstance();


        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                atrasados = new ArrayList<>();
                for (DataSnapshot dh:snapshot.getChildren()){
                    Emprestimo e = dh.getValue(Emprestimo.class);
                    dataEntega.setTimeInMillis(e.getDataPrevista());
                    if (dataAtual.after(dataEntega)){
                        atrasados.add(e);
                        atrasadoAsapter = new EmprestimoAdapter(atrasados);
                    }
                    setAtrasados(atrasados);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static List<Emprestimo> getAtrasados() {
        listAtrasados();
        return atrasados;
    }

    public static void setAtrasados(List<Emprestimo> atrasados) {
        EmprestimoService.atrasados = atrasados;
    }

    public static EmprestimoAdapter getAtrasadoAsapter() {
        listAtrasados();
        return atrasadoAsapter;
    }
}
