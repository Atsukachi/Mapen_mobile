package com.example.mapen.data;

import java.io.Serializable;

public class SkpByIdResponse implements Serializable {
    String id_skp, nama_skp;

    public SkpByIdResponse(String id_skp, String nama_skp) {
        this.id_skp = id_skp;
        this.nama_skp = nama_skp;
    }

    public String getId_skp() {
        return id_skp;
    }

    public String getNama_skp() {
        return nama_skp;
    }

    public void setNama_skp(String nama_skp) {
        this.nama_skp = nama_skp;
    }

    @Override
    public String toString() {
        return "SkpByIdResponse{" +
                "id_skp='" + id_skp + '\'' +
                ", nama_skp='" + nama_skp + '\'' +
                '}';
    }
}
