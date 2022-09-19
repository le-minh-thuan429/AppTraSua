package com.example.apptrasua.CaNhan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinCaNhan extends AppCompatActivity {
    final int GALERY_REQUEST = 456;
    final int GALERY_REQUEST1 = 556;
    TextView doianh,Usename,hoTen,Sodienthoai,email,diachi;
    TextView[] textViews;
    Bitmap bitmap;
    CircleImageView circleImageView;
    ImageView image_bia;
    final String DATABASE_NAME = "AppTraSua.db";
    Button Luu;
    SQLiteDatabase database;
    String makh;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ca_nhan);
        AnhXa();
        makh="KH"+Comon.id;
        doianh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadAnh(Gravity.BOTTOM);
            }
        });
        Luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //oppen();
               // database.execSQL("delete from  NguoiDung ");
                Luu();
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadAnh(Gravity.BOTTOM);
            }
        });
        textViews = new TextView[5];
        textViews[0] = findViewById(R.id.Usename);
        textViews[1] =  findViewById(R.id.hoTen);
        textViews[2] = findViewById(R.id.Sodienthoai);
        textViews[3] = findViewById(R.id.email);
        textViews[4] =  findViewById(R.id.diachi);

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.Usename:

                            NhapDuLieu(Gravity.CENTER,textViews[0],"Usename");
                            break;
                        case R.id.hoTen:
                            NhapDuLieu(Gravity.CENTER,textViews[1],"Họ tên");
                            break;
                        case R.id.Sodienthoai:
                            NhapDuLieu(Gravity.CENTER,textViews[2],"Số điện thoại");
                            break;
                        case R.id.email:
                            NhapDuLieu(Gravity.CENTER,textViews[3],"Email");
                            break;
                        case R.id.diachi:
                            NhapDuLieu(Gravity.CENTER,textViews[4],"Địa chỉ");
                            break;
                    }
                }

            });
        }

        View();


    }
    public void AnhXa(){
        Luu=findViewById(R.id.Luu);
        doianh=findViewById(R.id.doianh);
        circleImageView=findViewById(R.id.profile_image);
        Usename=findViewById(R.id.Usename);
        hoTen=findViewById(R.id.hoTen);
        Sodienthoai=findViewById(R.id.Sodienthoai);
        email=findViewById(R.id.email);
        diachi=findViewById(R.id.diachi);
        image_bia=findViewById(R.id.image_bia);
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==GALERY_REQUEST){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALERY_REQUEST);
            }
        }
        else if (requestCode==GALERY_REQUEST1){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALERY_REQUEST1);
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Bạn chưa cấp quyền truy cập", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERY_REQUEST && resultCode == RESULT_OK && data!=null) {
            Uri uri =data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == GALERY_REQUEST1 && resultCode == RESULT_OK && data!=null) {
            Uri uri =data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap bitmap1=Bitmap.createScaledBitmap(bitmap, (int)390, (int)140, true);
                image_bia.setImageBitmap(bitmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] Hinhanh(){
        BitmapDrawable bitmapDrawable=(BitmapDrawable) circleImageView.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayInputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayInputStream);
        byte[] hinhanh=byteArrayInputStream.toByteArray();
        return hinhanh;
    }
    public byte[] Hinhanhbia(){
        BitmapDrawable bitmapDrawable=(BitmapDrawable) image_bia.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayInputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayInputStream);
        byte[] hinhanh=byteArrayInputStream.toByteArray();
        return hinhanh;
    }
    public void View(){

        Cursor cursorTaiKhoan;
        try {
            oppen();
            cursorTaiKhoan = database.rawQuery("select * from TaiKhoan where id = " + Comon.id , null);
            cursorTaiKhoan.moveToFirst();
            int countTK = cursorTaiKhoan.getCount();
            if (countTK == 1) {
                textViews[0].setText(cursorTaiKhoan.getString(1));
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(ThongTinCaNhan.this);
            al.setTitle("Database Demo");
            al.setMessage("Mã không tồn tại");
            al.create().show();
        }
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from NguoiDung where id = " + Comon.id , null);
            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count == 1) {
                //Toast.makeText(ThongTinCaNhan.this, "  "+cursor.getString(1), Toast.LENGTH_LONG).show();
                for (int i = 1; i <= 4; i++) {
                    if (cursor.getString(i).equals("")){
                        textViews[i].setText("Thiết lập ngay");
                        textViews[i].setTextColor(getResources().getColor(R.color.Red));
                    }
                    else if(!cursor.getString(i).equals("")&&cursor.getString(i).equals("Thiết lập ngay")) {
                        textViews[i].setText(cursor.getString(i).toString().trim());
                        textViews[i].setTextColor(getResources().getColor(R.color.Red));
                    }
                    else {
                        textViews[i].setText(cursor.getString(i).toString().trim());
                        textViews[i].setTextColor(getResources().getColor(R.color.black));
                    }
                }
                byte[] hinhanh=cursor.getBlob(6);
                Bitmap bitmap= BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
                circleImageView.setImageBitmap(bitmap);

                byte[] Hinhanhbia=cursor.getBlob(7);
                Bitmap bitmap1= BitmapFactory.decodeByteArray(Hinhanhbia,0,Hinhanhbia.length);
                Bitmap bitmap2=Bitmap.createScaledBitmap(bitmap1, (int)390, (int)140, true);
                image_bia.setImageBitmap(bitmap2);
            }
            if (count == 0){
               // Toast.makeText(ThongTinCaNhan.this, "không  ", Toast.LENGTH_LONG).show();
                makh="KH"+Comon.id;
                String sql="insert into NguoiDung(MaKH,HoTen, SoDienThoai, Email, DiaChi, id, HinhAnhUse, AnhBia) values(?,?,?,?,?,?,?,?)";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,makh);
                sqLiteStatement.bindString(2,"");
                sqLiteStatement.bindString(3,"");
                sqLiteStatement.bindString(4,"");
                sqLiteStatement.bindString(5,"");
                sqLiteStatement.bindDouble(6,Comon.id);
                sqLiteStatement.bindBlob(7,Hinhanh());
                sqLiteStatement.bindBlob(8,Hinhanhbia());
                sqLiteStatement.executeInsert();
              //  database.execSQL("insert into NguoiDung values('" + makh + "','" + null +  "'," + null + ",'" + null + "','" + null + "'," + Comon.id +  "," + hinhanh + "," + hinhanh + ")");
            }
            close();
        }catch (Exception exception){
            AlertDialog.Builder al = new AlertDialog.Builder(ThongTinCaNhan.this);
            al.setTitle("Database Demo");
            al.setMessage("Mã không tồn tại");
            al.create().show();
        }
    }
    public void Luu(){
        Cursor cursorTaiKhoan;
        String usename=Usename.getText().toString();
        try {
            oppen();
            cursorTaiKhoan = database.rawQuery("select * from TaiKhoan where id = " + Comon.id , null);
            cursorTaiKhoan.moveToFirst();
            int countTK = cursorTaiKhoan.getCount();
            if (countTK == 1) {
                String sql="update TaiKhoan set TenTK =? where id = ?";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,usename);
                sqLiteStatement.bindDouble(2,Comon.id);
                sqLiteStatement.executeUpdateDelete();
                sqLiteStatement.execute();
            }
            if (countTK == 0) {
                AlertDialog.Builder al = new AlertDialog.Builder(ThongTinCaNhan.this);
                al.setTitle("Database Demo");
                al.setMessage("Tài khoản không tồn tại");
                al.create().show();
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(ThongTinCaNhan.this);
            al.setTitle("Database Demo");
            al.setMessage("Tài khoản không tồn tại");
            al.create().show();
        }

        Cursor cursor;
        try {
            oppen();
            String HoTen=hoTen.getText().toString();
            String Email=email.getText().toString();
            String Diachi=diachi.getText().toString();
            cursor = database.rawQuery("select * from NguoiDung where id = " + Comon.id , null);
            cursor.moveToFirst();
             int count = cursor.getCount();
            if (count == 1) {
                String sodienthoai=Sodienthoai.getText().toString();
                int Sodienthoai=Integer.parseInt(sodienthoai);
                String sql="update NguoiDung set HoTen =?,SoDienThoai=?,Email=?,DiaChi=?,id = ?,HinhAnhUse=?,AnhBia=? where MaKH = ?";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,HoTen);
                sqLiteStatement.bindDouble(2,Sodienthoai);
                sqLiteStatement.bindString(3,Email);
                sqLiteStatement.bindString(4,Diachi);
                sqLiteStatement.bindDouble(5,Comon.id);
                sqLiteStatement.bindBlob(6,Hinhanh());
                sqLiteStatement.bindBlob(7,Hinhanhbia());
                sqLiteStatement.bindString(8,makh);
                sqLiteStatement.executeUpdateDelete();
                sqLiteStatement.execute();
                View();
                Toast.makeText(ThongTinCaNhan.this, "Lưu thành công  ", Toast.LENGTH_LONG).show();
                close();

                // database.execSQL("update NguoiDung set HoTen='" + HoTen + "',SoDienThoai=" + Sodienthoai + ",Email='" + Email + "',DiaChi='" + Diachi + "',id = " + Comon.id + " ,HinhAnhUse='" + hinhanh + "' ,AnhBia='" + hinhanhbia + "' where MaKH = '" + makh + "'");
                }
            if (count == 0){
                AlertDialog.Builder al = new AlertDialog.Builder(ThongTinCaNhan.this);
                al.setTitle("Database Demo");
                al.setMessage("Tài khoản không tồn tại");
                al.create().show();
             }
             close();
        }catch (SQLException exception){

            Toast.makeText(ThongTinCaNhan.this, "Số điện thọai có 9 chữ số ", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            String HoTen=hoTen.getText().toString();
            String Email=email.getText().toString();
            String Diachi=diachi.getText().toString();
            try{
                oppen();
                String sql="update NguoiDung set HoTen =?,SoDienThoai=?,Email=?,DiaChi=?,id = ?,HinhAnhUse=?,AnhBia=? where MaKH = ?";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,HoTen);
                sqLiteStatement.bindString(2,"");
                sqLiteStatement.bindString(3,Email);
                sqLiteStatement.bindString(4,Diachi);
                sqLiteStatement.bindDouble(5,Comon.id);
                sqLiteStatement.bindBlob(6,Hinhanh());
                sqLiteStatement.bindBlob(7,Hinhanhbia());
                sqLiteStatement.bindString(8,makh);
                sqLiteStatement.executeUpdateDelete();
                sqLiteStatement.execute();
                View();
                Toast.makeText(ThongTinCaNhan.this, "Lưu thành công  ", Toast.LENGTH_LONG).show();
                close();

            }catch (Exception exception){
                Toast.makeText(ThongTinCaNhan.this, "Lưu thất bại  ", Toast.LENGTH_LONG).show();
            }

        }
    }
    public void NhapDuLieu(int gravity,TextView textView,String danhmuc){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.itemnhapdulieu);

        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);
        if(Gravity.CENTER==gravity){
            dialog.setCancelable(false);
        }

        TextView Danhmuc=dialog.findViewById(R.id.DanhMuc);
        EditText DuLieu =dialog.findViewById(R.id.DuLieu);
        EditText DuLieuSDT =dialog.findViewById(R.id.DuLieuSDT);
        Button trolai=dialog.findViewById(R.id.trolai);
        Button Ok=dialog.findViewById(R.id.Ok);
        Danhmuc.setText(danhmuc.toString());
        DuLieu.setText(textView.getText().toString());
        if(danhmuc.contains("Số điện thoại")){
            DuLieu.setVisibility(View.GONE);
            DuLieuSDT.setVisibility(View.VISIBLE);
            DuLieuSDT.setText(textView.getText().toString());
        }else {
            DuLieu.setVisibility(View.VISIBLE);
            DuLieuSDT.setVisibility(View.GONE);
        }
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (danhmuc) {
                    case "Usename":

                        if(Comon.DinhDangUsename(DuLieu.getText().toString().trim())){
                            textView.setText(""+DuLieu.getText().toString().trim());
                            dialog.dismiss();
                        }else {
                            Toast.makeText(ThongTinCaNhan.this, "Nhập lại định dạng ", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Họ tên":

                        if(Comon.DinhDangHoTen(DuLieu.getText().toString().trim())){
                            textView.setText(""+DuLieu.getText().toString().trim());
                            dialog.dismiss();
                        }else {
                            Toast.makeText(ThongTinCaNhan.this, "Nhập lại định dạng ", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Số điện thoại":

                        if(Comon.DinhDangSDT(DuLieuSDT.getText().toString())){
                            textView.setText(""+DuLieuSDT.getText().toString().trim());
                            dialog.dismiss();
                        }else {
                            Toast.makeText(ThongTinCaNhan.this, "Nhập lại định dạng ", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Email":

                        if(Comon.DinhDangEmail(DuLieu.getText().toString().trim())){
                            textView.setText(""+DuLieu.getText().toString().trim());
                            dialog.dismiss();
                        }else {
                            Toast.makeText(ThongTinCaNhan.this, "Nhập lại định dạng ", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Địa chỉ":
                        if(Comon.DinhDangDiaChi(DuLieu.getText().toString().trim())){
                            textView.setText(""+DuLieu.getText().toString().trim());
                            dialog.dismiss();
                        }else {
                            Toast.makeText(ThongTinCaNhan.this, "Nhập lại định dạng ", Toast.LENGTH_LONG).show();
                        }
                        break;
                }

            }
        });
        trolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void LoadAnh(int gravity){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.itemloadanh);

        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);
        if(Gravity.BOTTOM==gravity){
            dialog.setCancelable(true);
        }

        Button Anhdaidien=dialog.findViewById(R.id.Anhdaidien);
        Button AnhBia=dialog.findViewById(R.id.AnhBia);
        Anhdaidien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(ThongTinCaNhan.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALERY_REQUEST);
                dialog.dismiss();
            };
        });

        AnhBia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(ThongTinCaNhan.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALERY_REQUEST1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}