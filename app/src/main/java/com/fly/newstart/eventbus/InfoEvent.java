package com.fly.newstart.eventbus;

/**
 * 包    名 : com.fly.newstart.eventbus
 * 作    者 : FLY
 * 创建时间 : 2019/8/12
 * 描述:
 */
public class InfoEvent {
    private String info;

    public InfoEvent(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
