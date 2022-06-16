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
import com.example.mapen.presensi.Presensi;
import com.example.mapen.presensi.WaktuPresensi;

public class PresensiFragment extends Fragment implements View.OnClickListener {
    LinearLayout presensi_btn, tabelpresensi_btn;
    FrameLayout fl_presensi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_presensi = inflater.inflate(R.layout.fragment_presensi, container, false);
        fl_presensi = view_presensi.findViewById(R.id.fl_presensi);

        // Presensi Pegawai
        presensi_btn = view_presensi.findViewById(R.id.btn_presensi);
        presensi_btn.setOnClickListener(this);

        // Tabel Presensi
        tabelpresensi_btn = view_presensi.findViewById(R.id.btn_tabelpresensi);
        tabelpresensi_btn.setOnClickListener(this);

        return view_presensi;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_presensi:
                Intent presensi = new Intent(getActivity(), WaktuPresensi.class);
                startActivity(presensi);
                break;
            case R.id.btn_tabelpresensi:
                Intent tabelpresensi = new Intent(getActivity(), TabelLogKegiatan.class);
                startActivity(tabelpresensi);
                break;
        }
    }
}