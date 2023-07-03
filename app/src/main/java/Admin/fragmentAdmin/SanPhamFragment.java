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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Adapter.AdapterSanPham;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;

import java.util.ArrayList;

import Admin.AdapterAdmin.AdapterDanhMuc;
import Admin.AdapterAdmin.AdapterSanPhamQT;
import Admin.ChonDanhMuc;
import Admin.ThemDanhM;
import Admin.ThemSanPham;

public class SanPhamFragment extends Fragment {

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    Context context;
    public RecyclerView list;
    TextView them;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_sanpham,container,false);
        them=view.findViewById(R.id.them);
        list=view.findViewById(R.id.list);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        list.setLayoutManager(gridLayoutManager);
        AdapterSanPhamQT useAdapter=new AdapterSanPhamQT(getSanPham(),getContext());
        list.setAdapter(useAdapter);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChonDanhMuc.class);
                startActivity(intent);
            }
        });

     /*   AdapterDanhMuc useAdapter=new AdapterDanhMuc(getListLoaiSP(),getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(useAdapter);

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemDanhM.class);
                startActivity(intent);
            }
        });*/

        return view;

    }

    public void oppen(){
        database = DatabaseHandler.initDatabase(getActivity(), DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    private ArrayList<SanPham> getSanPham(){

        ArrayList<SanPham> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from SanPham ", null);
            cursor.moveToFirst();
            do{
                SanPham sanPham=new SanPham(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                List.add(sanPham);

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
