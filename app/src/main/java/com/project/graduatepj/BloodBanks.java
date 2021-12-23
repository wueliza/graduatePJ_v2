package com.project.graduatepj;

public class BloodBanks {
    private String blnos;
    private String bloodProduct;
    private String bloodType;
    private String expiration;
    private String checkNum;
    private String checkDate;
    private String qrChart;
    private String rqno;
    private String rqChartNavigation;
    private String rqnoNavigation;

    public String getBlnos() {
        return blnos;
    }

    public String getBloodProduct() {
        return bloodProduct;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public String getQrChart() {
        return qrChart;
    }

    public String getRqno() {
        return rqno;
    }

    public String getRqnoNavigation() {
        return rqnoNavigation;
    }

    public String getRqChartNavigation() {
        return rqChartNavigation;
    }

    public BloodBanks(String blnos, String bloodProduct, String bloodType, String expiration, String checkNum, String checkDate, String qrChart, String rqno, String rqChartNavigation, String rqnoNavigation) {
        this.blnos = blnos;
        this.bloodProduct = bloodProduct;
        this.bloodType = bloodType;
        this.expiration = expiration;
        this.checkNum = checkNum;
        this.checkDate = checkDate;
        this.qrChart = qrChart;
        this.rqno = rqno;
        this.rqChartNavigation = rqChartNavigation;
        this.rqnoNavigation = rqnoNavigation;
    }
}
