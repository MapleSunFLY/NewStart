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
 * 描述: 属性的注解
 */

@Target(ElementType.FIELD) //该注解作用于什么之上 ，对应枚举标识 FIELD-标识为属性之上
@Retention(RetentionPolicy.RUNTIME)//jvm在运行时通过反射获取注解的值
public @interface InjectView {
    int value();
}
