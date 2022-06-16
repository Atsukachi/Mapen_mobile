package com.example.mapen.data;

public class MetodeKerjaResponse {
    String id_kerja, metode;

    public MetodeKerjaResponse(String id_kerja, String metode) {
        this.id_kerja = id_kerja;
        this.metode = metode;
    }

    public String getId_kerja() {
        return id_kerja;
    }

    public void setId_kerja(String id_kerja) {
        this.id_kerja = id_kerja;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    @Override
    public String toString() {
        return "MetodeKerjaResponse{" +
                "id_kerja='" + id_kerja + '\'' +
                ", metode='" + metode + '\'' +
                '}';
    }
}
