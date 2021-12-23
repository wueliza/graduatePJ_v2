package com.project.graduatepj;

import net.sourceforge.jtds.jdbc.DateTime;

public class Patient_Api {
    private String qrChart ;
    private String sex;
    private String name ;
    private String division ;
    private String height ;
    private String age ;
    private String weight ;
    private String bsnos ;
    private String birthDate ;
    private String ora4Chart ;
    private String Emid ;
    private String bedNum ;
    private String bloodType;

    public String getQrChart() {
        return qrChart;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getDivision() {
        return division;
    }

    public String getHeight() {
        return height;
    }

    public String getAge() {
        return age;
    }

    public String getWeight() {
        return weight;
    }

    public String getBsnos() {
        return bsnos;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getOra4Chart() {
        return ora4Chart;
    }

    public String getEmid() {
        return Emid;
    }

    public String getBedNum() {
        return bedNum;
    }

    public String getBloodType() {
        return bloodType;
    }
    /*public virtual staff Em { get; set; }
    public virtual Ora4Chart Ora4ChartNavigation { get; set; }
    public virtual ICollection<BloodBank> BloodBanks { get; set; }
    public virtual ICollection<Medicine> Medicines { get; set; }
    public virtual ICollection<Tpr> Tprs { get; set; }
    public virtual ICollection<TransOperation> TransOperations { get; set; }*/



    /*private int patientNum;
    private String sex;
    private String birth;
    private String bedNum;
    private String bloodType;
    private String name;
    private String division;
    private String height;
    private String age;
    private String weight;
    private String bsno;

    public int getPatientNum() {
        return patientNum;
    }

    public String getSex() {
        return sex;
    }

    public String getBirth() {
        return birth;
    }

    public String getBedNum() {
        return bedNum;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getName() {
        return name;
    }

    public String getDivision() {
        return division;
    }

    public String getHeight() {
        return height;
    }

    public String getAge() {
        return age;
    }

    public String getWeight() {
        return weight;
    }

    public String getBsno() {
        return bsno;
    }

    public Patient_Api(int patientNum, String sex, String birth, String bedNum, String bloodType, String name, String division, String height, String age, String weight, String bsno) {
        this.patientNum = patientNum;
        this.sex = sex;
        this.birth = birth;
        this.bedNum = bedNum;
        this.bloodType = bloodType;
        this.name = name;
        this.division = division;
        this.height = height;
        this.age = age;
        this.weight = weight;
        this.bsno = bsno;
    }*/
}
