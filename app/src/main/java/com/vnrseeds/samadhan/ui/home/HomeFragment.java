package com.vnrseeds.samadhan.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.HomeAssetListAdapter;
import com.vnrseeds.samadhan.adapter.HomeSoftwareListAdapter;
import com.vnrseeds.samadhan.adapter.HomeTicketListAdapter;
import com.vnrseeds.samadhan.adapter.NoticeListAdapter;
import com.vnrseeds.samadhan.addassetforms.DeployAssetListActivity;
import com.vnrseeds.samadhan.notifications.NotificationDetailsActivity;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.noticeparser.NoticeResponse;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.ticketassetparser.TicketAssetListResponse;
import com.vnrseeds.samadhan.parser.ticketassetparser.TicketSPStatusSummary;
import com.vnrseeds.samadhan.parser.ticketassetparser.TicketStatusSummary;
import com.vnrseeds.samadhan.parser.ticketassetparser.UserApplication;
import com.vnrseeds.samadhan.parser.ticketassetparser.UserAsset;
import com.vnrseeds.samadhan.parser.ticketslistparser.Datum;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketListResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketsListPojo;
import com.vnrseeds.samadhan.pojo.AssetListPojo;
import com.vnrseeds.samadhan.pojo.NoticeListPojo;
import com.vnrseeds.samadhan.pojo.NotificationDetailsPojo;
import com.vnrseeds.samadhan.pojo.SoftwareListPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.ticketsystem.AddBYODAssetActivity;
import com.vnrseeds.samadhan.ticketsystem.RaiseTicketActivity;
import com.vnrseeds.samadhan.ticketsystem.TicketAssetListActivity;
import com.vnrseeds.samadhan.ticketsystem.TicketsListActivity;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements HomeTicketListAdapter.TicketsListAdapterListener,HomeAssetListAdapter.HomeAssetListAdapterListener,HomeSoftwareListAdapter.HomeSoftwareListAdapterListener, NoticeListAdapter.NoticeAdapterListener{

    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private List<Object> roleList = new ArrayList<>();
    private LinearLayout ll_first;
    private List<Datum> ticketlistArray;
    private final ArrayList<TicketsListPojo> AssetArray = new ArrayList<>();
    private HomeTicketListAdapter listAdapter;
    private RecyclerView lv_ticketslist;
    private TextView tv_tickets_in_progress;
    private RecyclerView rv_harwarelist;
    private RecyclerView rv_softwarelist;
    private HomeAssetListAdapter assetAdapter;
    private HomeSoftwareListAdapter softwareListAdapter;
    private ArrayList<UserAsset> assetsdata = new ArrayList<>();
    private List<UserApplication> softwaredata = new ArrayList<>();
    private final String ticketFor="Self";
    private String raisedByID;
    private TextView tv_assetCount;
    private TextView tv_softwareCount;
    private final ArrayList<AssetListPojo> assetListData = new ArrayList<>();
    private final List<SoftwareListPojo> softwareListData = new ArrayList<>();
    private CardView cv_deployAsset;
    private TextView ticket_analysis;
    private CardView card_allChart;
    private TextView ticket_stat;
    private PieChart piechart_allTickets;
    private PieChart piechart;
    private TextView tv_openAll;
    private TextView tv_assignAll;
    private TextView tv_diagnosisAll;
    private TextView tv_resolveAll;
    private TextView tv_diagnosis;
    private TextView tv_resolve;
    private TextView tv_assign;
    private CardView cardMyChart;
    private List<com.vnrseeds.samadhan.parser.noticeparser.Datum> noticeList=new ArrayList<>();
    private NoticeListAdapter noticelistAdapter;
    private RecyclerView lv_noticelist;
    private CardView cv_byod;
    private int byodCNT=0;
    private FragmentActivity context;
    private ArrayList<NoticeListPojo> noticeListData=new ArrayList<>();
    private User userData;

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences.getInstance(getContext());
        Utils.getInstance(getContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(getActivity());
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        //final CardView cv_addasset = root.findViewById(R.id.cv_addasset);
        //final ImageView btn_addasset = root.findViewById(R.id.btn_addasset);
        //final ImageView btn_editasset = root.findViewById(R.id.btn_editasset);
        tv_tickets_in_progress = root.findViewById(R.id.tv_tickets_in_progress);
        final ImageView btn_ticketraise = root.findViewById(R.id.btn_ticketraise);
        final ImageView btn_ticketlist = root.findViewById(R.id.btn_ticketlist);

        //@SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton btn_myasset = root.findViewById(R.id.btn_myasset);
        //ll_first = root.findViewById(R.id.ll_first);
        lv_ticketslist = root.findViewById(R.id.lv_ticketslist);
        rv_harwarelist = root.findViewById(R.id.rv_harwarelist);
        rv_softwarelist = root.findViewById(R.id.rv_softwarelist);
        tv_assetCount = root.findViewById(R.id.tv_assetCount);
        tv_softwareCount = root.findViewById(R.id.tv_softwareCount);
        cv_deployAsset = root.findViewById(R.id.cv_deployAsset);
        cv_byod = root.findViewById(R.id.cv_byod);
        ticket_analysis = root.findViewById(R.id.ticket_analysis);
        card_allChart = root.findViewById(R.id.card_allChart);
        cardMyChart = root.findViewById(R.id.cardMyChart);
        ticket_stat = root.findViewById(R.id.ticket_stat);
        piechart_allTickets = root.findViewById(R.id.piechart_allTickets);
        piechart = root.findViewById(R.id.piechart);
        tv_openAll = root.findViewById(R.id.tv_openAll);
        tv_assignAll = root.findViewById(R.id.tv_assignAll);
        tv_diagnosisAll = root.findViewById(R.id.tv_diagnosisAll);
        tv_resolveAll = root.findViewById(R.id.tv_resolveAll);
        tv_diagnosis = root.findViewById(R.id.tv_diagnosis);
        tv_resolve = root.findViewById(R.id.tv_resolve);
        tv_assign = root.findViewById(R.id.tv_assign);
        lv_noticelist = root.findViewById(R.id.lv_noticelist);

        context = getActivity();

        listAdapter = new HomeTicketListAdapter(getContext(), AssetArray,this);
        lv_ticketslist.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        lv_ticketslist.setLayoutManager(mLayoutManager);
        //lv_ticketslist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lv_ticketslist.setItemAnimator(new DefaultItemAnimator());
        lv_ticketslist.setAdapter(listAdapter);

        assetAdapter = new HomeAssetListAdapter(getActivity(), assetListData,this);
        rv_harwarelist.setHasFixedSize(true);
        //rv_harwarelist.setLayoutManager(new LinearLayoutManager(getContext()));
        //lv_assetlist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //rv_harwarelist.setItemAnimator(new DefaultItemAnimator());
        rv_harwarelist.setAdapter(assetAdapter);

        softwareListAdapter = new HomeSoftwareListAdapter(getActivity(), softwareListData,this);
        rv_softwarelist.setHasFixedSize(true);
        //rv_softwarelist.setLayoutManager(new LinearLayoutManager(getContext()));
        //rv_softwarelist.setItemAnimator(new DefaultItemAnimator());
        rv_softwarelist.setAdapter(softwareListAdapter);

        noticelistAdapter = new NoticeListAdapter(getActivity(), noticeListData, this);
        lv_noticelist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        lv_noticelist.setLayoutManager(llm);
        lv_noticelist.setAdapter(noticelistAdapter);
        noticelistAdapter.notifyDataSetChanged();

        userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        raisedByID = userData.getUser_id();
        //Log.e("BOYD", userData.getUserIsByod());
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        getRoleData();
        getNotice();
        getTicketList();
        getAssetList();

        btn_ticketraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getActivity(), TicketAssetListActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(getActivity(), TicketAssetListActivity.class);
                startActivity(intent);
            }
        });

        btn_ticketlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketsListActivity.class);
                startActivity(intent);
            }
        });

        cv_deployAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeployAssetListActivity.class);
                startActivity(intent);
            }
        });

        cv_byod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddBYODAssetActivity.class);
                intent.putExtra("byodCNT", String.valueOf(byodCNT));
                startActivity(intent);
            }
        });

        return root;
    }

    private void getNotice() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e("NoticeList:", token);
        Call<NoticeResponse> call = apiInterface.getNotice("application/json", "Bearer "+token);
        call.enqueue(new Callback<NoticeResponse>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NotNull Call<NoticeResponse> call, @NotNull Response<NoticeResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    NoticeResponse noticeResponse = response.body();
                    assert noticeResponse != null;
                    noticeList = noticeResponse.getData();
                    Log.e("NoticeList:", String.valueOf(noticeList));
                    int cnt = 1;
                    if (!noticeList.isEmpty()){
                        for (com.vnrseeds.samadhan.parser.noticeparser.Datum obj:noticeList){
                            noticeListData.add(new NoticeListPojo(obj.getNotificationId(),obj.getNotificationSubject(),obj.getNotificationMessage(),obj.getNotificationAffectedFrom(),obj.getNotificationAffectedTo(),obj.getNotificationCreatedBy(),obj.getNotificationCreatedAt(),obj.getNotificationIsViewed()));
                            noticelistAdapter.notifyDataSetChanged();
                            cnt++;
                        }
                        /*lv_noticelist.setAdapter(noticelistAdapter);
                        noticelistAdapter.notifyDataSetChanged();*/
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(getActivity(), msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<NoticeResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getAssetList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<TicketAssetListResponse> call = apiInterface.getTicketRaiseDataInfo("application/json", "Bearer " + token, ticketFor, raisedByID);
        call.enqueue(new Callback<TicketAssetListResponse>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NotNull Call<TicketAssetListResponse> call, @NotNull Response<TicketAssetListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    assetsdata.clear();
                    softwaredata.clear();
                    TicketAssetListResponse ticketAssetListResponse = response.body();
                    assert ticketAssetListResponse != null;
                    assetsdata = ticketAssetListResponse.getData().getUserAssets();
                    //assetAdapter.notifyDataSetChanged();
                    if (assetsdata.isEmpty()){
                        //Utils.getInstance().showAlert(TicketAssetListActivity.this, "No assets found");
                    }else {
                        int cnt = 1;
                        RoleResponse roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
                        for (UserAsset obj:assetsdata){
                            if (obj.getAssetIsByod().equalsIgnoreCase("1")){
                                byodCNT++;
                            }
                            assetListData.add(new AssetListPojo(cnt,obj.getAssetId(),obj.getAsset_ac_id(),obj.getAssetCode(),obj.getAssetType(),obj.getBrandName(),obj.getModelName(),obj.getAssetSerialNo(),obj.getAssetInstallationDate(),obj.getAcName(),obj.getAcIcon(),obj.getLocationName(),obj.getDepartmentName(),obj.getDsName(),obj.getAssetCustodian(), obj.getAcCode(), obj.getAssetIsByod(), obj.getTicketIsActive()));
                            assetAdapter.notifyDataSetChanged();
                            cnt++;
                        }
                        tv_assetCount.setText(String.valueOf(assetsdata.size()));

                        if (byodCNT>0 && (roleResponse.getData().isEmpty() || !roleResponse.getData().contains("HARDWARE_ENGINEER"))){
                            cv_byod.setVisibility(View.GONE);
                        }else{
                            if (userData.getUserIsByod()!=null) {
                                if (userData.getUserIsByod().equalsIgnoreCase("0") && !roleResponse.getData().contains("HARDWARE_ENGINEER")) {
                                    cv_byod.setVisibility(View.GONE);
                                } else {
                                    cv_byod.setVisibility(View.VISIBLE);
                                }
                            }

                        }

                        Log.e("BYOD Assets", String.valueOf(byodCNT));
                    }

                    softwaredata = ticketAssetListResponse.getData().getUserApplications();
                    //softwareListAdapter.notifyDataSetChanged();
                    if (softwaredata.isEmpty()){
                        //Utils.getInstance().showAlert(TicketAssetListActivity.this, "No applications found");
                    }else {
                        for (UserApplication obj:softwaredata){
                            softwareListData.add(new SoftwareListPojo(obj.getApplicationId(),obj.getApplicationName(), obj.getApplicationIcon()));
                            softwareListAdapter.notifyDataSetChanged();
                        }
                        tv_softwareCount.setText(String.valueOf(softwaredata.size()));
                    }

                    TicketStatusSummary ticketStatusSummary = ticketAssetListResponse.getData().getTicketStatusSummary();

                    tv_openAll.setText("Not Assigned("+ticketStatusSummary.getOpen()+")");
                    tv_assignAll.setText("Assign("+ticketStatusSummary.getAssign()+")");
                    tv_diagnosisAll.setText("Diagnosis("+ticketStatusSummary.getDiagnosis()+")");
                    tv_resolveAll.setText("Resolve("+ticketStatusSummary.getResolve()+")");

                    //Set the data and color to the pie chart
                    piechart_allTickets.addPieSlice(new PieModel("Not Assigned", Integer.parseInt(ticketStatusSummary.getOpen()),
                                    Color.parseColor("#49454F")));

                    piechart_allTickets.addPieSlice(
                            new PieModel(
                                    "Assign",
                                    Integer.parseInt(ticketStatusSummary.getAssign()),
                                    Color.parseColor("#C3C148")));
                    piechart_allTickets.addPieSlice(
                            new PieModel(
                                    "Diagnosis",
                                    Integer.parseInt(ticketStatusSummary.getDiagnosis()),
                                    Color.parseColor("#EAEAD2")));
                    piechart_allTickets.addPieSlice(
                            new PieModel(
                                    "Resolved",
                                    Integer.parseInt(ticketStatusSummary.getResolve()),
                                    Color.parseColor("#DDDB91")));

                    // To animate the pie chart
                    piechart_allTickets.setInnerValueString(String.valueOf(Integer.parseInt(ticketStatusSummary.getOpen())+Integer.parseInt(ticketStatusSummary.getAssign())+Integer.parseInt(ticketStatusSummary.getDiagnosis())+Integer.parseInt(ticketStatusSummary.getResolve())));
                    piechart_allTickets.setInnerValueColor(Color.parseColor("#49454F"));
                    piechart_allTickets.setUseInnerPadding(true);
                    piechart_allTickets.setUseInnerValue(true);
                    piechart_allTickets.setInnerValueSize(64);
                    piechart_allTickets.startAnimation();

                    TicketSPStatusSummary ticketSPStatusSummary = ticketAssetListResponse.getData().getTicketSPStatusSummary();
                    tv_diagnosis.setText("Diagnosis("+ticketSPStatusSummary.getDiagnosis()+")");
                    tv_resolve.setText("Resolve("+ticketSPStatusSummary.getResolve()+")");
                    tv_assign.setText("Assign("+ticketSPStatusSummary.getAssign()+")");

                    //Set the data and color to the pie chart
                    piechart.addPieSlice(
                            new PieModel(
                                    "Assign",
                                    Integer.parseInt(ticketSPStatusSummary.getAssign()),
                                    Color.parseColor("#C3C148")));
                    piechart.addPieSlice(
                            new PieModel(
                                    "Diagnosis",
                                    Integer.parseInt(ticketSPStatusSummary.getDiagnosis()),
                                    Color.parseColor("#EAEAD2")));
                    piechart.addPieSlice(
                            new PieModel(
                                    "Resolved",
                                    Integer.parseInt(ticketSPStatusSummary.getResolve()),
                                    Color.parseColor("#DDDB91")));

                    // To animate the pie chart
                    piechart.setInnerValueString(String.valueOf(Integer.parseInt(ticketSPStatusSummary.getAssign())+Integer.parseInt(ticketSPStatusSummary.getResolve())+Integer.parseInt(ticketSPStatusSummary.getDiagnosis())));
                    piechart.setInnerValueColor(Color.parseColor("#49454F"));
                    piechart.setUseInnerPadding(true);
                    piechart.setUseInnerValue(true);
                    piechart.setInnerValueSize(64);
                    piechart.startAnimation();
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(getActivity(), msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<TicketAssetListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getRoleData() {
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<RoleResponse> call = apiInterface.getRoleListInfo("application/json", "Bearer " + token);
        call.enqueue(new Callback<RoleResponse>() {

            @Override
            public void onResponse(@NonNull Call<RoleResponse> call, @NonNull Response<RoleResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    RoleResponse roleResponse = response.body();
                    assert roleResponse != null;
                    roleList = roleResponse.getData();
                    Log.e(TAG, String.valueOf(roleList));
                    SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_ROLES_OBJ, roleResponse);
                    if ((roleList.contains("CUSTODIAN") && roleList.size()==1) || roleList.isEmpty()){
                        cv_deployAsset.setVisibility(View.GONE);
                        card_allChart.setVisibility(View.GONE);
                        cardMyChart.setVisibility(View.GONE);
                        ticket_analysis.setVisibility(View.GONE);
                        lv_ticketslist.setVisibility(View.VISIBLE);
                        ticket_stat.setVisibility(View.VISIBLE);
                        tv_tickets_in_progress.setVisibility(View.VISIBLE);
                        //navigationView.getMenu().findItem(R.id.nav_pushNotification).setVisible(false);
                    }else {
                        cv_deployAsset.setVisibility(View.VISIBLE);
                        card_allChart.setVisibility(View.VISIBLE);
                        cardMyChart.setVisibility(View.VISIBLE);
                        ticket_analysis.setVisibility(View.VISIBLE);
                        lv_ticketslist.setVisibility(View.GONE);
                        ticket_stat.setVisibility(View.GONE);
                        tv_tickets_in_progress.setVisibility(View.GONE);
                        //navigationView.getMenu().findItem(R.id.nav_pushNotification).setVisible(true);
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(getActivity(), msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RoleResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getTicketList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<TicketListResponse> call = apiInterface.getTicketListInfo("application/json", "Bearer "+token, "", "Withdraw,Closed");
        call.enqueue(new Callback<TicketListResponse>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NotNull Call<TicketListResponse> call, @NotNull Response<TicketListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    TicketListResponse ticketListResponse = response.body();
                    assert ticketListResponse != null;
                    ticketlistArray = ticketListResponse.getData();
                    int cnt = 1;
                    if (ticketlistArray.isEmpty()){
                        tv_tickets_in_progress.setText("0 tickets in progress");
                        //Utils.getInstance().showAlert(getActivity(), "No tickets found");
                    }else {
                        for (Datum obj : ticketlistArray) {
                            if (!obj.getTicketStatus().equalsIgnoreCase("Closed")) {
                                AssetArray.add(new TicketsListPojo(cnt, obj.getTicketCode(), obj.getRaiseBy(), obj.getPriorityName(), obj.getIssueName(), obj.getIssueName(), "IT", "Pune", "Software Development", obj.getTicketDescription(), obj.getTicketCurrentStatus(), obj.getTicketRaiseDate(), obj.getAssignTo(), obj.getTicketServiceTypeName(), obj.getTicketServiceType(), obj.getTicketId(), obj.getTicketIsViewed(), obj.getTicketPriorityId(), obj.getTicketUserId(), obj.getTicketIssueIds(), obj.getTicketRaiseBy(), obj.getTicketUserType(), obj.getTicketAssetType(), obj.getTicketAssetAcId(), obj.getTicketServiceTypeId(), obj.getTicketIsWorkInProgress(), obj.getTicketResolveDescription(), obj.getTicketResolveDate(), obj.getTicketEstimatedHandleDate(), obj.getTicketReplyDescription(), obj.getTicketAssetAcName(), obj.getIssueName(), obj.getTicketIssueIdsStr(), obj.getTicketResolveBy(), obj.getTicketIssueOther(), obj.getRaiseBy(), obj.getTicketEstimatedHandleDescription(), obj.getTicketCloseBy(), obj.getTicketCloseDate(), obj.getTicketCloseRating(), obj.getTicketCloseDescription(), obj.getTicketServiceTypeCurrentStatus(), obj.getTicketSiteVisitDescription(), obj.getTicketSiteVisitDate(), obj.getTicketReopenNumber(), obj.getBehalfRaiseBy(), obj.getLocationIsBaseLocation(), obj.getTicketServiceTypeIcon(), obj.getTicketRaiseFiles(), obj.getAssetIsByod(), obj.getTicketModuleId(), obj.getTicketSubModuleId(), obj.getTicketIsAddToAsset(), obj.getTicketIsSiteVisit(), obj.getTicketSiteVisitAt(), obj.getTicketResolveFile(), obj.getModuleName(), obj.getSubModuleName()));
                                listAdapter.notifyDataSetChanged();
                                cnt++;
                            }
                        }
                        /*AssetArray.add(new TicketsListPojo(1,"0922/0001","Shailendra Kamble","Low","Hardware","Laptop Camera Issue","IT","Pune","Software Development","My laptop camera is not working when connected with WIFI","","Open","09-09-2022 10:10:40","Sourabh"));
                        listAdapter.notifyDataSetChanged();*/
                        tv_tickets_in_progress.setText(AssetArray.size()+" tickets in progress");
                        //Toast.makeText(getActivity(), ticketListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(getActivity(), msg);
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

    }

    @Override
    public void onContactSelected(AssetListPojo assetListPojo) {
        Intent intent = new Intent(getActivity(), RaiseTicketActivity.class);
        if (assetListPojo.getTicketIsActive()==0) {
            intent.putExtra("assetName", assetListPojo.getAc_name() + " (" + assetListPojo.getAsset_code() + " | " + assetListPojo.getAsset_type() + ")");
            intent.putExtra("assetName_for_icon", assetListPojo.getAc_name());
            intent.putExtra("ac_shcode", assetListPojo.getAsset_code());
            intent.putExtra("ac_id", assetListPojo.getAsset_id());
            intent.putExtra("raisedFor", ticketFor);
            intent.putExtra("raisedByID", raisedByID);
            intent.putExtra("serviceType", "Hardware");
            intent.putExtra("isBYOD", assetListPojo.getAssetIsByod());
            intent.putExtra("navigateTo", "dashboard");
            startActivity(intent);
            getActivity().finish();
        }else {
            Toast.makeText(getActivity(), "Ticket is already raised for this asset", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(getActivity(), assetListPojo.getAsset_code(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContactSelected(SoftwareListPojo softwareListPojo) {
        Intent intent = new Intent(getActivity(), RaiseTicketActivity.class);
        intent.putExtra("assetName", softwareListPojo.getApplication_name());
        intent.putExtra("ac_shcode", "");
        intent.putExtra("ac_id", softwareListPojo.getApplication_id());
        intent.putExtra("raisedFor", ticketFor);
        intent.putExtra("raisedByID", raisedByID);
        intent.putExtra("serviceType", "Software");
        intent.putExtra("icon", softwareListPojo.getApplication_icon());
        intent.putExtra("isBYOD", "0");
        intent.putExtra("navigateTo", "dashboard");
        startActivity(intent);
        getActivity().finish();
        //Toast.makeText(getActivity(), softwareListPojo.getApplication_name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContactSelected(NoticeListPojo notificationData) {
        NotificationDetailsPojo notificationDetailsPojo = new NotificationDetailsPojo(notificationData.getNotificationId(),notificationData.getNotificationSubject(),notificationData.getNotificationMessage(),notificationData.getNotificationAffectedFrom(),notificationData.getNotificationAffectedTo(),"0");
        SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_NOTIFICATION_OBJ, notificationDetailsPojo);
        Intent intent = new Intent(getActivity(), NotificationDetailsActivity.class);
        intent.putExtra("detailsOf", "Notice");
        startActivity(intent);
        getActivity().finish();
    }
}