package com.example.android.quiztruefalse.kelasobjek;

/**
 * Created by Aulia Ramadhan on 13/04/2018.
 */

public class KuisObjek {
    private String id;
    private String kode;
    private String namaKuis;
    private String pembuat;
    private Boolean onetime;

    public KuisObjek() {
    }

    public KuisObjek(String id, String kode, String namaKuis, String pembuat, Boolean onetime) {
        this.id = id;
        this.kode = kode;
        this.namaKuis = namaKuis;
        this.pembuat = pembuat;
        this.onetime = onetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNamaKuis() {
        return namaKuis;
    }

    public void setNamaKuis(String namaKuis) {
        this.namaKuis = namaKuis;
    }

    public String getPembuat() {
        return pembuat;
    }

    public void setPembuat(String pembuat) {
        this.pembuat = pembuat;
    }

    public Boolean getOnetime() {
        return onetime;
    }

    public void setOnetime(Boolean onetime) {
        this.onetime = onetime;
    }
}
