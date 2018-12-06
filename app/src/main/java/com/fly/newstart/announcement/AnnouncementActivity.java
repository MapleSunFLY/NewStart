package com.fly.newstart.announcement;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.fly.myview.cloze.announcement.UPMarqueeView;
import com.fly.newstart.R;
import com.shangyi.android.http.real.SimpleRetrofit;
import com.shangyi.android.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementActivity extends AppCompatActivity {

    private UPMarqueeView viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        viewFlipper = (UPMarqueeView) findViewById(R.id.viewFlipper);
        List<UPMarqueeView.UPMarqueeViewData> datas = new ArrayList<UPMarqueeView.UPMarqueeViewData>();
        datas.add(new UPMarqueeView.UPMarqueeViewData(1,"热议","aaaaaaaaaaaaaaaaa"));
        datas.add(new UPMarqueeView.UPMarqueeViewData(2,"热议","bbbbbbbbbbbbbbbbb"));
        datas.add(new UPMarqueeView.UPMarqueeViewData(3,"热议","ccccccccccccccccc"));
        datas.add(new UPMarqueeView.UPMarqueeViewData(4,"热议","ddddddddddddddddd"));
        datas.add(new UPMarqueeView.UPMarqueeViewData(5,"热议","eeeeeeeeeeeeeeeee"));
        datas.add(new UPMarqueeView.UPMarqueeViewData(6,"热议","fffffffffffffffff"));
        datas.add(new UPMarqueeView.UPMarqueeViewData(7,"热议","ggggggggggggggggg"));
        viewFlipper.setViews(datas);
        viewFlipper.setOnClickListener(new UPMarqueeView.OnClickListener() {
            @Override
            public void onClick(View view, UPMarqueeView.UPMarqueeViewData data) {
                Toast.makeText(AnnouncementActivity.this,data.getValue(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
