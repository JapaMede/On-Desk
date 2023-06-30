package com.example.ondesk.Service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ondesk.Adapter.LeitorAdapter;
import com.example.ondesk.configFirebase.config;
import com.example.ondesk.model.CEP;
import com.example.ondesk.model.Leitor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LeitorService {

    FirebaseAuth mAuth;
    DatabaseReference fb;
    UsuarioService us;
    static DatabaseReference dbRef;

    static List<Leitor> listaVerificado;
    static LeitorAdapter adapter;
    static CEP endereco;
    static int num;

    public static CEP getEndereco() {
        return endereco;
    }

    public static void setEndereco(CEP endereco) {
        LeitorService.endereco = endereco;
    }

    public static int getNum() {
        return num;
    }

    public static void setNum(int num) {
        LeitorService.num = num;
    }

    public void CriarLogin(Leitor u, Context t){
        mAuth = FirebaseAuth.getInstance();
        us = new UsuarioService();

        mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser usuLocal = mAuth.getCurrentUser();
                    UserProfileChangeRequest update = new UserProfileChangeRequest.Builder()
                            .setDisplayName(u.getNome()).build();
                    usuLocal.updateProfile(update);
                    u.setId(usuLocal.getUid());
                    u.setVerificado(false);
                    salvaLeitor(u);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        showAlert("Falha no cadastro", "O usuário já está registrado.", t);
                    } else {
                        showAlert("Falha no cadastro", "Não foi possível realizar o cadastro.", t);
                    }
                }
            }
        });
    }

    public void salvaLeitor(Leitor u){
        fb = config.getFirebase();
        fb.child("Leitor").child(u.getId()).setValue(u);
    }

    public void login(String user, String pass, Context t){
        us = new UsuarioService();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.i("Id", mAuth.getCurrentUser().getUid());
                }else {
                    showAlert("Falha no login", "Usuário ou senha incorretos.", t);
                }
            }
        });
    }
    private void showAlert(String title, String message, Context t) {
        AlertDialog.Builder builder = new AlertDialog.Builder(t);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void validaLeitor(CEP c, int num){
        mAuth = FirebaseAuth.getInstance();
        dbRef = config.getFirebase();
        FirebaseUser user = mAuth.getCurrentUser();

        if (c != null) {
            dbRef.child("Leitor")
                .child(user.getUid())
                .child("Complementar")
                .setValue(c);
        }

        dbRef.child("Leitor")
                .child(user.getUid())
                .child("Complementar")
                .child("numero")
                .setValue(num);

        dbRef.child("Leitor")
                .child(user.getUid())
                .child("verificado")
                .setValue(true);
    }


    public static void getVerificado(){
        dbRef = config.getFirebase();
        Query q = dbRef.child("Leitor")
                .orderByChild("verificado")
                .equalTo(true);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVerificado = new ArrayList<>();
                for (DataSnapshot dh:snapshot.getChildren()){
                    Leitor l = dh.getValue(Leitor.class);
                    listaVerificado.add(l);
                    adapter = new LeitorAdapter(listaVerificado);
                }
                setListaVerificado(listaVerificado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static List<Leitor> getListaVerificado() {
        return listaVerificado;
    }

    public static void setListaVerificado(List<Leitor> listaVerificado) {
        LeitorService.listaVerificado = listaVerificado;
    }

    public static LeitorAdapter getAdapterEmprestimo(){
        getVerificado();
        return adapter;
    }


    public Leitor getInfoLeitorLogado(){
        FirebaseAuth au = FirebaseAuth.getInstance();
        FirebaseUser u = au.getCurrentUser();
        Leitor l = new Leitor();
        l.setId(u.getUid());
        l.setEmail(u.getEmail());
        l.setNome(u.getDisplayName());
        return l;
    }
    public void getEndeFromLeitor(){
        UsuarioService us = new UsuarioService();
        String user = us.getIdLeitorLogado();

        dbRef = config.getFirebase();
        dbRef.child("Leitor")
                .child(user)
                .child("Complementar")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            try {
                                CEP ende = snapshot.getValue(CEP.class);
                                setEndereco(ende);
                                int numero = snapshot.child("numero").getValue(Integer.class);
                                setNum(numero);
                            }catch (RuntimeException ex){
                               Log.i("EX", ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
