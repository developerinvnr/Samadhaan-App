package com.vnrseeds.samadhan.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.pojo.NotificationDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetailsActivity extends AppCompatActivity {

    private static final String TAG = "NotificationDetailsActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private NotificationDetailsPojo notificationDetails;
    private TextView tv_subject;
    private TextView tv_description;
    private TextView tv_servicetype;
    private TextView tv_assetNames;
    private TextView tv_issueGroup;
    private TextView tv_affectedToDate;
    private LinearLayout ll_noticeData;
    private String detailsOf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(NotificationDetailsActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);

        back_nav = findViewById(R.id.back_nav);
        tv_subject = findViewById(R.id.tv_subject);
        tv_description = findViewById(R.id.tv_description);
        tv_servicetype = findViewById(R.id.tv_servicetype);
        tv_assetNames = findViewById(R.id.tv_assetNames);
        tv_issueGroup = findViewById(R.id.tv_issueGroup);
        tv_affectedToDate = findViewById(R.id.tv_affectedToDate);
        ll_noticeData = findViewById(R.id.ll_noticeData);

        detailsOf=getIntent().getStringExtra("detailsOf");

        notificationDetails = (NotificationDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_NOTIFICATION_OBJ, NotificationDetailsPojo.class);

        tv_subject.setText(notificationDetails.getNotificationSubject());
        tv_description.setText(notificationDetails.getNotificationMessage());
        //tv_assetNames.setText(notificationDetails.getNotificationMessage());
        if (detailsOf.equalsIgnoreCase("Notice")){
            ll_noticeData.setVisibility(View.VISIBLE);
            tv_affectedToDate.setText(notificationDetails.getNotificationCreatedBy());
            toolbar_title.setText("Notice Details");
        }else {
            ll_noticeData.setVisibility(View.GONE);
            toolbar_title.setText("Notification Details");
        }

    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(view -> {
            Intent intent = new Intent(NotificationDetailsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        if (notificationDetails.getNotificationIsViewed()==null || notificationDetails.getNotificationIsViewed().equals("0")) {
            setNotificationViewed(notificationDetails.getNotificationId());
        }
    }

    @SuppressLint("LongLogTag")
    private void setNotificationViewed(Integer notificationId) {
        Log.e(TAG, String.valueOf(notificationId));
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.setNotificationViewed("application/json", "Bearer "+token, notificationId);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    assert submitSuccessResponse != null;
                    if (submitSuccessResponse.getStatus()) {
                        Toast.makeText(NotificationDetailsActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(NotificationDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(NotificationDetailsActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SubmitSuccessResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NotificationDetailsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}