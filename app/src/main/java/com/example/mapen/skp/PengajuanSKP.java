package com.example.mapen.skp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapen.LoginActivity;
import com.example.mapen.MainActivity;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.data.ChangepassResponse;
import com.example.mapen.data.GetBulanResponse;
import com.example.mapen.data.LoginResponse;
import com.example.mapen.data.PengajuanskpResponse;
import com.example.mapen.profil.ChangePassword;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuanSKP extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_back;
    ScrollView scrollView;
    Spinner spinnerBulan;
    SharedPreferences sp;
    TextView skp_user_nama;
    EditText skp_nama, skp_tahun;
    String namaSKP, bulanSKP, tahunSKP, user_idSKP, namaSKP_response;
    LinearLayout pengajuan_skp_savebtn;
    String[] listBulan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_skp);

        initComponents();

        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        getDataBulan();
    }

    private void initComponents() {
        skp_tahun = findViewById(R.id.txtTahunPengajuanSKP);
        skp_nama = findViewById(R.id.txtNamaPengajuanSKP);

        spinnerBulan = findViewById(R.id.spinnerBulanPengajuanSKP);

        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        pengajuan_skp_savebtn = findViewById(R.id.btn_save_pengajuanskp);
        pengajuan_skp_savebtn.setOnClickListener(this);

        scrollView = findViewById(R.id.sv_pengajuan_skp);
    }

    private void getDataBulan() {
        sp = getSharedPreferences("user_detail",MODE_PRIVATE);

        if(sp.contains("user_id")) {
            Call<List<GetBulanResponse>> bulanCall = ApiClient
                    .getBulanService()
                    .getAllMonth();

            bulanCall.enqueue(new Callback<List<GetBulanResponse>>() {
                @Override
                public void onResponse(Call<List<GetBulanResponse>> call, Response<List<GetBulanResponse>> response) {
                    if(response.isSuccessful()){
                        List<GetBulanResponse> obj = response.body();

                        if (obj != null && obj.size() > 0) {
                            listBulan = new String[obj.size()];

                            for (int i = 0; i < obj.size(); i++) {
                                listBulan[i] = String.valueOf(obj.get(i).getNama_bulan());

                                spinnerBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        bulanSKP = adapterView.getItemAtPosition(i).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        Toast.makeText(PengajuanSKP.this, "Month Field Has Not Been Selected", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(PengajuanSKP.this, R.layout.spinner_item, listBulan);
                                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                                spinnerBulan.setAdapter(spinnerArrayAdapter);
                            }
                        } else {
                            Toast.makeText(PengajuanSKP.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PengajuanSKP.this, "List Data Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GetBulanResponse>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_save_pengajuanskp:
                pengajuanskp();
                break;
        }
    }

    private void pengajuanskp() {
        user_idSKP = sp.getString("user_id", "");
        tahunSKP = skp_tahun.getText().toString();
        namaSKP = skp_nama.getText().toString();

        Call<PengajuanskpResponse> skpCall = ApiClient
                .getPengajuanskpService()
                .pengajuan_skp(user_idSKP, bulanSKP, tahunSKP, namaSKP);

        skpCall.enqueue(new Callback<PengajuanskpResponse>() {
            @Override
            public void onResponse(Call<PengajuanskpResponse> skpCall, Response<PengajuanskpResponse> skpResponse) {
                if (skpResponse.isSuccessful()) {
                    PengajuanskpResponse obj = skpResponse.body();
                    namaSKP_response = obj.getNama_skp();
                    Toast.makeText(PengajuanSKP.this, "Pengajuan SKP " + namaSKP_response + " berhasil!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(PengajuanSKP.this, "Pengajuan SKP Error", Toast.LENGTH_SHORT).show();
                }
                Log.d("MSG", String.valueOf(skpResponse));
            }

            @Override
            public void onFailure(Call<PengajuanskpResponse> call, Throwable t) {
                Toast.makeText(PengajuanSKP.this, "Pengajuan SKP Error", Toast.LENGTH_SHORT).show();
                Log.e("FAIL",t.getLocalizedMessage());
            }
        });
    }
}