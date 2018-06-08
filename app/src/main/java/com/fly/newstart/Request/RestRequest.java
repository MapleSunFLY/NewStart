package com.fly.newstart.Request;

import android.text.TextUtils;

import com.fly.newstart.R;
import com.fly.newstart.common.base.BaseApplication;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.Request
 * 作    者 : FLY
 * 创建时间 : 2018/6/8
 * <p>
 * 描述:
 */

public class RestRequest <T> extends BaseRequest<T> {
    public static final MediaType MEDIA_JSON = MediaType.parse("application/json;");

    public static <T> BaseRequest<T> builder() {
        BaseRequest<T> request = new RestRequest<T>();
        request.https(false);
        request.setDefaultConnectTime();
        request.headUrl(BaseApplication.getAppContext().getString(R.string.defualt_http_url));
        return request;
    }

    public RestRequest() {
        super();
    }

    @Override
    public Request getOKHttpRequest() {
        Request request = null;
        if (getRestMethodEnum() == RestMethodEnum.POST) {
            String body=getBodyObj();
            RequestBody requestBody=null;
            if (getRequestBody() ==null){
                if(TextUtils.isEmpty(body)){
                    requestBody= RequestBody.create(MEDIA_JSON,new byte[0]);
                }else {
                    requestBody = RequestBody.create(MEDIA_JSON,body );
                }
            }else {
                requestBody = getRequestBody();
            }
            request = new Request.Builder().url(getUrl())
                    .post(requestBody).build();
        }else if (getRestMethodEnum() == RestMethodEnum.GET) {
            request = new Request.Builder().url(getUrl()).get().build();
        }
        return request;
    }

    @Override
    public BaseRequest<T> userId(Object userId) {
        return super.userId(userId);
    }



    @Override
    protected Map<String, Object> getParaPublic() {
        try {
            if (userId != null && !TextUtils.isEmpty(userId.toString())) {
                if (paraPublic.containsKey("userId")) paraPublic.remove("userId");
                paraPublic.put("userId", userId);
            }
        } catch (Exception e) {
        }
        return paraPublic;
    }
}
