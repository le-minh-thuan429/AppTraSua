package com.example.apptrasua.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Adapter.AdapterGioHang;
import com.example.apptrasua.Comon;
import com.example.apptrasua.Models.GioHang;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;

public class GioHangActivity extends AppCompatActivity {

    SanPham sanPham;
    AdapterGioHang adapterGioHang;
    RecyclerView listGH;
    public static TextView soluong, Tong;
    TextView quaylai;
    Button HoanTat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        listGH=findViewById(R.id.listGH);
        quaylai=findViewById(R.id.quaylai);
        HoanTat=findViewById(R.id.HoanTat);
        soluong=findViewById(R.id.soluong);
        soluong.setText("("+Comon.gioHangArrayList.size()+")");
        Tong=findViewById(R.id.Tong);
        Tong.setText(Comon.formatMoney(Tong())+" VND");

        adapterGioHang=new AdapterGioHang(Comon.gioHangArrayList, GioHangActivity.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(GioHangActivity.this, RecyclerView.VERTICAL,false);
        listGH.setLayoutManager(linearLayoutManager);
        listGH.setAdapter(adapterGioHang);
        adapterGioHang.notifyDataSetChanged();

        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        HoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Comon.gioHangArrayList.size()==0){
                    Toast.makeText(GioHangActivity.this, "Không có sản phẩm nào trong giỏ hàng", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent=new Intent(GioHangActivity.this, ThongTinThanhToan.class);
                    startActivity(intent);
                }
            }
        });

    }

    public int Tong(){
        int tong=0;
        for(GioHang e: Comon.gioHangArrayList) {
           tong=tong+e.getTong();
        }
        return tong;
    }


}