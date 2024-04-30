package com.example.keiskisdaiktais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pagrindinis extends AppCompatActivity {

    private TextView textViewUserEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            textViewUserEmail.setText("Vartotojas: " + currentUser.getEmail());
        }

    }
}