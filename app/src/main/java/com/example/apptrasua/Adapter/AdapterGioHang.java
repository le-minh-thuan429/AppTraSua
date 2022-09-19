package com.example.apptrasua.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptrasua.Comon;
import com.example.apptrasua.GioHang.GioHangActivity;
import com.example.apptrasua.Models.GioHang;
import com.example.apptrasua.Models.Tooping;
import com.example.apptrasua.R;
import com.example.apptrasua.fragment.GiohangFragment;

import java.util.ArrayList;

public class AdapterGioHang extends RecyclerView.Adapter<AdapterGioHang.GioHangView> {
    ArrayList<GioHang> arrayList;
    Context context;


    public AdapterGioHang(ArrayList<GioHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public GioHangView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptergiohang, parent, false);

        return new GioHangView(convertView);
    }

    int a;
    String maTP;
    @Override
    public void onBindViewHolder(@NonNull GioHangView holder, int position) {
        final GioHang gioHang=this.arrayList.get(position);
        Glide.with(context).load(gioHang.getLinkAnh()).into(holder.img);
        holder.tenSP.setText(gioHang.getTenSP());
        holder.gia.setText("Giá: "+ Comon.formatMoney(gioHang.getDonGia())+" VND");
        holder.soluong.setText(gioHang.getSoLuongMua()+"");

        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a=gioHang.getSoLuongMua();
                a++;
                holder.soluong.setText(a + "");
                gioHang.setSoLuongMua(a);
                gioHang.setTong(a * gioHang.getDonGia());
                notifyDataSetChanged();
                GioHangActivity.Tong.setText(Comon.formatMoney(Tong())+" VND");
                GiohangFragment.Tong.setText(Comon.formatMoney(Tong())+" VND");


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
                    notifyDataSetChanged();
                    GioHangActivity.Tong.setText(Comon.formatMoney(Tong())+" VND");
                    GiohangFragment.Tong.setText(Comon.formatMoney(Tong())+" VND");
                }
                if(a<=0){
                    holder.soluong.setText("0");
                    a=0;
                    gioHang.setSoLuongMua(a);
                    gioHang.setTong(a*gioHang.getDonGia());
                    notifyDataSetChanged();
                    GioHangActivity.Tong.setText(Comon.formatMoney(Tong())+" VND");
                    GiohangFragment.Tong.setText(Comon.formatMoney(Tong())+" VND");

                }
            }
        });
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyDataSetChanged();
                GioHangActivity.Tong.setText(Comon.formatMoney(Tong())+" VND");
                GiohangFragment.Tong.setText(Comon.formatMoney(Tong())+" VND");

            }
        });
        holder.Topping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tooping(Gravity.CENTER,gioHang);
            }
        });
    }
    Tooping tooping;
    TextView Ok;
    RadioGroup ChonDuong,Size,ChonDa,ViPhu;
    RadioButton hatde,tranchau,xuongmai,Lamam,nhieuda,itda,sizeL,sizesL,sizesM,khongduong,itduong,nhieuduong;
    public void Tooping(int gravity,GioHang gioHang){
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_tooping);

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
            dialog.setCancelable(true);
        }

         Ok=dialog.findViewById(R.id.Ok);
         ChonDuong =dialog.findViewById(R.id.ChonDuong);
         Size =dialog.findViewById(R.id.Size);
         ChonDa =dialog.findViewById(R.id.ChonDa);
         ViPhu =dialog.findViewById(R.id.ViPhu);


         hatde =dialog.findViewById(R.id.hatde);
         tranchau =dialog.findViewById(R.id.tranchau);
         xuongmai =dialog.findViewById(R.id.xuongmai);

          Lamam =dialog.findViewById(R.id.Lamam);
          nhieuda =dialog.findViewById(R.id.nhieuda);
          itda =dialog.findViewById(R.id.itda);

          sizeL =dialog.findViewById(R.id.sizeL);
          sizesL =dialog.findViewById(R.id.sizesL);
         sizesM =dialog.findViewById(R.id.sizesM);

         khongduong =dialog.findViewById(R.id.khongduong);
         itduong =dialog.findViewById(R.id.itduong);
         nhieuduong =dialog.findViewById(R.id.nhieuduong);

        ViewTooping( gioHang);

        tooping =new Tooping("","","","","","");
        tooping.setMaTP(maTP);
        tooping.setMCTHD(gioHang.getMaCTHD().trim());

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i=0;i<Comon.toopingArrayList.size();i++){
                    Tooping tooping1=Comon.toopingArrayList.get(i);
                    if(tooping1.getMCTHD().trim().equals(gioHang.getMaCTHD().trim())){
                        if(khongduong.isChecked()){
                            tooping.setChonDuong("Không đường");
                        }

                        if(itduong.isChecked()) {
                            tooping.setChonDuong("50% đường");
                        }

                        if(nhieuduong.isChecked()){
                            tooping.setChonDuong("100% đường");
                        }


                        if(sizeL.isChecked()){
                            tooping.setSize("Size L");
                        }

                        if(sizesL.isChecked()){
                            tooping.setSize("Size Ls");
                        }

                        if(sizesM.isChecked()) {
                            tooping.setSize("Size M");
                        }



                        if(Lamam.isChecked()){
                            tooping.setChonDa("Làm ấm");
                        }

                        if(itda.isChecked()){
                            tooping.setChonDa("50% đá");
                        }

                        if(nhieuda.isChecked()){
                            tooping.setChonDa("100% đá");
                        }


                        if(hatde.isChecked()){
                            tooping.setViPhu("Thêm hạt dẻ");
                        }

                        if(tranchau.isChecked()){
                            tooping.setViPhu("Thêm trân châu");
                        }

                        if(xuongmai.isChecked()){
                            tooping.setViPhu("Thêm hạt xương mai");
                        }

                        Comon.toopingArrayList.set(i,tooping);
                        //Toast.makeText(context, "thuan "+tooping.getChonDuong(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;

                    }

                }
            }
        });

        dialog.show();
}

    public void ViewTooping(GioHang gioHang){
        for (Tooping tooping:Comon.toopingArrayList){
            if(tooping.getMCTHD().trim().equals(gioHang.getMaCTHD().trim())){
                maTP=tooping.getMaTP();
                switch (tooping.getChonDuong().trim()){
                    case "Không đường":
                        khongduong.setChecked(true);
                        break;
                    case "50% đường":
                        itduong.setChecked(true);
                        break;
                    case "100% đường":
                        nhieuduong.setChecked(true);
                        break;
                    default:
                        khongduong.setChecked(false);
                        itduong.setChecked(false);
                        nhieuduong.setChecked(false);
                }

                switch (tooping.getSize().trim()){
                    case "Size L":
                        sizeL.setChecked(true);

                        break;
                    case "Size Ls":
                        sizesL.setChecked(true);

                        break;
                    case "Size M":
                        sizesM.setChecked(true);
                        break;
                    default:
                        sizeL.setChecked(false);
                        sizesL.setChecked(false);
                        sizesM.setChecked(false);
                }
                switch (tooping.getChonDa().trim()){
                    case "Làm ấm":
                        Lamam.setChecked(true);
                        break;
                    case "100% đá":
                        nhieuda.setChecked(true);
                        break;
                    case "50% đá":
                        itda.setChecked(true);
                        break;
                    default:
                        Lamam.setChecked(false);
                        nhieuda.setChecked(false);
                        itda.setChecked(false);
                }
                switch (tooping.getViPhu().trim()){
                    case "Thêm hạt dẻ":
                        hatde.setChecked(true);
                        break;
                    case "Thêm trân châu":
                        tranchau.setChecked(true);
                        break;
                    case "Thêm hạt xương mai":
                        xuongmai.setChecked(true);
                        break;
                    default:
                        hatde.setChecked(false);
                        tranchau.setChecked(false);
                        xuongmai.setChecked(false);
                }
                break;
            }
        }
    }


    public int Tong(){
        int tong=0;
        for(GioHang e: Comon.gioHangArrayList) {
            tong=tong+e.getTong();
        }
        return tong;
    }
    @Override
    public int getItemCount() {
        if(arrayList !=null){
            return arrayList.size();
        }
        return 1;
    }
    public class GioHangView extends RecyclerView.ViewHolder {

        TextView tenSP;
        TextView gia,Topping, xoa,cong,tru,soluong,Ok;
        ImageView img;
        CardView cardView;



        public GioHangView(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tenSP = itemView.findViewById(R.id.tenSP);
            gia=itemView.findViewById(R.id.gia);
            xoa=itemView.findViewById(R.id.xoa);
            cardView=itemView.findViewById(R.id.layout_sanpham);
            Topping=itemView.findViewById(R.id.Topping);
            soluong=itemView.findViewById(R.id.soluong);
            cong=itemView.findViewById(R.id.cong);
            tru=itemView.findViewById(R.id.tru);



        }

    }

}


