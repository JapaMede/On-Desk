package com.example.ondesk.Util;



import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;

public class FirebaseUtils {
    public static void searchForEmailInTable(OnEmailFoundListener listener) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("/Colaboradores");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                if (map.containsKey(user.getUid())) {
                    String bancos = ((Map<String, String>) map.get(user.getUid())).get("email");
                    Log.d("Tag", bancos);
                    listener.onEmailFound(true);
                }else{
                    listener.onEmailFound(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onEmailFound(false);
            }
        });
    }

    public interface OnEmailFoundListener {
        void onEmailFound(boolean found);
    }
}
