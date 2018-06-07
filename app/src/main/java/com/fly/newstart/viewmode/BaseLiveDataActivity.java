package com.fly.newstart.viewmode;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;

import com.fly.newstart.common.base.BaseActivity;
import com.fly.newstart.utils.LogUtil;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.viewmode
 * 作    者 : FLY
 * 创建时间 : 2018/5/25
 * <p>
 * 描述: base LiveData
 */

public class BaseLiveDataActivity<T extends BaseViewModel> extends BaseActivity {
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
        this.mViewModel = registerViewModel(modelClass, false,true);
    }

    public void initViewModel(Class<T> modelClass, boolean isSingle) {
        this.mViewModel = registerViewModel(modelClass, isSingle,true);
    }

    public <Q extends BaseViewModel> Q registerViewModel(Class<Q> modelClass, boolean isSingle) {
        return registerViewModel(modelClass, isSingle, true);
    }

    public <Q extends BaseViewModel> Q registerViewModel(Class<Q> modelClass, boolean isSingle, boolean isRegisterError) {
        String key = modelClass.getCanonicalName();
        if (key == null) {
            key = getClass().getCanonicalName();
        }
        return registerViewModel(modelClass, isSingle ? (this.toString() + ":" + key) : key, isRegisterError);
    }

    public void initViewModel(Class<T> modelClass, String viewModelName) {
        this.mViewModel = registerViewModel(modelClass, viewModelName, true);
    }

    public void initViewModel(Class<T> modelClass, String viewModelName, boolean isRegisterError) {
        this.mViewModel = registerViewModel(modelClass, viewModelName, isRegisterError);
    }

    public <Q extends BaseViewModel> Q registerViewModel(Class<Q> modelClass,String viewModelName,boolean isRegisterError){
        Q mViewModel = ViewModelProviders.of(this).get(viewModelName, modelClass);
        LogUtil.print("-----------------A-----------------------");
        LogUtil.print("-A--modelClass:"+modelClass+" viewModelName:"+viewModelName+" :isRegisterError:"+isRegisterError+" obj:"+(mViewModel!=null?mViewModel.toString():null));
        LogUtil.print("----------------------------------------");
        if (isRegisterError)
            observeErrorLiveData(mViewModel);
        return mViewModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
