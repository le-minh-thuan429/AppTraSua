package com.example.apptrasua.Models;

import android.text.TextUtils;
import android.util.Patterns;

public class Taikhoan {
   String id ;
    String TenTK ;
    String Email ;
    String MatKhau ;
    String TrangThai ;

    @Override
    public String toString() {
        return "Taikhoan{" +
                "id='" + id + '\'' +
                ", TenTK='" + TenTK + '\'' +
                ", Email='" + Email + '\'' +
                ", MatKhau='" + MatKhau + '\'' +
                ", TrangThai='" + TrangThai + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTenTK(String tenTK) {
        TenTK = tenTK;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public String getTenTK() {
        return TenTK;
    }

    public String getEmail() {
        return Email;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public Taikhoan(String id, String tenTK, String email, String matKhau, String trangThai) {
        this.id = id;
        TenTK = tenTK;
        Email = email;
        MatKhau = matKhau;
        TrangThai = trangThai;
    }

    public Boolean isValidEmail(){
        return !TextUtils.isEmpty(Email)&& Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }
    public Boolean isValidPassword(){
        return !TextUtils.isEmpty(MatKhau)&& MatKhau.length() >= 6;
    }
    public Boolean isValidUsename(){
        return !TextUtils.isEmpty(TenTK) && TenTK.length() >= 6;
    }
}
