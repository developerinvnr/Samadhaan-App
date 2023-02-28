package com.vnrseeds.samadhan.notifications;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.NotificationListAdapter;
import com.vnrseeds.samadhan.parser.notificationlistparser.Datum;
import com.vnrseeds.samadhan.parser.notificationlistparser.NotificationListResponse;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.pojo.NotificationDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListActivity extends AppCompatActivity implements NotificationListAdapter.NotificationAdapterListener{

    private static final String TAG = "NotificationListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private ImageButton back_nav;
    private RecyclerView rv_notificationList;
    private NotificationListAdapter listAdapter;
    private String token;
    private final ArrayList<NotificationDetailsPojo> notificationDetailsData = new ArrayList<>();
    private FloatingActionButton btn_pushNotification;
    private List<Datum> notificationList;
    private RoleResponse roleResponse;
    private ImageView iv_noDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(NotificationListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        toolbar_title.setText("Notifications");
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        rv_notificationList = findViewById(R.id.rv_notificationList);
        btn_pushNotification = findViewById(R.id.btn_pushNotification);
        iv_noDataFound = findViewById(R.id.iv_noDataFound);

        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            btn_pushNotification.setVisibility(View.GONE);
        }else{
            btn_pushNotification.setVisibility(View.VISIBLE);
        }
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(view -> {
            Intent intent = new Intent(NotificationListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)) {
            getNotificationList();
        }else {
            getAllNotifications();
        }
        btn_pushNotification.setOnClickListener(view -> {
            Intent intent = new Intent(NotificationListActivity.this, PushNotificationActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void getAllNotifications() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<NotificationListResponse> call = apiInterface.getAllNotifications("application/json", "Bearer "+token);
        call.enqueue(new Callback<NotificationListResponse>() {
            @SuppressLint({"NotifyDataSetChanged", "LongLogTag"})
            @Override
            public void onResponse(@NotNull Call<NotificationListResponse> call, @NotNull Response<NotificationListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    NotificationListResponse notificationListResponse = response.body();
                    assert notificationListResponse != null;
                    notificationList = notificationListResponse.getData();
                    Log.e(TAG, String.valueOf(notificationList));
                    if (notificationList.isEmpty()){
                        rv_notificationList.setVisibility(View.GONE);
                        iv_noDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(NotificationListActivity.this, "No new notifications", Toast.LENGTH_SHORT).show();
                    }else {
                        iv_noDataFound.setVisibility(View.GONE);
                        listAdapter = new NotificationListAdapter(NotificationListActivity.this, notificationList,NotificationListActivity.this);
                        rv_notificationList.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_notificationList.setLayoutManager(mLayoutManager);
                        rv_notificationList.setItemAnimator(new DefaultItemAnimator());
                        rv_notificationList.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                        //Toast.makeText(NotificationListActivity.this, notificationListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(NotificationListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(NotificationListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<NotificationListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getNotificationList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<NotificationListResponse> call = apiInterface.getNotifications("application/json", "Bearer "+token);
        call.enqueue(new Callback<NotificationListResponse>() {
            @SuppressLint({"NotifyDataSetChanged", "LongLogTag"})
            @Override
            public void onResponse(@NotNull Call<NotificationListResponse> call, @NotNull Response<NotificationListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    NotificationListResponse notificationListResponse = response.body();
                    assert notificationListResponse != null;
                    notificationList = notificationListResponse.getData();
                    Log.e(TAG, String.valueOf(notificationList));
                    if (notificationList.isEmpty()){
                        rv_notificationList.setVisibility(View.GONE);
                        iv_noDataFound.setVisibility(View.VISIBLE);
                        Toast.makeText(NotificationListActivity.this, "No new notifications", Toast.LENGTH_SHORT).show();
                    }else {
                        iv_noDataFound.setVisibility(View.GONE);
                        listAdapter = new NotificationListAdapter(NotificationListActivity.this, notificationList,NotificationListActivity.this);
                        rv_notificationList.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rv_notificationList.setLayoutManager(mLayoutManager);
                        rv_notificationList.setItemAnimator(new DefaultItemAnimator());
                        rv_notificationList.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                        //Toast.makeText(NotificationListActivity.this, notificationListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(NotificationListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(NotificationListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<NotificationListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onContactSelected(Datum notificationData) {
        //Toast.makeText(NotificationListActivity.this, notificationData.getNotificationMessage(), Toast.LENGTH_SHORT).show();
        NotificationDetailsPojo notificationDetailsPojo = new NotificationDetailsPojo(notificationData.getNotificationId(),notificationData.getNotificationSubject(),notificationData.getNotificationMessage(),notificationData.getNotificationCreatedAt(),notificationData.getNotificationCreatedBy(),notificationData.getNotificationIsViewed());
        SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_NOTIFICATION_OBJ, notificationDetailsPojo);
        Intent intent = new Intent(NotificationListActivity.this, NotificationDetailsActivity.class);
        intent.putExtra("detailsOf", "Notification");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NotificationListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}