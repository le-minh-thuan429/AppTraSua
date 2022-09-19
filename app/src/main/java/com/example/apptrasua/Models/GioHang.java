package com.example.apptrasua.Models;

import java.io.Serializable;

public class GioHang implements Serializable {

    private String MaCTHD;
    private String MaDH;
    private String MaSP;
    private String TenSP;
    private int DonGia;
    private int SoLuongMua;
    private int Tong;
    private String linkAnh;

    public String getMaCTHD() {
        return MaCTHD;
    }

    public String getMaDH() {
        return MaDH;
    }

    public String getMaSP() {
        return MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public int getDonGia() {
        return DonGia;
    }

    public int getSoLuongMua() {
        return SoLuongMua;
    }

    public int getTong() {
        return Tong;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setMaCTHD(String maCTHD) {
        MaCTHD = maCTHD;
    }

    public void setMaDH(String maDH) {
        MaDH = maDH;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public void setDonGia(int donGia) {
        DonGia = donGia;
    }

    public void setSoLuongMua(int soLuongMua) {
        SoLuongMua = soLuongMua;
    }

    public void setTong(int tong) {
        Tong = tong;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public GioHang(String maCTHD, String maDH, String maSP, String tenSP, int donGia, int soLuongMua, int tong, String linkAnh) {
        MaCTHD = maCTHD;
        MaDH = maDH;
        MaSP = maSP;
        TenSP = tenSP;
        DonGia = donGia;
        SoLuongMua = soLuongMua;
        Tong = tong;
        this.linkAnh = linkAnh;
    }
}
