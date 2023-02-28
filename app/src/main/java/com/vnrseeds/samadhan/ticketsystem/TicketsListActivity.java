package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.SoftwareTicketsListAdapter;
import com.vnrseeds.samadhan.adapter.TicketsListAdapter;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.Datum;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketListResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketsListPojo;
import com.vnrseeds.samadhan.pojo.SoftwareTicketsListPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

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

public class TicketsListActivity extends AppCompatActivity implements TicketsListAdapter.TicketsListAdapterListener, SoftwareTicketsListAdapter.SoftwareTicketsListAdapterListener{
    private static final String TAG = "TicketsListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private List<Datum> ticketlistArray = new ArrayList<>();
    private TicketsListAdapter listAdapter;
    private SearchView searchView;
    private RecyclerView lv_ticketslist;
    private ImageButton back_nav;
    private final ArrayList<TicketsListPojo> AssetArray = new ArrayList<>();
    //private Spinner spinner_ticketstatus;
    private RoleResponse roleResponse;

    private ImageButton btn_software;
    private ImageButton btn_hardware;
    private CardView card_hardware;
    private CardView card_software;
    private String serviceType="Hardware";
    private TextView tv_ticketHistory;
    private final ArrayList<SoftwareTicketsListPojo> softwareArray = new ArrayList<>();
    private RecyclerView lv_ticketslist_sw;
    private SoftwareTicketsListAdapter listSoftwareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(TicketsListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        tv_ticketHistory = findViewById(R.id.tv_ticketHistory);
        tv_ticketHistory.setVisibility(View.VISIBLE);
        back_nav = findViewById(R.id.back_nav);
        toolbar_title.setText("Tickets List");
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        searchView = findViewById(R.id.searchView);
        lv_ticketslist = findViewById(R.id.lv_ticketslist);
        //spinner_ticketstatus = findViewById(R.id.spinner_ticketstatus);
        listAdapter = new TicketsListAdapter(this, AssetArray,this);
        lv_ticketslist.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lv_ticketslist.setLayoutManager(mLayoutManager);
        //lv_ticketslist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lv_ticketslist.setItemAnimator(new DefaultItemAnimator());
        lv_ticketslist.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        //mLayoutManager.removeAllViews();

        lv_ticketslist_sw = findViewById(R.id.lv_ticketslist_sw);
        listSoftwareAdapter = new SoftwareTicketsListAdapter(this, softwareArray,this);
        lv_ticketslist_sw.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManagersw = new LinearLayoutManager(getApplicationContext());
        lv_ticketslist_sw.setLayoutManager(mLayoutManagersw);
        lv_ticketslist_sw.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lv_ticketslist_sw.setItemAnimator(new DefaultItemAnimator());
        lv_ticketslist_sw.setAdapter(listSoftwareAdapter);
        listSoftwareAdapter.notifyDataSetChanged();

        btn_software = findViewById(R.id.btn_software);
        btn_hardware = findViewById(R.id.btn_hardware);
        card_hardware = findViewById(R.id.card_hardware);
        card_software = findViewById(R.id.card_software);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(view -> {
            Intent intent = new Intent(TicketsListActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        getTicketList();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                listSoftwareAdapter.getFilter().filter(newText);
                return false;
            }
        });
        btn_hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceType="Hardware";
                card_hardware.setCardBackgroundColor(Color.parseColor("#deddb9"));
                card_software.setCardBackgroundColor(Color.parseColor("#EAE9E9"));
                listAdapter.getFilter().filter("Hardware");

                lv_ticketslist.setVisibility(View.VISIBLE);
                lv_ticketslist_sw.setVisibility(View.GONE);
            }
        });
        btn_software.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceType="Software";
                card_software.setCardBackgroundColor(Color.parseColor("#deddb9"));
                card_hardware.setCardBackgroundColor(Color.parseColor("#EAE9E9"));
                //listAdapter.getFilter().filter("Software");

                lv_ticketslist.setVisibility(View.GONE);
                lv_ticketslist_sw.setVisibility(View.VISIBLE);
            }
        });
        tv_ticketHistory.setOnClickListener(view -> {
            Intent intent = new Intent(TicketsListActivity.this, TicketHistoryListActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void getTicketList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<TicketListResponse> call = apiInterface.getTicketListInfo("application/json", "Bearer "+token, "", "Withdraw,Closed");
        call.enqueue(new Callback<TicketListResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<TicketListResponse> call, @NotNull Response<TicketListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    TicketListResponse ticketListResponse = response.body();
                    assert ticketListResponse != null;
                    ticketlistArray = ticketListResponse.getData();
                    int cnt = 1;
                    int cntsw = 1;
                    if (ticketlistArray.isEmpty()){
                        Utils.getInstance().showAlert(TicketsListActivity.this, "No tickets found");
                    }else {
                        for (Datum obj : ticketlistArray) {
                            if (obj.getTicketServiceType().equalsIgnoreCase("Hardware")) {
                                AssetArray.add(new TicketsListPojo(cnt, obj.getTicketCode(), obj.getRaiseBy(), obj.getPriorityName(), obj.getIssueName(), obj.getIssueName(), "IT", obj.getRaiseBy(), "Software Development", obj.getTicketDescription(), obj.getTicketCurrentStatus(), obj.getTicketRaiseDate(), obj.getAssignTo(), obj.getTicketServiceTypeName(), obj.getTicketServiceType(), obj.getTicketId(), obj.getTicketIsViewed(), obj.getTicketPriorityId(), obj.getTicketUserId(), obj.getTicketIssueIds(), obj.getTicketRaiseBy(), obj.getTicketUserType(), obj.getTicketAssetType(), obj.getTicketAssetAcId(), obj.getTicketServiceTypeId(), obj.getTicketIsWorkInProgress(), obj.getTicketResolveDescription(), obj.getTicketResolveDate(), obj.getTicketEstimatedHandleDate(), obj.getTicketReplyDescription(), obj.getTicketAssetAcName(), obj.getIssueName(), obj.getTicketIssueIdsStr(), obj.getTicketResolveBy(), obj.getTicketIssueOther(), obj.getRaiseBy(), obj.getTicketEstimatedHandleDescription(), obj.getCloseBy(),obj.getTicketCloseDate(),obj.getTicketCloseRating(),obj.getTicketCloseDescription(), obj.getTicketServiceTypeCurrentStatus(), obj.getTicketSiteVisitDescription(), obj.getTicketSiteVisitDate(), obj.getTicketReopenNumber(), obj.getBehalfRaiseBy(), obj.getLocationIsBaseLocation(), obj.getTicketServiceTypeIcon(), obj.getTicketRaiseFiles(), obj.getAssetIsByod(), obj.getTicketModuleId(), obj.getTicketSubModuleId(), obj.getTicketIsAddToAsset(), obj.getTicketIsSiteVisit(), obj.getTicketSiteVisitAt(), obj.getTicketResolveFile(), obj.getModuleName(), obj.getSubModuleName()));
                                listAdapter.notifyDataSetChanged();
                                cnt++;
                            }else {
                                softwareArray.add(new SoftwareTicketsListPojo(cntsw, obj.getTicketCode(), obj.getRaiseBy(), obj.getPriorityName(), obj.getIssueName(), obj.getIssueName(), "IT", obj.getRaiseBy(), "Software Development", obj.getTicketDescription(), obj.getTicketCurrentStatus(), obj.getTicketRaiseDate(), obj.getAssignTo(), obj.getTicketServiceTypeName(), obj.getTicketServiceType(), obj.getTicketRaiseFiles(), obj.getTicketId(), obj.getTicketIsViewed(), obj.getTicketPriorityId(), obj.getTicketUserId(), obj.getTicketIssueIds(), obj.getTicketRaiseBy(), obj.getTicketUserType(), obj.getTicketAssetType(), obj.getTicketAssetAcId(), obj.getTicketServiceTypeId(), obj.getTicketIsWorkInProgress(), obj.getTicketResolveDescription(), obj.getTicketResolveDate(), obj.getTicketEstimatedHandleDate(), obj.getTicketReplyDescription(), obj.getTicketAssetAcName(), obj.getIssueName(), obj.getTicketIssueIdsStr(), obj.getTicketResolveBy(), obj.getTicketIssueOther(), obj.getRaiseBy(), obj.getTicketEstimatedHandleDescription(), obj.getCloseBy(), obj.getTicketCloseDate(), obj.getTicketCloseRating(), obj.getTicketCloseDescription(), obj.getTicketServiceTypeCurrentStatus(), obj.getTicketSiteVisitDescription(), obj.getTicketSiteVisitDate(), obj.getTicketReopenNumber(), obj.getBehalfRaiseBy(), obj.getLocationIsBaseLocation(), obj.getTicketServiceTypeIcon(), obj.getAssetIsByod(), obj.getTicketModuleId(), obj.getTicketSubModuleId(), obj.getTicketIsAddToAsset(), obj.getTicketIsSiteVisit(), obj.getTicketSiteVisitAt(), obj.getTicketResolveFile(), obj.getModuleName(), obj.getSubModuleName()));
                                lv_ticketslist_sw.setAdapter(listSoftwareAdapter);
                                listSoftwareAdapter.notifyDataSetChanged();
                                cntsw++;
                            }
                        }
                        Log.e("Software Array", String.valueOf(softwareArray));
                        Log.e("Asset Array", String.valueOf(AssetArray));
                        /*if (serviceType.equalsIgnoreCase("Hardware")){
                            listAdapter.getFilter().filter("Hardware");
                        }else {
                            listSoftwareAdapter.getFilter().filter("Software");
                        }*/
                        /*AssetArray.add(new TicketsListPojo(1,"0922/0001","Shailendra Kamble","Low","Hardware","Laptop Camera Issue","IT","Pune","Software Development","My laptop camera is not working when connected with WIFI","","Open","09-09-2022 10:10:40","Sourabh"));
                        listAdapter.notifyDataSetChanged();*/
                        //Toast.makeText(TicketsListActivity.this, ticketListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketsListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketsListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<TicketListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onContactSelected(TicketsListPojo ticketsListPojo) {
        if (!ticketsListPojo.getTicketStatus().equalsIgnoreCase("Cancelled")) {
            TicketDetailsPojo ticketDetailsPojo = new TicketDetailsPojo(ticketsListPojo.getSrno(), ticketsListPojo.getTicketNo(), ticketsListPojo.getCustodianName(), ticketsListPojo.getPriority(), ticketsListPojo.getIssueType(), ticketsListPojo.getIssueTitle(), ticketsListPojo.getDepartmentName(), ticketsListPojo.getLocationName(), ticketsListPojo.getSectionName(), ticketsListPojo.getIssueDesc(), ticketsListPojo.getTicketStatus(), ticketsListPojo.getTicketDate(), ticketsListPojo.getAssignTo(), ticketsListPojo.getAssetCategory(), ticketsListPojo.getServiceType(), ticketsListPojo.getIssueImage(), ticketsListPojo.getTicketId(), ticketsListPojo.getTicketIsViewed(), ticketsListPojo.getTicket_priority_id(), ticketsListPojo.getTicket_user_id(), ticketsListPojo.getTicket_issue_ids(), ticketsListPojo.getTicket_raised_by_id(), ticketsListPojo.getTicket_user_type(), ticketsListPojo.getTicket_asset_type(), ticketsListPojo.getTicketAssetCatagoryID(), ticketsListPojo.getTicket_service_type_id(), ticketsListPojo.getTicket_is_work_in_progress(), ticketsListPojo.getTicketResolveDescription(), ticketsListPojo.getTicketResolveDate(), ticketsListPojo.getTicketEstimatedHandleDate(), ticketsListPojo.getIssueName(), ticketsListPojo.getTicketAssetAcName(), ticketsListPojo.getTicketEstimatedHandleDescription(), ticketsListPojo.getTicketIssueOther(), ticketsListPojo.getTicketCloseBy(), ticketsListPojo.getTicketCloseDate(), ticketsListPojo.getTicketCloseRating(), ticketsListPojo.getTicketCloseDescription(), ticketsListPojo.getTicketServiceTypeCurrentStatus(), ticketsListPojo.getRaiseBy(), ticketsListPojo.getTicketSiteVisitDescription(), ticketsListPojo.getTicketSiteVisitDate(), ticketsListPojo.getTicketReopenNumber(), ticketsListPojo.getLocationIsBaseLocation(), ticketsListPojo.getTicketServiceTypeIcon(), ticketsListPojo.getAssetIsByod(), ticketsListPojo.getTicketModuleId(), ticketsListPojo.getTicketSubModuleId(), ticketsListPojo.getTicketIsAddToAsset(), ticketsListPojo.getTicketIsSiteVisit(), ticketsListPojo.getTicketSiteVisitAt(), ticketsListPojo.getTicketResolveFile(), ticketsListPojo.getModuleName(), ticketsListPojo.getSubModuleName());
            ticketDetailsPojo.setTicketIssueIdsStr(ticketsListPojo.getTicketIssueIdsStr());
            SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_TICKET_OBJ, ticketDetailsPojo);
            getTicketInfo(ticketsListPojo.getTicketId());
            roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
            if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size() == 1)) {
                Intent intent = new Intent(TicketsListActivity.this, TicketHandlingUserActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(TicketsListActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void getTicketInfo(String ticketId) {
        Log.e("Ticket ID : ", ticketId);
    }

    public void editTicket(TicketsListPojo ticketsListPojo) {
        //Toast.makeText(TicketsListActivity.this, ticketsListPojo.getTicketNo(), Toast.LENGTH_SHORT).show();
        //TicketDetailsPojo ticketDetailsPojo = new TicketDetailsPojo(ticketsListPojo.getSrno(), ticketsListPojo.getTicketNo(), ticketsListPojo.getCustodianName(), ticketsListPojo.getPriority(), ticketsListPojo.getIssueType(), ticketsListPojo.getIssueTitle(),ticketsListPojo.getDepartmentName(),ticketsListPojo.getLocationName(),ticketsListPojo.getSectionName(),ticketsListPojo.getIssueDesc(),ticketsListPojo.getTicketStatus(),ticketsListPojo.getTicketDate(),ticketsListPojo.getAssignTo(),ticketsListPojo.getAssetCategory(),ticketsListPojo.getServiceType(),ticketsListPojo.getIssueImage(), ticketsListPojo.getTicketId(), ticketsListPojo.getTicketIsViewed(), ticketsListPojo.getTicket_priority_id(), ticketsListPojo.getTicket_user_id(), ticketsListPojo.getTicket_issue_ids(), ticketsListPojo.getTicket_raised_by_id(), ticketsListPojo.getTicket_user_type());
        /*if (ticketsListPojo.getTicket_user_type().equalsIgnoreCase("Other")){
            Utils.getInstance().showAlert(TicketsListActivity.this, "Sevice provider cannot edit other's ticket");
        }else {*/
            //Toast.makeText(TicketsListActivity.this, ticketsListPojo.getTicketNo(), Toast.LENGTH_SHORT).show();
            TicketDetailsPojo ticketDetailsPojo = new TicketDetailsPojo(ticketsListPojo.getSrno(), ticketsListPojo.getTicketNo(), ticketsListPojo.getCustodianName(), ticketsListPojo.getPriority(), ticketsListPojo.getIssueType(), ticketsListPojo.getIssueTitle(),ticketsListPojo.getDepartmentName(),ticketsListPojo.getLocationName(),ticketsListPojo.getSectionName(),ticketsListPojo.getIssueDesc(),ticketsListPojo.getTicketStatus(),ticketsListPojo.getTicketDate(),ticketsListPojo.getAssignTo(),ticketsListPojo.getAssetCategory(),ticketsListPojo.getServiceType(),ticketsListPojo.getIssueImage(), ticketsListPojo.getTicketId(), ticketsListPojo.getTicketIsViewed(), ticketsListPojo.getTicket_priority_id(), ticketsListPojo.getTicket_user_id(), ticketsListPojo.getTicket_issue_ids(), ticketsListPojo.getTicket_raised_by_id(), ticketsListPojo.getTicket_user_type(), ticketsListPojo.getTicket_asset_type(), ticketsListPojo.getTicketAssetCatagoryID(), ticketsListPojo.getTicket_service_type_id(), ticketsListPojo.getTicket_is_work_in_progress(), ticketsListPojo.getTicketResolveDescription(), ticketsListPojo.getTicketResolveDate(), ticketsListPojo.getTicketEstimatedHandleDate(), ticketsListPojo.getIssueName(), ticketsListPojo.getTicketAssetAcName(), ticketsListPojo.getTicketEstimatedHandleDescription(), ticketsListPojo.getTicketIssueOther(), ticketsListPojo.getTicketCloseBy(), ticketsListPojo.getTicketCloseDate(), ticketsListPojo.getTicketCloseRating(), ticketsListPojo.getTicketCloseDescription(), ticketsListPojo.getTicketServiceTypeCurrentStatus(), ticketsListPojo.getRaiseBy(), ticketsListPojo.getTicketSiteVisitDescription(), ticketsListPojo.getTicketSiteVisitDate(), ticketsListPojo.getTicketReopenNumber(), ticketsListPojo.getLocationIsBaseLocation(), ticketsListPojo.getTicketServiceTypeIcon(), ticketsListPojo.getAssetIsByod(), ticketsListPojo.getTicketModuleId(), ticketsListPojo.getTicketSubModuleId(), ticketsListPojo.getTicketIsAddToAsset(), ticketsListPojo.getTicketIsSiteVisit(), ticketsListPojo.getTicketSiteVisitAt(), ticketsListPojo.getTicketResolveFile(), ticketsListPojo.getModuleName(), ticketsListPojo.getSubModuleName());
            ticketDetailsPojo.setTicketIssueIdsStr(ticketsListPojo.getTicketIssueIdsStr());
            SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_TICKET_OBJ, ticketDetailsPojo);
            Intent intent = new Intent(TicketsListActivity.this, EditTicketActivity.class);
            startActivity(intent);
            finish();
        //}
    }

    @SuppressLint("SetTextI18n")
    public void cancelTicket(TicketsListPojo ticketsListPojo) {
        if (!ticketsListPojo.getTicketStatus().equalsIgnoreCase("Diagnosis") && !ticketsListPojo.getTicketStatus().equalsIgnoreCase("Resolved")) {
            final Dialog dialog = new Dialog(TicketsListActivity.this);
            dialog.setContentView(R.layout.custompopup_addnotes);
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
            TextInputLayout til_lable = dialog.findViewById(R.id.til_lable);
            CheckBox cb_show_to_user = dialog.findViewById(R.id.cb_show_to_user);
            tv_lastlot.setText("Withdraw Ticket");
            til_lable.setHint("Withdrawal Reason");
            cb_show_to_user.setVisibility(View.GONE);
            button_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = et_message.getText().toString();
                    if (message.equalsIgnoreCase("")) {
                        Toast.makeText(TicketsListActivity.this, "Enter message", Toast.LENGTH_LONG).show();
                    } else {
                        dialog.cancel();
                        actionCancelTicket("Withdraw", ticketsListPojo.getTicketId(), message);
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
    }

    private void actionCancelTicket(String action, String ticketId, String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTicketCancelInfo("application/json", "Bearer " + token, action, ticketId, message);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(TicketsListActivity.this, TicketsListActivity.class);
                        Toast.makeText(TicketsListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                        //Toast.makeText(TicketsListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketsListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketsListActivity.this, msg);
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
                Toast.makeText(TicketsListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TicketsListActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onContactSelected(SoftwareTicketsListPojo softwareTicketsListPojo) {
        if (!softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Cancelled")) {
            TicketDetailsPojo ticketDetailsPojo = new TicketDetailsPojo(softwareTicketsListPojo.getSrno(), softwareTicketsListPojo.getTicketNo(), softwareTicketsListPojo.getCustodianName(), softwareTicketsListPojo.getPriority(), softwareTicketsListPojo.getIssueType(), softwareTicketsListPojo.getIssueTitle(), softwareTicketsListPojo.getDepartmentName(), softwareTicketsListPojo.getLocationName(), softwareTicketsListPojo.getSectionName(), softwareTicketsListPojo.getIssueDesc(), softwareTicketsListPojo.getTicketStatus(), softwareTicketsListPojo.getTicketDate(), softwareTicketsListPojo.getAssignTo(), softwareTicketsListPojo.getAssetCategory(), softwareTicketsListPojo.getServiceType(), softwareTicketsListPojo.getIssueImage(), softwareTicketsListPojo.getTicketId(), softwareTicketsListPojo.getTicketIsViewed(), softwareTicketsListPojo.getTicket_priority_id(), softwareTicketsListPojo.getTicket_user_id(), softwareTicketsListPojo.getTicket_issue_ids(), softwareTicketsListPojo.getTicket_raised_by_id(), softwareTicketsListPojo.getTicket_user_type(), softwareTicketsListPojo.getTicket_asset_type(), softwareTicketsListPojo.getTicketAssetCatagoryID(), softwareTicketsListPojo.getTicket_service_type_id(), softwareTicketsListPojo.getTicket_is_work_in_progress(), softwareTicketsListPojo.getTicketResolveDescription(), softwareTicketsListPojo.getTicketResolveDate(), softwareTicketsListPojo.getTicketEstimatedHandleDate(), softwareTicketsListPojo.getIssueName(), softwareTicketsListPojo.getTicketAssetAcName(), softwareTicketsListPojo.getTicketEstimatedHandleDescription(), softwareTicketsListPojo.getTicketIssueOther(), softwareTicketsListPojo.getTicketCloseBy(), softwareTicketsListPojo.getTicketCloseDate(), softwareTicketsListPojo.getTicketCloseRating(), softwareTicketsListPojo.getTicketCloseDescription(), softwareTicketsListPojo.getTicketServiceTypeCurrentStatus(), softwareTicketsListPojo.getRaiseBy(), softwareTicketsListPojo.getTicketSiteVisitDescription(), softwareTicketsListPojo.getTicketSiteVisitDate(), softwareTicketsListPojo.getTicketReopenNumber(), softwareTicketsListPojo.getLocationIsBaseLocation(), softwareTicketsListPojo.getTicketServiceTypeIcon(), softwareTicketsListPojo.getAssetIsByod(), softwareTicketsListPojo.getTicketModuleId(), softwareTicketsListPojo.getTicketSubModuleId(), softwareTicketsListPojo.getTicketIsAddToAsset(), softwareTicketsListPojo.getTicketIsSiteVisit(), softwareTicketsListPojo.getTicketSiteVisitAt(), softwareTicketsListPojo.getTicketResolveFile(), softwareTicketsListPojo.getModuleName(), softwareTicketsListPojo.getSubModuleName());
            ticketDetailsPojo.setTicketIssueIdsStr(softwareTicketsListPojo.getTicketIssueIdsStr());
            SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_TICKET_OBJ, ticketDetailsPojo);
            roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
            if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size() == 1)) {
                Intent intent = new Intent(TicketsListActivity.this, TicketHandlingUserActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(TicketsListActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void editTicketSW(SoftwareTicketsListPojo softwareTicketsListPojo) {
        //Toast.makeText(TicketsListActivity.this, softwareTicketsListPojo.getTicketNo(), Toast.LENGTH_SHORT).show();
        TicketDetailsPojo ticketDetailsPojo = new TicketDetailsPojo(softwareTicketsListPojo.getSrno(), softwareTicketsListPojo.getTicketNo(), softwareTicketsListPojo.getCustodianName(), softwareTicketsListPojo.getPriority(), softwareTicketsListPojo.getIssueType(), softwareTicketsListPojo.getIssueTitle(),softwareTicketsListPojo.getDepartmentName(),softwareTicketsListPojo.getLocationName(),softwareTicketsListPojo.getSectionName(),softwareTicketsListPojo.getIssueDesc(),softwareTicketsListPojo.getTicketStatus(),softwareTicketsListPojo.getTicketDate(),softwareTicketsListPojo.getAssignTo(),softwareTicketsListPojo.getAssetCategory(),softwareTicketsListPojo.getServiceType(),softwareTicketsListPojo.getIssueImage(), softwareTicketsListPojo.getTicketId(), softwareTicketsListPojo.getTicketIsViewed(), softwareTicketsListPojo.getTicket_priority_id(), softwareTicketsListPojo.getTicket_user_id(), softwareTicketsListPojo.getTicket_issue_ids(), softwareTicketsListPojo.getTicket_raised_by_id(), softwareTicketsListPojo.getTicket_user_type(), softwareTicketsListPojo.getTicket_asset_type(), softwareTicketsListPojo.getTicketAssetCatagoryID(), softwareTicketsListPojo.getTicket_service_type_id(), softwareTicketsListPojo.getTicket_is_work_in_progress(), softwareTicketsListPojo.getTicketResolveDescription(), softwareTicketsListPojo.getTicketResolveDate(), softwareTicketsListPojo.getTicketEstimatedHandleDate(), softwareTicketsListPojo.getIssueName(), softwareTicketsListPojo.getTicketAssetAcName(), softwareTicketsListPojo.getTicketEstimatedHandleDescription(), softwareTicketsListPojo.getTicketIssueOther(), softwareTicketsListPojo.getTicketCloseBy(), softwareTicketsListPojo.getTicketCloseDate(), softwareTicketsListPojo.getTicketCloseRating(), softwareTicketsListPojo.getTicketCloseDescription(), softwareTicketsListPojo.getTicketServiceTypeCurrentStatus(), softwareTicketsListPojo.getRaiseBy(), softwareTicketsListPojo.getTicketSiteVisitDescription(), softwareTicketsListPojo.getTicketSiteVisitDate(), softwareTicketsListPojo.getTicketReopenNumber(), softwareTicketsListPojo.getLocationIsBaseLocation(), softwareTicketsListPojo.getTicketServiceTypeIcon(), softwareTicketsListPojo.getAssetIsByod(), softwareTicketsListPojo.getTicketModuleId(), softwareTicketsListPojo.getTicketSubModuleId(), softwareTicketsListPojo.getTicketIsAddToAsset(), softwareTicketsListPojo.getTicketIsSiteVisit(), softwareTicketsListPojo.getTicketSiteVisitAt(), softwareTicketsListPojo.getTicketResolveFile(), softwareTicketsListPojo.getModuleName(), softwareTicketsListPojo.getSubModuleName());
        ticketDetailsPojo.setTicketIssueIdsStr(softwareTicketsListPojo.getTicketIssueIdsStr());
        SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_TICKET_OBJ, ticketDetailsPojo);
        Intent intent = new Intent(TicketsListActivity.this, EditTicketActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    public void cancelTicketSW(SoftwareTicketsListPojo softwareTicketsListPojo) {
        final Dialog dialog = new Dialog(TicketsListActivity.this);
        dialog.setContentView(R.layout.custompopup_addnotes);
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
        TextInputLayout til_lable = dialog.findViewById(R.id.til_lable);
        CheckBox cb_show_to_user = dialog.findViewById(R.id.cb_show_to_user);
        tv_lastlot.setText("Withdraw Ticket");
        til_lable.setHint("Withdrawal Reason");
        cb_show_to_user.setVisibility(View.GONE);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString().trim();
                if (message.equalsIgnoreCase("")){
                    Toast.makeText(TicketsListActivity.this, "Enter message", Toast.LENGTH_LONG).show();
                }else {
                    dialog.cancel();
                    actionCancelTicket("Withdraw",softwareTicketsListPojo.getTicketId(),message);
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
}