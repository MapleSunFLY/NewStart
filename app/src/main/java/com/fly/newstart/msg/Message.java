package com.fly.newstart.msg;

import android.view.Gravity;

/**
 * 包    名 : com.fly.newstart.msg
 * 作    者 : FLY
 * 创建时间 : 2019/6/24
 * 描述: 消息实体
 */
public class Message {

    /**
     * 优先级，数值越大优先级越高，最好逐个曾更加
     */
    private int priority;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容·
     */
    private String content;

    /**
     * 位置
     */
    private Gravity gravity;

    /**
     * 路由
     */
    private String route;

    /**
     * 是否自动关闭
     */
    private boolean isClose;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }
}
