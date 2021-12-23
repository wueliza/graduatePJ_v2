package com.project.graduatepj;

import java.sql.Date;

public class TransOperationRecord {
    private String Rqno ;//血袋號碼
    private String Name;
    private String BloodType ;
    private String BedNum ;
    private String QrChart ;//手圈病歷號
    private String Emid ;//護理人員編號
    private String ConfrimId ;

    private Date RecordTime;
    private String RecordId ;

    public TransOperationRecord(String rqno, String name, String bloodType, String bedNum, String qrChart, String emid, String confrimId) {
        Rqno = rqno;
        Name = name;
        BloodType = bloodType;
        BedNum = bedNum;
        QrChart = qrChart;
        Emid = emid;
        ConfrimId = confrimId;
    }
}
