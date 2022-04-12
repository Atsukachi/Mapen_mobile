package com.example.mapen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mapen.api.ApiClient;
import com.example.mapen.data.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btn_signin;
    String email_login, password_login, message, email, user_id, role_id, image, keahlian, name, alamat, telephone;
    EditText login_email, login_pass;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        checkUserExistence();
    }

    private void initComponents() {
        login_email = findViewById(R.id.txtEmailLogin);
        login_pass = findViewById(R.id.txtPasswordLogin);

        btn_signin = findViewById(R.id.btnSignIn);
        btn_signin.setOnClickListener(view -> processlogin());
    }

     void processlogin() {
        email_login = login_email.getText().toString();
        password_login = login_pass.getText().toString();

        Call<LoginResponse> call = ApiClient
                .getInstance()
                .getApi()
                .verifyUser(email_login, password_login);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse obj = response.body();

                message = obj.getMessage();
                user_id = obj.getUser_id();
                role_id = obj.getRole_id();
                email = obj.getEmail();
                name = obj.getName();
                image = obj.getImage();
                alamat = obj.getAlamat();
                keahlian = obj.getKeahlian();
                telephone = obj.getTelephone();

                if(message.equals("failed"))
                {
                    login_email.setText("");
                    login_pass.setText("");
                }

                if(message.equals("exist"))
                {
                    sp = getSharedPreferences("user_detail", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("user_id", user_id);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("alamat", alamat);
                    editor.putString("telephone", telephone);
                    editor.putString("image", image);
                    editor.putString("keahlian", keahlian);
                    editor.putString("role_id", role_id);
                    editor.commit();
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserExistence() {
        sp = getSharedPreferences("user_detail", MODE_PRIVATE);
        if(sp.contains("email")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}