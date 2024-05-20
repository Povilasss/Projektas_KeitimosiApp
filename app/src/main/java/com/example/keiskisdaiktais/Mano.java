package com.example.keiskisdaiktais;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Mano extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Upload> uploadList;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mano);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

            databaseReference.orderByChild("userId").equalTo(currentUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            uploadList = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Upload upload = snapshot.getValue(Upload.class);
                                if (upload != null) {
                                    upload.setUploadId(snapshot.getKey()); // Ensure the key is set
                                    uploadList.add(upload);
                                }
                            }
                            adapter = new MyAdapter(uploadList, databaseReference);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle database error
                        }
                    });
        }
    }



    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<Upload> uploadList;
        private DatabaseReference databaseReference;

        public MyAdapter(List<Upload> uploadList, DatabaseReference databaseReference) {
            this.uploadList = uploadList;
            this.databaseReference = databaseReference;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_description_price, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Upload upload = uploadList.get(position);
            holder.tvDescription.setText(upload.getDescription());
            holder.tvPrice.setText(upload.getPrice());
            Glide.with(holder.itemView.getContext())
                    .load(upload.getImageUrl())
                    .into(holder.ivImage);

            // Delete button functionality
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = holder.getAdapterPosition();
                    onDeleteClick(clickedPosition, holder.itemView);
                }
            });

            // Edit button functionality
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = holder.getAdapterPosition();
                    onEditClick(clickedPosition);
                }
            });
        }

        @Override
        public int getItemCount() {
            return uploadList.size();
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView ivImage;
            TextView tvDescription, tvPrice;
            Button btnDelete, btnEdit;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ivImage = itemView.findViewById(R.id.ivImage);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvPrice = itemView.findViewById(R.id.tvPrice);
                btnDelete = itemView.findViewById(R.id.btnDelete);
                btnEdit = itemView.findViewById(R.id.btnEdit);
            }
        }

        private void onDeleteClick(int position, View itemView) {
            Upload selectedItem = uploadList.get(position);
            String selectedKey = selectedItem.getUploadId(); // Ensure uploadId is correctly set

            if (selectedKey != null) {
                databaseReference.child(selectedKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Remove the item from the list and notify the adapter
                        uploadList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(itemView.getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(itemView.getContext(), "Failed to delete item", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(itemView.getContext(), "Failed to get the item ID", Toast.LENGTH_SHORT).show();
            }
        }

        private void onEditClick(int position) {
            // Handle edit functionality
            // You can open a dialog or activity to edit the item's description and price
        }
    }
}


