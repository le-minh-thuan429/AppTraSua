package com.example.apptrasua.Models;

import java.io.Serializable;

public class LoaiSP implements Serializable {
    String MaLoai;
    String TenLoai;
    String GhiChu;
    String LinkAnh;

    public LoaiSP(String maLoai, String tenLoai, String ghiChu, String linkAnh) {
        MaLoai = maLoai;
        TenLoai = tenLoai;
        GhiChu = ghiChu;
        LinkAnh = linkAnh;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setMaLoai(String maLoai) {
        MaLoai = maLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }
}
