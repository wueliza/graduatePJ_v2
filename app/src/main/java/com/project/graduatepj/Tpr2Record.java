package com.project.graduatepj;

import java.sql.Date;

public class Tpr2Record {
    private String transTime ;
    private String t;
    private String p ;
    private String r ;
    private String bp1;
    private String bp2;
    private Date recordTime;
    private String recordId ;
    private String qrchart;
    private String emid;

    public Tpr2Record(String transTime, String t, String p, String r, String bp1, String bp2, String qrchart, String emid) {
        this.transTime = transTime;
        this.t = t;
        this.p = p;
        this.r = r;
        this.bp1 = bp1;
        this.bp2 = bp2;
        this.qrchart = qrchart;
        this.emid = emid;
    }
}
