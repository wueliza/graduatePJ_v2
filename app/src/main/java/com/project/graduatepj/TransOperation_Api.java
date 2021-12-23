package com.project.graduatepj;

public class TransOperation_Api {
    private String rqno;
    private String qrChart;
    private String emid;
    private String bloodBagAmount;
    private BloodBanks[] bloodBanks;

    public String getRqno() {
        return rqno;
    }

    public void setRqno(String rqno) {
        this.rqno = rqno;
    }

    public String getQrChart() {
        return qrChart;
    }

    public void setQrChart(String qrChart) {
        this.qrChart = qrChart;
    }

    public String getEmid() {
        return emid;
    }

    public void setEmid(String emid) {
        this.emid = emid;
    }

    public String getBloodBagAmount() {
        return bloodBagAmount;
    }

    public void setBloodBagAmount(String bloodBagAmount) {
        this.bloodBagAmount = bloodBagAmount;
    }

    public BloodBanks[] getBloodBanks() {
        return bloodBanks;
    }

    public void setBloodBanks(BloodBanks[] bloodBanks) {
        this.bloodBanks = bloodBanks;
    }
}
