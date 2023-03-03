package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.addtoassetconsumalesparser.AddToAssetConsumablesListResponse;
import com.vnrseeds.samadhan.parser.addtoassetconsumalesparser.Asset;
import com.vnrseeds.samadhan.parser.addtoassetperipherallistparser.PeripheralListResponse;
import com.vnrseeds.samadhan.parser.assetcategoryparser.AssetCategoryListResponse;
import com.vnrseeds.samadhan.parser.classificationparser.ClassificationResponse;
import com.vnrseeds.samadhan.parser.classificationparser.Data;
import com.vnrseeds.samadhan.parser.primarylocparser.PrimaryLocationsResponse;
import com.vnrseeds.samadhan.parser.removableassets.ExternalPeripheral;
import com.vnrseeds.samadhan.parser.removableassets.InternalPeripheral;
import com.vnrseeds.samadhan.parser.removableassets.MyKit;
import com.vnrseeds.samadhan.parser.removableassets.RamCapacity;
import com.vnrseeds.samadhan.parser.removableassets.RemovableAssetsResponse;
import com.vnrseeds.samadhan.parser.secondarylocparser.SecondaryLocationResponse;
import com.vnrseeds.samadhan.parser.storagesectionparser.Datum;
import com.vnrseeds.samadhan.parser.storagesectionparser.StorageSectionResponse;
import com.vnrseeds.samadhan.parser.storagetypeparser.StorageTypeResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToAssetActivity extends AppCompatActivity {

    private static final String TAG = "AddToAssetActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private TicketDetailsPojo ticketDetailsPojo;
    private RadioGroup rg_action;
    private TextView tv_availabletQty;
    private TextView et_newAssetQty;
    private Button button_submit;
    private AutoCompleteTextView dd_assetClassification;
    private AutoCompleteTextView dd_assetCategory;
    private ArrayAdapter categoryAdapter;
    private ArrayAdapter classificationAdapter;
    ArrayList<String> categorylist = new ArrayList<>();
    private ArrayList<String> classificationlist = new ArrayList<>();
    private AutoCompleteTextView dd_assetName;
    private LinearLayout ll_assetType;
    private List<Data> classifications;
    private String classification_id="0";
    private List<com.vnrseeds.samadhan.parser.assetcategoryparser.Data> assetCategory;
    private String ac_id;
    private ArrayList<String> consumalelist=new ArrayList<>();
    private ArrayAdapter<String> consumableAdapter;
    private List<Asset> consumaleList;
    private List<com.vnrseeds.samadhan.parser.addtoassetperipherallistparser.Asset> peripheralList;
    private String asset_id="";
    private LinearLayout ll_storeQty;
    private LinearLayout ll_newQty;
    private Integer storeQty=0;
    private LinearLayout ll_classification;
    private LinearLayout ll_capacity;
    private List<InternalPeripheral> internalPeripheralList;
    private final ArrayList<String> internalperipherallist = new ArrayList<>();
    private TextView tv_spinnerAsset;
    private AutoCompleteTextView dd_capacity;
    private ArrayAdapter<String> internalPeripheralAdapter;
    private ArrayAdapter<Integer> capacityAdapter;
    private List<RamCapacity> capacityList;
    private String[] externalperipherallist = new String[0];
    private final ArrayList<Integer> capacitylist = new ArrayList<>();
    private List<ExternalPeripheral> externalPeripheralList;
    //private ArrayList<String> externalperipherallist = new ArrayList<>();
    private RadioGroup rg_assetType;
    private TextInputLayout il_capacity;
    private LinearLayout ll_hddingb;
    private LinearLayout ll_assets;
    private List<MyKit> kitList;
    private final ArrayList kitlist = new ArrayList<>();
    private AutoCompleteTextView dd_kit;
    private ArrayAdapter<String> kitAdapter;
    private RadioButton rb_action;
    private RadioButton rb_addToAsset;
    private RadioButton rb_removeAsset;
    private RadioButton rb_internal;
    private RadioButton rb_external;
    private String capacity_id="";
    private List<Integer> capacityArray=new ArrayList<>();
    private boolean[] selectedAsset;
    private List<Integer> assetArray=new ArrayList<>();
    private String kit_id="";
    private String asset_history_ids="";
    private EditText et_disksize;
    private RadioButton radio_hdd_gb;
    private RadioButton radio_hdd_tb;
    private RadioButton rb_mainAsset;
    private String remove_asset_type="Internal";
    private TextView tv_sloc;
    private LinearLayout ll_sloc;
    private RadioGroup rg_kitOrDiscard;
    private TextInputLayout il_kit;
    private String removeType="";
    private LinearLayout ll_primary_loc;
    private LinearLayout ll_secondary_loc;
    private LinearLayout ll_storageSection;
    private AutoCompleteTextView spinner_primary_loc;
    private AutoCompleteTextView spinner_sec_loc;
    private AutoCompleteTextView spinner_storageType;
    private AutoCompleteTextView spinner_storageSection;
    private EditText et_boxNumber;
    private List<Datum> storageSections=new ArrayList<>();
    private final ArrayList<String> storageSections_list=new ArrayList<>();
    private List<com.vnrseeds.samadhan.parser.storagetypeparser.Datum> storageTypes=new ArrayList<>();
    private final ArrayList<String> storageTypes_list=new ArrayList<>();
    private List<com.vnrseeds.samadhan.parser.secondarylocparser.Datum> secondaryLocations=new ArrayList<>();
    private final ArrayList<String> secondaryLoc_list=new ArrayList<>();
    private List<com.vnrseeds.samadhan.parser.primarylocparser.Datum> primaryLocations=new ArrayList<>();
    private String secondaryLoc_id="";
    private String storageType_id="";
    private String storageSections_id="";
    private String primaryLoc_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_asset);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(AddToAssetActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add to asset");

        back_nav = findViewById(R.id.back_nav);
        tv_availabletQty = findViewById(R.id.tv_availabletQty);
        et_newAssetQty = findViewById(R.id.et_newAssetQty);
        TextView tv_ticketdate = findViewById(R.id.tv_ticketdate);
        TextView tv_ticketno = findViewById(R.id.tv_ticketno);
        TextView tv_ticket_title = findViewById(R.id.tv_ticket_title);
        TextView tv_priority = findViewById(R.id.tv_priority);
        rg_action = findViewById(R.id.rg_action);
        rg_assetType = findViewById(R.id.rg_assetType);
        ll_storeQty = findViewById(R.id.ll_storeQty);
        ll_newQty = findViewById(R.id.ll_newQty);
        ll_assetType = findViewById(R.id.ll_assetType);
        ll_classification = findViewById(R.id.ll_classification);
        ll_capacity = findViewById(R.id.ll_capacity);
        button_submit = findViewById(R.id.button_submit);
        dd_assetClassification = findViewById(R.id.dd_assetClassification);
        dd_assetCategory = findViewById(R.id.dd_assetCategory);
        dd_assetName = findViewById(R.id.dd_assetName);
        tv_spinnerAsset = findViewById(R.id.tv_spinnerAsset);
        dd_capacity = findViewById(R.id.dd_capacity);
        il_capacity = findViewById(R.id.il_capacity);
        ll_hddingb = findViewById(R.id.ll_hddingb);
        ll_assets = findViewById(R.id.ll_assets);
        ll_sloc = findViewById(R.id.ll_sloc);
        dd_kit = findViewById(R.id.dd_kit);
        rb_addToAsset = findViewById(R.id.rb_addToAsset);
        rb_removeAsset = findViewById(R.id.rb_removeAsset);
        rb_internal = findViewById(R.id.rb_internal);
        rb_external = findViewById(R.id.rb_external);
        rb_mainAsset = findViewById(R.id.rb_mainAsset);
        et_disksize = findViewById(R.id.et_disksize);
        radio_hdd_gb = findViewById(R.id.radio_hdd_gb);
        radio_hdd_tb = findViewById(R.id.radio_hdd_tb);
        tv_sloc = findViewById(R.id.tv_sloc);
        rg_kitOrDiscard = findViewById(R.id.rg_kitOrDiscard);
        il_kit = findViewById(R.id.il_kit);
        ll_primary_loc = findViewById(R.id.ll_primary_loc);
        ll_secondary_loc = findViewById(R.id.ll_secondary_loc);
        ll_storageSection = findViewById(R.id.ll_storageSection);
        spinner_primary_loc = findViewById(R.id.spinner_primary_loc);
        spinner_sec_loc = findViewById(R.id.spinner_sec_loc);
        spinner_storageType = findViewById(R.id.spinner_storageType);
        spinner_storageSection = findViewById(R.id.spinner_storageSection);
        et_boxNumber = findViewById(R.id.et_boxNumber);

        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
        tv_ticketno.setText(ticketDetailsPojo.getTicketNo());
        tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
        tv_priority.setText(ticketDetailsPojo.getPriority());

        if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical") || ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
            tv_priority.setTextColor(Color.RED);
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Mediaum")){
            tv_priority.setTextColor(Color.parseColor("#FF9800"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low") || ticketDetailsPojo.getPriority().equalsIgnoreCase("Normal")){
            tv_priority.setTextColor(Color.parseColor("#3F5F36"));
        }


        categoryAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, categorylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_assetCategory.setAdapter(categoryAdapter);

        classificationAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, classificationlist);
        classificationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_assetClassification.setAdapter(classificationAdapter);

        consumableAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, consumalelist);
        consumableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_assetName.setAdapter(consumableAdapter);

        capacityAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, capacitylist);
        capacityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_capacity.setAdapter(capacityAdapter);

        kitAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, kitlist);
        kitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_kit.setAdapter(kitAdapter);
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(view -> {
            Intent intent = new Intent(AddToAssetActivity.this, TicketHandlingActivity.class);
            startActivity(intent);
            finish();
        });

        getClassification();
        //Log.e("ServiceTypeID", ticketDetailsPojo.getTicket_service_type_id());
        getRemovableAssets(ticketDetailsPojo.getTicket_service_type_id());
        getPrimaryLoc();

        rg_assetType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb.getText().equals("In-Built")) {
                    tv_spinnerAsset.setVisibility(View.GONE);
                    ll_assets.setVisibility(View.VISIBLE);
                    /*il_capacity.setVisibility(View.VISIBLE);
                    ll_hddingb.setVisibility(View.VISIBLE);*/
                    remove_asset_type="Internal";
                    Log.e("Internal Peripheral : ", String.valueOf(internalperipherallist));
                    consumableAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, internalperipherallist);
                    consumableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_assetName.setAdapter(consumableAdapter);
                }else if (rb.getText().equals("Additional")) {
                    tv_spinnerAsset.setVisibility(View.VISIBLE);
                    ll_assets.setVisibility(View.GONE);
                    ll_hddingb.setVisibility(View.GONE);
                    il_capacity.setVisibility(View.GONE);
                    remove_asset_type="External";
                    /*il_capacity.setVisibility(View.GONE);
                    ll_hddingb.setVisibility(View.GONE);*/
                }else {
                    remove_asset_type="Main";
                    tv_spinnerAsset.setVisibility(View.GONE);
                    ll_assets.setVisibility(View.GONE);
                    ll_hddingb.setVisibility(View.GONE);
                    il_capacity.setVisibility(View.GONE);
                }
            }
        });

        rg_action.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                rb_action = findViewById(checkedId);
                if (rb_action.getText().equals("Add to Asset")) {
                    ll_assetType.setVisibility(View.GONE);
                    ll_classification.setVisibility(View.VISIBLE);
                    ll_capacity.setVisibility(View.GONE);
                    ll_assets.setVisibility(View.VISIBLE);
                    tv_sloc.setText("");
                    getClassification();
                    dd_assetName.setText("Select");
                    consumalelist.clear();
                    consumableAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, consumalelist);
                    consumableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_assetName.setAdapter(consumableAdapter);
                    button_submit.setText("Add Asset");
                    if (classification_id.equalsIgnoreCase("3")) {
                        ll_storeQty.setVisibility(View.VISIBLE);
                        ll_newQty.setVisibility(View.VISIBLE);
                        ll_sloc.setVisibility(View.GONE);
                    }else {
                        ll_storeQty.setVisibility(View.GONE);
                        ll_newQty.setVisibility(View.GONE);
                        ll_sloc.setVisibility(View.VISIBLE);
                    }
                } else if (rb_action.getText().equals("Remove Asset")) {
                    ll_assetType.setVisibility(View.VISIBLE);
                    ll_classification.setVisibility(View.GONE);
                    ll_capacity.setVisibility(View.VISIBLE);
                    button_submit.setText("Add to Kit");
                    ll_storeQty.setVisibility(View.GONE);
                    ll_newQty.setVisibility(View.GONE);
                    ll_sloc.setVisibility(View.GONE);
                    classification_id="";
                    categorylist.clear();
                    dd_assetCategory.setText("Select");
                    categoryAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, categorylist);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_assetCategory.setAdapter(categoryAdapter);
                    classificationlist.clear();
                    dd_assetClassification.setText("Select");
                    classificationAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, classificationlist);
                    classificationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_assetClassification.setAdapter(classificationAdapter);

                    if (rb_internal.isChecked()){
                        //getRemovableAssets(ticketDetailsPojo.getTicket_service_type_id());
                        asset_id="";
                        dd_assetName.setText("Select");
                        consumableAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, internalperipherallist);
                        consumableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dd_assetName.setAdapter(consumableAdapter);
                    }else if (rb_external.isChecked()){
                        tv_spinnerAsset.setVisibility(View.VISIBLE);
                        ll_assets.setVisibility(View.GONE);
                        ll_hddingb.setVisibility(View.GONE);
                        il_capacity.setVisibility(View.GONE);
                    }else {
                        tv_spinnerAsset.setVisibility(View.GONE);
                        ll_assets.setVisibility(View.GONE);
                        ll_hddingb.setVisibility(View.GONE);
                        il_capacity.setVisibility(View.GONE);
                    }
                }
            }
        });

        rg_kitOrDiscard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb_removeAction = findViewById(checkedId);
                if (rb_removeAction.getText().equals("Kit")){
                    il_kit.setVisibility(View.VISIBLE);
                    removeType = "Kit";
                    button_submit.setText("Add to Kit");
                    ll_primary_loc.setVisibility(View.GONE);
                    ll_secondary_loc.setVisibility(View.GONE);
                    ll_storageSection.setVisibility(View.GONE);
                }else if (rb_removeAction.getText().equals("Store")){
                    il_kit.setVisibility(View.GONE);
                    removeType = "Store";
                    ll_primary_loc.setVisibility(View.VISIBLE);
                    ll_secondary_loc.setVisibility(View.VISIBLE);
                    ll_storageSection.setVisibility(View.VISIBLE);
                    button_submit.setText("Add to Store");
                }else if (rb_removeAction.getText().equals("Discard")){
                    il_kit.setVisibility(View.GONE);
                    removeType = "Discard";
                    ll_primary_loc.setVisibility(View.GONE);
                    ll_secondary_loc.setVisibility(View.GONE);
                    ll_storageSection.setVisibility(View.GONE);
                    button_submit.setText("Discard");
                }
            }
        });

        spinner_primary_loc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    spinner_sec_loc.setText("Select");
                    spinner_storageType.setText("Select");
                    spinner_storageSection.setText("Select");
                    secondaryLoc_list.clear();
                    storageTypes_list.clear();
                    storageSections_list.clear();

                    secondaryLoc_id="";
                    storageType_id="";
                    storageSections_id="";

                    ArrayAdapter<String> secondaryLocadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, secondaryLoc_list);
                    secondaryLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_sec_loc.setAdapter(secondaryLocadapter);

                    ArrayAdapter<String> storageTypeadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, storageTypes_list);
                    storageTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageType.setAdapter(storageTypeadapter);

                    ArrayAdapter<String> storageSectionadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, storageSections_list);
                    storageSectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageSection.setAdapter(storageSectionadapter);

                    primaryLoc_id = String.valueOf(primaryLocations.get(i).getId());
                    getSecondaryLoc(primaryLoc_id);
                }
            }
        });

        spinner_sec_loc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    spinner_storageType.setText("Select");
                    spinner_storageSection.setText("Select");
                    storageTypes_list.clear();
                    storageSections_list.clear();

                    storageType_id="";
                    storageSections_id="";

                    ArrayAdapter<String> storageTypeadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, storageTypes_list);
                    storageTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageType.setAdapter(storageTypeadapter);

                    ArrayAdapter<String> storageSectionadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, storageSections_list);
                    storageSectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageSection.setAdapter(storageSectionadapter);

                    secondaryLoc_id = String.valueOf(secondaryLocations.get(i).getId());
                    getStorageType(secondaryLoc_id);
                }
            }
        });

        spinner_storageType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {

                    spinner_storageSection.setText("Select");
                    storageSections_list.clear();

                    storageSections_id="";

                    ArrayAdapter<String> storageSectionadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, storageSections_list);
                    storageSectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageSection.setAdapter(storageSectionadapter);

                    storageType_id = String.valueOf(storageTypes.get(i).getId());
                    getStorageSection(storageType_id);
                }
            }
        });

        spinner_storageSection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    storageSections_id = String.valueOf(storageSections.get(i).getId());
                }
            }
        });

        dd_capacity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>=0) {
                    capacity_id = String.valueOf(capacityList.get(i).getRcId());
                }
            }
        });

        dd_kit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>=0) {
                    kit_id = String.valueOf(kitList.get(i).getKitId());
                }
            }
        });

        dd_assetClassification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ac_id="";
                asset_id="";
                //dd_assetCategory.setText("Select");
                dd_assetName.setText("Select");
                tv_sloc.setText("");
                if (i>=0) {
                    classification_id = String.valueOf(classifications.get(i).getClassificationId());
                    getAssetCategoryList(classification_id);
                    //Toast.makeText(getApplicationContext(), dd_assetClassification.getText().toString() + "-" + classifications.get(i).getClassificationId(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        dd_assetCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>=0) {
                    ac_id = String.valueOf(assetCategory.get(i).getAcId());
                    asset_id="";
                    dd_assetName.setText("Select");
                    tv_sloc.setText("");
                    String consumable = dd_assetClassification.getText().toString().trim();
                    Log.e(TAG, "AssetID:"+ac_id);
                    if (consumable.equalsIgnoreCase("Consumables")) {
                        ll_storeQty.setVisibility(View.VISIBLE);
                        ll_newQty.setVisibility(View.VISIBLE);
                        tv_availabletQty.setText("0");
                        getConsumableList(ac_id);
                    }else {
                        ll_storeQty.setVisibility(View.GONE);
                        ll_newQty.setVisibility(View.GONE);
                        getPeripheralList(ac_id);
                    }
                    Toast.makeText(getApplicationContext(), dd_assetCategory.getText().toString() + "-" + assetCategory.get(i).getAcId(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dd_assetName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>=0) {
                    Log.e(TAG, "AssetID:"+ac_id);
                    if (rb_removeAsset.isChecked()) {
                        String assetName = String.valueOf(internalPeripheralList.get(i).getAcCode());
                        if (assetName.equalsIgnoreCase("RAMS")){
                            il_capacity.setVisibility(View.VISIBLE);
                            ll_hddingb.setVisibility(View.GONE);
                        }else {
                            il_capacity.setVisibility(View.GONE);
                        }
                        if (assetName.equalsIgnoreCase("HDDS") || assetName.equalsIgnoreCase("SSDS")){
                            ll_hddingb.setVisibility(View.VISIBLE);
                            il_capacity.setVisibility(View.GONE);
                        }else {
                            ll_hddingb.setVisibility(View.GONE);
                        }
                    }
                    //tv_sloc.setText(peripheralList.get(i).getPrimaryStorage()+"/"+peripheralList.get(i).getSecondaryStorage()+"/"+peripheralList.get(i).getStorageType()+"/"+peripheralList.get(i).getSectionName()+"/"+peripheralList.get(i).getAssetBoxNumber());
                    if (classification_id.equalsIgnoreCase("3")) {
                        asset_id = String.valueOf(consumaleList.get(i).getAcId());
                        tv_availabletQty.setText(String.valueOf(consumaleList.get(i).getAvailable()));
                        storeQty = consumaleList.get(i).getAvailable();
                        String sloc="";
                        if (consumaleList.get(i).getPrimaryStorage()!=null || !consumaleList.get(i).getPrimaryStorage().equalsIgnoreCase("")){
                            sloc=consumaleList.get(i).getPrimaryStorage();
                        }
                        if (consumaleList.get(i).getSecondaryStorage()!=null || !consumaleList.get(i).getSecondaryStorage().equalsIgnoreCase("")){
                            sloc=sloc+"/"+consumaleList.get(i).getSecondaryStorage();
                        }
                        if (consumaleList.get(i).getStorageType()!=null || !consumaleList.get(i).getStorageType().equalsIgnoreCase("")){
                            sloc=sloc+"/"+consumaleList.get(i).getStorageType();
                        }
                        if (consumaleList.get(i).getSectionName()!=null || !consumaleList.get(i).getSectionName().equalsIgnoreCase("")){
                            sloc=sloc+"/"+consumaleList.get(i).getSectionName();
                        }
                        if (consumaleList.get(i).getAssetBoxNumber()!=null || !consumaleList.get(i).getAssetBoxNumber().equalsIgnoreCase("")){
                            sloc=sloc+"/"+consumaleList.get(i).getAssetBoxNumber();
                        }
                        tv_sloc.setText(sloc);
                    }else {
                        if (rb_removeAsset.isChecked()){
                            asset_id = String.valueOf(internalPeripheralList.get(i).getAcId());
                        }else {
                            asset_id = String.valueOf(peripheralList.get(i).getAssetId());
                            String sloc="";
                            if (peripheralList.get(i).getPrimaryStorage()!=null || !peripheralList.get(i).getPrimaryStorage().equalsIgnoreCase("")){
                                sloc=peripheralList.get(i).getPrimaryStorage();
                            }
                            if (peripheralList.get(i).getSecondaryStorage()!=null || !peripheralList.get(i).getSecondaryStorage().equalsIgnoreCase("")){
                                sloc=sloc+"/"+peripheralList.get(i).getSecondaryStorage();
                            }
                            if (peripheralList.get(i).getStorageType()!=null || !peripheralList.get(i).getStorageType().equalsIgnoreCase("")){
                                sloc=sloc+"/"+peripheralList.get(i).getStorageType();
                            }
                            if (peripheralList.get(i).getSectionName()!=null || !peripheralList.get(i).getSectionName().equalsIgnoreCase("")){
                                sloc=sloc+"/"+peripheralList.get(i).getSectionName();
                            }
                            if (peripheralList.get(i).getAssetBoxNumber()!=null || !peripheralList.get(i).getAssetBoxNumber().equalsIgnoreCase("")){
                                sloc=sloc+"/"+peripheralList.get(i).getAssetBoxNumber();
                            }
                            tv_sloc.setText(sloc);
                            //tv_sloc.setText(peripheralList.get(i).getPrimaryStorage()+"/"+peripheralList.get(i).getSecondaryStorage()+"/"+peripheralList.get(i).getStorageType()+"/"+peripheralList.get(i).getSectionName()+"/"+peripheralList.get(i).getAssetBoxNumber());
                        }
                    }
                }
            }
        });

        tv_spinnerAsset.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddToAssetActivity.this);
            builder.setTitle("Select Asset");
            builder.setCancelable(false);
            builder.setMultiChoiceItems(externalperipherallist, selectedAsset, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    if (b) {
                        assetArray.add(i);
                        Collections.sort(assetArray);
                    } else {
                        assetArray.remove(Integer.valueOf(i));
                    }
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < assetArray.size(); j++) {
                        stringBuilder.append(externalperipherallist[assetArray.get(j)]);
                        Log.e(TAG, externalperipherallist[assetArray.get(j)]);
                        if (j != assetArray.size() - 1) {
                            stringBuilder.append(",");
                        }
                    }
                    asset_history_ids="";
                    for (int k = 0; k < assetArray.size(); k++) {
                        int ind = assetArray.get(k);
                        if (asset_history_ids.equalsIgnoreCase("")) {
                            asset_history_ids = String.valueOf(externalPeripheralList.get(ind).getAssetHistoryId());
                        } else {
                            asset_history_ids = asset_history_ids + "," + externalPeripheralList.get(ind).getAssetHistoryId();
                        }
                    }
                    tv_spinnerAsset.setText(stringBuilder.toString());
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    asset_history_ids="";
                    for (int j = 0; j < selectedAsset.length; j++) {
                        selectedAsset[j] = false;
                        assetArray.clear();
                        tv_spinnerAsset.setText("");
                    }
                }
            });
            builder.show();
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb_removeAsset.isChecked()){
                    String diskSize=et_disksize.getText().toString().trim();
                    String disk_size_unit="";
                    if (radio_hdd_gb.isChecked()){
                        disk_size_unit="GB";
                    }else if (radio_hdd_tb.isChecked()){
                        disk_size_unit="TB";
                    }
                    if (removeType.equalsIgnoreCase("Kit") && kit_id.equalsIgnoreCase("")){
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Select kit");
                    }else if (rb_external.isChecked() && asset_history_ids.equalsIgnoreCase("")){
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Select assets");
                    }else if (removeType.equalsIgnoreCase("Store") && primaryLoc_id.equalsIgnoreCase("")){
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Select primary location");
                    }else if (removeType.equalsIgnoreCase("Store") && secondaryLoc_id.equalsIgnoreCase("")){
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Select secondary location");
                    }else if (removeType.equalsIgnoreCase("Store") && storageType_id.equalsIgnoreCase("")){
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Select storage type");
                    }else if (rb_internal.isChecked() && asset_id.equalsIgnoreCase("")){
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Select assets");
                    }else {
                        submitRemoveAsset(ticketDetailsPojo.getTicketId(),kit_id,asset_history_ids,asset_id,capacity_id,diskSize,disk_size_unit,remove_asset_type);
                    }
                }else {
                    String qty = et_newAssetQty.getText().toString().trim();
                    if (classification_id.equalsIgnoreCase("3") && ac_id.equalsIgnoreCase("")) {
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Select asset category");
                    } else if (classification_id.equalsIgnoreCase("3") && qty.equalsIgnoreCase("")) {
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Enter Quantity");
                    } else if (classification_id.equalsIgnoreCase("3") && Integer.parseInt(qty) > storeQty) {
                        Utils.getInstance().showAlert(AddToAssetActivity.this, "Quantity should be less than or equal to store quantity");
                    } else {
                        submitAddToAsset(asset_id, ac_id, qty);
                    }
                }
            }
        });
    }

    private void submitRemoveAsset(String ticketId, String kit_id, String asset_history_ids, String asset_id, String capacity_id, String diskSize, String disk_size_unit, String remove_asset_type) {
        Log.e(TAG, ticketId+"="+kit_id+"="+asset_history_ids+"="+asset_id+"="+capacity_id+"="+diskSize+"="+disk_size_unit);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        String boxNumber = et_boxNumber.getText().toString().trim();
        Call<SubmitSuccessResponse> call = apiInterface.getRemoveAssetSubmitInfo("application/json", "Bearer " + token,ticketId, kit_id, asset_history_ids, asset_id, capacity_id, diskSize, disk_size_unit, remove_asset_type, removeType, primaryLoc_id, secondaryLoc_id, storageType_id, storageSections_id, boxNumber);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(AddToAssetActivity.this, TicketsListActivity.class);
                        Toast.makeText(AddToAssetActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<SubmitSuccessResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getRemovableAssets(String ticket_service_type_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<RemovableAssetsResponse> call = apiInterface.getRemovableAssets("application/json", "Bearer "+token, ticket_service_type_id);
        //Log.e(TAG, token+"====="+ticket_service_type_id);
        call.enqueue(new Callback<RemovableAssetsResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<RemovableAssetsResponse> call, @NotNull Response<RemovableAssetsResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    RemovableAssetsResponse removableAssetsResponse = response.body();
                    assert removableAssetsResponse != null;
                    internalPeripheralList = removableAssetsResponse.getData().getInternalPeripherals();
                    internalperipherallist.clear();

                    for (InternalPeripheral obj:internalPeripheralList){
                        if (obj.getAcName()!=null) {
                            internalperipherallist.add(obj.getAcName());
                        }
                    }

                    externalPeripheralList = removableAssetsResponse.getData().getExternalPeripherals();
                    ArrayList<String> externalperipherallist1 = new ArrayList<>();
                    for (ExternalPeripheral obj:externalPeripheralList){
                        if (obj.getAssetConsumableName()!=null) {
                            externalperipherallist1.add(obj.getAssetConsumableName());
                        }
                    }

                    externalperipherallist = externalperipherallist1.toArray(new String[0]);
                    selectedAsset = new boolean[externalperipherallist.length];

                    capacityList = removableAssetsResponse.getData().getRamCapacityList();
                    capacitylist.clear();
                    for (RamCapacity obj:capacityList){
                        capacitylist.add(obj.getRcCapacity());
                    }
                    dd_capacity.setAdapter(capacityAdapter);
                    capacityAdapter.notifyDataSetChanged();

                    kitList = removableAssetsResponse.getData().getMyKits();
                    kitlist.clear();
                    for (MyKit obj:kitList){
                        kitlist.add(obj.getKitName());
                    }
                    dd_kit.setAdapter(kitAdapter);
                    kitAdapter.notifyDataSetChanged();
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<RemovableAssetsResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void submitAddToAsset(String asset_id, String ac_id, String qty) {
        Log.e(TAG, ticketDetailsPojo.getTicketId()+"="+asset_id+"="+ac_id+"="+qty);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getAddToAssetSubmitInfo("application/json", "Bearer " + token,ticketDetailsPojo.getTicketId(), asset_id, ac_id, qty);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(AddToAssetActivity.this, TicketsListActivity.class);
                        Toast.makeText(AddToAssetActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<SubmitSuccessResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getPeripheralList(String ac_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<PeripheralListResponse> call = apiInterface.getPeripheralInfo("application/json", "Bearer "+token, ac_id);
        Log.e("Peripherals", token+"====="+ac_id);
        call.enqueue(new Callback<PeripheralListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<PeripheralListResponse> call, @NotNull Response<PeripheralListResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    PeripheralListResponse peripheralListResponse = response.body();
                    assert peripheralListResponse != null;
                    peripheralList = peripheralListResponse.getData().getAssets();
                    consumalelist.clear();
                    //categorylist.add("Select");
                    //Log.e(TAG, String.valueOf(assetCategory));
                    //dd_assetCategory.setText("Select");
                    for (com.vnrseeds.samadhan.parser.addtoassetperipherallistparser.Asset obj:peripheralList){
                        consumalelist.add(obj.getAssetConsumableName()+" - "+obj.getAssetSerialNo());
                    }
                    Log.e("Peripherals", String.valueOf(consumalelist));
                    consumableAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, consumalelist);
                    consumableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_assetName.setAdapter(consumableAdapter);
                    consumableAdapter.notifyDataSetChanged();
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PeripheralListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getConsumableList(String ac_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<AddToAssetConsumablesListResponse> call = apiInterface.getConsumalesInfo("application/json", "Bearer "+token, ac_id);
        Log.e("Consumables", token+"====="+ac_id);
        call.enqueue(new Callback<AddToAssetConsumablesListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<AddToAssetConsumablesListResponse> call, @NotNull Response<AddToAssetConsumablesListResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    AddToAssetConsumablesListResponse addToAssetConsumablesListResponse = response.body();
                    assert addToAssetConsumablesListResponse != null;
                    consumaleList = addToAssetConsumablesListResponse.getData().getAssets();
                    consumalelist.clear();
                    //categorylist.add("Select");
                    //Log.e(TAG, String.valueOf(assetCategory));
                    //dd_assetCategory.setText("Select");
                    for (Asset obj:consumaleList){
                        consumalelist.add(obj.getAcName());
                    }
                    Log.e("Consumables", String.valueOf(consumalelist));
                    consumableAdapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, consumalelist);
                    consumableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_assetName.setAdapter(consumableAdapter);
                    consumableAdapter.notifyDataSetChanged();
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddToAssetConsumablesListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getAssetCategoryList(String classification_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<AssetCategoryListResponse> call = apiInterface.getAssetCategoryInfo("application/json", "Bearer "+token, classification_id, "", ticketDetailsPojo.getTicketAssetCatagoryID());
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
                    for (com.vnrseeds.samadhan.parser.assetcategoryparser.Data obj:assetCategory){
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
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
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

    private void getClassification() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<ClassificationResponse> call = apiInterface.getClassificationInfo("application/json", "Bearer "+token, "ADD_TO_ASSET");
        call.enqueue(new Callback<ClassificationResponse>() {
            @Override
            public void onResponse(@NotNull Call<ClassificationResponse> call, @NotNull Response<ClassificationResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    ClassificationResponse classificationResponse = response.body();
                    assert classificationResponse != null;
                    classifications = classificationResponse.getData();
                    classificationlist.clear();
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
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
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

    private void getStorageSection(String storageType_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<StorageSectionResponse> call = apiInterface.getStorageSectionData("application/json", "Bearer " + token,storageType_id);
        Log.e(TAG, String.valueOf(call));
        call.enqueue(new Callback<StorageSectionResponse>() {
            @Override
            public void onResponse(@NonNull Call<StorageSectionResponse> call, @NonNull Response<StorageSectionResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    StorageSectionResponse storageSectionResponse = response.body();
                    assert storageSectionResponse != null;
                    storageSections.clear();
                    storageSections_list.clear();
                    storageSections = storageSectionResponse.getData();
                    for (com.vnrseeds.samadhan.parser.storagesectionparser.Datum obj : storageSections) {
                        storageSections_list.add(obj.getSectionName());
                    }
                    ArrayAdapter<String> storageSectionadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, storageSections_list);
                    storageSectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageSection.setAdapter(storageSectionadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<StorageSectionResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(AddToAssetActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getStorageType(String secondaryLoc_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<StorageTypeResponse> call = apiInterface.getStorageTypeData("application/json", "Bearer " + token,secondaryLoc_id);
        Log.e(TAG, String.valueOf(call));
        call.enqueue(new Callback<StorageTypeResponse>() {
            @Override
            public void onResponse(@NonNull Call<StorageTypeResponse> call, @NonNull Response<StorageTypeResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    StorageTypeResponse storageTypeResponse = response.body();
                    storageTypes.clear();
                    assert storageTypeResponse != null;
                    storageTypes = storageTypeResponse.getData();
                    storageTypes_list.clear();
                    for (com.vnrseeds.samadhan.parser.storagetypeparser.Datum obj : storageTypes) {
                        storageTypes_list.add(obj.getStorageType());
                    }
                    ArrayAdapter<String> storageTypeadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, storageTypes_list);
                    storageTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageType.setAdapter(storageTypeadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<StorageTypeResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(AddToAssetActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSecondaryLoc(String primaryLoc_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SecondaryLocationResponse> call = apiInterface.getSecondaryLocData("application/json", "Bearer " + token,primaryLoc_id);
        Log.e(TAG, String.valueOf(call));
        call.enqueue(new Callback<SecondaryLocationResponse>() {
            @Override
            public void onResponse(@NonNull Call<SecondaryLocationResponse> call, @NonNull Response<SecondaryLocationResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SecondaryLocationResponse secondaryLocationResponse = response.body();
                    assert secondaryLocationResponse != null;
                    secondaryLoc_list.clear();
                    secondaryLocations.clear();
                    secondaryLocations = secondaryLocationResponse.getData();
                    for (com.vnrseeds.samadhan.parser.secondarylocparser.Datum obj : secondaryLocations) {
                        secondaryLoc_list.add(obj.getSecondaryLocationName());
                    }
                    ArrayAdapter<String> secondaryLocadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, secondaryLoc_list);
                    secondaryLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_sec_loc.setAdapter(secondaryLocadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SecondaryLocationResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(AddToAssetActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPrimaryLoc() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<PrimaryLocationsResponse> call = apiInterface.getPrimaryLocData("application/json", "Bearer " + token);
        Log.e(TAG, String.valueOf(call));
        call.enqueue(new Callback<PrimaryLocationsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PrimaryLocationsResponse> call, @NonNull Response<PrimaryLocationsResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    PrimaryLocationsResponse primaryLocationsResponse = response.body();
                    assert primaryLocationsResponse != null;

                    primaryLocations = primaryLocationsResponse.getData();
                    ArrayList<String> primaryLoc_list = new ArrayList<>();
                    for (com.vnrseeds.samadhan.parser.primarylocparser.Datum obj : primaryLocations) {
                        primaryLoc_list.add(obj.getPrimaryLocationName());
                    }
                    ArrayAdapter<String> primaryLocadapter = new ArrayAdapter<>(AddToAssetActivity.this, android.R.layout.simple_spinner_item, primaryLoc_list);
                    primaryLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_primary_loc.setAdapter(primaryLocadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddToAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddToAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<PrimaryLocationsResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(AddToAssetActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddToAssetActivity.this, TicketHandlingActivity.class);
        startActivity(intent);
        finish();
    }
}