package com.example.notepad.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoteModel {
    @SerializedName("catatan_nama")
    @Expose
    private String judul;

    @SerializedName("catatan_keterangan")
    @Expose
    private String keterangan;

    @SerializedName("catatan_tanggal")
    @Expose
    private String tanggal;

    @SerializedName("catatan_waktu")
    @Expose
    private String waktu;

    @SerializedName("catatan_lokasi")
    @Expose
    private String lokasi;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
