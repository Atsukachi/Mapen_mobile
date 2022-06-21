package com.example.mapen.skp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import com.example.mapen.R;
import com.example.mapen.data.TabelskpResponse;

public class DetailSKP extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_back;
    LinearLayout edit_detail_skp_btn, nilai_detail_skp_btn;
    TextView tv_bulan_dskp, tv_tahun_dskp, tv_namaskp_dskp, tv_nilai_dskp, tv_jmlkeg_dskp, tv_validasi_dskp;
    SharedPreferences sp;
    ScrollView scrollView;
    Intent intent;
    Context context;
    TabelskpResponse tabelskpResponse;
    String namabulan_detailSKP, tahun_detailSKP, namaskp_detailSKP, nilai_detailSKP, cekvalidasi_detailSKP, jmlkegiatan_detailSKP;
    int validasi_skp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_skp);

        initComponents();

        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        intent = getIntent();
        if(intent.getExtras() != null){
            tabelskpResponse = (TabelskpResponse) intent.getSerializableExtra("data_detail_skp");

            sp = getSharedPreferences("user_detail",MODE_PRIVATE);

            namabulan_detailSKP = tabelskpResponse.getNama_bulan();
            tahun_detailSKP = tabelskpResponse.getTahun();
            namaskp_detailSKP = tabelskpResponse.getNama_skp();
            nilai_detailSKP = tabelskpResponse.getNilai();
            cekvalidasi_detailSKP = tabelskpResponse.getCek_validasi();
            validasi_skp = Integer.parseInt(cekvalidasi_detailSKP);
            jmlkegiatan_detailSKP = tabelskpResponse.getJml_kegiatan();

        } else {
            Toast.makeText(DetailSKP.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
        }

        tv_bulan_dskp.setText(namabulan_detailSKP);
        tv_tahun_dskp.setText(tahun_detailSKP);
        tv_namaskp_dskp.setText(namaskp_detailSKP);
        tv_nilai_dskp.setText(nilai_detailSKP);
        tv_jmlkeg_dskp.setText(jmlkegiatan_detailSKP);

        if(validasi_skp == 0) {
            tv_validasi_dskp.setText(R.string.msg_belum_mengajukan);
        } else if (validasi_skp == 1) {
            tv_validasi_dskp.setText(R.string.lbl_sedang_proses);
        } else if (validasi_skp == 2) {
            tv_validasi_dskp.setText(R.string.lbl_sudah_selesai);
        } else {
            tv_validasi_dskp.setText(R.string.data_tidak_ditemukan);
            tv_validasi_dskp.setTextColor(ContextCompat.getColor(context, R.color.red_A700));
        }
    }

    private void initComponents() {
        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        edit_detail_skp_btn = findViewById(R.id.btn_edit_detailskp);
        edit_detail_skp_btn.setOnClickListener(this);

        nilai_detail_skp_btn = findViewById(R.id.btn_nilai_detailskp);
        nilai_detail_skp_btn.setOnClickListener(this);

        tv_bulan_dskp = findViewById(R.id.txtBulanDetailSKP);
        tv_tahun_dskp = findViewById(R.id.txtTahunDetailSKP);
        tv_namaskp_dskp = findViewById(R.id.txtNamaDetailSKP);
        tv_nilai_dskp = findViewById(R.id.txtNilaiDetailSKP);
        tv_jmlkeg_dskp = findViewById(R.id.txtJumlahKegiatanDetailSKP);
        tv_validasi_dskp = findViewById(R.id.txtStatusValidasiDetailSKP);

        scrollView = findViewById(R.id.sv_pengajuan_skp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_edit_detailskp:
                editpengajuanskp();
                break;
            case R.id.btn_nilai_detailskp:
                nilaipengajuanskp();
                break;
        }
    }

    private void editpengajuanskp() {
        startActivity(new Intent(this, EditSKP.class).putExtra("data_edit_skp", tabelskpResponse));
    }

    private void nilaipengajuanskp() {
        startActivity(new Intent(this, NilaiSKP.class).putExtra("data_nilai_skp", tabelskpResponse));
    }
}