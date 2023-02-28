package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.AssignListAdapter;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.parser.ticketviewparser.AssignLog;
import com.vnrseeds.samadhan.parser.ticketviewparser.TicketViewResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignHistoryActivity extends AppCompatActivity {

    private static final String TAG = "AssignHistoryActivity";
    private CustomProgressDialogue customProgressDialogue;
    private ImageButton back_nav;
    private String token;
    private RecyclerView lv_assignlist;
    private TicketDetailsPojo ticketDetailsPojo;
    private RoleResponse roleResponse;
    private List<AssignLog> ticketAssignArray;
    private AssignListAdapter ticketViewAdapter;
    private TextView tv_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_history);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(AssignHistoryActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        toolbar_title.setText("Assign History");
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        TextView tv_ticketdate = findViewById(R.id.tv_ticketdate);
        TextView tv_ticketno = findViewById(R.id.tv_ticketno);
        TextView tv_ticket_title = findViewById(R.id.tv_ticket_title);
        TextView tv_priority = findViewById(R.id.tv_priority);
        tv_more = findViewById(R.id.tv_more);

        //searchView = findViewById(R.id.searchView);
        lv_assignlist = findViewById(R.id.lv_assignlist);
        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);

        tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
        tv_ticketno.setText(ticketDetailsPojo.getTicketNo());
        if (ticketDetailsPojo.getIssueTitle()!=null){
            if (ticketDetailsPojo.getIssueTitle().length()>30) {
                tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle().substring(0, 30) + "...");
            }else {
                tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
            }
            //tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
        }else {
            tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
        }
        tv_priority.setText(ticketDetailsPojo.getPriority());

        if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
            tv_priority.setTextColor(Color.parseColor("#FE5247"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
            tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
            tv_priority.setTextColor(Color.parseColor("#FF8744"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
            tv_priority.setTextColor(Color.parseColor("#8FFF36"));
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
                    Intent intent = new Intent(AssignHistoryActivity.this, TicketHandlingUserActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(AssignHistoryActivity.this, TicketHandlingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        tv_more.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AssignHistoryActivity.this);
                dialog.setContentView(R.layout.custompopup_ticket_details);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();

                ImageView iv_close = dialog.findViewById(R.id.iv_close);
                TextView tv_ticketID = dialog.findViewById(R.id.tv_ticketID);
                TextView tv_ticketdate = dialog.findViewById(R.id.tv_ticketdate);
                TextView tv_priority = dialog.findViewById(R.id.tv_priority);
                TextView tv_ticket_title = dialog.findViewById(R.id.tv_ticket_title);
                TextView tv_ticket_desc = dialog.findViewById(R.id.tv_ticket_desc);
                TextView tv_custodian = dialog.findViewById(R.id.tv_custodian);
                TextView tv_ticketStatus = dialog.findViewById(R.id.tv_ticketStatus);
                TextView tv_category = dialog.findViewById(R.id.tv_category);
                TextView tv_assignto = dialog.findViewById(R.id.tv_assignto);
                TextView tv_astype = dialog.findViewById(R.id.tv_astype);
                tv_ticketID.setText("Ticket "+ticketDetailsPojo.getTicketNo());
                tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
                tv_priority.setText(ticketDetailsPojo.getPriority());
                if (ticketDetailsPojo.getIssueTitle()!=null){
                    tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
                }else {
                    tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
                }
                tv_ticket_desc.setText(ticketDetailsPojo.getIssueDesc());
                tv_custodian.setText(ticketDetailsPojo.getCustodianName());
                tv_ticketStatus.setText(ticketDetailsPojo.getTicketStatus());
                tv_category.setText(ticketDetailsPojo.getAssetCategory());
                tv_astype.setText(ticketDetailsPojo.getServiceType());
                tv_assignto.setText(ticketDetailsPojo.getAssignTo());

                if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
                    tv_priority.setTextColor(Color.parseColor("#FE5247"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
                    tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
                    tv_priority.setTextColor(Color.parseColor("#FF8744"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
                    tv_priority.setTextColor(Color.parseColor("#8FFF36"));
                }

                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
        getAssignList();
    }

    private void getAssignList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<TicketViewResponse> call = apiInterface.getTicketViewInfo("application/json", "Bearer " + token, ticketDetailsPojo.getTicketId());
        call.enqueue(new Callback<TicketViewResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<TicketViewResponse> call, @NonNull Response<TicketViewResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    TicketViewResponse ticketViewResponse = response.body();
                    assert ticketViewResponse != null;
                    ticketAssignArray = ticketViewResponse.getData().getAssignLogs();
                    ticketViewAdapter = new AssignListAdapter(AssignHistoryActivity.this, ticketAssignArray);
                    lv_assignlist.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    lv_assignlist.setLayoutManager(mLayoutManager);
                    lv_assignlist.setItemAnimator(new DefaultItemAnimator());
                    lv_assignlist.setAdapter(ticketViewAdapter);
                    ticketViewAdapter.notifyDataSetChanged();
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AssignHistoryActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AssignHistoryActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TicketViewResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(AssignHistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            Intent intent = new Intent(AssignHistoryActivity.this, TicketHandlingUserActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(AssignHistoryActivity.this, TicketHandlingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}