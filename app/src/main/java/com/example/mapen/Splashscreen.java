package com.example.mapen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {
    private int waktu_loading = 4000; //4000 = 4 detik

    Animation logo_anim;
    ImageView ss_logo;
    String name;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        logo_anim = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        ss_logo = findViewById(R.id.ss_logo);
        ss_logo.setAnimation(logo_anim);

        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sp = getSharedPreferences("user_detail", MODE_PRIVATE);
                    name = sp.getString("name", null);
                    if(sp.contains("name") && (sp.contains("email"))){
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(main);
                    } else {
                        Intent home = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(home);
                    }
                    finish();
                }
            }, waktu_loading);
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}