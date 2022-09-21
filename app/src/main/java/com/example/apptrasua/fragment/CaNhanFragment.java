package com.example.apptrasua.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.apptrasua.CaNhan.ThongTinCaNhan;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DangNhap_DangKy;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.MainActivity;
import com.example.apptrasua.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaNhanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaNhanFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public CaNhanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CaNhanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaNhanFragment newInstance(String param1, String param2) {
        CaNhanFragment fragment = new CaNhanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    Button TTCN, TTDH;
    TextView SuaHS,Usename,DangXuat;
    ImageView image;
    CircleImageView profile_image;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    View view;
    private MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        view=inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        TTCN=view.findViewById(R.id.TTCN);
        DangXuat=view.findViewById(R.id.DangXuat);
        SuaHS=view.findViewById(R.id.SuaHS);
        TTDH=view.findViewById(R.id.TTDH);
        Usename=view.findViewById(R.id.Usename);
        Usename.setText( ViewUsename());
        image=view.findViewById(R.id.image);
        profile_image=view.findViewById(R.id.profile_image);
        TTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ThongTinCaNhan.class));
            }
        });
        SuaHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ThongTinCaNhan.class));
            }
        });
        TTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mainActivity=(MainActivity)getActivity();

        DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder al = new AlertDialog.Builder(getContext());
                al.setTitle("Đăng xuất");
                al.setMessage("Bạn muốn đăng xuất");
                al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        oppen();
                        String sql="update TaiKhoan set TrangThai =? where id = ?";
                        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                        sqLiteStatement.clearBindings();
                        sqLiteStatement.bindString(1,"OFF");
                        sqLiteStatement.bindDouble(2,Comon.id);
                        sqLiteStatement.executeUpdateDelete();
                        sqLiteStatement.execute();
                        close();
                        startActivity(new Intent(getContext(), DangNhap_DangKy.class));
                        mainActivity.finish();

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
        ViewAnh();
        return  view;
    }

    public void oppen(){
        database = DatabaseHandler.initDatabase(getActivity(), DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
    public String ViewUsename(){
        String Usename1 = null;
        Cursor cursorTaiKhoan;
        try {
            oppen();
            cursorTaiKhoan = database.rawQuery("select * from TaiKhoan where id = " + Comon.id , null);
            cursorTaiKhoan.moveToFirst();
            int countTK = cursorTaiKhoan.getCount();
            if (countTK == 1) {
                Usename1 = cursorTaiKhoan.getString(1);
            }
            close();

        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
            al.setTitle("Database Demo");
            al.setMessage("Mã không tồn tại");
            al.create().show();

        }
        return Usename1;
    }
    public void ViewAnh(){
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from NguoiDung where id = " + Comon.id , null);
            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count == 1) {
                byte[] hinhanh=cursor.getBlob(6);
                Bitmap bitmap= BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
                profile_image.setImageBitmap(bitmap);

                byte[] Hinhanhbia=cursor.getBlob(7);
                Bitmap bitmap1= BitmapFactory.decodeByteArray(Hinhanhbia,0,Hinhanhbia.length);
                Bitmap bitmap2=Bitmap.createScaledBitmap(bitmap1, (int)390, (int)140, true);
                image.setImageBitmap(bitmap2);
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
            al.setTitle("Database Demo");
            al.setMessage("Mã không tồn tại");
            al.create().show();
        }

    }
}