package com.PK.astro;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://www.pkgastrologer.com/api/";
    private ApiService apiService;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 2296;
    ProgressBar progressBar;
    PDFView pdfView;
    String pdfUrl;

    private static final String TAG = ContactActivity.class.getSimpleName();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        pdfView = findViewById(R.id.pdfView);  // Assuming you have a PDFView in your layout

        progressBar = findViewById(R.id.progressBar);  // Assuming you have a ProgressBar in your layout

        // Create an instance of ApiService
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Call the method to get AboutUs data
        Call<List<AboutItem>> call = apiService.getAboutUs();
        call.enqueue(new Callback<List<AboutItem>>() {
            @Override
            public void onResponse(Call<List<AboutItem>> call, Response<List<AboutItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AboutItem> aboutItems = response.body();
                    // Assuming there is only one PDF in the response (or you can loop through all items)
                    if (!aboutItems.isEmpty()) {
                        AboutItem item = aboutItems.get(0); // Get the first item
                        String pdfName = item.getPdfFileName();
                        String pdfPath = item.getPdfPath();
                        // Show PDF information in a Toast
                        String toastMessage = pdfPath+pdfName;

                        loadPdfFromUrl(toastMessage);

                    } else {
                        Log.e(TAG, "Empty response body!");
                    }
                } else {
                    Log.e(TAG, "Failed to fetch data!");
                }
            }

            @Override
            public void onFailure(Call<List<AboutItem>> call, Throwable t) {
                Log.e(TAG, "Error fetching data: " + t.getMessage());
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
        Toast.makeText(ContactActivity.this, message, Toast.LENGTH_SHORT).show();
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
