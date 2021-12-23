package com.project.graduatepj;

import java.sql.Date;

public class OperationRecord {
    private String Ora4Chart ;
    private String QrChart ;
    private String Emid;
    private String Name ;
    private String Birth ;
    private Date RecordTime;
    private String RecordId ;

    public OperationRecord(String ora4Chart, String qrChart, String emid, String name, String birth) {
        Ora4Chart = ora4Chart;
        QrChart = qrChart;
        Emid = emid;
        Name = name;
        Birth = birth;
    }
}
