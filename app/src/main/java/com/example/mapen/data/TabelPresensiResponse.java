package com.example.mapen.data;

public class TabelPresensiResponse {
    String tanggal, date, foto, lat, lng, riwayat, status, kerja;
    int user_id, cek_presensi;

    public TabelPresensiResponse(String tanggal, String date, String foto, String lat, String lng, String riwayat, String status, String kerja, int user_id, int cek_presensi) {
        this.tanggal = tanggal;
        this.date = date;
        this.foto = foto;
        this.lat = lat;
        this.lng = lng;
        this.riwayat = riwayat;
        this.status = status;
        this.kerja = kerja;
        this.user_id = user_id;
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

    public String getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(String riwayat) {
        this.riwayat = riwayat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKerja() {
        return kerja;
    }

    public void setKerja(String kerja) {
        this.kerja = kerja;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
                ", riwayat='" + riwayat + '\'' +
                ", status='" + status + '\'' +
                ", kerja='" + kerja + '\'' +
                ", user_id=" + user_id +
                ", cek_presensi=" + cek_presensi +
                '}';
    }
}
