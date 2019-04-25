package com.fly.newstart.ioc.annotetion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.ioc
 * 作    者 : FLY
 * 创建时间 : 2019/4/24
 * 描述: 布局注入注解
 */


@Target(ElementType.TYPE) //该注解作用于什么之上 ，对应枚举标识 METHOD-标识为类之上
@Retention(RetentionPolicy.RUNTIME)//jvm在运行时通过反射获取注解的值
//RUNTIME-jvm在运行时通过反射来完成的过程
//CLASS-在编译时进行一些预操作，并且注解会在class存在
//SOURCE-源码级的，主要是做一些检查检测操作
public @interface ContentView {
    int value();
}
