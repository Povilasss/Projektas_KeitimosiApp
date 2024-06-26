package com.example.keiskisdaiktais;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Ikelti extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText etDescription, etPrice;
    private Button btnUpload, button13;
    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ikelti);

        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        btnUpload = findViewById(R.id.btnUpload);
        button13 = findViewById(R.id.button13);  // Add this line to find the button by its ID

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {  // Add this block to handle button click
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ikelti.this, Pagrindinis.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadFile();
        }
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child("uploads/" + System.currentTimeMillis() + ".jpg");

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrlTask = taskSnapshot.getStorage().getDownloadUrl();
                            downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    saveToDatabase(imageUrl); // Pridėta nauja funkcija
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Ikelti.this, "Klaida įkeliant nuotrauką", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Pasirinkite nuotrauką", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveToDatabase(String imageUrl) {
        String description = etDescription.getText().toString().trim();
        String price = etPrice.getText().toString().trim();

        if (currentUser != null) {
            String userId = currentUser.getUid(); // Gauti vartotojo ID

            String uploadId = databaseReference.push().getKey();

            Map<String, Object> uploadDetails = new HashMap<>();
            uploadDetails.put("userId", userId); // Priskirti vartotojo ID
            uploadDetails.put("imageUrl", imageUrl);
            uploadDetails.put("description", description);
            uploadDetails.put("price", price);

            databaseReference.child(uploadId).setValue(uploadDetails);
            Toast.makeText(Ikelti.this, "Duomenys sėkmingai išsaugoti", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vartotojas neprisijungęs", Toast.LENGTH_SHORT).show();
        }

    }

}

