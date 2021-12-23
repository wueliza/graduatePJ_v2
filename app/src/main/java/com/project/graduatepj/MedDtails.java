package com.project.graduatepj;

public class MedDtails {
    private String tubg;
    private String checkNum;
    private String qrChart;
    private String name;
    private String dose;
    private String frequence;

    public String getTubg() {
        return tubg;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public String getQrChart() {
        return qrChart;
    }

    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getFrequence() {
        return frequence;
    }

    public MedDtails(String tubg, String checkNum, String qrChart, String name, String dose, String frequence) {
        this.tubg = tubg;
        this.checkNum = checkNum;
        this.qrChart = qrChart;
        this.name = name;
        this.dose = dose;
        this.frequence = frequence;
    }
}
