package com.PK.astro;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity1 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText searchEditText;
    private TextView title;

    private List<Zodiac> zodiacList;
    private List<Zodiac> filteredZodiacList;
    private ZodiacAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        searchEditText = findViewById(R.id.searchEditText);
        title = findViewById(R.id.tvAl4l);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Set 3 columns in the grid

        String itemId = getIntent().getStringExtra("ITEM_ID");
        String itemName = getIntent().getStringExtra("ITEM_NAME");

        title.setText(itemName);

        fetchData(itemId);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    private void fetchData(String itemId) {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.pkgastrologer.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Map<String, String> body = new HashMap<>();
        body.put("id", itemId);

        apiService.getZodiacData(body).enqueue(new Callback<List<Zodiac>>() {
            @Override
            public void onResponse(Call<List<Zodiac>> call, Response<List<Zodiac>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    zodiacList = response.body();
                    filteredZodiacList = new ArrayList<>(zodiacList);
                    adapter = new ZodiacAdapter(filteredZodiacList, itemId);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Zodiac>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // Handle failure
            }
        });
    }

    private void filter(String text) {
        List<Zodiac> filteredList = new ArrayList<>();
        for (Zodiac item : zodiacList) {
            if ((item.getTitle() != null && item.getTitle().toLowerCase().contains(text.toLowerCase())) ||
                    (item.getTamilName() != null && item.getTamilName().toLowerCase().contains(text.toLowerCase()))) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }


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

