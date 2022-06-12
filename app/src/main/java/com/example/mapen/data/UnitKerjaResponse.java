package com.example.mapen.data;

public class UnitKerjaResponse {
    int id_unit_kerja;
    String nama_unit_kerja;

    public UnitKerjaResponse(int id_unit_kerja, String nama_unit_kerja) {
        this.id_unit_kerja = id_unit_kerja;
        this.nama_unit_kerja = nama_unit_kerja;
    }

    public int getId_unit_kerja() {
        return id_unit_kerja;
    }

    public void setId_unit_kerja(int id_unit_kerja) {
        this.id_unit_kerja = id_unit_kerja;
    }

    public String getNama_unit_kerja() {
        return nama_unit_kerja;
    }

    public void setNama_unit_kerja(String nama_unit_kerja) {
        this.nama_unit_kerja = nama_unit_kerja;
    }

    @Override
    public String toString() {
        return "UnitKerjaResponse{" +
                "id_unit_kerja=" + id_unit_kerja +
                ", nama_unit_kerja='" + nama_unit_kerja + '\'' +
                '}';
    }
}
