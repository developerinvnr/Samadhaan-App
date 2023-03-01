package com.vnrseeds.samadhan.ticketsystem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.seviceproviderlistparser.ServiceProviderListResponse;
import com.vnrseeds.samadhan.parser.seviceproviderlistparser.User;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.parser.ticketviewparser.RaiseData;
import com.vnrseeds.samadhan.parser.ticketviewparser.TicketViewResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.AppConfig;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kotlin.io.TextStreamsKt;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";
    private TextView tv_ticketdate;
    private TextView tv_ticketno;
    private TextView tv_ticket_title;
    private TextView tv_priority;
    private TextView tv_assignto;
    private TextView tv_ticketStatus;
    private TextView tv_category;
    private TextView tv_astype;
    private TextView tv_custodian;
    private TextView tv_ticket_desc;
    private Button button_submit;
    private String strtext;
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private TicketDetailsPojo ticketDetailsPojo;
    private RoleResponse roleResponse;
    private LinearLayout ll_assignto;
    private List<User> users;
    private ArrayList<String> userlist = new ArrayList<>();
    private String assignto_id="";
    private CheckBox checkbox_assignto;
    private LinearLayout ll_check_assignto;
    private LinearLayout ll_issuephoto;
    private ImageView iv_issuephoto;
    private Button button_assign;
    private Button button_esdt;
    private AutoCompleteTextView spinner_assignto;
    private LinearLayout ll_buttons;
    private TextView tv_assetname;
    private ImageView iv_assetImage;
    private TextView tv_raisedBy;
    private TextView tv_visitdate;
    private ImageView iv_previous;
    private ImageView iv_next;
    private int position=0;
    private List<String> imageList=new ArrayList<>();
    private TextView tv_assetIsByod;
    private LinearLayout ll_visitDate;
    private RaiseData ticketData;
    private LinearLayout ll_classification;
    private LinearLayout ll_subClassification;
    private TextView tv_classification;
    private TextView tv_subClassification;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTheme(view);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme(View view) {
        SharedPreferences.getInstance(getContext());
        Utils.getInstance(getContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(getActivity());
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        //spinner_assignto = view.findViewById(R.id.spinner_assignto);
        tv_ticketdate = view.findViewById(R.id.tv_ticketdate);
        tv_ticketno = view.findViewById(R.id.tv_ticketno);
        tv_assetname = view.findViewById(R.id.tv_assetname);
        tv_assetIsByod = view.findViewById(R.id.tv_assetIsByod);
        tv_ticket_title = view.findViewById(R.id.tv_ticket_title);
        tv_priority = view.findViewById(R.id.tv_priority);
        tv_assignto = view.findViewById(R.id.tv_assignto);
        tv_ticketStatus = view.findViewById(R.id.tv_ticketStatus);
        tv_category = view.findViewById(R.id.tv_category);
        tv_astype = view.findViewById(R.id.tv_astype);
        tv_custodian = view.findViewById(R.id.tv_custodian);
        tv_ticket_desc = view.findViewById(R.id.tv_ticket_desc);
        button_submit = view.findViewById(R.id.button_submit);
        ll_assignto = view.findViewById(R.id.ll_assignto);
        ll_check_assignto = view.findViewById(R.id.ll_check_assignto);
        checkbox_assignto = view.findViewById(R.id.checkbox_assignto);
        ll_issuephoto = view.findViewById(R.id.ll_issuephoto);
        iv_issuephoto = view.findViewById(R.id.iv_issuephoto);
        button_assign = view.findViewById(R.id.button_assign);
        button_esdt = view.findViewById(R.id.button_esdt);
        ll_buttons = view.findViewById(R.id.ll_buttons);
        iv_assetImage = view.findViewById(R.id.iv_assetImage);
        tv_raisedBy = view.findViewById(R.id.tv_raisedBy);
        tv_visitdate = view.findViewById(R.id.tv_visitdate);
        iv_previous = view.findViewById(R.id.iv_previous);
        iv_next = view.findViewById(R.id.iv_next);
        ll_visitDate = view.findViewById(R.id.ll_visitDate);
        ll_classification = view.findViewById(R.id.ll_classification);
        ll_subClassification = view.findViewById(R.id.ll_subClassification);
        tv_classification = view.findViewById(R.id.tv_classification);
        tv_subClassification = view.findViewById(R.id.tv_subClassification);

        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        tv_ticket_desc.setText(ticketDetailsPojo.getIssueDesc());
        tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
        tv_ticketno.setText(ticketDetailsPojo.getTicketNo());
        tv_raisedBy.setText(ticketDetailsPojo.getRaiseBy());
        //tv_visitdate.setText(ticketDetailsPojo.getTicketSiteVisitDate());
        if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("")){
            tv_assetname.setText(ticketDetailsPojo.getAssetCategory());
        }else {
            tv_assetname.setText(ticketDetailsPojo.getTicketAssetAcName());
        }

        if (ticketDetailsPojo.getAssetIsByod().equalsIgnoreCase("1")){
            tv_assetIsByod.setText("(BYOD)");
        }else {
            tv_assetIsByod.setVisibility(View.GONE);
        }
        if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")) {
            if (ticketDetailsPojo.getModuleName() != null) {
                ll_classification.setVisibility(View.VISIBLE);
                tv_classification.setText(ticketDetailsPojo.getModuleName());
            } else {
                ll_classification.setVisibility(View.GONE);
            }

            if (ticketDetailsPojo.getSubModuleName()!=null){
                ll_subClassification.setVisibility(View.VISIBLE);
                tv_subClassification.setText(ticketDetailsPojo.getSubModuleName());
            }else {
                ll_subClassification.setVisibility(View.GONE);
            }
        }else {
            ll_classification.setVisibility(View.GONE);
            ll_subClassification.setVisibility(View.GONE);
        }

        if (ticketDetailsPojo.getTicketIssueOther()!=null && ticketDetailsPojo.getIssueTitle()!=null){
            tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle()+","+ticketDetailsPojo.getTicketIssueOther());
        }else if (ticketDetailsPojo.getIssueTitle()!=null){
            tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
        }else if (ticketDetailsPojo.getTicketIssueOther()!=null){
            tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
        }
        tv_priority.setText(ticketDetailsPojo.getPriority());
        tv_assignto.setText(ticketDetailsPojo.getAssignTo());
        //tv_ticketStatus.setText(ticketDetailsPojo.getTicketStatus());
        tv_category.setText(ticketDetailsPojo.getAssetCategory());
        tv_astype.setText(ticketDetailsPojo.getServiceType());
        tv_custodian.setText(ticketDetailsPojo.getCustodianName());


        if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
            if (ticketDetailsPojo.getTicketSiteVisitDate() != null || !ticketDetailsPojo.getTicketSiteVisitDate().equalsIgnoreCase("")) {
                tv_visitdate.setText(ticketDetailsPojo.getTicketSiteVisitDate());
            } else {
                tv_visitdate.setText("No visit scheduled");
            }
            ll_visitDate.setVisibility(View.VISIBLE);
        }else {
            ll_visitDate.setVisibility(View.GONE);
        }

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Open")){
            tv_ticketStatus.setBackgroundColor(Color.parseColor("#FFCA43"));
        }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Assigned")){
            tv_ticketStatus.setBackgroundColor(Color.parseColor("#3f51b5"));
        }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")){
            tv_ticketStatus.setBackgroundColor(Color.parseColor("#FE5247"));
        }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Work in Progress")){
            tv_ticketStatus.setBackgroundColor(Color.parseColor("#FE5247"));
        }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
            tv_ticketStatus.setBackgroundColor(Color.parseColor("#519F40"));
        }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed")){
            tv_ticketStatus.setBackgroundColor(Color.parseColor("#837D8C"));
        }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            tv_ticketStatus.setBackgroundColor(Color.parseColor("#00BCD4"));
        }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Reopen")){
            tv_ticketStatus.setBackgroundColor(Color.parseColor("#FC39AE"));
        }
        tv_ticketStatus.setText(ticketDetailsPojo.getTicketStatus());

        if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
            iv_assetImage.setColorFilter(Color.parseColor("#49454F"));
            if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Desktop")) {
                iv_assetImage.setBackgroundResource(R.drawable.desktop);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Access Point")) {
                iv_assetImage.setBackgroundResource(R.drawable.access_point);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Amplifier")) {
                iv_assetImage.setBackgroundResource(R.drawable.amplifier);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Barcode Scanner")) {
                iv_assetImage.setBackgroundResource(R.drawable.barcode_scanner);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Biometric Attendance")) {
                iv_assetImage.setBackgroundResource(R.drawable.biometric_attendance);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Camera")) {
                iv_assetImage.setBackgroundResource(R.drawable.camera);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Desk Phone")) {
                iv_assetImage.setBackgroundResource(R.drawable.desk_phone);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Digital Video Recorder")) {
                iv_assetImage.setBackgroundResource(R.drawable.digital_video_recorder);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("EPABX")) {
                iv_assetImage.setBackgroundResource(R.drawable.epabx);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Firewall")) {
                iv_assetImage.setBackgroundResource(R.drawable.firewall);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Hard Disk Drive")) {
                iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Laptop")) {
                iv_assetImage.setBackgroundResource(R.drawable.laptop);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Microphone")) {
                iv_assetImage.setBackgroundResource(R.drawable.microphone);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Mobile")) {
                iv_assetImage.setBackgroundResource(R.drawable.mobile);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Modem")) {
                iv_assetImage.setBackgroundResource(R.drawable.modem);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Monitor")) {
                iv_assetImage.setBackgroundResource(R.drawable.desktop);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Network Video Recorder")) {
                iv_assetImage.setBackgroundResource(R.drawable.network_video_recorder);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Printer")) {
                iv_assetImage.setBackgroundResource(R.drawable.printer);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Rack Enclosure")) {
                iv_assetImage.setBackgroundResource(R.drawable.rack_enclosure);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Router")) {
                iv_assetImage.setBackgroundResource(R.drawable.router);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Scanner")) {
                iv_assetImage.setBackgroundResource(R.drawable.scanner);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Server")) {
                iv_assetImage.setBackgroundResource(R.drawable.server);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("SFP Transceiver")) {
                iv_assetImage.setBackgroundResource(R.drawable.sfp_transreceiver);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Speaker")) {
                iv_assetImage.setBackgroundResource(R.drawable.speaker);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("SSD Drive")) {
                iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Storage Device")) {
                iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Switch")) {
                iv_assetImage.setBackgroundResource(R.drawable.resource_switch);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Tablet")) {
                iv_assetImage.setBackgroundResource(R.drawable.tablet);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Television")) {
                iv_assetImage.setBackgroundResource(R.drawable.television);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Thin Client")) {
                iv_assetImage.setBackgroundResource(R.drawable.thin_client);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("UPS")) {
                iv_assetImage.setBackgroundResource(R.drawable.ups);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Wireless Controller")) {
                iv_assetImage.setBackgroundResource(R.drawable.wireless_controller);
            } else if (ticketDetailsPojo.getTicketAssetAcName().equalsIgnoreCase("Wireless RF Device")) {
                iv_assetImage.setBackgroundResource(R.drawable.wireless_rf_device);
            } else {
                iv_assetImage.setBackgroundResource(R.drawable.desktop);
            }
        }else {
            Glide.with(getActivity())
                    .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/application_softwares/"+ticketDetailsPojo.getTicketServiceTypeIcon()))
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .into(iv_assetImage);
        }
        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            ll_assignto.setVisibility(View.GONE);
            ll_check_assignto.setVisibility(View.GONE);
            ll_buttons.setVisibility(View.GONE);
        }else{
            if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
                ll_buttons.setVisibility(View.GONE);
            }else {
                if (roleResponse.getData().contains("SOFTWARE_ENGINEER") && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")){
                    ll_buttons.setVisibility(View.GONE);
                }else if (roleResponse.getData().contains("HARDWARE_ENGINEER") && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")){
                    ll_buttons.setVisibility(View.GONE);
                }else {
                    ll_buttons.setVisibility(View.VISIBLE);
                }

                if (ticketDetailsPojo.getTicketIsViewed().equalsIgnoreCase("0")) {
                    setViewTicketStatus(ticketDetailsPojo.getTicketId());
                }
            }
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

        if (!ticketDetailsPojo.getIssueImage().isEmpty()){
            ll_issuephoto.setVisibility(View.VISIBLE);
            Log.e("Images", String.valueOf(ticketDetailsPojo.getIssueImage()));
            imageList=ticketDetailsPojo.getIssueImage();
            Log.e("Images List", String.valueOf(imageList.get(0)));
            Glide.with(getActivity())
                    .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + imageList.get(0)))
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .into(iv_issuephoto);
        }else {
            ll_issuephoto.setVisibility(View.GONE);
        }

    }

    private void init() {
        getReply(ticketDetailsPojo.getTicketId());
        checkbox_assignto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox_assignto.isChecked()) {
                    ll_assignto.setVisibility(View.VISIBLE);
                } else {
                    ll_assignto.setVisibility(View.GONE);
                }
            }
        });

        /*button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String assignto = spinner_assignto.getSelectedItem().toString();
                tv_assignto.setText(assignto);
                setAssignTo(assignto_id);
            }
        });*/

        // click here to view previous image
        iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    //decrease the position by 1
                    position--;
                    Glide.with(getActivity())
                            .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + imageList.get(position)))
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(iv_issuephoto);
                    //iv_issuephoto.setImageURI(imageList.get(position));
                }else {
                    Toast.makeText(getActivity(), "First Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // click here to select next image
        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < imageList.size() - 1) {
                    // increase the position by 1
                    position++;
                    Glide.with(getActivity())
                            .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + imageList.get(position)))
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(iv_issuephoto);
                    //iv_issuephoto.setImageURI(imageList.get(position));
                } else {
                    Toast.makeText(getActivity(), "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custompopup_assign);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();

                ImageView iv_close = dialog.findViewById(R.id.iv_close);
                Button button_add = dialog.findViewById(R.id.button_add);
                EditText et_message = dialog.findViewById(R.id.et_message);
                spinner_assignto = dialog.findViewById(R.id.spinner_assignto);
                getAssignToList(ticketDetailsPojo.getTicketId());

                button_add.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         String assignto = spinner_assignto.getText().toString();
                         String message = et_message.getText().toString();
                         tv_assignto.setText(assignto);
                         if(assignto.equalsIgnoreCase("Select") || assignto_id.equalsIgnoreCase("")){
                             Toast.makeText(getActivity(), "Select assign to person", Toast.LENGTH_LONG).show();
                         }else{
                             setAssignTo(assignto_id, message);
                         }
                     }
                });

                spinner_assignto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String assignto = spinner_assignto.getText().toString();
                        if (assignto.equalsIgnoreCase("Self")){
                            assignto_id = "Self";
                        }else {
                            assignto_id = String.valueOf(users.get(i - 1).getUserId());
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
        });

        button_esdt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custompopup_estimated_datetime);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();

                ImageView iv_close = dialog.findViewById(R.id.iv_close);
                Button button_add = dialog.findViewById(R.id.button_add);
                EditText et_message = dialog.findViewById(R.id.et_message);
                TextView expected_resdate = dialog.findViewById(R.id.tv_expected_resdate);
                TextView expected_restime = dialog.findViewById(R.id.tv_expected_restime);
                TextView tv_ticketID = dialog.findViewById(R.id.tv_ticketID);
                TextView tv_ticketdate = dialog.findViewById(R.id.tv_ticketdate);
                TextView tv_priority = dialog.findViewById(R.id.tv_priority);
                TextView tv_ticket_title = dialog.findViewById(R.id.tv_ticket_title);

                tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
                tv_ticketID.setText(ticketDetailsPojo.getTicketNo());
                tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
                tv_priority.setText(ticketDetailsPojo.getPriority());

                if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
                    tv_priority.setTextColor(Color.parseColor("#FE5247"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
                    tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
                    tv_priority.setTextColor(Color.parseColor("#FF8744"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
                    tv_priority.setTextColor(Color.parseColor("#8FFF36"));
                }

                if (ticketDetailsPojo.getTicketEstimatedHandleDate()!=null && !ticketDetailsPojo.getTicketEstimatedHandleDate().equalsIgnoreCase("")) {
                    String[] dateTime = ticketDetailsPojo.getTicketEstimatedHandleDate().split(" ");
                    int monthNumber1 =0;
                    Log.e("Month", dateTime[1]);
                    try {
                        monthNumber1 = getNumberFromMonthName(dateTime[1], Locale.ENGLISH);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    expected_resdate.setText(dateTime[0] + "-" + monthNumber1 + "-" + dateTime[2]);
                    expected_restime.setText(dateTime[3]);
                    et_message.setText(ticketDetailsPojo.getTicketEstimatedHandleDescription());
                }

                expected_resdate.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        Utils.getInstance().showDatePicker(getActivity(),"0","15", new DateCommunicator() {
                            @Override
                            public void getDate(String date) {
                                expected_resdate.setText(date);
                            }
                        });
                    }
                });

                expected_restime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // on below line we are getting the
                        // instance of our calendar.
                        final Calendar c = Calendar.getInstance();
                        // on below line we are getting our hour, minute.
                        int hour = c.get(Calendar.HOUR_OF_DAY);
                        int minute = c.get(Calendar.MINUTE);
                        int ampm = c.get(Calendar.AM_PM);

                        // on below line we are initializing our Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // on below line we are setting selected time
                                        // in our text view.
                                        String AM_PM = "null";
                                        /*if(hourOfDay < 12) {
                                            AM_PM = "AM";
                                        } else {
                                            AM_PM = "PM";
                                        }*/
                                        if (c.get(Calendar.AM_PM) == Calendar.AM)
                                            AM_PM = "AM";
                                        else if (c.get(Calendar.AM_PM) == Calendar.PM)
                                            AM_PM = "PM";
                                        expected_restime.setText(hourOfDay + ":" + minute);

                                    }
                                }, hour, minute,  false);
                        // at last we are calling show to
                        // display our time picker dialog.
                        timePickerDialog.show();
                    }
                });

                button_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = et_message.getText().toString();
                        String resdate = expected_resdate.getText().toString();
                        String restime = expected_restime.getText().toString();
                        if (resdate.equalsIgnoreCase("")){
                            Toast.makeText(getActivity(), "Select estimated resolution date", Toast.LENGTH_LONG).show();
                        }else if (restime.equalsIgnoreCase("")){
                            Toast.makeText(getActivity(), "Select estimated resolution time", Toast.LENGTH_LONG).show();
                        }else {
                            dialog.cancel();
                            submitEstimatedDateTime(message,resdate+" "+restime);
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
        });
    }

    private void getReply(String ticketId) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<TicketViewResponse> call = apiInterface.getTicketViewInfo("application/json", "Bearer " + token, ticketId);
        call.enqueue(new Callback<TicketViewResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<TicketViewResponse> call, @NonNull Response<TicketViewResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    TicketViewResponse ticketViewResponse = response.body();
                    assert ticketViewResponse != null;
                    ticketData = ticketViewResponse.getData().getRaiseData();

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(getActivity(), msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TicketViewResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitEstimatedDateTime(String message, String dateTime) {
        Log.e(TAG, ticketDetailsPojo.getTicketId()+"="+message+"="+dateTime);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateHandleInfo("application/json", "Bearer " + token, "Estimated", ticketDetailsPojo.getTicketId(), "", dateTime, "", message, "cr_title", "cr_desc", "cr_remarks", "", 0, 0, 0,"priority_id", "");
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        getFragmentManager().beginTransaction().detach(new TicketHandlingFragment()).attach(new TicketHandlingFragment()).commit();
                        //Utils.getInstance().showAlert(getActivity(), submitSuccessResponse.getMessage());
                        Toast.makeText(getActivity(), submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(getActivity(), msg);
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
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAssignTo(String assignto_id, String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateAssignInfo("application/json", "Bearer " + token, "Assign", ticketDetailsPojo.getTicketId(), assignto_id, message);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(getActivity(), TicketsListActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(), submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(getActivity(), msg);
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
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAssignToList(String ticketId) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<ServiceProviderListResponse> call = apiInterface.getServiceProviderListInfo("application/json", "Bearer " + token, ticketId);
        call.enqueue(new Callback<ServiceProviderListResponse>() {
            @Override
            public void onResponse(@NotNull Call<ServiceProviderListResponse> call, @NotNull Response<ServiceProviderListResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    ServiceProviderListResponse serviceProviderListResponse = response.body();
                    users = serviceProviderListResponse.getData().getUsers();
                    userlist.clear();
                    userlist.add("Self");
                    for (User obj : users) {
                        userlist.add(obj.getUserName());
                    }

                    ArrayAdapter<String> userAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, userlist);
                    userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_assignto.setAdapter(userAdapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(getActivity(), msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ServiceProviderListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void setViewTicketStatus(String ticketId) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<ResponseBody> call = apiInterface.setTicketViewStatus("application/json", "Bearer "+token,ticketId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    try {
                        assert response.body() != null;
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String status = jsonObject.getString("status");
                        String msg = jsonObject.getString("message");
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Utils.getInstance().showAlert(getActivity(), e.getMessage());
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            //Utils.getInstance().showAlert(getActivity(), msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    public static int getNumberFromMonthName(String monthName, Locale locale) throws ParseException {

        DateTimeFormatter dtFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtFormatter = DateTimeFormatter.ofPattern("MMM")
                    .withLocale(locale);
        }
        TemporalAccessor temporalAccessor = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            temporalAccessor = dtFormatter.parse(monthName);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return temporalAccessor.get(ChronoField.MONTH_OF_YEAR);
        }
        return 0;
    }

    /*public void onCheckboxClicked(View view) {
        if (checkbox_assignto.isChecked()) {
            ll_assignto.setVisibility(View.VISIBLE);
        } else {
            ll_assignto.setVisibility(View.GONE);
        }
    }*/
}