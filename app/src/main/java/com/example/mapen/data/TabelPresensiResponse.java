package com.example.mapen.data;

public class TabelPresensiResponse {
    String tanggal, date, foto, lat, lng;
    int user_id, riwayat, status, kerja, cek_presensi;

    public TabelPresensiResponse(String tanggal, String date, String foto, String lat, String lng, int user_id, int riwayat, int status, int kerja, int cek_presensi) {
        this.tanggal = tanggal;
        this.date = date;
        this.foto = foto;
        this.lat = lat;
        this.lng = lng;
        this.user_id = user_id;
        this.riwayat = riwayat;
        this.status = status;
        this.kerja = kerja;
        this.cek_presensi = cek_presensi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(int riwayat) {
        this.riwayat = riwayat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getKerja() {
        return kerja;
    }

    public void setKerja(int kerja) {
        this.kerja = kerja;
    }

    public int getCek_presensi() {
        return cek_presensi;
    }

    public void setCek_presensi(int cek_presensi) {
        this.cek_presensi = cek_presensi;
    }

    @Override
    public String toString() {
        return "TabelPresensiResponse{" +
                "tanggal='" + tanggal + '\'' +
                ", date='" + date + '\'' +
                ", foto='" + foto + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", user_id=" + user_id +
                ", riwayat=" + riwayat +
                ", status=" + status +
                ", kerja=" + kerja +
                ", cek_presensi=" + cek_presensi +
                '}';
    }
}
