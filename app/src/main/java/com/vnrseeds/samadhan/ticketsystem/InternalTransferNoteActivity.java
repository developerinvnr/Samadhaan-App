package com.vnrseeds.samadhan.ticketsystem;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.itncreateparser.ITNCreateResponse;
import com.vnrseeds.samadhan.parser.itncreateparser.Location;
import com.vnrseeds.samadhan.parser.itncreateparser.ServiceProvider;
import com.vnrseeds.samadhan.parser.itncreateparser.Workshop;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
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

public class InternalTransferNoteActivity extends AppCompatActivity {

    private static final String TAG = "InternalTransferNoteActivity";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private TextView tv_more;
    private RadioGroup rg_itnFor;
    private TextInputLayout il_serviceProvider;
    private AutoCompleteTextView spinner_serviceProvider;
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
    private String itnFor="Self";
    private LinearLayout ll_itnFor;
    private TextView tv_sendToDetails;
    private TextView tv_sendTo;
    private TicketDetailsPojo ticketDetailsPojo;
    private String sendTo="Workshop";
    private LinearLayout ll_courier;
    private LinearLayout ll_companyVehicle;
    private LinearLayout ll_transport;
    private List<ServiceProvider> serviceProviders = new ArrayList<>();
    private ArrayList<String> serviceProviderList=new ArrayList<>();
    private List<String> tranferModes;
    private String modeOfTransfer="";
    private String sp_id="";
    private String vendor_id="";
    private AutoCompleteTextView dd_location;
    private AutoCompleteTextView dd_fromWorkshop;
    private String selloc_id="";
    private TextInputLayout il_location;

