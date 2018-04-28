package com.fly.newstart.pdf;

import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fly.newstart.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.UnsupportedEncodingException;

public class PDFViewActivity1 extends AppCompatActivity {

    private WebView pdfViewerWeb;
    private String docPath = "http://sjfosstest.biz-united.com.cn/upload/file/1520491432758_df8b72b5-3bbe-4c76-a2e4-868a89caec25.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        findViewById(R.id.ll_pdf).setVisibility(View.GONE);
        findViewById(R.id.wb_agreement_detail).setVisibility(View.VISIBLE);
        pdfViewerWeb = (WebView) findViewById(R.id.wb_agreement_detail);
        init();
    }

    private void init() {
        WebSettings settings = pdfViewerWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);
        pdfViewerWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        pdfViewerWeb.setWebChromeClient(new WebChromeClient());
        pdfViewerWeb.loadUrl("file:///android_asset/pdfjs/web/viewer.html?url=" + docPath);
    }


}
