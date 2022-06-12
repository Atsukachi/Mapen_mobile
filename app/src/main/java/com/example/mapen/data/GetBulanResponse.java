package com.example.mapen.data;

public class GetBulanResponse {
    int id_bulan;
    String nama_bulan;

    public GetBulanResponse(int id_bulan, String nama_bulan) {
        this.id_bulan = id_bulan;
        this.nama_bulan = nama_bulan;
    }

    public int getId_bulan() {
        return id_bulan;
    }

    public void setId_bulan(int id_bulan) {
        this.id_bulan = id_bulan;
    }

    public String getNama_bulan() {
        return nama_bulan;
    }

    public void setNama_bulan(String nama_bulan) {
        this.nama_bulan = nama_bulan;
    }

    @Override
    public String toString() {
        return "PengajuanskpResponse{" +
                "id_bulan='" + id_bulan + '\'' +
                ", nama_bulan='" + nama_bulan + '\'' +
                '}';
    }
}
