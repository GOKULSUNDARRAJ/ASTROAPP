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

import java.util.List;

public class HomeAdapter2 extends RecyclerView.Adapter<HomeAdapter2.HomeViewHolder> {
    private List<HomeItem> homeItemList;
    private Context context;
    private static final int START_INDEX = 10; // Start index for items to display
    private static final int END_INDEX = 15; // End index for items to display

    public HomeAdapter2(List<HomeItem> homeItemList, Context context) {
        this.homeItemList = homeItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        int dataIndex = START_INDEX - 1 + position; // Calculate the index in homeItemList

        if (dataIndex >= 0 && dataIndex < homeItemList.size() && dataIndex <= END_INDEX - 1) {
            HomeItem item = homeItemList.get(dataIndex);
            holder.titleTextView.setText(item.getTitle());
            Glide.with(context)
                    .load(item.getPath() + "/" + item.getImage())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(100)))
                    .into(holder.imageView);

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
        // Calculate the number of items to display within the specified range
        return Math.min(homeItemList.size() - START_INDEX + 1, END_INDEX - START_INDEX + 1);
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
