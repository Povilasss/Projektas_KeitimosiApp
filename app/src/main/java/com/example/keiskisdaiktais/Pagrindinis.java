package com.example.keiskisdaiktais;

import android.os.Bundle;
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

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            textViewUserEmail.setText("Vartotojas: " + currentUser.getEmail());
        }

    }
}