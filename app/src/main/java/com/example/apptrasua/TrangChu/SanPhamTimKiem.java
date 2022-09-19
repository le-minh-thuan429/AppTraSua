package com.example.apptrasua.TrangChu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Adapter.AdapterSanPham;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;

import java.util.ArrayList;

public class SanPhamTimKiem extends AppCompatActivity {

    TextView quaylai;
    RecyclerView listView;
    AdapterSanPham adapterSanPham;

    ArrayList<SanPham> phamList=new ArrayList<>();

    final String DATABASE_NAME = "AppTraSua.db";

    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_tim_kiem);
        listView = findViewById(R.id.list);
        quaylai = findViewById(R.id.quaylai);
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String timkiem = getIntent().getStringExtra("abc").toString().toLowerCase().trim();
    //    Toast.makeText(SanPhamTimKiem.this, timkiem, Toast.LENGTH_LONG).show();

        listView=findViewById(R.id.list);


        adapterSanPham=new AdapterSanPham(getSPTimKiem(timkiem),SanPhamTimKiem.this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(SanPhamTimKiem.this,2);
        listView.setLayoutManager(gridLayoutManager);
        listView.setAdapter(adapterSanPham);
        adapterSanPham.notifyDataSetChanged();

    }

    public void oppen(){
        database = DatabaseHandler.initDatabase(this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    private ArrayList<SanPham> getSPTimKiem(String timkiem ){

        ArrayList<SanPham> List=new ArrayList<>();
        ArrayList<SanPham> List1=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from SanPham " , null);
            cursor.moveToFirst();
            do{
                SanPham sanPham=new SanPham(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                List.add(sanPham);

            }while (cursor.moveToNext());
            for(SanPham sanPham:List){
                if(sanPham.getTenSP().toLowerCase().trim().contains(timkiem)){
                    List1.add(sanPham);
                }
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(SanPhamTimKiem.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List1;
    }
}