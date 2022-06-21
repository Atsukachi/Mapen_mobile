package com.example.mapen.kegiatan;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mapen.MainActivity;
import com.example.mapen.R;
import com.example.mapen.api.ApiClient;
import com.example.mapen.api.FileUtils;
import com.example.mapen.api.MapenService;
import com.example.mapen.data.KodeKegiatanResponse;
import com.example.mapen.data.SkpByIdResponse;
import com.example.mapen.data.TabelLogKegiatanData;
import com.example.mapen.data.TabelLogKegiatanResponse;
import com.example.mapen.data.UnitKerjaResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mapen.api.ApiClient.PERMISSION_REQUEST_STORAGE;

public class EditLogKegiatan extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_back;
    LinearLayout log_kegiatan_savebtn, log_kegiatan_uploadfile;
    TextView logkeg_kodekegiatan, logkeg_file, header_logkeg;
    EditText logkeg_uraian;
    Intent intent;
    MapenService serviceAPI;
    TabelLogKegiatanResponse tabelLogKegiatanResponse;
    List<String> listdataSKP, listdataIDSKP, SKPlist, IDSKPlist;
    String[] listUnitKerja, listIdUnitKerja;
    ScrollView scrollView;
    SharedPreferences sp;
    Uri fileUri;
    Cursor cursor;
    Spinner spinnerSKP, spinnerUnitKerja;
    String tanggal_editLogKeg, editlk_user, user_nama_editLogKeg, id_editLogKeg, kegiatan_id_editLogKeg, nama_unitkerja_editLogKeg, uraian_editLogKeg, nama_skp_editLogKeg, file_editLogKeg;
    String skp_editlk, id_skp_editlk, editlk_kodekegiatan, editlk_unitkerja, editlk_idunitkerja, fileName;
    int unitkerja_editLogKeg, skp_editLogKeg, user_editLogKeg, file_categories_editLogKeg, count, jumlah;
    RequestBody requestFile, requestId, requestUser, requestUnitKerja, requestUraian, requestSKP, requestTanggal;
    File filePart;
    MultipartBody.Part partFile;
    private static final int REQUEST_PICK_FILE = ApiClient.REQUEST_PICK_FILE;
    private static final int REQUEST_WRITE_PERMISSION = ApiClient.REQUEST_WRITE_PERMISSION;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            logkegiatan();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_kegiatan);

        initComponents();

        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        intent = getIntent();
        if(intent.getExtras() != null){
            tabelLogKegiatanResponse = (TabelLogKegiatanResponse) intent.getSerializableExtra("data_edit_logkeg");
            Log.d("tabellogkeg", String.valueOf(tabelLogKegiatanResponse));

            sp = getSharedPreferences("user_detail",MODE_PRIVATE);
            user_nama_editLogKeg = sp.getString("name", "");

            id_editLogKeg = tabelLogKegiatanResponse.getId();
            kegiatan_id_editLogKeg = tabelLogKegiatanResponse.getKegiatan_id();
            unitkerja_editLogKeg = tabelLogKegiatanResponse.getUnitkerja();
            nama_unitkerja_editLogKeg = tabelLogKegiatanResponse.getNama_unit_kerja();
            uraian_editLogKeg = tabelLogKegiatanResponse.getUraian();
            skp_editLogKeg = tabelLogKegiatanResponse.getSkp();
            nama_skp_editLogKeg = tabelLogKegiatanResponse.getNama_skp();
            user_editLogKeg = tabelLogKegiatanResponse.getUser();
            tanggal_editLogKeg = tabelLogKegiatanResponse.getTanggal();
            file_editLogKeg = tabelLogKegiatanResponse.getFile();
            file_categories_editLogKeg = tabelLogKegiatanResponse.getFile_categories();

            logkeg_uraian.setText(uraian_editLogKeg);
            logkeg_file.setText(file_editLogKeg);
        } else {
            Toast.makeText(EditLogKegiatan.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
        }

        getDataSKP();
        getDataUnitKerja();
        getKodeKegiatan();

        spinnerSKP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                skp_editlk = adapterView.getItemAtPosition(i).toString();
                count = i;
                id_skp_editlk = IDSKPlist.get(count);
                Log.d("SKP", skp_editlk);
                Log.d("SKP", id_skp_editlk);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(EditLogKegiatan.this, "SKP Field Has Not Been Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initComponents() {
        logkeg_uraian = findViewById(R.id.txtUraianPekerjaanLogKeg);
        logkeg_kodekegiatan = findViewById(R.id.txtKodeKegiatanLogKeg);
        logkeg_file = findViewById(R.id.txtFileLogKegiatan);

        header_logkeg = findViewById(R.id.txtHeaderLogKeg);
        header_logkeg.setText(R.string.lbl_edit_logkeg);

        spinnerSKP = findViewById(R.id.spinnerSKPLogKeg);
        spinnerUnitKerja = findViewById(R.id.spinnerUnitKerjaLogKeg);

        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        log_kegiatan_savebtn = findViewById(R.id.btn_save_logkeg);
        log_kegiatan_savebtn.setOnClickListener(this);

        log_kegiatan_uploadfile = findViewById(R.id.btn_uploadfile_logkeg);
        log_kegiatan_uploadfile.setOnClickListener(this);

        scrollView = findViewById(R.id.sv_logkegiatan);
    }

    private void getKodeKegiatan() {
        sp = getSharedPreferences("user_detail",MODE_PRIVATE);
        editlk_user = sp.getString("user_id", "");
        if(sp.contains("user_id")) {
            serviceAPI = ApiClient.getKodeKegService();
            serviceAPI.kode_kegiatan(editlk_user).enqueue(new Callback<KodeKegiatanResponse>() {
                @Override
                public void onResponse(Call<KodeKegiatanResponse> call, Response<KodeKegiatanResponse> response) {
                    if(response.isSuccessful()){
                        KodeKegiatanResponse obj = response.body();
                        editlk_kodekegiatan = obj.getKodekegiatan();
                        logkeg_kodekegiatan.setText(editlk_kodekegiatan);
                    } else {
                        Toast.makeText(EditLogKegiatan.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<KodeKegiatanResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    private void getDataSKP() {
        sp = getSharedPreferences("user_detail",MODE_PRIVATE);
        editlk_user = sp.getString("user_id", "");
        if(sp.contains("user_id")) {
            Call<List<SkpByIdResponse>> idskpCall = ApiClient
                    .getSKPLogKegService()
                    .skpbyid(editlk_user);

            idskpCall.enqueue(new Callback<List<SkpByIdResponse>>() {
                @Override
                public void onResponse(Call<List<SkpByIdResponse>> call, Response<List<SkpByIdResponse>> response) {
                    if(response.isSuccessful()){
                        List<SkpByIdResponse> obj = response.body();
                        Log.d("listskp", String.valueOf(obj));

                        listdataSKP = new ArrayList<String>();
                        listdataIDSKP = new ArrayList<String>();
                        listdataSKP.add(0,"Tidak Dengan SKP");
                        listdataIDSKP.add(0,"0");

                        SKPlist = new ArrayList<String>();
                        IDSKPlist = new ArrayList<String>();

                        SKPlist.addAll(listdataSKP);
                        IDSKPlist.addAll(listdataIDSKP);

                        for (int i = 0; i < obj.size(); i++) {
                            SKPlist.addAll(Arrays.asList(obj.get(i).getNama_skp()));
                            Log.d("skplist", String.valueOf(SKPlist));
                            
                            IDSKPlist.addAll(Arrays.asList(obj.get(i).getId_skp()));
                            Log.d("idskplist", String.valueOf(IDSKPlist));
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(EditLogKegiatan.this, R.layout.spinner_item, SKPlist);
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spinnerSKP.setAdapter(spinnerArrayAdapter);
                        spinnerSKP.setSelection(((ArrayAdapter<String>)spinnerSKP.getAdapter()).getPosition(nama_skp_editLogKeg));
                    }
                }

                @Override
                public void onFailure(Call<List<SkpByIdResponse>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    private void getDataUnitKerja() {
        serviceAPI = ApiClient.getUnitKerjaService();
        serviceAPI.unit_kerja().enqueue(new Callback<List<UnitKerjaResponse>>() {
            @Override
            public void onResponse(Call<List<UnitKerjaResponse>> call, Response<List<UnitKerjaResponse>> response) {
                if(response.isSuccessful()){
                    List<UnitKerjaResponse> obj = response.body();

                    if (obj != null && obj.size() > 0) {
                        listUnitKerja = new String[obj.size()];
                        listIdUnitKerja = new String[obj.size()];

                        for (int i = 0; i < obj.size(); i++) {
                            listUnitKerja[i] = String.valueOf(obj.get(i).getNama_unit_kerja());
                            listIdUnitKerja[i] = String.valueOf(obj.get(i).getId_unit_kerja());

                            spinnerUnitKerja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    editlk_unitkerja = adapterView.getItemAtPosition(i).toString();
                                    jumlah = i;
                                    editlk_idunitkerja = listIdUnitKerja[jumlah];
                                    Log.d("UK", editlk_unitkerja);
                                    Log.d("UK", editlk_idunitkerja);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    Toast.makeText(EditLogKegiatan.this, "Unit Kerja Field Has Not Been Selected", Toast.LENGTH_SHORT).show();
                                }
                            });

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(EditLogKegiatan.this, R.layout.spinner_item, listUnitKerja);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            spinnerUnitKerja.setAdapter(spinnerArrayAdapter);
                            spinnerUnitKerja.setSelection(((ArrayAdapter<String>)spinnerUnitKerja.getAdapter()).getPosition(nama_unitkerja_editLogKeg));
                        }
                    } else {
                        Toast.makeText(EditLogKegiatan.this, "List Data Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditLogKegiatan.this, "List Data Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UnitKerjaResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_save_logkeg:
                requestPermission();
                break;
            case R.id.btn_uploadfile_logkeg:
                chooseFile();
                break;
        }
    }

    private void chooseFile() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        } else {
            openFilePicker();
        }
    }

    private void openFilePicker() {
        Intent fileIntent = new Intent();
        fileIntent.setAction(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("*/*");
        startActivityForResult(Intent.createChooser(fileIntent, "Select File"), REQUEST_PICK_FILE);
    }

    private void requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            logkegiatan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_FILE) {
                if (data != null) {
                    fileUri = data.getData();
                    cursor = getContentResolver().query(fileUri, null, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    cursor.close();
                    logkeg_file.setText(fileName);
                    Log.d("COBA", String.valueOf(fileUri));
                }
            }
        }
    }

    private void logkegiatan() {
        if(fileName == null) {
            Toast.makeText(this, "Silahkan pilih file terlebih dahulu...", Toast.LENGTH_SHORT).show();
        } else {
            sp = getSharedPreferences("user_detail",MODE_PRIVATE);
            editlk_user = sp.getString("user_id", "");

            filePart = FileUtils.getFile(this, fileUri);
            requestFile = RequestBody.create(MediaType.parse("multipart/form-file"), filePart);
            partFile = MultipartBody.Part.createFormData("editfile", filePart.getName(), requestFile);

            requestId = RequestBody.create(MediaType.parse("text/plain"), id_editLogKeg);
            requestUser = RequestBody.create(MediaType.parse("text/plain"), editlk_user);
            requestUnitKerja = RequestBody.create(MediaType.parse("text/plain"), editlk_idunitkerja);
            requestUraian = RequestBody.create(MediaType.parse("text/plain"), logkeg_uraian.getText().toString());
            requestSKP = RequestBody.create(MediaType.parse("text/plain"), id_skp_editlk);

            Call<TabelLogKegiatanData> logkegCall = ApiClient
                    .getEditLogKegiatanService()
                    .edit_log_kegiatan(partFile, requestId, requestUnitKerja, requestUraian, requestSKP);

            logkegCall.enqueue(new Callback<TabelLogKegiatanData>() {
                @Override
                public void onResponse(Call<TabelLogKegiatanData> call, Response<TabelLogKegiatanData> response) {
                    if (response.isSuccessful()) {
                        TabelLogKegiatanData obj = response.body();
                        Log.d("EDIT_LOGKEG", String.valueOf(obj));

                        Toast.makeText(getApplicationContext(), "Successfully Uploaded : " + response.message(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Edit Log Kegiatan Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TabelLogKegiatanData> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Edit Log Kegiatan Error", Toast.LENGTH_SHORT).show();
                    Log.e("FAIL",t.getLocalizedMessage());
                }
            });
        }
    }
}