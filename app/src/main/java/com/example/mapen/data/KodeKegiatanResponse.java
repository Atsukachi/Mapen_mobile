package com.example.mapen.data;

public class KodeKegiatanResponse {
    String kodekegiatan;

    public KodeKegiatanResponse(String kodekegiatan) {
        this.kodekegiatan = kodekegiatan;
    }

    public String getKodekegiatan() {
        return kodekegiatan;
    }

    public void setKodekegiatan(String kodekegiatan) {
        this.kodekegiatan = kodekegiatan;
    }

    @Override
    public String toString() {
        return "KodeKegiatanResponse{" +
                "kodekegiatan='" + kodekegiatan + '\'' +
                '}';
    }
}
