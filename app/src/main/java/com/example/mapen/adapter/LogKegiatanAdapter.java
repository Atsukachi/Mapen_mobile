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
    String tanggal, id_kegiatan, nama_skp, id, uraian, file, getUrl, imageFile, tgl, extension, nama_unit_kerja, uraianLK, regex;
    int unitkerja, skp, user, file_categories;
    Context mContext;
    SharedPreferences sp;
    DateFormat format;
    Date date;

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
        user = tabelLogKegiatanResponse.getUser();
        tanggal = tabelLogKegiatanResponse.getTanggal();
        file = tabelLogKegiatanResponse.getFile();
        file_categories = tabelLogKegiatanResponse.getFile_categories();
        extension = tabelLogKegiatanResponse.getExtension();
        nama_unit_kerja = tabelLogKegiatanResponse.getNama_unit_kerja();

        holder.kegiatan_idVH.setText(id_kegiatan);
        holder.unitkerjaVH.setText(nama_unit_kerja);
        holder.uraianVH.setText(uraianLK);
        holder.skpVH.setText(nama_skp);
        holder.tanggalVH.setText(tanggal);
        holder.fileVH.setText(file);

        getUrl = ApiClient.kegiatan_imageUrl;
        imageFile = getUrl + file;

        if(file_categories == 1) {
            Glide.with(context)
                    .load(imageFile)
                    .apply(new RequestOptions().override(300, 300))
                    .centerCrop()
                    .into(holder.file_categoriesVH);
        } else if (file_categories == 2) {
            holder.file_categoriesVH.setImageResource(R.drawable.video);
        } else if (file_categories == 3) {
            holder.file_categoriesVH.setImageResource(R.drawable.word);
        } else if (file_categories == 4) {
            holder.file_categoriesVH.setImageResource(R.drawable.excel);
        } else if (file_categories == 5) {
            holder.file_categoriesVH.setImageResource(R.drawable.powerpoint);
        } else if (file_categories == 6) {
            holder.file_categoriesVH.setImageResource(R.drawable.pdf);
        } else if (file_categories == 7) {
            holder.file_categoriesVH.setImageResource(R.drawable.library);
        } else {
            holder.file_categoriesVH.setImageResource(R.drawable.notfound);
        }

        holder.btn_editlogkegVH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedEdit(tabelLogKegiatanResponse);
            }
        });
    }

    public interface ClickedItem{
        void ClickedEdit(TabelLogKegiatanResponse tabelLogKegiatanResponse);
    }

    @Override
    public int getItemCount() {
        return tabelLogKegiatanResponseList.size();
    }

    public class AdapterLogKeg_vh extends RecyclerView.ViewHolder {
        TextView kegiatan_idVH;
        TextView unitkerjaVH;
        TextView uraianVH;
        TextView skpVH;
        TextView tanggalVH;
        TextView fileVH;
        ImageView file_categoriesVH;
        LinearLayout btn_editlogkegVH;

        public AdapterLogKeg_vh(@NonNull View itemView) {
            super(itemView);
            kegiatan_idVH = itemView.findViewById(R.id.txtKegiatanID_lk);
            unitkerjaVH = itemView.findViewById(R.id.txtUnitKerja_lk);
            uraianVH = itemView.findViewById(R.id.txtUraian_lk);
            skpVH = itemView.findViewById(R.id.txtSKP_lk);
            tanggalVH = itemView.findViewById(R.id.txtTanggal_lk);
            fileVH = itemView.findViewById(R.id.txtFile_lk);
            file_categoriesVH = itemView.findViewById(R.id.txtFileCategories_lk);
            btn_editlogkegVH = itemView.findViewById(R.id.btn_edit_tabellogkeg);
        }
    }
}
