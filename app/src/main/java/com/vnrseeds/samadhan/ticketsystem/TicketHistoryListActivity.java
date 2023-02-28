package com.vnrseeds.samadhan.ticketsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.TicketsHistoryListAdapter;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.Datum;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketListResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketsListPojo;
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

public class TicketHistoryListActivity extends AppCompatActivity implements TicketsHistoryListAdapter.TicketsHistoryListAdapterListener{
    private static final String TAG = "TicketHistoryListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private List<Datum> ticketlistArray = new ArrayList<>();
    private TicketsHistoryListAdapter listAdapter;
    private SearchView searchView;
    private RecyclerView lv_ticketslist;
    private ImageButton back_nav;
    private final ArrayList<TicketsListPojo> AssetArray = new ArrayList<>();
    private RoleResponse roleResponse;

    private ImageButton btn_software;
    private ImageButton btn_hardware;
    private CardView card_hardware;
    private CardView card_software;
    private String serviceType="Hardware";
    private TextView tv_ticketHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(TicketHistoryListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        toolbar_title.setText("Tickets History");
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        searchView = findViewById(R.id.searchView);

        btn_software = findViewById(R.id.btn_software);
        btn_hardware = findViewById(R.id.btn_hardware);
        card_hardware = findViewById(R.id.card_hardware);
        card_software = findViewById(R.id.card_software);
        searchView = findViewById(R.id.searchView);
        lv_ticketslist = findViewById(R.id.lv_ticketslist);
        //spinner_ticketstatus = findViewById(R.id.spinner_ticketstatus);
        listAdapter = new TicketsHistoryListAdapter(this, AssetArray,this);
        lv_ticketslist.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lv_ticketslist.setLayoutManager(mLayoutManager);
        //lv_ticketslist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lv_ticketslist.setItemAnimator(new DefaultItemAnimator());
        lv_ticketslist.setAdapter(listAdapter);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(view -> {
            Intent intent = new Intent(TicketHistoryListActivity.this, TicketsListActivity.class);
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
            }
        });
        btn_software.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceType="Software";
                card_software.setCardBackgroundColor(Color.parseColor("#deddb9"));
                card_hardware.setCardBackgroundColor(Color.parseColor("#EAE9E9"));
                listAdapter.getFilter().filter("Software");
            }
        });
    }

    private void getTicketList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<TicketListResponse> call = apiInterface.getTicketListInfo("application/json", "Bearer "+token, "Withdraw,Closed", "");
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
                    if (ticketlistArray.isEmpty()){
                        Utils.getInstance().showAlert(TicketHistoryListActivity.this, "No tickets found");
                    }else {
                        for (Datum obj : ticketlistArray) {
                            AssetArray.add(new TicketsListPojo(cnt,obj.getTicketCode(),obj.getRaiseBy(),obj.getPriorityName(),obj.getIssueName(),obj.getIssueName(),"IT","Pune","Software Development",obj.getTicketDescription(), obj.getTicketCurrentStatus(),obj.getTicketRaiseDate(),obj.getAssignTo(), obj.getTicketServiceTypeName(), obj.getTicketServiceType(), obj.getTicketId(), obj.getTicketIsViewed(), obj.getTicketPriorityId(), obj.getTicketUserId(), obj.getTicketIssueIds(), obj.getTicketRaiseBy(), obj.getTicketUserType(), obj.getTicketAssetType(), obj.getTicketAssetAcId(), obj.getTicketServiceTypeId(), obj.getTicketIsWorkInProgress(), obj.getTicketResolveDescription(), obj.getTicketResolveDate(), obj.getTicketEstimatedHandleDate(), obj.getTicketReplyDescription(), obj.getTicketAssetAcName(), obj.getIssueName(), obj.getTicketIssueIdsStr(), obj.getResolveBy(), obj.getTicketIssueOther(), obj.getRaiseBy(), obj.getTicketEstimatedHandleDescription(), obj.getCloseBy(),obj.getTicketCloseDate(),obj.getTicketCloseRating(),obj.getTicketCloseDescription(), obj.getTicketServiceTypeCurrentStatus(), obj.getTicketSiteVisitDescription(), obj.getTicketSiteVisitDate(), obj.getTicketReopenNumber(), obj.getBehalfRaiseBy(), obj.getLocationIsBaseLocation(), obj.getTicketServiceTypeIcon(), obj.getTicketRaiseFiles(), obj.getAssetIsByod(), obj.getTicketModuleId(), obj.getTicketSubModuleId(), obj.getTicketIsAddToAsset(), obj.getTicketIsSiteVisit(), obj.getTicketSiteVisitAt(), obj.getTicketResolveFile(), obj.getModuleName(), obj.getSubModuleName()));
                            listAdapter.notifyDataSetChanged();
                            cnt++;
                        }
                        if (serviceType.equalsIgnoreCase("Hardware")){
                            listAdapter.getFilter().filter("Hardware");
                        }else {
                            listAdapter.getFilter().filter("Software");
                        }
                        /*AssetArray.add(new TicketsListPojo(1,"0922/0001","Shailendra Kamble","Low","Hardware","Laptop Camera Issue","IT","Pune","Software Development","My laptop camera is not working when connected with WIFI","","Open","09-09-2022 10:10:40","Sourabh"));
                        listAdapter.notifyDataSetChanged();*/
                        //Toast.makeText(TicketHistoryListActivity.this, ticketListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketHistoryListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketHistoryListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
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
            roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
            if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size() == 1)) {
                Intent intent = new Intent(TicketHistoryListActivity.this, TicketHandlingUserActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(TicketHistoryListActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TicketHistoryListActivity.this, TicketsListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}