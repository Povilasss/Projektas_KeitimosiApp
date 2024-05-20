package com.example.keiskisdaiktais;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Upload> uploadList;

    public ImageAdapter(List<Upload> uploadList) {
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload upload = uploadList.get(position);
        holder.tvDescription.setText(upload.getDescription());
        holder.tvPrice.setText(upload.getPrice());
        Glide.with(holder.itemView.getContext())
                .load(upload.getImageUrl())
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvDescription, tvPrice;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}

