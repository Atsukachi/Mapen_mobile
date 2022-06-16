package com.example.mapen.api;

import com.example.mapen.data.ChangepassResponse;
import com.example.mapen.data.EditProfilResponse;
import com.example.mapen.data.GetBulanResponse;
import com.example.mapen.data.KodeKegiatanResponse;
import com.example.mapen.data.MetodeKerjaResponse;
import com.example.mapen.data.RiwayatPresensiResponse;
import com.example.mapen.data.TabelLogKegiatanData;
import com.example.mapen.data.TabelLogKegiatanResponse;
import com.example.mapen.data.LoginResponse;
import com.example.mapen.data.PengajuanskpResponse;
import com.example.mapen.data.SkpByIdResponse;
import com.example.mapen.data.TabelPresensiResponse;
import com.example.mapen.data.TabelskpResponse;
import com.example.mapen.data.UnitKerjaResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MapenService {

    // Auth
    @FormUrlEncoded
    @POST("C_Login/")
    Call<LoginResponse> verifyUser(
            @Field("email") String email,
            @Field("password") String password
    );

    // Profil
    @FormUrlEncoded
    @POST("C_Changepassword/")
    Call<ChangepassResponse> changePass(
            @Field("email") String email,
            @Field("current_password") String current_password,
            @Field("new_password1") String new_password1,
            @Field("new_password2") String new_password2
    );

    @Multipart
    @POST("C_Editprofil/")
    Call<EditProfilResponse> editProfil(
            @Part MultipartBody.Part keahlian,
            @Part("user_id") RequestBody user_id,
            @Part("email") RequestBody email,
            @Part("alamat") RequestBody alamat,
            @Part("telephone") RequestBody telephone
    );

    @Multipart
    @POST("C_Editprofil/foto")
    Call<EditProfilResponse> editFotoProfil(
            @Part MultipartBody.Part image,
            @Part("user_id") RequestBody user_id
    );

    @FormUrlEncoded
    @POST("C_Editprofil/")
    Call<EditProfilResponse> editProfilUser(
            @Field("user_id") String user_id,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("telephone") String telephone
    );

    // SKP
    @GET("C_Table/skp")
    Call<List<TabelskpResponse>> userid_skp(
            @Query("user_id") String user_id
    );

    @GET("C_Pengajuanskp/")
    Call<List<GetBulanResponse>> getAllMonth();

    @FormUrlEncoded
    @POST("C_Pengajuanskp/")
    Call<PengajuanskpResponse> pengajuan_skp(
            @Field("user_id") String user_id,
            @Field("bulan") String bulan,
            @Field("tahun") String tahun,
            @Field("nama_skp") String nama_skp
    );

    @FormUrlEncoded
    @POST("C_Editskp/")
    Call<PengajuanskpResponse> edit_skp(
            @Field("id_skp") String id_skp,
            @Field("user_id") String user_id,
            @Field("bulan") String bulan,
            @Field("tahun") String tahun,
            @Field("nama_skp") String nama_skp
    );

    @FormUrlEncoded
    @POST("C_Nilaiskp/")
    Call<PengajuanskpResponse> nilai_skp(
            @Field("id_skp") String id_skp,
            @Field("nilai") String nilai
    );

    // Log Kegiatan
    @GET("C_Table/unit_kerja")
    Call<List<UnitKerjaResponse>> unit_kerja();

    @GET("C_Table/skpbyid")
    Call<List<SkpByIdResponse>> skpbyid(
            @Query("user_id") String user_id
    );

    @GET("C_Logkegiatan/")
    Call<KodeKegiatanResponse> kode_kegiatan(
            @Query("user_id") String user_id
    );

    @GET("C_Logkegiatan/data")
    Call<List<TabelLogKegiatanResponse>> userid_logkeg(
            @Query("user_id") String user_id
    );

    @Multipart
    @POST("C_Logkegiatan/")
    Call<TabelLogKegiatanData> log_kegiatan(
            @Part MultipartBody.Part file,
            @Part("user") RequestBody user,
            @Part("unitkerja") RequestBody unitkerja,
            @Part("uraian") RequestBody uraian,
            @Part("skp") RequestBody skp
    );

    @Multipart
    @POST("C_Editlogkegiatan/")
    Call<TabelLogKegiatanData> edit_log_kegiatan(
            @Part MultipartBody.Part editfile,
            @Part("id") RequestBody id,
            @Part("unitkerja") RequestBody unitkerja,
            @Part("uraian") RequestBody uraian,
            @Part("skp") RequestBody skp
    );

    // Presensi
    @GET("C_Presensi/metodekerja")
    Call<List<MetodeKerjaResponse>> metode_kerja();

    @GET("C_Presensi/waktu")
    Call<RiwayatPresensiResponse> riwayat_presensi(
            @Query("id_user") String id_user,
            @Query("riwayat") String riwayat
    );

    @Multipart
    @POST("C_Presensi/")
    Call<TabelPresensiResponse> presensi(
            @Part MultipartBody.Part file,
            @Part("user_id") RequestBody user_id,
            @Part("riwayat") RequestBody riwayat,
            @Part("status") RequestBody status,
            @Part("kerja") RequestBody kerja,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng
    );
}
