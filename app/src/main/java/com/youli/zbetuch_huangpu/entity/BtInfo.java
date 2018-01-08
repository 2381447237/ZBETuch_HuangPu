package com.youli.zbetuch_huangpu.entity;

/**
 * Created by liutao on 2018/1/5.
 *
 * 补贴信息
 */

public class BtInfo {

    private String time;//支付年月
    private String name;//补贴项目名称
    private String money;//补贴金额
    private String nature;//补贴性质

    public BtInfo(String time, String name, String money, String nature) {
        this.time = time;
        this.name = name;
        this.money = money;
        this.nature = nature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}
