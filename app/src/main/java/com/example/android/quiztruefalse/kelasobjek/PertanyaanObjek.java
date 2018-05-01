package com.example.android.quiztruefalse.kelasobjek;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Aulia Ramadhan on 07/04/2018.
 */

public class PertanyaanObjek implements Serializable {
    private String idPertanyaan;
    private  String pertanyaan;
    private String pilihanA;
    private String pilihanB;
    private String pilihanC;
    private String pilihanD;
    private String jawaban;

    public PertanyaanObjek() {
    }

    public PertanyaanObjek(String idPertanyaan, String pertanyaan, String pilihanA, String pilihanB, String pilihanC, String pilihanD, String jawaban) {
        this.idPertanyaan = idPertanyaan;
        this.pertanyaan = pertanyaan;
        this.pilihanA = pilihanA;
        this.pilihanB = pilihanB;
        this.pilihanC = pilihanC;
        this.pilihanD = pilihanD;
        this.jawaban = jawaban;
    }

    public String getIdPertanyaan() {
        return idPertanyaan;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public String getPilihanA() {
        return pilihanA;
    }

    public String getPilihanB() {
        return pilihanB;
    }

    public String getPilihanC() {
        return pilihanC;
    }

    public String getPilihanD() {
        return pilihanD;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setIdPertanyaan(String idPertanyaan) {
        this.idPertanyaan = idPertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public void setPilihanA(String pilihanA) {
        this.pilihanA = pilihanA;
    }

    public void setPilihanB(String pilihanB) {
        this.pilihanB = pilihanB;
    }

    public void setPilihanC(String pilihanC) {
        this.pilihanC = pilihanC;
    }

    public void setPilihanD(String pilihanD) {
        this.pilihanD = pilihanD;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }


}
