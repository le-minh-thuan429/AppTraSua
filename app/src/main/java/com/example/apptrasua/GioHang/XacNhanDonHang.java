package com.example.apptrasua.GioHang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Adapter.AdapterXacNhanGioHang;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.DonHang;
import com.example.apptrasua.Models.GiaoHang;
import com.example.apptrasua.Models.GioHang;
import com.example.apptrasua.R;

import java.io.Serializable;
import java.util.Random;

public class XacNhanDonHang extends AppCompatActivity {

    TextView ViewTTDH,MAHD,HoTen,PTTT,Diachi,TienHang, Tong, quaylai;
    CardView layout_TTDH;
    Button Tieptheo;
    GiaoHang giaoHang;
    DonHang donHang;
    RecyclerView list;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    String MaHD="";
    String DiaChi;
    AdapterXacNhanGioHang adapterXacNhanGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang);
        list=findViewById(R.id.list);
        ViewTTDH=findViewById(R.id.ViewTTDH);
        Tieptheo=findViewById(R.id.Tieptheo);
        layout_TTDH=findViewById(R.id.layout_TTDH);

        MAHD=findViewById(R.id.MaHD);
        HoTen=findViewById(R.id.HoTen);
        PTTT=findViewById(R.id.PTTT);
        Diachi=findViewById(R.id.Diachi);
        TienHang=findViewById(R.id.TienHang);
        Tong=findViewById(R.id.Tong);
        quaylai=findViewById(R.id.quaylai);

        Bundle bundle=getIntent().getExtras();
        giaoHang= (GiaoHang) bundle.get("ThongTinGiaoHang");
        DiaChi=giaoHang.getDiaChi()+"\n"+giaoHang.getDiaChiCuThe() ;
        donHang=new DonHang(TaoMaDH(),giaoHang.getHoTen(),giaoHang.getPTThanhToan(),DiaChi, Tong(), Tong(),"Xác nhận",giaoHang.getMaKH(),giaoHang.getId());

        MAHD.setText(donHang.getMaDH());
        HoTen.setText(donHang.getHoTen());
        PTTT.setText(donHang.getPTThanhToan());
        Diachi.setText(donHang.getDiaChi());
        TienHang.setText(Comon.formatMoney(donHang.getTienHang())+" VND");
        Tong.setText(Comon.formatMoney(donHang.getThanhTien())+" VND");
        list.setVisibility(View.VISIBLE);


        adapterXacNhanGioHang=new AdapterXacNhanGioHang(Comon.gioHangArrayList, XacNhanDonHang.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(XacNhanDonHang.this, RecyclerView.VERTICAL,false);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(adapterXacNhanGioHang);
        adapterXacNhanGioHang.notifyDataSetChanged();


        ViewTTDH.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_TTDH.setVisibility(View.VISIBLE);
                    a=false;
                }else {
                    layout_TTDH.setVisibility(View.GONE);
                    a=true;
                }

            }
        });
        Tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(XacNhanDonHang.this, HoanThanh.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("ThongTinGiaoHang", (Serializable) giaoHang);
                bundle.putSerializable("DonHang", (Serializable) donHang);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        UpdateMaHD(donHang);
    }
    public void UpdateMaHD(DonHang donHang){
        for(int i=0;i< Comon.gioHangArrayList.size();i++) {
            GioHang gioHang=Comon.gioHangArrayList.get(i);
            gioHang.setMaDH(donHang.getMaDH());
            Comon.gioHangArrayList.set(i,gioHang);
        }

    }
    public int Tong(){
        int tong=0;
        for(GioHang e: Comon.gioHangArrayList) {
            tong=tong+e.getTong();
        }
        return tong;
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(XacNhanDonHang.this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    private int RanDomAnh(int min,int max){
        Random random=new Random();
        int a= random.nextInt((max-min)+1)+min;
        return a;
    }
    public String TaoMaDH(){
        int a=0;
        boolean b = true;
        Cursor cursor;
        oppen();
        cursor = database.rawQuery("select * from DonHang" , null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        if (count==0) {
            MaHD= Comon.id+"MADH"+RanDomAnh(0,10000000);
            return MaHD;
        }else {
            MaHD= Comon.id+"MADH"+RanDomAnh(0,10000000);
            do{
                if (!cursor.getString(0).equals(MaHD)) {
                    a++;
                    cursor.moveToNext();
                    if (a == count) {
                       break;
                    }
                }else {
                    MaHD=Comon.id+"MADH"+RanDomAnh(0,10000000);
                    cursor.moveToFirst();
                    b=true;
                }
            }while (b);
            if (a == count) {
                return MaHD;
            }
            return MaHD;
        }
    }
}