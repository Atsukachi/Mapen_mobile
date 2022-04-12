package com.example.mapen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener{
    ImageView btn_back;
    TextView iniemail;
    LinearLayout btn_save_cp;
    String current_pass, new_pass1, new_pass2, email, error_message;
    EditText cp_pass, cp_newpass1, cp_newpass2;
    Context mContext;
    ProgressDialog loading;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        email = sharedPreferences.getString("email", null);

        mContext = this;
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
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                break;
        }
    }

//    private void changepassword() {
//        baseApiService.changepassRequest(cp_pass.getText().toString(),
//                cp_newpass1.getText().toString(),
//                cp_newpass2.getText().toString(),
//                email)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()){
//                            Log.i("debug", "onResponse: BERHASIL");
//                            loading.dismiss();
//                            try {
//                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                if (jsonRESULTS.getString("error").equals("false")){
//                                    Toast.makeText(mContext, "Berhasil Update Password", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(mContext, MainActivity.class));
//                                } else {
//                                    error_message = jsonRESULTS.getString("error_msg");
//                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException | IOException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            Log.i("debug", "onResponse: GA BERHASIL");
//                            loading.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
//                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}