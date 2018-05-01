package com.example.android.quiztruefalse.kelasobjek;

/**
 * Created by Aulia Ramadhan on 21/04/2018.
 */

public class ScoreObjek {

    private String id;
    private String idKuis;
    private String namaKuis;
    private String idUser;
    private String namaUser;
    private int Score;

    public ScoreObjek() {
    }

    public ScoreObjek(String id, String idKuis, String namaKuis, String idUser, String namaUser, int score) {
        this.id = id;
        this.idKuis = idKuis;
        this.namaKuis = namaKuis;
        this.idUser = idUser;
        this.namaUser = namaUser;
        Score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdKuis() {
        return idKuis;
    }

    public void setIdKuis(String idKuis) {
        this.idKuis = idKuis;
    }

    public String getNamaKuis() {
        return namaKuis;
    }

    public void setNamaKuis(String namaKuis) {
        this.namaKuis = namaKuis;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
