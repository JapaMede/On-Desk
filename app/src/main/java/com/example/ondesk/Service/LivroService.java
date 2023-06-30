package com.example.ondesk.Service;


import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;

import com.example.ondesk.Adapter.LivroAdapter;
import com.example.ondesk.configFirebase.config;
import com.example.ondesk.model.Livro;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LivroService {

    static DatabaseReference dbRef;


    static LivroAdapter lAdapterCatalogo;
    static List<Livro> livrosCatalogo;


    static List<Livro> livrosRua;
    static LivroAdapter lAdapterRua;


    static List<Livro> livrosEmprestimo;
    static LivroAdapter lAdapterEmprestimo;


    public static void getEmprestimos(){
        dbRef = config.getFirebase().child("Livro");
        Query query = dbRef.orderByChild("status").equalTo(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                livrosEmprestimo = new ArrayList<>();
                for (DataSnapshot dh : snapshot.getChildren()){
                    Livro l = dh.getValue(Livro.class);
                    livrosEmprestimo.add(l);
                    lAdapterEmprestimo = new LivroAdapter(livrosEmprestimo);
                }
                setLivrosEmprestimo(livrosEmprestimo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static List<Livro> getLivrosEmprestimo() {
        return livrosEmprestimo;
    }
    public static void setLivrosEmprestimo(List<Livro> livrosEmprestimo) {
        LivroService.livrosEmprestimo = livrosEmprestimo;
    }
    public static LivroAdapter getlAdapterEmprestimo(){
        getEmprestimos();
        return lAdapterEmprestimo;
    }



    public static void getRua(){
        dbRef = config.getFirebase().child("Livro");
        Query query = dbRef.orderByChild("status").equalTo(false);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                livrosRua = new ArrayList<>();
                for (DataSnapshot dh : snapshot.getChildren()){
                    Livro l = dh.getValue(Livro.class);
                    livrosRua.add(l);
                    lAdapterRua = new LivroAdapter(livrosRua);
                }
                setLivrosRua(livrosRua);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static List<Livro> getLivrosRua() {
        return livrosRua;
    }
    public static void setLivrosRua(List<Livro> livrosRua) {
        LivroService.livrosRua = livrosRua;
    }

    public static LivroAdapter getlAdapterRua() {
        getRua();
        return lAdapterRua;
    }
    public static void getCatalogo(){

        dbRef = config.getFirebase();
        dbRef.child("Livro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                livrosCatalogo = new ArrayList<>();
                for (DataSnapshot dh : snapshot.getChildren()) {
                    Livro l = dh.getValue(Livro.class);
                    livrosCatalogo.add(l);
                    lAdapterCatalogo = new LivroAdapter(livrosCatalogo);
                }
                setLivrosCatalogo(livrosCatalogo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static LivroAdapter getlAdapter() {
        getCatalogo();
        return lAdapterCatalogo;
    }
    public static void setLivrosCatalogo(List<Livro> livrosCatalogo) {
        LivroService.livrosCatalogo = livrosCatalogo;
    }
    public static List<Livro> getLivrosCatalogo() {
        return livrosCatalogo;
    }


    public void salvaLivro(Livro l, Uri urlImage){
        dbRef = config.getFirebase();
        if (l.getId() != null){
            dbRef.child("Livro").child(l.getId()).setValue(l);
            Log.i("salva", "ja tem");

        }else {
            String s = dbRef.child("Livro").push().getKey();
            Log.i("salva", s);
            l.setId (s);
            l.setStatus(true);
            dbRef.child("Livro").child(l.getId()).setValue(l);
            uploadImage(urlImage, l.getId());
        }
    }


    public void efetuarBaixa(Livro l){
        dbRef = config.getFirebase();

        dbRef.child("Livro")
                .child(l.getId())
                .child("status")
                .setValue(true);

        dbRef.child("Emprestimo")
                .child(l.getId())
                .removeValue();
    }

    public void uploadImage(Uri imageUri, String id) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        dbRef = config.getFirebase();
        StorageReference imagesRef = storageRef.child("images/" + UUID.randomUUID().toString());

        imagesRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                dbRef.child("Livro").child(id).child("urlImage")
                                        .setValue(uri.toString());
                            }
                        });
                    }
                });
    }


}
