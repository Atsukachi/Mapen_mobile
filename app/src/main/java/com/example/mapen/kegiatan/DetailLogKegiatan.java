package com.example.mapen.kegiatan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.data.TabelLogKegiatanResponse;

public class DetailLogKegiatan extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_back, iv_file_categories_logkeg;
    LinearLayout edit_detail_logkeg_btn;
    SharedPreferences sp;
    ScrollView scrollView;
    Intent intent;
    TabelLogKegiatanResponse tabelLogKegiatanResponse;
    TextView logkeg_kodekegiatan, logkeg_skp, logkeg_unitkerja, logkeg_uraian, logkeg_file;
    String kodekeg_dlk, skp_dlk, unitkerja_dlk, uraian_dlk, file_dlk, getUrl, imageFile;
    int filekategori_dlk;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_log_kegiatan);

        initComponents();

        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        intent = getIntent();
        if(intent.getExtras() != null){
            tabelLogKegiatanResponse = (TabelLogKegiatanResponse) intent.getSerializableExtra("data_detail_logkeg");

            sp = getSharedPreferences("user_detail",MODE_PRIVATE);

            kodekeg_dlk = tabelLogKegiatanResponse.getKegiatan_id();
            skp_dlk = tabelLogKegiatanResponse.getNama_skp();
            unitkerja_dlk = tabelLogKegiatanResponse.getNama_unit_kerja();
            uraian_dlk = tabelLogKegiatanResponse.getUraian();
            file_dlk = tabelLogKegiatanResponse.getFile();
            filekategori_dlk = tabelLogKegiatanResponse.getFile_categories();

            getUrl = ApiClient.kegiatan_imageUrl;
            imageFile = getUrl + file_dlk;

            if(filekategori_dlk == 1) {
                Glide.with(this)
                        .load(imageFile)
                        .apply(new RequestOptions().override(300, 300))
                        .centerCrop()
                        .into(iv_file_categories_logkeg);
            } else if (filekategori_dlk == 2) {
                iv_file_categories_logkeg.setImageResource(R.drawable.video);
            } else if (filekategori_dlk == 3) {
                iv_file_categories_logkeg.setImageResource(R.drawable.word);
            } else if (filekategori_dlk == 4) {
                iv_file_categories_logkeg.setImageResource(R.drawable.excel);
            } else if (filekategori_dlk == 5) {
                iv_file_categories_logkeg.setImageResource(R.drawable.powerpoint);
            } else if (filekategori_dlk == 6) {
                iv_file_categories_logkeg.setImageResource(R.drawable.pdf);
            } else if (filekategori_dlk == 7) {
                iv_file_categories_logkeg.setImageResource(R.drawable.library);
            } else {
                iv_file_categories_logkeg.setImageResource(R.drawable.notfound);
            }
        } else {
            Toast.makeText(DetailLogKegiatan.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
        }

        logkeg_kodekegiatan.setText(kodekeg_dlk);
        logkeg_skp.setText(skp_dlk);
        logkeg_unitkerja.setText(unitkerja_dlk);
        logkeg_uraian.setText(uraian_dlk);
        logkeg_file.setText(file_dlk);
    }

    private void initComponents() {
        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        edit_detail_logkeg_btn = findViewById(R.id.btn_edit_detaillogkeg);
        edit_detail_logkeg_btn.setOnClickListener(this);

        iv_file_categories_logkeg = findViewById(R.id.ivFileCategories_DetailLogKegiatan);
        scrollView = findViewById(R.id.sv_logkegiatan);

        logkeg_kodekegiatan = findViewById(R.id.txtKodeKegiatan_DetailLogKeg);
        logkeg_skp = findViewById(R.id.txtSKP_DetailLogKeg);
        logkeg_unitkerja = findViewById(R.id.txtUnitKerja_DetailLogKeg);
        logkeg_uraian = findViewById(R.id.txtUraianPekerjaan_DetailLogKeg);
        logkeg_file = findViewById(R.id.txtFile_DetailLogKegiatan);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_edit_detaillogkeg:
                editlogkegiatan();
                break;
        }
    }

    private void editlogkegiatan() {
        startActivity(new Intent(this, EditLogKegiatan.class).putExtra("data_edit_logkeg", tabelLogKegiatanResponse));
    }
}