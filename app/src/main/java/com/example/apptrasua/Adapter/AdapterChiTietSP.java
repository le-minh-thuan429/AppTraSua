package com.example.apptrasua.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AdapterChiTietSP extends RecyclerView.Adapter<AdapterChiTietSP.CTSPView> {

    Context context;
    SanPham sanPham;

    public AdapterChiTietSP(SanPham sanPham, Context context) {
        this.sanPham = sanPham;
        this.context = context;
    }
    
    @NonNull
    @Override
    public CTSPView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterchitietsp, parent, false);
        return new CTSPView(convertView);
    }

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;
    String maCTHD;
    String maTP;


    int a;
    @Override
    public void onBindViewHolder(@NonNull CTSPView holder, int position) {
        holder.tenSP.setText(sanPham.getTenSP());
        holder.gia.setText(Comon.formatMoney(sanPham.getDonGia())+" VND");
        maCTHD=Comon.id+"CTHD"+Comon.gioHangArrayList.size();

        GioHang gioHang= new GioHang(maCTHD,"",sanPham.getMaSP(),sanPham.getTenSP(),sanPham.getDonGia(),1,sanPham.getDonGia(),sanPham.getLinkAnh());
        holder.soluong.setText(gioHang.getSoLuongMua() + "");
        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a=gioHang.getSoLuongMua();
                a++;
                holder.soluong.setText(a + "");
                gioHang.setSoLuongMua(a);
                gioHang.setTong(a * gioHang.getDonGia());

            }
        });
        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = gioHang.getSoLuongMua();
                if(a>0) {
                    a--;
                    holder.soluong.setText(a + "");
                    gioHang.setSoLuongMua(a);
                    gioHang.setTong(a * gioHang.getDonGia());
                }
                if(a<=0){
                    holder.soluong.setText("0");
                    a=0;
                    gioHang.setSoLuongMua(a);
                    gioHang.setTong(a*gioHang.getDonGia());
                }
            }
        });
       // Glide.with(context).load(sanPham.getLinkAnh()).into(holder.image);
        holder.Muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int b=0;
                for(GioHang e: Comon.gioHangArrayList) {
                    if(e.getMaSP().equals(gioHang.getMaSP())){
                        Intent intent=new Intent(context, GioHangActivity.class);
                        context.startActivity(intent);
                        break;
                    }else{
                        b++;

                    }
                }
                if(b==Comon.gioHangArrayList.size()){
                    Comon.gioHangArrayList.add(gioHang);
                    Intent intent=new Intent(context, GioHangActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        holder.requestTask1.execute(sanPham.getLinkAnh());
        holder.ViewTopping.setOnClickListener(new View.OnClickListener() {
           boolean a=false,b=true;
           @Override
           public void onClick(View view) {

               if(a) {
                   holder.Topping.setVisibility(View.VISIBLE);
                   a=false;
               }else {
                   holder.Topping.setVisibility(View.GONE);
                   a=true;
               }

           }
       });

       holder.giohang.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               GioHang(gioHang);
               Tooping(holder,gioHang);
           }
       });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.listSP.setLayoutManager(linearLayoutManager);
        AdapterSanPhamTuongTu useAdapter=new AdapterSanPhamTuongTu(getListSP(sanPham.getMaLoai()),context);
        holder.listSP.setAdapter(useAdapter);
        Tooping(holder,gioHang);
    }


    public void oppen(){
        database = DatabaseHandler.initDatabase((Activity) context, DATABASE_NAME);
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
            AlertDialog.Builder al = new AlertDialog.Builder(context);
            al.setTitle("Database Demo");
            al.setMessage("Dữ liệu lỗi");
            al.create().show();
        }

        return List;
    }

    public void Tooping( CTSPView holder,GioHang gioHang){
        maTP=Comon.id+"MaTP"+Comon.toopingArrayList.size();
        Tooping tooping =new Tooping("","","","","","");
        tooping.setMCTHD(gioHang.getMaCTHD());
        tooping.setMaTP(maTP);
        holder.ChonDuong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.khongduong:
                        tooping.setChonDuong("Không đường");
                     //   Toast.makeText(context, "thuan "+tooping.getChonDuong(), Toast.LENGTH_LONG).show();

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
        holder.Size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        holder.ChonDa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        holder.ViPhu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        Comon.toopingArrayList.add(tooping);

    }

    public void GioHang(GioHang gioHang){
        AlertDialog.Builder al = new AlertDialog.Builder(context);
        al.setTitle("Database Demo");
        al.setMessage("Thêm vào giỏ hàng");
        al.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int b=0;
                for(GioHang e: Comon.gioHangArrayList) {
                    if(e.getMaSP().equals(gioHang.getMaSP())){
                        Toast.makeText(context, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_LONG).show();
                        break;
                    }else{
                        b++;
                    }
                }
                if(b==Comon.gioHangArrayList.size()){
                    Comon.gioHangArrayList.add(gioHang);
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_LONG).show();

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

    @Override
    public int getItemCount() {
        return 1;
    }

    public class CTSPView extends RecyclerView.ViewHolder {


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
        public CTSPView(@NonNull View itemView) {
            super(itemView);
            ViewTopping =itemView.findViewById(R.id.ViewTopping);
            Topping =itemView.findViewById(R.id.Topping);
            listSP =itemView.findViewById(R.id.listSP);
            tenSP =itemView.findViewById(R.id.tenSP);
            gia =itemView.findViewById(R.id.gia);
            chitietsp =itemView.findViewById(R.id.chitietsp);
            giohang =itemView.findViewById(R.id.giohang);
            image =itemView.findViewById(R.id.image);
            Muangay =itemView.findViewById(R.id.Muangay);
            soluong =itemView.findViewById(R.id.soluong);
            cong =itemView.findViewById(R.id.cong);
            tru =itemView.findViewById(R.id.tru);

            ChonDuong =itemView.findViewById(R.id.ChonDuong);
            Size =itemView.findViewById(R.id.Size);
            ChonDa =itemView.findViewById(R.id.ChonDa);
            ViPhu =itemView.findViewById(R.id.ViPhu);


            hatde =itemView.findViewById(R.id.hatde);
            tranchau =itemView.findViewById(R.id.tranchau);
            xuongmai =itemView.findViewById(R.id.xuongmai);

            Lamam =itemView.findViewById(R.id.Lamam);
            nhieuda =itemView.findViewById(R.id.nhieuda);
            itda =itemView.findViewById(R.id.itda);

            sizeL =itemView.findViewById(R.id.sizeL);
            sizesL =itemView.findViewById(R.id.sizesL);
            sizesM =itemView.findViewById(R.id.sizesM);

            khongduong =itemView.findViewById(R.id.khongduong);
            itduong =itemView.findViewById(R.id.itduong);
            nhieuduong =itemView.findViewById(R.id.nhieuduong);

            requestTask1 = new RequestTask1();

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

    }
}
