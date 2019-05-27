package com.fly.newstart.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.fly.newstart.R;
import com.fly.newstart.ioc.InjectManager;
import com.fly.newstart.network.NetworkManager;

/**
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.common.base
 * 作    者 : FLY
 * 创建时间 : 2017/8/7
 * <p>
 * 描述: Activity的基类
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * tag,用于打印log,如L.i(TAG,"xxxxxx")
     */
    protected final String TAG = "FLY." + this.getClass().getSimpleName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//矢量图片资源向后兼容
    }

    ViewGroup rootView;
    protected View mProgressView;
    private Dialog mDialogError;


    @Override
    public  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 帮助所有子类完成注入工作
        InjectManager.inject(this);
        NetworkManager.getDefault().registerObserver(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        rootView = (ViewGroup) getWindow().getDecorView();
        initProgressLayout();
    }

    private void initProgressLayout() {
        if (mProgressView == null) {
            mProgressView = getLayoutInflater().inflate(R.layout.common_loading_layout, rootView
                    , false);
            mProgressView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            setProgressVisible(false);
            rootView.addView(mProgressView);
        }
    }

    public void setProgressVisible(boolean show) {
        if (mProgressView != null) {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void error(String error) {
        error(error, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public void error(String error, DialogInterface.OnClickListener onClickListener) {
        setProgressVisible(false);
        if (!TextUtils.isEmpty(error)) {
            if (mDialogError != null) {
                mDialogError.dismiss();
                mDialogError = null;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(error);
            builder.setPositiveButton(R.string.common_confirm, onClickListener);
            mDialogError = builder.show();
        }
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().removeObserver(this);
    }
}
