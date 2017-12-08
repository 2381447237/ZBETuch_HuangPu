package com.youli.zbetuch_huangpu.entity;

/**
 * 作者: zhengbin on 2017/12/5.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 */

public class MyFollowInfo {

    private String name;
    private String sfz;

    public MyFollowInfo(String name, String sfz) {
        this.name = name;
        this.sfz = sfz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }
}
