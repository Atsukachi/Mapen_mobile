package com.example.mapen.skp;

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
import com.example.mapen.adapter.SKPAdapter;
import com.example.mapen.api.ApiClient;
import com.example.mapen.api.MapenService;
import com.example.mapen.data.TabelskpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  TabelSKP extends AppCompatActivity implements SKPAdapter.ClickedItem {
    ImageView btn_back;
    RecyclerView tabelskp_rv;
    SKPAdapter skpAdapter;
    SharedPreferences sp;
    String user_id;
    MapenService serviceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel_skp);

        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tabelskp_rv = findViewById(R.id.rv_tabelskp);
        tabelskp_rv.setLayoutManager(new LinearLayoutManager(this));
//        tabelskp_rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        RecyclerView.ItemDecoration dividerItemDecorator = new DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider));
        tabelskp_rv.addItemDecoration(dividerItemDecorator);
        skpAdapter = new SKPAdapter(this);
        getSKP();
    }

    private void getSKP() {
        sp = getSharedPreferences("user_detail", MODE_PRIVATE);

        if(sp.contains("email")){
            user_id = sp.getString("user_id", "");

            serviceAPI = ApiClient.getTabelskpService();
            serviceAPI.userid_skp(user_id).enqueue(new Callback<List<TabelskpResponse>>() {
                @Override
                public void onResponse(Call<List<TabelskpResponse>> call, Response<List<TabelskpResponse>> response) {
                    if(response.isSuccessful()){
                        List<TabelskpResponse> tabelskpResponses = response.body();
                        skpAdapter.setData(tabelskpResponses);
                        tabelskp_rv.setAdapter(skpAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<TabelskpResponse>> call, Throwable t) {
                    Toast.makeText(TabelSKP.this, "Data SKP Error", Toast.LENGTH_SHORT).show();
                    Log.e("FAIL",t.getLocalizedMessage());
                }
            });
        }
    }

    @Override
    public void ClickedEdit(TabelskpResponse tabelskpResponse) {
        startActivity(new Intent(this, EditSKP.class).putExtra("data_edit_skp",tabelskpResponse));
    }

    @Override
    public void ClickedNilai(TabelskpResponse tabelskpResponse) {
        startActivity(new Intent(this, NilaiSKP.class).putExtra("data_nilai_skp",tabelskpResponse));
    }
}