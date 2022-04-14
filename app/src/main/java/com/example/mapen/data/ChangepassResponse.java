package com.example.mapen.data;

public class ChangepassResponse {
    String email, current_password, new_password1, new_password2, status;

    public ChangepassResponse(String email, String current_password, String new_password1, String new_password2, String status) {
        this.email = email;
        this.current_password = current_password;
        this.new_password1 = new_password1;
        this.new_password2 = new_password2;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrent_password() {
        return current_password;
    }

    public void setCurrent_password(String current_password) {
        this.current_password = current_password;
    }

    public String getNew_password1() {
        return new_password1;
    }

    public void setNew_password1(String new_password1) {
        this.new_password1 = new_password1;
    }

    public String getNew_password2() {
        return new_password2;
    }

    public void setNew_password2(String new_password2) {
        this.new_password2 = new_password2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChangepassResponse{" +
                "email='" + email + '\'' +
                ", current_password='" + current_password + '\'' +
                ", new_password1='" + new_password1 + '\'' +
                ", new_password2='" + new_password2 + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
