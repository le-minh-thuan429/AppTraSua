package Admin.fragmentAdmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.Models.Taikhoan;
import com.example.apptrasua.R;

import java.util.ArrayList;

import Admin.AdapterAdmin.AdapterDanhMuc;
import Admin.AdapterAdmin.AdapterTaiKhoan;
import Admin.ThemDanhM;

public class DonHangFragment extends Fragment {

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    Context context;
    public RecyclerView list;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_taikhoan_donhang,container,false);
        list=view.findViewById(R.id.list);

        AdapterTaiKhoan useAdapter=new AdapterTaiKhoan(getListTaiKhoan(),getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(useAdapter);
        return view;

    }

    public void oppen(){
        database = DatabaseHandler.initDatabase(getActivity(), DATABASE_NAME);
    }
    public void close(){
        database.close();
    }

    private ArrayList<Taikhoan> getListTaiKhoan(){

        ArrayList<Taikhoan> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from TaiKhoan ", null);
            cursor.moveToFirst();
            do{
                Taikhoan taikhoan=new Taikhoan(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(3));
                List.add(taikhoan);


            }while (cursor.moveToNext());

            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setTitle("Thông báo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }

}
