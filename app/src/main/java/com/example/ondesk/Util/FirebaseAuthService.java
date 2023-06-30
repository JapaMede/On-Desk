package com.example.ondesk.Util;


import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthService {
    private static FirebaseAuth mAuth;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static AuthStateListener mStateListener;

    public static void initialize(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                if (mStateListener != null) {
                    mStateListener.onAuthStateChanged(true);
                }
            } else {
                if (mStateListener != null) {
                    mStateListener.onAuthStateChanged(false);
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    public static void release() {
        if (mAuth != null && mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        mStateListener = null;
    }

    public static void setAuthStateListener(AuthStateListener listener) {
        mStateListener = listener;
    }
}
