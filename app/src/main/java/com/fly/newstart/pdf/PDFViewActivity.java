package com.fly.newstart.pdf;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fly.newstart.R;
import com.fly.newstart.utils.SPUtils;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;

public class PDFViewActivity extends AppCompatActivity {

    private PDFView pdfView;
    private LinearLayout mLlBtn;
    private TextView pageTv, pageTv1;
    private int p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        findViewById(R.id.ll_pdf).setVisibility(View.VISIBLE);
        findViewById(R.id.wb_agreement_detail).setVisibility(View.GONE);
        mLlBtn = (LinearLayout) findViewById(R.id.llBtn);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pageTv = (TextView) findViewById(R.id.pageTv);
        pageTv1 = (TextView) findViewById(R.id.pageTv1);
        initPDF();
        //initPDF1();
    }
    private void initPDF() {
        pdfView.setVisibility(View.VISIBLE);
        final int myPage = 0;
        //选择pdf
        pdfView.fromAsset("pdf1.pdf")
//                .pages(0, 2, 3, 4, 5); // 把0 , 2 , 3 , 4 , 5 过滤掉
                //是否允许翻页，默认是允许翻页
                .enableSwipe(true)
                //pdf文档翻页是否是左右滑动翻页
                .swipeHorizontal(false)
                //
                .enableDoubletap(false)
                //设置默认显示第0页
                .defaultPage(0)
                //允许在当前页面上绘制一些内容，通常在屏幕中间可见。
//                .onDraw(onDrawListener)
//                // 允许在每一页上单独绘制一个页面。只调用可见页面
                .onDrawAll(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                    }
                })
                //设置加载监听
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        pageTv.setText(nbPages + "");
                        pageTv1.setText(myPage +  "/");
                    }
                })
                //设置翻页监听
                .onPageChange(new OnPageChangeListener() {

                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        p = page;
                        pageTv1.setText(page + "/");
                    }
                })
                //设置页面滑动监听
                .onPageScroll(new OnPageScrollListener() {
                    @Override
                    public void onPageScrolled(int page, float positionOffset) {
                        if (positionOffset >=0.7f) {
                            mLlBtn.setVisibility(View.VISIBLE);
                        }else {
                            mLlBtn.setVisibility(View.GONE);
                        }
                    }
                })
//                .onError(onErrorListener)
                // 首次提交文档后调用。
//                .onRender(onRenderListener)
                // 渲染风格（就像注释，颜色或表单）
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                // 改善低分辨率屏幕上的渲染
                .enableAntialiasing(true)
                // 页面间的间距。定义间距颜色，设置背景视图
                .spacing(0)
                .load();
    }
}
