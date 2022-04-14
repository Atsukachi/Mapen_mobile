package com.example.mapen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mapen.api.ApiClient;
import com.example.mapen.api.MapenService;
import com.example.mapen.data.ChangepassResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener{
    ImageView btn_back;
    TextView iniemail;
    LinearLayout btn_save_cp;
    String current_password, new_password1, new_password2, email, email_cp, status_cp;
    EditText cp_pass, cp_newpass1, cp_newpass2;
    MapenService serviceAPI;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        initComponents();
    }

    private void initComponents() {
        btn_back = findViewById(R.id.arrow_back);
        btn_back.setOnClickListener(this);

        btn_save_cp = findViewById(R.id.btn_save_changepass);
        btn_save_cp.setOnClickListener(this);

        cp_pass = findViewById(R.id.txtCurrentPassword);
        cp_newpass1 = findViewById(R.id.txtNewPassword);
        cp_newpass2 = findViewById(R.id.txtRepeatNewPassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_back:
                onBackPressed();
                break;
            case R.id.btn_save_changepass:
                changepassword();
                break;
        }
    }

    private void changepassword() {
        sp = getSharedPreferences("user_detail",MODE_PRIVATE);

        current_password = cp_pass.getText().toString();
        new_password1 = cp_newpass1.getText().toString();
        new_password2 = cp_newpass2.getText().toString();

        if(sp.contains("user_id")){
            email = sp.getString("email", "");
            Log.d("TAG", email);

            Call<ChangepassResponse> call = ApiClient
                    .getInstance()
                    .getApi()
                    .changePass(email, current_password, new_password1, new_password1);

            call.enqueue(new Callback<ChangepassResponse>() {
                @Override
                public void onResponse(Call <ChangepassResponse> call, Response<ChangepassResponse> response) {
                    if(response.isSuccessful()){
                        ChangepassResponse obj = response.body();

                        email_cp = obj.getEmail();
                        status_cp = obj.getStatus();

                        if(status_cp.equals("fail"))
                        {
                            Toast.makeText(ChangePassword.this, "Change Password Failed", Toast.LENGTH_SHORT).show();
                            cp_pass.setText("");
                            cp_newpass1.setText("");
                            cp_newpass2.setText("");
                        }

                        if(status_cp.equals("success"))
                        {
                            Toast.makeText(ChangePassword.this, "Change Password Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ChangepassResponse> call, Throwable t) {
                    Toast.makeText(ChangePassword.this, "Change Password Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}