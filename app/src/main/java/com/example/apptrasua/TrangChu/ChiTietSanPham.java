package com.example.apptrasua.TrangChu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Adapter.AdapterChiTietSP;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;

public class ChiTietSanPham extends AppCompatActivity {

    RecyclerView list;
    public  static  ChiTietSanPham chiTietSanPham;
    SanPham sanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        list=findViewById(R.id.list);

        Bundle bundle=getIntent().getExtras();
        sanPham=(SanPham)bundle.get("ObJect");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ChiTietSanPham.this, RecyclerView.VERTICAL,false);
        list.setLayoutManager(linearLayoutManager);
        AdapterChiTietSP adapterLoaiSP=new AdapterChiTietSP(sanPham,ChiTietSanPham.this);
        list.setAdapter(adapterLoaiSP);
         chiTietSanPham=new ChiTietSanPham();
    }
    public void thoat(){
        chiTietSanPham.finish();
    }
}