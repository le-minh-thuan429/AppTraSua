package com.example.apptrasua.TrangChu;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Adapter.AdapterSanPhamTuongTu;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.GioHang.GioHangActivity;
import com.example.apptrasua.Models.GioHang;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.Models.Tooping;
import com.example.apptrasua.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class ChiTietSanPham extends AppCompatActivity {

    TextView ViewTopping,tenSP,gia,chitietsp;
    CardView Topping;
    RecyclerView listSP;
    Button Muangay,giohang,cong,tru,soluong;
    ImageView image;
    RequestTask1 requestTask1;
    RadioGroup ChonDuong,Size,ChonDa,ViPhu;
    RadioButton hatde,tranchau,xuongmai;
    RadioButton Lamam,itda,nhieuda;
    RadioButton sizeL,sizesL,sizesM;
    RadioButton khongduong,itduong,nhieuduong;
    SanPham sanPham;


    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    String MACTHD;
    String maTP;
    Tooping tooping;
    int Soluong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        AnhXa();

        Bundle bundle=getIntent().getExtras();
        sanPham=(SanPham)bundle.get("SanPham");

        tenSP.setText(sanPham.getTenSP());
        gia.setText(Comon.formatMoney(sanPham.getDonGia())+" VND");
        requestTask1 = new RequestTask1();
        requestTask1.execute(sanPham.getLinkAnh());

        GioHang gioHang= new GioHang(MACTHD,"",sanPham.getMaSP(),sanPham.getTenSP(),sanPham.getDonGia(),1,sanPham.getDonGia(),sanPham.getLinkAnh());
        int y=0;
        if(Comon.gioHangArrayList.size()==0){
            MACTHD=Comon.id+"CTHD"+RanDomAnh(0,1000000000);
            gioHang.setMaCTHD(MACTHD);
        }else if(Comon.gioHangArrayList.size()>0) {
            for(GioHang e: Comon.gioHangArrayList) {
                if(e.getMaSP().equals(sanPham.getMaSP())){
                    gioHang.setMaCTHD(e.getMaCTHD().trim());
                    break;
                }else {
                    y++;

                }
            }
            if(y==Comon.gioHangArrayList.size()){
                boolean a=true;
                do{
                    MACTHD=Comon.id+"CTHD"+RanDomAnh(0,1000000000);
                    for(GioHang e: Comon.gioHangArrayList) {
                        if(!e.getMaCTHD().equals(MACTHD)){
                            gioHang.setMaCTHD(MACTHD);
                            a=false;
                            break;
                        }else {
                            a=true;
                        }
                    }
                }while (a);
            }
        }

        soluong.setText(gioHang.getSoLuongMua() + "");
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Soluong=gioHang.getSoLuongMua();
                Soluong++;
                soluong.setText(Soluong + "");
                gioHang.setSoLuongMua(Soluong);
                gioHang.setTong(Soluong * gioHang.getDonGia());

            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Soluong = gioHang.getSoLuongMua();
                if(Soluong>0) {
                    Soluong--;
                    soluong.setText(Soluong + "");
                    gioHang.setSoLuongMua(Soluong);
                    gioHang.setTong(Soluong * gioHang.getDonGia());
                }
                if(Soluong<=0){
                    soluong.setText("0");
                    Soluong=0;
                    gioHang.setSoLuongMua(Soluong);
                    gioHang.setTong(Soluong*gioHang.getDonGia());
                }
            }
        });
        Muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int b=0;
                for(int y=0;y<Comon.gioHangArrayList.size();y++) {
                    GioHang gioHang1=Comon.gioHangArrayList.get(y);
                    if(gioHang1.getMaSP().equals(gioHang.getMaSP())){
                        //  Toast.makeText(ChiTietSanPham.this, "thuan ", Toast.LENGTH_LONG).show();
                        for (int i=0;i<Comon.toopingArrayList.size();i++){
                            Tooping tooping1=Comon.toopingArrayList.get(i);
                            if(tooping1.getMCTHD().trim().equals(gioHang1.getMaCTHD().trim())){
                                if(tooping.getChonDuong().equals("")&&tooping.getSize().equals("")&&tooping.getChonDa().equals("")&&tooping.getViPhu().equals("")){
                                    Comon.gioHangArrayList.set(y,gioHang);
                                    Intent intent=new Intent(ChiTietSanPham.this, GioHangActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(ChiTietSanPham.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_LONG).show();
                                    finish();
                                    break;
                                }else {
                                    Comon.gioHangArrayList.set(y,gioHang);
                                  //  Toast.makeText(ChiTietSanPham.this, "so luong"+gioHang.getSoLuongMua(), Toast.LENGTH_LONG).show();
                                    Comon.toopingArrayList.set(i,tooping);
                                    Intent intent=new Intent(ChiTietSanPham.this, GioHangActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(ChiTietSanPham.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_LONG).show();
                                    finish();
                                    break;
                                }
                            }
                        }
                        break;
                    }else{
                        b++;
                    }
                }
                if(b==Comon.gioHangArrayList.size()){
                    Comon.gioHangArrayList.add(gioHang);
                    Comon.toopingArrayList.add(tooping);
                    Intent intent=new Intent(ChiTietSanPham.this, GioHangActivity.class);
                    startActivity(intent);
                    Toast.makeText(ChiTietSanPham.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        ViewTopping.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    Topping.setVisibility(View.VISIBLE);
                    a=false;
                }else {
                    Topping.setVisibility(View.GONE);
                    a=true;
                }

            }
        });

        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GioHang(gioHang);

            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ChiTietSanPham.this,RecyclerView.HORIZONTAL,false);
        listSP.setLayoutManager(linearLayoutManager);
        AdapterSanPhamTuongTu useAdapter=new AdapterSanPhamTuongTu(getListSP(sanPham.getMaLoai()),ChiTietSanPham.this);
        listSP.setAdapter(useAdapter);
        Tooping(gioHang);
    }
    public void AnhXa(){
        ViewTopping =findViewById(R.id.ViewTopping);
        Topping =findViewById(R.id.Topping);
        listSP =findViewById(R.id.listSP);
        tenSP =findViewById(R.id.tenSP);
        gia =findViewById(R.id.gia);
        chitietsp =findViewById(R.id.chitietsp);
        giohang =findViewById(R.id.giohang);
        image =findViewById(R.id.image);
        Muangay =findViewById(R.id.Muangay);
        soluong =findViewById(R.id.soluong);
        cong =findViewById(R.id.cong);
        tru =findViewById(R.id.tru);

        ChonDuong =findViewById(R.id.ChonDuong);
        Size =findViewById(R.id.Size);
        ChonDa =findViewById(R.id.ChonDa);
        ViPhu =findViewById(R.id.ViPhu);


        hatde =findViewById(R.id.hatde);
        tranchau =findViewById(R.id.tranchau);
        xuongmai =findViewById(R.id.xuongmai);

        Lamam =findViewById(R.id.Lamam);
        nhieuda =findViewById(R.id.nhieuda);
        itda =findViewById(R.id.itda);

        sizeL =findViewById(R.id.sizeL);
        sizesL =findViewById(R.id.sizesL);
        sizesM =findViewById(R.id.sizesM);

        khongduong =findViewById(R.id.khongduong);
        itduong =findViewById(R.id.itduong);
        nhieuduong =findViewById(R.id.nhieuduong);
    }
    private int RanDomAnh(int min,int max){
        Random random=new Random();
        int a= random.nextInt((max-min)+1)+min;
        return a;
    }

    public class RequestTask1 extends AsyncTask<String, Void, Bitmap> {

        Bitmap bitmap,bitmap1;

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap1=Bitmap.createScaledBitmap(bitmap, (int)390, (int)145, true);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap1;
        }
        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            image.setImageBitmap(s);
        }
    }
    public void GioHang(GioHang gioHang){
        AlertDialog.Builder al = new AlertDialog.Builder(ChiTietSanPham.this);
        al.setTitle("Database Demo");
        al.setMessage("Thêm vào giỏ hàng");
        al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int e) {
                int b=0;
                for(int y=0;y<Comon.gioHangArrayList.size();y++) {
                    GioHang gioHang1=Comon.gioHangArrayList.get(y);
                    if(gioHang1.getMaSP().equals(gioHang.getMaSP())){
                        for (int i=0;i<Comon.toopingArrayList.size();i++){
                            Tooping tooping1=Comon.toopingArrayList.get(i);
                            if(tooping1.getMCTHD().trim().equals(gioHang1.getMaCTHD().trim())){
                                if(tooping.getChonDuong().equals("")&&tooping.getSize().equals("")&&tooping.getChonDa().equals("")&&tooping.getViPhu().equals("")){
                                    Comon.gioHangArrayList.set(y,gioHang);
                                    Toast.makeText(ChiTietSanPham.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_LONG).show();
                                    break;
                                }else {
                                    Comon.gioHangArrayList.set(y,gioHang);
                                    Comon.toopingArrayList.set(i,tooping);
                                    Toast.makeText(ChiTietSanPham.this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }
                        break;
                    }else{
                        b++;
                    }
                }
                if(b==Comon.gioHangArrayList.size()){
                    Comon.gioHangArrayList.add(gioHang);
                    Comon.toopingArrayList.add(tooping);
                    Toast.makeText(ChiTietSanPham.this, "Thêm thành công", Toast.LENGTH_LONG).show();

                }
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

    public void oppen(){
        database = DatabaseHandler.initDatabase(ChiTietSanPham.this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }

    private ArrayList<SanPham> getListSP(String maloai){

        ArrayList<SanPham> List=new ArrayList<>();
        Cursor cursor;

        try {
            oppen();
            cursor = database.rawQuery("select * from SanPham where MaLoai ='"+maloai+"'", null);
            cursor.moveToFirst();
            do{
                SanPham sanPham=new SanPham(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                List.add(sanPham);

            }while (cursor.moveToNext());

            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(ChiTietSanPham.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }

    public void Tooping( GioHang gioHang){
        tooping =new Tooping("","","","","","");
        int b=0;
        if(Comon.toopingArrayList.size()==0){
            maTP=Comon.id+"maTP"+RanDomAnh(0,1000000000);
            tooping.setMaTP(maTP);
        }else if(Comon.toopingArrayList.size()>0) {
            boolean a=true;
            do{
                maTP=Comon.id+"maTP"+RanDomAnh(0,1000000000);
                for(Tooping e: Comon.toopingArrayList) {
                    if(!e.getMaTP().equals(maTP)){
                        b++;
                        if(b==Comon.toopingArrayList.size()){
                            tooping.setMaTP(maTP);
                            a=false;
                            break;
                        }
                    }else {
                        a=true;
                        break;
                    }
                }
            }while (a);
            if(b==Comon.toopingArrayList.size()){
                tooping.setMaTP(maTP);
            }
        }
        tooping.setMCTHD(gioHang.getMaCTHD());
        ChonDuong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.khongduong:
                        tooping.setChonDuong("Không đường");

                        break;
                    case R.id.itduong:
                        tooping.setChonDuong("50% đường");

                        break;
                    case R.id.nhieuduong:
                        tooping.setChonDuong("100% đường");
                        break;
                    default:
                        tooping.setChonDuong("");
                }
            }
        });
        Size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.sizeL:
                        tooping.setSize("Size L");

                        break;
                    case R.id.sizesL:
                        tooping.setSize("Size Ls");

                        break;
                    case R.id.sizesM:
                        tooping.setSize("Size M");
                        break;
                    default:
                        tooping.setSize("");
                }
            }
        });
        ChonDa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.Lamam:
                        tooping.setChonDa("Làm ấm");
                        break;
                    case R.id.itda:
                        tooping.setChonDa("50% đá");
                        break;
                    case R.id.nhieuda:
                        tooping.setChonDa("100% đá");
                        break;
                    default:
                        tooping.setChonDa("");
                }
            }
        });
        ViPhu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.hatde:
                        tooping.setViPhu("Thêm hạt dẻ");
                        break;
                    case R.id.tranchau:
                        tooping.setViPhu("Thêm trân châu");
                        break;
                    case R.id.xuongmai:
                        tooping.setViPhu("Thêm hạt xương mai");
                        break;
                    default:
                        tooping.setViPhu("");
                }
            }
        });


    }
}