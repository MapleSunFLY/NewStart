package com.fly.myview.loading.core;

import com.fly.myview.loading.page.Page;

/**
 * 包    名 : com.fly.myview.loading.core
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 转换接口，可将网络返回实体转换成对应的状态页，达到自动适配状态页的目的。
 */
public interface Convertor<T> {
    Class<?extends Page> map(T t);
}
