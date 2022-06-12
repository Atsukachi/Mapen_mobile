package com.example.mapen.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.mapen.R;
import com.example.mapen.kegiatan.TabelLogKegiatan;
import com.example.mapen.kegiatan.LogKegiatan;

public class KegiatanFragment extends Fragment implements View.OnClickListener {
    LinearLayout logkegiatan_btn, tabelkegiatan_btn;
    FrameLayout fl_kegiatan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_kegiatan = inflater.inflate(R.layout.fragment_kegiatan, container, false);
        fl_kegiatan = view_kegiatan.findViewById(R.id.fl_kegiatan);

        // Log Kegiatan
        logkegiatan_btn = view_kegiatan.findViewById(R.id.btn_logkegiatan);
        logkegiatan_btn.setOnClickListener(this);

        // Tabel Kegiatan
        tabelkegiatan_btn = view_kegiatan.findViewById(R.id.btn_tabelkegiatan);
        tabelkegiatan_btn.setOnClickListener(this);

        return view_kegiatan;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logkegiatan:
                Intent logkegiatan = new Intent(getActivity(), LogKegiatan.class);
                startActivity(logkegiatan);
                break;
            case R.id.btn_tabelkegiatan:
                Intent t_kegiatan = new Intent(getActivity(), TabelLogKegiatan.class);
                startActivity(t_kegiatan);
                break;
        }
    }
}