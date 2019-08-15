package com.fly.newstart.eventbus.bus;

/**
 * 包    名 : com.fly.newstart.eventbus.bus
 * 作    者 : FLY
 * 创建时间 : 2019/8/12
 * 描述: 线程的模式
 */
public enum ThreadMode {

    /**
     * 事件的处理和事件的发送在同一进程，所以事件处理时间不应太长，不然影响事件的发送线程，而线程可能是UI线程
     */
    POSTING,

    /**
     * 事件的处理会在UI线程中执行，事件处理不能有耗时操作
     */
    MAIN,

    /**
     * 后台进程，处理如保存到数据库等操作
     */
    BACKGROUND,

    /**
     * 异步执行，另起线程操作，事件会在单独的线程执行，主要用于在后台线程的耗时操作
     */
    ASYNC
}
