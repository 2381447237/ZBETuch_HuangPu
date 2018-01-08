package com.youli.zbetuch_huangpu.entity;

/**
 * Created by liutao on 2018/1/7.
 *
 * 学习经历
 */

public class StudyJlInfo {

    private String startTime;//起始日期
    private String endTime;//终止日期
    private String shool;//学校名称
    private String edu;//文化程度
    private String major;//所学专业
    private String grdu;//毕业肄业
    private String fullTime;//全日制
    private String mark;//备注

    public StudyJlInfo(String startTime, String endTime, String shool, String edu, String major, String grdu, String fullTime, String mark) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.shool = shool;
        this.edu = edu;
        this.major = major;
        this.grdu = grdu;
        this.fullTime = fullTime;
        this.mark = mark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShool() {
        return shool;
    }

    public void setShool(String shool) {
        this.shool = shool;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrdu() {
        return grdu;
    }

    public void setGrdu(String grdu) {
        this.grdu = grdu;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
