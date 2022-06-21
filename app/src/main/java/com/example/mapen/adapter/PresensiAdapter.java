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
import com.example.mapen.data.TabelPresensiResponse;
import com.example.mapen.presensi.TabelPresensi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PresensiAdapter extends RecyclerView.Adapter<PresensiAdapter.AdapterPresensi_vh> {
    private List<TabelPresensiResponse> tabelPresensiResponseList;
    private Context context;
    String tanggal, foto, date, latitude, longitude, riwayat, status, kerja, getUrl, fotoFile, presensi_cek;
    int cek_presensi;


    public void setData(List<TabelPresensiResponse> tabelPresensiResponseList) {
        this.tabelPresensiResponseList = tabelPresensiResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PresensiAdapter.AdapterPresensi_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new PresensiAdapter.AdapterPresensi_vh(LayoutInflater.from(context).inflate(R.layout.row_tabelpresensi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PresensiAdapter.AdapterPresensi_vh holder, int position) {

        TabelPresensiResponse tabelPresensiResponse = tabelPresensiResponseList.get(position);
        Log.d("adapterPresensi", String.valueOf(tabelPresensiResponse));

        //Post User ID from TabelPresensi.java
        tanggal = tabelPresensiResponse.getTanggal();
        date = tabelPresensiResponse.getDate();
        riwayat = tabelPresensiResponse.getRiwayat();
        status = tabelPresensiResponse.getStatus();
        kerja = tabelPresensiResponse.getKerja();
        foto = tabelPresensiResponse.getFoto();
        latitude = tabelPresensiResponse.getLat();
        longitude = tabelPresensiResponse.getLng();
        cek_presensi = tabelPresensiResponse.getCek_presensi();

        holder.tanggalVH.setText(tanggal);
        holder.dateVH.setText(date);
        holder.riwayatVH.setText(riwayat);
        holder.statusVH.setText(status);
        holder.kerjaVH.setText(kerja);
        holder.latitudeVH.setText(latitude);
        holder.longitudeVH.setText(longitude);
        if (cek_presensi == 1) {
            presensi_cek = "Tepat Waktu";
            holder.cek_presensiVH.setText(presensi_cek);
        } else {
            presensi_cek = "Terlambat";
            holder.cek_presensiVH.setText(presensi_cek);
        }

        getUrl = ApiClient.foto_presensiUrl;
        fotoFile = getUrl + foto;

        Glide.with(context)
                .load(fotoFile)
                .apply(new RequestOptions().override(300, 300))
                .centerCrop()
                .into(holder.fotoVH);
    }

    @Override
    public int getItemCount() {
        return tabelPresensiResponseList.size();
    }

    public class AdapterPresensi_vh extends RecyclerView.ViewHolder {
        TextView tanggalVH;
        TextView dateVH;
        TextView riwayatVH;
        TextView statusVH;
        TextView kerjaVH;
        TextView latitudeVH;
        TextView longitudeVH;
        TextView cek_presensiVH;
        ImageView fotoVH;

        public AdapterPresensi_vh(@NonNull View itemView) {
            super(itemView);
            tanggalVH = itemView.findViewById(R.id.txtTanggalPresensi);
            dateVH = itemView.findViewById(R.id.txtDatePresensi);
            riwayatVH = itemView.findViewById(R.id.txtRiwayatPresensi);
            statusVH = itemView.findViewById(R.id.txtStatusPresensi);
            kerjaVH = itemView.findViewById(R.id.txtMetodeKerjaPresensi);
            latitudeVH = itemView.findViewById(R.id.txtLatitudePresensi);
            longitudeVH = itemView.findViewById(R.id.txtLongitudePresensi);
            cek_presensiVH = itemView.findViewById(R.id.txtCekPresensi);
            fotoVH = itemView.findViewById(R.id.ivFotoPresensi);
        }
    }
}
