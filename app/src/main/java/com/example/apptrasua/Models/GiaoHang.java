package com.example.apptrasua.Models;

import java.io.Serializable;

public class GiaoHang implements Serializable {

    private String MaKH;
    private String HoTen;
    private int SoDienThoai;
    private String Email;
    private String DiaChi;

    public void setDiaChiCuThe(String diaChiCuThe) {
        DiaChiCuThe = diaChiCuThe;
    }

    public String getDiaChiCuThe() {
        return DiaChiCuThe;
    }

    public GiaoHang(String maKH, String hoTen, int soDienThoai, String email, String diaChi, String diaChiCuThe, int id, String PTThanhToan) {
        MaKH = maKH;
        HoTen = hoTen;
        SoDienThoai = soDienThoai;
        Email = email;
        DiaChi = diaChi;
        DiaChiCuThe = diaChiCuThe;
        this.id = id;
        this.PTThanhToan = PTThanhToan;
    }

    private String DiaChiCuThe;
    private   int id;
    private   String PTThanhToan;

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public void setSoDienThoai(int soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPTThanhToan(String PTThanhToan) {
        this.PTThanhToan = PTThanhToan;
    }

    public String getMaKH() {
        return MaKH;
    }

    public String getHoTen() {
        return HoTen;
    }

    public int getSoDienThoai() {
        return SoDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public int getId() {
        return id;
    }

    public String getPTThanhToan() {
        return PTThanhToan;
    }

}
