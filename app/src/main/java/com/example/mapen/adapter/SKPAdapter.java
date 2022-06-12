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
    String tahun, nama_skp, nilai, cek_validasi, nama_bulan, jml_kegiatan, id_skp;
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

        //Post User ID from TabelSKP.java
        id_skp = tabelskpResponse.getId_skp();
        nama_bulan = tabelskpResponse.getNama_bulan();
        tahun = tabelskpResponse.getTahun();
        nama_skp = tabelskpResponse.getNama_skp();
        nilai = tabelskpResponse.getNilai();
        cek_validasi = tabelskpResponse.getCek_validasi();
        validasi_skp = Integer.parseInt(cek_validasi);
        jml_kegiatan = tabelskpResponse.getJml_kegiatan();

        holder.id_skpVH.setText(id_skp);
        holder.nama_bulanVH.setText(nama_bulan);
        holder.tahunVH.setText(tahun);
        holder.nama_skpVH.setText(nama_skp);
        holder.nilaiVH.setText(nilai);

        if(validasi_skp == 0) {
            holder.cek_validasiVH.setText(R.string.msg_belum_mengajukan);
        } else if (validasi_skp == 1) {
            holder.cek_validasiVH.setText(R.string.lbl_sedang_proses);
        } else if (validasi_skp == 2) {
            holder.cek_validasiVH.setText(R.string.lbl_sudah_selesai);
        } else {
            holder.cek_validasiVH.setText(R.string.data_tidak_ditemukan);
            holder.cek_validasiVH.setTextColor(ContextCompat.getColor(context, R.color.red_A700));
        }

        holder.jml_kegVH.setText(jml_kegiatan);
        holder.btn_editskpVH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedEdit(tabelskpResponse);
            }
        });
        holder.btn_nilaiskpVH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedNilai(tabelskpResponse);
            }
        });
    }

    public interface ClickedItem{
        void ClickedEdit(TabelskpResponse tabelskpResponse);
        void ClickedNilai(TabelskpResponse tabelskpResponse);
    }

    @Override
    public int getItemCount() {
        return tabelskpResponseList.size();
    }

    public class AdapterSKP_vh extends RecyclerView.ViewHolder {
        TextView id_skpVH;
        TextView nama_bulanVH;
        TextView tahunVH;
        TextView nama_skpVH;
        TextView cek_validasiVH;
        TextView nilaiVH;
        TextView jml_kegVH;
        LinearLayout btn_editskpVH;
        LinearLayout btn_nilaiskpVH;

        public AdapterSKP_vh(@NonNull View itemView) {
            super(itemView);
            id_skpVH = itemView.findViewById(R.id.txtIDSKPTabelSKP);
            nama_bulanVH = itemView.findViewById(R.id.txtBulanTabelSKP);
            tahunVH = itemView.findViewById(R.id.txtTahunTabelSKP);
            nama_skpVH = itemView.findViewById(R.id.txtNamaSKPTabelSKP);
            cek_validasiVH = itemView.findViewById(R.id.txtValidasiTabelSKP);
            nilaiVH = itemView.findViewById(R.id.txtNilaiTabelSKP);
            jml_kegVH = itemView.findViewById(R.id.txtJumlahKegiatanTabelSKP);
            btn_editskpVH = itemView.findViewById(R.id.btn_edit_tabelskp);
            btn_nilaiskpVH = itemView.findViewById(R.id.btn_nilai_tabelskp);
        }
    }
}