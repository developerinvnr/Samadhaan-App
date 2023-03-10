package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.addassetforms.Utility;
import com.vnrseeds.samadhan.parser.addtoticketparser.AddToTicketResponse;
import com.vnrseeds.samadhan.parser.issuelistparser.Datum;
import com.vnrseeds.samadhan.parser.issuelistparser.IssueListResponse;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.submoduleparser.SubModuleListResponse;
import com.vnrseeds.samadhan.parser.ticketassetparser.UserApplication;
import com.vnrseeds.samadhan.parser.ticketassetparser.UserAsset;
import com.vnrseeds.samadhan.parser.ticketcreateparser.Module;
import com.vnrseeds.samadhan.parser.ticketcreateparser.Priority;
import com.vnrseeds.samadhan.parser.ticketcreateparser.TicketCreateResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.AppConfig;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTicketActivity extends AppCompatActivity {
    private static final String TAG = "RaiseTicketActivity";
    private static final String SELECT = "select";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private String ticketFor = "Self";
    private ImageButton back_nav;
    private AutoCompleteTextView spinner_priority;
    private AutoCompleteTextView spinner_issue;
    private EditText et_issue_title;
    private EditText et_ticket_desc;
    private ImageView iv_issuephoto;

    private List<UserAsset> assetsdata;
    private final ArrayList<String> assetDataList = new ArrayList<>();
    private List<String> serviceTypesList = new ArrayList<>();
    private String ticketAction="Raise";
    private List<Priority> priorities;
    private final ArrayList<String> priorityList = new ArrayList<>();
    private List<UserApplication> applicationdata;
    private final ArrayList<String> appDataList = new ArrayList<>();
    private String assetcategory_id="";
    private List<Datum> issueListData;
    private final ArrayList<String> issueList = new ArrayList<>();
    private Button bt_submit;
    private String servicetype;
    private String application_id="";
    private String priority_id;
    private String issue_id="";
    private RoleResponse roleResponse;

    //Add Photo
    private static final int PERMISSION_CODE = 1234;
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    private String path_billcopy;
    private final List<MultipartBody.Part> list = new ArrayList<>();
    private File BILL_COPY;
    private Uri image_uri;
    private String userChoosenTask;
    private TicketDetailsPojo ticketDetailsPojo;
    //private TextView tv_ticketno;
    private TextView tv_servicetype;
    private User userData;
    private String raisedByID;
    private TextView tv_assetname;
    private String ticketno;
    private TextView tv_issuephoto;
    private TextView tv_spinnerIssue;
    private String[] issuelist = new String[0];
    private boolean[] selectedIssues;
    private final ArrayList<Integer> issueArray = new ArrayList<>();
    private TextInputLayout il_issueTitle;
    private List<com.vnrseeds.samadhan.parser.addtoticketparser.Datum> ticketListData;
    private LinearLayout ll_addToTicket;
    private LinearLayout ll_hide_content;
    private final ArrayList<String> ticketList = new ArrayList<>();
    private Spinner spinner_ticketlist;
    private LinearLayout ll_issuephoto;
    private ImageView iv_assetImage;
    private ArrayList<Uri> mArrayUri=new ArrayList<>();
    private int position=0;
    private ImageView iv_next;
    private ImageView iv_previous;
    private LinearLayout ll_classification;
    private LinearLayout ll_subClassification;
    private List<Module> classifications;
    private ArrayList<String> classificationsList=new ArrayList<>();
    private AutoCompleteTextView spinner_classification;
    private AutoCompleteTextView spinner_subClassification;
    private String classification_id="";
    private List<com.vnrseeds.samadhan.parser.submoduleparser.Datum> subClassifications;
    private ArrayList<String> subClassificationsList=new ArrayList<>();
    private String subclassification_id="";
    private String issue_subclassification_id="";
    private String issue_classification_id="";
    private TextView tv_asset_category;
    private int other_issue_selected;
    private TextView tv_ticketfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        customProgressDialogue = new CustomProgressDialogue(EditTicketActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Edit Ticket");
        back_nav = findViewById(R.id.back_nav);

        spinner_priority = findViewById(R.id.spinner_priority);
        spinner_issue = findViewById(R.id.spinner_issue);
        tv_spinnerIssue = findViewById(R.id.tv_spinnerIssue);
        il_issueTitle = findViewById(R.id.il_issueTitle);
        iv_assetImage = findViewById(R.id.iv_assetImage);

        et_issue_title = findViewById(R.id.et_issue_title);
        et_ticket_desc = findViewById(R.id.et_ticket_desc);

        //tv_ticketno = findViewById(R.id.tv_ticketno);
        tv_servicetype = findViewById(R.id.tv_servicetype);
        tv_assetname = findViewById(R.id.tv_assetname);
        tv_ticketfor = findViewById(R.id.tv_ticketfor);
        tv_asset_category = findViewById(R.id.tv_asset_category);
        tv_issuephoto = findViewById(R.id.tv_issuephoto);
        iv_next = findViewById(R.id.iv_next);
        iv_previous = findViewById(R.id.iv_previous);

        iv_issuephoto = findViewById(R.id.iv_issuephoto);
        ll_addToTicket = findViewById(R.id.ll_addToTicket);
        ll_hide_content = findViewById(R.id.ll_hide_content);
        ll_issuephoto = findViewById(R.id.ll_issuephoto);
        spinner_ticketlist = findViewById(R.id.spinner_ticketlist);
        bt_submit = findViewById(R.id.bt_submit);
        ll_classification = findViewById(R.id.ll_classification);
        ll_subClassification = findViewById(R.id.ll_subClassification);
        spinner_classification = findViewById(R.id.spinner_classification);
        spinner_subClassification = findViewById(R.id.spinner_subClassification);

        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        tv_ticketfor.setText(ticketDetailsPojo.getTicket_user_type());
        if (ticketDetailsPojo.getTicketModuleId()!=null){
            classification_id=ticketDetailsPojo.getTicketModuleId();
        }
        if (ticketDetailsPojo.getTicketSubModuleId()!=null){
            subclassification_id=ticketDetailsPojo.getTicketSubModuleId();
        }
        issue_classification_id=ticketDetailsPojo.getTicketModuleId();
        issue_subclassification_id=ticketDetailsPojo.getTicketSubModuleId();

        if (issue_subclassification_id==null){
            issue_classification_id="";
        }
        if (ticketDetailsPojo.getModuleName()!=null) {
            spinner_classification.setText(ticketDetailsPojo.getModuleName());
        }else {
            classification_id="";
        }
        if (ticketDetailsPojo.getSubModuleName()!=null) {
            spinner_subClassification.setText(ticketDetailsPojo.getSubModuleName());
        }else {
            subclassification_id="";
        }
        ticketno=ticketDetailsPojo.getTicketNo();
        //tv_ticketno.setText(ticketDetailsPojo.getTicketNo());
        tv_servicetype.setText(ticketDetailsPojo.getServiceType());
        tv_assetname.setText(ticketDetailsPojo.getAssetCategory());
        et_ticket_desc.setText(ticketDetailsPojo.getIssueDesc());
        spinner_priority.setText(ticketDetailsPojo.getPriority());
        if (ticketDetailsPojo.getAssetIsByod().equalsIgnoreCase("1")){
            tv_asset_category.setText(ticketDetailsPojo.getTicketAssetAcName()+"(BYOD)");
        }else {
            tv_asset_category.setText(ticketDetailsPojo.getAssetCategory());
        }
        issue_id= ticketDetailsPojo.getTicketIssueIdsStr();
        /*if (ticketDetailsPojo.getIssueName()!=null && !ticketDetailsPojo.getIssueName().equalsIgnoreCase("")){
            tv_spinnerIssue.setText(ticketDetailsPojo.getIssueName());
            il_issueTitle.setVisibility(View.GONE);
        }else {
            //tv_spinnerIssue.setText(ticketDetailsPojo.getTicketIssueOther());
            tv_spinnerIssue.setText("Other\n????????????");
            il_issueTitle.setVisibility(View.VISIBLE);
            et_issue_title.setText(ticketDetailsPojo.getTicketIssueOther());
        }*/
        if (ticketDetailsPojo.getTicketIssueOther()!=null && ticketDetailsPojo.getIssueName()!=null){
            tv_spinnerIssue.setText(ticketDetailsPojo.getIssueName()+",Other\n????????????");
            il_issueTitle.setVisibility(View.VISIBLE);
            et_issue_title.setText(ticketDetailsPojo.getTicketIssueOther());
            other_issue_selected=1;
        }else if (ticketDetailsPojo.getIssueName()!=null){
            tv_spinnerIssue.setText(ticketDetailsPojo.getIssueName());
            il_issueTitle.setVisibility(View.GONE);
        }else if (ticketDetailsPojo.getTicketIssueOther()!=null){
            other_issue_selected=1;
            tv_spinnerIssue.setText("Other\n????????????");
            il_issueTitle.setVisibility(View.VISIBLE);
            et_issue_title.setText(ticketDetailsPojo.getTicketIssueOther());
        }

        priority_id=ticketDetailsPojo.getTicket_priority_id();
        raisedByID=ticketDetailsPojo.getTicket_raised_by_id();
        assetcategory_id=ticketDetailsPojo.getTicket_service_type_id();
        application_id=ticketDetailsPojo.getTicket_service_type_id();
        servicetype=ticketDetailsPojo.getServiceType();
        String ac_shcode = ticketDetailsPojo.getTicketAssetAcName();

        if (servicetype.equalsIgnoreCase("Hardware")) {
            ll_classification.setVisibility(View.GONE);
            ll_subClassification.setVisibility(View.GONE);
            iv_assetImage.setColorFilter(Color.parseColor("#49454F"));
            if (ac_shcode.equalsIgnoreCase("Desktop")) {
                iv_assetImage.setBackgroundResource(R.drawable.desktop);
            } else if (ac_shcode.equalsIgnoreCase("Access Point")) {
                iv_assetImage.setBackgroundResource(R.drawable.access_point);
            } else if (ac_shcode.equalsIgnoreCase("Amplifier")) {
                iv_assetImage.setBackgroundResource(R.drawable.amplifier);
            } else if (ac_shcode.equalsIgnoreCase("Barcode Scanner")) {
                iv_assetImage.setBackgroundResource(R.drawable.barcode_scanner);
            } else if (ac_shcode.equalsIgnoreCase("Biometric Attendance")) {
                iv_assetImage.setBackgroundResource(R.drawable.biometric_attendance);
            } else if (ac_shcode.equalsIgnoreCase("Camera")) {
                iv_assetImage.setBackgroundResource(R.drawable.camera);
            } else if (ac_shcode.equalsIgnoreCase("Desk Phone")) {
                iv_assetImage.setBackgroundResource(R.drawable.desk_phone);
            } else if (ac_shcode.equalsIgnoreCase("Digital Video Recorder")) {
                iv_assetImage.setBackgroundResource(R.drawable.digital_video_recorder);
            } else if (ac_shcode.equalsIgnoreCase("EPABX")) {
                iv_assetImage.setBackgroundResource(R.drawable.epabx);
            } else if (ac_shcode.equalsIgnoreCase("Firewall")) {
                iv_assetImage.setBackgroundResource(R.drawable.firewall);
            } else if (ac_shcode.equalsIgnoreCase("Hard Disk Drive")) {
                iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ac_shcode.equalsIgnoreCase("Laptop")) {
                iv_assetImage.setBackgroundResource(R.drawable.laptop);
            } else if (ac_shcode.equalsIgnoreCase("Microphone")) {
                iv_assetImage.setBackgroundResource(R.drawable.microphone);
            } else if (ac_shcode.equalsIgnoreCase("Mobile")) {
                iv_assetImage.setBackgroundResource(R.drawable.mobile);
            } else if (ac_shcode.equalsIgnoreCase("Modem")) {
                iv_assetImage.setBackgroundResource(R.drawable.modem);
            } else if (ac_shcode.equalsIgnoreCase("Monitor")) {
                iv_assetImage.setBackgroundResource(R.drawable.desktop);
            } else if (ac_shcode.equalsIgnoreCase("Network Video Recorder")) {
                iv_assetImage.setBackgroundResource(R.drawable.network_video_recorder);
            } else if (ac_shcode.equalsIgnoreCase("Printer")) {
                iv_assetImage.setBackgroundResource(R.drawable.printer);
            } else if (ac_shcode.equalsIgnoreCase("Rack Enclosure")) {
                iv_assetImage.setBackgroundResource(R.drawable.rack_enclosure);
            } else if (ac_shcode.equalsIgnoreCase("Router")) {
                iv_assetImage.setBackgroundResource(R.drawable.router);
            } else if (ac_shcode.equalsIgnoreCase("Scanner")) {
                iv_assetImage.setBackgroundResource(R.drawable.scanner);
            } else if (ac_shcode.equalsIgnoreCase("Server")) {
                iv_assetImage.setBackgroundResource(R.drawable.server);
            } else if (ac_shcode.equalsIgnoreCase("SFP Transceiver")) {
                iv_assetImage.setBackgroundResource(R.drawable.sfp_transreceiver);
            } else if (ac_shcode.equalsIgnoreCase("Speaker")) {
                iv_assetImage.setBackgroundResource(R.drawable.speaker);
            } else if (ac_shcode.equalsIgnoreCase("SSD Drive")) {
                iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ac_shcode.equalsIgnoreCase("Storage Device")) {
                iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ac_shcode.equalsIgnoreCase("Switch")) {
                iv_assetImage.setBackgroundResource(R.drawable.resource_switch);
            } else if (ac_shcode.equalsIgnoreCase("Tablet")) {
                iv_assetImage.setBackgroundResource(R.drawable.tablet);
            } else if (ac_shcode.equalsIgnoreCase("Television")) {
                iv_assetImage.setBackgroundResource(R.drawable.television);
            } else if (ac_shcode.equalsIgnoreCase("Thin Client")) {
                iv_assetImage.setBackgroundResource(R.drawable.thin_client);
            } else if (ac_shcode.equalsIgnoreCase("UPS")) {
                iv_assetImage.setBackgroundResource(R.drawable.ups);
            } else if (ac_shcode.equalsIgnoreCase("Wireless Controller")) {
                iv_assetImage.setBackgroundResource(R.drawable.wireless_controller);
            } else if (ac_shcode.equalsIgnoreCase("Wireless RF Device")) {
                iv_assetImage.setBackgroundResource(R.drawable.wireless_rf_device);
            } else {
                iv_assetImage.setBackgroundResource(R.drawable.desktop);
            }
        }else {
            ll_classification.setVisibility(View.VISIBLE);
            if (ticketDetailsPojo.getSubModuleName()!=null) {
                ll_subClassification.setVisibility(View.VISIBLE);
            }else {
                ll_subClassification.setVisibility(View.GONE);
            }
            Glide.with(this)
                    .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/application_softwares/"+ticketDetailsPojo.getTicketServiceTypeIcon()))
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .into(iv_assetImage);
        }
        getIssueList(servicetype,assetcategory_id);

        /*if (ticketDetailsPojo.getIssueImage()!=null) {
            Glide.with(EditTicketActivity.this)
                    .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + ticketDetailsPojo.getIssueImage()))
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .into(iv_issuephoto);
        }*/

        //userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        //raisedByID = userData.getUser_id();
        if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Open")){
            spinner_priority.setEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                spinner_priority.setAllowClickWhenDisabled(false);
            }
            spinner_issue.setEnabled(false);
        }
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(view -> {
            Intent intent = new Intent(EditTicketActivity.this, TicketsListActivity.class);
            startActivity(intent);
            finish();
        });

        getTicketCreateData(ticketAction);
        spinner_priority.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i >= 0) {
                priority_id = String.valueOf(priorities.get(i).getPriorityId());
            }
        });
        spinner_issue.setOnItemClickListener((adapterView, view, i, l) -> {
            String issue = spinner_issue.getText().toString();
            if (issue.equalsIgnoreCase("Other\n????????????")){
                il_issueTitle.setVisibility(View.VISIBLE);
                /*if (issue_id.equalsIgnoreCase("")) {
                    issue_id = "0";
                }else {
                    issue_id = issue_id+",0";
                }*/
                issue_id = String.valueOf(issueListData.get(i - 1).getIssueId());
            }else {
                il_issueTitle.setVisibility(View.GONE);
                if (i > 0) {
                    issue_id = String.valueOf(issueListData.get(i - 1).getIssueId());
                }
            }
        });

        tv_spinnerIssue.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditTicketActivity.this);
            builder.setTitle("Select Issue");
            builder.setCancelable(false);
            builder.setMultiChoiceItems(issuelist, selectedIssues, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    if (b) {
                        issueArray.add(i);
                        Collections.sort(issueArray);
                    } else {
                        issueArray.remove(Integer.valueOf(i));
                    }
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (issueArray.size()>0) {
                        for (int j = 0; j < issueArray.size(); j++) {
                            stringBuilder.append(issuelist[issueArray.get(j)]);
                            if (issuelist[issueArray.get(j)].equalsIgnoreCase("Other\n????????????")) {
                                other_issue_selected = 1;
                                il_issueTitle.setVisibility(View.VISIBLE);
                            } else {
                                other_issue_selected = 0;
                                il_issueTitle.setVisibility(View.GONE);
                                if (i > 0) {
                                    //issue_id = String.valueOf(issueListData.get(i - 1).getIssueId());
                                    //getThisAssetTicketList(issue_id);
                                }
                            }
                            if (j != issueArray.size() - 1) {
                                stringBuilder.append(",");
                            }
                        }
                    }else{
                        il_issueTitle.setVisibility(View.GONE);
                        et_issue_title.setText("");
                    }
                    Log.e("IssueArraySize", String.valueOf(issueArray.size()));
                    issue_id="";
                    for (int k = 0; k < issueArray.size(); k++) {
                        int ind = issueArray.get(k);
                        if (issue_id.equalsIgnoreCase("")) {
                            issue_id = String.valueOf(issueListData.get(ind).getIssueId());
                        } else {
                            issue_id = issue_id + "," + issueListData.get(ind).getIssueId();
                        }
                    }
                    Log.e("IssueArray", issue_id);
                    if (issueArray.size()>0) {
                        //getThisAssetTicketList(issue_id);
                    }
                    tv_spinnerIssue.setText(stringBuilder.toString());
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
                    issue_id="";
                    for (int j = 0; j < selectedIssues.length; j++) {
                        selectedIssues[j] = false;
                        issueArray.clear();
                        tv_spinnerIssue.setText("");
                        il_issueTitle.setVisibility(View.GONE);
                    }
                }
            });
            builder.show();
        });

        /*iv_issuephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(EditTicketActivity.this);
                dialog.setContentView(R.layout.image_preview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();

                ImageButton ib_close = dialog.findViewById(R.id.ib_close);
                ImageView image_preview = dialog.findViewById(R.id.image_preview);

                if (image_uri!=null){
                    image_preview.setImageURI(image_uri);
                }else {
                    if (ticketDetailsPojo.getIssueImage()!=null){
                        //image_preview.setImageURI(image_uri);
                        Glide.with(EditTicketActivity.this)
                                .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + ticketDetailsPojo.getIssueImage()))
                                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                .into(image_preview);
                    }

                }


                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });*/

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String action="Raise";
                String ticket_desc = et_ticket_desc.getText().toString();
                String servicetype = ticketDetailsPojo.getServiceType();

                String priority = spinner_priority.getText().toString();
                String issue = tv_spinnerIssue.getText().toString();
                String issue_title = et_issue_title.getText().toString().trim();

                Log.e(TAG, String.valueOf(issueArray));
                /*for (int i = 0; i < issueArray.size(); i++) {
                    int ind = issueArray.get(i);
                    if (issue_id.equalsIgnoreCase("")) {
                        issue_id = String.valueOf(issueListData.get(ind).getIssueId());
                    } else {
                        issue_id = issue_id + "," + issueListData.get(ind).getIssueId();
                    }
                }*/

                if (priority.equalsIgnoreCase(SELECT)){
                    Utils.getInstance().showAlert(EditTicketActivity.this, "Select priority");
                }else if (issue.equalsIgnoreCase("Select issue") || issue.equalsIgnoreCase("")){
                    Utils.getInstance().showAlert(EditTicketActivity.this, "Select issue");
                }else if (other_issue_selected==1 && issue_title.equalsIgnoreCase("")){
                    Utils.getInstance().showAlert(EditTicketActivity.this, "Enter issue title");
                }else if (ticket_desc.length()>1000){
                    Utils.getInstance().showAlert(EditTicketActivity.this, "Description should not be greater than 1000 characters");
                }else {
                    Log.e(TAG, action+"="+ticketFor+"="+servicetype+"="+assetcategory_id+"="+application_id+"="+priority_id+"="+issue_id+"="+ticket_desc+"="+issue_title+"="+issue);
                    submitRaiseTicketForm(action,ticketFor,servicetype,assetcategory_id,application_id,priority_id,issue_id,ticket_desc,issue_title,issue);
                }
            }
        });

        tv_issuephoto.setOnClickListener(view -> {
            selectImage();
        });

        // click here to view previous image
        iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    //decrease the position by 1
                    position--;
                    iv_issuephoto.setImageURI(mArrayUri.get(position));
                }else {
                    Toast.makeText(EditTicketActivity.this, "First Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // click here to select next image
        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < mArrayUri.size() - 1) {
                    // increase the position by 1
                    position++;
                    iv_issuephoto.setImageURI(mArrayUri.get(position));
                } else {
                    Toast.makeText(EditTicketActivity.this, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinner_classification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    issue_subclassification_id="";
                    subclassification_id="";
                    issue_id="";
                    for (int j = 0; j < selectedIssues.length; j++) {
                        selectedIssues[j] = false;
                        issueArray.clear();
                        tv_spinnerIssue.setText("");
                        et_issue_title.setText("");
                        il_issueTitle.setVisibility(View.GONE);
                    }
                    classification_id = String.valueOf(classifications.get(i).getModuleId());
                    issue_classification_id = String.valueOf(classifications.get(i).getModuleId());
                    getSubClassifications(classification_id);
                }
            }
        });

        spinner_subClassification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0) {
                    issue_classification_id="";
                    issue_id="";
                    for (int j = 0; j < selectedIssues.length; j++) {
                        selectedIssues[j] = false;
                        issueArray.clear();
                        tv_spinnerIssue.setText("");
                        et_issue_title.setText("");
                        il_issueTitle.setVisibility(View.GONE);
                    }
                    subclassification_id = String.valueOf(subClassifications.get(i).getSmId());
                    issue_subclassification_id = String.valueOf(subClassifications.get(i).getSmId());
                    getIssueList(servicetype,assetcategory_id);
                }
            }
        });

        iv_issuephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrayUri.size()>0) {
                    final Dialog dialog = new Dialog(EditTicketActivity.this);
                    dialog.setContentView(R.layout.custom_image_popup);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();

                    ImageView iv_imageRemove = dialog.findViewById(R.id.iv_imageRemove);
                    ImageView iv_close = dialog.findViewById(R.id.iv_close);
                    ImageView iv_issuephoto1 = dialog.findViewById(R.id.iv_issuephoto);
                    ImageView iv_previous = dialog.findViewById(R.id.iv_previous);
                    ImageView iv_next = dialog.findViewById(R.id.iv_next);

                    iv_issuephoto1.setImageURI(mArrayUri.get(0));

                    iv_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    iv_imageRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mArrayUri.size() > 0) {
                                mArrayUri.remove(position);
                                list.remove(position);
                                position=0;
                                if (mArrayUri.size() > 0) {
                                    iv_issuephoto1.setImageURI(mArrayUri.get(position));
                                    iv_issuephoto.setImageURI(mArrayUri.get(position));
                                } else {
                                    iv_issuephoto1.setImageDrawable(null);
                                    iv_issuephoto.setImageDrawable(null);
                                }
                            }
                        }
                    });

                    iv_previous.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (position > 0) {
                                //decrease the position by 1
                                position--;
                                iv_issuephoto1.setImageURI(mArrayUri.get(position));
                            } else {
                                Toast.makeText(EditTicketActivity.this, "First Image Already Shown", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // click here to select next image
                    iv_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (position < mArrayUri.size() - 1) {
                                // increase the position by 1
                                position++;
                                iv_issuephoto1.setImageURI(mArrayUri.get(position));
                            } else {
                                Toast.makeText(EditTicketActivity.this, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void getSubClassifications(String classification_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubModuleListResponse> call = apiInterface.getSubModuleDataInfo("application/json", "Bearer " + token, classification_id);
        call.enqueue(new Callback<SubModuleListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<SubModuleListResponse> call, @NonNull Response<SubModuleListResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubModuleListResponse subModuleListResponse = response.body();
                    assert subModuleListResponse != null;
                    spinner_subClassification.setText("Select");
                    subClassifications = subModuleListResponse.getData();
                    subClassificationsList.clear();
                    if (!subClassifications.isEmpty()) {
                        ll_subClassification.setVisibility(View.VISIBLE);
                        for (com.vnrseeds.samadhan.parser.submoduleparser.Datum obj : subClassifications) {
                            subClassificationsList.add(obj.getSmName());
                        }
                    }else {
                        ll_subClassification.setVisibility(View.GONE);
                    }
                    ArrayAdapter<String> subClassificationsAadapter = new ArrayAdapter<>(EditTicketActivity.this, android.R.layout.simple_spinner_item, subClassificationsList);
                    subClassificationsAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_subClassification.setAdapter(subClassificationsAadapter);
                    getIssueList(servicetype,assetcategory_id);
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTicketActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTicketActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SubModuleListResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(EditTicketActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitRaiseTicketForm(String action, String ticketFor, String servicetype, String assetcategory_id, String application_id, String priority_id, String issue_id, String ticket_desc, String issue_title, String issue) {
        RequestBody action1 = RequestBody.create(MediaType.parse("text/plain"), action);
        RequestBody ticketFor1 = RequestBody.create(MediaType.parse("text/plain"), ticketFor);
        RequestBody servicetype1 = RequestBody.create(MediaType.parse("text/plain"), servicetype);
        RequestBody assetcategory_id1 = RequestBody.create(MediaType.parse("text/plain"), assetcategory_id);
        RequestBody application_id1 = RequestBody.create(MediaType.parse("text/plain"), application_id);
        RequestBody priority_id1 = RequestBody.create(MediaType.parse("text/plain"), priority_id);
        RequestBody issue_id1 = RequestBody.create(MediaType.parse("text/plain"), issue_id);
        RequestBody ticket_desc1 = RequestBody.create(MediaType.parse("text/plain"), ticket_desc);
        RequestBody issue_title1 = RequestBody.create(MediaType.parse("text/plain"), issue_title);
        RequestBody ticket_id = RequestBody.create(MediaType.parse("text/plain"), ticketDetailsPojo.getTicketId());
        RequestBody issue1 = RequestBody.create(MediaType.parse("text/plain"), issue);
        RequestBody raisedByID1 = RequestBody.create(MediaType.parse("text/plain"), raisedByID);
        RequestBody classification_id1 = RequestBody.create(MediaType.parse("text/plain"), classification_id);
        RequestBody subclassification_id1 = RequestBody.create(MediaType.parse("text/plain"), subclassification_id);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateInfo("application/json", "Bearer " + token, action1, ticketFor1,servicetype1,assetcategory_id1,application_id1,priority_id1,ticket_desc1,issue_id1,issue_title1,ticket_id,issue1,raisedByID1,classification_id1,subclassification_id1,list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(EditTicketActivity.this, TicketsListActivity.class);
                        Toast.makeText(EditTicketActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTicketActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTicketActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SubmitSuccessResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(EditTicketActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIssueList(String servicetype, String assetcategory_id) {
        Log.e(TAG, servicetype+"="+assetcategory_id+"="+ticketDetailsPojo.getTicketId());
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<IssueListResponse> call = apiInterface.getIssueListDataInfo("application/json", "Bearer " + token, servicetype, assetcategory_id, ticketDetailsPojo.getTicketId(), ticketDetailsPojo.getTicket_user_id(), issue_classification_id, issue_subclassification_id);
        call.enqueue(new Callback<IssueListResponse>() {
            @Override
            public void onResponse(@NonNull Call<IssueListResponse> call, @NonNull Response<IssueListResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    IssueListResponse issueListResponse = response.body();
                    assert issueListResponse != null;
                    issueListData = issueListResponse.getData();
                    issueList.clear();
                    ArrayList<String> issuelist1 = new ArrayList<>();
                    if (ticketDetailsPojo.getTicket_issue_ids().equalsIgnoreCase("0")){
                        et_issue_title.setText(ticketDetailsPojo.getIssueTitle());
                    }else {
                        issueList.add(ticketDetailsPojo.getIssueTitle());
                    }
                    for (Datum obj : issueListData) {
                        issueList.add(obj.getIssueName());
                        issuelist1.add(obj.getIssueName()+"\n"+obj.getIssueHindiName());
                    }

                    issuelist = issuelist1.toArray(new String[0]);
                    selectedIssues = new boolean[issuelist.length];
                    String[] brlist = new String[0];
                    if (!ticketDetailsPojo.getTicketIssueIdsStr().equalsIgnoreCase("")){
                        brlist = ticketDetailsPojo.getTicketIssueIdsStr().split(",");
                    }
                    Log.e(TAG, String.valueOf(issueList));
                    for (int i = 0; i < issueListData.size(); i++) {
                        if (Arrays.asList(brlist).contains(issueListData.get(i).getIssueId())) {
                            selectedIssues[i] = true;
                            issueArray.add(i);
                            Log.e(TAG, "selectedbrowser :" + selectedIssues[i]);
                        }
                    }

                    ArrayAdapter<String> issuesAadapter = new ArrayAdapter<>(EditTicketActivity.this, android.R.layout.simple_spinner_item, issuelist);
                    issuesAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_issue.setAdapter(issuesAadapter);
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTicketActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTicketActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<IssueListResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(EditTicketActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTicketCreateData(String ticketAction) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<TicketCreateResponse> call = apiInterface.getTicketCreateDataInfo("application/json", "Bearer " + token, ticketAction, servicetype, assetcategory_id);
        call.enqueue(new Callback<TicketCreateResponse>() {
            @Override
            public void onResponse(@NonNull Call<TicketCreateResponse> call, @NonNull Response<TicketCreateResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    TicketCreateResponse ticketCreateResponse = response.body();
                    assert ticketCreateResponse != null;
                    priorities = ticketCreateResponse.getData().getPriorityList();
                    priorityList.clear();
                    //priorityList.add(ticketDetailsPojo.getPriority());
                    for (Priority obj : priorities) {
                        priorityList.add(obj.getPriorityName());
                    }
                    ArrayAdapter<String> priorityAadapter = new ArrayAdapter<>(EditTicketActivity.this, android.R.layout.simple_spinner_item, priorityList);
                    priorityAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_priority.setAdapter(priorityAadapter);

                    classifications = ticketCreateResponse.getData().getModuleList();
                    classificationsList.clear();
                    for (Module obj : classifications) {
                        classificationsList.add(obj.getModuleName());
                    }
                    ArrayAdapter<String> classificationsAadapter = new ArrayAdapter<>(EditTicketActivity.this, android.R.layout.simple_spinner_item, classificationsList);
                    classificationsAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_classification.setAdapter(classificationsAadapter);
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTicketActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTicketActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TicketCreateResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(EditTicketActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getThisAssetTicketList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<AddToTicketResponse> call = apiInterface.getAddToTicketInfo("application/json", "Bearer " + token, servicetype, assetcategory_id, raisedByID);
        call.enqueue(new Callback<AddToTicketResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddToTicketResponse> call, @NonNull Response<AddToTicketResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    AddToTicketResponse addToTicketResponse = response.body();
                    assert addToTicketResponse != null;
                    ticketListData = addToTicketResponse.getData();
                    if (!ticketListData.isEmpty()){
                        ll_addToTicket.setVisibility(View.VISIBLE);
                        ll_hide_content.setVisibility(View.GONE);
                        ticketList.clear();
                        ticketList.add(SELECT);
                        for (com.vnrseeds.samadhan.parser.addtoticketparser.Datum obj : ticketListData) {
                            ticketList.add(obj.getTicketCode()+" - "+obj.getRaiseBy());
                        }
                        ArrayAdapter<String> ticketListAadapter = new ArrayAdapter<>(EditTicketActivity.this, android.R.layout.simple_spinner_item, ticketList);
                        ticketListAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_ticketlist.setAdapter(ticketListAadapter);
                    }else {
                        ll_addToTicket.setVisibility(View.GONE);
                        ll_hide_content.setVisibility(View.VISIBLE);
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTicketActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTicketActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddToTicketResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(EditTicketActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Click here to open camera", "Choose from gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditTicketActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditTicketActivity.this);

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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bitmap != null;
        String absolutePath = saveImage(bitmap);
        path_billcopy = getPath(image_uri);
        list.add(prepareFilePart("ticket_raise_image[]", Uri.parse(absolutePath)));

        /*if (data.getClipData() != null) {
            ClipData mClipData = data.getClipData();
            Log.e("Data:", String.valueOf(mClipData));
            int cout = data.getClipData().getItemCount();
            for (int i = 0; i < cout; i++) {
                //adding imageuri in array
                Uri imageurl = data.getClipData().getItemAt(i).getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageurl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert bitmap != null;
                String absolutePath = saveImage(bitmap);
                mArrayUri.add(imageurl);
                list.add(prepareFilePart("ticket_raise_files[]", Uri.parse(absolutePath)));
            }
            //setting 1st selected image into image switcher
            ll_issuephoto.setVisibility(View.VISIBLE);
            iv_issuephoto.setImageURI(mArrayUri.get(0));
            position = 0;
        } else {
            Uri imageurl = data.getData();
            mArrayUri.add(imageurl);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageurl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bitmap != null;
            String absolutePath = saveImage(bitmap);
            list.add(prepareFilePart("ticket_raise_files[]", Uri.parse(absolutePath)));
            ll_issuephoto.setVisibility(View.VISIBLE);
            iv_issuephoto.setImageURI(mArrayUri.get(0));
            position = 0;
        }*/
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
        ll_issuephoto.setVisibility(View.VISIBLE);
        /*Bitmap bm = null;
        Bitmap selectedBitmap = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                iv_issuephoto.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        if (data.getClipData() != null) {
            ClipData mClipData = data.getClipData();
            Log.e("Data:", String.valueOf(mClipData));
            int cout = data.getClipData().getItemCount();
            for (int i = 0; i < cout; i++) {
                //adding imageuri in array
                Uri imageurl = data.getClipData().getItemAt(i).getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageurl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert bitmap != null;
                String absolutePath = saveImage(bitmap);
                mArrayUri.add(imageurl);
                list.add(prepareFilePart("ticket_raise_files[]", Uri.parse(absolutePath)));
            }
            //setting 1st selected image into image switcher
            ll_issuephoto.setVisibility(View.VISIBLE);
            iv_issuephoto.setImageURI(mArrayUri.get(0));
            position = 0;
        } else {
            Uri imageurl = data.getData();
            mArrayUri.add(imageurl);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageurl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bitmap != null;
            String absolutePath = saveImage(bitmap);
            list.add(prepareFilePart("ticket_raise_files[]", Uri.parse(absolutePath)));
            ll_issuephoto.setVisibility(View.VISIBLE);
            iv_issuephoto.setImageURI(mArrayUri.get(0));
            position = 0;
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ESS/");
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
            MediaScannerConnection.scanFile(EditTicketActivity.this, new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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
        Intent intent = new Intent(EditTicketActivity.this, TicketsListActivity.class);
        startActivity(intent);
        finish();
    }
}