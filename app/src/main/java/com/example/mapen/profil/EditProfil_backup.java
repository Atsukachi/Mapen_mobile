package com.example.mapen.profil;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mapen.MainActivity;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.data.EditProfilResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfil_backup extends AppCompatActivity implements View.OnClickListener {
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    ImageView btn_back;
    FloatingActionButton editfoto_fab;
    TextView upload_keahlian, upload_foto, ep_nama, ep_keahlian;
    EditText ep_email, ep_alamat, ep_telephone;
    LinearLayout btn_save_ep;
    SharedPreferences sp;
    String dialogTitle, dialogMessage, imageUser, getUrl, idUser, imagePath, alamat, keahlian, telephone, edit_email, edit_alamat, edit_telephone, edit_keahlian;
    CircleImageView userImage;
    ScrollView scrollView;
    MultipartBody.Part partImage;
    RequestBody requestFile, requestUser_id, requestEmail, requestAlamat, requestTelephone;
    File imageFile;
    Uri imageUri;
    Cursor cursor;
    int nameIndex;
    private static final int REQUEST_PICK_PHOTO = ApiClient.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = ApiClient.REQUEST_WRITE_PERMISSION;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        if(requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            updateEditProfil();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_backup);

        initComponents();

        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        sp = getSharedPreferences("user_detail",MODE_PRIVATE);

        alamat = sp.getString("alamat", "");
        telephone = sp.getString("telephone", "");
        keahlian = sp.getString("keahlian", "");

        checkCondition();

        getUrl = ApiClient.imageUrl;
        imageUser = getUrl + sp.getString("image", "");

        Glide.with(this)
                .load(imageUser)
                .into(userImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_PICK_PHOTO){
                if(data != null){
                    // Ambil Image Dari Galeri dan Foto
                    imageUri = data.getData();
                    String[] filePathColumn = {
                            MediaStore.Images.Media.DATA
                    };

                    cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    nameIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagePath = cursor.getString(nameIndex);
                    userImage.setImageURI(data.getData());
                    cursor.close();

//                    Log.d("ABC", String.valueOf(imageUri));
//                    Log.d("ABC", imagePath);
                }
            }
        }
    }

    private void checkCondition() {
        if(alamat.equals("")) {
            Toast.makeText(EditProfil_backup.this, "Alamat Harus Terisikan", Toast.LENGTH_SHORT).show();
            ep_nama.setText(sp.getString("name", ""));
            ep_email.setText(sp.getString("email", ""));
            return;
        }
        if(telephone.equals("")) {
            Toast.makeText(EditProfil_backup.this, "Telepon Harus Terisikan", Toast.LENGTH_SHORT).show();
            ep_nama.setText(sp.getString("name", ""));
            ep_email.setText(sp.getString("email", ""));
            return;
        }
//        if(keahlian.equals("")) {
//            Toast.makeText(EditProfil.this, "Keahlian Harus Terisikan", Toast.LENGTH_SHORT).show();
//            ep_nama.setText(sp.getString("name", ""));
//            ep_email.setText(sp.getString("email", ""));
//            return;
//        }
        ep_nama.setText(sp.getString("name", ""));
        ep_email.setText(sp.getString("email", ""));
        ep_alamat.setText(sp.getString("alamat", ""));
        ep_telephone.setText((sp.getString("telephone", "")).substring(2));
        ep_keahlian.setText(sp.getString("keahlian", ""));
    }

    private void initComponents() {
        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        editfoto_fab = findViewById(R.id.fab_editfoto);
        editfoto_fab.setOnClickListener(this);

        btn_save_ep = findViewById(R.id.btn_save_editprofil);
        btn_save_ep.setOnClickListener(this);

        ep_nama = findViewById(R.id.txtNamaEditProfil);
        ep_email = findViewById(R.id.txtEmailEditProfil);
        ep_alamat = findViewById(R.id.txtAlamatEditProfil);
        ep_telephone = findViewById(R.id.txtTeleponEditProfil);
        ep_keahlian = findViewById(R.id.txtKeahlianEditProfil);

        userImage = findViewById(R.id.user_image_editprofil);
        scrollView = findViewById(R.id.sv_editprofil);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.fab_editfoto:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                break;
//            case R.id.btn_save_editprofil:
//                requestPermission();
        }
    }

