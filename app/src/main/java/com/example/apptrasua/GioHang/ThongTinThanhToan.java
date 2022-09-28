package com.example.apptrasua.GioHang;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.apptrasua.Adapter.AdapterDiaChi;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.DiaChi;
import com.example.apptrasua.Models.GiaoHang;
import com.example.apptrasua.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class ThongTinThanhToan extends AppCompatActivity {

    TextView ViewPTTT,ViewTTCN,ViewDCTT, quaylai , LoiDiaChiCuThe, LoiDiaChi, LoiSodienthoai ,LoiEmail, LoiHoTen;
    CardView layout_DCTT,layout_PTTT,layout_TTCN,layout_TTChuyenKhoan;
    RadioButton TTChuyenkhoan,TTtructiep;
    RadioGroup RadioGroupPTTT;
    Button tieptheo;
    EditText Sodienthoai,Email,HoTen,diachi,diachicuthe;
    Spinner Spinnerdiachi;
    GiaoHang giaoHang;
    String maKH;
    CheckBox Luudiachi;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    AdapterDiaChi adapterDiaChi;
    boolean check = true;
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
                ThongTinThanhToan();
            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        adapterDiaChi= new AdapterDiaChi(this,R.layout.item_selected,getListDiaChi());
        Spinnerdiachi.setAdapter(adapterDiaChi);
        Spinnerdiachi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DiaChi diaChi=adapterDiaChi.getItem(i);
                String DiaChi=diaChi.getDiaChi();
                String DiaChiCuThe=diaChi.getDiaChiCuThe();

                diachi.setText(DiaChi);
                diachicuthe.setText(DiaChiCuThe);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                check=false;
            }
        });


    }
    private ArrayList<DiaChi> getListDiaChi(){

        ArrayList<DiaChi> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from DiaChiGiaoHang where id="+Comon.id, null);
            int count=cursor.getCount();
            cursor.moveToFirst();
            if (count==0) {

                List.add(new DiaChi("",""));

            }else {
                do{
                    DiaChi diaChi=new DiaChi(cursor.getString(0),cursor.getString(2));
                    List.add(diaChi);
                }while (cursor.moveToNext());
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(ThongTinThanhToan.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(ThongTinThanhToan.this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }

    public void TaoMaKH(){
        int a=0;
        boolean b = true;
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from ThongTinGiaoHang" , null);
            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count==0) {
                maKH=Comon.id+"MAKH"+RanDomAnh(0,10000000);
                giaoHang.setMaKH(maKH);
            }else {
                do{
                    if (!cursor.getString(0).equals(maKH)) {
                        a++;
                        cursor.moveToNext();
                        if (a == count) {
                            giaoHang.setMaKH(maKH);
                           break;
                        }
                    }else {
                        maKH=Comon.id+"MAKH"+RanDomAnh(0,10000000);
                        cursor.moveToFirst();
                        b=true;
                    }
                }while (b);
            }
            close();

        }catch (Exception e){
            e.toString();
        }
    }
    public void AnhXa(){
        LoiDiaChiCuThe=findViewById(R.id.LoiDiaChiCuThe);
        LoiDiaChi=findViewById(R.id.LoiDiaChi);
        LoiSodienthoai=findViewById(R.id.LoiSodienthoai);
        LoiEmail=findViewById(R.id.LoiEmail);
        LoiHoTen=findViewById(R.id.LoiHoTen);

        Sodienthoai=findViewById(R.id.Sodienthoai);
        Email=findViewById(R.id.Email);
        HoTen=findViewById(R.id.HoTen);
        diachi=findViewById(R.id.diachi);
        diachicuthe=findViewById(R.id.diachicuthe);
        TTtructiep=findViewById(R.id.TTtructiep);
        Spinnerdiachi=findViewById(R.id.Spinnerdiachi);
        Luudiachi=findViewById(R.id.Luudiachi);
        quaylai=findViewById(R.id.quaylai);
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
    public void ThongTinThanhToan(){
        String sodienthoaii= Sodienthoai.getText().toString().trim();
        String email= Email.getText().toString();
        String hoTen= HoTen.getText().toString();
        String Diachi= diachi.getText().toString();
        String Diachicuthe= diachicuthe.getText().toString();
        
        layout_DCTT.setVisibility(View.VISIBLE);
        if (Comon.DinhDangSDT(sodienthoaii)){
            try {
                int Sodienthoai=Integer.parseInt(sodienthoaii);
                giaoHang.setSoDienThoai(Sodienthoai);
                LoiSodienthoai.setVisibility(View.GONE);
            }catch (Exception e){
                layout_TTCN.setVisibility(View.VISIBLE);
                LoiSodienthoai.setVisibility(View.VISIBLE);
             //   Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Số điện thọai ", Toast.LENGTH_LONG).show();
            }
        }
        else {
            layout_TTCN.setVisibility(View.VISIBLE);
            LoiSodienthoai.setVisibility(View.VISIBLE);
           // Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Số điện thọai ", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangEmail(email)){
            giaoHang.setEmail(email);
            LoiEmail.setVisibility(View.GONE);
        }
        else {
            layout_TTCN.setVisibility(View.VISIBLE);
            LoiEmail.setVisibility(View.VISIBLE);
          //  Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Email ", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangHoTen(hoTen)){
            giaoHang.setHoTen(hoTen);
            LoiHoTen.setVisibility(View.GONE);
        }
        else {
            layout_TTCN.setVisibility(View.VISIBLE);
            LoiHoTen.setVisibility(View.VISIBLE);
            //Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Họ tên  ", Toast.LENGTH_LONG).show();
        }

        if (Comon.DinhDangDiaChi(Diachi)){
            giaoHang.setDiaChi(Diachi);
            LoiDiaChi.setVisibility(View.GONE);
        }
        else {
            layout_DCTT.setVisibility(View.VISIBLE);
            LoiDiaChi.setVisibility(View.VISIBLE);
           // Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Địa chỉ ", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangDiaChi(Diachicuthe)){
            giaoHang.setDiaChiCuThe(Diachicuthe);
            LoiDiaChiCuThe.setVisibility(View.GONE);
        }
        else {
            layout_DCTT.setVisibility(View.VISIBLE);
            LoiDiaChiCuThe.setVisibility(View.VISIBLE);

        }
        if (Luudiachi.isChecked()){
           LuuDiaChi();
        }
        if (LoiSodienthoai.getVisibility()==View.GONE && Comon.DinhDangSDT(sodienthoaii)&&Comon.DinhDangEmail(email)&&Comon.DinhDangHoTen(hoTen)&&Comon.DinhDangDiaChi(Diachi)&&Comon.DinhDangDiaChi(Diachicuthe)){
            if (TTtructiep.isChecked()||TTChuyenkhoan.isChecked()){
                Intent intent=new Intent(ThongTinThanhToan.this, XacNhanDonHang.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("ThongTinGiaoHang", (Serializable) giaoHang);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else {
                layout_PTTT.setVisibility(View.VISIBLE);
              //  Toast.makeText(ThongTinThanhToan.this, "Bạn chưa chọn phương thức thanh toán", Toast.LENGTH_LONG).show();
            }
        }

    }
    private int RanDomAnh(int min,int max){
        Random random=new Random();
        int a= random.nextInt((max-min)+1)+min;
        return a;
    }
    public void LuuDiaChi(){
        String Diachi= diachi.getText().toString().trim();
        String Diachicuthe= diachicuthe.getText().toString().trim();

        int a=0;
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from DiaChiGiaoHang where id="+Comon.id, null);
            int count=cursor.getCount();
            cursor.moveToFirst();
            if (count==0){
                if (Comon.DinhDangDiaChi(Diachicuthe)&&Comon.DinhDangDiaChi(Diachicuthe)){
                    String sql="insert into DiaChiGiaoHang(DiaChi,id, DiaChiCuThe) values(?,?,?)";
                    SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                    sqLiteStatement.clearBindings();
                    sqLiteStatement.bindString(1,Diachi);
                    sqLiteStatement.bindDouble(2,Comon.id);
                    sqLiteStatement.bindString(3,Diachicuthe);
                    sqLiteStatement.executeInsert();
                    Toast.makeText(ThongTinThanhToan.this, "Địa chỉ đã lưu thành công", Toast.LENGTH_LONG).show();
                    close();
                }
                else {
                    layout_DCTT.setVisibility(View.VISIBLE);
                    LoiDiaChi.setVisibility(View.VISIBLE);
                    LoiDiaChiCuThe.setVisibility(View.VISIBLE);
                    Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Địa chỉ", Toast.LENGTH_LONG).show();
                }
            }
            else {
                cursor.moveToFirst();
                do{
                    if (cursor.getString(0).trim().equals(Diachi)&&cursor.getString(2).trim().equals(Diachicuthe)) {
                        Toast.makeText(ThongTinThanhToan.this, "Địa chỉ đã lưu thành công", Toast.LENGTH_LONG).show();
                        break;
                    }else {
                        a++;
                    }
                }while (cursor.moveToNext());
                if (a==count){
                    if (Comon.DinhDangDiaChi(Diachicuthe)&&Comon.DinhDangDiaChi(Diachicuthe)){
                        String sql="insert into DiaChiGiaoHang(DiaChi,id, DiaChiCuThe) values(?,?,?)";
                        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                        sqLiteStatement.clearBindings();
                        sqLiteStatement.bindString(1,Diachi);
                        sqLiteStatement.bindDouble(2,Comon.id);
                        sqLiteStatement.bindString(3,Diachicuthe);
                        sqLiteStatement.executeInsert();
                        close();
                    }
                    else {
                        layout_DCTT.setVisibility(View.VISIBLE);
                        LoiDiaChi.setVisibility(View.VISIBLE);
                        LoiDiaChiCuThe.setVisibility(View.VISIBLE);
                        Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Địa chỉ", Toast.LENGTH_LONG).show();
                    }
                }

            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(ThongTinThanhToan.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }


    }

}