package com.fly.newstart.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.fly.myview.dialog.CommonDialog;
import com.fly.myview.dialog.progress.ProgressDialog;
import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.shangyi.android.utils.ViewUtils;

public class DialogActivity extends BaseActivity implements View.OnClickListener{

    private CommonDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.btn_center).setOnClickListener(this);
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_top).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
        findViewById(R.id.btn_bottom).setOnClickListener(this);
        findViewById(R.id.btn_progress).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_center:
                dialog = new CommonDialog(this);

                dialog.contentView(R.layout.dialog_center)
                        .animType(CommonDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true).show();
                dialog.findViewById(R.id.tv_confirm).setOnClickListener(this);
                dialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Log.d("FLY110", "dismiss: 1234");
                    }
                });
                break;
            case R.id.btn_left:
                CommonDialog dialog_left = new CommonDialog(this);

                dialog_left.contentView(R.layout.dialog_left)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
                        .dimAmount(0.5f)
                        .gravity(Gravity.LEFT | Gravity.CENTER)
                        .animType(CommonDialog.AnimInType.LEFT)
                        .canceledOnTouchOutside(true).show();

                break;
            case R.id.btn_top:
                CommonDialog dialog_top = new CommonDialog(this);

                dialog_top.contentView(R.layout.dialog_photo)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                        .dimAmount(0.5f)
                        .gravity(Gravity.TOP)
                        .offset(0, ViewUtils.dip2px(this, 48))
                        .animType(CommonDialog.AnimInType.TOP)
                        .canceledOnTouchOutside(true).show();


                break;
            case R.id.btn_right:
                CommonDialog dialog_right = new CommonDialog(this);

                dialog_right.contentView(R.layout.dialog_right)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))

                        .gravity(Gravity.RIGHT | Gravity.CENTER)
                        .animType(CommonDialog.AnimInType.RIGHT)
                        .offset(20, 0)
                        .canceledOnTouchOutside(true).show();

                break;
            case R.id.btn_bottom:
                CommonDialog dialog_bottom = new CommonDialog(this);

                dialog_bottom.contentView(R.layout.dialog_photo)
                        .gravity(Gravity.BOTTOM)
                        .animType(CommonDialog.AnimInType.BOTTOM)
                        .canceledOnTouchOutside(true).show();


                break;
            case R.id.btn_progress:

                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.color_iv(0xffffffff)
                        .color_bg_progress(0xffffffff)
                        .colors_progress(0xff2a5caa).show();
                break;
            case R.id.tv_confirm:
                dialog.dismiss();
                break;
            case R.id.tv_cancel:
                dialog.dismiss();
                break;
        }

    }
}
