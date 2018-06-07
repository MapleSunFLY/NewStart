package com.fly.newstart.viewmode;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.viewmode
 * 作    者 : FLY
 * 创建时间 : 2018/6/4
 * <p>
 * 描述:
 */

public class HttpErrorException  extends RuntimeException {

    private ResponseJson responseJson;
    public HttpErrorException(ResponseJson responseJson) {
        super(responseJson!=null?responseJson.msg:"");
        this.responseJson=responseJson;
    }
    public HttpErrorException(String message) {
        super(message);
        this.responseJson=new ResponseJson();
        responseJson.code="";
        responseJson.msg=message;
    }
    public ResponseJson getResponseJson() {
        return responseJson;
    }
}
