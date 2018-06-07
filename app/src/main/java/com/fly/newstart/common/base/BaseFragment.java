package com.fly.newstart.common.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.common.base
 * 作    者 : FLY
 * 创建时间 : 2018/6/4
 * <p>
 * 描述:
 */

public class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            baseActivity = (BaseActivity) context;
        }

    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public void setProgressVisible(boolean isVisible) {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        if (baseActivity != null)
            baseActivity.setProgressVisible(isVisible);
    }
}
