package Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

public class ThemSanPham extends AppCompatActivity {

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    TextInputEditText tensanpham,dongia,tinhtrang,link;
    Button Them;
    String MaSP;
    SanPham sanPham;
    LoaiSP loaiSP;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        tensanpham= findViewById(R.id.tensanpham);
        dongia= findViewById(R.id.dongia);
        tinhtrang= findViewById(R.id.tinhtrang);
        Them= findViewById(R.id.Them);
        link= findViewById(R.id.link);

        Bundle bundle=getIntent().getExtras();
        loaiSP=(LoaiSP)bundle.get("LoaiSP");
        sanPham=new SanPham("","",0,"","","");
        MaSP="SP"+RanDomAnh(0,10000000);
        TaoMaSP();
        Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(ThemSanPham.this);
                al.setTitle("Thêm danh mục");
                al.setMessage("Bạn muốn thêm sản phẩm");
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
        Cursor cursor;
        try {
            oppen();
            String Tensp=tensanpham.getText().toString().trim();
            String TinhTrang=tinhtrang.getText().toString().trim();
            String Link=link.getText().toString().trim();
            String DonGia=dongia.getText().toString().trim();
            int DonGia2=Integer.parseInt(DonGia);
            if(Tensp.equals("")||Link.equals("")||TinhTrang.equals("")||DonGia.equals("")){
                AlertDialog.Builder al = new AlertDialog.Builder(ThemSanPham.this);
                al.setTitle("Thông báo");
                al.setMessage("Thông tin không được để chống");
                al.create().show();
            }else {

                String sql="insert into SanPham(MaSP,TenSP, DonGia, MaLoai, TinhTrang, LinkAnh) values(?,?,?,?,?,?)";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,sanPham.getMaSP());
                sqLiteStatement.bindString(2,Tensp);
                sqLiteStatement.bindDouble(3,DonGia2);
                sqLiteStatement.bindString(4,loaiSP.getMaLoai());
                sqLiteStatement.bindString(5,TinhTrang);
                sqLiteStatement.bindString(6,Link);
               // sqLiteStatement.executeUpdateDelete();
                sqLiteStatement.executeInsert();
                Toast.makeText(ThemSanPham.this, "Lưu thành công  ", Toast.LENGTH_LONG).show();
                finish();

                // database.execSQL("update NguoiDung set HoTen='" + HoTen + "',SoDienThoai=" + Sodienthoai + ",Email='" + Email + "',DiaChi='" + Diachi + "',id = " + Comon.id + " ,HinhAnhUse='" + hinhanh + "' ,AnhBia='" + hinhanhbia + "' where MaKH = '" + makh + "'");

                close();
            }
        }catch (SQLException exception){

            AlertDialog.Builder al = new AlertDialog.Builder(ThemSanPham.this);
            al.setTitle("Thông báo");
            al.setMessage("Dữ liệu lỗi ");
            al.create().show();

        }
    }

    public void TaoMaSP(){
        int a=0;
        boolean b = true;
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from LoaiSP" , null);
            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count==0) {
                MaSP="SP"+RanDomAnh(0,10000000);
                sanPham.setMaSP(MaSP);
            }else {
                do{
                    if (!cursor.getString(0).equals(MaSP)) {
                        a++;
                        cursor.moveToNext();
                        if (a == count) {
                            sanPham.setMaSP(MaSP);
                            break;
                        }
                    }else {
                        MaSP="SP"+RanDomAnh(0,10000000);
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