package com.fly.newstart.myview;

import android.os.Bundle;
import android.widget.SeekBar;

import com.fly.myview.progressbar.SaleProgressView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        final SaleProgressView saleProgressView = (SaleProgressView ) findViewById(R.id.spv);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seek);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                saleProgressView.setTotalAndCurrentCount(100,i);
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
