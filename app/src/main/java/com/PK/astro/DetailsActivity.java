package com.PK.astro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {


    private static final String BASE_URL = "https://www.pkgastrologer.com/api/";
    private static final String TAG = "DetailsActivity";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST = 2296;

    TextView title2, title1, date,subservice,tamilname,norecord;
    ImageView image;

    String pdfUrl;
    PDFView pdfView;

    WebView des;
    LottieAnimationView lottieAnimationView;
    private int pageNumber = 0;
    private boolean isPdfLoading = false; // To track PDF loading state

    Button viewall,back;
    CardView cardView;




    private ImageView playButton;
    private ImageView pauseButton;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Handler handler = new Handler();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        pdfView = findViewById(R.id.pdfView);
        viewall=findViewById(R.id.viewall);
        back=findViewById(R.id.btnNextPage);

        norecord=findViewById(R.id.norecord);
        tamilname=findViewById(R.id.t4);
        subservice= findViewById(R.id.subservice);
        // Assuming you have a PDFView in your layout
        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.t3);
        des = findViewById(R.id.t7);
        image = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);

        // Create Retrofit instance
        cardView=findViewById(R.id.cardView);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create ApiService instance
        ApiService apiService = retrofit.create(ApiService.class);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        String subId = intent.getStringExtra("SUB_ID");
        String mainId = intent.getStringExtra("MAIN_ID");
        String image1 = intent.getStringExtra("IMAGE");




        // Create request body
        YourRequestBodyModel requestBody = new YourRequestBodyModel();
        requestBody.setMain_catid(mainId);
        requestBody.setSub_catid(subId);

        // Make API call
        // Make API call
        Call<List<YourResponseItem>> call = apiService.getDetail(requestBody);
        call.enqueue(new Callback<List<YourResponseItem>>() {
            @Override
            public void onResponse(Call<List<YourResponseItem>> call, Response<List<YourResponseItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<YourResponseItem> data = response.body();
                    for (YourResponseItem item : data) {
                        Log.d(TAG, "Service Name: " + item.getServiceName());
                        Log.d(TAG, "Description: " + item.getDescription());

                        title1.setText(item.getServiceName());
                        subservice.setText(item.getSubName());
                        title2.setText(item.getTitle());
                        tamilname.setText(item.getTamilName());


                        LinearLayout audilayou;
                        audilayou=findViewById(R.id.audilayou);


                        image.setVisibility(View.VISIBLE);
                        cardView.setVisibility(View.VISIBLE);


                        String description = item.getDescription();

                        Spanned spanned = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY, null, new MyTagHandler());


                        String htmlContent = item.getDescription();

                        // Load HTML content
                        des.loadData(htmlContent, "text/html", "UTF-8");

                        // Optional: Enable JavaScript (if needed)
                        des.getSettings().setJavaScriptEnabled(true);

                        // Optional: Handle URL clicks in the WebView
                        des.setWebViewClient(new WebViewClient());

                        Glide.with(DetailsActivity.this)
                                .load( item.getImagepath()+"/"+item.getImage())
                                .placeholder(R.drawable.placeholder)
                                .into(image);

                        playButton = findViewById(R.id.play);
                        pauseButton = findViewById(R.id.pause);
                        seekBar = findViewById(R.id.seekbar);

                        if (!item.getAudio().isEmpty()){
                            audilayou.setVisibility(View.VISIBLE);
                        }else {
                            audilayou.setVisibility(View.GONE);
                        }

                        mediaPlayer = new MediaPlayer();
                        try {
                            String audioUrl = item.getAudioPath()+"/"+item.getAudio();
                            mediaPlayer.setDataSource(audioUrl); // Replace with your audio URL


                            mediaPlayer.prepare(); // might take long! (for buffering, etc)
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        seekBar.setMax(mediaPlayer.getDuration());

                        playButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isPlaying) {
                                    mediaPlayer.start();
                                    isPlaying = true;
                                    playButton.setVisibility(View.GONE);
                                    pauseButton.setVisibility(View.VISIBLE);
                                    updateSeekBar();
                                }
                            }
                        });

                        pauseButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isPlaying) {
                                    mediaPlayer.pause();
                                    isPlaying = false;
                                    playButton.setVisibility(View.VISIBLE);
                                    pauseButton.setVisibility(View.GONE);
                                }
                            }
                        });

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                isPlaying = false;
                                playButton.setVisibility(View.VISIBLE);
                                pauseButton.setVisibility(View.GONE);
                            }
                        });

                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser) {
                                    mediaPlayer.seekTo(progress);
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {}

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {}
                        });



                        // Concatenate pdf path and pdf name
                        String pdfUrl1 = item.getPdfPath() + item.getPdf();
                        loadPdfFromUrl(pdfUrl1);
                        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent1 = new Intent(view.getContext(), PdfActivity.class);
                                intent1.putExtra("PDF_URL", pdfUrl1);
                                startActivity(intent1);
                            }
                        });

                    }
                } else {
                    viewall.setVisibility(View.GONE);
                    back.setVisibility(View.GONE);
                    pdfView.setVisibility(View.GONE);
                    image.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.GONE);
                    norecord.setVisibility(View.VISIBLE);
                    Log.e(TAG, "Error: " + response.code() + " " + response.message());
                    runOnUiThread(() -> showToast("Error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<YourResponseItem>> call, Throwable t) {
                Log.e(TAG, "Failed to fetch data: " + t.getMessage());
                viewall.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                pdfView.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.GONE);
                norecord.setVisibility(View.VISIBLE);
                runOnUiThread(() -> showToast("No Record Available"));
            }
        });


    }

    private void loadPdfFromUrl(String pdfUrl) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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


        new Thread(() -> {
            try {
                URL url = new URL(pdfUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                String contentType = connection.getContentType();

                if (!"application/pdf".equals(contentType)) {
                    runOnUiThread(() -> {

                        showToast("The file is not a valid PDF");
                        viewall.setVisibility(View.GONE);
                        back.setVisibility(View.GONE);
                        pdfView.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.GONE);
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

                        }
                        outputStream.write(buffer, 0, length);
                    }
                }

                runOnUiThread(() -> {

                    if (pdfFile.exists()) {
                        pdfView.fromFile(pdfFile)
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        showToast("PDF loaded with " + nbPages + " pages.");
                                    }
                                })
                                .defaultPage(pageNumber)
                                .enableSwipe(true)
                                .swipeHorizontal(true)
                                .onPageChange(DetailsActivity.this)
                                .enableAnnotationRendering(true)
                                .onLoad(this)
                                .load();

                        viewall.setVisibility(View.VISIBLE);
                        back.setVisibility(View.VISIBLE);
                        pdfView.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }else {
                        showToast("Failed to load PDF file");
                        viewall.setVisibility(View.GONE);
                        back.setVisibility(View.GONE);
                        pdfView.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.GONE);
                    }
                });



            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {

                    showToast("Failed to download PDF");
                    viewall.setVisibility(View.GONE);
                    back.setVisibility(View.GONE);
                    pdfView.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.GONE);
                });
            }
        }).start();
    }





    private void showToast(String message) {
        Toast.makeText(DetailsActivity.this, message, Toast.LENGTH_SHORT).show();
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
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        TextView pageTextView = findViewById(R.id.Page);
        pageTextView.setText(String.format("%s %s / %s", "PDF", page + 1, pageCount));

    }

    @Override
    public void loadComplete(int nbPages) {
        printBookMarkTree(pdfView.getTableOfContents(), "-");
    }

    private void printBookMarkTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            if (b.hasChildren()) {
                printBookMarkTree(b.getChildren(), sep + "-");
            }
        }
    }



    public void nextPage(View view) {
        if (pageNumber < pdfView.getPageCount() - 1) {
            pageNumber++;
            pdfView.jumpTo(pageNumber, true);
        } else {
            Toast.makeText(this, "Already on the last page", Toast.LENGTH_SHORT).show();
        }
    }



    public void back(View view) {
        if (pageNumber > 0) {
            pageNumber--;
            pdfView.jumpTo(pageNumber, true);
        } else {
            Toast.makeText(this, "Already on the first page", Toast.LENGTH_SHORT).show();
        }
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

    private void updateSeekBar() {
        if (mediaPlayer != null && isPlaying) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            }, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
