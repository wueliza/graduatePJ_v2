package com.project.graduatepj;

import java.sql.Date;

public class CheckOperationRecord {
    private String QrChart;  //手圈病歷號
    private String Bsno;  //檢體編號
    private String Emid;  //護理人員編號
    private String ConfirmId; //確認員編號
    private Date RecordTime;  //記錄時間
    private String RecordId;  //紀錄ID

    public CheckOperationRecord(String qrChart, String bsno, String emid, String confirmId) {
        QrChart = qrChart;
        Bsno = bsno;
        Emid = emid;
        ConfirmId = confirmId;
    }
    public CheckOperationRecord(String qrChart, String bsno) {
        QrChart = qrChart;
        Bsno = bsno;
    }


}

