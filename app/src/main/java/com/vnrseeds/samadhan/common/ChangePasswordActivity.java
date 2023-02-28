package com.vnrseeds.samadhan.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = "ChangePasswordActivity";
    private EditText currentPass, newPass, confirmPass;
    private Button savePass, btn_cancel;
    private String cPass, nPass, coPass = null;
    private ProgressDialog progressDialog;
    private String user_id;
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password2);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    private void setTheme(){
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(ChangePasswordActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        currentPass = findViewById(R.id.currentpass);
        newPass = findViewById(R.id.newPass);
        confirmPass = findViewById(R.id.confirmPass);
        savePass = findViewById(R.id.savePass);
        btn_cancel = findViewById(R.id.btn_cancel);

        userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
    }
    private void init(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userData.getUserIsResetPassword()==1) {
                    Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    SharedPreferences.getInstance().clearSharedPref();
                    Intent loginActivity = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    loginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(loginActivity);
                    finish();
                }
            }
        });

        savePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cPass = currentPass.getText().toString().trim();
                nPass = newPass.getText().toString().trim();
                coPass = confirmPass.getText().toString().trim();

                if (cPass.isEmpty()) {
                    Utils.getInstance().showAlert(ChangePasswordActivity.this, "Current Password is required!");
                } else if (nPass.isEmpty()) {
                    Utils.getInstance().showAlert(ChangePasswordActivity.this, "New Password is required!");
                } else if (coPass.isEmpty()) {
                    Utils.getInstance().showAlert(ChangePasswordActivity.this, "Confirm Password is required!");
                } else if (nPass.length()>16) {
                    Utils.getInstance().showAlert(ChangePasswordActivity.this, "New Password may not be greater than 16 characters!");
                } else if (nPass.length()<6) {
                    Utils.getInstance().showAlert(ChangePasswordActivity.this, "New Password must be at least 6 characters!");
                } else if (nPass.equals(cPass)) {
                    Utils.getInstance().showAlert(ChangePasswordActivity.this, "New Password must be different from Current Password!");
                } else if (!nPass.equals(coPass)) {
                    Utils.getInstance().showAlert(ChangePasswordActivity.this, "Confirm Password must be same as New Password!");
                }else {
                    submitChangePassword(cPass,nPass,coPass);
                }
            }
        });
    }

    private void submitChangePassword(String cPass, String nPass, String coPass) {
        customProgressDialogue.show();
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<SubmitSuccessResponse> call = apiInterface.getChangePasswordResponse("application/json", "Bearer "+token, cPass, nPass, coPass);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                customProgressDialogue.cancel();
                if (response.isSuccessful()) {
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    assert submitSuccessResponse != null;
                    if (submitSuccessResponse.getStatus()) {
                        customProgressDialogue.cancel();
                        //Log.e(TAG, "Status : " + loginResponse.getStatus());
                        Toast.makeText(ChangePasswordActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        SharedPreferences.getInstance().clearSharedPref();
                        Intent loginActivity = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        loginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(loginActivity);
                        finish();
                    } else {
                        customProgressDialogue.cancel();
                        Utils.getInstance().showAlert(ChangePasswordActivity.this, submitSuccessResponse.getMessage());
                        //Log.e(TAG, "Status : " + loginResponse.getStatus());
                    }
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(ChangePasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                            //Utils.getInstance().showAlert(ChangePasswordActivity.this, "Invalid username or password");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitSuccessResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}