package com.fly.newstart.webview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ValueCallback;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

public class WebViewActivity extends BaseActivity {

    private static final int FILECHOOSER_RESULTCODE = 0x0011;
    private static final int REQ_CHOOSE = 0x0010;
    private WebView webView;

    private String url = " http://39.107.113.91:8888/hero-wx/questionnaire/getQuestionByInteractionId?token=b45ca46d4c4240e08857a083b5063836&interactionId=3&pictureNum=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initWeb();
        loadUrl(url);
    }

    private void initWeb() {
        webView = (WebView) findViewById(R.id.web_web);
    }

    private void loadUrl(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowFileAccess(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                Log.i("打印日志","网页加载失败");
            }
        });
        //进度条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    Log.i("打印日志","加载完成");
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadCallbackAboveL = uploadMsg;
        dispatchTakePictureIntent();
    }

    //5.0以下的掉用
    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        dispatchTakePictureIntent();
    }

    //拍照
    private void dispatchTakePictureIntent() {
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        String IMAGE_UNSPECIFIED = "image/*";
        innerIntent.setType(IMAGE_UNSPECIFIED); // 查看类型
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQ_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE || requestCode == REQ_CHOOSE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                //onActivityResultAboveL(requestCode, data);
            } else if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&&webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
