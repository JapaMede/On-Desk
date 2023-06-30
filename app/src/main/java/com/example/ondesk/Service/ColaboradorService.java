package com.example.ondesk.Service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.example.ondesk.configFirebase.config;
import com.example.ondesk.model.Colaborador;
import com.example.ondesk.model.Leitor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

public class ColaboradorService {
    FirebaseAuth mAuth;
    DatabaseReference fb;
    UsuarioService us;


    public void CriarLogin(Colaborador c){
        us = new UsuarioService();
        mAuth = FirebaseAuth.getInstance();
        if(us.checkUser()){
            us.logout();
        }
        mAuth.createUserWithEmailAndPassword(c.getEmail(), c.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser usuLocal = mAuth.getCurrentUser();
                            UserProfileChangeRequest update = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(c.getNome()).build();
                            usuLocal.updateProfile(update);
                            c.setId(usuLocal.getUid());
                            salvaColaborador(c);
                        }
                    }
                });
    }


    public void salvaColaborador(Colaborador c){
        fb = config.getFirebase();
        fb.child("Colaboradores").child(c.getId()).setValue(c);
    }

    public boolean login(String user, String pass, Context t){
        us = new UsuarioService();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.i("Id", mAuth.getCurrentUser().getUid());
                }else{
                    showAlert("Falha no login", "Usuário ou senha incorretos.", t);
                }
            }
        });
        if (us.checkUser()){
            return true;
        }
        return false;
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

    public Colaborador getInfoColabLogado(){
        FirebaseAuth au = FirebaseAuth.getInstance();
        FirebaseUser u = au.getCurrentUser();
        Colaborador c = new Colaborador();
        c.setId(u.getUid());
        c.setEmail(u.getEmail());
        c.setNome(u.getDisplayName());
        return c;
    }
}
