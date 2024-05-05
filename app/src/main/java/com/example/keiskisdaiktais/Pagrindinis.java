package com.example.keiskisdaiktais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pagrindinis extends AppCompatActivity {

    private TextView textViewUserEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pagrindinis);

        mAuth = FirebaseAuth.getInstance();

        textViewUserEmail = findViewById(R.id.textViewUserEmail);

        Button logoutButton = findViewById(R.id.button10);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Pagrindinis.this, AntrasLangas.class);
                startActivity(intent);
                finish();
            }
        });

        Button button = findViewById(R.id.button9);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pagrindinis.this,Ikelti.class);
                startActivity(intent);
            }
        });

        Button button12 = findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pagrindinis.this, Mano.class);
                startActivity(intent);
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            textViewUserEmail.setText("Vartotojas: " + currentUser.getEmail());
        }
    }
}
