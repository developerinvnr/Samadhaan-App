package com.vnrseeds.samadhan.ticketsystem;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.TicketViewAdapter;
import com.vnrseeds.samadhan.addassetforms.Utility;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.parser.priorityparser.Datum;
import com.vnrseeds.samadhan.parser.priorityparser.PriorityResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.parser.ticketviewparser.HandleReplyLog;
import com.vnrseeds.samadhan.parser.ticketviewparser.TicketViewResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketHandlingFragment extends Fragment {

    private static final String TAG = "TicketHandlingFragment";
    private RecyclerView lv_commentlist;
    private Button button_addnotes;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    private TextView expected_resdate;
    private TextView expected_restime;
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private TicketDetailsPojo ticketDetailsPojo;
    private TicketViewAdapter ticketViewAdapter;
    private List<HandleReplyLog> ticketViewArray = new ArrayList<>();
    private CheckBox cb_wip;
    private String is_wip="0";
    private Button button_diagnosys;
    private TextView tv_more;
    private CheckBox cb_hold;
    private String ticket_is_change_request="Support Request";
    private String ticket_is_change_request_status="Work in Progress";
    private final int ticket_is_hold=0;
    private int cr_is_discussion_required=0;
    private final int cr_is_finish_in_next_version=0;
    private List<Datum> priority;
    private final ArrayList<String> prioritylist=new ArrayList<>();
    private AutoCompleteTextView spinner_priority;
    private String priority_id="";

    private ArrayList<Uri> mArrayUri=new ArrayList<>();
    private int position=0;
    private ImageView iv_previous;
    private ImageView iv_next;
    private static final int PERMISSION_CODE = 1234;
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    private String path_billcopy;
    private final List<MultipartBody.Part> list = new ArrayList<>();
    private File BILL_COPY;
    private Uri image_uri;
    private String userChoosenTask;
    private LinearLayout ll_issuephoto;
    private ImageView iv_issuephoto;

    public TicketHandlingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_handling, container, false);
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
        lv_commentlist = view.findViewById(R.id.lv_commentlist);
        button_addnotes = view.findViewById(R.id.button_addnotes);
        button_diagnosys = view.findViewById(R.id.button_diagnosys);
        TextView tv_ticketdate = view.findViewById(R.id.tv_ticketdate);
        TextView tv_ticketno = view.findViewById(R.id.tv_ticketno);
        TextView tv_ticket_title = view.findViewById(R.id.tv_ticket_title);
        TextView tv_priority = view.findViewById(R.id.tv_priority);
        tv_more = view.findViewById(R.id.tv_more);

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
            if (ticketDetailsPojo.getTicketIssueOther()!=null) {
                if (ticketDetailsPojo.getTicketIssueOther().length() > 30) {
                    tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther().substring(0, 30) + "...");
                } else {
                    tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
                }
            }
            //tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
        }
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

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            button_addnotes.setVisibility(View.GONE);
        }else {
            button_addnotes.setVisibility(View.VISIBLE);
        }

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
            button_diagnosys.setVisibility(View.GONE);
        }else {
            button_diagnosys.setVisibility(View.VISIBLE);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        lv_commentlist.setLayoutManager(mLayoutManager);
        lv_commentlist.setItemAnimator(new DefaultItemAnimator());
    }

    private void init() {
        getReply(ticketDetailsPojo.getTicketId());
        button_diagnosys.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custompopup_tickethandle);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();

                expected_resdate = dialog.findViewById(R.id.tv_expected_resdate);
                expected_restime = dialog.findViewById(R.id.tv_expected_restime);
                Button button_add = dialog.findViewById(R.id.button_add);
                EditText et_message = dialog.findViewById(R.id.et_message);
                EditText et_estimate_note = dialog.findViewById(R.id.et_estimate_note);
                EditText et_cr_remarks = dialog.findViewById(R.id.et_cr_remarks);
                EditText et_cr_title = dialog.findViewById(R.id.et_cr_title);
                EditText et_cr_desc = dialog.findViewById(R.id.et_cr_desc);
                ImageView iv_close = dialog.findViewById(R.id.iv_close);
                LinearLayout ll_edtr = dialog.findViewById(R.id.ll_edtr);
                //LinearLayout ll_hold = dialog.findViewById(R.id.ll_hold);
                LinearLayout ll_changeRequest = dialog.findViewById(R.id.ll_changeRequest);
                cb_wip = dialog.findViewById(R.id.cb_wip);
                //cb_hold = dialog.findViewById(R.id.cb_hold);
                CheckBox cb_dis_req = dialog.findViewById(R.id.cb_dis_req);
                RadioButton rb_wip = dialog.findViewById(R.id.rb_wip);
                RadioButton rb_hold = dialog.findViewById(R.id.rb_hold);
                RadioButton rb_nextVersion = dialog.findViewById(R.id.rb_nextVersion);
                //CheckBox cb_nextVersion = dialog.findViewById(R.id.cb_nextVersion);
                RadioGroup rg_ticketRequestType = dialog.findViewById(R.id.rg_ticketRequestType);
                RadioGroup rg_ticketRequestStatus = dialog.findViewById(R.id.rg_ticketRequestStatus);
                LinearLayout ll_ticketRequestType = dialog.findViewById(R.id.ll_ticketRequestType);
                LinearLayout ll_cr_status = dialog.findViewById(R.id.ll_cr_status);
                LinearLayout ll_discussion_req = dialog.findViewById(R.id.ll_discussion_req);
                spinner_priority = dialog.findViewById(R.id.spinner_priority);
                if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")){
                    ll_ticketRequestType.setVisibility(View.GONE);
                    ll_cr_status.setVisibility(View.GONE);
                    ll_discussion_req.setVisibility(View.GONE);
                }else{
                    ll_ticketRequestType.setVisibility(View.VISIBLE);
                    //ll_cr_status.setVisibility(View.VISIBLE);
                    ll_discussion_req.setVisibility(View.VISIBLE);
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
                    et_estimate_note.setText(ticketDetailsPojo.getTicketEstimatedHandleDescription());
                }

                getPriority();
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
                if(ticketDetailsPojo.getTicket_is_work_in_progress()!=null && ticketDetailsPojo.getTicket_is_work_in_progress().equalsIgnoreCase("1")){
                    cb_wip.setChecked(true);
                }
                cb_wip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cb_wip.isChecked()) {
                            is_wip="1";
                        } else {
                            is_wip="0";
                        }
                    }
                });

                rg_ticketRequestType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                        RadioButton rb = dialog.findViewById(checkedId);
                        if (rb.getText().equals("Support Request")) {
                            ticket_is_change_request="Support Request";
                            cb_wip.setVisibility(View.VISIBLE);
                            ll_edtr.setVisibility(View.VISIBLE);
                            ll_cr_status.setVisibility(View.GONE);
                            ll_changeRequest.setVisibility(View.GONE);
                            ll_discussion_req.setVisibility(View.VISIBLE);
                        }else if (rb.getText().equals("Change Request")) {
                            ticket_is_change_request="Change Request";
                            //ll_hold.setVisibility(View.VISIBLE);
                            cb_wip.setVisibility(View.GONE);
                            ll_edtr.setVisibility(View.VISIBLE);
                            ll_cr_status.setVisibility(View.VISIBLE);
                            ll_changeRequest.setVisibility(View.GONE);
                            ll_discussion_req.setVisibility(View.VISIBLE);
                            if (rb_wip.isChecked()) {
                                ticket_is_change_request_status="Work in Progress";
                                ll_edtr.setVisibility(View.VISIBLE);
                                ll_changeRequest.setVisibility(View.GONE);
                                ll_discussion_req.setVisibility(View.VISIBLE);
                            }else if (rb_hold.isChecked()) {
                                ticket_is_change_request_status="Hold";
                                ll_edtr.setVisibility(View.GONE);
                                ll_changeRequest.setVisibility(View.GONE);
                            }else if (rb_nextVersion.isChecked()) {
                                ticket_is_change_request_status="Next Version";
                                ll_edtr.setVisibility(View.GONE);
                                ll_changeRequest.setVisibility(View.VISIBLE);
                            }
                        }else if (rb.getText().equals("Invalid Request")) {
                            ticket_is_change_request="Invalid Request";
                            cb_wip.setVisibility(View.GONE);
                            ll_edtr.setVisibility(View.GONE);
                            ll_cr_status.setVisibility(View.GONE);
                            ll_changeRequest.setVisibility(View.GONE);
                            ll_discussion_req.setVisibility(View.GONE);
                        }
                    }
                });

                rg_ticketRequestStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                        RadioButton rb = dialog.findViewById(checkedId);
                        if (rb.getText().equals("Work in Progress")) {
                            ticket_is_change_request_status="Work in Progress";
                            ll_edtr.setVisibility(View.VISIBLE);
                            ll_changeRequest.setVisibility(View.GONE);
                            ll_discussion_req.setVisibility(View.VISIBLE);
                        }else if (rb.getText().equals("Hold for now")) {
                            ticket_is_change_request_status="Hold";
                            ll_edtr.setVisibility(View.GONE);
                            ll_changeRequest.setVisibility(View.GONE);
                        }else if (rb.getText().equals("Schedule in next version")) {
                            ticket_is_change_request_status="Next Version";
                            ll_edtr.setVisibility(View.GONE);
                            ll_changeRequest.setVisibility(View.VISIBLE);
                        }
                    }
                });

                /*cb_hold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cb_hold.isChecked()){
                            ticket_is_hold=1;
                            ll_edtr.setVisibility(View.GONE);
                            ll_changeRequest.setVisibility(View.VISIBLE);
                        }else {
                            ticket_is_hold=0;
                            ll_edtr.setVisibility(View.VISIBLE);
                            ll_changeRequest.setVisibility(View.GONE);
                        }
                    }
                });*/

                cb_dis_req.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cb_dis_req.isChecked()){
                            cr_is_discussion_required=1;
                        }else {
                            cr_is_discussion_required=0;
                        }
                    }
                });

                spinner_priority.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i>=0){
                            priority_id=String.valueOf(priority.get(i).getPriorityId());
                        }
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
                        String message = et_message.getText().toString().trim();
                        String resdate = expected_resdate.getText().toString().trim();
                        String restime = expected_restime.getText().toString().trim();
                        String estimate_note = et_estimate_note.getText().toString().trim();
                        String cr_title = et_cr_title.getText().toString().trim();
                        String cr_desc = et_cr_desc.getText().toString().trim();
                        String cr_remarks = et_cr_remarks.getText().toString().trim();
                        if (message.equalsIgnoreCase("")) {
                            Toast.makeText(getActivity(), "Enter Diagnosis Note", Toast.LENGTH_LONG).show();
                        } else if (ticket_is_change_request.equalsIgnoreCase("Support Request") && resdate.equalsIgnoreCase("")) {
                            Toast.makeText(getActivity(), "Select estimated resolution date", Toast.LENGTH_LONG).show();
                        } else if (ticket_is_change_request.equalsIgnoreCase("Support Request") && restime.equalsIgnoreCase("")) {
                            Toast.makeText(getActivity(), "Select estimated resolution time", Toast.LENGTH_LONG).show();
                        } else if (ticket_is_change_request_status.equalsIgnoreCase("Work in Progress") && resdate.equalsIgnoreCase("")) {
                            Toast.makeText(getActivity(), "Select estimated resolution date", Toast.LENGTH_LONG).show();
                        } else if (ticket_is_change_request_status.equalsIgnoreCase("Work in Progress") && restime.equalsIgnoreCase("")) {
                            Toast.makeText(getActivity(), "Select estimated resolution time", Toast.LENGTH_LONG).show();
                        } else if (ticket_is_change_request_status.equalsIgnoreCase("Next Version") && cr_title.isEmpty()) {
                            Toast.makeText(getActivity(), "Enter CR title", Toast.LENGTH_LONG).show();
                        } else if (ticket_is_change_request_status.equalsIgnoreCase("Next Version") && cr_desc.isEmpty()) {
                            Toast.makeText(getActivity(), "Enter CR description", Toast.LENGTH_LONG).show();
                        } else if (ticket_is_change_request_status.equalsIgnoreCase("Next Version") && cr_remarks.isEmpty()) {
                            Toast.makeText(getActivity(), "Enter CR remark", Toast.LENGTH_LONG).show();
                        } else {
                            dialog.cancel();
                            submitDiagnosys(message, resdate + " " + restime, estimate_note, cr_title, cr_desc, cr_remarks, ticket_is_change_request, ticket_is_hold, cr_is_discussion_required, cr_is_finish_in_next_version,priority_id,ticket_is_change_request_status);
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

        button_addnotes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custompopup_addnotes);
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
                CheckBox cb_show_to_user = dialog.findViewById(R.id.cb_show_to_user);
                ll_issuephoto = dialog.findViewById(R.id.ll_issuephoto);
                iv_issuephoto = dialog.findViewById(R.id.iv_issuephoto);
                iv_previous = dialog.findViewById(R.id.iv_previous);
                iv_next = dialog.findViewById(R.id.iv_next);
                TextView tv_issuephoto = dialog.findViewById(R.id.tv_issuephoto);
                cb_show_to_user.setVisibility(View.GONE);
                tv_issuephoto.setVisibility(View.VISIBLE);
                tv_lastlot.setText("Add Note");

                tv_issuephoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectImage();
                    }
                });

                button_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = et_message.getText().toString().trim();
                        if (message.equalsIgnoreCase("")){
                            Toast.makeText(getActivity(), "Enter message", Toast.LENGTH_LONG).show();
                        }else {
                            dialog.cancel();
                            submitNote(message);
                        }
                    }
                });

                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
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
                            Toast.makeText(getActivity(), "First Image Already Shown", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tv_more.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custompopup_ticket_details);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();

                ImageView iv_close = dialog.findViewById(R.id.iv_close);
                TextView tv_ticketID = dialog.findViewById(R.id.tv_ticketID);
                TextView tv_ticketdate = dialog.findViewById(R.id.tv_ticketdate);
                TextView tv_priority = dialog.findViewById(R.id.tv_priority);
                TextView tv_ticket_title = dialog.findViewById(R.id.tv_ticket_title);
                TextView tv_ticket_desc = dialog.findViewById(R.id.tv_ticket_desc);
                TextView tv_custodian = dialog.findViewById(R.id.tv_custodian);
                TextView tv_ticketStatus = dialog.findViewById(R.id.tv_ticketStatus);
                TextView tv_category = dialog.findViewById(R.id.tv_category);
                TextView tv_assignto = dialog.findViewById(R.id.tv_assignto);
                TextView tv_astype = dialog.findViewById(R.id.tv_astype);
                tv_ticketID.setText("Ticket "+ticketDetailsPojo.getTicketNo());
                tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
                tv_priority.setText(ticketDetailsPojo.getPriority());
                if (ticketDetailsPojo.getIssueTitle()!=null){
                    tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
                }else {
                    tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
                }
                tv_ticket_desc.setText(ticketDetailsPojo.getIssueDesc());
                tv_custodian.setText(ticketDetailsPojo.getCustodianName());
                tv_ticketStatus.setText(ticketDetailsPojo.getTicketStatus());
                tv_category.setText(ticketDetailsPojo.getAssetCategory());
                tv_astype.setText(ticketDetailsPojo.getServiceType());
                tv_assignto.setText(ticketDetailsPojo.getAssignTo());

                if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
                    tv_priority.setTextColor(Color.parseColor("#FE5247"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
                    tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
                    tv_priority.setTextColor(Color.parseColor("#FF8744"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
                    tv_priority.setTextColor(Color.parseColor("#8FFF36"));
                }

                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
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

    private void submitDiagnosysCR(String message, String cr_title, String cr_desc, String cr_remarks, int ticket_is_change_request, int ticket_is_hold, int cr_is_discussion_required, int cr_is_finish_in_next_version, String priority_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateHoldInfo("application/json", "Bearer " + token, "Handle", ticketDetailsPojo.getTicketId(), message, cr_title, cr_desc, cr_remarks, ticket_is_change_request, ticket_is_hold, cr_is_discussion_required, cr_is_finish_in_next_version,priority_id);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        getFragmentManager().beginTransaction().detach(new TicketHandlingFragment()).attach(new TicketHandlingFragment()).commit();
                        Toast.makeText(getActivity(), submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
                        getReply(ticketDetailsPojo.getTicketId());
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

    private void getPriority() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<PriorityResponse> call = apiInterface.getPriority("application/json", "Bearer " + token);
        call.enqueue(new Callback<PriorityResponse>() {
            @Override
            public void onResponse(@NotNull Call<PriorityResponse> call, @NotNull Response<PriorityResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    PriorityResponse priorityResponse = response.body();
                    priority = priorityResponse.getData();
                    prioritylist.clear();
                    for (Datum obj : priority) {
                        prioritylist.add(obj.getPriorityName());
                    }

                    ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, prioritylist);
                    priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_priority.setAdapter(priorityAdapter);
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
            public void onFailure(@NotNull Call<PriorityResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getReply(String ticketId) {
        Log.e("Ticket ID : ", ticketId);
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
                    ticketViewArray = ticketViewResponse.getData().getHandleReplyLogs();
                    ticketViewAdapter = new TicketViewAdapter(getContext(), ticketViewArray);
                    lv_commentlist.setAdapter(ticketViewAdapter);
                    ticketViewAdapter.notifyDataSetChanged();
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

    private void submitDiagnosys(String message, String dateTime, String estimate_note, String cr_title, String cr_desc, String cr_remarks, String ticket_is_change_request, int ticket_is_hold, int cr_is_discussion_required, int cr_is_finish_in_next_version, String priority_id, String ticket_is_change_request_status) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateHandleInfo("application/json", "Bearer " + token, "Handle", ticketDetailsPojo.getTicketId(), message, dateTime, is_wip, estimate_note, cr_title, cr_desc, cr_remarks, ticket_is_change_request, ticket_is_hold, cr_is_discussion_required, cr_is_finish_in_next_version,priority_id,ticket_is_change_request_status);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        if (ticket_is_change_request.equalsIgnoreCase("Invalid Request") || ticket_is_change_request_status.equalsIgnoreCase("Next Version")) {
                            Intent intent = new Intent(getActivity(), TicketsListActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else {
                            assert getFragmentManager() != null;
                            getFragmentManager().beginTransaction().detach(new TicketHandlingFragment()).attach(new TicketHandlingFragment()).commit();
                            //Utils.getInstance().showAlert(getActivity(), submitSuccessResponse.getMessage());
                            //ticketDetailsPojo.setTicketStatus("Diagnosis");
                            Toast.makeText(getActivity(), submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
                            getReply(ticketDetailsPojo.getTicketId());
                        }
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

    private void submitNote(String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        RequestBody action1 = RequestBody.create(MediaType.parse("text/plain"), "Reply");
        RequestBody ticketID = RequestBody.create(MediaType.parse("text/plain"), ticketDetailsPojo.getTicketId());
        RequestBody message1 = RequestBody.create(MediaType.parse("text/plain"), message);
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateUserNoteInfo("application/json", "Bearer " + token, action1, ticketID, message1, list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        //getFragmentManager().beginTransaction().detach(new TicketHandlingFragment()).attach(new TicketHandlingFragment()).commit();
                        //Utils.getInstance().showAlert(getActivity(), submitSuccessResponse.getMessage());
                        Toast.makeText(getActivity(), submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
                        getReply(ticketDetailsPojo.getTicketId());
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

    private void selectImage() {
        final CharSequence[] items = {"Click here to open camera", "Choose from gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

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
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
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
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

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
        //iv_issuephoto.setImageURI(image_uri);
        path_billcopy = getPath(image_uri);
        //Log.e(TAG, String.valueOf(image_uri));
        mArrayUri.add(image_uri);
        list.add(prepareFilePart("ticket_reply_files[]", Uri.parse(path_billcopy)));
        iv_issuephoto.setImageURI(mArrayUri.get(0));
        position = 0;
    }

    public String getPath(Uri image_uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(image_uri, projection, null, null, null);
        getActivity().startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void onSelectFromGalleryResult(Intent data) {
        /*Bitmap bm = null;
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
        assert bm != null;
        path_billcopy = getPath(image_uri);
        Log.e(TAG, String.valueOf(image_uri));
        list.add(prepareFilePart("ticket_raise_files[]", image_uri));*/


        //Get the Image from data
        if (data.getClipData() != null) {
            ClipData mClipData = data.getClipData();
            Log.e("Data:", String.valueOf(mClipData));
            int cout = data.getClipData().getItemCount();
            for (int i = 0; i < cout; i++) {
                //adding imageuri in array
                Uri imageurl = data.getClipData().getItemAt(i).getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageurl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert bitmap != null;
                String absolutePath = saveImage(bitmap);
                mArrayUri.add(imageurl);
                list.add(prepareFilePart("ticket_reply_files[]", Uri.parse(absolutePath)));
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
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageurl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bitmap != null;
            String absolutePath = saveImage(bitmap);
            list.add(prepareFilePart("ticket_reply_files[]", Uri.parse(absolutePath)));
            ll_issuephoto.setVisibility(View.VISIBLE);
            iv_issuephoto.setImageURI(mArrayUri.get(0));
            position = 0;
        }
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
            MediaScannerConnection.scanFile(getActivity(), new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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

}