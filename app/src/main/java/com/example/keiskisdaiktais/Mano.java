package com.example.keiskisdaiktais;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mano extends AppCompatActivity {

    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mano);

        ivImage = findViewById(R.id.ivImage);

        // Inicijuoti Firebase Realtime Database nuorodą
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        // Gauti duomenis apie ikeltas nuotraukas iš Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Gauti informaciją apie kiekvieną ikeltą nuotrauką
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String price = snapshot.child("price").getValue(String.class);

                    // Įkelti nuotrauką į ImageView naudojant Glide biblioteką
                    Glide.with(Mano.this).load(imageUrl).into(ivImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Klaida gavant duomenis iš Firebase Realtime Database
            }
        });
    }
}
