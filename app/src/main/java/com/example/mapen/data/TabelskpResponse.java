package com.example.mapen.data;

import java.io.Serializable;

public class TabelskpResponse implements Serializable {
    String id_skp, user, tahun, nama_skp, nilai, cek_validasi, nama_bulan, jml_kegiatan;

    public TabelskpResponse(String id_skp, String user, String tahun, String nama_skp, String nilai, String cek_validasi, String nama_bulan, String jml_kegiatan) {
        this.id_skp = id_skp;
        this.user = user;
        this.tahun = tahun;
        this.nama_skp = nama_skp;
        this.nilai = nilai;
        this.cek_validasi = cek_validasi;
        this.nama_bulan = nama_bulan;
        this.jml_kegiatan = jml_kegiatan;
    }

    public String getId_skp() {
        return id_skp;
    }

    public void setId_skp(String id_skp) {
        this.id_skp = id_skp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getNama_skp() {
        return nama_skp;
    }

    public void setNama_skp(String nama_skp) {
        this.nama_skp = nama_skp;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getCek_validasi() {
        return cek_validasi;
    }

    public void setCek_validasi(String cek_validasi) {
        this.cek_validasi = cek_validasi;
    }

    public String getNama_bulan() {
        return nama_bulan;
    }

    public void setNama_bulan(String nama_bulan) {
        this.nama_bulan = nama_bulan;
    }

    public String getJml_kegiatan() {
        return jml_kegiatan;
    }

    public void setJml_kegiatan(String jml_kegiatan) {
        this.jml_kegiatan = jml_kegiatan;
    }

    @Override
    public String toString() {
        return "TabelskpResponse{" +
                "id_skp='" + id_skp + '\'' +
                ", user='" + user + '\'' +
                ", tahun='" + tahun + '\'' +
                ", nama_skp='" + nama_skp + '\'' +
                ", nilai='" + nilai + '\'' +
                ", cek_validasi='" + cek_validasi + '\'' +
                ", nama_bulan='" + nama_bulan + '\'' +
                ", jml_kegiatan='" + jml_kegiatan + '\'' +
                '}';
    }
}
