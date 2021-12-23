package com.project.graduatepj;

import java.sql.Date;

public class MedGiveRecord {
    private String QrChart ;
    private String Name ;
    private String Sex ;
    private String BedNum;
    private String Bsa ;
    private String Height;
    private String Weight ;
    private String Age ;
    private String Emid ;
    private String ConfirmId;
    private String Tubg;
    private Date RecordTime;
    private String RecordId ;

    public MedGiveRecord(String qrChart, String name, String sex, String bedNum, String bsa, String height, String weight, String age, String emid, String confirmId, String tubg) {
        QrChart = qrChart;
        Name = name;
        Sex = sex;
        BedNum = bedNum;
        Bsa = bsa;
        Height = height;
        Weight = weight;
        Age = age;
        Emid = emid;
        ConfirmId = confirmId;
        Tubg = tubg;
    }
}
