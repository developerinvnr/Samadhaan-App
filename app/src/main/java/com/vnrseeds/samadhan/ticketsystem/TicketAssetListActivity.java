 package com.vnrseeds.samadhan.ticketsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.SoftwareListAdapter;
import com.vnrseeds.samadhan.adapter.TicketAssetListAdapter;
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListData;
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListPojo;
import com.vnrseeds.samadhan.parser.assetmaintaincestatusparser.MaintainanceStatusResponse;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianData;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianResponse;
import com.vnrseeds.samadhan.parser.departmentparser.Data;
import com.vnrseeds.samadhan.parser.departmentparser.DepartmentResponse;
import com.vnrseeds.samadhan.parser.locationparser.LocationData;
import com.vnrseeds.samadhan.parser.locationparser.LocationResponse;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.sectionparser.SectionData;
import com.vnrseeds.samadhan.parser.sectionparser.SectionResponse;
import com.vnrseeds.samadhan.parser.ticketassetparser.TicketAssetListResponse;
import com.vnrseeds.samadhan.parser.ticketassetparser.UserApplication;
import com.vnrseeds.samadhan.parser.ticketassetparser.UserAsset;
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

public class TicketAssetListActivity extends AppCompatActivity implements TicketAssetListAdapter.TicketAssetListAdapterListener, SoftwareListAdapter.TicketSoftwareListAdapterListener {
    private static final String TAG = "TicketAssetListActivity";
    private static final String SELECT = "Select";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private final List<AssetListData> assetlistArray = new ArrayList<>();
    private TicketAssetListAdapter listAdapter;
    private SoftwareListAdapter softwareListAdapter;
    private SearchView searchView;
    private RecyclerView lv_assetlist;
    private ImageButton back_nav;
    private final ArrayList<AssetListPojo> AssetArray = new ArrayList<>();

    private AutoCompleteTextView spinner_location;
    private AutoCompleteTextView spinner_department;
    private AutoCompleteTextView spinner_section;
    private AutoCompleteTextView spinner_raisedby;

    private List<LocationData> locations;
    private final ArrayList<String> locationList = new ArrayList<>();
    private List<Data> departments;
    private final ArrayList<String> deptlist = new ArrayList<>();
    private String selloc_id="";
    private String seldept_id="";
    private List<SectionData> sections;
    private final ArrayList<String> sectionslist = new ArrayList<>();
    private String selsection_id="";
    private List<CustodianData> custodians;
    private final ArrayList<String> custodianlist = new ArrayList<>();
    private String raisedByID = "";
    private ArrayList<UserAsset> assetsdata = new ArrayList<>();
    private String ticketFor="Self";
    private RadioGroup rg_ticketfor;
    private LinearLayout ll_dept_section;
    private LinearLayout ll_raisedby;
    private User userData;
    private ImageButton btn_software;
    private ImageButton btn_hardware;
    private RecyclerView lv_swlist;
    private String serviceType="Hardware";
    private List<UserApplication> softwaredata = new ArrayList<>();
    private CardView card_hardware;
    private CardView card_software;
    private RoleResponse roleResponse;
    private LinearLayout ll_ticketFor;
    private View view;
    private TextView tv_ticketFor;
    private TextView tv_nodatafound_hw;
    private TextView tv_nodatafound_sw;
    private String user_id="";
    private ArrayAdapter<String> deptadapter;
    private ArrayAdapter<String> sectionadapter;
    private ArrayAdapter<String> custodianapter;
    private ArrayList<com.vnrseeds.samadhan.pojo.AssetListPojo> assetListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_asset_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(TicketAssetListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        toolbar_title.setText("Asset List");
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        searchView = findViewById(R.id.searchView);
        lv_assetlist = findViewById(R.id.lv_assetlist);
        lv_swlist = findViewById(R.id.lv_swlist);

        spinner_location = findViewById(R.id.spinner_location);
        spinner_department = findViewById(R.id.spinner_department);
        spinner_section = findViewById(R.id.spinner_section);
        spinner_raisedby = findViewById(R.id.spinner_raisedby);
        rg_ticketfor = findViewById(R.id.rg_ticketfor);
        ll_dept_section = findViewById(R.id.ll_dept_section);
        ll_raisedby = findViewById(R.id.ll_raisedby);
        btn_software = findViewById(R.id.btn_software);
        btn_hardware = findViewById(R.id.btn_hardware);
        card_hardware = findViewById(R.id.card_hardware);
        card_software = findViewById(R.id.card_software);
        ll_ticketFor = findViewById(R.id.ll_ticketFor);
        tv_ticketFor = findViewById(R.id.tv_ticketFor);
        tv_nodatafound_hw = findViewById(R.id.tv_nodatafound_hw);
        tv_nodatafound_sw = findViewById(R.id.tv_nodatafound_sw);
        view = findViewById(R.id.view);

        userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        raisedByID = userData.getUser_id();
        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            tv_ticketFor.setVisibility(View.GONE);
            rg_ticketfor.setVisibility(View.GONE);
        }else{
            tv_ticketFor.setVisibility(View.VISIBLE);
            rg_ticketfor.setVisibility(View.VISIBLE);
        }
        //listAdapter = new TicketAssetListAdapter(TicketAssetListActivity.this, assetsdata,TicketAssetListActivity.this);

