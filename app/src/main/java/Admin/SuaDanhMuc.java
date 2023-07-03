package Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.apptrasua.CaNhan.ThongTinCaNhan;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.R;
import com.google.android.material.textfield.TextInputEditText;

public class SuaDanhMuc extends AppCompatActivity {


    TextInputEditText tenloai,link,mota;
    Button Sua;

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    LoaiSP loaiSP;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_danh_muc);
        tenloai = findViewById(R.id.tenloai);
        link= findViewById(R.id.link);
        mota= findViewById(R.id.mota);
        Sua= findViewById(R.id.Sua);

        Bundle bundle=getIntent().getExtras();
        loaiSP=(LoaiSP)bundle.get("LoaiSP");

        tenloai.setText(loaiSP.getTenLoai());
        mota.setText(loaiSP.getGhiChu());
        link.setText(loaiSP.getLinkAnh());


        Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(SuaDanhMuc.this);
                al.setTitle("Sửa danh mục");
                al.setMessage("Bạn muốn Sửa danh mục");
                al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       Sua();
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

    public void Sua(){
        String TenLoai=tenloai.getText().toString();
        String Link=link.getText().toString();
        String Mota=mota.getText().toString();
        try{
            if(TenLoai.equals("")||Link.equals("")||Mota.equals("")){
                AlertDialog.Builder al = new AlertDialog.Builder(SuaDanhMuc.this);
                al.setTitle("Thông báo");
                al.setMessage("Thông tin không được để chống");
                al.create().show();
            }else {
                oppen();
                String sql="update LoaiSP set TenLoai =?,GhiChu=?,LinkAnh=? where MaLoai = ?";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,TenLoai);
                sqLiteStatement.bindString(2,Mota);
                sqLiteStatement.bindString(3,Link);
                sqLiteStatement.bindString(4,loaiSP.getMaLoai());
                sqLiteStatement.executeUpdateDelete();
                sqLiteStatement.execute();

                Toast.makeText(SuaDanhMuc.this, "Sửa thành công  ", Toast.LENGTH_LONG).show();
                close();
                finish();
            }

        }catch (Exception exception){
            Toast.makeText(SuaDanhMuc.this, "Sửa thất bại  ", Toast.LENGTH_LONG).show();
        }
    }


}