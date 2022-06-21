package com.example.mapen.presensi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mapen.DividerItemDecorator;
import com.example.mapen.R;
import com.example.mapen.adapter.PresensiAdapter;
import com.example.mapen.adapter.SKPAdapter;
import com.example.mapen.api.ApiClient;
import com.example.mapen.api.MapenService;
import com.example.mapen.data.TabelPresensiResponse;
import com.example.mapen.data.TabelskpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabelPresensi extends AppCompatActivity {
    ImageView btn_back;
    RecyclerView tabelpresensi_rv;
    PresensiAdapter presensiAdapter;
    SharedPreferences sp;
    String user_id;
    MapenService serviceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel_presensi);

        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tabelpresensi_rv = findViewById(R.id.rv_tabelpresensi);
        tabelpresensi_rv.setLayoutManager(new LinearLayoutManager(this));
//        tabelskp_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecyclerView.ItemDecoration dividerItemDecorator = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        tabelpresensi_rv.addItemDecoration(dividerItemDecorator);
        presensiAdapter = new PresensiAdapter();
        getPresensi();
    }

    private void getPresensi() {
        sp = getSharedPreferences("user_detail", MODE_PRIVATE);

        if(sp.contains("email")){
            user_id = sp.getString("user_id", "");

            serviceAPI = ApiClient.getTabelPresensiService();
            serviceAPI.getPresensi(user_id).enqueue(new Callback<List<TabelPresensiResponse>>() {
                @Override
                public void onResponse(Call<List<TabelPresensiResponse>> call, Response<List<TabelPresensiResponse>> response) {
                    if(response.isSuccessful()){
                        List<TabelPresensiResponse> tabelPresensiResponses = response.body();
                        Log.d("tabelpresensi", String.valueOf(tabelPresensiResponses));
                        presensiAdapter.setData(tabelPresensiResponses);
                        tabelpresensi_rv.setAdapter(presensiAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<TabelPresensiResponse>> call, Throwable t) {
                    Toast.makeText(TabelPresensi.this, "Data Presensi Error", Toast.LENGTH_SHORT).show();
                    Log.e("FAIL",t.getLocalizedMessage());
                }
            });
        }
    }
}