    public static final String[] MOBILENO = {"0000000000", "1111111111", "2222222222", "3333333333", "4444444444", "5555555555", "6666666666", "7777777777", "8888888888", "9999999999"};
    public static final String[] MOBILENO_FIRSTDIGIT = {"0", "1", "2", "3", "4", "5"};
    private TextInputLayout il_fromWorkshop;
    private List<Location> location;
    private List<Workshop> workshop;
    private AutoCompleteTextView dd_packingType;
    private List<String> packingtype;
    private String workshop_id="";
    private RadioButton rb_self;
    private RadioButton rb_user;
    private RoleResponse roleResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_transfer_note);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(InternalTransferNoteActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Generate ITN");

        back_nav = findViewById(R.id.back_nav);
        TextView tv_ticketdate = findViewById(R.id.tv_ticketdate);
        TextView tv_ticketno = findViewById(R.id.tv_ticketno);
        TextView tv_ticket_title = findViewById(R.id.tv_ticket_title);
        TextView tv_priority = findViewById(R.id.tv_priority);
        tv_more = findViewById(R.id.tv_more);
        tv_sendToDetails = findViewById(R.id.tv_sendToDetails);
        tv_sendTo = findViewById(R.id.tv_sendTo);
        ll_itnFor = findViewById(R.id.ll_itnFor);
        rg_itnFor = findViewById(R.id.rg_itnFor);
        il_serviceProvider = findViewById(R.id.il_serviceProvider);
        spinner_serviceProvider = findViewById(R.id.spinner_serviceProvider);
        il_fromWorkshop = findViewById(R.id.il_fromWorkshop);
        dd_fromWorkshop = findViewById(R.id.dd_fromWorkshop);
        dd_location = findViewById(R.id.dd_location);
        dd_fromWorkshop = findViewById(R.id.dd_fromWorkshop);
        il_location = findViewById(R.id.il_location);
        spinner_modeOfTransfer = findViewById(R.id.spinner_modeOfTransfer);
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
        rb_self = findViewById(R.id.rb_self);
        rb_user = findViewById(R.id.rb_user);

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

        if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
            tv_priority.setTextColor(Color.parseColor("#FE5247"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
            tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
            tv_priority.setTextColor(Color.parseColor("#FF8744"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
            tv_priority.setTextColor(Color.parseColor("#8FFF36"));
        }

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
            rb_self.setChecked(true);
            rb_user.setEnabled(false);
        }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnisis")){

        }

        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            ll_itnFor.setVisibility(View.GONE);
            itnFor="User";
            sendTo="Workshop";
            il_fromWorkshop.setHint("Dispatch to");
            //tv_sendToDetails.setVisibility(View.GONE);
            //tv_sendTo.setVisibility(View.GONE);
            tv_sendTo.setText("Dispatch from");
            il_serviceProvider.setVisibility(View.VISIBLE);
            il_location.setVisibility(View.GONE);
        }else{
            itnFor="Self";
            sendTo="User";
            il_fromWorkshop.setHint("Dispatch from");
            il_serviceProvider.setVisibility(View.GONE);
            tv_sendToDetails.setVisibility(View.VISIBLE);
            tv_sendTo.setVisibility(View.VISIBLE);
            tv_sendTo.setText("Dispatch to");
            sp_id=ticketDetailsPojo.getTicket_user_id();
            il_location.setVisibility(View.VISIBLE);
            /*if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")){
                ll_itnFor.setVisibility(View.GONE);
                itnFor="User";
                sendTo="User";
                il_fromWorkshop.setHint("Dispatch to");
                //tv_sendToDetails.setVisibility(View.GONE);
                //tv_sendTo.setVisibility(View.GONE);
                tv_sendTo.setText("Dispatch from");
                il_serviceProvider.setVisibility(View.VISIBLE);
                il_location.setVisibility(View.GONE);
                rb_self.setEnabled(false);
                rb_user.setEnabled(true);
                rb_user.setChecked(true);
            }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")) {
                ll_itnFor.setVisibility(View.VISIBLE);
                itnFor = "Self";
                sendTo = "Workshop";
                il_fromWorkshop.setHint("Dispatch from");
                il_serviceProvider.setVisibility(View.GONE);
                il_location.setVisibility(View.VISIBLE);
                tv_sendToDetails.setVisibility(View.VISIBLE);
                tv_sendTo.setVisibility(View.VISIBLE);
                tv_sendTo.setText("Dispatch to");
                sp_id = ticketDetailsPojo.getTicket_user_id();
                rb_self.setEnabled(true);
                rb_self.setChecked(true);
                rb_user.setEnabled(false);
            }*/
        }
    }

    @SuppressLint("SetTextI18n")
    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(view -> {
            if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
                Intent intent = new Intent(InternalTransferNoteActivity.this, TicketHandlingUserActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(InternalTransferNoteActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }

        });

        rg_itnFor.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            if (rb.getText().equals("Self")) {
                itnFor="Self";
                sendTo="User";
                il_fromWorkshop.setHint("Dispatch from");
                il_serviceProvider.setVisibility(View.GONE);
                tv_sendToDetails.setVisibility(View.VISIBLE);
                tv_sendTo.setVisibility(View.VISIBLE);
                tv_sendTo.setText("Dispatch to");
                sp_id=ticketDetailsPojo.getTicket_user_id();
                il_location.setVisibility(View.VISIBLE);
            } else if (rb.getText().equals("User")) {
                il_fromWorkshop.setHint("Dispatch to");
                itnFor="User";
                sendTo="Workshop";
                sp_id="";
                il_serviceProvider.setVisibility(View.VISIBLE);
                //tv_sendToDetails.setVisibility(View.GONE);
                //tv_sendTo.setVisibility(View.GONE);
                tv_sendTo.setText("Dispatch from");
                il_location.setVisibility(View.GONE);
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

        spinner_serviceProvider.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sp_id = String.valueOf(serviceProviders.get(i).getUserId());
            }
        });

        dd_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    selloc_id = String.valueOf(location.get(i).getLocationId());
                }
            }
        });

        dd_fromWorkshop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    workshop_id = String.valueOf(workshop.get(i).getId());
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
                if (workshop_id.equalsIgnoreCase("")){
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Select workshop");
                }else if (itnFor.equalsIgnoreCase("User") && sp_id.equalsIgnoreCase("")){
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Select service provider");
                }else if (modeOfTransfer.equalsIgnoreCase("Select") || modeOfTransfer.equalsIgnoreCase("")){
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Select mode of transit");
                }else if (!mobileno.isEmpty() && Arrays.asList(MOBILENO).contains(mobileno)) {
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Invalid mobile number");
                }else if (!mobileno.isEmpty() && Arrays.asList(MOBILENO_FIRSTDIGIT).contains(first_dig[0])) {
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Invalid mobile number");
                }else if (!mobileno.isEmpty() && mobileno.length()!=10) {
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Invalid mobile number");
                }else if (!mobileno.isEmpty() && Arrays.asList(first_dig).contains(" ")) {
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Space not allowed in mobile number");
                }else if (perticulars.isEmpty() || perticulars.equalsIgnoreCase("Select")){
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Select packing type");
                }else if (qty.isEmpty()){
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Enter quantity");
                }else if (!qty.isEmpty() && qty.equalsIgnoreCase("0")){
                    Utils.getInstance().showAlert(InternalTransferNoteActivity.this, "Invalid quantity");
                }else {
                    submitITN(ticketDetailsPojo.getTicketId(),itnFor,sendTo,sp_id,vendor_id,person_name,mobileno,company_name,docketno,transport_name,vehicleno,driver_name,perticulars,qty,remark);
                }

            }
        });

        getDropDownData();
    }

    @SuppressLint("LongLogTag")
    private void submitITN(String ticketId, String itnFor, String sendTo, String sp_id, String vendor_id, String person_name, String mobileno, String company_name, String docketno, String transport_name, String vehicleno, String driver_name, String perticulars, String qty, String remark) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e(TAG,token+"="+ticketId+"="+itnFor+"="+sendTo+"="+sp_id+"="+vendor_id+"="+modeOfTransfer+"="+person_name+"="+mobileno+"="+company_name+"="+docketno+"="+transport_name+"="+vehicleno+"="+driver_name+"="+perticulars+"="+qty+"="+remark);
        Call<SubmitSuccessResponse> call = apiInterface.getITNSubmitInfo("application/json", "Bearer " + token, "", ticketId, itnFor, sendTo, sp_id, vendor_id, modeOfTransfer, person_name, mobileno, company_name, docketno, transport_name, vehicleno, driver_name, perticulars, qty, remark, selloc_id);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(InternalTransferNoteActivity.this, ITNListActivity.class);
                        Toast.makeText(InternalTransferNoteActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(InternalTransferNoteActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(InternalTransferNoteActivity.this, msg);
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
    private void getDropDownData() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<ITNCreateResponse> call = apiInterface.getITNCreateData("application/json", "Bearer " + token, ticketDetailsPojo.getTicketId());
        Log.e(TAG, String.valueOf(call));
        call.enqueue(new Callback<ITNCreateResponse>() {
            @Override
            public void onResponse(@NonNull Call<ITNCreateResponse> call, @NonNull Response<ITNCreateResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    ITNCreateResponse itnCreateResponse = response.body();
                    assert itnCreateResponse != null;
                    tranferModes = itnCreateResponse.getData().getTransferModeList();

                    ArrayAdapter<String> priorityAadapter = new ArrayAdapter<>(InternalTransferNoteActivity.this, android.R.layout.simple_spinner_item, tranferModes);
                    priorityAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_modeOfTransfer.setAdapter(priorityAadapter);

                    serviceProviders = itnCreateResponse.getData().getServiceProviderList();
                    for (ServiceProvider obj : serviceProviders){
                        serviceProviderList.add(obj.getUserName());
                    }
                    ArrayAdapter<String> spAadapter = new ArrayAdapter<>(InternalTransferNoteActivity.this, android.R.layout.simple_spinner_item, serviceProviderList);
                    spAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_serviceProvider.setAdapter(spAadapter);

                    location = itnCreateResponse.getData().getLocations();
                    ArrayList<String> locationlist = new ArrayList<>();
                    for (Location obj : location) {
                        locationlist.add(obj.getLocationName());
                    }
                    ArrayAdapter<String> locationlistadapter = new ArrayAdapter<>(InternalTransferNoteActivity.this, android.R.layout.simple_spinner_item, locationlist);
                    locationlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_location.setAdapter(locationlistadapter);

                    workshop = itnCreateResponse.getData().getWorkshops();
                    ArrayList<String> workshoplist = new ArrayList<>();
                    for (Workshop obj : workshop) {
                        workshoplist.add(obj.getWorkshopName());
                    }
                    ArrayAdapter<String> workshopadapter = new ArrayAdapter<>(InternalTransferNoteActivity.this, android.R.layout.simple_spinner_item, workshoplist);
                    workshopadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_fromWorkshop.setAdapter(workshopadapter);

                    packingtype = itnCreateResponse.getData().getPackingTypeList();

                    ArrayAdapter<String> packingtypeadapter = new ArrayAdapter<>(InternalTransferNoteActivity.this, android.R.layout.simple_spinner_item, packingtype);
                    packingtypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dd_packingType.setAdapter(packingtypeadapter);

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(InternalTransferNoteActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(InternalTransferNoteActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<ITNCreateResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(InternalTransferNoteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            Intent intent = new Intent(InternalTransferNoteActivity.this, TicketHandlingUserActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(InternalTransferNoteActivity.this, TicketHandlingActivity.class);
            startActivity(intent);
            finish();
        }
    }

}