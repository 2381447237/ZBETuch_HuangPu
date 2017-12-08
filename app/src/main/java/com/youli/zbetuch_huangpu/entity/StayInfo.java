package com.youli.zbetuch_huangpu.entity;

import java.io.Serializable;

/**
 * Created by sfhan on 2017/12/5.
 */

public class StayInfo implements Serializable {


    /**
     * id : 1
     * title : 培训任务
     * notice_time : 2017-11-11T09:00:00
     * create_date : 2017-10-30T10:03:57.887
     * type : 已完成
     */

    private int id;
    private String title;
    private String notice_time;
    private String create_date;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(String notice_time) {
        this.notice_time = notice_time;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
