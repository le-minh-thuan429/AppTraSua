package com.example.apptrasua.TrangChu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Adapter.AdapterSanPhamTheoLoai;
import com.example.apptrasua.Adapter.AdapterTimKiemTheoLoai;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;

import java.util.ArrayList;

public class SanPhamTheoLoai extends AppCompatActivity {

    RecyclerView listSP;
    final String DATABASE_NAME = "AppTraSua.db";
    public static FrameLayout layout_nen;
    TextView tenloai,gotimkiem;
    LoaiSP loaiSP;
    SQLiteDatabase database;
    AutoCompleteTextView timkiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_theo_loai);
        listSP=findViewById(R.id.listSP);
        timkiem=findViewById(R.id.timkiem);
        gotimkiem=findViewById(R.id.gotimkiem);
        tenloai=findViewById(R.id.tenloai);
        layout_nen=findViewById(R.id.layout_nen);
        Bundle bundle=getIntent().getExtras();
        loaiSP=(LoaiSP)bundle.get("ObJect");
        tenloai.setText(loaiSP.getTenLoai());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SanPhamTheoLoai.this, RecyclerView.VERTICAL,false);
        listSP.setLayoutManager(linearLayoutManager);
        AdapterSanPhamTheoLoai adapterLoaiSP=new AdapterSanPhamTheoLoai( getListSP(loaiSP.getMaLoai()),SanPhamTheoLoai.this);
        listSP.setAdapter(adapterLoaiSP);

        AdapterTimKiemTheoLoai countryadapter=new AdapterTimKiemTheoLoai(SanPhamTheoLoai.this, R.layout.item_timkiem,getListSP(loaiSP.getMaLoai()));
        timkiem.setAdapter(countryadapter);
        gotimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a= timkiem.getText().toString().toLowerCase().trim();
                if(Comon.DinhDangTimkiem(a)){
                    Intent intent1 = new Intent(SanPhamTheoLoai.this, SanPhamTimKiem.class);
                    intent1.putExtra("abc", a);
                    startActivity(intent1);
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
    private ArrayList<SanPham> getListSP(String maloai){

        ArrayList<SanPham> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from SanPham where MaLoai = '"+maloai+"'", null);
            cursor.moveToFirst();
            do{
                SanPham sanPham=new SanPham(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                List.add(sanPham);

            }while (cursor.moveToNext());

            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(SanPhamTheoLoai.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }
        return List;
    }
}