package com.example.apptrasua;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptrasua.Models.Taikhoan;
import com.google.android.material.textfield.TextInputEditText;

public class DangNhap_DangKy extends AppCompatActivity {

    TextView Singup,login,or;
    LinearLayout Singuplayout,loginlayout,layout;
    Button singin;
    ImageView image,image1;
    TextInputEditText email,password,Usename,emailDangky,passworDangky,Confimpassword;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky_dangnhap);

        Usename=findViewById(R.id.Usename);
        emailDangky=findViewById(R.id.emailDangky);
        passworDangky=findViewById(R.id.passworDangky);
        Confimpassword=findViewById(R.id.Confimpassword);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        image=findViewById(R.id.image);
        image1=findViewById(R.id.image1);
        or=findViewById(R.id.or);
        singin=findViewById(R.id.singin);
        Singup=findViewById(R.id.Singup);
        login=findViewById(R.id.login);
        Singuplayout=findViewById(R.id.Singuplayout);
        loginlayout=findViewById(R.id.loginlayout);
        Singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ViewDangky();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDangNhap();
            }
        });

        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginlayout.getVisibility()==View.VISIBLE && Singuplayout.getVisibility()==View.GONE ){
                    Dangnhap();
                }
                if(Singuplayout.getVisibility()==View.VISIBLE && loginlayout.getVisibility()==View.GONE){
                    Dangky();

                }
            }
        });
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    public void ViewDangNhap() {
        login.setBackgroundResource(R.drawable.switch_trcks);
        Singup.setTextColor(getResources().getColor(R.color.Red));
        Singup.setBackground(null);
        login.setTextColor(getResources().getColor(R.color.white));
        Singuplayout.setVisibility(View.GONE);
        loginlayout.setVisibility(View.VISIBLE);
        or.setVisibility(View.VISIBLE);
        image1.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
        singin.setText("????ng nh???p");
    }
    public void ViewDangky(){
        Singup.setBackgroundResource(R.drawable.switch_trcks);
        Singup.setTextColor(getResources().getColor(R.color.white));
        login.setBackground(null);
        login.setTextColor(getResources().getColor(R.color.Red));
        Singuplayout.setVisibility(View.VISIBLE);
        loginlayout.setVisibility(View.GONE);
        singin.setText("????ng k??");
        or.setVisibility(View.GONE);
        image1.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
    }

    public void Dangnhap(){
        int emails = 0;
        int matkhau = 0;
        int h=0;
        oppen();
        Cursor cursor = database.rawQuery("SELECT * FROM  Taikhoan", null);
        String Email = email.getText().toString().trim();
        String Matkhau = password.getText().toString().trim();
        Taikhoan taikhoan= new Taikhoan("","",Email,Matkhau,"");
        if (taikhoan.isValidEmail()&&taikhoan.isValidPassword()) {
            cursor.moveToFirst();
            do {

                if (taikhoan.getEmail().equals(cursor.getString(2))) {
                    emails++;
                }

                if (taikhoan.getMatKhau().equals(cursor.getString(3))) {
                    matkhau++;
                }
                if (taikhoan.getEmail().equals(cursor.getString(2)) && taikhoan.getMatKhau().equals(cursor.getString(3))) {
                  //  Intent in = new Intent(MainActivity.this, MainActivity4.class);
                  //  startActivity(in);
                    ProgressDialog dialog = new ProgressDialog(DangNhap_DangKy.this);
                    dialog.setTitle("??ang ????ng nh???p");
                    dialog.setIcon(R.drawable.anh2);
                    dialog.setMessage("Vui l??ng ch???");
                    dialog.setCancelable(true);
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Comon.id=cursor.getInt(0);
                            oppen();
                            String sql="update TaiKhoan set TrangThai =? where id = ?";
                            SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                            sqLiteStatement.clearBindings();
                            sqLiteStatement.bindString(1,"ON");
                            sqLiteStatement.bindDouble(2,cursor.getInt(0));
                            sqLiteStatement.executeUpdateDelete();
                            sqLiteStatement.execute();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                            Toast.makeText(DangNhap_DangKy.this, "????ng nh???p th??nh c??ng ", Toast.LENGTH_LONG).show();
                        }
                    }, 3000);
                    close();
                    h++;
                    break;
                }
            } while (cursor.moveToNext());
            if(h==0) {
                if (matkhau == 0 && emails == 0) {
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                    al.setTitle("Database Demo");
                    al.setMessage("T??i kho???n kh??ng t???n t???i");
                    al.create().show();
                } else if (emails == 0 && matkhau != 0) {
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                    al.setTitle("Database Demo");
                    al.setMessage("Email kh??ng t???n t???i");
                    al.create().show();
                } else if (matkhau == 0 && emails != 0) {
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                    al.setTitle("Database Demo");
                    al.setMessage("M???t kh???u sai");
                    al.create().show();
                }else if(matkhau!=0 && emails!= 0){
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                    al.setTitle("Database Demo");
                    al.setMessage( "M???t kh???u kh??ng tr??ng kh???p v???i t??i kho???n ");
                    al.create().show();
                }
            }
        }
        else {
            if (!taikhoan.isValidEmail()) {
                AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                al.setTitle("Database Demo");
                al.setMessage( "M???i b???n nh???p ????ng ?????nh d???ng Email");
                al.create().show();
            }
            if (!taikhoan.isValidPassword()) {
                AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                al.setTitle("Database Demo");
                al.setMessage( "M???t kh???u >= 6 k?? t??? v?? kh??ng d???u\nM???i b???n nh???p l???i ");
                al.create().show();
            }
            close();
        }
    }

    public void Dangky(){
        oppen();
        String usename =Usename.getText().toString().trim();
        String PassworDangky=passworDangky.getText().toString().trim();
        String confimpassword =Confimpassword.getText().toString().trim();
        String EmailDangky =emailDangky.getText().toString().trim();

        Taikhoan taikhoan= new Taikhoan("",usename,EmailDangky,PassworDangky,"OFF");
        if(taikhoan.isValidUsename() && taikhoan.isValidEmail() && taikhoan.isValidPassword()) {
            if (taikhoan.getMatKhau().trim().equals(confimpassword)){
                Cursor cursor;
                cursor = database.rawQuery("select * from Taikhoan where Email ='" + EmailDangky + "'", null);
                int count = cursor.getCount();
                if (count == 1) {
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                    al.setTitle("Database Demo");
                    al.setMessage("T??i kho???n ???? t???n t???i !");
                    al.create().show();
                } else if (count == 0) {
                    database.execSQL("insert into Taikhoan(TenTK,Email,MatKhau,TrangThai) values('" + taikhoan.getTenTK() + "','" + taikhoan.getEmail() +  "','" + taikhoan.getMatKhau() + "','" + taikhoan.getTrangThai() + "')");
                    ProgressDialog dialog = new ProgressDialog(DangNhap_DangKy.this);
                    dialog.setTitle("??ang ????ng nh???p");
                    dialog.setIcon(R.drawable.anh2);
                    dialog.setMessage("Vui l??ng ch???");
                    dialog.setCancelable(true);
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            ViewDangNhap();
                            Toast.makeText(DangNhap_DangKy.this, "????ng k?? th??nh c??ng ", Toast.LENGTH_LONG).show();
                        }
                    }, 3000);

                    close();
                }
            }
            else{
                AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                al.setTitle("Database Demo");
                al.setMessage("M???t kh???u nh???p l???i kh??ng tr??ng kh???p !");
                al.create().show();
            }
        }
        else{
            if (!taikhoan.isValidUsename()) {
                AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                al.setTitle("Database Demo");
                al.setMessage("M???i b???n nh???p ????ng ?????nh d???ng Usename\nUsename t??? 6 k?? t??? tr??? l??n !");
                al.create().show();
            }
            if (!taikhoan.isValidEmail()) {
                AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                al.setTitle("Database Demo");
                al.setMessage("M???i b???n nh???p ????ng ?????nh d???ng Email");
                al.create().show();
            }
            if (!taikhoan.isValidPassword()) {
                AlertDialog.Builder al = new AlertDialog.Builder(DangNhap_DangKy.this);
                al.setTitle("Database Demo");
                al.setMessage("M???i b???n nh???p l???i m???t kh???u");
                al.create().show();
            }
        }
    }
}