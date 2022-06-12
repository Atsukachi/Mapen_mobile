package com.example.mapen.data;

public class EditProfilResponse {

    String user_id, email, alamat, telephone, image, keahlian;

    public EditProfilResponse(String user_id, String email, String alamat, String telephone, String image, String keahlian) {
        this.user_id = user_id;
        this.email = email;
        this.alamat = alamat;
        this.telephone = telephone;
        this.image = image;
        this.keahlian = keahlian;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    @Override
    public String toString() {
        return "EditProfilResponse{" +
                "user_id='" + user_id + '\'' +
                ", email='" + email + '\'' +
                ", alamat='" + alamat + '\'' +
                ", telephone='" + telephone + '\'' +
                ", image='" + image + '\'' +
                ", keahlian='" + keahlian + '\'' +
                '}';
    }
}
