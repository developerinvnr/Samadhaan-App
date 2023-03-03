package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.SRNListAdapter;
import com.vnrseeds.samadhan.addassetforms.Utility;
import com.vnrseeds.samadhan.parser.primarylocparser.PrimaryLocationsResponse;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.secondarylocparser.SecondaryLocationResponse;
import com.vnrseeds.samadhan.parser.srnlistparser.SRNListResponse;
import com.vnrseeds.samadhan.parser.srnlistparser.Srn;
import com.vnrseeds.samadhan.parser.storagesectionparser.StorageSectionResponse;
import com.vnrseeds.samadhan.parser.storagetypeparser.StorageTypeResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.AppConfig;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SRNListActivity extends AppCompatActivity implements SRNListAdapter.SRNListAdapterListener{

    private static final String TAG = "SRNListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private ImageButton back_nav;
    private String token;
    private RecyclerView lv_itnlist;
    private TicketDetailsPojo ticketDetailsPojo;
    private List<Srn> srnListData=new ArrayList<>();
    private SRNListAdapter listAdapter;
    private LinearLayout ll_issuephoto;
    private ImageView iv_issuephoto;


    private static final int PERMISSION_CODE = 1234;
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    private String path_billcopy;
    private final List<MultipartBody.Part> list = new ArrayList<>();
    private File BILL_COPY;
    private Uri image_uri;
    private String userChoosenTask;
    private String srn_received_for="Self";
    private String srn_received_transfer_to="Store";
    private List<com.vnrseeds.samadhan.parser.primarylocparser.Datum> primaryLocations;
    private AutoCompleteTextView spinner_primary_loc;
    private AutoCompleteTextView spinner_sec_loc;
    private AutoCompleteTextView spinner_storageType;
    private AutoCompleteTextView spinner_storageSection;
    private String primaryLoc_id="";
    private List<com.vnrseeds.samadhan.parser.secondarylocparser.Datum> secondaryLocations=new ArrayList<>();
    private String secondaryLoc_id="";
    private List<com.vnrseeds.samadhan.parser.storagetypeparser.Datum> storageTypes=new ArrayList<>();
    private String storageType_id="";
    private List<com.vnrseeds.samadhan.parser.storagesectionparser.Datum> storageSections=new ArrayList<>();
    ArrayList<String> storageSections_list = new ArrayList<>();
    ArrayList<String> storageTypes_list = new ArrayList<>();
    ArrayList<String> secondaryLoc_list = new ArrayList<>();
    private String storageSections_id="";
    private RoleResponse roleResponse;
    private FloatingActionButton btn_generateSRN;
    private ImageView iv_noDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srnlist);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(SRNListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        btn_generateSRN = findViewById(R.id.btn_generateSRN);
        iv_noDataFound = findViewById(R.id.iv_noDataFound);
        toolbar_title.setText("SRN List");
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        lv_itnlist = findViewById(R.id.lv_itnlist);
        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") && ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
            btn_generateSRN.setVisibility(View.GONE);
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
                    Intent intent = new Intent(SRNListActivity.this, TicketHandlingUserActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SRNListActivity.this, TicketHandlingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_generateSRN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(SRNListActivity.this, ServiceRepaireNoteActivity.class);
                startActivity(intent2);
                finish();
            }
        });
        getSRNList();
    }

    private void getSRNList() {
        Log.e("Ticket ID:", ticketDetailsPojo.getTicketId());
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SRNListResponse> call = apiInterface.getSRNList("application/json", "Bearer " + token,ticketDetailsPojo.getTicketId());
        call.enqueue(new Callback<SRNListResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<SRNListResponse> call, @NotNull Response<SRNListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    srnListData.clear();
                    SRNListResponse srnListResponse = response.body();
                    assert srnListResponse != null;
                    srnListData = srnListResponse.getData().getSrnList();
                    Log.e(TAG, String.valueOf(srnListData));

                    if (srnListResponse.getData().getCanCreateSRN()==1){
                        btn_generateSRN.setVisibility(View.VISIBLE);
                    }else {
                        btn_generateSRN.setVisibility(View.GONE);
                    }

                    if (!srnListData.isEmpty()) {
                        iv_noDataFound.setVisibility(View.GONE);
                        listAdapter = new SRNListAdapter(SRNListActivity.this, srnListData, SRNListActivity.this);
                        lv_itnlist.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        lv_itnlist.setLayoutManager(mLayoutManager);
                        lv_itnlist.setItemAnimator(new DefaultItemAnimator());
                        lv_itnlist.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }else {
                        lv_itnlist.setVisibility(View.GONE);
                        iv_noDataFound.setVisibility(View.VISIBLE);
                        //Utils.getInstance().showAlert(SRNListActivity.this, "Data not found");
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(SRNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(SRNListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SRNListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onContactSelected(Srn datum) {

    }

    @SuppressLint("SetTextI18n")
    public void setReceived(Srn datum) {
        final Dialog dialog = new Dialog(SRNListActivity.this);
        dialog.setContentView(R.layout.custom_itn_receive_note);
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
        TextView tv_issuephoto = dialog.findViewById(R.id.tv_issuephoto);
        TextInputLayout til_lable = dialog.findViewById(R.id.til_lable);
        RadioGroup rg_receivingFor = dialog.findViewById(R.id.rg_receivingFor);
        RadioGroup rg_action = dialog.findViewById(R.id.rg_action);
        LinearLayout ll_itnFor = dialog.findViewById(R.id.ll_itnFor);
        LinearLayout ll_srn_received_transfer_to = dialog.findViewById(R.id.ll_srn_received_transfer_to);
        LinearLayout ll_primary_loc = dialog.findViewById(R.id.ll_primary_loc);
        LinearLayout ll_secondary_loc = dialog.findViewById(R.id.ll_secondary_loc);
        LinearLayout ll_storageSection = dialog.findViewById(R.id.ll_storageSection);
        spinner_primary_loc = dialog.findViewById(R.id.spinner_primary_loc);
        spinner_sec_loc = dialog.findViewById(R.id.spinner_sec_loc);
        spinner_storageType = dialog.findViewById(R.id.spinner_storageType);
        spinner_storageSection = dialog.findViewById(R.id.spinner_storageSection);
        EditText et_boxNumber = dialog.findViewById(R.id.et_boxNumber);
        RadioButton rb_self = dialog.findViewById(R.id.rb_self);
        RadioButton rb_user = dialog.findViewById(R.id.rb_user);
        RadioButton rb_service_center = dialog.findViewById(R.id.rb_service_center);
        ll_issuephoto = dialog.findViewById(R.id.ll_issuephoto);
        iv_issuephoto = dialog.findViewById(R.id.iv_issuephoto);
        ll_itnFor.setVisibility(View.VISIBLE);
        ll_srn_received_transfer_to.setVisibility(View.VISIBLE);
        ll_primary_loc.setVisibility(View.VISIBLE);
        ll_secondary_loc.setVisibility(View.VISIBLE);
        ll_storageSection.setVisibility(View.VISIBLE);

        tv_lastlot.setText("Receive SRN");
        til_lable.setHint(R.string.receive_note);

        tv_issuephoto.setOnClickListener(view -> selectImage());

        if (datum.getSrnScReceivedAt()!=null){
            rb_service_center.setChecked(false);
            rb_service_center.setVisibility(View.GONE);
            if (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1){
                rb_self.setChecked(false);
                rb_self.setVisibility(View.GONE);
                rb_user.setChecked(true);
                srn_received_for="User";
                ll_primary_loc.setVisibility(View.GONE);
                ll_secondary_loc.setVisibility(View.GONE);
                ll_storageSection.setVisibility(View.GONE);
                ll_srn_received_transfer_to.setVisibility(View.GONE);
            }
        }else {
            rb_self.setChecked(false);
            rb_self.setVisibility(View.GONE);
            rb_user.setChecked(false);
            rb_user.setVisibility(View.GONE);
            rb_service_center.setChecked(true);
            srn_received_for="Service Center";
            ll_primary_loc.setVisibility(View.GONE);
            ll_secondary_loc.setVisibility(View.GONE);
            ll_storageSection.setVisibility(View.GONE);
            ll_srn_received_transfer_to.setVisibility(View.GONE);
            tv_issuephoto.setVisibility(View.GONE);
        }

        rg_receivingFor.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            RadioButton rb1 = dialog.findViewById(checkedId);
            if (rb1.getText().equals("Self")) {
                srn_received_for="Self";
                ll_primary_loc.setVisibility(View.GONE);
                ll_secondary_loc.setVisibility(View.GONE);
                ll_storageSection.setVisibility(View.GONE);
                ll_srn_received_transfer_to.setVisibility(View.VISIBLE);
                tv_issuephoto.setVisibility(View.VISIBLE);
            } else if (rb1.getText().equals("User")) {
                srn_received_for="User";
                ll_primary_loc.setVisibility(View.GONE);
                ll_secondary_loc.setVisibility(View.GONE);
                ll_storageSection.setVisibility(View.GONE);
                ll_srn_received_transfer_to.setVisibility(View.GONE);
                tv_issuephoto.setVisibility(View.VISIBLE);
            } else if (rb1.getText().equals("Service Center")) {
                srn_received_for="Service Center";
                ll_primary_loc.setVisibility(View.GONE);
                ll_secondary_loc.setVisibility(View.GONE);
                ll_storageSection.setVisibility(View.GONE);
                ll_srn_received_transfer_to.setVisibility(View.GONE);
                tv_issuephoto.setVisibility(View.GONE);
            }
        });

        getPrimaryLoc();

        spinner_primary_loc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                    ArrayAdapter<String> secondaryLocadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, secondaryLoc_list);
                    secondaryLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_sec_loc.setAdapter(secondaryLocadapter);

                    ArrayAdapter<String> storageTypeadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, storageTypes_list);
                    storageTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageType.setAdapter(storageTypeadapter);

                    ArrayAdapter<String> storageSectionadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, storageSections_list);
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

                    ArrayAdapter<String> storageTypeadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, storageTypes_list);
                    storageTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageType.setAdapter(storageTypeadapter);

                    ArrayAdapter<String> storageSectionadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, storageSections_list);
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

                    ArrayAdapter<String> storageSectionadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, storageSections_list);
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

        rg_action.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            RadioButton rb2 = dialog.findViewById(checkedId);
            if (rb2.getText().equals("Workshop")) {
                srn_received_transfer_to="Workshop";
                ll_primary_loc.setVisibility(View.GONE);
                ll_secondary_loc.setVisibility(View.GONE);
                ll_storageSection.setVisibility(View.GONE);
            } else if (rb2.getText().equals("Store")) {
                srn_received_transfer_to="Store";
                ll_primary_loc.setVisibility(View.VISIBLE);
                ll_secondary_loc.setVisibility(View.VISIBLE);
                ll_storageSection.setVisibility(View.VISIBLE);
            }else if (rb2.getText().equals("Discard")) {
                srn_received_transfer_to="Discard";
                ll_primary_loc.setVisibility(View.GONE);
                ll_secondary_loc.setVisibility(View.GONE);
                ll_storageSection.setVisibility(View.GONE);
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString().trim();
                String boxNumber = et_boxNumber.getText().toString();
                if (message.equalsIgnoreCase("")){
                    Toast.makeText(SRNListActivity.this, "Enter message", Toast.LENGTH_LONG).show();
                }else if (srn_received_for.equalsIgnoreCase("Self") && srn_received_transfer_to.equalsIgnoreCase("Store") && primaryLoc_id.equalsIgnoreCase("")){
                    Toast.makeText(SRNListActivity.this, "Select Primary Location", Toast.LENGTH_LONG).show();
                }else if (srn_received_for.equalsIgnoreCase("Self") && srn_received_transfer_to.equalsIgnoreCase("Store") && secondaryLoc_id.equalsIgnoreCase("")){
                    Toast.makeText(SRNListActivity.this, "Select Secondary Location", Toast.LENGTH_LONG).show();
                }else if (srn_received_for.equalsIgnoreCase("Self") && srn_received_transfer_to.equalsIgnoreCase("Store") && storageType_id.equalsIgnoreCase("")){
                    Toast.makeText(SRNListActivity.this, "Select Storage Type", Toast.LENGTH_LONG).show();
                }else {
                    dialog.cancel();
                    updateReceive(datum.getSrnId(),srn_received_for,srn_received_transfer_to,message,boxNumber);
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
                    ArrayAdapter<String> storageSectionadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, storageSections_list);
                    storageSectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageSection.setAdapter(storageSectionadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(SRNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(SRNListActivity.this, msg);
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
                Toast.makeText(SRNListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    ArrayAdapter<String> storageTypeadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, storageTypes_list);
                    storageTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_storageType.setAdapter(storageTypeadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(SRNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(SRNListActivity.this, msg);
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
                Toast.makeText(SRNListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    ArrayAdapter<String> secondaryLocadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, secondaryLoc_list);
                    secondaryLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_sec_loc.setAdapter(secondaryLocadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(SRNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(SRNListActivity.this, msg);
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
                Toast.makeText(SRNListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    ArrayAdapter<String> primaryLocadapter = new ArrayAdapter<>(SRNListActivity.this, android.R.layout.simple_spinner_item, primaryLoc_list);
                    primaryLocadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_primary_loc.setAdapter(primaryLocadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(SRNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(SRNListActivity.this, msg);
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
                Toast.makeText(SRNListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateReceive(Integer srnId, String srn_received_for, String srn_received_transfer_to, String message, String boxNumber) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e("Receive Params", srnId+"="+srn_received_for+"="+srn_received_transfer_to+"="+message+"="+list);
        RequestBody itnID1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(srnId));
        RequestBody srn_received_for1 = RequestBody.create(MediaType.parse("text/plain"), srn_received_for);
        RequestBody srn_received_transfer_to1 = RequestBody.create(MediaType.parse("text/plain"), srn_received_transfer_to);
        RequestBody message1 = RequestBody.create(MediaType.parse("text/plain"), message);
        RequestBody boxNumber1 = RequestBody.create(MediaType.parse("text/plain"), boxNumber);
        RequestBody primaryLoc_id1 = RequestBody.create(MediaType.parse("text/plain"), primaryLoc_id);
        RequestBody secondaryLoc_id1 = RequestBody.create(MediaType.parse("text/plain"), secondaryLoc_id);
        RequestBody storageType_id1 = RequestBody.create(MediaType.parse("text/plain"), storageType_id);
        RequestBody storageSections_id1 = RequestBody.create(MediaType.parse("text/plain"), storageSections_id);
        Call<SubmitSuccessResponse> call = apiInterface.setSRNReceived("application/json", "Bearer " + token, itnID1, srn_received_for1, srn_received_transfer_to1, message1, primaryLoc_id1, secondaryLoc_id1, storageType_id1, storageSections_id1, boxNumber1, list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(SRNListActivity.this, SRNListActivity.class);
                        Toast.makeText(SRNListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(SRNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(SRNListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitSuccessResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    public void viewPDF(Srn datum) {
        Log.e("ITN ID:", String.valueOf(datum.getSrnId()));
        String file_url = AppConfig.BASE_IMAGE_URL + "uploads/itn/"+datum.getSrnFile();
        Toast.makeText(SRNListActivity.this, file_url, Toast.LENGTH_SHORT).show();
        String filename = datum.getSrnFile();
        String[] filename1 = filename.split("\\.");

        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(file_url);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(filename1[0]);
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadmanager.enqueue(request);
        Log.e("Download PDF : ", String.valueOf(reference));
    }

    @SuppressLint("SetTextI18n")
    public void cancelITN(Srn datum) {
        final Dialog dialog = new Dialog(SRNListActivity.this);
        dialog.setContentView(R.layout.custom_itn_receive_note);
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
        TextView tv_issuephoto = dialog.findViewById(R.id.tv_issuephoto);
        TextInputLayout til_lable = dialog.findViewById(R.id.til_lable);
        ll_issuephoto = dialog.findViewById(R.id.ll_issuephoto);
        iv_issuephoto = dialog.findViewById(R.id.iv_issuephoto);
        tv_lastlot.setText("Cancel SRN");
        til_lable.setHint(R.string.cancel_note);

        tv_issuephoto.setVisibility(View.GONE);
        ll_issuephoto.setVisibility(View.GONE);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString();
                if (message.equalsIgnoreCase("")){
                    Toast.makeText(SRNListActivity.this, "Enter message", Toast.LENGTH_LONG).show();
                }else {
                    dialog.cancel();
                    cancelSRN(datum.getSrnId(),message);
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

    private void cancelSRN(Integer srnId, String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e("Receive Params", srnId+"="+message);
        Call<SubmitSuccessResponse> call = apiInterface.setSRNCancel("application/json", "Bearer " + token, srnId, message);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(SRNListActivity.this, ITNListActivity.class);
                        Toast.makeText(SRNListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(SRNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(SRNListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SubmitSuccessResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Click here to open camera", "Choose from gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SRNListActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(SRNListActivity.this);

                if (items[item].equals("Click here to open camera")) {
                    userChoosenTask = "Click here to open camera";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from gallery")) {
                    userChoosenTask = "Choose from gallery";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                openCamera();
            }
        }else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"new_image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camintent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(camintent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        ll_issuephoto.setVisibility(View.VISIBLE);
        iv_issuephoto.setImageURI(image_uri);
        path_billcopy = getPath(image_uri);
        Log.e(TAG, String.valueOf(image_uri));
        list.add(prepareFilePart("itn_received_file", Uri.parse(path_billcopy)));
    }

    public String getPath(Uri image_uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(image_uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        Bitmap selectedBitmap = null;
        if (data != null) {
            try {
                image_uri = data.getData();
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ll_issuephoto.setVisibility(View.VISIBLE);
        iv_issuephoto.setImageBitmap(bm);
        String absolutePath = saveImage(bm);
        list.add(prepareFilePart("itn_received_file", Uri.parse(absolutePath)));
        /*assert bm != null;
        path_billcopy = getPath(image_uri);
        Log.e(TAG, String.valueOf(image_uri));
        list.add(prepareFilePart("ticket_raise_files", image_uri));*/
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Samadhaan/");
        wallpaperDirectory.mkdirs();

        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {

            BILL_COPY = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            BILL_COPY.createNewFile();
            FileOutputStream fo = new FileOutputStream(BILL_COPY);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(SRNListActivity.this, new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + BILL_COPY.getAbsolutePath());
            return BILL_COPY.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());
        Log.i("here is error", file.getAbsolutePath());
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @Override
    public void onBackPressed() {
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            Intent intent = new Intent(SRNListActivity.this, TicketHandlingUserActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(SRNListActivity.this, TicketHandlingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}