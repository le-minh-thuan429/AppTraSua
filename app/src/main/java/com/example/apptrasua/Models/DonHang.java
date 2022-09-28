package com.example.apptrasua.Models;

import java.io.Serializable;

public class DonHang implements Serializable {
    private  String MaDH;
    private  String HoTen;
    private  String PTThanhToan;
    private  String DiaChi;

    private  int TienHang;
    private  int ThanhTien;
    private  String TrangThai;
    private  String MaKH;

    public void setMaDH(String maDH) {
        MaDH = maDH;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public void setPTThanhToan(String PTThanhToan) {
        this.PTThanhToan = PTThanhToan;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public void setTienHang(int tienHang) {
        TienHang = tienHang;
    }

    public void setThanhTien(int thanhTien) {
        ThanhTien = thanhTien;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaDH() {
        return MaDH;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getPTThanhToan() {
        return PTThanhToan;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public int getTienHang() {
        return TienHang;
    }

    public int getThanhTien() {
        return ThanhTien;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public String getMaKH() {
        return MaKH;
    }

    public int getId() {
        return id;
    }

    public DonHang(String maDH, String hoTen, String PTThanhToan, String diaChi, int tienHang, int thanhTien, String trangThai, String maKH, int id) {
        MaDH = maDH;
        HoTen = hoTen;
        this.PTThanhToan = PTThanhToan;
        DiaChi = diaChi;
        TienHang = tienHang;
        ThanhTien = thanhTien;
        TrangThai = trangThai;
        MaKH = maKH;
        this.id = id;
    }

    private  int id;
}
