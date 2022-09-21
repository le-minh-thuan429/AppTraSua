package com.example.apptrasua.GioHang;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.apptrasua.R;

public class XacNhanDonHang extends AppCompatActivity {

    TextView ViewTTDH;
    CardView layout_TTDH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang);
        ViewTTDH=findViewById(R.id.ViewTTDH);
        layout_TTDH=findViewById(R.id.layout_TTDH);
        ViewTTDH.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_TTDH.setVisibility(View.VISIBLE);
                    a=false;
                }else {
                    layout_TTDH.setVisibility(View.GONE);
                    a=true;
                }

            }
        });
    }
}