package com.vnrseeds.samadhan.addassetforms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.DeployAssetListAdapter;
import com.vnrseeds.samadhan.parser.assetcategoryparser.AssetCategoryListResponse;
import com.vnrseeds.samadhan.parser.assetcategoryparser.Data;
import com.vnrseeds.samadhan.parser.classificationparser.ClassificationResponse;
import com.vnrseeds.samadhan.parser.deployableassetparser.Datum;
import com.vnrseeds.samadhan.parser.deployableassetparser.DeployableAssetResponse;
import com.vnrseeds.samadhan.parser.locationparser.LocationResponse;
import com.vnrseeds.samadhan.pojo.DeployableAssetListPojo;
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

public class DeployAssetListActivity extends AppCompatActivity implements DeployAssetListAdapter.DeployAssetListAdapterListener{
    private final String TAG="DeployAssetListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private AutoCompleteTextView dd_assetCategory;
    private ImageButton back_nav;
    private List<Data> assetCategory;
    ArrayList<String> categorylist = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private RecyclerView lv_assetlist;
    private DeployAssetListAdapter listAdapter;
    private List<Datum> asselistArray;
    private String ac_id;
    private AutoCompleteTextView dd_assetClassification;
    private ArrayAdapter<String> classificationAdapter;
    private List<com.vnrseeds.samadhan.parser.classificationparser.Data> classifications;
    private ArrayList<String> classificationlist = new ArrayList<>();
    private String classification_id;
    private ArrayList<DeployableAssetListPojo> assetArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_asset_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(DeployAssetListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.deploy_asset);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        back_nav = findViewById(R.id.back_nav);
        dd_assetCategory = findViewById(R.id.dd_assetCategory);
        dd_assetClassification = findViewById(R.id.dd_assetClassification);
        lv_assetlist = findViewById(R.id.lv_assetlist);
        //dd_assetCategory.setDropDownHeight(100);
        //dd_assetCategory.setDropDownBackgroundResource(R.color.colorbackgroung);
        //categorylist.add("Select");
        categoryAdapter = new ArrayAdapter<>(DeployAssetListActivity.this, android.R.layout.simple_spinner_item, categorylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_assetCategory.setAdapter(categoryAdapter);

        classificationAdapter = new ArrayAdapter<>(DeployAssetListActivity.this, android.R.layout.simple_spinner_item, classificationlist);
        classificationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_assetClassification.setAdapter(classificationAdapter);

        lv_assetlist = findViewById(R.id.lv_assetlist);
        listAdapter = new DeployAssetListAdapter(this, assetArray,this);
        lv_assetlist.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lv_assetlist.setLayoutManager(mLayoutManager);
        /*lv_assetlist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lv_assetlist.setItemAnimator(new DefaultItemAnimator());*/
        lv_assetlist.setAdapter(listAdapter);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeployAssetListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getClassification();
        //getAssetCategoryList();

        dd_assetClassification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                assetArray.clear();
                listAdapter.notifyDataSetChanged();
                ac_id="";
                //dd_assetCategory.setText("Select");
                if (i>=0) {
                    classification_id = String.valueOf(classifications.get(i).getClassificationId());
                    //dd_assetCategory.setText("Select");
                    getAssetCategoryList(classification_id);
                    //Toast.makeText(getApplicationContext(), dd_assetClassification.getText().toString() + "-" + classifications.get(i).getClassificationId(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dd_assetCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //listAdapter.getFilter().filter(dd_assetCategory.getText().toString());
                if (i>=0) {
                    ac_id = String.valueOf(assetCategory.get(i).getAcId());
                    getAssetList(ac_id);
                    //Toast.makeText(getApplicationContext(), dd_assetCategory.getText().toString() + "-" + assetCategory.get(i).getAcId(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getClassification() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<ClassificationResponse> call = apiInterface.getClassificationInfo("application/json", "Bearer "+token, "DEPLOY_ASSET");
        call.enqueue(new Callback<ClassificationResponse>() {
            @Override
            public void onResponse(@NotNull Call<ClassificationResponse> call, @NotNull Response<ClassificationResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    ClassificationResponse classificationResponse = response.body();
                    assert classificationResponse != null;
                    classifications = classificationResponse.getData();
                    classificationlist.clear();
                    Log.e(TAG, String.valueOf(categorylist));
                    categorylist.clear();
                    dd_assetCategory.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                    //categorylist.add("Select");
                    for (com.vnrseeds.samadhan.parser.classificationparser.Data obj:classifications){
                        classificationlist.add(obj.getClassificationName());
                    }
                    dd_assetClassification.setAdapter(classificationAdapter);
                    classificationAdapter.notifyDataSetChanged();
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClassificationResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getAssetCategoryList(String classification_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<AssetCategoryListResponse> call = apiInterface.getAssetCategoryInfo("application/json", "Bearer "+token, classification_id, "DEPLOY_ASSET", "");
        Log.e(TAG, token+"====="+classification_id);
        call.enqueue(new Callback<AssetCategoryListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<AssetCategoryListResponse> call, @NotNull Response<AssetCategoryListResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    AssetCategoryListResponse assetCategoryListResponse = response.body();
                    assert assetCategoryListResponse != null;
                    assetCategory = assetCategoryListResponse.getData();
                    categorylist.clear();
                    //categorylist.add("Select");
                    //Log.e(TAG, String.valueOf(assetCategory));
                    //dd_assetCategory.setText("Select");
                    for (Data obj:assetCategory){
                        categorylist.add(obj.getAcName());
                    }
                    Log.e(TAG, String.valueOf(categorylist));
                    dd_assetCategory.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AssetCategoryListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getAssetList(String ac_id) {
        Log.e(TAG, ac_id);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<DeployableAssetResponse> call = apiInterface.getStoreAssetListInfo("application/json", "Bearer "+token, ac_id);
        call.enqueue(new Callback<DeployableAssetResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<DeployableAssetResponse> call, @NotNull Response<DeployableAssetResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    DeployableAssetResponse deployableAssetResponse = response.body();
                    assert deployableAssetResponse != null;
                    asselistArray = deployableAssetResponse.getData();
                    assetArray.clear();
                    listAdapter.notifyDataSetChanged();
                    Log.e(TAG, String.valueOf(assetArray));
                    if (asselistArray.isEmpty()){
                        Utils.getInstance().showAlert(DeployAssetListActivity.this, "No assets found");
                    }else {
                        int cnt = 1;
                        for (Datum obj:asselistArray){
                            assetArray.add(new DeployableAssetListPojo(cnt,obj.getAssetId(),obj.getAssetCode(),obj.getAssetName(),obj.getAcName(),obj.getAcCode(),obj.getBrandName(),obj.getBmName(),obj.getAssetSerialNo(),obj.getAssetCurrentStatus()));
                            listAdapter.notifyDataSetChanged();
                            cnt++;
                        }
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<DeployableAssetResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    public void deployAsset(DeployableAssetListPojo deployableAssetListPojo) {
        Intent intent = new Intent(DeployAssetListActivity.this, DeployAssetFormActivity.class);
        intent.putExtra("asset_code", deployableAssetListPojo.getAssetCode());
        intent.putExtra("serviceType", "Hardware");
        intent.putExtra("assetName", deployableAssetListPojo.getAcName());
        intent.putExtra("asset_id", String.valueOf(deployableAssetListPojo.getAssetId()));
        intent.putExtra("ac_id", ac_id);
        startActivity(intent);
        finish();
        //getLocation(deployableAssetListPojo.getAssetId(),deployableAssetListPojo.getAssetCode(),"Hardware",deployableAssetListPojo.getAcName());
    }

    private void getLocation(int assetId, String assetCode, String serviceType, String assetName) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e(TAG,assetId+"="+ac_id+"="+assetCode+"="+serviceType+"="+assetName);
        Call<LocationResponse> call = apiInterface.getLocationListInfo("application/json", "Bearer "+token,"");
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationResponse> call, @NotNull Response<LocationResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    LocationResponse locationResponse = response.body();
                    //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_ADD_ASDD_OBJ, dropDownResponse);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetListActivity.this, msg);
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

    @Override
    public void onContactSelected(DeployableAssetListPojo deployableAssetListPojo) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeployAssetListActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}