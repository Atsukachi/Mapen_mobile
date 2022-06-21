package com.example.mapen.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.data.TabelLogKegiatanResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LogKegiatanAdapter extends RecyclerView.Adapter<LogKegiatanAdapter.AdapterLogKeg_vh> {
    private List<TabelLogKegiatanResponse> tabelLogKegiatanResponseList;
    private Context context;
    private final LogKegiatanAdapter.ClickedItem clickedItem;
    String tanggal, id_kegiatan, nama_skp, nama_skp_limit, id, uraian, file, getUrl, imageFile, tgl, extension, nama_unit_kerja, uraianLK, regex;
    int unitkerja, skp, user, file_categories;

    public LogKegiatanAdapter(LogKegiatanAdapter.ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(List<TabelLogKegiatanResponse> tabelLogKegiatanResponseList) {
        this.tabelLogKegiatanResponseList = tabelLogKegiatanResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogKegiatanAdapter.AdapterLogKeg_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new LogKegiatanAdapter.AdapterLogKeg_vh(LayoutInflater.from(context).inflate(R.layout.row_tabellogkegiatan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LogKegiatanAdapter.AdapterLogKeg_vh holder, int position) {

        TabelLogKegiatanResponse tabelLogKegiatanResponse = tabelLogKegiatanResponseList.get(position);
        Log.d("data", String.valueOf(tabelLogKegiatanResponse));

        //Post User ID from TabelLogKegiatan.java
        id = tabelLogKegiatanResponse.getId();
        id_kegiatan = tabelLogKegiatanResponse.getKegiatan_id();
        unitkerja = tabelLogKegiatanResponse.getUnitkerja();
        uraian = tabelLogKegiatanResponse.getUraian();
        uraianLK = uraian.replaceAll("<p>|</p>", "");
        skp = tabelLogKegiatanResponse.getSkp();
        nama_skp = tabelLogKegiatanResponse.getNama_skp();
        nama_skp_limit = tabelLogKegiatanResponse.getNama_skp_limit();
        user = tabelLogKegiatanResponse.getUser();
        tanggal = tabelLogKegiatanResponse.getTanggal();
        file = tabelLogKegiatanResponse.getFile();
        file_categories = tabelLogKegiatanResponse.getFile_categories();
        extension = tabelLogKegiatanResponse.getExtension();
        nama_unit_kerja = tabelLogKegiatanResponse.getNama_unit_kerja();

        holder.skp_lkVH.setText(nama_skp_limit);
        holder.tanggal_lkVH.setText(tanggal);

        holder.btn_detail_lkVH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedDetail(tabelLogKegiatanResponse);
            }
        });
    }

    public interface ClickedItem{
        void ClickedDetail(TabelLogKegiatanResponse tabelLogKegiatanResponse);
    }

    @Override
    public int getItemCount() {
        return tabelLogKegiatanResponseList.size();
    }

    public class AdapterLogKeg_vh extends RecyclerView.ViewHolder {
        TextView skp_lkVH;
        TextView tanggal_lkVH;
        LinearLayout btn_detail_lkVH;

        public AdapterLogKeg_vh(@NonNull View itemView) {
            super(itemView);
            skp_lkVH = itemView.findViewById(R.id.txtSKP_lk);
            tanggal_lkVH = itemView.findViewById(R.id.txtTanggal_lk);
            btn_detail_lkVH = itemView.findViewById(R.id.btn_detail_tabellogkegiatan);
        }
    }
}
