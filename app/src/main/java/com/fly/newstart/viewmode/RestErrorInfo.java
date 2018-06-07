package com.fly.newstart.viewmode;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.viewmode
 * 作    者 : FLY
 * 创建时间 : 2018/6/4
 * <p>
 * 描述: 请求的 error 返回
 */

public class RestErrorInfo {
    public String code;
    public String message;
   // public int source;

    public RestErrorInfo(ResponseJson responseJson){
        this.code=responseJson.code;
        this.message=responseJson.msg;
    }
    public RestErrorInfo(String code,String message){
        this.code=code;
        this.message=message;
    }
    public RestErrorInfo(String message)
    {
        this.code="";
        this.message=message;
    }
}
