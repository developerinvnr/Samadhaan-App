package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.srnparser.SRNCreateResponse;
import com.vnrseeds.samadhan.parser.srnparser.ServiceCenter;
import com.vnrseeds.samadhan.parser.srnparser.Vendor;
import com.vnrseeds.samadhan.parser.srnparser.Workshop;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRepaireNoteActivity extends AppCompatActivity {

    private static final String TAG = "ServiceRepaireNoteActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private TextView tv_more;
    private RadioGroup rg_itnFor;
    private AutoCompleteTextView spinner_dispatchFrom;
    private RadioGroup rg_vendorService;
    private AutoCompleteTextView spinner_vendor;
    private AutoCompleteTextView spinner_serviceCenter;

    private AutoCompleteTextView spinner_modeOfTransfer;
    private LinearLayout ll_byHand;
    private TextInputLayout til_personName;
    private EditText et_person_name;
    private TextInputLayout til_mobileNo;
    private TextInputLayout til_companyName;
    private EditText et_company_name;
    private EditText et_mobileno;
    private TextInputLayout til_docketNo;
    private EditText et_docketno;
    private TextInputLayout til_transportName;
    private EditText et_transport_name;
    private TextInputLayout til_vehicleNo;
    private EditText et_vehicleno;
    private TextInputLayout til_driverName;
    private EditText et_driver_name;
    private TextInputLayout til_mobileNo1;
    private EditText et_mobileno1;
    private EditText et_qty;
    private EditText et_remark;
    private Button button_submit;
    private LinearLayout ll_courier;
    private LinearLayout ll_companyVehicle;
    private LinearLayout ll_transport;
    private AutoCompleteTextView dd_packingType;
    private TicketDetailsPojo ticketDetailsPojo;
    private String srnFor="Self";
    private LinearLayout ll_dispatchFrom;
    private LinearLayout ll_userDispatchFrom;
    private List<String> tranferModes;
    private List<com.vnrseeds.samadhan.parser.srnparser.Workshop> workshop;
    private List<String> packingtype;
    private List<Vendor> vendors;
    private List<ServiceCenter> serviceCenter;
    private TextView tv_sendToDetails;
    private String sendTo="Vendor";
    private TextInputLayout il_vendor;
    private TextInputLayout il_serviceCenter;
    private String modeOfTransfer="";

    public static final String[] MOBILENO = {"0000000000", "1111111111", "2222222222", "3333333333", "4444444444", "5555555555", "6666666666", "7777777777", "8888888888", "9999999999"};
    public static final String[] MOBILENO_FIRSTDIGIT = {"0", "1", "2", "3", "4", "5"};
    private String workshop_id="";
    private String vendor_id="";
    private String serviceCenter_id="";
    private TextView tv_address;
    private RadioGroup rg_address;
    private String srn_vendor_address="1";
    private String address_one="";
    private String address_two="";
    private RadioButton rb_address2;
    private LinearLayout ll_itnFor;
    private RoleResponse roleResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_repaire_note);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(ServiceRepaireNoteActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Service Repaire Note");

        back_nav = findViewById(R.id.back_nav);
        TextView tv_ticketdate = findViewById(R.id.tv_ticketdate);
        TextView tv_ticketno = findViewById(R.id.tv_ticketno);
        TextView tv_ticket_title = findViewById(R.id.tv_ticket_title);
        TextView tv_priority = findViewById(R.id.tv_priority);
        tv_more = findViewById(R.id.tv_more);

        rg_itnFor = findViewById(R.id.rg_itnFor);
        spinner_dispatchFrom = findViewById(R.id.spinner_workshop);
        rg_vendorService = findViewById(R.id.rg_vendorService);
        spinner_vendor = findViewById(R.id.spinner_vendor);
        spinner_serviceCenter = findViewById(R.id.spinner_serviceCenter);

        spinner_modeOfTransfer = findViewById(R.id.spinner_modeOfTransfer);
        ll_userDispatchFrom = findViewById(R.id.ll_userDispatchFrom);
        ll_dispatchFrom = findViewById(R.id.ll_dispatchFrom);
        ll_byHand = findViewById(R.id.ll_byHand);
        ll_courier = findViewById(R.id.ll_courier);
        ll_companyVehicle = findViewById(R.id.ll_companyVehicle);
        ll_transport = findViewById(R.id.ll_transport);
        til_personName = findViewById(R.id.til_personName);
        et_person_name = findViewById(R.id.et_person_name);
        til_mobileNo = findViewById(R.id.til_mobileNo);
        et_mobileno = findViewById(R.id.et_mobileno);
        til_companyName = findViewById(R.id.til_companyName);
        et_company_name = findViewById(R.id.et_company_name);
        til_docketNo = findViewById(R.id.til_docketNo);
        et_docketno = findViewById(R.id.et_docketno);
        til_transportName = findViewById(R.id.til_transportName);
        et_transport_name = findViewById(R.id.et_transport_name);
        til_vehicleNo = findViewById(R.id.til_vehicleNo);
        et_vehicleno = findViewById(R.id.et_vehicleno);
        til_driverName = findViewById(R.id.til_driverName);
        et_driver_name = findViewById(R.id.et_driver_name);
        til_mobileNo1 = findViewById(R.id.til_mobileNo1);
        et_mobileno1 = findViewById(R.id.et_mobileno1);
        dd_packingType = findViewById(R.id.dd_packingType);
        et_qty = findViewById(R.id.et_qty);
        et_remark = findViewById(R.id.et_remark);
        button_submit = findViewById(R.id.button_submit);
        tv_sendToDetails = findViewById(R.id.tv_sendToDetails);
        il_vendor = findViewById(R.id.il_vendor);
        il_serviceCenter = findViewById(R.id.il_serviceCenter);
        tv_address = findViewById(R.id.tv_address);
        rg_address = findViewById(R.id.rg_address);
        rb_address2 = findViewById(R.id.rb_address2);
        ll_itnFor = findViewById(R.id.ll_itnFor);

        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
        tv_ticketno.setText(ticketDetailsPojo.getTicketNo());
        if (ticketDetailsPojo.getIssueTitle()!=null){
            if (ticketDetailsPojo.getIssueTitle().length()>30) {
                tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle().substring(0, 30) + "...");
            }else {
                tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
            }
            //tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
        }else {
            if (ticketDetailsPojo.getTicketIssueOther().length()>30) {
                tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther().substring(0, 30) + "...");
            }else {
                tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
            }
        }
        tv_priority.setText(ticketDetailsPojo.getPriority());
        tv_sendToDetails.setText(ticketDetailsPojo.getLocationName());

        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            ll_userDispatchFrom.setVisibility(View.VISIBLE);
            ll_itnFor.setVisibility(View.GONE);
            ll_dispatchFrom.setVisibility(View.GONE);
            srnFor="User";
        }else{
            ll_userDispatchFrom.setVisibility(View.GONE);
            srnFor="Self";
            tv_sendToDetails.setVisibility(View.VISIBLE);
            ll_itnFor.setVisibility(View.VISIBLE);
            ll_dispatchFrom.setVisibility(View.VISIBLE);
        }

        if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
            tv_priority.setTextColor(Color.parseColor("#FE5247"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
            tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
            tv_priority.setTextColor(Color.parseColor("#FF8744"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
            tv_priority.setTextColor(Color.parseColor("#8FFF36"));
        }
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(view -> {
            if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
                Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingUserActivity.class);
                startActivity(intent);
                finish();
            }else {
                if ((roleResponse.getData().contains("HARDWARE_ENGINEER") || roleResponse.getData().contains("NETWORK_ENGINEER")) && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                    Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingActivity.class);
                    startActivity(intent);
                    finish();
                }else if (roleResponse.getData().contains("SOFTWARE_ENGINEER") && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")) {
                    Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingActivity.class);
                    startActivity(intent);
                    finish();
                }else if (roleResponse.getData().contains("HARDWARE_ENGINEER") || roleResponse.getData().contains("NETWORK_ENGINEER") || roleResponse.getData().contains("SOFTWARE_ENGINEER")) {
                    Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingUserActivity.class);
                    startActivity(intent);
                    finish();
                }
                /*Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

        rg_itnFor.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            if (rb.getText().equals("Self")) {
                srnFor="Self";
                ll_dispatchFrom.setVisibility(View.VISIBLE);
                ll_userDispatchFrom.setVisibility(View.GONE);
            } else if (rb.getText().equals("User")) {
                srnFor="User";
                ll_dispatchFrom.setVisibility(View.GONE);
                ll_userDispatchFrom.setVisibility(View.VISIBLE);
            }
        });

        rg_vendorService.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb.getText().equals("Vendor")) {
                    sendTo="Vendor";
                    il_vendor.setVisibility(View.VISIBLE);
                    il_serviceCenter.setVisibility(View.GONE);
                    tv_address.setText("");
                    address_one="";
                    address_two="";
                    tv_address.setText("");
                    //spinner_vendor.setText("Select");
                } else if (rb.getText().equals("Service Center")) {
                    sendTo="Service Center";
                    il_vendor.setVisibility(View.GONE);
                    il_serviceCenter.setVisibility(View.VISIBLE);
                    tv_address.setText("");
                    //spinner_serviceCenter.setText("Select");
                }
            }
        });

        rg_address.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            if (rb.getText().equals("Address-1")) {
                srn_vendor_address="1";
                tv_address.setText(address_one);
            } else if (rb.getText().equals("Address-2")) {
                srn_vendor_address="2";
                tv_address.setText(address_two);
            }
        });

        spinner_modeOfTransfer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                modeOfTransfer=spinner_modeOfTransfer.getText().toString();
                if (modeOfTransfer.equalsIgnoreCase("By Hand")){
                    ll_byHand.setVisibility(View.VISIBLE);
                    ll_courier.setVisibility(View.GONE);
                    ll_companyVehicle.setVisibility(View.GONE);
                    ll_transport.setVisibility(View.GONE);
                }else if (modeOfTransfer.equalsIgnoreCase("Courier")){
                    ll_byHand.setVisibility(View.GONE);
                    ll_courier.setVisibility(View.VISIBLE);
                    ll_companyVehicle.setVisibility(View.GONE);
                    ll_transport.setVisibility(View.GONE);
                }else if (modeOfTransfer.equalsIgnoreCase("Company Vehicle")){
                    ll_byHand.setVisibility(View.GONE);
                    ll_courier.setVisibility(View.GONE);
                    ll_companyVehicle.setVisibility(View.VISIBLE);
                    ll_transport.setVisibility(View.VISIBLE);
                    til_transportName.setVisibility(View.GONE);
                }else if (modeOfTransfer.equalsIgnoreCase("Transport")){
                    ll_byHand.setVisibility(View.GONE);
                    ll_courier.setVisibility(View.GONE);
                    ll_companyVehicle.setVisibility(View.VISIBLE);
                    ll_transport.setVisibility(View.VISIBLE);
                    til_transportName.setVisibility(View.VISIBLE);
                }
            }
        });

        spinner_dispatchFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    workshop_id = String.valueOf(workshop.get(i).getId());
                }
            }
        });

        spinner_vendor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    vendor_id = String.valueOf(vendors.get(i).getVendorId());
                    address_one=vendors.get(i).getAddress1()+", "+vendors.get(i).getPincode1();
                    address_two=vendors.get(i).getAddress2()+", "+vendors.get(i).getPincode2();
                    tv_address.setText(address_one);
                    if (vendors.get(i).getVendorSecondAddress().equalsIgnoreCase("N")){
                        rb_address2.setEnabled(false);
                        rb_address2.setVisibility(View.GONE);
                    }else {
                        rb_address2.setEnabled(true);
                        rb_address2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        spinner_serviceCenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    serviceCenter_id = String.valueOf(serviceCenter.get(i).getVendorId());
                    address_one=vendors.get(i).getAddress1()+", "+serviceCenter.get(i).getPincode1();
                    address_two=vendors.get(i).getAddress2()+", "+serviceCenter.get(i).getPincode2();
                    tv_address.setText(address_one);
                    if (serviceCenter.get(i).getVendorSecondAddress().equalsIgnoreCase("N")){
                        rb_address2.setEnabled(false);
                        rb_address2.setVisibility(View.GONE);
                    }else {
                        rb_address2.setEnabled(true);
                        rb_address2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String person_name = et_person_name.getText().toString().trim();
                String company_name = et_company_name.getText().toString().trim();
                String docketno = et_docketno.getText().toString().trim();
                String transport_name = et_transport_name.getText().toString().trim();
                String vehicleno = et_vehicleno.getText().toString().trim();
                String driver_name = et_driver_name.getText().toString().trim();
                String perticulars = dd_packingType.getText().toString().trim();
                String qty = et_qty.getText().toString().trim();
                String remark = et_remark.getText().toString().trim();
                String mobileno="";
                if (modeOfTransfer.equalsIgnoreCase("By Hand")){
                    mobileno = et_mobileno.getText().toString().trim();
                }else {
                    mobileno = et_mobileno1.getText().toString().trim();
                }
                String[] first_dig = mobileno.split("");
                if (srnFor.equalsIgnoreCase("Self") && workshop_id.equalsIgnoreCase("")){
                    Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, "Select workshop");
                }else if (modeOfTransfer.equalsIgnoreCase("Select") || modeOfTransfer.equalsIgnoreCase("")){
                    Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, "Select mode of transit");
                }else if (!mobileno.isEmpty() && Arrays.asList(MOBILENO).contains(mobileno)) {
                    Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, "Invalid mobile number");
                }else if (!mobileno.isEmpty() && Arrays.asList(MOBILENO_FIRSTDIGIT).contains(first_dig[0])) {
                    Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, "Invalid mobile number");
                }else if (!mobileno.isEmpty() && Arrays.asList(first_dig).contains(" ")) {
                    Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, "Space not allowed in mobile number");
                }else if (perticulars.isEmpty() || perticulars.equalsIgnoreCase("Select")){
                    Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, "Select packing type");
                }else if (qty.isEmpty()){
                    Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, "Enter quantity");
                }else {
                    submitSRN(ticketDetailsPojo.getTicketId(),srnFor,sendTo,workshop_id,vendor_id,serviceCenter_id,modeOfTransfer,person_name,mobileno,company_name,docketno,transport_name,vehicleno,driver_name,perticulars,qty,remark);
                }

            }
        });
        getSRNCreteInfo();
    }

    @SuppressLint("LongLogTag")
    private void submitSRN(String ticketId, String srnFor, String sendTo, String workshop_id, String vendor_id, String serviceCenter_id, String modeOfTransfer, String person_name, String mobileno, String company_name, String docketno, String transport_name, String vehicleno, String driver_name, String perticulars, String qty, String remark) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e(TAG,token+"="+ticketId+"="+srnFor+"="+sendTo+"="+workshop_id+"="+vendor_id+"="+serviceCenter_id+"="+modeOfTransfer+"="+person_name+"="+mobileno+"="+company_name+"="+docketno+"="+transport_name+"="+vehicleno+"="+driver_name+"="+perticulars+"="+qty+"="+remark);
        Call<SubmitSuccessResponse> call = apiInterface.getSRNSubmitInfo("application/json", "Bearer " + token, "", ticketId, srnFor, sendTo, workshop_id, vendor_id, serviceCenter_id, modeOfTransfer, person_name, mobileno, company_name, docketno, transport_name, vehicleno, driver_name, perticulars, qty, remark, srn_vendor_address);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(ServiceRepaireNoteActivity.this, SRNListActivity.class);
                        Toast.makeText(ServiceRepaireNoteActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(ServiceRepaireNoteActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, msg);
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

    @SuppressLint("LongLogTag")
    private void getSRNCreteInfo() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SRNCreateResponse> call = apiInterface.getSRNCreateData("application/json", "Bearer " + token, ticketDetailsPojo.getTicketId());
        Log.e(TAG, String.valueOf(call));
        call.enqueue(new Callback<SRNCreateResponse>() {
            @Override
            public void onResponse(@NonNull Call<SRNCreateResponse> call, @NonNull Response<SRNCreateResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SRNCreateResponse srnCreateResponse = response.body();
                    assert srnCreateResponse != null;
                    tranferModes = srnCreateResponse.getData().getModes();

                    ArrayAdapter<String> priorityAadapter = new ArrayAdapter<>(ServiceRepaireNoteActivity.this, android.R.layout.simple_spinner_item, tranferModes);
                    priorityAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_modeOfTransfer.setAdapter(priorityAadapter);

                    workshop = srnCreateResponse.getData().getWorkshops();
                    ArrayList<String> workshoplist = new ArrayList<>();
                    for (Workshop obj : workshop) {
                        workshoplist.add(obj.getWorkshopName());
                    }
                    ArrayAdapter<String> workshopadapter = new ArrayAdapter<>(ServiceRepaireNoteActivity.this, android.R.layout.simple_spinner_item, workshoplist);
                    workshopadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_dispatchFrom.setAdapter(workshopadapter);

                    vendors = srnCreateResponse.getData().getVendors();
                    ArrayList<String> vendorlist = new ArrayList<>();
                    for (Vendor obj : vendors) {
                        vendorlist.add(obj.getVendorName());
                    }
                    ArrayAdapter<String> vendoradapter = new ArrayAdapter<>(ServiceRepaireNoteActivity.this, android.R.layout.simple_spinner_item, vendorlist);
                    vendoradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_vendor.setAdapter(vendoradapter);

                    serviceCenter = srnCreateResponse.getData().getServiceCenters();
                    ArrayList<String> serviceCenterlist = new ArrayList<>();
                    for (ServiceCenter obj : serviceCenter) {
                        serviceCenterlist.add(obj.getVendorName());
                    }
                    ArrayAdapter<String> serviceCenteradapter = new ArrayAdapter<>(ServiceRepaireNoteActivity.this, android.R.layout.simple_spinner_item, serviceCenterlist);
                    serviceCenteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_serviceCenter.setAdapter(serviceCenteradapter);

                    packingtype = srnCreateResponse.getData().getPackingTypes();

                    ArrayAdapter<String> packingtypeadapter = new ArrayAdapter<>(ServiceRepaireNoteActivity.this, android.R.layout.simple_spinner_item, packingtype);
                    packingtypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_packingType.setAdapter(packingtypeadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(ServiceRepaireNoteActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(ServiceRepaireNoteActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SRNCreateResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(ServiceRepaireNoteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingUserActivity.class);
            startActivity(intent);
            finish();
        }else {
            if ((roleResponse.getData().contains("HARDWARE_ENGINEER") || roleResponse.getData().contains("NETWORK_ENGINEER")) && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }else if (roleResponse.getData().contains("SOFTWARE_ENGINEER") && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")) {
                Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }else if (roleResponse.getData().contains("HARDWARE_ENGINEER") || roleResponse.getData().contains("NETWORK_ENGINEER") || roleResponse.getData().contains("SOFTWARE_ENGINEER")) {
                Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingUserActivity.class);
                startActivity(intent);
                finish();
            }
            /*Intent intent = new Intent(ServiceRepaireNoteActivity.this, TicketHandlingActivity.class);
            startActivity(intent);
            finish();*/
        }
    }
}