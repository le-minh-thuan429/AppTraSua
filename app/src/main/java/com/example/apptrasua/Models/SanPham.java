package com.example.apptrasua.Models;

import java.io.Serializable;

public class SanPham implements Serializable {
    String MaSP;
    String TenSP;
    int DonGia;
    String MaLoai;
    String TinhTrang;
    String LinkAnh;

    public String getMaSP() {
        return MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public int getDonGia() {
        return DonGia;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public SanPham(String maSP, String tenSP, int donGia, String maLoai, String tinhTrang, String linkAnh) {
        MaSP = maSP;
        TenSP = tenSP;
        DonGia = donGia;
        MaLoai = maLoai;
        TinhTrang = tinhTrang;
        LinkAnh = linkAnh;
    }
}
