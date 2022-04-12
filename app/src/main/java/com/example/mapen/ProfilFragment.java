package com.example.mapen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfilFragment extends Fragment implements View.OnClickListener {
    LinearLayout editprofil_btn, changepass_btn;
    FrameLayout fl_profil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_profil = inflater.inflate(R.layout.fragment_profil, container, false);

        fl_profil = view_profil.findViewById(R.id.fl_profil);

        // Edit Profil
        editprofil_btn = view_profil.findViewById(R.id.btn_editprofil);
        editprofil_btn.setOnClickListener(this);

        // Change Password
        changepass_btn = view_profil.findViewById(R.id.btn_changepass);
        changepass_btn.setOnClickListener(this);

        return view_profil;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_editprofil:
                Intent ep = new Intent(getActivity(), EditProfil.class);
                startActivity(ep);
                break;
            case R.id.btn_changepass:
                Intent cp = new Intent(getActivity(), ChangePassword.class);
                startActivity(cp);
                break;
        }
    }
}