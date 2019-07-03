package com.fly.newstart.load;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fly.myview.loading.core.LoadingService;
import com.fly.myview.loading.core.LoadingSir;
import com.fly.myview.loading.page.Page;
import com.fly.newstart.R;

/**
 * 包    名 : com.fly.newstart.load
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述:
 */
public class FragmentA extends Fragment {

    protected LoadingService mBaseLoadService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.activity_loading_convertor, null);
        mBaseLoadService = LoadingSir.getDefault().register(rootView, new Page.OnReloadListener() {
            @Override
            public void onReload(View v) {

            }
        });
        return mBaseLoadService.getLoadLayout();
    }
}