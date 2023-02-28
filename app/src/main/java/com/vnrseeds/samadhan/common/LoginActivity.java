package com.vnrseeds.samadhan.common;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.loginparser.LoginResponse;
import com.vnrseeds.samadhan.parser.loginparser.User;
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

public class LoginActivity extends AppCompatActivity {
    private String TAG = "LoginActivity";
    private Button bt_submit;
    private EditText et_username;
    private EditText et_password;
    ProgressDialog progressDialog;
    private CustomProgressDialogue customProgressDialogue;
    private String firebaseToken;
    private String notiToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    // Get new Instance ID token
                    Log.e("NoToken", "dsgsdgsd");
                }else {
                    notiToken = task.getResult();
                    Log.e("NewToken", notiToken);
                }
            }
        });
        setTheme();
        init();
    }

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(LoginActivity.this);
        bt_submit = findViewById(R.id.bt_loginsubmit);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    private void init() {
        //User user = new User();
        //notiToken = SharedPreferences.getInstance().getString(SharedPreferences.KEY_NOTIFICATION_TOKEN);
        //Log.e("FirebaseToken", MyFirebaseMessagingService.getToken(this));

        User userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        String token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        if (userData!=null && token!=null && !token.equalsIgnoreCase("")){
            //Toast.makeText(LoginActivity.this, "Welcome : "+token, Toast.LENGTH_SHORT).show();
            Toast.makeText(LoginActivity.this, "Welcome : "+userData.getUser_name(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        bt_submit.setOnClickListener(view -> {
            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            if (username.isEmpty()){
                Utils.getInstance().showAlert(LoginActivity.this, "Enter username");
            }else if (password.isEmpty()){
                Utils.getInstance().showAlert(LoginActivity.this, "Enter password");
            }else {
                submit(username,password);
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
    }

    private void submit(String username, String password) {
        customProgressDialogue.show();
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Log.e("Login", username+"="+password);
        Call<LoginResponse> call = apiInterface.getLoginInfo("application/json", username,password,notiToken);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                customProgressDialogue.cancel();
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.getStatus()) {
                        customProgressDialogue.cancel();
                        //Log.e(TAG, "Status : " + loginResponse.getStatus());
                        User userData = loginResponse.getData().getUser();
                        String token = loginResponse.getData().getToken();
                        SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_LOGIN_OBJ, userData);
                        SharedPreferences.getInstance().createString(token, SharedPreferences.KEY_TOKEN);
                        Toast.makeText(LoginActivity.this, "Welcome : "+userData.getUser_name(), Toast.LENGTH_SHORT).show();
                        if (userData.getUserIsResetPassword()==1) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        customProgressDialogue.cancel();
                        Utils.getInstance().showAlert(LoginActivity.this, "Invalid username or password");
                        Log.e(TAG, "Status : " + loginResponse.getStatus());
                    }
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(LoginActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}