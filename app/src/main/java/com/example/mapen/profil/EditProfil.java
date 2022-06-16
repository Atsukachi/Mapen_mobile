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
import android.provider.OpenableColumns;
import android.text.Html;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.mapen.MainActivity;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.api.FileUtils;
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

public class EditProfil extends AppCompatActivity implements View.OnClickListener {
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    ImageView btn_back;
    FloatingActionButton editfoto_fab;
    TextView upload_foto, ep_nama, ep_keahlian;
    EditText ep_email, ep_alamat, ep_telephone;
    LinearLayout btn_save_ep, btn_uploadkegiatan_ep;
    SharedPreferences sp;
    String imageNew, newImage, keahlianNew, imageUser, getUrl, idUser, dialogTitle, dialogMessage, alamat, keahlian, telephone, edit_email, edit_alamat, edit_telephone, edit_keahlian;
    CircleImageView userImage;
    ScrollView scrollView;
    MultipartBody.Part partImage, partKeahlian;
    RequestBody requestFoto, requestKeahlian, requestUser_id, requestEmail, requestAlamat, requestTelephone;
    File imageFile, keahlianFile;
    Uri imageUri, keahlianUri;
    Cursor cursor;
    int nameIndex;
    private static final int REQUEST_PICK_PHOTO = ApiClient.REQUEST_PICK_PHOTO;
    private static final int REQUEST_PICK_FILE = ApiClient.REQUEST_PICK_FILE;
    private static final int REQUEST_WRITE_PERMISSION = ApiClient.REQUEST_WRITE_PERMISSION;
    private static final int PERMISSION_REQUEST_STORAGE = ApiClient.PERMISSION_REQUEST_STORAGE;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateFotoProfil();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);

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
        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    imageUri = data.getData();

                    cursor = getContentResolver().query(imageUri, null, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    newImage = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.d("newImage", newImage);
                    cursor.close();

                    userImage.setImageURI(imageUri);
                    requestPermissionFoto();
                } else {
                    onBackPressed();
                }
            } else if (requestCode == REQUEST_PICK_FILE) {
                if (data != null) {
                    keahlianUri = data.getData();

                    cursor = getContentResolver().query(keahlianUri, null, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    keahlianNew = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    cursor.close();

                    ep_keahlian.setText(keahlianNew);
                } else {
                    onBackPressed();
                }
            }
        }
    }

    private void checkCondition() {
        Log.d("sp", sp.getString("name", ""));
        Log.d("sp", sp.getString("email", ""));
        Log.d("sp", sp.getString("alamat", ""));
        Log.d("sp", sp.getString("telephone", ""));
        Log.d("sp", sp.getString("image", ""));
        Log.d("sp", sp.getString("keahlian", ""));

        if(alamat.equals("")) {
            ep_nama.setText(sp.getString("name", ""));
            ep_email.setText(sp.getString("email", ""));
            ep_telephone.setText((sp.getString("telephone", "")).substring(2));
            ep_keahlian.setText(sp.getString("keahlian", ""));
            return;
        }
        if(telephone.equals("")) {
            ep_nama.setText(sp.getString("name", ""));
            ep_email.setText(sp.getString("email", ""));
            ep_alamat.setText(sp.getString("alamat", ""));
            ep_keahlian.setText(sp.getString("keahlian", ""));
            return;
        }
        if(keahlian.equals("")) {
            ep_nama.setText(sp.getString("name", ""));
            ep_email.setText(sp.getString("email", ""));
            ep_alamat.setText(sp.getString("alamat", ""));
            ep_telephone.setText((sp.getString("telephone", "")).substring(2));
            return;
        }
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

        btn_uploadkegiatan_ep = findViewById(R.id.btn_uploadkeahlian_ep);
        btn_uploadkegiatan_ep.setOnClickListener(this);

        ep_nama = findViewById(R.id.txtNamaEditProfil);
        ep_email = findViewById(R.id.txtEmailEditProfil);
        ep_alamat = findViewById(R.id.txtAlamatEditProfil);
        ep_telephone = findViewById(R.id.txtTeleponEditProfil);
        ep_keahlian = findViewById(R.id.txtKeahlianEditProfil);

        userImage = findViewById(R.id.user_image_editprofil);
        scrollView = findViewById(R.id.sv_editprofil);
    }

    private void chooseKeahlian() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        } else {
            openPickerFile();
        }
    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        } else {
            openGallery();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.fab_editfoto:
                choosePhoto();
                break;
            case R.id.btn_save_editprofil:
                requestPermissionKeahlian();
                break;
            case R.id.btn_uploadkeahlian_ep:
                chooseKeahlian();
                break;
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_PICK_PHOTO);
    }

    private void openPickerFile() {
        Intent fileIntent = new Intent();
        fileIntent.setAction(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("*/*");
        startActivityForResult(Intent.createChooser(fileIntent, "Select File"), REQUEST_PICK_FILE);
    }

    private void updateFotoProfil() {
        if(imageUri == null) {
            updateKeahlianProfil();
        } else {
            sp = getSharedPreferences("user_detail", MODE_PRIVATE);
            idUser = sp.getString("user_id", "");

            imageFile = FileUtils.getFile(this, imageUri);
            requestFoto = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
            partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFoto);
            imageNew = imageFile.getName();
            imageNew = imageNew.replace(" ", "_");
            Log.d("imageNew", imageNew);
            requestUser_id = RequestBody.create(MediaType.parse("text/plain"), idUser);

            Call<EditProfilResponse> call = ApiClient
                    .getEditFotoProfilService()
                    .editFotoProfil(partImage, requestUser_id);

            call.enqueue(new Callback<EditProfilResponse>() {
                @Override
                public void onResponse(Call<EditProfilResponse> call, Response<EditProfilResponse> response) {
                    if (response.isSuccessful()) {
                        EditProfilResponse epr_foto = response.body();
                        Log.d("RESPONSE_FOTO", String.valueOf(epr_foto));
                        String spImage = epr_foto.getImage();
                        SharedPreferences.Editor spEditor = sp.edit();
                        spEditor.putString("image", spImage);
                        spEditor.commit();
                        spEditor.apply();

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("fotobaru", imageNew);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Uploaded : " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditProfilResponse> call, Throwable t) {
                    Log.d("ERROR_FOTO", "Failed Uploading image : " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error, image.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void updateKeahlianProfil() {
        if(keahlianUri == null) {
            showAlertDialog();
        } else {
            sp = getSharedPreferences("user_detail", MODE_PRIVATE);
            idUser = sp.getString("user_id", "");

            keahlianFile = FileUtils.getFile(this, keahlianUri);
            requestKeahlian = RequestBody.create(MediaType.parse("multipart/form-file"), keahlianFile);
            partKeahlian = MultipartBody.Part.createFormData("keahlian", keahlianFile.getName(), requestKeahlian);

            Log.d("RESULT", keahlianFile.getPath());
            Log.d("RESULT", keahlianFile.getName());
            Log.d("RESULT", String.valueOf(requestKeahlian));
            Log.d("RESULT", String.valueOf(partKeahlian));

            requestUser_id = RequestBody.create(MediaType.parse("text/plain"), idUser);
            requestEmail = RequestBody.create(MediaType.parse("text/plain"), ep_email.getText().toString());
            requestAlamat = RequestBody.create(MediaType.parse("text/plain"), ep_alamat.getText().toString());
            requestTelephone = RequestBody.create(MediaType.parse("text/plain"), ep_telephone.getText().toString());

            Call<EditProfilResponse> call = ApiClient
                    .getEditKeahlianProfilService()
                    .editProfil(partKeahlian, requestUser_id, requestEmail, requestAlamat, requestTelephone);

            call.enqueue(new Callback<EditProfilResponse>() {
                @Override
                public void onResponse(Call<EditProfilResponse> call, Response<EditProfilResponse> response) {
                    if (response.isSuccessful()) {
                        EditProfilResponse epr_keahlian = response.body();
                        Log.d("RESPONSE_KEAHLIAN", String.valueOf(epr_keahlian));
                        String spEmail = epr_keahlian.getEmail();
                        String spAlamat = epr_keahlian.getAlamat();
                        String spTelephone = epr_keahlian.getTelephone();
                        String spKeahlian = epr_keahlian.getKeahlian();
                        SharedPreferences.Editor spEditor = sp.edit();
                        spEditor.putString("email", spEmail);
                        spEditor.putString("alamat", spAlamat);
                        spEditor.putString("telephone", spTelephone);
                        spEditor.putString("keahlian", spKeahlian);
                        spEditor.commit();
                        spEditor.apply();

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Uploaded : " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<EditProfilResponse> call, Throwable t) {
                    Log.d("ERROR_KEAHLIAN", "Failed Uploading file : " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error, file.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showAlertDialog() {
        dialogTitle = "Edit Profil";
        dialogMessage = "Apakah Anda ingin memperbarui data tanpa mengubah file keahlian?";

        alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder
                .setPositiveButton(("Yes"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        updateTanpaKeahlianProfil();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(("No"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void updateTanpaKeahlianProfil() {
        sp = getSharedPreferences("user_detail", MODE_PRIVATE);
        idUser = sp.getString("user_id", "");

        edit_email = ep_email.getText().toString();
        edit_alamat = ep_alamat.getText().toString();
        edit_telephone = ep_telephone.getText().toString();

        Call<EditProfilResponse> call = ApiClient
                .getEditProfilService()
                .editProfilUser(idUser, edit_email, edit_alamat, edit_telephone);

        call.enqueue(new Callback<EditProfilResponse>() {
            @Override
            public void onResponse(Call<EditProfilResponse> call, Response<EditProfilResponse> response) {
                if (response.isSuccessful()) {
                    EditProfilResponse obj = response.body();
                    String sp_email = obj.getEmail();
                    String sp_alamat = obj.getAlamat();
                    String sp_telephone = obj.getTelephone();

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("email", sp_email);
                    editor.putString("alamat", sp_alamat);
                    editor.putString("telephone", sp_telephone);
                    editor.commit();
                    editor.apply();

                    Intent intent = new Intent(EditProfil.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Successfully Uploaded : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<EditProfilResponse> call, Throwable t) {
                Log.d("ERROR", "Failed Uploading : " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error, image.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestPermissionFoto(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            updateFotoProfil();
        }
    }

    private void requestPermissionKeahlian(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            updateKeahlianProfil();
        }
    }
}