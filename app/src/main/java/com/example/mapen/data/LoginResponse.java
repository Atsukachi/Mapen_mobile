package com.example.mapen.data;

public class LoginResponse {
    String user_id, name, email, image, alamat, keahlian, role_id, telephone, message;


    public LoginResponse(String user_id, String name, String email, String image, String alamat, String keahlian, String role_id, String telephone, String message, Boolean error) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.image = image;
        this.alamat = alamat;
        this.keahlian = keahlian;
        this.role_id = role_id;
        this.telephone = telephone;
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(String keahlian) {
        this.keahlian = keahlian;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", alamat='" + alamat + '\'' +
                ", keahlian='" + keahlian + '\'' +
                ", role_id='" + role_id + '\'' +
                ", telephone='" + telephone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
