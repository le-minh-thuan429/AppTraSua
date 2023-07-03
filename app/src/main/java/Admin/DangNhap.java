package Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.apptrasua.Comon;
import com.example.apptrasua.DangNhap_DangKy;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.Taikhoan;
import com.example.apptrasua.PhanHeNguoiDung;
import com.example.apptrasua.R;
import com.example.apptrasua.Start;
import com.google.android.material.textfield.TextInputEditText;

import Admin.ModelsAM.TaiKhoanAdmin;

public class DangNhap extends AppCompatActivity {


    TextView Singup,login,or;
    LinearLayout Singuplayout,loginlayout,layout;
    Button singin;

    TextInputEditText taikhoan ,password,MaQT,Usename,passworDangky,Confimpassword, MaQTDangKy;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        Usename=findViewById(R.id.Usename);
        MaQTDangKy=findViewById(R.id.MaQTDangKy);
        passworDangky=findViewById(R.id.passworDangky);
        Confimpassword=findViewById(R.id.Confimpassword);

        taikhoan=findViewById(R.id.taikhoan);
        password=findViewById(R.id.password);

        MaQT=findViewById(R.id.MaQT);

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
                    //Intent intent = new Intent(DangNhap.this, MainActivity.class);
                   // startActivity(intent);
                }
                if(Singuplayout.getVisibility()==View.VISIBLE && loginlayout.getVisibility()==View.GONE){
                   // Dangky();

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
       // or.setVisibility(View.VISIBLE);
        //image1.setVisibility(View.VISIBLE);
        //image.setVisibility(View.VISIBLE);
        singin.setText("Đăng nhập");
    }
    public void ViewDangky(){
        Singup.setBackgroundResource(R.drawable.switch_trcks);
        Singup.setTextColor(getResources().getColor(R.color.white));
        login.setBackground(null);
        login.setTextColor(getResources().getColor(R.color.Red));
        Singuplayout.setVisibility(View.VISIBLE);
        loginlayout.setVisibility(View.GONE);
        singin.setText("Đăng ký");
      //  or.setVisibility(View.GONE);
     //   image1.setVisibility(View.GONE);
      //  image.setVisibility(View.GONE);
    }

    public void Dangnhap(){
        int tennguoidung = 0;
        int matkhau = 0;
        int maquangtri = 0;
        int h=0;
        oppen();
        Cursor cursor = database.rawQuery("SELECT * FROM  TaiKhoanAdmin", null);
        String TenNguoiDung = taikhoan.getText().toString().trim();
        String Matkhau = password.getText().toString().trim();
        String MaQuangTri = MaQT.getText().toString().trim();
        TaiKhoanAdmin taikhoan= new TaiKhoanAdmin("",TenNguoiDung,MaQuangTri,Matkhau);
        if (taikhoan.isValidUsename()&&taikhoan.isValidPassword()) {
            cursor.moveToFirst();
            do {

                if (taikhoan.getTenTK().equals(cursor.getString(1))) {
                    tennguoidung++;
                }

                if (taikhoan.getMatKhau().equals(cursor.getString(3))) {
                    matkhau++;
                }
                if (taikhoan.getMaQuanTri().equals(cursor.getString(2))) {
                    maquangtri++;
                }
                if (taikhoan.getTenTK().equals(cursor.getString(1)) && taikhoan.getMatKhau().equals(cursor.getString(3))&& taikhoan.getMaQuanTri().equals(cursor.getString(2))) {
                    //  Intent in = new Intent(MainActivity.this, MainActivity4.class);
                    //  startActivity(in);
                    ProgressDialog dialog = new ProgressDialog(DangNhap.this);
                    dialog.setTitle("Đang đăng nhập");
                    dialog.setIcon(R.drawable.anh2);
                    dialog.setMessage("Vui lòng chờ");
                    dialog.setCancelable(true);
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Comon.idQuangTri=cursor.getInt(0);
                            Intent intent = new Intent(DangNhap.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(DangNhap.this, "Đăng nhập thành công ", Toast.LENGTH_LONG).show();
                        }
                    }, 3000);
                    close();
                    h++;
                    break;
                }
            } while (cursor.moveToNext());
            if(h==0) {
                if (matkhau == 0 && tennguoidung == 0) {
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap.this);
                    al.setTitle("Đăng Nhập");
                    al.setMessage("Tài khoản không tồn tại");
                    al.create().show();
                } else if (tennguoidung == 0 && matkhau != 0) {
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap.this);
                    al.setTitle("Đăng Nhập");
                    al.setMessage("Tên tài khoản không tồn tại");
                    al.create().show();
                } else if (matkhau == 0 && tennguoidung != 0) {
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap.this);
                    al.setTitle("Đăng Nhập");
                    al.setMessage("Mật khẩu sai");
                    al.create().show();
                }
                else if(matkhau!=0 && tennguoidung!= 0&&maquangtri!= 0){
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap.this);
                    al.setTitle("Đăng Nhập");
                    al.setMessage( "Mật khẩu không trùng khớp với tài khoản ");
                    al.create().show();
                }
                else if(matkhau!=0 && tennguoidung!= 0&&maquangtri == 0){
                    AlertDialog.Builder al = new AlertDialog.Builder(DangNhap.this);
                    al.setTitle("Đăng Nhập");
                    al.setMessage( "Mã quản trị bị sai\nMời bạn nhập lại ");
                    al.create().show();
                }
            }
        }
        else {
            if (!taikhoan.isValidUsename()) {
                AlertDialog.Builder al = new AlertDialog.Builder(DangNhap.this);
                al.setTitle("Đăng Nhập");
                al.setMessage( "Mời bạn nhập đúng định dạng Tên tài khoản");
                al.create().show();
            }
            if (!taikhoan.isValidPassword()) {
                AlertDialog.Builder al = new AlertDialog.Builder(DangNhap.this);
                al.setTitle("Đăng Nhập");
                al.setMessage( "Mật khẩu >= 6 ký tự và không dấu\nMời bạn nhập lại ");
                al.create().show();
            }
            close();
        }
    }



}