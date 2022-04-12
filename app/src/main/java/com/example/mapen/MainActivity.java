package com.example.mapen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.mapen.api.ApiClient;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    BottomNavigationView menu_navigation;
    TextView navbar_name;
    LinearLayout navbar_back;
    SharedPreferences sp;
    String imageUser, getUrl;
    CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu_navigation = findViewById(R.id.bottom_menu);
        menu_navigation.setOnItemSelectedListener(this);
        menu_navigation.setSelectedItemId(R.id.menu_profil);

        navbar_name = findViewById(R.id.txtNavbarNama);

        checkuser();
        userImage = findViewById(R.id.user_image);
        getUrl = ApiClient.imageUrl;
        imageUser = getUrl + sp.getString("image", "");

        Glide.with(this)
                .load(imageUser)
                .into(userImage);


        navbar_back = findViewById(R.id.btn_logout);
        navbar_back.setOnClickListener(view -> {
            movetoLogin();
        });
    }

    private void checkuser() {
        sp = getSharedPreferences("user_detail",MODE_PRIVATE);
        if(sp.contains("email")) {
            navbar_name.setText(sp.getString("name", ""));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    private void movetoLogin() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();

        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }

    SKPFragment skp_fragment = new SKPFragment();
    WaktuPresensiFragment waktupresensi_fragment = new WaktuPresensiFragment();
    KegiatanFragment kegiatan_fragment = new KegiatanFragment();
    ProfilFragment profil_fragment = new ProfilFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_skp:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, skp_fragment).commit();
                return true;

            case R.id.menu_presensi:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, waktupresensi_fragment).commit();
                return true;

            case R.id.menu_logkegiatan:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, kegiatan_fragment).commit();
                return true;

            case R.id.menu_profil:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, profil_fragment).commit();
                return true;
        }
        return true;
    }
}