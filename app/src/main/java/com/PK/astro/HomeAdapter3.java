package com.PK.astro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter3 extends RecyclerView.Adapter<HomeAdapter3.HomeViewHolder> {
    private List<HomeItem> homeItemList;
    private List<HomeItem> filteredList;
    private Context context;

    public HomeAdapter3(List<HomeItem> homeItemList, Context context) {
        this.homeItemList = homeItemList;
        this.filteredList = new ArrayList<>(homeItemList);
        this.context = context;
    }

    public void filterList(List<HomeItem> filteredList) {
        this.filteredList = filteredList.size() > 20 ? filteredList.subList(0, 20) : filteredList; // Limit to 9 items
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        HomeItem item = filteredList.get(position);
        holder.titleTextView.setText(item.getTitle());
        Glide.with(context)
                .load(item.getPath() + "/" + item.getImage())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                .into(holder.imageView);


        int itemId2 = Integer.parseInt(item.getId());

        if (itemId2==16){
            holder.cardproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(view.getContext(), DetailActivity13.class);
                    intent1.putExtra("ITEM_ID", item.getId());
                    intent1.putExtra("ITEM_NAME", item.getTitle());
                    view.getContext().startActivity(intent1);

                }
            });
        }
        else if (itemId2 > 12) {
            holder.cardproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(view.getContext(), DetailsActivity.class);
                    intent1.putExtra("SUB_ID","0");
                    intent1.putExtra("MAIN_ID", item.getId());
                    view.getContext().startActivity(intent1);


                }
            });
        }
        else {
            holder.cardproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(view.getContext(), Activity1.class);
                    intent1.putExtra("ITEM_ID", item.getId());
                    intent1.putExtra("ITEM_NAME", item.getTitle());
                    view.getContext().startActivity(intent1);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        ConstraintLayout cardproduct;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.scanimageview);
            titleTextView = itemView.findViewById(R.id.scantextview);
            cardproduct = itemView.findViewById(R.id.cardproduct);
        }
    }
}
