package com.PK.astro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TraningCentreActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://www.pkgastrologer.com/";
    private ApiService apiService;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 2296;


    ProgressBar progressBar;
    PDFView pdfView;
    String pdfUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traning_centre);

        pdfView = findViewById(R.id.pdfView);  // Assuming you have a PDFView in your layout

        progressBar = findViewById(R.id.progressBar);  // Assuming you have a ProgressBar in your layout


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of your API service interface
        apiService = retrofit.create(ApiService.class);

        // Example button click or method call to fetch PDF path
        fetchPdfPath();


    }

    private void fetchPdfPath() {
        String pdfEndpoint = "api/get_training"; // Endpoint to fetch PDF details

        // Initiate the request to fetch PDF path
        Call<TrainingResponse[]> call = apiService.getTrainingData(pdfEndpoint);
        call.enqueue(new Callback<TrainingResponse[]>() {
            @Override
            public void onResponse(Call<TrainingResponse[]> call, Response<TrainingResponse[]> response) {
                if (response.isSuccessful() && response.body() != null && response.body().length > 0) {
                    // Assuming there's only one PDF path, you can modify as per your API response structure
                    String pdfPath = response.body()[0].getPdfPath()+response.body()[0].getPdfFilename(); // Adjust as per your JSON structure


                    // Display PDF path in a Toast message


                    loadPdfFromUrl(pdfPath);
                } else {
                    Toast.makeText(TraningCentreActivity.this, "Failed to fetch PDF path", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrainingResponse[]> call, Throwable t) {
                Toast.makeText(TraningCentreActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadPdfFromUrl(String pdfUrl) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                downloadAndDisplayPdf(pdfUrl);
            }
        } else {
            downloadAndDisplayPdf(pdfUrl);
        }
    }
    private void downloadAndDisplayPdf(String pdfUrl) {
        progressBar.setVisibility(View.VISIBLE);

        new Thread(() -> {
            try {
                URL url = new URL(pdfUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                String contentType = connection.getContentType();

                if (!"application/pdf".equals(contentType)) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        showToast("The file is not a valid PDF");
                    });
                    return;
                }

                int fileLength = connection.getContentLength();
                File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "downloaded.pdf");

                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(pdfFile)) {

                    byte[] buffer = new byte[1024];
                    int length;
                    long total = 0;
                    while ((length = inputStream.read(buffer)) > 0) {
                        total += length;
                        if (fileLength > 0) {
                            int progress = (int) (total * 100 / fileLength);
                            runOnUiThread(() -> progressBar.setProgress(progress));
                        }
                        outputStream.write(buffer, 0, length);
                    }
                }

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    if (pdfFile.exists()) {
                        pdfView.fromFile(pdfFile)
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        showToast("PDF loaded with " + nbPages + " pages.");
                                    }
                                })
                                .load();
                    } else {
                        showToast("Failed to load PDF file");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    showToast("Failed to download PDF");
                });
            }
        }).start();
    }





    private void showToast(String message) {
        Toast.makeText(TraningCentreActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String combinedUrls = pdfUrl;
                loadPdfFromUrl(combinedUrls.split("\n\n")[0]);
            } else {
                showToast("Permission denied");
            }
        }
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
