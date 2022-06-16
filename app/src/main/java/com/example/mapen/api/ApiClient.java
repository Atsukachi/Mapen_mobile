package com.example.mapen.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // Rumah
//    private static final String url = "http://192.168.100.12/mapen/index.php/";
//    public static final String imageUrl = "http://192.168.100.12/mapen/assets/images/users/";
//    public static final String kegiatan_imageUrl = "http://192.168.100.12/mapen/assets/document/kegiatan/photo/";
//    public static final String profil_keahlianUrl = "http://192.168.100.12/mapen/assets/document/users/";

    // Umum
//    private static final String url = "http://192.168.67.250/mapen/index.php/";
//    public static final String imageUrl = "http://192.168.67.250/mapen/assets/images/users/";
//    public static final String kegiatan_imageUrl = "http://192.168.67.250/mapen/assets/document/kegiatan/photo/";
//    public static final String profil_keahlianUrl = "http://192.168.67.250/mapen/assets/document/users/";

    // CoE
    private static final String url = "http://192.168.35.141/mapen/index.php/";
    public static final String imageUrl = "http://192.168.35.141/mapen/assets/images/users/";
    public static final String kegiatan_imageUrl = "http://192.168.35.141/mapen/assets/document/kegiatan/photo/";
    public static final String profil_keahlianUrl = "http://192.168.35.141/mapen/assets/document/users/";

    private static ApiClient clientObject;
    private static Retrofit retrofit;
    public static final int REQUEST_PICK_PHOTO = 2;
    public static final int REQUEST_PICK_FILE = 3;
    public static final int REQUEST_WRITE_PERMISSION = 786;
    public static final int PERMISSION_REQUEST_STORAGE = 4;
    public static final int PERMISSION_REQUEST_LOCATION = 101;

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Edit Keahlian Profil
    public static MapenService getEditKeahlianProfilService(){
        MapenService editkeahlianprofilService = getRetrofit().create(MapenService.class);
        return editkeahlianprofilService;
    }

    // Edit Profil
    public static MapenService getEditProfilService(){
        MapenService editprofilService = getRetrofit().create(MapenService.class);
        return editprofilService;
    }

    // Edit Foto Profil
    public static MapenService getEditFotoProfilService(){
        MapenService editfotoprofilService = getRetrofit().create(MapenService.class);
        return editfotoprofilService;
    }

    // Log Kegiatan
    public static MapenService getLogKegiatanService(){
        MapenService logKegiatanService = getRetrofit().create(MapenService.class);
        return logKegiatanService;
    }

    // Edit Log Kegiatan
    public static MapenService getEditLogKegiatanService(){
        MapenService editlogKegiatanService = getRetrofit().create(MapenService.class);
        return editlogKegiatanService;
    }

    // SKP Log Kegiatan
    public static MapenService getSKPLogKegService(){
        MapenService skpLogKegService = getRetrofit().create(MapenService.class);
        return skpLogKegService;
    }

    // Kode Kegiatan Log Kegiatan
    public static MapenService getKodeKegService(){
        MapenService kodeKegService = getRetrofit().create(MapenService.class);
        return kodeKegService;
    }

    // Unit Kerja Log Kegiatan
    public static MapenService getUnitKerjaService(){
        MapenService unitKerjaService = getRetrofit().create(MapenService.class);
        return unitKerjaService;
    }

    // Tabel Log Kegiatan
    public static MapenService getTabellogkegService(){
        MapenService tabellogkegService = getRetrofit().create(MapenService.class);
        return tabellogkegService;
    }

    // Pengajuan SKP
    public static MapenService getPengajuanskpService(){
        MapenService pengajuanskpService = getRetrofit().create(MapenService.class);
        return pengajuanskpService;
    }

    // Edit SKP
    public static MapenService getEditskpService(){
        MapenService editskpService = getRetrofit().create(MapenService.class);
        return editskpService;
    }

    // Bulan Pengajuan SKP
    public static MapenService getBulanService(){
        MapenService bulanService = getRetrofit().create(MapenService.class);
        return bulanService;
    }

    // Tabel Pengajuan SKP
    public static MapenService getTabelskpService(){
        MapenService tabelskpService = getRetrofit().create(MapenService.class);
        return tabelskpService;
    }

    // Metode Kerja Presensi
    public static MapenService getMetodeKerjaService(){
        MapenService metodeKerjaService = getRetrofit().create(MapenService.class);
        return metodeKerjaService;
    }

    // Riwayat Presensi
    public static MapenService getRiwayatPresensiService(){
        MapenService riwayatPresensiService = getRetrofit().create(MapenService.class);
        return riwayatPresensiService;
    }

    // Presensi
    public static MapenService getPresensiService(){
        MapenService presensiService = getRetrofit().create(MapenService.class);
        return presensiService;
    }

    public static synchronized ApiClient getInstance()
    {
        if(clientObject == null)
            clientObject = new ApiClient();
        return clientObject;
    }

    public MapenService getApi() {
        return retrofit.create(MapenService.class);
    }
}