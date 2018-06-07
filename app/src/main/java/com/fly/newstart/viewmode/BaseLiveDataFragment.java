package com.fly.newstart.viewmode;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseFragment;
import com.fly.newstart.utils.LogUtil;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.viewmode
 * 作    者 : FLY
 * 创建时间 : 2018/6/4
 * <p>
 * 描述:
 */

public class BaseLiveDataFragment<T extends BaseViewModel> extends BaseFragment {
    protected T mViewModel;

    protected void observeErrorLiveData(BaseViewModel viewModel) {
        if (viewModel != null) {
            viewModel.getErrorLiveData().observe(this, new Observer<RestErrorInfo>() {
                @Override
                public void onChanged(@Nullable RestErrorInfo restErrorInfo) {
                    if (restErrorInfo != null)
                        error(restErrorInfo.message);
                }
            });
        }
    }

    public void initViewModel(Class<T> modelClass) {
        this.mViewModel = registerViewModel(modelClass, false, true);//ViewModelProviders.of(getBaseActivity()).get(this.toString() + ":" + modelClass, modelClass);
    }

    public void initViewModel(Class<T> modelClass, boolean isSingle) {
        this.mViewModel = registerViewModel(modelClass, isSingle, true);
    }

    public void initViewModel(Class<T> modelClass, String viewModelName) {
        this.mViewModel = registerViewModel(modelClass, viewModelName, true);
    }

    public void initViewModel(Class<T> modelClass, String viewModelName, boolean isRegisterError) {
        this.mViewModel = registerViewModel(modelClass, viewModelName, isRegisterError);
    }

    public void initViewModel(Class<T> modelClass, boolean isSingle, boolean isRegisterError) {
        this.mViewModel = registerViewModel(modelClass, isSingle, isRegisterError);
    }

    public <Q extends BaseViewModel> Q registerViewModel(Class<Q> modelClass, boolean isSingle) {
        return registerViewModel(modelClass, isSingle, false);
    }

    public <Q extends BaseViewModel> Q registerViewModel(Class<Q> modelClass, boolean isSingle, boolean isRegisterError) {
        String key = modelClass.getCanonicalName();
        if (key == null) {
            key = getClass().getCanonicalName();
        }
        return registerViewModel(modelClass, isSingle ? (this.toString() + ":" + key) : key, isRegisterError);
    }

    public <Q extends BaseViewModel> Q registerViewModel(Class<Q> modelClass, String viewModelName, boolean isRegisterError) {
        Q mViewModel = ViewModelProviders.of(getBaseActivity()).get(viewModelName, modelClass);
        LogUtil.print("--------------------F--------------------");
        LogUtil.print("---modelClass:" + modelClass + " viewModelName:" + viewModelName + " :isRegisterError:" + isRegisterError + " obj:" + (mViewModel != null ? mViewModel.toString() : null));
        LogUtil.print("----------------------------------------");
        if (isRegisterError)
            observeErrorLiveData(mViewModel);
        mViewModel.setActivity(getBaseActivity());
        return mViewModel;
    }

    public <Q extends BaseViewModel> Q registerViewModel(Class<Q> modelClass) {
        return registerViewModel(modelClass, true);
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

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}