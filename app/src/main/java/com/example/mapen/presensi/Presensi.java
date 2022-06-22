package com.example.mapen.presensi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapen.MainActivity;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.api.FileUtils;
import com.example.mapen.api.MapenService;
import com.example.mapen.data.MetodeKerjaResponse;
import com.example.mapen.data.RiwayatPresensiResponse;
import com.example.mapen.data.TabelLogKegiatanData;
import com.example.mapen.data.TabelLogKegiatanResponse;
import com.example.mapen.data.TabelPresensiResponse;
import com.example.mapen.data.UnitKerjaResponse;
import com.example.mapen.kegiatan.EditLogKegiatan;
import com.example.mapen.kegiatan.LogKegiatan;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mapen.api.ApiClient.PERMISSION_REQUEST_CAMERA;
import static com.example.mapen.api.ApiClient.PERMISSION_REQUEST_STORAGE;

public class Presensi extends AppCompatActivity implements View.OnClickListener {
    GpsTracker gpsTracker;
    TextView tvLatitude, tvLongitude, tvStatus, tvRiwayat;
    ImageView btn_back, ivFoto;
    ScrollView scrollView;
    LinearLayout uploadfoto_btn, save_btn, lokasi_btn;
    SharedPreferences sp;
    MapenService serviceAPI;
    String[] listIdMetodeKerja, listNamaMetodekerja, listRiwayatPresensi, listStatusPresensi;
    Spinner spinnerMetodeKerja;
    String pathFoto, getTime, user_id, idriwayat, idstatus, riwayat, status, idmetodekerja, namametodekerja, extStorageDirectory, fileName, fotoName, fotoPath, riwayatPresensi, statusPresensi;
    int size;
    OutputStream outStream;
    Bitmap fotoBitmap;
    Bundle extras;
    File fotoFile, filePart;
    Uri fotoUri;
    MultipartBody.Part fotoPart;
    double latitude, longitude;
    RequestBody userRequest, fotoRequest, riwayatRequest, statusRequest, kerjaRequest, latRequest, longRequest, cek_presensiRequest;

