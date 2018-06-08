package com.fly.newstart.Request.sign;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 版    权 ：博智信息
 * 项目名称 : SuiJingFangDms_YiDongXiao
 * 包    名 : com.fly.newstart.Request.sign
 * 作    者 : FLY
 * 创建时间 : 2018/6/7
 * <p>
 * 描述:
 */


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SignIgnore {

}