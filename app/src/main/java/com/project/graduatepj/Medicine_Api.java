package com.project.graduatepj;

public class Medicine_Api {
    private String tubg;
    private MedDtails[] medDetails;

    public String getTubg() {
        return tubg;
    }

    public void setTubg(String tubg) {
        this.tubg = tubg;
    }

    public MedDtails[] getMedDetails() {
        return medDetails;
    }

    public void setMedDetails(MedDtails[] medDetails) {
        this.medDetails = medDetails;
    }
}
