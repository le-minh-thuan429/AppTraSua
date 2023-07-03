package com.example.apptrasua.Models;


import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import com.example.apptrasua.CaNhan.DiaChiMaps;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class DiaChi {
    private String DiaChi;
    private float QuangDuong;

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

    public float getQuangDuong() {
        return QuangDuong;
    }

    public void setQuangDuong(float quangDuong) {
        QuangDuong = quangDuong;
    }

    public DiaChi(String diaChi, float quangDuong, String diaChiCuThe) {
        DiaChi = diaChi;
        QuangDuong = quangDuong;
        DiaChiCuThe = diaChiCuThe;
    }

    private String DiaChiCuThe;


}
