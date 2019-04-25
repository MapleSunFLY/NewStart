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
 * 包    名 : com.fly.newstart.ioc.annotetion
 * 作    者 : FLY
 * 创建时间 : 2019/4/24
 * 描述: 事件注解的注解 用于封装点击事件规律的对象
 */

@Target(ElementType.ANNOTATION_TYPE) //该注解作用于什么之上 ，对应枚举标识 ANNOTATION_TYPE-标识为注解之上
@Retention(RetentionPolicy.RUNTIME)//jvm在运行时通过反射获取注解的值
public @interface EventBase {

    //setxxxListener
    String listenerSetter();

    //new View.xxxListener
    Class<?> listenerType();

    //回调执行方法：onxxx()
    String listenerCallBack();

}
