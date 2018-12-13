package com.fly.newstart.myview;

import android.os.Bundle;
import android.widget.SeekBar;

import com.fly.myview.progressbar.ArcProgress;
import com.fly.myview.progressbar.SaleProgressView;
import com.fly.myview.progressbar.UpdataAPPProgressBar;
import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;

import java.util.List;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.myview
 * 作    者 : FLY
 * 创建时间 : 2017/9/26
 * <p>
 * 描述: 自定义进度条
 */

public class ProgressActivity extends BaseActivity {

    SaleProgressView saleProgressView;
    ArcProgress arcProgress;
    UpdataAPPProgressBar updataAPPProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        saleProgressView = (SaleProgressView) findViewById(R.id.spv);
        arcProgress = (ArcProgress) findViewById(R.id.arcProgress);
        updataAPPProgressBar = (UpdataAPPProgressBar) findViewById(R.id.updata);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saleProgressView.setTotalAndCurrentCount(100, i);
                arcProgress.setProgress(i);
                updataAPPProgressBar.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
