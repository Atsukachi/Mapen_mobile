package com.example.mapen.presensi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.api.MapenService;
import com.example.mapen.data.EditProfilResponse;
import com.example.mapen.data.RiwayatPresensiResponse;
import com.example.mapen.kegiatan.EditLogKegiatan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaktuPresensi extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_back;
    ScrollView scrollView;
    LinearLayout jamdatang_btn, jampulang_btn;
    MapenService serviceAPI;
    SharedPreferences sp;
    String[] listRiwayatPresensi, listStatusPresensi, listPCekPresensi, listCekPPresensi;
    String idriwayatPresensi, idstatusPresensi, riwayatPresensi, statusPresensi, riwayatCek, user_id;
    int pcekPresensi, cekpPresensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waktu_presensi);

        initComponents();
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);
    }

    private void initComponents() {
        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        scrollView = findViewById(R.id.sv_waktupresensi);

        jamdatang_btn = findViewById(R.id.btn_jamdatang);
        jamdatang_btn.setOnClickListener(this);
        jampulang_btn = findViewById(R.id.btn_jampulang);
        jampulang_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_jamdatang:
                presensiJamDatang();
                break;
            case R.id.btn_jampulang:
                presensiJamPulang();
                break;
        }
    }

    private void presensiJamDatang() {
        riwayatCek = "Jam Datang";
        getRiwayatPresensi(riwayatCek);
    }

    private void presensiJamPulang() {
        riwayatCek = "Jam Pulang";
        getRiwayatPresensi(riwayatCek);
    }

    private void getRiwayatPresensi(String riwayatCek) {
        sp = getSharedPreferences("user_detail", MODE_PRIVATE);
        user_id = sp.getString("user_id", "");

        Call<RiwayatPresensiResponse> call = ApiClient
                .getRiwayatPresensiService()
                .riwayat_presensi(user_id, riwayatCek);

        call.enqueue(new Callback<RiwayatPresensiResponse>() {

            @Override
            public void onResponse(Call<RiwayatPresensiResponse> call, Response<RiwayatPresensiResponse> response) {
                if(response.isSuccessful()) {
                    RiwayatPresensiResponse riwayatobj = response.body();
                    Log.d("riwayatobj", String.valueOf(riwayatobj));

                    assert riwayatobj != null;
                    riwayatPresensi = riwayatobj.getRiwayat();
                    statusPresensi = riwayatobj.getStatus();
                    idriwayatPresensi = riwayatobj.getId_riwayat();
                    idstatusPresensi = riwayatobj.getId_status();
                    pcekPresensi = Integer.parseInt(riwayatobj.getP_cek());
                    cekpPresensi = Integer.parseInt(riwayatobj.getCek_p());

                    if (pcekPresensi == 0 && cekpPresensi == 0) {
                        Intent jampresensi = new Intent(getApplicationContext(), Presensi.class);
                        jampresensi.putExtra("data_riwayat_presensi",riwayatPresensi);
                        jampresensi.putExtra("data_status_presensi", statusPresensi);
                        jampresensi.putExtra("data_idriwayat_presensi",idriwayatPresensi);
                        jampresensi.putExtra("data_idstatus_presensi", idstatusPresensi);
                        startActivity(jampresensi);
                    } else {
                        Toast.makeText(WaktuPresensi.this, "Presensi Tidak Dapat Dilakukan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WaktuPresensi.this, "List Data Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RiwayatPresensiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}