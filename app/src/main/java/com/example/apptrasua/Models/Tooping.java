package com.example.apptrasua.Models;

public class Tooping {

    private  String MaTP ;
    private  String ChonDuong ;
    private  String Size ;
    private  String ChonDa ;
    private  String ViPhu ;
    private  String MCTHD ;

    public Tooping() {

    }

    public void setMaTP(String maTP) {
        MaTP = maTP;
    }

    public void setChonDuong(String chonDuong) {
        ChonDuong = chonDuong;
    }

    public void setSize(String size) {
        Size = size;
    }

    public void setChonDa(String chonDa) {
        ChonDa = chonDa;
    }

    public void setViPhu(String viPhu) {
        ViPhu = viPhu;
    }

    public void setMCTHD(String MCTHD) {
        this.MCTHD = MCTHD;
    }

    public String getMaTP() {
        return MaTP;
    }

    public String getChonDuong() {
        return ChonDuong;
    }

    public String getSize() {
        return Size;
    }

    public String getChonDa() {
        return ChonDa;
    }

    public String getViPhu() {
        return ViPhu;
    }

    public String getMCTHD() {
        return MCTHD;
    }

    public Tooping(String maTP, String chonDuong, String size, String chonDa, String viPhu, String MCTHD) {
        MaTP = maTP;
        ChonDuong = chonDuong;
        Size = size;
        ChonDa = chonDa;
        ViPhu = viPhu;
        this.MCTHD = MCTHD;
    }


}
