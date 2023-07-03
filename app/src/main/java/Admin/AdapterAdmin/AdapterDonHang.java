package Admin.AdapterAdmin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptrasua.Comon;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.DonHang;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.R;

import java.util.ArrayList;

public class AdapterDonHang extends RecyclerView.Adapter<AdapterDonHang.DonHangView> {

    ArrayList<DonHang> arrayList;
    Context context;

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;

    public AdapterDonHang(ArrayList<DonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterDonHang.DonHangView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_donhangqt, parent, false);
        return new AdapterDonHang.DonHangView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDonHang.DonHangView holder, @SuppressLint("RecyclerView") int position) {

        final DonHang donHang=this.arrayList.get(position);
        holder.MaHD.setText(donHang.getMaDH());
        holder.HoTen.setText(donHang.getHoTen());
        holder.Tong.setText(Comon.formatMoney(donHang.getThanhTien())+" VND");
        holder.TrangThaiDonHang.setText(donHang.getTrangThai());

        holder.ChiTietDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChiTietDonHang(Gravity.CENTER,donHang);


            }
        });
        holder.Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HuyDonHang(Gravity.CENTER,donHang ,position);
              //  holder.TrangThaiDonHang.setText("Đa Hủy");



            }
        });
        holder.Giao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GiaoDonHang(Gravity.CENTER,donHang ,position);
               // holder.TrangThaiDonHang.setText("Đa Giao");


            }
        });
    }
    public void HuyDonHang(int gravity,DonHang donHang ,int position){
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.itemhuydonhang);
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
        Button Ok, trolai ;

        trolai=dialog.findViewById(R.id.trolai);
        Ok=dialog.findViewById(R.id.Ok);

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trangthai="Đã Hủy";
                oppen();
                try {
                    String sql="update DonHang set TrangThai =? where MaDH = ?";
                    SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                    sqLiteStatement.clearBindings();
                    sqLiteStatement.bindString(1,trangthai);
                    sqLiteStatement.bindString(2,donHang.getMaDH());
                    sqLiteStatement.executeUpdateDelete();
                    sqLiteStatement.execute();
                    Toast.makeText(context,"Hủy đơn hàng thành công",Toast.LENGTH_SHORT).show();
                   // arrayList.remove(position);
                    donHang.setTrangThai("Đa Hủy");
                    arrayList.set(position,donHang);
                    notifyDataSetChanged();
                    dialog.dismiss();
                    close();
                }catch (Exception exception){
                    Toast.makeText(context,"Hủy đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                }


            }
        });
        trolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void GiaoDonHang(int gravity,DonHang donHang ,int position){
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.itemhuydonhang);
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
        Button Ok, trolai ;
        TextView DuLieu;

        trolai=dialog.findViewById(R.id.trolai);
        Ok=dialog.findViewById(R.id.Ok);
        DuLieu=dialog.findViewById(R.id.DuLieu);
        DuLieu.setText("Giao đơn hàng này");

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trangthai="Đã Giao";
                oppen();
                try {
                    String sql="update DonHang set TrangThai =? where MaDH = ?";
                    SQLiteStatement sqLiteStatement=database.compileStatement(sql);
                    sqLiteStatement.clearBindings();
                    sqLiteStatement.bindString(1,trangthai);
                    sqLiteStatement.bindString(2,donHang.getMaDH());
                    sqLiteStatement.executeUpdateDelete();
                    sqLiteStatement.execute();
                    Toast.makeText(context,"Giao đơn hàng thành công",Toast.LENGTH_SHORT).show();
                   // arrayList.remove(position);
                    donHang.setTrangThai("Đa Giao");
                    arrayList.set(position,donHang);
                    notifyDataSetChanged();
                    dialog.dismiss();
                    close();
                }catch (Exception exception){
                    Toast.makeText(context,"Giao đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                }

            }
        });
        trolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void ChiTietDonHang(int gravity,DonHang donHang){
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.itemhoadon);
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
        TextView MAHD,HoTen,PTTT,Diachi,TienHang;
        TextView Ok;

        MAHD=dialog.findViewById(R.id.MaHD);
        Ok=dialog.findViewById(R.id.Ok);
        HoTen=dialog.findViewById(R.id.HoTen);
        PTTT=dialog.findViewById(R.id.PTTT);
        Diachi=dialog.findViewById(R.id.Diachi);
        TienHang=dialog.findViewById(R.id.TienHang);

        MAHD.setText(donHang.getMaDH());
        HoTen.setText(donHang.getHoTen());
        PTTT.setText(donHang.getPTThanhToan());
        Diachi.setText(donHang.getDiaChi());
        TienHang.setText(Comon.formatMoney(donHang.getThanhTien())+" VND");

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        if(arrayList !=null){
            return arrayList.size();
        }
        return 1;
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase((Activity)context, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }

    public class DonHangView extends RecyclerView.ViewHolder {

        TextView MaHD, HoTen, Tong, ChiTietDonHang ,TrangThaiDonHang;
        Button Huy,Giao;
        CardView layout_donhang;
        public DonHangView(@NonNull View itemView) {
            super(itemView);
            MaHD=itemView.findViewById(R.id.MaHD);
            HoTen = itemView.findViewById(R.id.HoTen);
            Tong = itemView.findViewById(R.id.Tong);
            ChiTietDonHang = itemView.findViewById(R.id.ChiTietDonHang);
            Giao = itemView.findViewById(R.id.Giao);
            Huy = itemView.findViewById(R.id.Huy);
            layout_donhang = itemView.findViewById(R.id.layout_donhang);
            TrangThaiDonHang = itemView.findViewById(R.id.TrangThaiDonHang);

        }
    }
}
