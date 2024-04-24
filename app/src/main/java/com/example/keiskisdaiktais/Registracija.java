package com.example.keiskisdaiktais;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registracija extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.editTextTextEmailAddress2);
        passwordEditText = findViewById(R.id.editTextTextPassword2);
        registerButton = findViewById(R.id.button4);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registracija.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration success
                                    Toast.makeText(Registracija.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                    // Navigate to next activity
                                    Intent intent = new Intent(Registracija.this, AntrasLangas.class);
                                    startActivity(intent);
                                } else {
                                    // Registration failed
                                    Toast.makeText(Registracija.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                    Log.e("RegistrationError", task.getException().getMessage());
                                }

                                // Set full screen mode
                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, // Aplikacija pilnu ekranu
                                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            }
                        });
            }
        });

        Button button = findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registracija.this, AntrasLangas.class);
                startActivity(intent);
            }
        });
    }
}