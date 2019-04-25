package com.fly.newstart.ioc.annotetion;

        import android.view.View;

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
 * 描述: 长按事件的注解
 */

@Target(ElementType.METHOD) //该注解作用于什么之上 ，对应枚举标识 METHOD-标识为方法之上
@Retention(RetentionPolicy.RUNTIME)//jvm在运行时通过反射获取注解的值
@EventBase(listenerSetter = "setOnLongClickListener", listenerType = View.OnLongClickListener.class, listenerCallBack = "onLongClick")
public @interface OnLongClick { //注解对应方法需要boolean返回值
    int[] value();
}
