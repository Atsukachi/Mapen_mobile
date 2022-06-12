package com.example.mapen.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mapen.R;
import com.example.mapen.profil.ChangePassword;
import com.example.mapen.profil.EditProfil;

import static android.content.Context.MODE_PRIVATE;

public class ProfilFragment extends Fragment implements View.OnClickListener {
    LinearLayout editprofil_btn, changepass_btn;
    FrameLayout fl_profil;
    SharedPreferences sp;
    ScrollView scrollView;
    TextView tv_nama, tv_email, tv_alamat, tv_telepon, tv_keahlian;
    String alamat, telephone, keahlian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view_profil = inflater.inflate(R.layout.fragment_profil, container, false);

        fl_profil = view_profil.findViewById(R.id.fl_profil);

        tv_nama = view_profil.findViewById(R.id.txtNamaProfil);
        tv_email = view_profil.findViewById(R.id.txtEmailProfil);
        tv_alamat = view_profil.findViewById(R.id.txtAlamatProfil);
        tv_telepon = view_profil.findViewById(R.id.txtTeleponProfil);
        tv_keahlian = view_profil.findViewById(R.id.txtKeahlianProfil);

        scrollView = view_profil.findViewById(R.id.sv_profil);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        // Edit Profil
        editprofil_btn = view_profil.findViewById(R.id.btn_editprofil);
        editprofil_btn.setOnClickListener(this);

        // Change Password
        changepass_btn = view_profil.findViewById(R.id.btn_changepass);
        changepass_btn.setOnClickListener(this);

        getShared();

        return view_profil;
    }

    private void getShared() {
        sp = getActivity().getSharedPreferences("user_detail", MODE_PRIVATE);
        alamat = sp.getString("alamat", "");
        telephone = sp.getString("telephone", "");
        keahlian = sp.getString("keahlian", "");

        if(alamat.equals("")) {
            Toast.makeText(getActivity(), "Lengkapi Data!", Toast.LENGTH_SHORT).show();
            tv_nama.setText(sp.getString("name", ""));
            tv_email.setText(sp.getString("email", ""));
            tv_alamat.setText(R.string.data_tidak_ditemukan);
            tv_alamat.setTextColor(Color.parseColor("#FF0000"));

            tv_telepon.setText((sp.getString("telephone", "")).substring(2));
            tv_keahlian.setText(sp.getString("keahlian", ""));
            return;
        }
        if(telephone.equals("")) {
            Toast.makeText(getActivity(), "Lengkapi Data!", Toast.LENGTH_SHORT).show();
            tv_nama.setText(sp.getString("name", ""));
            tv_email.setText(sp.getString("email", ""));
            tv_telepon.setText(R.string.xxxxxxxxxxx);
            tv_telepon.setTextColor(Color.parseColor("#FF0000"));

            tv_alamat.setText(sp.getString("alamat", ""));
            tv_keahlian.setText(sp.getString("keahlian", ""));
            return;
        }
        if(keahlian.equals("")) {
            Toast.makeText(getActivity(), "Lengkapi Data!", Toast.LENGTH_SHORT).show();
            tv_nama.setText(sp.getString("name", ""));
            tv_email.setText(sp.getString("email", ""));
            tv_keahlian.setText(R.string.data_tidak_ditemukan);
            tv_keahlian.setTextColor(Color.parseColor("#FF0000"));

            tv_alamat.setText(sp.getString("alamat", ""));
            tv_telepon.setText((sp.getString("telephone", "")).substring(2));
            return;
        }
        setTextsp();
    }

    private void setTextsp() {
        tv_nama.setText(sp.getString("name", ""));
        tv_email.setText(sp.getString("email", ""));
        tv_alamat.setText(sp.getString("alamat", ""));
        tv_telepon.setText((sp.getString("telephone", "")).substring(2));
        tv_keahlian.setText(sp.getString("keahlian", ""));
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