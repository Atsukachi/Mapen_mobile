package com.example.mapen.data;

public class PengajuanskpResponse {
    int id_skp, user, bulan, nilai, cek_validasi;
    String tahun, nama_skp;

    public PengajuanskpResponse(int id_skp, int user, int bulan, int nilai, int cek_validasi, String tahun, String nama_skp) {
        this.id_skp = id_skp;
        this.user = user;
        this.bulan = bulan;
        this.nilai = nilai;
        this.cek_validasi = cek_validasi;
        this.tahun = tahun;
        this.nama_skp = nama_skp;
    }

    public int getId_skp() {
        return id_skp;
    }

    public void setId_skp(int id_skp) {
        this.id_skp = id_skp;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getBulan() {
        return bulan;
    }

    public void setBulan(int bulan) {
        this.bulan = bulan;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public int getCek_validasi() {
        return cek_validasi;
    }

    public void setCek_validasi(int cek_validasi) {
        this.cek_validasi = cek_validasi;
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

    @Override
    public String toString() {
        return "PengajuanskpResponse{" +
                "id_skp=" + id_skp +
                ", user=" + user +
                ", bulan=" + bulan +
                ", nilai=" + nilai +
                ", cek_validasi=" + cek_validasi +
                ", tahun='" + tahun + '\'' +
                ", nama_skp='" + nama_skp + '\'' +
                '}';
    }
}
