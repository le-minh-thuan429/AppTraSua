package com.example.apptrasua.Models;

public class DiaChi {
    private String DiaChi;

    public String getDiaChi() {
        return DiaChi;
    }


    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public void setDiaChiCuThe(String diaChiCuThe) {
        DiaChiCuThe = diaChiCuThe;
    }

    public String getDiaChiCuThe() {
        return DiaChiCuThe;
    }

    public DiaChi(String diaChi, String diaChiCuThe) {
        DiaChi = diaChi;
        DiaChiCuThe = diaChiCuThe;
    }

    private String DiaChiCuThe;
}
