package com.example.mapen.kegiatan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapen.DividerItemDecorator;
import com.example.mapen.R;
import com.example.mapen.adapter.LogKegiatanAdapter;
import com.example.mapen.adapter.SKPAdapter;
import com.example.mapen.api.ApiClient;
import com.example.mapen.api.MapenService;
import com.example.mapen.data.TabelLogKegiatanResponse;
import com.example.mapen.data.TabelskpResponse;
import com.example.mapen.skp.EditSKP;
import com.example.mapen.skp.TabelSKP;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabelLogKegiatan extends AppCompatActivity implements LogKegiatanAdapter.ClickedItem{
    ImageView btn_back;
    RecyclerView tabellogkeg_rv;
    LogKegiatanAdapter logKegiatanAdapter;
    SharedPreferences sp;
    String user_id;
    MapenService serviceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel_log_kegiatan);

        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tabellogkeg_rv = findViewById(R.id.rv_tabellogkeg);
        tabellogkeg_rv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration dividerItemDecorator = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        tabellogkeg_rv.addItemDecoration(dividerItemDecorator);
        logKegiatanAdapter = new LogKegiatanAdapter(this);
        getLogKeg();
    }

    private void getLogKeg() {
        sp = getSharedPreferences("user_detail", MODE_PRIVATE);

        if(sp.contains("email")){
            user_id = sp.getString("user_id", "");

            serviceAPI = ApiClient.getTabellogkegService();
            serviceAPI.userid_logkeg(user_id).enqueue(new Callback<List<TabelLogKegiatanResponse>>() {
                @Override
                public void onResponse(Call<List<TabelLogKegiatanResponse>> call, Response<List<TabelLogKegiatanResponse>> response) {
                    if(response.isSuccessful()){
                        List<TabelLogKegiatanResponse> tabelLogKegiatanResponses = response.body();
                        logKegiatanAdapter.setData(tabelLogKegiatanResponses);
                        tabellogkeg_rv.setAdapter(logKegiatanAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<TabelLogKegiatanResponse>> call, Throwable t) {
                    Toast.makeText(TabelLogKegiatan.this, "Data Log Kegiatan Error", Toast.LENGTH_SHORT).show();
                    Log.e("FAIL",t.getLocalizedMessage());
                }
            });
        }
    }

    @Override
    public void ClickedEdit(TabelLogKegiatanResponse tabelLogKegiatanResponse) {
        startActivity(new Intent(this, EditLogKegiatan.class).putExtra("data_edit_logkeg",tabelLogKegiatanResponse));
    }
}