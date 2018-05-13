package com.fly.newstart.circlemenu;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseActivity;

public class CircleMenuActivity extends BaseActivity {

    private RadioButton mRB1, mRB2, mRB3, mRB4, mRB5, mRB6;

    private ImageView mImgMenu;
    private boolean is = true;

    private RadioButton mRB0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_menu);
        initView();
    }

    private void initView() {
        mImgMenu = (ImageView) findViewById(R.id.imgMenu);
        mRB1 = (RadioButton) findViewById(R.id.rb1);
        mRB2 = (RadioButton) findViewById(R.id.rb2);
        mRB3 = (RadioButton) findViewById(R.id.rb3);
        mRB4 = (RadioButton) findViewById(R.id.rb4);
        mRB5 = (RadioButton) findViewById(R.id.rb5);
        mRB6 = (RadioButton) findViewById(R.id.rb6);
        mRB6.setFocusable(false);
        mRB0 = mRB1;
        mRB0.setEnabled(false);
        mImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is) {
                    ObjectAnimator.ofFloat(mImgMenu, "rotation", 45.0f, 0.0f).setDuration(300).start();
                    openAnimator(mRB1,600);
                    openAnimator(mRB2,500);
                    openAnimator(mRB3,400);
                    openAnimator(mRB4,300);
                    openAnimator(mRB5,200);
                    openAnimator(mRB6,100);
                    is = true;
                } else {
                    ObjectAnimator.ofFloat(mImgMenu, "rotation", 0.0f, 45.0f).setDuration(300).start();
                    endAnimator(mRB1,600);
                    endAnimator(mRB2,500);
                    endAnimator(mRB3,400);
                    endAnimator(mRB4,300);
                    endAnimator(mRB5,200);
                    endAnimator(mRB6,100);
                    is = false;
                }
            }
        });

        initRbListener(mRB1,true);
        initRbListener(mRB2,true);
        initRbListener(mRB3,true);
        initRbListener(mRB4,true);
        initRbListener(mRB5,false);
        initRbListener(mRB6,false);
    }

    private void initRbListener(final RadioButton radioButton,boolean is){
        if (!is){
            radioButton.setEnabled(false);
            radioButton.setTextColor(getResources().getColor(R.color.color_white));
            radioButton.setBackground(getResources().getDrawable(R.drawable.bg_circular_ring_gray_6));
            return;
        }
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton.isChecked()){
                    mRB0.setEnabled(true);
                    mRB0.setTextColor(getResources().getColor(R.color.color_common_red));
                    mRB0.setBackground(getResources().getDrawable(R.drawable.bg_circular_ring_gray));
                    radioButton.setEnabled(false);
                    radioButton.setTextColor(getResources().getColor(R.color.color_white));
                    radioButton.setBackground(getResources().getDrawable(R.drawable.bg_circular_ring_gray_red));
                    mRB0 = radioButton;
                }
            }
        });
    }


    private void openAnimator(View view ,float end) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        alphaAnimator.setDuration(300);
        alphaAnimator.start();
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(view, "translationX", end, 0);
        translationXAnimator.setDuration(300);
        animatorSet.playTogether(alphaAnimator, translationXAnimator);
        animatorSet.start();

    }

    private void endAnimator(View view ,float end) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        alphaAnimator.setDuration(300);
        alphaAnimator.start();
        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, end);
        translationXAnimator.setDuration(300);
        animatorSet.playTogether(alphaAnimator, translationXAnimator);
        animatorSet.start();
    }


}
