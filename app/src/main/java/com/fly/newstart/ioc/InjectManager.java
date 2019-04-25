package com.fly.newstart.ioc;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.fly.newstart.ioc.annotetion.ContentView;
import com.fly.newstart.ioc.annotetion.EventBase;
import com.fly.newstart.ioc.annotetion.InjectView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


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
 * 描述: 注入管理类
 */

public class InjectManager {
    /**
     * 注解的初始注入
     *
     * @param activity
     */
    public static void inject(Activity activity) {

        //布局的注入
        injectLayout(activity);

        //控件的注入
        injectView(activity);

        //事件的注入
        injectEvents(activity);

    }

    /**
     * 布局的注入
     *
     * @param activity
     */
    private static void injectLayout(Activity activity) {
        //获取类
        Class<? extends Activity> cla = activity.getClass();
        //获取类上的注解
        ContentView contentView = cla.getAnnotation(ContentView.class);
        //获取注解的值
        if (contentView != null) {
            int layoutId = contentView.value();
            // 执行方法：setContentView(R.layout.activity_ioc);
            // 方法一：activity.setContentView(layoutId);
            // 方法二：反射获取，高大上
            try {
                //setContentView 是父类的方法不能使用getDeclareMathods()，需要使用getMethod()
                Method method = cla.getMethod("setContentView", int.class);
                //执行方法
                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 控件的注入
     *
     * @param activity
     */
    private static void injectView(Activity activity) {
        //获取类
        Class<? extends Activity> cla = activity.getClass();
        //获取类的所有属性
        Field[] fields = cla.getDeclaredFields();
        // 循环 拿到每个属性
        if (fields == null || fields.length < 1) return;
        for (Field field : fields) {
            // 获取每个属性的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) { //并不是所有属性都有注解
                //获取注解的值
                int viewId = injectView.value();
                //执行方法：mBtn01 = findViewById(R.id.btnO1)
                try {
                    Method method = cla.getMethod("findViewById", int.class); //findViewById 需要赋值
                    //执行方法 获取返回值
                    Object view = method.invoke(activity, viewId);
                    //设置私有属性的访问权限，默认为false
                    field.setAccessible(true);
                    //给属性赋值
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 事件的注入
     *
     * @param activity
     */
    private static void injectEvents(Activity activity) {
        //获取类
        Class<? extends Activity> cla = activity.getClass();
        //获取类的所有方法，事件注解肯定是当前类
        Method[] methods = cla.getDeclaredMethods();
        if (methods == null || methods.length < 1) return;
        for (Method method : methods) {
            //获取每个方法的注解
            Annotation[] annotations = method.getAnnotations();
            //一个可能有多个注解
            if (annotations == null || annotations.length < 1) continue;
            for (Annotation annotation : annotations) {
                //获取注解上的注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    //通过EventBase注解，获取3个重要的规律
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    //事件的3个规律
                    String listenerSetter = eventBase.listenerSetter();
                    Class<?> listenerType = eventBase.listenerType();
                    String listenerCallBack = eventBase.listenerCallBack();
                    //获取注解的值
                    try {
                        // 通过运行annotationType获取OnClick注解的value值
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        //运行OnClick注解的value方法获取value值
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);

                        //打包之后，代理处理后续工作，可以兼容多种事件方式，不需要写接口
                        //创建拦截器
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        handler.addMethod(listenerCallBack, method);
                        //创建代理
                        Object listene = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                        if (viewIds == null || viewIds.length < 1) continue;
                        for (int viewId : viewIds) {
                            //控件的赋值，保证控件没有赋值也可使用
                            View view = activity.findViewById(viewId);
                            //获取set方法
                            Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                            //执行方法，传入代理
                            setter.invoke(view, listene);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
