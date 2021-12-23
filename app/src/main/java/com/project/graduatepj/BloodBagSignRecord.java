package com.project.graduatepj;

import java.sql.Date;

public class BloodBagSignRecord {
    private String Rqno ;
    private String BloodType ;
    private String Emid ;
    private String TransId ;
    private String BloodAmount ;
    private Date RecordTime;
    private String RecordId ;

    public BloodBagSignRecord(String rqno, String bloodType, String emid, String transId, String bloodAmount) {
        Rqno = rqno;
        BloodType = bloodType;
        Emid = emid;
        TransId = transId;
        BloodAmount = bloodAmount;
    }
}
