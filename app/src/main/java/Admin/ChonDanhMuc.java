package Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.R;

import java.util.ArrayList;

import Admin.AdapterAdmin.AdapterChonDanhMuc;
import Admin.AdapterAdmin.AdapterDanhMuc;

public class ChonDanhMuc extends AppCompatActivity {

    public RecyclerView list;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_danh_muc);
        list=findViewById(R.id.list);

        AdapterChonDanhMuc useAdapter=new AdapterChonDanhMuc(getListLoaiSP(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(useAdapter);
    }

    public void oppen(){
        database = DatabaseHandler.initDatabase(this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }

    private ArrayList<LoaiSP> getListLoaiSP(){

        ArrayList<LoaiSP> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from LoaiSP ", null);
            cursor.moveToFirst();
            do{
                LoaiSP loaiSP=new LoaiSP(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                List.add(loaiSP);

            }while (cursor.moveToNext());

            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }
}