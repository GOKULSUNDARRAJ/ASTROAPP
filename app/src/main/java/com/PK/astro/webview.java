package com.PK.astro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class webview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the PDF URL
        String pdfUrl = "https://pkgastrologer.com/admin/img/pdf/001_07052024.pdf";
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl);
    }
}