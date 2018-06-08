package com.fly.newstart.viewmode;

import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

import rx.functions.Action1;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.viewmode
 * 作    者 : FLY
 * 创建时间 : 2018/6/8
 * <p>
 * 描述:
 */

public class ViewModel extends BaseViewModel {

    private MutableLiveData<ResponseJson<List<Object>>> enterOutFormsEntityList = new MutableLiveData<>();

    public MutableLiveData<ResponseJson<List<Object>>> getEnterOutFormsEntityList() {
        return enterOutFormsEntityList;
    }

    public void getEnterOutFormsEntityListInfo(HashMap<String, Object> uploadFilter) {
        submitRequest(Model.getEnterOutFormsEntityListInfo(uploadFilter), new Action1<ResponseJson<List<Object>>>() {
            @Override
            public void call(ResponseJson<List<Object>> listResponseJson) {
                enterOutFormsEntityList.postValue(listResponseJson);
            }
        });
    }
}
