package com.example.mapen.skp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapen.MainActivity;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.data.PengajuanskpResponse;
import com.example.mapen.data.TabelskpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NilaiSKP extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_back;
    Intent intent;
    TabelskpResponse tabelskpResponse;
    SharedPreferences sp;
    LinearLayout pengajuan_nilai_skp_savebtn;
    String id_nilaiSKP, nama_nilaiSKP, nilaiSKP, cekvalidasi_nilaiSKP, editnilaiSKP, user_id;
    EditText skpNilai;
    TextView skpNama, skpValidasi;
    int validasiSKP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai_skp);

        initComponents();

        intent = getIntent();
        if(intent.getExtras() != null){
            tabelskpResponse = (TabelskpResponse) intent.getSerializableExtra("data_nilai_skp");
            Log.d("data_nilai_skp", String.valueOf(tabelskpResponse));
            if (tabelskpResponse != null){
                id_nilaiSKP = tabelskpResponse.getId_skp();
                nama_nilaiSKP = tabelskpResponse.getNama_skp();
                nilaiSKP = tabelskpResponse.getNilai();
                cekvalidasi_nilaiSKP = tabelskpResponse.getCek_validasi();
            } else {
                Toast.makeText(NilaiSKP.this, "Unable to Receive Data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(NilaiSKP.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
        }

        skpNama.setText(nama_nilaiSKP);
        skpNilai.setText(nilaiSKP);
        validasiSKP = Integer.parseInt(cekvalidasi_nilaiSKP);

        if(validasiSKP == 0){
            skpValidasi.setText(R.string.msg_belum_mengajukan);
            skpNilai.setFocusable(true);
        } else if (validasiSKP == 1){
            skpValidasi.setText(R.string.lbl_sedang_proses);
            skpNilai.setInputType(InputType.TYPE_NULL);
            skpNilai.setTextIsSelectable(false);
            skpNilai.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return true;
                }
            });
        } else if (validasiSKP == 2){
            skpValidasi.setText(R.string.lbl_sudah_selesai);
            skpNilai.setInputType(InputType.TYPE_NULL);
            skpNilai.setTextIsSelectable(false);
            skpNilai.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return true;
                }
            });
        } else {
            skpValidasi.setText("");
            Toast.makeText(NilaiSKP.this, "Error to Validate", Toast.LENGTH_SHORT).show();
            skpNilai.setInputType(InputType.TYPE_NULL);
            skpNilai.setTextIsSelectable(false);
            skpNilai.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    return true;
                }
            });
        }
    }

    private void initComponents() {
        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        pengajuan_nilai_skp_savebtn = findViewById(R.id.btn_save_nilaiskp);
        pengajuan_nilai_skp_savebtn.setOnClickListener(this);

        skpNilai = findViewById(R.id.txtNilaiSKP);
        skpNama = findViewById(R.id.txtNamaNilaiSKP);
        skpValidasi = findViewById(R.id.txtStatusValidasiNilaiSKP);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_save_nilaiskp:
                nilaiskp();
                break;
        }
    }

    private void nilaiskp() {
        sp = getSharedPreferences("user_detail",MODE_PRIVATE);
        editnilaiSKP = skpNilai.getText().toString();
        if(sp.contains("user_id")){
            user_id = sp.getString("user_id", "");

            Call<PengajuanskpResponse> callSKP_nilai = ApiClient
                    .getInstance()
                    .getApi()
                    .nilai_skp(id_nilaiSKP, editnilaiSKP);

            callSKP_nilai.enqueue(new Callback<PengajuanskpResponse>() {
                @Override
                public void onResponse(Call <PengajuanskpResponse> callSKP_nilai, Response<PengajuanskpResponse> responseSKP_nilai) {
                    if (responseSKP_nilai.isSuccessful()) {
                        Toast.makeText(NilaiSKP.this, "Pengajuan Nilai SKP Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(NilaiSKP.this, "Pengajuan Nilai SKP Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PengajuanskpResponse> callSKP_nilai, Throwable t) {
                    Toast.makeText(NilaiSKP.this, "Data Error", Toast.LENGTH_SHORT).show();
                    Log.e("FAIL",t.getLocalizedMessage());
                }
            });
        }
    }
}