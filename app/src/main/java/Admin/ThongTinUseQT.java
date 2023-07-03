package Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.DonHang;
import com.example.apptrasua.Models.Nguoidung;
import com.example.apptrasua.Models.Taikhoan;
import com.example.apptrasua.R;

import java.util.ArrayList;

import Admin.AdapterAdmin.AdapterDonHang;
import Admin.AdapterAdmin.AdapterNguoiDungQT;

public class ThongTinUseQT extends AppCompatActivity {

    Taikhoan taikhoan;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    public RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_use_qt);

        list=findViewById(R.id.list);
        Bundle bundle=getIntent().getExtras();
        taikhoan=(Taikhoan)bundle.get("Taikhoan");

        AdapterNguoiDungQT useAdapter=new AdapterNguoiDungQT(getListNguoiDung(taikhoan.getId()),this);
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

    private ArrayList<Nguoidung> getListNguoiDung(String id){

        ArrayList<Nguoidung> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from  NguoiDung where id='"+ id +"'", null);
            int count=cursor.getCount();
            cursor.moveToFirst();
            if (count==0) {
                //   List.add(new DonHang("","","","",0,0,"","", Comon.id));
                return List;
            }else {
                do{
                    Nguoidung nguoidung=new Nguoidung(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getBlob(6),cursor.getBlob(7));
                    List.add(nguoidung);

                }while (cursor.moveToNext());
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(this);
            al.setTitle("Thông báo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }
        return List;
    }
}