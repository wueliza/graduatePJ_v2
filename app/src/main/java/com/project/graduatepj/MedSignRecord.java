package com.project.graduatepj;

import java.sql.Date;

public class MedSignRecord {
    private String emid;
    private String transId;
    private String tubg;
    private String medAmount;
    private String name;
    private String recordTime;
    private String recordId;

    public MedSignRecord(String emid, String transId, String tubg, String medAmount, String name) {
        this.emid = emid;
        this.transId = transId;
        this.tubg = tubg;
        this.medAmount = medAmount;
        this.name = name;
    }
}
