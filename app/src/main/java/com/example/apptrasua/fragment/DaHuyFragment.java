package com.example.apptrasua.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Adapter.AdapterDonHangDaHuy;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.DonHang;
import com.example.apptrasua.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaHuyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaHuyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView listDH;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    public DaHuyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tap1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DaHuyFragment newInstance(String param1, String param2) {
        DaHuyFragment fragment = new DaHuyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    AdapterDonHangDaHuy adapterDonHangDaHuy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tap3, container, false);
        listDH=view.findViewById(R.id.listDH);

        String trangthai="Đã Hủy";
        adapterDonHangDaHuy=new AdapterDonHangDaHuy(getListDonHang(trangthai), getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        listDH.setLayoutManager(linearLayoutManager);
        listDH.setAdapter(adapterDonHangDaHuy);
        adapterDonHangDaHuy.notifyDataSetChanged();
        return view;
    }

    public void oppen(){
        database = DatabaseHandler.initDatabase(getActivity(), DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    private ArrayList<DonHang> getListDonHang(String trangthai){

        ArrayList<DonHang> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from  DonHang where TrangThai='"+ trangthai +"'", null);
            int count=cursor.getCount();
            cursor.moveToFirst();
            if (count==0) {
                //   List.add(new DonHang("","","","",0,0,"","", Comon.id));
                return List;
            }else {
                do{
                    DonHang donHang=new DonHang(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8),cursor.getInt(9));
                    List.add(donHang);
                }while (cursor.moveToNext());
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(getContext());
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }
        return List;
    }
}