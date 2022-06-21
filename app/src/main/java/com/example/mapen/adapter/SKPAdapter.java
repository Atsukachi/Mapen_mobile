package com.example.mapen.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapen.R;
import com.example.mapen.data.TabelskpResponse;
import com.example.mapen.skp.NilaiSKP;

import java.util.List;

public class SKPAdapter extends RecyclerView.Adapter<SKPAdapter.AdapterSKP_vh> {
    private List<TabelskpResponse> tabelskpResponseList;
    private Context context;
    private final SKPAdapter.ClickedItem clickedItem;
    String tahun, nama_skp, nama_skp_limit, nilai, cek_validasi, nama_bulan, jml_kegiatan, id_skp;
    int validasi_skp;

    public SKPAdapter(SKPAdapter.ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<TabelskpResponse> tabelskpResponseList) {
        this.tabelskpResponseList = tabelskpResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SKPAdapter.AdapterSKP_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SKPAdapter.AdapterSKP_vh(LayoutInflater.from(context).inflate(R.layout.row_tabelskp, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SKPAdapter.AdapterSKP_vh holder, int position) {

        TabelskpResponse tabelskpResponse = tabelskpResponseList.get(position);
        Log.d("tabelskp", String.valueOf(tabelskpResponse));

        //Post User ID from TabelSKP.java
        id_skp = tabelskpResponse.getId_skp();
        nama_bulan = tabelskpResponse.getNama_bulan();
        tahun = tabelskpResponse.getTahun();
        nama_skp = tabelskpResponse.getNama_skp();
        nama_skp_limit = tabelskpResponse.getNama_skp_limit();
        nilai = tabelskpResponse.getNilai();
        cek_validasi = tabelskpResponse.getCek_validasi();
        validasi_skp = Integer.parseInt(cek_validasi);
        jml_kegiatan = tabelskpResponse.getJml_kegiatan();

        holder.nama_bulanVH.setText(nama_bulan);
        holder.tahunVH.setText(tahun);
        holder.nama_skpVH.setText(nama_skp_limit);
        holder.btn_detailskpVH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedDetail(tabelskpResponse);
            }
        });
    }

    public interface ClickedItem{
        void ClickedDetail(TabelskpResponse tabelskpResponse);
    }

    @Override
    public int getItemCount() {
        return tabelskpResponseList.size();
    }

    public class AdapterSKP_vh extends RecyclerView.ViewHolder {
        TextView nama_bulanVH;
        TextView tahunVH;
        TextView nama_skpVH;
        LinearLayout btn_detailskpVH;

        public AdapterSKP_vh(@NonNull View itemView) {
            super(itemView);
            nama_bulanVH = itemView.findViewById(R.id.txtBulanTabelSKP);
            tahunVH = itemView.findViewById(R.id.txtTahunTabelSKP);
            nama_skpVH = itemView.findViewById(R.id.txtNamaSKPTabelSKP);
            btn_detailskpVH = itemView.findViewById(R.id.btn_detail_tabelskp);
        }
    }
}