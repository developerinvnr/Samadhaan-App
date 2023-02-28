package com.vnrseeds.samadhan.addassetforms;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.DropDownResponse;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianData;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianResponse;
import com.vnrseeds.samadhan.parser.departmentparser.Data;
import com.vnrseeds.samadhan.parser.departmentparser.DepartmentResponse;
import com.vnrseeds.samadhan.parser.locationparser.LocationData;
import com.vnrseeds.samadhan.parser.locationparser.LocationResponse;
import com.vnrseeds.samadhan.parser.sectionparser.SectionData;
import com.vnrseeds.samadhan.parser.sectionparser.SectionResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
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

public class DeployAssetFormActivity extends AppCompatActivity {

    private static final String TAG = "DeployAssetFormActivity";
    private static final String SELECT = "Select";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private AutoCompleteTextView dd_assetType;
    private String asset_id;
    private DropDownResponse dropDownResponse;
    private List<LocationData> location;
    private AutoCompleteTextView dd_location;
    private String selloc_id;
    private List<Data> departments;
    private final ArrayList<String> deptlist=new ArrayList<>();
    private AutoCompleteTextView dd_department;
    private String seldept_id;
    private List<SectionData> sections;
    private final ArrayList<String> sectionslist = new ArrayList<>();
    private AutoCompleteTextView dd_section;
    private AutoCompleteTextView dd_custodian;
    private String selsection_id;
    private List<CustodianData> custodians;
    private String[] custodianlist;
    boolean[] selectedcustodian;
    private final ArrayList<Integer> custodianArray = new ArrayList<>();
    private EditText et_installationDate;
    private TextView tv_spinnercustodian;
    private TextInputLayout il_custodian;
    private Button button_submit;
    private String custodian_id;
    private EditText et_precise_loc;
    private String ac_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_asset_form);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        //dropDownResponse = (DropDownResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ADD_ASDD_OBJ, DropDownResponse.class);
        customProgressDialogue = new CustomProgressDialogue(DeployAssetFormActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.deploy_asset);

        back_nav = findViewById(R.id.back_nav);
        TextView tv_assetname = findViewById(R.id.tv_assetname);
        TextView tv_assetCode = findViewById(R.id.tv_assetCode);
        TextView tv_seviceType = findViewById(R.id.tv_seviceType);

        dd_assetType = findViewById(R.id.dd_assetType);
        dd_location = findViewById(R.id.dd_location);
        dd_department = findViewById(R.id.dd_department);
        dd_section = findViewById(R.id.dd_section);
        dd_custodian = findViewById(R.id.dd_custodian);
        tv_spinnercustodian = findViewById(R.id.tv_spinnercustodian);
        et_installationDate = findViewById(R.id.et_installationDate);
        il_custodian = findViewById(R.id.il_custodian);
        et_precise_loc = findViewById(R.id.et_precise_loc);
        button_submit = findViewById(R.id.button_submit);

        String asset_code = getIntent().getStringExtra("asset_code");
        String serviceType = getIntent().getStringExtra("serviceType");
        String assetName = getIntent().getStringExtra("assetName");
        asset_id = getIntent().getStringExtra("asset_id");
        ac_id = getIntent().getStringExtra("ac_id");

        tv_assetCode.setText(asset_code);
        tv_seviceType.setText(serviceType);
        tv_assetname.setText(assetName);

        String[] assetTypeList = {"Individual", "Shared", "Group"};
        ArrayAdapter<String> assettypeAdapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, assetTypeList);
        assettypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_assetType.setAdapter(assettypeAdapter);

        /*location = dropDownResponse.getData().getLocationList();
        ArrayList<String> locationlist = new ArrayList<>();
        for (Location obj : location) {
            locationlist.add(obj.getLocationName());
        }
        ArrayAdapter<String> locationlistadapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, locationlist);
        locationlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dd_location.setAdapter(locationlistadapter);*/
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeployAssetFormActivity.this, DeployAssetListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getLocation();
        et_installationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(DeployAssetFormActivity.this,null,"0", new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        et_installationDate.setText(date);
                    }
                });
            }
        });
        dd_assetType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String assettype = dd_assetType.getText().toString();
                if (assettype.equalsIgnoreCase("Individual")){
                    il_custodian.setVisibility(View.VISIBLE);
                    tv_spinnercustodian.setVisibility(View.GONE);
                }else {
                    il_custodian.setVisibility(View.GONE);
                    tv_spinnercustodian.setVisibility(View.VISIBLE);
                }
            }
        });
        dd_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    selloc_id = String.valueOf(location.get(i).getLocationId());
                    deptlist.clear();
                    dd_department.setText("Select");
                    sectionslist.clear();
                    dd_section.setText("Select");
                    dd_custodian.setText("Select");
                    tv_spinnercustodian.setText("");
                    String[] custList = new String[0];
                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, custList);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_custodian.setAdapter(custodianapter);
                    getDeptList(selloc_id);
                    String assettype = dd_assetType.getText().toString();
                    //if (!assettype.equalsIgnoreCase("Individual") && !assettype.equalsIgnoreCase(SELECT)) {
                        custodianArray.clear();
                        tv_spinnercustodian.setText("");
                        getCustodianList_loc(selloc_id);
                    //}
                }
            }
        });
        dd_department.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sectionslist.clear();
                dd_section.setText(SELECT);
                dd_custodian.setText(SELECT);
                tv_spinnercustodian.setText("");
                String[] custList = new String[0];
                ArrayAdapter<String> custodianapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, custList);
                custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dd_custodian.setAdapter(custodianapter);
                if (i >= 0) {
                    seldept_id = String.valueOf(departments.get(i).getDepartmentId());
                    sectionslist.clear();
                    getSectionList(selloc_id, seldept_id);
                }
            }
        });
        dd_section.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dd_custodian.setText(SELECT);
                tv_spinnercustodian.setText("");
                String[] custList = new String[0];
                ArrayAdapter<String> custodianapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, custList);
                custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dd_custodian.setAdapter(custodianapter);
                if (i >= 0) {
                    selsection_id = String.valueOf(sections.get(i).getDsId());
                    String assettype = dd_assetType.getText().toString();
                    //if (assettype.equalsIgnoreCase("Individual")) {
                        getCustodianList(selsection_id);
                    //}
                }
            }
        });
        dd_custodian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    custodian_id = String.valueOf(custodians.get(i).getCustodianId());
                }
            }
        });
        tv_spinnercustodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeployAssetFormActivity.this);
                builder.setTitle("Select Custodian");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(custodianlist, selectedcustodian, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            custodianArray.add(i);
                            Collections.sort(custodianArray);
                        } else {
                            custodianArray.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < custodianArray.size(); j++) {
                            stringBuilder.append(custodianlist[custodianArray.get(j)]);
                            if (j != custodianArray.size() - 1) {
                                stringBuilder.append(",");
                            }
                        }

                        tv_spinnercustodian.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selectedcustodian.length; j++) {
                            selectedcustodian[j] = false;
                            custodianArray.clear();
                            tv_spinnercustodian.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String assettype = dd_assetType.getText().toString();
                String installationdate = et_installationDate.getText().toString().trim();
                String precise_loc = et_precise_loc.getText().toString().trim();
                String installationloc = dd_location.getText().toString();
                String department = dd_department.getText().toString();
                String section = dd_section.getText().toString();
                String cust_id = null;
                if (assettype.equalsIgnoreCase("Individual")){
                    cust_id=custodian_id;
                }else {
                    for (int i = 0; i < custodianArray.size(); i++) {
                        int ind = custodianArray.get(i);
                        if (cust_id == null) {
                            cust_id = String.valueOf(custodians.get(ind).getCustodianId());
                        } else {
                            cust_id = cust_id + "," + custodians.get(ind).getCustodianId();
                        }
                    }
                }
                if (assettype.equalsIgnoreCase("") || assettype.equalsIgnoreCase("select")) {
                    Utils.getInstance().showAlert(DeployAssetFormActivity.this, "Select asset type");
                } else if (installationdate.equalsIgnoreCase("dd-mm-yyyy")){
                    Utils.getInstance().showAlert(DeployAssetFormActivity.this, "Select Installation Date");
                }else if (installationloc.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(DeployAssetFormActivity.this, "Select Installation location");
                } else if (department.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(DeployAssetFormActivity.this, "Select Installation department");
                } else if (section.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(DeployAssetFormActivity.this, "Select section");
                } else if (cust_id==null) {
                    Utils.getInstance().showAlert(DeployAssetFormActivity.this, "Select custodian");
                }else {
                    submitForm(asset_id,ac_id,assettype,installationdate,selloc_id,seldept_id,selsection_id,cust_id,precise_loc);
                }

            }
        });
    }

    private void submitForm(String asset_id, String ac_id, String assettype, String installationdate, String selloc_id, String seldept_id, String selsection_id, String cust_id, String precise_loc) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e(TAG,asset_id+"="+ac_id+"="+assettype+"="+installationdate+"="+seldept_id+"="+selsection_id+"="+cust_id);
        Call<SubmitSuccessResponse> call = apiInterface.getDeploySubmitInfo("application/json", "Bearer " + token, asset_id, assettype, installationdate, selloc_id, seldept_id, selsection_id, cust_id, precise_loc);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(DeployAssetFormActivity.this, MainActivity.class);
                        Toast.makeText(DeployAssetFormActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetFormActivity.this, msg);
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

    private void getLocation() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<LocationResponse> call = apiInterface.getLocationListInfo("application/json", "Bearer "+token,"");
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(@NotNull Call<LocationResponse> call, @NotNull Response<LocationResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    LocationResponse locationResponse = response.body();
                    //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_ADD_ASDD_OBJ, dropDownResponse);
                    location = locationResponse.getData();
                    ArrayList<String> locationlist = new ArrayList<>();
                    for (LocationData obj : location) {
                        locationlist.add(obj.getLocationName());
                    }
                    ArrayAdapter<String> locationlistadapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, locationlist);
                    locationlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_location.setAdapter(locationlistadapter);
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetFormActivity.this, msg);
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

    private void getDeptList(String selloc_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e(TAG,selloc_id);
        Call<DepartmentResponse> call = apiInterface.getDeptListInfo("application/json", "Bearer " + token, selloc_id);
        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(@NotNull Call<DepartmentResponse> call, @NotNull Response<DepartmentResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    DepartmentResponse departmentResponse = response.body();
                    assert departmentResponse != null;
                    departments = departmentResponse.getData();
                    deptlist.clear();
                    for (com.vnrseeds.samadhan.parser.departmentparser.Data obj : departments) {
                        deptlist.add(obj.getDepartmentName());
                    }
                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_department.setAdapter(deptadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetFormActivity.this, msg);
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
                    SectionResponse sectionResponse = response.body();
                    assert sectionResponse != null;
                    sections = sectionResponse.getData();
                    sectionslist.clear();
                    for (SectionData obj : sections) {
                        sectionslist.add(obj.getDsName());
                    }

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetFormActivity.this, msg);
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
        Call<CustodianResponse> call = apiInterface.getCustodianInfo("application/json", "Bearer " + token, selsection_id, "");
        call.enqueue(new Callback<CustodianResponse>() {
            @Override
            public void onResponse(@NotNull Call<CustodianResponse> call, @NotNull Response<CustodianResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    CustodianResponse custodianResponse = response.body();
                    assert custodianResponse != null;
                    custodians = custodianResponse.getData();
                    ArrayList<String> custodianlist1 = new ArrayList<>();
                    custodianlist = new String[0];
                    for (CustodianData obj : custodians) {
                        custodianlist1.add(obj.getCustodianName());
                    }
                    custodianlist = custodianlist1.toArray(new String[0]);
                    selectedcustodian = new boolean[custodianlist.length];

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetFormActivity.this, msg);
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

    private void getCustodianList_loc(String selloc_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<CustodianResponse> call = apiInterface.getCustodianLocInfo("application/json", "Bearer " + token, selloc_id);
        call.enqueue(new Callback<CustodianResponse>() {
            @Override
            public void onResponse(@NotNull Call<CustodianResponse> call, @NotNull Response<CustodianResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    CustodianResponse custodianResponse = response.body();
                    assert custodianResponse != null;
                    custodians = custodianResponse.getData();
                    ArrayList<String> custodianlist1 = new ArrayList<>();
                    custodianlist = new String[0];
                    for (CustodianData obj : custodians) {
                        custodianlist1.add(obj.getCustodianName());
                    }
                    custodianlist = custodianlist1.toArray(new String[0]);
                    selectedcustodian = new boolean[custodianlist.length];

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(DeployAssetFormActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(DeployAssetFormActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(DeployAssetFormActivity.this, msg);
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
        Intent intent = new Intent(DeployAssetFormActivity.this, DeployAssetListActivity.class);
        startActivity(intent);
        finish();
    }
}