    private static final int REQUEST_PICK_PHOTO = ApiClient.REQUEST_PICK_PHOTO;
    private static final int PERMISSION_REQUEST_CAMERA = ApiClient.PERMISSION_REQUEST_CAMERA;
    private static final int REQUEST_WRITE_PERMISSION = ApiClient.REQUEST_WRITE_PERMISSION;
    private static final int PERMISSION_REQUEST_LOCATION = ApiClient.PERMISSION_REQUEST_LOCATION;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presensiPegawai();
            } else {
                Toast.makeText(this, "Location Permission is Required", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openPhotoPicker();
            } else {
                Toast.makeText(this, "Camera Permission is Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi);

        initComponents();

        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        getDataMetodeKerja();
//        getRiwayatPresensi();

        sp = getSharedPreferences("user_detail", MODE_PRIVATE);

        extras = getIntent().getExtras();
        if (extras != null) {
            riwayat = extras.getString("data_riwayat_presensi");
            status = extras.getString("data_status_presensi");
            idriwayat = extras.getString("data_idriwayat_presensi");
            idstatus = extras.getString("data_idstatus_presensi");

            tvRiwayat.setText(riwayat);
            tvStatus.setText(status);

            Log.d("riwayat", riwayat);
            Log.d("status", status);
        } else {
            Toast.makeText(Presensi.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void initComponents() {
        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        scrollView = findViewById(R.id.sv_presensi);

        uploadfoto_btn = findViewById(R.id.btn_uploadfotoPresensi);
        uploadfoto_btn.setOnClickListener(this);

        save_btn = findViewById(R.id.btn_save_presensi);
        save_btn.setOnClickListener(this);

        ivFoto = findViewById(R.id.ivFotoPresensi);

        spinnerMetodeKerja = findViewById(R.id.spinnerMetodeKerjaPresensi);

        tvStatus = findViewById(R.id.txtStatusPresensi);
        tvRiwayat = findViewById(R.id.txtRiwayatPresensi);
        tvLatitude = findViewById(R.id.txtLokasiLatPresensi);
        tvLongitude = findViewById(R.id.txtLokasiLongPresensi);

        lokasi_btn = findViewById(R.id.btn_ambillokasiPresensi);
        lokasi_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_save_presensi:
                requestPermission();
                break;
            case R.id.btn_uploadfotoPresensi:
                choosePhoto();
                break;
            case R.id.btn_ambillokasiPresensi:
                requestLocation();
                break;
        }
    }

    private void requestLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        gpsTracker = new GpsTracker(Presensi.this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            tvLatitude.setText(String.valueOf(latitude));
            tvLongitude.setText(String.valueOf(longitude));
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        } else {
            openPhotoPicker();
        }
    }

    private void openPhotoPicker() {
        Intent fileIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(fileIntent, REQUEST_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    fotoBitmap = (Bitmap) data.getExtras().get("data");
                    ivFoto.setImageBitmap(fotoBitmap);

                    try {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        if (imageBitmap != null) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 1000, byteArrayOutputStream);
                            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                            fotoFile = saveBitmap(fotoBitmap);
                            Log.d("fotoFile", String.valueOf(fotoFile));
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private File saveBitmap(Bitmap fotoBitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(extStorageDirectory);
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory);
        }

        try {
            outStream = new FileOutputStream(file);
            fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    private Uri getImageUri(Context context, Bitmap fotoBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        fotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        getTime = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        fileName = "image_" + getTime;
        fotoPath = MediaStore.Images.Media.insertImage(context.getContentResolver(), fotoBitmap, fileName, null);
        return Uri.parse(fotoPath);
    }

    private String getRealPathFromURI(Uri fotoUri) {
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(fotoUri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                pathFoto = cursor.getString(idx);
                Log.d("pathFoto", pathFoto);
                cursor.close();
            }
        }
        return pathFoto;
    }

    private void requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            presensiPegawai();
        }
    }

    private void getDataMetodeKerja() {
        serviceAPI = ApiClient.getMetodeKerjaService();
        serviceAPI.metode_kerja().enqueue(new Callback<List<MetodeKerjaResponse>>() {

            @Override
            public void onResponse(Call<List<MetodeKerjaResponse>> call, Response<List<MetodeKerjaResponse>> response) {
                if(response.isSuccessful()){
                    List<MetodeKerjaResponse> obj = response.body();
                    Log.d("obj", String.valueOf(obj));
                    if (obj != null && obj.size() > 0) {
                        listIdMetodeKerja = new String[obj.size()];
                        listNamaMetodekerja = new String[obj.size()];

                        for (int i = 0; i < obj.size(); i++) {
                            listIdMetodeKerja[i] = obj.get(i).getId_kerja();
                            listNamaMetodekerja[i] = obj.get(i).getMetode();

                            spinnerMetodeKerja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    namametodekerja = adapterView.getItemAtPosition(i).toString();
                                    size = i;
                                    idmetodekerja = listIdMetodeKerja[size];
                                    Log.d("MK", namametodekerja);
                                    Log.d("MK", idmetodekerja);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    Toast.makeText(Presensi.this, "Metode Kerja Field Has Not Been Selected", Toast.LENGTH_SHORT).show();
                                }
                            });

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(Presensi.this, R.layout.spinner_item, listNamaMetodekerja);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            spinnerMetodeKerja.setAdapter(spinnerArrayAdapter);
                        }
                    } else {
                        Toast.makeText(Presensi.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Presensi.this, "List Data Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MetodeKerjaResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void presensiPegawai() {
        if(fotoBitmap == null) {
            Toast.makeText(this, "Silahkan pilih foto terlebih dahulu...", Toast.LENGTH_SHORT).show();
        } else {
            sp = getSharedPreferences("user_detail",MODE_PRIVATE);
            user_id = sp.getString("user_id", "");

//            filePart = FileUtils.getFile(this, Uri.fromFile(fotoFile));
            fotoRequest = RequestBody.create(MediaType.parse("image/*"), fotoFile);
            fotoPart = MultipartBody.Part.createFormData("foto", fotoFile.getName(), fotoRequest);

            userRequest = RequestBody.create(MediaType.parse("text/plain"), user_id);
            riwayatRequest = RequestBody.create(MediaType.parse("text/plain"), idriwayat);
            statusRequest = RequestBody.create(MediaType.parse("text/plain"), idstatus);
            kerjaRequest = RequestBody.create(MediaType.parse("text/plain"), idmetodekerja);
            latRequest = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude));
            longRequest = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude));

            Call<TabelPresensiResponse> presensiCall = ApiClient
                    .getLogKegiatanService()
                    .presensi(fotoPart, userRequest, riwayatRequest, statusRequest, kerjaRequest, latRequest, longRequest);

            presensiCall.enqueue(new Callback<TabelPresensiResponse>() {
                @Override
                public void onResponse(Call<TabelPresensiResponse> call, Response<TabelPresensiResponse> response) {
                    if (response.isSuccessful()) {
                        TabelPresensiResponse obj = response.body();
                        Log.d("presensi", String.valueOf(obj));
                    } else {
                        Toast.makeText(getApplicationContext(), "Presensi Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TabelPresensiResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Presensi Error", Toast.LENGTH_SHORT).show();
                    Log.e("FAIL",t.getLocalizedMessage());
                }
            });
        }
    }
}