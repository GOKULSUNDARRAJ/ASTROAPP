package com.PK.astro;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import java.util.ArrayList;

public class ZodiacAdapter extends RecyclerView.Adapter<ZodiacAdapter.ZodiacViewHolder> {
    private List<Zodiac> zodiacList;
    private List<Zodiac> filteredZodiacList;
    private String itemId;

    public static class ZodiacViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleTextView;
        public TextView tamilNameTextView;
        LinearLayout cardproduct;

        public ZodiacViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.scanimageview);
            titleTextView = itemView.findViewById(R.id.scantextview);
            tamilNameTextView = itemView.findViewById(R.id.tamilname);
            cardproduct = itemView.findViewById(R.id.cardproduct);
        }
    }

    public ZodiacAdapter(List<Zodiac> zodiacList, String itemId) {
        this.zodiacList = zodiacList;
        this.filteredZodiacList = new ArrayList<>(zodiacList);
        this.itemId = itemId;
    }

    @Override
    public ZodiacViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_zodiac, parent, false);
        return new ZodiacViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ZodiacViewHolder holder, int position) {
        Zodiac zodiac = filteredZodiacList.get(position);

        holder.titleTextView.setText(zodiac.getTitle());
        holder.tamilNameTextView.setText(zodiac.getTamilName());

        // Load image using Glide
        Glide.with(holder.imageView.getContext())
                .load(zodiac.getPath() + "/" + zodiac.getImage())
                .into(holder.imageView);

        holder.cardproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                    intent.putExtra("SUB_ID", zodiac.getId());
                    intent.putExtra("MAIN_ID", itemId);
                    intent.putExtra("IMAGE", zodiac.getPath() + "/" + zodiac.getImage());
                    view.getContext().startActivity(intent);


                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return filteredZodiacList.size();
    }

    public void filterList(List<Zodiac> filteredList) {
        filteredZodiacList = filteredList;
        notifyDataSetChanged();
    }
}
