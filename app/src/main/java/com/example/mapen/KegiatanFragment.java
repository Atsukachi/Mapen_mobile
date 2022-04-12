package com.example.mapen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class KegiatanFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_kegiatan = inflater.inflate(R.layout.fragment_kegiatan, container, false);

        LinearLayout logkeg_btn, tabelkeg_btn;

        FrameLayout fl_kegiatan = view_kegiatan.findViewById(R.id.fl_kegiatan);

        // Log Kegiatan
        logkeg_btn = view_kegiatan.findViewById(R.id.btn_logkegiatan);
        logkeg_btn.setOnClickListener(view -> {
            Fragment lk = new LogKegiatanFragment();
            FragmentManager lk_fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction lk_ft = lk_fm.beginTransaction();
            lk_ft.replace(R.id.layout_kegiatan, lk);
            lk_ft.addToBackStack(null);
            lk_ft.commit();

            fl_kegiatan.setVisibility(View.GONE);
        });

        // Tabel Kegiatan
        tabelkeg_btn = view_kegiatan.findViewById(R.id.btn_tabelkeg);
        tabelkeg_btn.setOnClickListener(view -> {
            Fragment tk = new TabelKegiatanFragment();
            FragmentManager tk_fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction tk_ft = tk_fm.beginTransaction();
            tk_ft.replace(R.id.layout_kegiatan, tk);
            tk_ft.addToBackStack(null);
            tk_ft.commit();

            fl_kegiatan.setVisibility(View.GONE);
        });

        return view_kegiatan;
    }
}