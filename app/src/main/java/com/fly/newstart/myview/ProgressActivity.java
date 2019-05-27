package com.fly.newstart.myview;

import android.os.Bundle;
import android.widget.SeekBar;

import com.fly.myview.chart.LineChartView;
import com.fly.myview.chart.StatisticsLineChartView;
import com.fly.myview.progressbar.ArcProgress;
import com.fly.myview.progressbar.SaleProgressView;
import com.fly.myview.progressbar.StageProgress;
import com.fly.myview.progressbar.UpdataAPPProgressBar;
import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;
import com.shangyi.android.utils.ListUtils;

import java.util.ArrayList;
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

    private SaleProgressView saleProgressView;
    private ArcProgress arcProgress;
    private UpdataAPPProgressBar updataAPPProgressBar;
    private StageProgress stageProgress;
    private LineChartView lineChartView;
    private StatisticsLineChartView statisticsLineChartView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        saleProgressView = (SaleProgressView) findViewById(R.id.spv);
        arcProgress = (ArcProgress) findViewById(R.id.arcProgress);
        updataAPPProgressBar = (UpdataAPPProgressBar) findViewById(R.id.updata);
        stageProgress = (StageProgress) findViewById(R.id.stageProgress);
        statisticsLineChartView = (StatisticsLineChartView) findViewById(R.id.statisticsLine);
        stageProgress.setMax(200);
        stageProgress.setProgress(200);
        stageProgress.setThisStage(1);
        stageProgress.setText("7日目标：150-400(min)");
        lineChartView = (LineChartView) findViewById(R.id.line);
        List<Double> list = new ArrayList<>();
        list.add(100d);
        list.add(100d);
        list.add(58d);
        list.add(58d);
        list.add(50.2d);
        list.add(50.2d);
        lineChartView.setLines(list);
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
        List<Integer> doubles = ListUtils.newArrayList(50,60,70,75,80,80,85,90,100,55,55,60,70,75,80,80,85,90,100,55,60,70,75,80,80,85,90,100,100,55,60,70,75,80,80,85,90,100);
        List<String> strings = ListUtils.newArrayList("0","40","80","120","160","200","240");
        statisticsLineChartView.setLines(doubles);
        statisticsLineChartView.setNums(strings);
    }
}
