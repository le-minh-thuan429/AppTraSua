package com.example.apptrasua.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.apptrasua.Models.SanPham;
import com.example.apptrasua.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTimKiemTrangChu extends ArrayAdapter<SanPham> {

    private List<SanPham> listSP;
    boolean a=false;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timkiem,parent,false);
        }

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager) getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            view.getApplicationWindowToken(), 0);
                }
                return false;
            }
        });

        AdapterTrangChu.TrangChuView.layout_nen.setVisibility(View.GONE);

       // AdapterTrangChu.TrangChuView.timkiem.setEnabled(true);
        TextView textView=convertView.findViewById(R.id.tv_title);
        ImageView img=convertView.findViewById(R.id.img);

        SanPham sanPham=getItem(position);

        textView.setText(sanPham.getTenSP());
        Glide.with(getContext()).load(sanPham.getLinkAnh()).into(img);
        return convertView;
    }

    public AdapterTimKiemTrangChu(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        listSP=new ArrayList<>(objects);

    }
    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<SanPham> list=new ArrayList<>();
                if(charSequence==null||charSequence.length()==0){
                    list.addAll(listSP);
                }
                else {

                    String tensp=charSequence.toString().toLowerCase().trim();
                        for(SanPham sanPham:listSP){
                            if(sanPham.getTenSP().toLowerCase().contains(tensp)){
                                list.add(sanPham);
                            }
                        }
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values=list;
                filterResults.count=list.size();
                return filterResults;
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
               /* String TenSP=((SanPham) resultValue).getTenSP();
                if(Comon.DinhDangTimkiem(TenSP)){
                    Intent intent1 = new Intent(getContext(), SanPhamTimKiem.class);
                    intent1.putExtra("abc", a);
                    getContext().startActivity(intent1);
                }*/
              //  Toast.makeText(getContext(),"thuannnn", Toast.LENGTH_SHORT).show();
                return ((SanPham) resultValue).getTenSP();
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                clear();
                addAll( (List<SanPham>) filterResults.values);
                AdapterTrangChu.TrangChuView.layout_nen.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        };
    }

}
