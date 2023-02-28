package com.vnrseeds.samadhan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vnrseeds.samadhan.addassetforms.AddAccessPointActivity;
import com.vnrseeds.samadhan.addassetforms.AddAssetActivity;
import com.vnrseeds.samadhan.addassetforms.AddThinClientActivity;
import com.vnrseeds.samadhan.addassetforms.CameraActivity;
import com.vnrseeds.samadhan.addassetforms.DVRNVRActivity;
import com.vnrseeds.samadhan.addassetforms.EPBXActivity;
import com.vnrseeds.samadhan.addassetforms.LaptopActivity;
import com.vnrseeds.samadhan.addassetforms.ModemActivity;
import com.vnrseeds.samadhan.addassetforms.RouterActivity;
import com.vnrseeds.samadhan.addassetforms.SFPTransceiverActivity;
import com.vnrseeds.samadhan.addassetforms.PrinterActivity;
import com.vnrseeds.samadhan.addassetforms.TabActivity;
import com.vnrseeds.samadhan.addassetforms.TelevisionActivity;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.DropDownResponse;
import com.vnrseeds.samadhan.parser.assetcategoryparser.Data;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetCategorySelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String TAG = "AssetCategorySelectionActivity";
    private String category;
    private Spinner sp_category;
    private Button bt_next;
    private CustomProgressDialogue customProgressDialogue;
    private List<Data> assetCategory;
    private String ac_id;
    private String token;
    private String ac_shcode;
    private ImageButton back_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_category_selection);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();

    }

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(AssetCategorySelectionActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.add_asset);

        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        sp_category = findViewById(R.id.spinner_ascategory);
        back_nav = findViewById(R.id.back_nav);
        //String[] DayOfWeek = {"Desktop", "NVR", "DVR",  "Camera", "Printer","Modem","Router","Switch","Rack","WiFi","Wireless Controller",
        //"Thin Client","Desk Phone","Projector","Amplifier","Attendance Machine","Barcode Scanner","Patch Cord","SFP","POE","Tab","EPBX","MIC","Speaker","TV"};

        //category = sp_category.getSelectedItem().toString().trim();
        bt_next = findViewById(R.id.bt_nextToAddAsset);

        /*ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<AssetCategoryListResponse> call = apiInterface.getAssetCategoryInfo("application/json", "Bearer "+token, "");
        call.enqueue(new Callback<AssetCategoryListResponse>() {
            @Override
            public void onResponse(@NotNull Call<AssetCategoryListResponse> call, @NotNull Response<AssetCategoryListResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    AssetCategoryListResponse assetCategoryListResponse = response.body();
                    assetCategory = assetCategoryListResponse.getData();
                    ArrayList<String> categorylist = new ArrayList<>();
                    categorylist.add("select category");
                    for (Data obj:assetCategory){
                        categorylist.add(obj.getAcName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AssetCategorySelectionActivity.this, android.R.layout.simple_spinner_item, categorylist);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_category.setAdapter(adapter);
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AssetCategorySelectionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AssetCategorySelectionActivity.this, msg);
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
        });*/
    }

    private void init(){
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssetCategorySelectionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = sp_category.getSelectedItem().toString();
                if (!category.equalsIgnoreCase("select category")) {
                    if (ac_shcode.equalsIgnoreCase("DSKT") || ac_shcode.equalsIgnoreCase("SRVR")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, AddAssetActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_shcode", ac_shcode);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("TNCT")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, AddThinClientActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("LAPT")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, LaptopActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("NVRC") || ac_shcode.equalsIgnoreCase("DVRC")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, DVRNVRActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("CAMR")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, CameraActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_shcode", ac_shcode);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    }else if (ac_shcode.equalsIgnoreCase("ACPT")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, AddAccessPointActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_shcode", ac_shcode);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("PRNT")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, PrinterActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("RUTR")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, RouterActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("MODM") || ac_shcode.equalsIgnoreCase("RACK")
                            || ac_shcode.equalsIgnoreCase("MICP") || ac_shcode.equalsIgnoreCase("DPHN")
                            || ac_shcode.equalsIgnoreCase("PRJT") || ac_shcode.equalsIgnoreCase("AMPF")
                            || ac_shcode.equalsIgnoreCase("BMTA") || ac_shcode.equalsIgnoreCase("BRCS")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, ModemActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_shcode", ac_shcode);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("SFPT") || ac_shcode.equalsIgnoreCase("SPKR") || ac_shcode.equalsIgnoreCase("SWCH")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, SFPTransceiverActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_shcode", ac_shcode);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("TBLT") || ac_shcode.equalsIgnoreCase("MOBL")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, TabActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_shcode", ac_shcode);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("UPSY") || ac_shcode.equalsIgnoreCase("TELV")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, TelevisionActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("ac_shcode", ac_shcode);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    } else if (ac_shcode.equalsIgnoreCase("EPBX")) {
                        Intent intent = new Intent(AssetCategorySelectionActivity.this, EPBXActivity.class);
                        intent.putExtra("category", ac_shcode);
                        intent.putExtra("ac_id", ac_id);
                        startActivity(intent);
                        finish();
                    }else {
                        Utils.getInstance().showAlert(AssetCategorySelectionActivity.this, "Select valid asset category");
                    }
                }else {
                    Utils.getInstance().showAlert(AssetCategorySelectionActivity.this, "Select asset category");
                }
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }

        sp_category.setOnItemSelectedListener(AssetCategorySelectionActivity.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = sp_category.getSelectedItem().toString();

        if (i>0) {
            ac_id = String.valueOf(assetCategory.get(i-1).getAcId());
            ac_shcode = assetCategory.get(i-1).getAcCode();
            getDropDownList(ac_id);
            //Toast.makeText(getApplicationContext(), category + "-" + assetCategory.get(i - 1).getAcId(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getDropDownList(String ac_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<DropDownResponse> call = apiInterface.getDropDownInfo("application/json", "Bearer "+token, ac_id);
        call.enqueue(new Callback<DropDownResponse>() {
            @Override
            public void onResponse(@NotNull Call<DropDownResponse> call, @NotNull Response<DropDownResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    DropDownResponse dropDownResponse = response.body();
                    SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_ADD_ASDD_OBJ, dropDownResponse);
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AssetCategorySelectionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AssetCategorySelectionActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<DropDownResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}