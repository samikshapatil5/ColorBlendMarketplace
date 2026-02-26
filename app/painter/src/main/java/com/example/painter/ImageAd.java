package com.example.painter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAd extends RecyclerView.Adapter<ImageAd.DataViewHolder> {

    private Context context;
    private List<DataModel> dataModels;

    public ImageAd(Context context, List<DataModel> dataModels) {
        this.context = context;
        this.dataModels = dataModels;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DataModel dataModel = dataModels.get(position);
        holder.textView.setText("Name: " + dataModel.getName() +
                "\nExperience: " + dataModel.getQualification());

        // Create a new adapter for the image RecyclerView
        ImageAdpt imageAdapter = new ImageAdpt(context, dataModel.getImageUrls());
        holder.imageRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.imageRecyclerView.setAdapter(imageAdapter);

        // Set OnClickListener for the image button
        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the phone number
                String phoneNumber = dataModel.getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView imageRecyclerView;
        ImageButton imgButton;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.displayedDataTextView);
            imageRecyclerView = itemView.findViewById(R.id.imageView);
            imgButton = itemView.findViewById(R.id.imgbtn);
        }
    }
}
