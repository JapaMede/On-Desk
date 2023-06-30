package com.example.ondesk.configFirebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class config {

    private static DatabaseReference database;

    public static DatabaseReference getFirebase(){
        if(database == null){
            database = FirebaseDatabase.getInstance().getReference();
        }
        return database;
    }
}
