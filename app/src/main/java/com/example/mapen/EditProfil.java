package com.example.mapen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mapen.api.ApiClient;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfil extends AppCompatActivity implements View.OnClickListener{
    ImageView btn_back;
    TextView upload_keahlian, download_file, ep_nama, ep_keahlian;
    EditText ep_email, ep_alamat, ep_telephone;
    LinearLayout btn_save_ep;
    SharedPreferences sp;
    String imageUser, getUrl;
    CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

        initComponents();

        sp = getSharedPreferences("user_detail",MODE_PRIVATE);
        ep_nama.setText(sp.getString("name", ""));
        ep_email.setText(sp.getString("email", ""));
        ep_alamat.setText(sp.getString("alamat", ""));
        ep_telephone.setText((sp.getString("telephone", "")).substring(2));
        ep_keahlian.setText(sp.getString("keahlian", ""));

        getUrl = ApiClient.imageUrl;
        imageUser = getUrl + sp.getString("image", "");

        Glide.with(this)
                .load(imageUser)
                .into(userImage);
    }

    private void initComponents() {
        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        upload_keahlian = findViewById(R.id.txtUploadKeahlian);
        upload_keahlian.setOnClickListener(this);

        download_file = findViewById(R.id.txtDownloadFile);
        download_file.setOnClickListener(this);

        btn_save_ep = findViewById(R.id.btn_save_editprofil);
        btn_save_ep.setOnClickListener(this);

        ep_nama = findViewById(R.id.txtNamaEditProfil);
        ep_email = findViewById(R.id.txtEmailEditProfil);
        ep_alamat = findViewById(R.id.txtAlamatEditProfil);
        ep_telephone = findViewById(R.id.txtTeleponEditProfil);
        ep_keahlian = findViewById(R.id.txtKeahlianEditProfil);
        userImage = findViewById(R.id.user_image);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.txtUploadKeahlian:
                // something happen
                break;
            case R.id.txtDownloadFile:
                // something happen
                break;
            case R.id.btn_save_editprofil:
                // something happen
                break;
        }
    }
}