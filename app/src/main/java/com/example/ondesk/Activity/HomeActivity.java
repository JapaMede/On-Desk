package com.example.ondesk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ondesk.R;

public class HomeActivity extends AppCompatActivity {

    Button btnLeitor, btnColab;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        btnLeitor = findViewById(R.id.btnLeitor);
        btnColab = findViewById(R.id.btnColab);


    }

    public void LoginLeitor(View view){
        Intent loginLeitor = new Intent(HomeActivity.this, LoginLeitorActivity.class);
        startActivity(loginLeitor);
        finish();
    }

    public void LoginColab(View view){
        Intent loginLeitor = new Intent(HomeActivity.this, LoginColabActivity.class);
        startActivity(loginLeitor);
        finish();
    }
}
