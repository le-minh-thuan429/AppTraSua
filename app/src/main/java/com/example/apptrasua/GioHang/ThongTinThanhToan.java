package com.example.apptrasua.GioHang;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.example.apptrasua.Adapter.AdapterDiaChi;
import com.example.apptrasua.CaNhan.DiaChiMaps;
import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.DiaChi;
import com.example.apptrasua.Models.DonHang;
import com.example.apptrasua.Models.GiaoHang;
import com.example.apptrasua.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ThongTinThanhToan extends AppCompatActivity {

    TextView ViewPTTT,ViewTTCN,ViewDCTT, quaylai , LoiDiaChiCuThe, LoiDiaChi, LoiSodienthoai ,LoiEmail, LoiHoTen;
    CardView layout_DCTT,layout_PTTT,layout_TTCN;
    RadioButton TTtructiep;
    RadioGroup RadioGroupPTTT;
    Button tieptheo;
    EditText Sodienthoai,Email,HoTen,diachicuthe;
    public  TextView diachi , khoangcach;
    Spinner Spinnerdiachi;
    GiaoHang giaoHang;
    String maKH;
    CheckBox Luudiachi;
    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    AdapterDiaChi adapterDiaChi;
    boolean check = true;
    LinearLayout ViTriHienTai;
    int MY_REQUEST_CODE=10;
    float KhoangCach;

    float distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_thanh_toan);
        AnhXa();
        giaoHang= new GiaoHang("","",0,"","","", Comon.id,"");
        maKH=Comon.id+"MAKH"+RanDomAnh(0,10000000);
        TaoMaKH();
        ViTriHienTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DiaChiMaps diaChiMaps= new DiaChiMaps();
                checkPermission();
               // Intent intent = new Intent(ThongTinThanhToan.this,DiaChiMaps.class);

              //  startActivity(intent);
            }
        });

        //Toast.makeText(ThongTinThanhToan.this, Diachi, Toast.LENGTH_LONG).show();

        diachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Diachi= diachi.getText().toString().trim();
                String Khoangcach = null;

                Intent intent = new Intent(ThongTinThanhToan.this,DiaChiMaps.class);
                intent.putExtra("DiaChi",Diachi);
                intent.putExtra("KhoangCach",Khoangcach);
                startActivityForResult(intent,MY_REQUEST_CODE);
            }
        });


        ViewPTTT.setOnClickListener(new View.OnClickListener() {
            boolean a=false;
            @Override
            public void onClick(View view) {

                if(a) {
                    layout_PTTT.setVisibility(View.GONE);
                  //  ThongBaoQuangDuong(Gravity.CENTER);
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
                      //  layout_TTChuyenKhoan.setVisibility(View.GONE);
                        giaoHang.setPTThanhToan("Trực tiếp");
                        break;


                }
            }
        });
        tieptheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongTinThanhToan();
            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        adapterDiaChi= new AdapterDiaChi(this,R.layout.item_selected,getListDiaChi());
        Spinnerdiachi.setAdapter(adapterDiaChi);
        Spinnerdiachi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DiaChi diaChi=adapterDiaChi.getItem(i);
                String DiaChi=diaChi.getDiaChi();
             //   String DiaChiCuThe=diaChi.getDiaChiCuThe();

                diachi.setText(DiaChi);
               //diachicuthe.setText(DiaChiCuThe);
                khoangcach.setText("Quảng đường giao hàng: "+diaChi.getQuangDuong()+" Km ");
                Comon.QuangDuong=diaChi.getQuangDuong();

               // String Diachi2= diachi.getText().toString().trim();

                //Toast.makeText(ThongTinThanhToan.this, Diachi2, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                check=false;
            }
        });


    }
    private ArrayList<DiaChi> getListDiaChi(){

        ArrayList<DiaChi> List=new ArrayList<>();
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from DiaChiGiaoHang where id="+Comon.id, null);
            int count=cursor.getCount();
            cursor.moveToFirst();
            if (count==0) {

                List.add(new DiaChi("",0,""));

            }else {
                do{
                    DiaChi diaChi=new DiaChi(cursor.getString(0),cursor.getFloat(3),cursor.getString(2));
                    List.add(diaChi);
                }while (cursor.moveToNext());
            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(ThongTinThanhToan.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase(ThongTinThanhToan.this, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }

    public void TaoMaKH(){
        int a=0;
        boolean b = true;
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from ThongTinGiaoHang" , null);
            cursor.moveToFirst();
            int count = cursor.getCount();
            if (count==0) {
                maKH=Comon.id+"MAKH"+RanDomAnh(0,10000000);
                giaoHang.setMaKH(maKH);
            }else {
                do{
                    if (!cursor.getString(0).equals(maKH)) {
                        a++;
                        cursor.moveToNext();
                        if (a == count) {
                            giaoHang.setMaKH(maKH);
                           break;
                        }
                    }else {
                        maKH=Comon.id+"MAKH"+RanDomAnh(0,10000000);
                        cursor.moveToFirst();
                        b=true;
                    }
                }while (b);
            }
            close();

        }catch (Exception e){
            e.toString();
        }
    }
    public void AnhXa(){
        LoiDiaChiCuThe=findViewById(R.id.LoiDiaChiCuThe);
        LoiDiaChi=findViewById(R.id.LoiDiaChi);
        LoiSodienthoai=findViewById(R.id.LoiSodienthoai);
        LoiEmail=findViewById(R.id.LoiEmail);
        LoiHoTen=findViewById(R.id.LoiHoTen);
        khoangcach=findViewById(R.id.khoangcach);

        Sodienthoai=findViewById(R.id.Sodienthoai);
        Email=findViewById(R.id.Email);
        HoTen=findViewById(R.id.HoTen);
        diachi=findViewById(R.id.diachi);
        diachicuthe=findViewById(R.id.diachicuthe);
        TTtructiep=findViewById(R.id.TTtructiep);
        Spinnerdiachi=findViewById(R.id.Spinnerdiachi);
        Luudiachi=findViewById(R.id.Luudiachi);
        quaylai=findViewById(R.id.quaylai);
        tieptheo=findViewById(R.id.tieptheo);
        //layout_TTChuyenKhoan=findViewById(R.id.layout_TTChuyenKhoan);
        RadioGroupPTTT=findViewById(R.id.RadioGroupPTTT);
       // TTChuyenkhoan=findViewById(R.id.TTChuyenkhoan);
        ViewPTTT=findViewById(R.id.ViewPTTT);
        ViewTTCN=findViewById(R.id.ViewTTCN);
        ViewDCTT=findViewById(R.id.ViewDCTT);
        layout_DCTT=findViewById(R.id.layout_DCTT);
        layout_PTTT=findViewById(R.id.layout_PTTT);
        layout_TTCN=findViewById(R.id.layout_TTCN);
        ViTriHienTai=findViewById(R.id.ViTriHienTai);
    }
    public void ThongTinThanhToan(){
        String sodienthoaii= Sodienthoai.getText().toString().trim();
        String email= Email.getText().toString();
        String hoTen= HoTen.getText().toString();
        String Diachi= diachi.getText().toString().trim();
       // String Diachicuthe= diachicuthe.getText().toString();
        
        layout_DCTT.setVisibility(View.VISIBLE);
        if (Comon.DinhDangSDT(sodienthoaii)){
            try {
                int Sodienthoai=Integer.parseInt(sodienthoaii);
                giaoHang.setSoDienThoai(Sodienthoai);
                LoiSodienthoai.setVisibility(View.GONE);
            }catch (Exception e){
                layout_TTCN.setVisibility(View.VISIBLE);
                LoiSodienthoai.setVisibility(View.VISIBLE);
             //   Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Số điện thọai ", Toast.LENGTH_LONG).show();
            }
        }
        else {
            layout_TTCN.setVisibility(View.VISIBLE);
            LoiSodienthoai.setVisibility(View.VISIBLE);
           // Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Số điện thọai ", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangEmail(email)){
            giaoHang.setEmail(email);
            LoiEmail.setVisibility(View.GONE);
        }
        else {
            layout_TTCN.setVisibility(View.VISIBLE);
            LoiEmail.setVisibility(View.VISIBLE);
          //  Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Email ", Toast.LENGTH_LONG).show();
        }
        if (Comon.DinhDangHoTen(hoTen)){
            giaoHang.setHoTen(hoTen);
            LoiHoTen.setVisibility(View.GONE);
        }
        else {
            layout_TTCN.setVisibility(View.VISIBLE);
            LoiHoTen.setVisibility(View.VISIBLE);
            //Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Họ tên  ", Toast.LENGTH_LONG).show();
        }

        if (Comon.DinhDangDiaChi(Diachi)){
            giaoHang.setDiaChi(Diachi);
            LoiDiaChi.setVisibility(View.GONE);
        }
        else {
            layout_DCTT.setVisibility(View.VISIBLE);
            LoiDiaChi.setVisibility(View.VISIBLE);
           // Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Địa chỉ ", Toast.LENGTH_LONG).show();
        }
        /*if (Comon.DinhDangDiaChi(Diachicuthe)){
            giaoHang.setDiaChiCuThe(Diachicuthe);
            LoiDiaChiCuThe.setVisibility(View.GONE);
        }
        else {
            layout_DCTT.setVisibility(View.VISIBLE);
            LoiDiaChiCuThe.setVisibility(View.VISIBLE);

        }*/

        if (LoiSodienthoai.getVisibility()==View.GONE && Comon.DinhDangSDT(sodienthoaii)&&Comon.DinhDangEmail(email)&&Comon.DinhDangHoTen(hoTen)&&Comon.DinhDangDiaChi(Diachi)){
            if (TTtructiep.isChecked()&&CheckQuangDuong()){

                if (Luudiachi.isChecked()){
                    LuuDiaChi();
                }
                Intent intent=new Intent(ThongTinThanhToan.this, XacNhanDonHang.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("ThongTinGiaoHang", (Serializable) giaoHang);
                intent.putExtras(bundle);


                startActivity(intent);

            }
            else {
                layout_PTTT.setVisibility(View.VISIBLE);
               // Toast.makeText(ThongTinThanhToan.this, "Bạn chưa chọn phương thức thanh toán", Toast.LENGTH_LONG).show();
            }
        }

    }


    public boolean CheckQuangDuong(){
        if (Comon.QuangDuong>50){
            ThongBaoQuangDuong(Gravity.CENTER);
            return false;
        }else {
            return true;
        }
    }
    private int RanDomAnh(int min,int max){
        Random random=new Random();
        int a= random.nextInt((max-min)+1)+min;
        return a;
    }
    public void LuuDiaChi(){
        String Diachi= diachi.getText().toString().trim();
        String Diachicuthe= "";
       // diachicuthe.getText().toString().trim();

        int a=0;
        Cursor cursor;
        try {
            oppen();
            cursor = database.rawQuery("select * from DiaChiGiaoHang where id="+Comon.id, null);
            int count=cursor.getCount();
            cursor.moveToFirst();
            if (count==0){
                if (Comon.DinhDangDiaChi(Diachi)){
                    String sql="insert into DiaChiGiaoHang(DiaChi,id, DiaChiCuThe, QuangDuong) values(?,?,?,?)";
                    SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                    sqLiteStatement.clearBindings();
                    sqLiteStatement.bindString(1,Diachi);
                    sqLiteStatement.bindDouble(2,Comon.id);
                    sqLiteStatement.bindString(3,Diachicuthe);
                    sqLiteStatement.bindDouble(4, Comon.QuangDuong);
                    sqLiteStatement.executeInsert();
                    Toast.makeText(ThongTinThanhToan.this, "Địa chỉ đã lưu thành công", Toast.LENGTH_LONG).show();
                    close();
                }
                else {
                    layout_DCTT.setVisibility(View.VISIBLE);
                    LoiDiaChi.setVisibility(View.VISIBLE);
                    //LoiDiaChiCuThe.setVisibility(View.VISIBLE);
                    Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Địa chỉ", Toast.LENGTH_LONG).show();
                }
            }
            else {
                cursor.moveToFirst();
                do{
                    if (cursor.getString(0).trim().equals(Diachi)) {
                        Toast.makeText(ThongTinThanhToan.this, "Địa chỉ đã lưu thành công", Toast.LENGTH_LONG).show();
                        break;
                    }else {
                        a++;
                    }
                }while (cursor.moveToNext());
                if (a==count){
                    if (Comon.DinhDangDiaChi(Diachi)){
                        String sql="insert into DiaChiGiaoHang(DiaChi,id, DiaChiCuThe, QuangDuong) values(?,?,?,?)";
                        SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                        sqLiteStatement.clearBindings();
                        sqLiteStatement.bindString(1,Diachi);
                        sqLiteStatement.bindDouble(2,Comon.id);
                        sqLiteStatement.bindString(3,Diachicuthe);
                        sqLiteStatement.bindDouble(4, Comon.QuangDuong);
                        sqLiteStatement.executeInsert();
                        close();
                    }
                    else {
                        layout_DCTT.setVisibility(View.VISIBLE);
                        LoiDiaChi.setVisibility(View.VISIBLE);
                      //  LoiDiaChiCuThe.setVisibility(View.VISIBLE);
                        Toast.makeText(ThongTinThanhToan.this, "Nhập lại định dạng Địa chỉ", Toast.LENGTH_LONG).show();
                    }
                }

            }
            close();
        }catch (Exception e){
            AlertDialog.Builder al = new AlertDialog.Builder(ThongTinThanhToan.this);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }


    }
    public void checkPermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera
                        getCurrentLocation();
                      //  Toast.makeText(ThongTinThanhToan.this, "Permission GRANTED", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings
                        }
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }
    public void getCurrentLocation() {
        FusedLocationProviderClient mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(getBaseContext());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            return;
                        }
                        refreshLocation(location);

                    }
                });
    }
    public void refreshLocation(Location location)  {
        if(location!=null){
            Geocoder geocoder= new Geocoder(ThongTinThanhToan.this, Locale.getDefault());
            List<Address> addressList= null;
            try {
                addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                String address=addressList.get(0).getAddressLine(0);
                diachi.setText(address);

                KhoangCach(location.getLatitude(),location.getLongitude());



                //  Log.d("Location",address);
             //   Toast.makeText(getBaseContext(), address, Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
    public void KhoangCach(double Latitude,double Longitude){

        Location crntLocation=new Location("crntlocation");
        crntLocation.setLatitude( 21.052659534610523);
        crntLocation.setLongitude(105.83772327957993);


        Location newLocation=new Location("newlocation");
        newLocation.setLatitude(Latitude);
        newLocation.setLongitude(Longitude);
        distance =crntLocation.distanceTo(newLocation)/1000;
        khoangcach.setText("Quảng đường giao hàng: "+distance+" Km ");
        Comon.QuangDuong=distance;
        //   Toast.makeText(DiaChiMaps.this, "Khoảng cách là"+distance, Toast.LENGTH_LONG).show();
    }

    public void ThongBaoQuangDuong(int gravity){
        final Dialog dialog=new Dialog(ThongTinThanhToan.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.itemquangduong);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);
        if(Gravity.CENTER==gravity){
            dialog.setCancelable(false);
        }
        TextView Ok;
        Ok=dialog.findViewById(R.id.Ok);


        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(MY_REQUEST_CODE==requestCode && resultCode==RESULT_OK){
                if(data != null){
                    diachi.setText(data.getStringExtra("DiaChi"));
                    khoangcach.setText("Quảng đường giao hàng: "+data.getStringExtra("KhoangCach")+" KM");
                    KhoangCach=Float.parseFloat(data.getStringExtra("KhoangCach"));
                    Comon.QuangDuong=KhoangCach;
                }else {
                    Toast.makeText(ThongTinThanhToan.this, "Lỗi truyền dữ liệu", Toast.LENGTH_LONG).show();

                }

            }

        }catch (Exception e){
            Toast.makeText(ThongTinThanhToan.this, "Lỗi truyền dữ liệu", Toast.LENGTH_LONG).show();
        }


    }
}