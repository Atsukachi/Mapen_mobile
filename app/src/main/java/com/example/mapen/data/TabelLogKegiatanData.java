package com.example.mapen.data;

public class TabelLogKegiatanData {
    int unitkerja, skp, user, file_categories;
    String kegiatan_id, uraian, file, id, extension, nama_unit_kerja, nama_skp;
    Long tanggal;

    public TabelLogKegiatanData(int unitkerja, int skp, int user, int file_categories, String kegiatan_id, String uraian, String file, Long tanggal, String id, String extension, String nama_unit_kerja, String nama_skp) {
        this.unitkerja = unitkerja;
        this.skp = skp;
        this.user = user;
        this.file_categories = file_categories;
        this.kegiatan_id = kegiatan_id;
        this.uraian = uraian;
        this.file = file;
        this.tanggal = tanggal;
        this.id = id;
        this.extension = extension;
        this.nama_unit_kerja = nama_unit_kerja;
        this.nama_skp = nama_skp;
    }

    public String getNama_skp() {
        return nama_skp;
    }

    public void setNama_skp(String nama_skp) {
        this.nama_skp = nama_skp;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getTanggal() {
        return tanggal;
    }

    public void setTanggal(Long tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public String toString() {
        return "TabelLogKegiatanData{" +
                "unitkerja=" + unitkerja +
                ", skp=" + skp +
                ", user=" + user +
                ", file_categories=" + file_categories +
                ", kegiatan_id='" + kegiatan_id + '\'' +
                ", uraian='" + uraian + '\'' +
                ", file='" + file + '\'' +
                ", id='" + id + '\'' +
                ", tanggal=" + tanggal +
                '}';
    }
}
