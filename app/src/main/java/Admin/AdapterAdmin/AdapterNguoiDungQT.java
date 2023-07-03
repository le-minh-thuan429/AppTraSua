package Admin.AdapterAdmin;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptrasua.DatabaseHandler;
import com.example.apptrasua.Models.LoaiSP;
import com.example.apptrasua.Models.Nguoidung;
import com.example.apptrasua.R;

import java.util.ArrayList;

public class AdapterNguoiDungQT extends RecyclerView.Adapter<AdapterNguoiDungQT.NguoiDungQTView>  {
    ArrayList<Nguoidung> arrayList;
    Context context;
    // bundle.putSerializable("ObJect", loaiSP);

    final String DATABASE_NAME = "AppTraSua.db";
    SQLiteDatabase database;

    public AdapterNguoiDungQT(ArrayList<Nguoidung> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterNguoiDungQT.NguoiDungQTView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_nguoidungqt, parent, false);

        return new AdapterNguoiDungQT.NguoiDungQTView(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNguoiDungQT.NguoiDungQTView holder, int position) {
        final Nguoidung nguoidung=this.arrayList.get(position);
        holder.MaND.setText(nguoidung.getId()+"");
        holder.HoTen.setText(nguoidung.getHoTen());
        holder.Sodienthoai.setText(nguoidung.getSoDienThoai()+"");
        holder.email.setText(nguoidung.getEmail());
        holder.Diachi.setText(nguoidung.getDiaChi());

    }

    @Override
    public int getItemCount() {
        if(arrayList !=null){
            return arrayList.size();
        }
        return 1;
    }

    public class NguoiDungQTView  extends RecyclerView.ViewHolder  {
        TextView MaND, HoTen, Sodienthoai, email ,Diachi;

        public NguoiDungQTView(@NonNull View itemView) {
            super(itemView);
            MaND=itemView.findViewById(R.id.MaND);
            HoTen = itemView.findViewById(R.id.HoTen);
            Sodienthoai = itemView.findViewById(R.id.Sodienthoai);
            email = itemView.findViewById(R.id.email);
            Diachi = itemView.findViewById(R.id.Diachi);

        }
    }
    public void oppen(){
        database = DatabaseHandler.initDatabase((Activity)context, DATABASE_NAME);
    }
    public void close(){
        database.close();
    }
}
