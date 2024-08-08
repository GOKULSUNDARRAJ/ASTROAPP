package com.PK.astro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private List<Video> videoList;
    private OnItemClickListener listener;

    public VideoAdapter(Context context, List<Video> videoList, OnItemClickListener listener) {
        this.context = context;
        this.videoList = videoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);


        // Load thumbnail using Picasso (assuming you have a thumbnail URL in Video class)
        String thumbnailUrl = video.getVideoUrl();

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the video URL
                String videoUrl = video.getVideoUrl();

                // Pass video URL to activity method
                listener.onItemClick(videoUrl);
            }
        });



        WebSettings webSettings = holder.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        holder.webView.setWebChromeClient(new WebChromeClient());

        // Load the YouTube video using an iframe

        String videoUrl = video.getVideoUrl();
        String html = "<html><body style=\"margin:0; padding:0;\">" +
                "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>" +
                "</body></html>";

        holder.webView.loadData(html, "text/html", "utf-8");



    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewThumbnail;
        private WebView webView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.edite);
        }
    }

    // Interface to handle item clicks
    public interface OnItemClickListener {
        void onItemClick(String videoUrl);
    }
}
