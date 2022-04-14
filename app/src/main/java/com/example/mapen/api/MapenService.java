package com.example.mapen.api;

import com.example.mapen.data.ChangepassResponse;
import com.example.mapen.data.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MapenService {

    @FormUrlEncoded
    @POST("C_Login/")
    Call<LoginResponse> verifyUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("C_Changepassword/")
    Call<ChangepassResponse> changePass(
            @Field("email") String email,
            @Field("current_password") String current_password,
            @Field("new_password1") String new_password1,
            @Field("new_password2") String new_password2
    );
}
