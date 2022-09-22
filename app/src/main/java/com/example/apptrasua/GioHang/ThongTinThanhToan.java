package com.example.apptrasua.GioHang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.GiaoHang;
import com.example.apptrasua.R;

import java.util.Random;

public class ThongTinThanhToan extends AppCompatActivity {

    TextView ViewPTTT,ViewTTCN,ViewDCTT;
    CardView layout_DCTT,layout_PTTT,layout_TTCN,layout_TTChuyenKhoan;
    RadioButton TTChuyenkhoan;
    RadioGroup RadioGroupPTTT;
    Button tieptheo;
    EditText Sodienthoai,Email,HoTen,diachi,diachicuthe;
    Spinner Spinnerdiachi;
    GiaoHang giaoHang;
    String maKH;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_thanh_toan);
        AnhXa();
        giaoHang= new GiaoHang("","",0,"","","", Comon.id,"");
        maKH=Comon.id+"MAKH"+RanDomAnh(0,10000000);
        TaoMaKH();;

        ViewPTTT.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_PTTT.setVisibility(View.GONE);
                    a=false;
                }else {
                    layout_PTTT.setVisibility(View.VISIBLE);
                    a=true;
                }

            }
        });
        ViewTTCN.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_TTCN.setVisibility(View.GONE);
                    a=false;
                }else {
                    layout_TTCN.setVisibility(View.VISIBLE);
                    a=true;
                }

            }
        });
        ViewDCTT.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_DCTT.setVisibility(View.GONE);
                    a=false;
                }else {
                    layout_DCTT.setVisibility(View.VISIBLE);
                    a=true;
                }

            }
        });
        RadioGroupPTTT.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.TTtructiep:
                        layout_TTChuyenKhoan.setVisibility(View.GONE);
                        giaoHang.setPTThanhToan("Trực tiếp");
                        break;
                    case R.id.TTChuyenkhoan:
                        layout_TTChuyenKhoan.setVisibility(View.VISIBLE);
                        giaoHang.setPTThanhToan("Chuyển khoản ");
                        break;

                }
            }
        });
        tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ThongTinThanhToan.this, XacNhanDonHang.class);
                startActivity(intent);
            }
        });
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(ThongTinThanhToan.this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }

    public void TaoMaKH(){
        int a=0;
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from ThongTinGiaoHang" , null);
            cursor.moveToFirst();
            do{
                if (!cursor.getString(0).equals(maKH)) {
                    a++;
                }else {
                    maKH=Comon.id+"MAKH"+RanDomAnh(0,10000000);
                    cursor.moveToFirst();
                }
            }while (cursor.moveToNext());
            int count = cursor.getCount();
            if (a == count) {
                giaoHang.setMaKH(maKH);
            }
            close();

        }catch (Exception e){
            e.toString();
        }
    }
    public void AnhXa(){
        Sodienthoai=findViewById(R.id.Sodienthoai);
        Email=findViewById(R.id.Email);
        HoTen=findViewById(R.id.HoTen);
        diachi=findViewById(R.id.diachi);
        diachicuthe=findViewById(R.id.diachicuthe);
        Spinnerdiachi=findViewById(R.id.Spinnerdiachi);

        tieptheo=findViewById(R.id.tieptheo);
        layout_TTChuyenKhoan=findViewById(R.id.layout_TTChuyenKhoan);
        RadioGroupPTTT=findViewById(R.id.RadioGroupPTTT);
        TTChuyenkhoan=findViewById(R.id.TTChuyenkhoan);
        ViewPTTT=findViewById(R.id.ViewPTTT);
        ViewTTCN=findViewById(R.id.ViewTTCN);
        ViewDCTT=findViewById(R.id.ViewDCTT);
        layout_DCTT=findViewById(R.id.layout_DCTT);
        layout_PTTT=findViewById(R.id.layout_PTTT);
        layout_TTCN=findViewById(R.id.layout_TTCN);
    }
    public void ThongTinCaNhan(){
        String sodienthoaii= Sodienthoai.getText().toString();
        int Sodienthoai=Integer.parseInt(sodienthoaii);
        String email= Email.getText().toString();
        String hoTen= HoTen.getText().toString();
        String Diachi= diachi.getText().toString();
        String Diachicuthe= diachicuthe.getText().toString();

        if (Comon.DinhDangSDT(sodienthoaii)){
            giaoHang.setSoDienThoai(Sodienthoai);
        }
        else {
            Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Số điện thọai ", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangEmail(email)){
            giaoHang.setEmail(email);
        }
        else {
            Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Email ", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangHoTen(hoTen)){
            giaoHang.setHoTen(hoTen);
        }
        else {
            Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Họ tên  ", Toast.LENGTH_LONG).show();
        }

        if (Comon.DinhDangDiaChi(Diachi)){
            giaoHang.setDiaChi(Diachi);
        }
        else {
            Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Địa chỉ ", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangDiaChi(Diachicuthe)){
            giaoHang.setDiaChiCuThe(Diachicuthe);
        }
        else {
            Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Địa chỉ Cụ thể", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangSDT(sodienthoaii)&&Comon.DinhDangEmail(email)&&Comon.DinhDangHoTen(hoTen)&&Comon.DinhDangDiaChi(Diachi)&&Comon.DinhDangDiaChi(Diachicuthe)){
            Intent intent=new Intent(ThongTinThanhToan.this, XacNhanDonHang.class);
            startActivity(intent);
        }

    }
    private int RanDomAnh(int min,int max){
        Random random=new Random();
        int a= random.nextInt((max-min)+1)+min;
        return a;
    }

}