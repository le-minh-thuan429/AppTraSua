package Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apptrasua.CaNhan.ThongTinCaNhan;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DangNhap_DangKy;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.GioHang.HoanThanh;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

public class ThemDanhM extends AppCompatActivity {

    TextInputEditText tenloai,link,mota;
    Button Them;

    String MaLoaiSP;

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    LoaiSP loaiSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_m);
        tenloai= findViewById(R.id.tenloai);
        link= findViewById(R.id.link);
        mota= findViewById(R.id.mota);
        Them= findViewById(R.id.Them);

         loaiSP=new LoaiSP("","","","");
         MaLoaiSP="L0"+RanDomAnh(0,10000000);
         TaoMaLoaiSP();

        Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(ThemDanhM.this);
                al.setTitle("Thêm danh mục");
                al.setMessage("Bạn muốn thêm danh mục");
                al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Luu();
                    }
                });
                al.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                al.create().show();
            }
        });


    }

    public void oppen(){
        database = DatabaseHandler.initDatabase(this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }



    public void Luu(){
        oppen();
        try {

            String TenLoai=tenloai.getText().toString().trim();
            String Link=link.getText().toString().trim();
            String Mota=mota.getText().toString().trim();
           if(TenLoai.equals("")||Link.equals("")||Mota.equals("")){
               AlertDialog.Builder al = new AlertDialog.Builder(ThemDanhM.this);
               al.setTitle("Thông báo");
               al.setMessage("Thông tin không được để chống");
               al.create().show();
           }else {

                String sql="insert into LoaiSP(MaLoai,TenLoai, GhiChu, LinkAnh) values(?,?,?,?)";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,loaiSP.getMaLoai());
                sqLiteStatement.bindString(2,TenLoai);
                sqLiteStatement.bindString(3,Mota);
                sqLiteStatement.bindString(4,Link);
               // sqLiteStatement.executeUpdateDelete();
                sqLiteStatement.executeInsert();
                Toast.makeText(ThemDanhM.this, "Lưu thành công  ", Toast.LENGTH_LONG).show();
                finish();

                // database.execSQL("update NguoiDung set HoTen='" + HoTen + "',SoDienThoai=" + Sodienthoai + ",Email='" + Email + "',DiaChi='" + Diachi + "',id = " + Comon.id + " ,HinhAnhUse='" + hinhanh + "' ,AnhBia='" + hinhanhbia + "' where MaKH = '" + makh + "'");

            close();
           }
        }catch (SQLException exception){

            AlertDialog.Builder al = new AlertDialog.Builder(ThemDanhM.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi Thong Tin Giao Hang");
            al.create().show();
            exception.toString();

        }
    }
    public void TaoMaLoaiSP(){
        int a=0;
        boolean b = true;
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from LoaiSP" , null);
            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count==0) {
                MaLoaiSP="L0"+RanDomAnh(0,10000000);
                loaiSP.setMaLoai(MaLoaiSP);
            }else {
                do{
                    if (!cursor.getString(0).equals(MaLoaiSP)) {
                        a++;
                        cursor.moveToNext();
                        if (a == count) {
                            loaiSP.setMaLoai(MaLoaiSP);
                            break;
                        }
                    }else {
                        MaLoaiSP="L0"+RanDomAnh(0,10000000);
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
    private int RanDomAnh(int min,int max){
        Random random=new Random();
        int a= random.nextInt((max-min)+1)+min;
        return a;
    }
}