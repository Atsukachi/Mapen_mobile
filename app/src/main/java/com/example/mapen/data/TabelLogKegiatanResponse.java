package com.example.mapen.data;

import java.io.Serializable;

public class TabelLogKegiatanResponse implements Serializable {
    int unitkerja, skp, user, file_categories;
    String tanggal, kegiatan_id, uraian, file, id, extension, nama_unit_kerja, nama_skp, nama_skp_limit;

    public TabelLogKegiatanResponse(int unitkerja, int skp, int user, int file_categories, String tanggal, String kegiatan_id, String uraian, String file, String id, String extension, String nama_unit_kerja, String nama_skp, String nama_skp_limit) {
        this.unitkerja = unitkerja;
        this.skp = skp;
        this.user = user;
        this.file_categories = file_categories;
        this.tanggal = tanggal;
        this.kegiatan_id = kegiatan_id;
        this.uraian = uraian;
        this.file = file;
        this.id = id;
        this.extension = extension;
        this.nama_unit_kerja = nama_unit_kerja;
        this.nama_skp = nama_skp;
        this.nama_skp_limit = nama_skp_limit;
    }

    public int getUnitkerja() {
        return unitkerja;
    }

    public void setUnitkerja(int unitkerja) {
        this.unitkerja = unitkerja;
    }

    public int getSkp() {
        return skp;
    }

    public void setSkp(int skp) {
        this.skp = skp;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getFile_categories() {
        return file_categories;
    }

    public void setFile_categories(int file_categories) {
        this.file_categories = file_categories;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKegiatan_id() {
        return kegiatan_id;
    }

    public void setKegiatan_id(String kegiatan_id) {
        this.kegiatan_id = kegiatan_id;
    }

    public String getUraian() {
        return uraian;
    }

    public void setUraian(String uraian) {
        this.uraian = uraian;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNama_unit_kerja() {
        return nama_unit_kerja;
    }

    public void setNama_unit_kerja(String nama_unit_kerja) {
        this.nama_unit_kerja = nama_unit_kerja;
    }

    public String getNama_skp() {
        return nama_skp;
    }

    public void setNama_skp(String nama_skp) {
        this.nama_skp = nama_skp;
    }

    public String getNama_skp_limit() {
        return nama_skp_limit;
    }

    public void setNama_skp_limit(String nama_skp_limit) {
        this.nama_skp_limit = nama_skp_limit;
    }

    @Override
    public String toString() {
        return "TabelLogKegiatanResponse{" +
                "unitkerja=" + unitkerja +
                ", skp=" + skp +
                ", user=" + user +
                ", file_categories=" + file_categories +
                ", tanggal='" + tanggal + '\'' +
                ", kegiatan_id='" + kegiatan_id + '\'' +
                ", uraian='" + uraian + '\'' +
                ", file='" + file + '\'' +
                ", id='" + id + '\'' +
                ", extension='" + extension + '\'' +
                ", nama_unit_kerja='" + nama_unit_kerja + '\'' +
                ", nama_skp='" + nama_skp + '\'' +
                ", nama_skp_limit='" + nama_skp_limit + '\'' +
                '}';
    }
}
