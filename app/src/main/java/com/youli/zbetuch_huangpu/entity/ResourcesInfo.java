package com.youli.zbetuch_huangpu.entity;

import java.io.Serializable;

/**
 * Created by sfhan on 2017/11/7.
 */

public class ResourcesInfo implements Serializable {

    private int dcid;//要显示的
    private String dclx;//要显示的
    private String dccjrxm;//要显示的
    private String bzdate;//要显示的
    private int dcmdrs;//要显示的
    private int dcwcrs;//要显示的

    private int dccjrid;
    private String dccjsj;
    private String dcjdmc;
    private String bz;
    private int rows;

    public ResourcesInfo(int dcid, String dclx, String dccjrxm,String bzdate,int dcmdrs,int dcwcrs) {
        this.dcid=dcid;
        this.dclx=dclx;
        this.dccjrxm=dccjrxm;
        this.bzdate=bzdate;
        this.dcmdrs=dcmdrs;
        this.dcwcrs=dcwcrs;
    }


    public int getDcid() {
        return dcid;
    }

    public void setDcid(int dcid) {
        this.dcid = dcid;
    }

    public String getDclx() {
        return dclx;
    }

    public void setDclx(String dclx) {
        this.dclx = dclx;
    }

    public String getDccjrxm() {
        return dccjrxm;
    }

    public void setDccjrxm(String dccjrxm) {
        this.dccjrxm = dccjrxm;
    }

    public String getBzdate() {
        return bzdate;
    }

    public void setBzdate(String bzdate) {
        this.bzdate = bzdate;
    }

    public int getDcmdrs() {
        return dcmdrs;
    }

    public void setDcmdrs(int dcmdrs) {
        this.dcmdrs = dcmdrs;
    }

    public int getDcwcrs() {
        return dcwcrs;
    }

    public void setDcwcrs(int dcwcrs) {
        this.dcwcrs = dcwcrs;
    }

    public int getDccjrid() {
        return dccjrid;
    }

    public void setDccjrid(int dccjrid) {
        this.dccjrid = dccjrid;
    }

    public String getDccjsj() {
        return dccjsj;
    }

    public void setDccjsj(String dccjsj) {
        this.dccjsj = dccjsj;
    }

    public String getDcjdmc() {
        return dcjdmc;
    }

    public void setDcjdmc(String dcjdmc) {
        this.dcjdmc = dcjdmc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }



    @Override
    public String toString() {
        return "ResourcesInfo{" + "dcid='" + dcid + '\''
                + ", dclx=" + dclx + "," +
                " dccjrxm='" + dccjrxm + '\'' +
                ", bzdate='" + bzdate + '\'' +
                ", dcmdrs=" + dcmdrs + ", " +
                "dcwcrs=" + dcwcrs +
                ", dccjrid=" + dccjrid + "," +
                " dccjsj=" + dccjsj + "," +
                " dcjdmc='" + dcjdmc + '\'' +
                "bz="+bz+",rows="+rows+"}";
    }
}