//    private void updateEditProfil(){
//        if(imagePath == null){
//            showAlertDialog();
//        } else {
//            sp = getSharedPreferences("user_detail", MODE_PRIVATE);
//            idUser = sp.getString("user_id", "");
//
//            imageFile = new File(imagePath);
//            requestFile = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
//            partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);
//
//            Log.d("RESULT", imageFile.getPath());
//            Log.d("RESULT", imageFile.getName());
//            Log.d("RESULT", String.valueOf(requestFile));
//            Log.d("RESULT", String.valueOf(partImage));
//
//            requestUser_id = RequestBody.create(MediaType.parse("text/plain"), idUser);
//            requestEmail = RequestBody.create(MediaType.parse("text/plain"), ep_email.getText().toString());
//            requestAlamat = RequestBody.create(MediaType.parse("text/plain"), ep_alamat.getText().toString());
//            requestTelephone = RequestBody.create(MediaType.parse("text/plain"), ep_telephone.getText().toString());
//
//            Call<EditProfilResponse> call = ApiClient
//                    .getEditKeahlianProfilService()
//                    .editProfil(partImage, requestUser_id, requestEmail, requestAlamat, requestTelephone);
//
//            call.enqueue(new Callback<EditProfilResponse>() {
//                @Override
//                public void onResponse(Call<EditProfilResponse> call, Response<EditProfilResponse> response) {
//                    if (response.isSuccessful()) {
//                        EditProfilResponse epr = response.body();
//                        Log.d("RESPONSE", String.valueOf(epr));
//                        String spEmail = epr.getEmail();
//                        String spAlamat = epr.getAlamat();
//                        String spTelephone = epr.getTelephone();
//                        String spImage = epr.getImage();
//                        SharedPreferences.Editor spEditor = sp.edit();
//                        spEditor.putString("email", spEmail);
//                        spEditor.putString("alamat", spAlamat);
//                        spEditor.putString("telephone", spTelephone);
//                        spEditor.putString("image", spImage);
//                        spEditor.commit();
//                        spEditor.apply();
//
//                        Intent intent = new Intent(EditProfil_backup.this, MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        finish();
//                        Toast.makeText(EditProfil_backup.this, "Successfully Uploaded : " + response.message(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<EditProfilResponse> call, Throwable t) {
//                    Log.d("ERROR", "Failed Uploading image : " + t.getMessage());
//                    Toast.makeText(getApplicationContext(), "Error, image.", Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//
//    private void showAlertDialog() {
//        dialogTitle = "Edit Profil";
//        dialogMessage = "Apakah Anda ingin memperbarui data tanpa mengubah foto profil?";
//
//        alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setTitle(dialogTitle);
//        alertDialogBuilder.setMessage(dialogMessage);
//        alertDialogBuilder
//                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        updateTanpaFotoProfil();
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int i) {
//                        dialog.cancel();
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

//    private void updateTanpaFotoProfil() {
//        sp = getSharedPreferences("user_detail", MODE_PRIVATE);
//        idUser = sp.getString("user_id", "");
//
//        edit_email = ep_email.getText().toString();
//        edit_alamat = ep_alamat.getText().toString();
//        edit_telephone = ep_telephone.getText().toString();
//
//        Call<EditProfilResponse> call = ApiClient
//                .getEditKeahlianProfilService()
//                .editProfilUser(idUser, edit_email, edit_alamat, edit_telephone);
//
//        call.enqueue(new Callback<EditProfilResponse>() {
//            @Override
//            public void onResponse(Call<EditProfilResponse> call, Response<EditProfilResponse> response) {
//                if (response.isSuccessful()) {
//                    EditProfilResponse obj = response.body();
//                    String sp_email = obj.getEmail();
//                    String sp_alamat = obj.getAlamat();
//                    String sp_telephone = obj.getTelephone();
////                    String sp_keahlian = obj.getKeahlian();
//
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("email", sp_email);
//                    editor.putString("alamat", sp_alamat);
//                    editor.putString("telephone", sp_telephone);
//                    editor.commit();
//                    editor.apply();
//
//                    Intent intent = new Intent(EditProfil_backup.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    finish();
//                    startActivity(intent);
//
//                    Toast.makeText(getApplicationContext(), "Successfully Uploaded : " + response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<EditProfilResponse> call, Throwable t) {
//                Log.d("ERROR", "Failed Uploading : " + t.getMessage());
//                Toast.makeText(getApplicationContext(), "Error, image.", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

//    private void requestPermission(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
//        } else {
//            updateEditProfil();
//        }
//    }
}