package com.example.md.hangitakimhangiligde;

/**
 * Created by Abdurrahman on 12.12.2021.
 */

public class Soru {
    private String soruCumle;
    private String cevap1;
    private String cevap2;
    private String cevap3;
    private String cevap4;
    private String dogruCevap;

    public Soru(String soruCumle, String cevap1, String cevap2, String cevap3, String cevap4, String dogruCevap){
        this.soruCumle = soruCumle;
        this.cevap1 = cevap1;
        this.cevap2 = cevap2;
        this.cevap3 = cevap3;
        this.cevap4 = cevap4;
        this.dogruCevap = dogruCevap;
    }


    public String getSoruCumle(){
        return soruCumle;
    }

    public String getCevap1(){
        return cevap1;
    }

    public String getCevap2(){
        return cevap2;
    }

    public String getCevap3(){
        return cevap3;
    }

    public String getCevap4(){
        return cevap4;
    }

    public String getDogruCevap(){
        return dogruCevap;
    }
}




