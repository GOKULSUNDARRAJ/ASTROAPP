package com.PK.astro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity13 extends AppCompatActivity implements VideoAdapter.OnItemClickListener {

    private WebView webView;
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private List<Video> videoList;

    private static final String BASE_URL = "https://www.pkgastrologer.com/api/";
    private static final String TAG = "DetailActivity13";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail13);


        // Initialize WebView
        webView = findViewById(R.id.edite);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        // Load the YouTube video using an iframe
        String videoId = "p5rPuxtfpjI?si=Kl_mE25VQQXo-z1l";
        String videoUrl = "https://www.youtube.com/embed/" + videoId;
        String html = "<html><body style=\"margin:0; padding:0;\">" +
                "<iframe width=\"100%\" height=\"100%\" src=\"" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>" +
                "</body></html>";

        webView.loadData(html, "text/html", "utf-8");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of ApiService interface
        ApiService apiService = retrofit.create(ApiService.class);

        // Call the API method
        Call<List<Video>> call = apiService.getVideos();

        // Execute the call asynchronously
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videoList = response.body();
                    videoAdapter = new VideoAdapter(DetailActivity13.this, videoList, DetailActivity13.this);
                    recyclerView.setAdapter(videoAdapter);
                } else {
                    Toast.makeText(DetailActivity13.this, "Failed to get response from API", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to get response from API");
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                Toast.makeText(DetailActivity13.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to fetch data: " + t.getMessage());
            }
        });
    }

    // Handle item click from VideoAdapter
    @Override
    public void onItemClick(String videoUrl) {

    }

    // Navigate back to MainActivity3
    public void goback(View view) {
        onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit the Page?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit the app
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit the Page?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit the app
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
