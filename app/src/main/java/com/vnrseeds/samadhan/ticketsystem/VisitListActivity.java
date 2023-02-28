package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.VisitListAdapter;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.communicator.alertCommunicator;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.sitevisitparser.SiteVisit;
import com.vnrseeds.samadhan.parser.sitevisitparser.SiteVisitListResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitListActivity extends AppCompatActivity implements VisitListAdapter.VisitListAdapterListener{

    private static final String TAG = "VisitListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private ImageButton back_nav;
    private String token;
    private RecyclerView lv_visitlist;
    private TicketDetailsPojo ticketDetailsPojo;
    private List<SiteVisit> visitListData=new ArrayList<>();
    private VisitListAdapter listAdapter;
    private FloatingActionButton btn_scheduleVisit;
    private int addVisitFlg=0;
    private RoleResponse roleResponse;
    private ImageView iv_noDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(VisitListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        btn_scheduleVisit = findViewById(R.id.btn_scheduleVisit);
        iv_noDataFound = findViewById(R.id.iv_noDataFound);
        toolbar_title.setText("Visit List");
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        lv_visitlist = findViewById(R.id.lv_visitlist);
        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
            btn_scheduleVisit.setVisibility(View.GONE);
        }else {
            btn_scheduleVisit.setVisibility(View.VISIBLE);
        }

        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") && ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
            btn_scheduleVisit.setVisibility(View.GONE);
        }
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
                    Intent intent = new Intent(VisitListActivity.this, TicketHandlingUserActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(VisitListActivity.this, TicketHandlingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btn_scheduleVisit.setOnClickListener(view -> {
            if (addVisitFlg==0) {
                actionVisit();
            }else {
                Toast.makeText(VisitListActivity.this, "Visit already scheduled", Toast.LENGTH_LONG).show();
            }
        });
        getVisitList();
    }

    private void actionVisit() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_visit_schedule);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        Button button_submit = dialog.findViewById(R.id.button_submit);
        EditText et_visit_purpose = dialog.findViewById(R.id.et_visit_purpose);
        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        EditText et_visitDate = dialog.findViewById(R.id.et_visitDate);
        et_visitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(VisitListActivity.this,"0","15", new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        et_visitDate.setText(date);
                    }
                });
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_visit_purpose.getText().toString().trim();
                String visit_date = et_visitDate.getText().toString();
                if (message.equalsIgnoreCase(" ") || message.isEmpty()){
                    Toast.makeText(VisitListActivity.this, "Enter visit purpose", Toast.LENGTH_LONG).show();
                }else if (visit_date.equalsIgnoreCase("") || visit_date.equalsIgnoreCase("dd-mm-yyyy")){
                    Toast.makeText(VisitListActivity.this, "Select visit date", Toast.LENGTH_LONG).show();
                }else {
                    dialog.cancel();
                    submitVisitSchedule("",visit_date,message);
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    private void submitVisitSchedule(String visitID, String visit_date, String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e(TAG, "application/json=Bearer"+token+"="+ticketDetailsPojo.getTicketId()+"="+visit_date+"="+message);
        Call<SubmitSuccessResponse> call = apiInterface.setScheduleVisit("application/json", "Bearer " + token, visitID, ticketDetailsPojo.getTicketId(), visit_date,message);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(VisitListActivity.this, VisitListActivity.class);
                        Toast.makeText(VisitListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(VisitListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(VisitListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SubmitSuccessResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(VisitListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getVisitList() {
        Log.e("Ticket ID:", ticketDetailsPojo.getTicketId());
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SiteVisitListResponse> call = apiInterface.getVisitList("application/json", "Bearer " + token,ticketDetailsPojo.getTicketId());
        call.enqueue(new Callback<SiteVisitListResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<SiteVisitListResponse> call, @NotNull Response<SiteVisitListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    visitListData.clear();
                    SiteVisitListResponse siteVisitListResponse = response.body();
                    assert siteVisitListResponse != null;
                    visitListData = siteVisitListResponse.getData().getSiteVisits();
                    Log.e(TAG, String.valueOf(visitListData));

                    if (!visitListData.isEmpty()) {
                        for (SiteVisit obj : visitListData){
                            if (obj.getTsvStatus().equalsIgnoreCase("Scheduled") || obj.getTsvStatus().equalsIgnoreCase("Rescheduled")){
                                addVisitFlg++;
                            }
                        }
                        iv_noDataFound.setVisibility(View.GONE);
                        listAdapter = new VisitListAdapter(VisitListActivity.this, visitListData, VisitListActivity.this);
                        lv_visitlist.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        lv_visitlist.setLayoutManager(mLayoutManager);
                        lv_visitlist.setItemAnimator(new DefaultItemAnimator());
                        lv_visitlist.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }else {
                        lv_visitlist.setVisibility(View.GONE);
                        iv_noDataFound.setVisibility(View.VISIBLE);
                        //Utils.getInstance().showAlert(VisitListActivity.this, "Data not found");
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(VisitListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(VisitListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SiteVisitListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onContactSelected(SiteVisit siteVisit) {

    }

    @SuppressLint("SetTextI18n")
    public void setConfirmed(SiteVisit siteVisit) {
        Utils.getInstance().showYesNOAlert(VisitListActivity.this, getString(R.string.confirmVisit_message), new alertCommunicator() {
            @Override
            public void onClickPositiveBtn() {
                confirmVisit(siteVisit.getTsvId());
            }

            @Override
            public void onClickNegativrBtn() {

            }
        });
    }

    private void confirmVisit(Integer visitID) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e("Receive Params", String.valueOf(visitID));
        Call<SubmitSuccessResponse> call = apiInterface.setConfirmVisit("application/json", "Bearer " + token, visitID);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(VisitListActivity.this, VisitListActivity.class);
                        Toast.makeText(VisitListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(VisitListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(VisitListActivity.this, msg);
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

    @SuppressLint("SetTextI18n")
    public void editVisit(SiteVisit siteVisit) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_visit_schedule);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        Button button_submit = dialog.findViewById(R.id.button_submit);
        EditText et_visit_purpose = dialog.findViewById(R.id.et_visit_purpose);
        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        EditText et_visitDate = dialog.findViewById(R.id.et_visitDate);
        String[] dateTime = siteVisit.getTsvVisitedAt().split(" ");
        int monthNumber1 =0;
        Log.e("Month", dateTime[1]);
        try {
            monthNumber1 = getNumberFromMonthName(dateTime[1], Locale.ENGLISH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        et_visitDate.setText(dateTime[0] + "-" + monthNumber1 + "-" + dateTime[2]);
        et_visit_purpose.setText(siteVisit.getTsvDescription());
        et_visitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(VisitListActivity.this,"0","15", new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        et_visitDate.setText(date);
                    }
                });
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_visit_purpose.getText().toString().trim();
                String visit_date = et_visitDate.getText().toString();
                if (message.equalsIgnoreCase(" ") || message.isEmpty()){
                    Toast.makeText(VisitListActivity.this, "Enter visit purpose", Toast.LENGTH_LONG).show();
                }else if (visit_date.equalsIgnoreCase("") || visit_date.equalsIgnoreCase("dd-mm-yyyy")){
                    Toast.makeText(VisitListActivity.this, "Select visit date", Toast.LENGTH_LONG).show();
                }else {
                    dialog.cancel();
                    submitVisitSchedule(String.valueOf(siteVisit.getTsvId()), visit_date,message);
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void cancelVisit(SiteVisit siteVisit) {
        final Dialog dialog = new Dialog(VisitListActivity.this);
        dialog.setContentView(R.layout.custom_itn_receive_note);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        Button button_add = dialog.findViewById(R.id.button_add);
        EditText et_message = dialog.findViewById(R.id.et_message);
        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        TextView tv_lastlot = dialog.findViewById(R.id.tv_lastlot);
        TextView tv_issuephoto = dialog.findViewById(R.id.tv_issuephoto);
        TextView tv_lable = dialog.findViewById(R.id.tv_lable);
        LinearLayout ll_issuephoto = dialog.findViewById(R.id.ll_issuephoto);
        tv_lastlot.setText("Cancel Visit");
        tv_lable.setText("Cancel Note");

        tv_issuephoto.setVisibility(View.GONE);
        ll_issuephoto.setVisibility(View.GONE);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString();
                if (message.equalsIgnoreCase("")){
                    Toast.makeText(VisitListActivity.this, "Enter message", Toast.LENGTH_LONG).show();
                }else {
                    dialog.cancel();
                    cancelvisit(siteVisit.getTsvId(),message);
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    private void cancelvisit(Integer tsvId, String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e("Receive Params", tsvId+"="+message);
        Call<SubmitSuccessResponse> call = apiInterface.setVisitCancel("application/json", "Bearer " + token, tsvId, message);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        addVisitFlg=0;
                        Intent intent = new Intent(VisitListActivity.this, VisitListActivity.class);
                        Toast.makeText(VisitListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(VisitListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(VisitListActivity.this, msg);
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

    public static int getNumberFromMonthName(String monthName, Locale locale) throws ParseException {

        DateTimeFormatter dtFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtFormatter = DateTimeFormatter.ofPattern("MMM")
                    .withLocale(locale);
        }
        TemporalAccessor temporalAccessor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            temporalAccessor = dtFormatter.parse(monthName);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return temporalAccessor.get(ChronoField.MONTH_OF_YEAR);
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            Intent intent = new Intent(VisitListActivity.this, TicketHandlingUserActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(VisitListActivity.this, TicketHandlingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}