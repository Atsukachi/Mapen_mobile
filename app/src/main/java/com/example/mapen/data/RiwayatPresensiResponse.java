package com.example.mapen.data;

public class RiwayatPresensiResponse {
    String id_riwayat, riwayat, status_id, cek, id_status, status, jam_datang, jam_pulang, cek_p, p_cek;

    public RiwayatPresensiResponse(String id_riwayat, String riwayat, String status_id, String cek, String id_status, String status, String jam_datang, String jam_pulang, String cek_p, String p_cek) {
        this.id_riwayat = id_riwayat;
        this.riwayat = riwayat;
        this.status_id = status_id;
        this.cek = cek;
        this.id_status = id_status;
        this.status = status;
        this.jam_datang = jam_datang;
        this.jam_pulang = jam_pulang;
        this.cek_p = cek_p;
        this.p_cek = p_cek;
    }

    public String getId_riwayat() {
        return id_riwayat;
    }

    public void setId_riwayat(String id_riwayat) {
        this.id_riwayat = id_riwayat;
    }

    public String getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(String riwayat) {
        this.riwayat = riwayat;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getCek() {
        return cek;
    }

    public void setCek(String cek) {
        this.cek = cek;
    }

    public String getId_status() {
        return id_status;
    }

    public void setId_status(String id_status) {
        this.id_status = id_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJam_datang() {
        return jam_datang;
    }

    public void setJam_datang(String jam_datang) {
        this.jam_datang = jam_datang;
    }

    public String getJam_pulang() {
        return jam_pulang;
    }

    public void setJam_pulang(String jam_pulang) {
        this.jam_pulang = jam_pulang;
    }

    public String getCek_p() {
        return cek_p;
    }

    public void setCek_p(String cek_p) {
        this.cek_p = cek_p;
    }

    public String getP_cek() {
        return p_cek;
    }

    public void setP_cek(String p_cek) {
        this.p_cek = p_cek;
    }

    @Override
    public String toString() {
        return "RiwayatPresensiResponse{" +
                "id_riwayat='" + id_riwayat + '\'' +
                ", riwayat='" + riwayat + '\'' +
                ", status_id='" + status_id + '\'' +
                ", cek='" + cek + '\'' +
                ", id_status='" + id_status + '\'' +
                ", status='" + status + '\'' +
                ", jam_datang='" + jam_datang + '\'' +
                ", jam_pulang='" + jam_pulang + '\'' +
                ", cek_p='" + cek_p + '\'' +
                ", p_cek='" + p_cek + '\'' +
                '}';
    }
}
