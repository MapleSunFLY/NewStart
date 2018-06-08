package com.fly.newstart.viewmode;

import com.fly.newstart.Request.RestMethodEnum;
import com.fly.newstart.Request.RestRequest;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.viewmode
 * 作    者 : FLY
 * 创建时间 : 2018/6/8
 * <p>
 * 描述:
 */

public class Model {

    public static Observable<ResponseJson<List<Object>>> getEnterOutFormsEntityListInfo(HashMap<String, Object> uploadFilter) {
        return RestRequest.<ResponseJson<List<Object>>>builder()
                .addPublicParaMap(uploadFilter)
                .addPublicPara("token","")
                .url(0)
                .restMethod(RestMethodEnum.POST)
                .setToJsonType(new TypeToken<ResponseJson<List<Object>>>() {
                }.getType())
                .requestJson().map(new Func1<ResponseJson<List<Object>>, ResponseJson<List<Object>>>() {
                    @Override
                    public ResponseJson<List<Object>> call(ResponseJson<List<Object>> listResponseJson) {
                        return listResponseJson;
                    }
                });
    }
}
