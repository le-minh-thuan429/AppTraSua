package Admin.fragmentAdmin;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.R;

import java.util.ArrayList;

import Admin.AdapterAdmin.AdapterDanhMuc;
import Admin.ThemDanhM;

public class DanhMucFragment  extends Fragment {


    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    Context context;
    public RecyclerView list;
    TextView them;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_danhmuc,container,false);
        them=view.findViewById(R.id.them);
        list=view.findViewById(R.id.list);

        AdapterDanhMuc useAdapter=new AdapterDanhMuc(getListLoaiSP(),getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(useAdapter);

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemDanhM.class);
                startActivity(intent);
            }
        });

        return view;

    }

    public void oppen(){
        database = DatabaseHandler.initDatabase(getActivity(), DATABASE_NAME);
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
