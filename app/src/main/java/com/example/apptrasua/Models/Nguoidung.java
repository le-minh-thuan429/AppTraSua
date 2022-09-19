package com.example.apptrasua.Models;

public class Nguoidung {
    private String MaKH;
    private String HoTen;
    private int SoDienThoai;
    private String Email;
    private String DiaChi;
    private   int id;
    private byte[] HinhAnhUse;
    private byte[] AnhBia;

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

    public byte[] getHinhAnhUse() {
        return HinhAnhUse;
    }

    public byte[] getAnhBia() {
        return AnhBia;
    }

    public Nguoidung(String maKH, String hoTen, int soDienThoai, String email, String diaChi, int id, byte[] hinhAnhUse, byte[] anhBia) {
        MaKH = maKH;
        HoTen = hoTen;
        SoDienThoai = soDienThoai;
        Email = email;
        DiaChi = diaChi;
        this.id = id;
        HinhAnhUse = hinhAnhUse;
        AnhBia = anhBia;
    }
}
