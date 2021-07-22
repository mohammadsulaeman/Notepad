package com.example.notepad.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotedRequestJson {

    @SerializedName("kegiatan_keterangan")
    @Expose
    private String keterangan;

    @SerializedName("kegiatan_tanggal")
    @Expose
    private String tanggal;

    @SerializedName("kegiatan_waktu")
    @Expose
    private String waktu;

    @SerializedName("kegiatan_lokasi")
    @Expose
    private String lokasi;

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
