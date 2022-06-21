package com.example.mapen.skp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mapen.MainActivity;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.data.GetBulanResponse;
import com.example.mapen.data.PengajuanskpResponse;
import com.example.mapen.data.TabelskpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSKP extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_back;
    ScrollView scrollView;
    TextView tv_header_pengajuanskp, skp_nama;
    SharedPreferences sp;
    Spinner spinnerBulan;
    EditText skp_tahun, skp_nama_rincian;
    LinearLayout edit_pengajuan_skp_savebtn;
    TabelskpResponse tabelskpResponse;
    Intent intent;
    String[] listBulan;
    String id_editSKP, nama_editSKP, bulan_editSKP, tahun_editSKP, user_id_editSKP, user_nama_editSKP;
    String user_id, bulan, edit_nama, edit_rincian, edit_tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_skp);

        initComponents();

        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        intent = getIntent();
        if(intent.getExtras() != null){
            tabelskpResponse = (TabelskpResponse) intent.getSerializableExtra("data_edit_skp");
            Log.d("data_edit_skp", String.valueOf(tabelskpResponse));

            sp = getSharedPreferences("user_detail",MODE_PRIVATE);
            user_nama_editSKP = sp.getString("name", "");

            id_editSKP = tabelskpResponse.getId_skp();
            user_id_editSKP = tabelskpResponse.getUser();
            tahun_editSKP = tabelskpResponse.getTahun();
            nama_editSKP = tabelskpResponse.getNama_skp();
            bulan_editSKP = tabelskpResponse.getNama_bulan();

        } else {
            Toast.makeText(EditSKP.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
        }

        skp_tahun.setText(tahun_editSKP);
        skp_nama_rincian.setText(nama_editSKP);

        getDataBulan();
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
                                        bulan = adapterView.getItemAtPosition(i).toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        Toast.makeText(EditSKP.this, "Month Field Has Not Been Selected", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(EditSKP.this, R.layout.spinner_item, listBulan);
                                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                                spinnerBulan.setAdapter(spinnerArrayAdapter);
                                spinnerBulan.setSelection(((ArrayAdapter<String>)spinnerBulan.getAdapter()).getPosition(bulan_editSKP));
                            }
                        } else {
                            Toast.makeText(EditSKP.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditSKP.this, "List Data Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GetBulanResponse>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    private void initComponents() {
        tv_header_pengajuanskp = findViewById(R.id.txtHeaderPengajuanSKP);
        tv_header_pengajuanskp.setText(R.string.lbl_edit_pengajuan_skp);

        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        spinnerBulan = findViewById(R.id.spinnerBulanPengajuanSKP);
        
        edit_pengajuan_skp_savebtn = findViewById(R.id.btn_save_pengajuanskp);
        edit_pengajuan_skp_savebtn.setOnClickListener(this);

        skp_tahun = findViewById(R.id.txtTahunPengajuanSKP);
        skp_nama_rincian = findViewById(R.id.txtNamaPengajuanSKP);

        scrollView = findViewById(R.id.sv_pengajuan_skp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_save_pengajuanskp:
                editpengajuanskp();
                break;
        }
    }

    private void editpengajuanskp() {
        sp = getSharedPreferences("user_detail", MODE_PRIVATE);
        edit_nama = skp_nama.getText().toString();
        edit_tahun = skp_tahun.getText().toString();
        edit_rincian = skp_nama_rincian.getText().toString();

        if(sp.contains("user_id")){
            user_id = sp.getString("user_id", "");

            Call<PengajuanskpResponse> callSKP_edit = ApiClient
                    .getEditskpService()
                    .edit_skp(id_editSKP, user_id, bulan, edit_tahun, edit_rincian);

            callSKP_edit.enqueue(new Callback<PengajuanskpResponse>() {
                @Override
                public void onResponse(Call <PengajuanskpResponse> callSKP_edit, Response<PengajuanskpResponse> responseSKP_edit) {
                    if (responseSKP_edit.isSuccessful()) {
                        Toast.makeText(EditSKP.this, "Edit Pengajuan SKP Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(EditSKP.this, "Edit Pengajuan SKP Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PengajuanskpResponse> callSKP_edit, Throwable t) {
                    Toast.makeText(EditSKP.this, "Data Error", Toast.LENGTH_SHORT).show();
                    Log.e("FAIL",t.getLocalizedMessage());
                }
            });
        }
    }
}