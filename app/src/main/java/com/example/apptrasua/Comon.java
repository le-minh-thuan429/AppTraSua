package com.example.apptrasua;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.apptrasua.Models.GioHang;
import com.example.apptrasua.Models.Tooping;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Comon {

    public static ArrayList<GioHang> gioHangArrayList=new ArrayList<>();
    public static ArrayList<Tooping> toopingArrayList=new ArrayList<>();
    public static int id;


    public static Boolean DinhDangTimkiem(String timkiem){
        return !TextUtils.isEmpty(timkiem);
    }
    public static Boolean DinhDangEmail(String email){
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static Boolean DinhDangHoTen(String hoten){
        return !TextUtils.isEmpty(hoten)&& hoten.length() >= 6;
    }
    public static Boolean DinhDangUsename(String Usename ){
        return !TextUtils.isEmpty(Usename) && Usename.length() >= 6;
    }
    public static Boolean DinhDangSDT(String sdt ){
        return !TextUtils.isEmpty(sdt)&& Patterns.PHONE.matcher(sdt).matches();
    }
    public static Boolean DinhDangDiaChi(String diachi ){
        return !TextUtils.isEmpty(diachi) && diachi.length() >= 10;
    }
    public static String formatMoney(int number) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getNumberInstance(localeVN);
        String str1 = currencyVN.format(number);
        return str1;
    }
}
