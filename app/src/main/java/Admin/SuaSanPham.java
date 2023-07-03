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

import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;
import com.google.android.material.textfield.TextInputEditText;

public class SuaSanPham extends AppCompatActivity {

    TextInputEditText tensanpham,dongia,tinhtrang,link;
    Button Sua;
    SanPham sanPham;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        tensanpham= findViewById(R.id.tensanpham);
        dongia= findViewById(R.id.dongia);
        tinhtrang= findViewById(R.id.tinhtrang);
        link= findViewById(R.id.link);
        Sua= findViewById(R.id.Sua);

        Bundle bundle=getIntent().getExtras();
        sanPham=(SanPham)bundle.get("SanPham");

        tensanpham.setText(sanPham.getTenSP());
        dongia.setText(sanPham.getDonGia()+"");
        link.setText(sanPham.getLinkAnh());
        tinhtrang.setText(sanPham.getTinhTrang());

        Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(SuaSanPham.this);
                al.setTitle("Sửa danh mục");
                al.setMessage("Bạn muốn Sửa thông tin sản phẩm");
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
        String Tensp=tensanpham.getText().toString();
        String TinhTrang=tinhtrang.getText().toString();
        String Link=link.getText().toString();
        String DonGia=dongia.getText().toString();
        int DonGia2=Integer.parseInt(DonGia);
        try{
            if(Tensp.equals("")||Link.equals("")||TinhTrang.equals("")||DonGia.equals("")){
                AlertDialog.Builder al = new AlertDialog.Builder(SuaSanPham.this);
                al.setTitle("Thông báo");
                al.setMessage("Thông tin không được để chống");
                al.create().show();
            }else {
                oppen();
                String sql="update SanPham set TenSP =?,DonGia=?,MaLoai=?,TinhTrang=?,LinkAnh=? where MaSP = ?";
                SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                sqLiteStatement.clearBindings();
                sqLiteStatement.bindString(1,Tensp);
                sqLiteStatement.bindDouble(2,DonGia2);
                sqLiteStatement.bindString(3,sanPham.getMaLoai());
                sqLiteStatement.bindString(4,TinhTrang);
                sqLiteStatement.bindString(5,Link);
                sqLiteStatement.bindString(6,sanPham.getMaSP());
                sqLiteStatement.executeUpdateDelete();
                sqLiteStatement.execute();

                Toast.makeText(SuaSanPham.this, "Sửa thành công  ", Toast.LENGTH_LONG).show();
                close();
                finish();
            }

        }catch (Exception exception){
            Toast.makeText(SuaSanPham.this, "Sửa thất bại  ", Toast.LENGTH_LONG).show();
        }
    }
}