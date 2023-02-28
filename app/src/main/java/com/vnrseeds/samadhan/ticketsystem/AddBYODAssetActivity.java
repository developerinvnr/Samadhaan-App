package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.brandlistparser.BrandListResponse;
import com.vnrseeds.samadhan.parser.brandmodelparser.BrandModelListResponse;
import com.vnrseeds.samadhan.parser.departmentparser.Data;
import com.vnrseeds.samadhan.parser.departmentparser.DepartmentResponse;
import com.vnrseeds.samadhan.parser.locationparser.LocationData;
import com.vnrseeds.samadhan.parser.locationparser.LocationResponse;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.notificationuserparser.Datum;
import com.vnrseeds.samadhan.parser.notificationuserparser.NotificationUserResponse;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
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

public class AddBYODAssetActivity extends AppCompatActivity {

    private static final String TAG = "AddBYODAssetActivity";
    private static final String SELECT = "Select";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private AutoCompleteTextView dd_location;
    private AutoCompleteTextView dd_dept;
    private AutoCompleteTextView dd_user;
    private AutoCompleteTextView dd_brand;
    private AutoCompleteTextView dd_model;
    private EditText et_serial_no;
    private LinearLayout ll_location;
    private LinearLayout ll_dept;
    private LinearLayout ll_user;
    private RadioGroup rg_assetFor;
    private List<LocationData> location;
    private final ArrayList<String> locationList = new ArrayList<>();
    private String selloc_id="";
    private ArrayList<String> deptlist=new ArrayList<>();
    private List<Data> departments;
    private ArrayAdapter<String> deptadapter;
    private ArrayList<String> custodianlist=new ArrayList<>();
    private String seldept_id="";
    private ArrayAdapter<String> custodianapter;
    private List<Datum> custodians;
    private List<com.vnrseeds.samadhan.parser.brandlistparser.Datum> brands;
    private ArrayList<String> brandList=new ArrayList<>();
    private String brand_id="";
    private List<com.vnrseeds.samadhan.parser.brandmodelparser.Data> models;
    private ArrayList<String> modelList=new ArrayList<>();
    private String model_id="";
    private Button button_submit;
    private User userData;
    private String user_id="";
    private RadioButton rb;
    private String asset_id="";
    private String asset_ac_id="2";
    private String remark="";
    private EditText et_remarks;
    private String assetFor="Self";
    private RoleResponse roleResponse;
    private LinearLayout ll_assetFor;
    private String byodCNT;
    private RadioButton rb_self;
    private RadioButton rb_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_byodasset);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(AddBYODAssetActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add BYOD");

        back_nav = findViewById(R.id.back_nav);
        rg_assetFor = findViewById(R.id.rg_assetFor);
        dd_location = findViewById(R.id.dd_location);
        dd_dept = findViewById(R.id.dd_dept);
        dd_user = findViewById(R.id.dd_user);
        dd_brand = findViewById(R.id.dd_brand);
        dd_model = findViewById(R.id.dd_model);
        et_serial_no = findViewById(R.id.et_serial_no);
        et_remarks = findViewById(R.id.et_remarks);
        ll_location = findViewById(R.id.ll_location);
        ll_dept = findViewById(R.id.ll_dept);
        ll_user = findViewById(R.id.ll_user);
        ll_assetFor = findViewById(R.id.ll_assetFor);
        button_submit = findViewById(R.id.button_submit);
        rb_self = findViewById(R.id.rb_self);
        rb_user = findViewById(R.id.rb_user);

        byodCNT=getIntent().getStringExtra("byodCNT");

        userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        user_id = userData.getUser_id();

        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            ll_assetFor.setVisibility(View.GONE);
        }else{
            ll_assetFor.setVisibility(View.VISIBLE);
        }

        if (Integer.parseInt(byodCNT)>0){
            rb_self.setChecked(false);
            rb_self.setEnabled(false);
            rb_user.setChecked(true);
            ll_location.setVisibility(View.VISIBLE);
            ll_dept.setVisibility(View.VISIBLE);
            ll_user.setVisibility(View.VISIBLE);
            assetFor="Other";
            user_id = "";
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
                Intent intent = new Intent(AddBYODAssetActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rg_assetFor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                rb = findViewById(checkedId);
                if (rb.getText().equals("Self")) {
                    ll_location.setVisibility(View.GONE);
                    ll_dept.setVisibility(View.GONE);
                    ll_user.setVisibility(View.GONE);
                    user_id = userData.getUser_id();
                    assetFor="Self";
                } else if (rb.getText().equals("Other")) {
                    ll_location.setVisibility(View.VISIBLE);
                    ll_dept.setVisibility(View.VISIBLE);
                    ll_user.setVisibility(View.VISIBLE);
                    assetFor="Other";
                    user_id = "";
                }
            }
        });

        getlocation();
        getBrand();
        dd_location.setOnItemClickListener((adapterView, view, i, l) -> {
            dd_dept.setText(SELECT);
            dd_user.setText(SELECT);
            if (i >= 0) {
                selloc_id = String.valueOf(location.get(i).getLocationId());
                getDeptList(selloc_id);
            }
        });

        dd_dept.setOnItemClickListener((adapterView, view1, i, l) -> {
            custodianlist.clear();
            dd_user.setText(SELECT);
            if (i >= 0) {
                seldept_id = String.valueOf(departments.get(i).getDepartmentId());
                getUserList(selloc_id, seldept_id);
            }
        });

        dd_user.setOnItemClickListener((adapterView, view1, i, l) -> {
            if (i >= 0) {
                user_id = String.valueOf(custodians.get(i).getUserId());
            }
        });

        dd_brand.setOnItemClickListener((adapterView, view1, i, l) -> {
            modelList.clear();
            dd_model.setText(SELECT);
            if (i >= 0) {
                brand_id = String.valueOf(brands.get(i).getBrandId());
                getModelsList(brand_id);
            }
        });

        dd_model.setOnItemClickListener((adapterView, view1, i, l) -> {
            if (i >= 0) {
                model_id = String.valueOf(models.get(i).getBmId());
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serial_no = et_serial_no.getText().toString().trim();
                remark = et_remarks.getText().toString().trim();
                if (assetFor.equals("Other") && selloc_id.equalsIgnoreCase("")){
                    Toast.makeText(AddBYODAssetActivity.this, "Select Location", Toast.LENGTH_SHORT).show();
                }else if (assetFor.equals("Other") && seldept_id.equalsIgnoreCase("")){
                    Toast.makeText(AddBYODAssetActivity.this, "Select Department", Toast.LENGTH_SHORT).show();
                }else if (assetFor.equals("Other") && user_id.equalsIgnoreCase("")){
                    Toast.makeText(AddBYODAssetActivity.this, "Select User", Toast.LENGTH_SHORT).show();
                }else if (brand_id.equalsIgnoreCase("")){
                    Toast.makeText(AddBYODAssetActivity.this, "Select Brand", Toast.LENGTH_SHORT).show();
                }else if (model_id.equalsIgnoreCase("")){
                    Toast.makeText(AddBYODAssetActivity.this, "Select Model", Toast.LENGTH_SHORT).show();
                }else if (serial_no.equalsIgnoreCase("")){
                    Toast.makeText(AddBYODAssetActivity.this, "Enter Serial Number", Toast.LENGTH_SHORT).show();
                }else {
                    submitData(serial_no);
                }
            }
        });
    }

    private void submitData(String serial_no) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getBYODSubmitInfo("application/json", "Bearer "+token, user_id, asset_id, asset_ac_id, brand_id, model_id, serial_no, remark);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    assert submitSuccessResponse != null;
                    if (submitSuccessResponse.getStatus()) {
                        Intent intent = new Intent(AddBYODAssetActivity.this, MainActivity.class);
                        Toast.makeText(AddBYODAssetActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddBYODAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddBYODAssetActivity.this, msg);
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

    private void getModelsList(String brand_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<BrandModelListResponse> call = apiInterface.getModelInfo("application/json", "Bearer "+token,"2", brand_id);
        call.enqueue(new Callback<BrandModelListResponse>() {
            @Override
            public void onResponse(@NotNull Call<BrandModelListResponse> call, @NotNull Response<BrandModelListResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    BrandModelListResponse brandModelListResponse = response.body();
                    //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_ADD_ASDD_OBJ, dropDownResponse);
                    assert brandModelListResponse != null;
                    models = brandModelListResponse.getData();
                    for (com.vnrseeds.samadhan.parser.brandmodelparser.Data obj : models) {
                        modelList.add(obj.getBmName());
                    }
                    ArrayAdapter<String> modelAadapter = new ArrayAdapter<>(AddBYODAssetActivity.this, android.R.layout.simple_spinner_item, modelList);
                    modelAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_model.setAdapter(modelAadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddBYODAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddBYODAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<BrandModelListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getBrand() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<BrandListResponse> call = apiInterface.getBrandsListInfo("application/json", "Bearer "+token,"2");
        call.enqueue(new Callback<BrandListResponse>() {
            @Override
            public void onResponse(@NotNull Call<BrandListResponse> call, @NotNull Response<BrandListResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    BrandListResponse brandListResponse = response.body();
                    //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_ADD_ASDD_OBJ, dropDownResponse);
                    assert brandListResponse != null;
                    brands = brandListResponse.getData();
                    for (com.vnrseeds.samadhan.parser.brandlistparser.Datum obj : brands) {
                        brandList.add(obj.getBrandName());
                    }
                    ArrayAdapter<String> brandAadapter = new ArrayAdapter<>(AddBYODAssetActivity.this, android.R.layout.simple_spinner_item, brandList);
                    brandAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_brand.setAdapter(brandAadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddBYODAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddBYODAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<BrandListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getUserList(String selloc_id, String seldept_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<NotificationUserResponse> call = apiInterface.getUsersInfo("application/json", "Bearer " + token, selloc_id, seldept_id, "BYOD_USER");
        call.enqueue(new Callback<NotificationUserResponse>() {
            @Override
            public void onResponse(@NotNull Call<NotificationUserResponse> call, @NotNull Response<NotificationUserResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    custodianlist.clear();
                    NotificationUserResponse custodianResponse = response.body();
                    assert custodianResponse != null;
                    custodians = custodianResponse.getData();
                    for (Datum obj : custodians) {
                        custodianlist.add(obj.getUserName());
                    }
                    custodianapter = new ArrayAdapter<>(AddBYODAssetActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_user.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddBYODAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddBYODAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<NotificationUserResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getDeptList(String selloc_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<DepartmentResponse> call = apiInterface.getDeptListInfoBYOD("application/json", "Bearer " + token, selloc_id, "BYOD_USER");
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
                    deptadapter = new ArrayAdapter<>(AddBYODAssetActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_dept.setAdapter(deptadapter);
                    deptadapter.notifyDataSetChanged();
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddBYODAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddBYODAssetActivity.this, msg);
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

    private void getlocation() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<LocationResponse> call = apiInterface.getLocationListInfo("application/json", "Bearer "+token,"BYOD_USER");
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationResponse> call, @NotNull Response<LocationResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    LocationResponse locationResponse = response.body();
                    //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_ADD_ASDD_OBJ, dropDownResponse);
                    assert locationResponse != null;
                    location = locationResponse.getData();
                    for (LocationData obj : location) {
                        locationList.add(obj.getLocationName());
                    }
                    ArrayAdapter<String> locationAadapter = new ArrayAdapter<>(AddBYODAssetActivity.this, android.R.layout.simple_spinner_item, locationList);
                    locationAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_location.setAdapter(locationAadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddBYODAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddBYODAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }
}