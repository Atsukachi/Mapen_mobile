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
import com.example.mapen.profil.ChangePassword;
import com.example.mapen.profil.EditProfil;
import com.example.mapen.skp.PengajuanSKP;
import com.example.mapen.skp.TabelSKP;

public class SKPFragment extends Fragment implements View.OnClickListener{
    LinearLayout pengajuanskp_btn, tabelskp_btn;
    FrameLayout fl_skp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_skp = inflater.inflate(R.layout.fragment_skp, container, false);

        fl_skp = view_skp.findViewById(R.id.fl_skp);

        // Edit Profil
        pengajuanskp_btn = view_skp.findViewById(R.id.btn_pengajuanskp);
        pengajuanskp_btn.setOnClickListener(this);

        // Change Password
        tabelskp_btn = view_skp.findViewById(R.id.btn_tabelskp);
        tabelskp_btn.setOnClickListener(this);

        return view_skp;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_pengajuanskp:
                Intent pengajuan = new Intent(getActivity(), PengajuanSKP.class);
                startActivity(pengajuan);
                break;
            case R.id.btn_tabelskp:
                Intent t_skp = new Intent(getActivity(), TabelSKP.class);
                startActivity(t_skp);
                break;
        }
    }
}