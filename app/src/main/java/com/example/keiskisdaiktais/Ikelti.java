package com.example.keiskisdaiktais;

import static com.example.keiskisdaiktais.R.layout.activity_ikelti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private Button btnUpload;
    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_ikelti);

        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        btnUpload = findViewById(R.id.btnUpload);

        storageReference = FirebaseStorage.getInstance().getReference();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
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
                            Toast.makeText(Ikelti.this, "Nuotrauka įkelta sėkmingai", Toast.LENGTH_SHORT).show();

                            String imageUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            String description = etDescription.getText().toString().trim();
                            String price = etPrice.getText().toString().trim();

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
                            String uploadId = databaseReference.push().getKey();

                            Map<String, Object> uploadDetails = new HashMap<>();
                            uploadDetails.put("imageUrl", imageUrl);
                            uploadDetails.put("description", description);
                            uploadDetails.put("price", price);

                            databaseReference.child(uploadId).setValue(uploadDetails);
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
}
