package com.example.apptrasua.GioHang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.apptrasua.R;

public class ThongTinThanhToan extends AppCompatActivity {

    TextView ViewPTTT,ViewTTCN,ViewDCTT;
    CardView layout_DCTT,layout_PTTT,layout_TTCN,layout_TTChuyenKhoan;
    RadioButton TTChuyenkhoan;
    RadioGroup RadioGroupPTTT;
    Button tieptheo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_thanh_toan);
        tieptheo=findViewById(R.id.tieptheo);
        layout_TTChuyenKhoan=findViewById(R.id.layout_TTChuyenKhoan);
        RadioGroupPTTT=findViewById(R.id.RadioGroupPTTT);
        TTChuyenkhoan=findViewById(R.id.TTChuyenkhoan);
        ViewPTTT=findViewById(R.id.ViewPTTT);
        ViewTTCN=findViewById(R.id.ViewTTCN);
        ViewDCTT=findViewById(R.id.ViewDCTT);
        layout_DCTT=findViewById(R.id.layout_DCTT);
        layout_PTTT=findViewById(R.id.layout_PTTT);
        layout_TTCN=findViewById(R.id.layout_TTCN);
        ViewPTTT.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_PTTT.setVisibility(View.GONE);
                    a=false;
                }else {
                    layout_PTTT.setVisibility(View.VISIBLE);
                    a=true;
                }

            }
        });
        ViewTTCN.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_TTCN.setVisibility(View.GONE);
                    a=false;
                }else {
                    layout_TTCN.setVisibility(View.VISIBLE);
                    a=true;
                }

            }
        });
        ViewDCTT.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_DCTT.setVisibility(View.GONE);
                    a=false;
                }else {
                    layout_DCTT.setVisibility(View.VISIBLE);
                    a=true;
                }

            }
        });
        RadioGroupPTTT.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.TTtructiep:
                        layout_TTChuyenKhoan.setVisibility(View.GONE);

                        break;
                    case R.id.TTChuyenkhoan:
                        layout_TTChuyenKhoan.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });
        tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ThongTinThanhToan.this, XacNhanDonHang.class);
                startActivity(intent);
            }
        });
    }
}