package com.fly.newstart.eventbus.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 包    名 : com.fly.newstart.eventbus.bus
 * 作    者 : FLY
 * 创建时间 : 2019/8/12
 * 描述: 事件接受的注解
 */

@Target(ElementType.METHOD) //方法之上
@Retention(RetentionPolicy.RUNTIME) //运行时通过反射获取
public @interface Subscribe {
    ThreadMode threadMode() default ThreadMode.POSTING;
}