        listAdapter = new TicketAssetListAdapter(TicketAssetListActivity.this, assetListData,TicketAssetListActivity.this);
        lv_assetlist.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lv_assetlist.setLayoutManager(mLayoutManager);
        lv_assetlist.setItemAnimator(new DefaultItemAnimator());
        lv_assetlist.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

        deptadapter = new ArrayAdapter<>(TicketAssetListActivity.this, android.R.layout.simple_spinner_item, deptlist);
        deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_department.setAdapter(deptadapter);

        sectionadapter = new ArrayAdapter<>(TicketAssetListActivity.this, android.R.layout.simple_spinner_item, sectionslist);
        sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_section.setAdapter(sectionadapter);

        custodianapter = new ArrayAdapter<>(TicketAssetListActivity.this, android.R.layout.simple_spinner_item, custodianlist);
        custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_raisedby.setAdapter(custodianapter);
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketAssetListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getLocationList();
        getAssetList();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                softwareListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btn_hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv_assetlist.setVisibility(View.VISIBLE);
                lv_swlist.setVisibility(View.GONE);
                tv_nodatafound_sw.setVisibility(View.GONE);
                serviceType="Hardware";
                card_hardware.setCardBackgroundColor(Color.parseColor("#deddb9"));
                card_software.setCardBackgroundColor(Color.parseColor("#EAE9E9"));
                if (assetsdata.isEmpty()) {
                    tv_nodatafound_hw.setVisibility(View.VISIBLE);
                    tv_nodatafound_sw.setVisibility(View.GONE);
                    lv_assetlist.setVisibility(View.GONE);
                }
            }
        });
        btn_software.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv_swlist.setVisibility(View.VISIBLE);
                lv_assetlist.setVisibility(View.GONE);
                tv_nodatafound_hw.setVisibility(View.GONE);
                serviceType="Software";
                card_software.setCardBackgroundColor(Color.parseColor("#deddb9"));
                card_hardware.setCardBackgroundColor(Color.parseColor("#EAE9E9"));
                if (softwaredata.isEmpty()) {
                    tv_nodatafound_hw.setVisibility(View.GONE);
                    tv_nodatafound_sw.setVisibility(View.VISIBLE);
                    lv_swlist.setVisibility(View.GONE);
                }

            }
        });

        spinner_location.setOnItemClickListener((adapterView, view, i, l) -> {
            spinner_department.setText(SELECT);
            spinner_section.setText(SELECT);
            spinner_raisedby.setText(SELECT);
            if (i >= 0) {
                selloc_id = String.valueOf(locations.get(i).getLocationId());
                getDeptList(selloc_id);
            }
        });

        spinner_department.setOnItemClickListener((adapterView, view1, i, l) -> {
            sectionslist.clear();
            custodianlist.clear();
            spinner_section.setText(SELECT);
            spinner_raisedby.setText(SELECT);
            if (i >= 0) {
                seldept_id = String.valueOf(departments.get(i).getDepartmentId());
                sectionslist.clear();
                getSectionList(selloc_id, seldept_id);
            }
        });

        spinner_section.setOnItemClickListener((adapterView, view1, i, l) -> {
            custodianlist.clear();
            spinner_raisedby.setText(SELECT);
            if (i >= 0) {
                selsection_id = String.valueOf(sections.get(i).getDsId());
                getCustodianList(selsection_id);
            }
        });

        spinner_raisedby.setOnItemClickListener((adapterView, view1, i, l) -> {
            if (i>=0){
                raisedByID = String.valueOf(custodians.get(i).getCustodianId());
                getAssetList();
            }
        });

        rg_ticketfor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb.getText().equals("Self")) {
                    ll_dept_section.setVisibility(View.GONE);
                    ll_raisedby.setVisibility(View.GONE);
                    ticketFor = "Self";
                    getAssetList();
                } else if (rb.getText().equals("Others")) {
                    ll_dept_section.setVisibility(View.VISIBLE);
                    ll_raisedby.setVisibility(View.VISIBLE);
                    ticketFor = "Other";
                    user_id=userData.getUser_id();
                    assetListData.clear();
                    softwaredata.clear();
                    tv_nodatafound_hw.setVisibility(View.VISIBLE);
                    lv_assetlist.setAdapter(listAdapter);
                    lv_swlist.setAdapter(softwareListAdapter);
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getAssetList() {
        Log.e(TAG, raisedByID);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<TicketAssetListResponse> call = apiInterface.getTicketRaiseDataInfo("application/json", "Bearer " + token, ticketFor, raisedByID);
        call.enqueue(new Callback<TicketAssetListResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<TicketAssetListResponse> call, @NotNull Response<TicketAssetListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    assetsdata.clear();
                    softwaredata.clear();
                    assetListData.clear();
                    TicketAssetListResponse ticketAssetListResponse = response.body();
                    assert ticketAssetListResponse != null;
                    assetsdata = ticketAssetListResponse.getData().getUserAssets();
                    Log.e(TAG, String.valueOf(assetsdata));
                    if (assetsdata.isEmpty()){
                        //Utils.getInstance().showAlert(TicketAssetListActivity.this, "No assets found");
                        if (serviceType.equalsIgnoreCase("Hardware")) {
                            tv_nodatafound_hw.setVisibility(View.VISIBLE);
                            tv_nodatafound_sw.setVisibility(View.GONE);
                        }
                    }else {
                        tv_nodatafound_hw.setVisibility(View.GONE);
                        int cnt = 1;
                        for (UserAsset obj:assetsdata){
                            assetListData.add(new com.vnrseeds.samadhan.pojo.AssetListPojo(cnt,obj.getAssetId(),obj.getAsset_ac_id(),obj.getAssetCode(),obj.getAssetType(),obj.getBrandName(),obj.getModelName(),obj.getAssetSerialNo(),obj.getAssetInstallationDate(),obj.getAcName(),obj.getAcIcon(),obj.getLocationName(),obj.getDepartmentName(),obj.getDsName(), obj.getAssetCustodian(), obj.getAcCode(), obj.getAssetIsByod(), obj.getTicketIsActive()));
                            listAdapter.notifyDataSetChanged();
                            cnt++;
                        }
                    }

                    softwaredata = ticketAssetListResponse.getData().getUserApplications();
                    if (softwaredata.isEmpty()){
                        //Utils.getInstance().showAlert(TicketAssetListActivity.this, "No applications found");
                        if (serviceType.equalsIgnoreCase("Software")) {
                            tv_nodatafound_hw.setVisibility(View.GONE);
                            tv_nodatafound_sw.setVisibility(View.VISIBLE);
                        }
                    }else {
                        softwareListAdapter = new SoftwareListAdapter(TicketAssetListActivity.this, softwaredata,TicketAssetListActivity.this);
                        lv_swlist.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        lv_swlist.setLayoutManager(mLayoutManager);
                        lv_swlist.setItemAnimator(new DefaultItemAnimator());
                        lv_swlist.setAdapter(softwareListAdapter);
                        softwareListAdapter.notifyDataSetChanged();
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketAssetListActivity.this, msg);
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

    private void getLocationList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<LocationResponse> call = apiInterface.getLocationListInfo("application/json", "Bearer " + token,"");
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationResponse> call, @NotNull Response<LocationResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    locationList.clear();
                    LocationResponse locationResponse = response.body();
                    assert locationResponse != null;
                    locations = locationResponse.getData();
                    for (LocationData obj : locations) {
                        locationList.add(obj.getLocationName());
                    }
                    ArrayAdapter<String> locationAadapter = new ArrayAdapter<>(TicketAssetListActivity.this, android.R.layout.simple_spinner_item, locationList);
                    locationAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_location.setAdapter(locationAadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketAssetListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getDeptList(String loc_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<DepartmentResponse> call = apiInterface.getDeptListInfo("application/json", "Bearer " + token, loc_id);
        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(@NotNull Call<DepartmentResponse> call, @NotNull Response<DepartmentResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    deptlist.clear();
                    DepartmentResponse departmentResponse = response.body();
                    assert departmentResponse != null;
                    departments = departmentResponse.getData();
                    for (com.vnrseeds.samadhan.parser.departmentparser.Data obj : departments) {
                        deptlist.add(obj.getDepartmentName());
                    }
                    deptadapter = new ArrayAdapter<>(TicketAssetListActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Log.e(TAG, String.valueOf(deptlist));
                    spinner_department.setAdapter(deptadapter);
                    deptadapter.notifyDataSetChanged();
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketAssetListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<DepartmentResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getSectionList(String selloc_id, String seldept_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SectionResponse> call = apiInterface.getSectionInfo("application/json", "Bearer " + token, selloc_id, seldept_id);
        call.enqueue(new Callback<SectionResponse>() {
            @Override
            public void onResponse(@NotNull Call<SectionResponse> call, @NotNull Response<SectionResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    sectionslist.clear();
                    SectionResponse sectionResponse = response.body();
                    assert sectionResponse != null;
                    sections = sectionResponse.getData();

                    for (SectionData obj : sections) {
                        sectionslist.add(obj.getDsName());
                    }
                    sectionadapter = new ArrayAdapter<>(TicketAssetListActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketAssetListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SectionResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getCustodianList(String selsection_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<CustodianResponse> call = apiInterface.getCustodianInfo("application/json", "Bearer " + token, selsection_id, user_id);
        call.enqueue(new Callback<CustodianResponse>() {
            @Override
            public void onResponse(@NotNull Call<CustodianResponse> call, @NotNull Response<CustodianResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    custodianlist.clear();
                    CustodianResponse custodianResponse = response.body();
                    assert custodianResponse != null;
                    custodians = custodianResponse.getData();
                    for (CustodianData obj : custodians) {
                        custodianlist.add(obj.getCustodianName());
                    }
                    custodianapter = new ArrayAdapter<>(TicketAssetListActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_raisedby.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketAssetListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CustodianResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TicketAssetListActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    /*@Override
    public void onContactSelected(AssetListPojo assetListPojo) {
        Intent intent = new Intent(TicketAssetListActivity.this, RaiseTicketActivity.class);
        intent.putExtra("category", assetListPojo.getAcName());
        intent.putExtra("ac_shcode", assetListPojo.getAcCode());
        intent.putExtra("ac_id", assetListPojo.getAssetId());
        startActivity(intent);
        finish();
        Toast.makeText(TicketAssetListActivity.this, assetListPojo.getAssetId(), Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onContactSelected(UserApplication userApplication) {
        /*Intent intent = new Intent(TicketAssetListActivity.this, RaiseTicketActivity.class);
        intent.putExtra("assetName", userApplication.getApplicationName());
        intent.putExtra("ac_shcode", "");
        intent.putExtra("ac_id", userApplication.getApplicationId());
        intent.putExtra("raisedFor", ticketFor);
        intent.putExtra("raisedByID", raisedByID);
        intent.putExtra("serviceType", serviceType);
        startActivity(intent);
        finish();
        Toast.makeText(TicketAssetListActivity.this, userApplication.getApplicationName(), Toast.LENGTH_SHORT).show();*/
    }

    public void raiseTicketSW(UserApplication userApplication) {
        checkAssetMaintainance(userApplication.getApplicationName(),userApplication.getApplicationName(),userApplication.getApplicationId(),ticketFor,raisedByID,serviceType,0, userApplication.getApplicationIcon(), "0");
        /*Intent intent = new Intent(TicketAssetListActivity.this, RaiseTicketActivity.class);
        intent.putExtra("assetName", userApplication.getApplicationName());
        intent.putExtra("ac_shcode", userApplication.getApplicationName());
        intent.putExtra("ac_id", userApplication.getApplicationId());
        intent.putExtra("raisedFor", ticketFor);
        intent.putExtra("raisedByID", raisedByID);
        intent.putExtra("serviceType", serviceType);
        startActivity(intent);
        finish();*/
        //Toast.makeText(TicketAssetListActivity.this, userApplication.getApplicationName(), Toast.LENGTH_SHORT).show();
    }

    private void checkAssetMaintainance(String applicationName, String applicationName1, String applicationId, String ticketFor, String raisedByID, String serviceType, int ticketIsActive, String iconName, String isBYOD) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<MaintainanceStatusResponse> call = apiInterface.getMaintainanceFlag("application/json", "Bearer " + token, serviceType, applicationId, raisedByID);
        call.enqueue(new Callback<MaintainanceStatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<MaintainanceStatusResponse> call, @NotNull Response<MaintainanceStatusResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    custodianlist.clear();
                    MaintainanceStatusResponse maintainanceStatusResponse = response.body();
                    assert maintainanceStatusResponse != null;
                    com.vnrseeds.samadhan.parser.assetmaintaincestatusparser.Data maintainanceData = maintainanceStatusResponse.getData();
                    if (maintainanceData.getServiceIsUnderMaintenance()==1){
                        Toast.makeText(TicketAssetListActivity.this, maintainanceData.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (serviceType.equalsIgnoreCase("Hardware") && ticketIsActive==1){
                        Toast.makeText(TicketAssetListActivity.this, "Ticket is already raised for this asset", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(TicketAssetListActivity.this, RaiseTicketActivity.class);
                        intent.putExtra("assetName", applicationName);
                        intent.putExtra("ac_shcode", applicationName1);
                        intent.putExtra("assetName_for_icon", applicationName1);
                        intent.putExtra("ac_id", applicationId);
                        intent.putExtra("raisedFor", ticketFor);
                        intent.putExtra("raisedByID", raisedByID);
                        intent.putExtra("serviceType", serviceType);
                        intent.putExtra("ticketIsActive", String.valueOf(ticketIsActive));
                        intent.putExtra("iconName", iconName);
                        intent.putExtra("isBYOD", isBYOD);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MaintainanceStatusResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    public void raiseTicketHW(com.vnrseeds.samadhan.pojo.AssetListPojo assetListPojo) {
        checkAssetMaintainance(assetListPojo.getAc_name()+" ("+assetListPojo.getAsset_code()+" | "+assetListPojo.getAsset_type()+")",assetListPojo.getAc_name(),assetListPojo.getAsset_id(),ticketFor,raisedByID,serviceType, assetListPojo.getTicketIsActive(), assetListPojo.getAc_icon(), assetListPojo.getAssetIsByod());
        /*Intent intent = new Intent(TicketAssetListActivity.this, RaiseTicketActivity.class);
        intent.putExtra("assetName", assetListPojo.getAc_name()+" ("+assetListPojo.getAsset_code()+" | "+assetListPojo.getAsset_type()+")");
        intent.putExtra("ac_shcode", assetListPojo.getAc_name());
        intent.putExtra("ac_id", assetListPojo.getAsset_id());
        intent.putExtra("raisedFor", ticketFor);
        intent.putExtra("raisedByID", raisedByID);
        intent.putExtra("serviceType", serviceType);
        startActivity(intent);
        finish();*/
        //Toast.makeText(TicketAssetListActivity.this, assetListPojo.getAc_name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContactSelected(com.vnrseeds.samadhan.pojo.AssetListPojo assetListPojo) {
        /*Intent intent = new Intent(TicketAssetListActivity.this, RaiseTicketActivity.class);
        intent.putExtra("assetName", userAsset.getAcName()+" ("+userAsset.getAssetCode()+" | "+userAsset.getAssetType()+")");
        intent.putExtra("ac_shcode", userAsset.getAssetCode());
        intent.putExtra("ac_id", userAsset.getAssetId());
        intent.putExtra("raisedFor", ticketFor);
        intent.putExtra("raisedByID", raisedByID);
        intent.putExtra("serviceType", serviceType);
        startActivity(intent);
        finish();
        Toast.makeText(TicketAssetListActivity.this, userAsset.getAcName(), Toast.LENGTH_SHORT).show();*/
    }
}