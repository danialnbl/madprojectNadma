package com.example.madproject;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {

    // UI components
    private WebView webView;
    private Button privacyPolicyButton, termsAndConditionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize the WebView and buttons
        webView = findViewById(R.id.webView);
        privacyPolicyButton = findViewById(R.id.privacypolicy);
        termsAndConditionsButton = findViewById(R.id.termandconditions);

        // Enable JavaScript for loading the PDF
        webView.getSettings().setJavaScriptEnabled(true);

        // Set WebView client
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, android.net.Uri errorUrl) {
//                super.onReceivedError(view, request, errorUrl);
//            }
        });

        // Button to open Privacy Policy
        privacyPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf("privacy_policy.pdf");  // PDF name from the raw folder
            }
        });

        // Button to open Terms and Conditions
        termsAndConditionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf("terms_and_conditions.pdf");  // PDF name from the raw folder
            }
        });
    }

    // Function to open PDF from the raw folder
    private void openPdf(String fileName) {
        // Open PDF file from raw folder using file URL
        String filePath = "file:///android_res/raw/" + fileName;
        webView.loadUrl(filePath);  // Load the PDF in WebView
    }
}
