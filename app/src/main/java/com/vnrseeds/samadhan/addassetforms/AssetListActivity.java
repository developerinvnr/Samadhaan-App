package com.vnrseeds.samadhan.addassetforms;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.AssetListAdapter;
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListData;
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListPojo;
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListResponse;
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

public class AssetListActivity extends AppCompatActivity implements AssetListAdapter.AssetListAdapterListener {
    private static final String TAG = "AssetListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private List<AssetListData> asselistArray = new ArrayList<>();
    private AssetListAdapter listAdapter;
    private SearchView searchView;
    private RecyclerView lv_assetlist;
    private ImageButton back_nav;
    private ArrayList<AssetListPojo> AssetArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(AssetListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        toolbar_title.setText(R.string.asset_list);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        searchView = findViewById(R.id.searchView);
        lv_assetlist = findViewById(R.id.lv_assetlist);
        listAdapter = new AssetListAdapter(this, AssetArray,this);
        lv_assetlist.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lv_assetlist.setLayoutManager(mLayoutManager);
        lv_assetlist.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lv_assetlist.setItemAnimator(new DefaultItemAnimator());
        lv_assetlist.setAdapter(listAdapter);
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssetListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        getAssetList();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    listAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    private void getAssetList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<AssetListResponse> call = apiInterface.getAssetListInfo("application/json", "Bearer "+token);
        call.enqueue(new Callback<AssetListResponse>() {
            @Override
            public void onResponse(@NotNull Call<AssetListResponse> call, @NotNull Response<AssetListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    AssetListResponse assetListResponse = response.body();
                    assert assetListResponse != null;
                    asselistArray = assetListResponse.getData();
                    if (asselistArray.isEmpty()){
                        Utils.getInstance().showAlert(AssetListActivity.this, "No assets found");
                    }else {
                        int cnt = 1;
                        for (AssetListData obj:asselistArray){
                            AssetArray.add(new AssetListPojo(cnt,obj.getAssetId(),obj.getAssetCode(),obj.getAcName(),obj.getAssetType(),obj.getBrandName(),obj.getBmName(),obj.getLocationName(),obj.getDepartmentName(),obj.getDsName(),obj.getCustodianName(),obj.getAssetStatus(),obj.getAc_code()));
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
                            Toast.makeText(AssetListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AssetListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AssetListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onContactSelected(AssetListPojo assetListPojo) {
        if (assetListPojo.getAcCode().equalsIgnoreCase("DSKT") || assetListPojo.getAcCode().equalsIgnoreCase("LAPT") || assetListPojo.getAcCode().equalsIgnoreCase("SRVR")) {
            Intent intent = new Intent(AssetListActivity.this, EditAssetActivity.class);
            intent.putExtra("category", assetListPojo.getAcName());
            intent.putExtra("ac_shcode", assetListPojo.getAcCode());
            intent.putExtra("ac_id", assetListPojo.getAssetId());
            startActivity(intent);
            finish();
        } else if (assetListPojo.getAcCode().equalsIgnoreCase("MODM") || assetListPojo.getAcCode().equalsIgnoreCase("BRCS")
                || assetListPojo.getAcCode().equalsIgnoreCase("RACK") || assetListPojo.getAcCode().equalsIgnoreCase("MICP")
                || assetListPojo.getAcCode().equalsIgnoreCase("DPHN") || assetListPojo.getAcCode().equalsIgnoreCase("PRJT")
                || assetListPojo.getAcCode().equalsIgnoreCase("AMPF") || assetListPojo.getAcCode().equalsIgnoreCase("BMTA")) {
            Intent intent = new Intent(AssetListActivity.this, EditModemActivity.class);
            intent.putExtra("category", assetListPojo.getAcName());
            intent.putExtra("ac_shcode", assetListPojo.getAcCode());
            intent.putExtra("ac_id", assetListPojo.getAssetId());
            startActivity(intent);
            finish();
        } else if (assetListPojo.getAcCode().equalsIgnoreCase("NVRC") || assetListPojo.getAcCode().equalsIgnoreCase("DVRC")) {
            Intent intent = new Intent(AssetListActivity.this, EditDVRNVRActivity.class);
            intent.putExtra("category", assetListPojo.getAcName());
            intent.putExtra("ac_shcode", assetListPojo.getAcCode());
            intent.putExtra("ac_id", assetListPojo.getAssetId());
            startActivity(intent);
            finish();
        } else if (assetListPojo.getAcCode().equalsIgnoreCase("SFPT") || assetListPojo.getAcCode().equalsIgnoreCase("SPKR") || assetListPojo.getAcCode().equalsIgnoreCase("SWCH")) {
            Intent intent = new Intent(AssetListActivity.this, EditSFPTransceiverActivity.class);
            intent.putExtra("category", assetListPojo.getAcName());
            intent.putExtra("ac_shcode", assetListPojo.getAcCode());
            intent.putExtra("ac_id", assetListPojo.getAssetId());
            startActivity(intent);
            finish();
        } else if (assetListPojo.getAcCode().equalsIgnoreCase("RUTR")) {
            Intent intent = new Intent(AssetListActivity.this, EditRouterActivity.class);
            intent.putExtra("category", assetListPojo.getAcName());
            intent.putExtra("ac_shcode", assetListPojo.getAcCode());
            intent.putExtra("ac_id", assetListPojo.getAssetId());
            startActivity(intent);
            finish();
        } else if (assetListPojo.getAcCode().equalsIgnoreCase("TBLT") || assetListPojo.getAcCode().equalsIgnoreCase("MOBL")) {
            Intent intent = new Intent(AssetListActivity.this, EditTabActivity.class);
            intent.putExtra("category", assetListPojo.getAcName());
            intent.putExtra("ac_shcode", assetListPojo.getAcCode());
            intent.putExtra("ac_id", assetListPojo.getAssetId());
            startActivity(intent);
            finish();
        } else if (assetListPojo.getAcCode().equalsIgnoreCase("UPSY") || assetListPojo.getAcCode().equalsIgnoreCase("TELV")) {
            Intent intent = new Intent(AssetListActivity.this, EditTelevisionActivity.class);
            intent.putExtra("category", assetListPojo.getAcName());
            intent.putExtra("ac_shcode", assetListPojo.getAcCode());
            intent.putExtra("ac_id", assetListPojo.getAssetId());
            startActivity(intent);
            finish();
        } else if (assetListPojo.getAcCode().equalsIgnoreCase("PRNT")) {
            Intent intent = new Intent(AssetListActivity.this, EditPrinterActivity.class);
            intent.putExtra("category", assetListPojo.getAcName());
            intent.putExtra("ac_shcode", assetListPojo.getAcCode());
            intent.putExtra("ac_id", assetListPojo.getAssetId());
            startActivity(intent);
            finish();
        }
        Toast.makeText(AssetListActivity.this, assetListPojo.getAssetId(), Toast.LENGTH_SHORT).show();
    }
}