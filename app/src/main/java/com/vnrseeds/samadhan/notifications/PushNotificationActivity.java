package com.vnrseeds.samadhan.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.parser.applicationlistparser.ApplicationResponseParser;
import com.vnrseeds.samadhan.parser.assetcategoryparser.AssetCategoryListResponse;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianData;
import com.vnrseeds.samadhan.parser.departmentparser.Data;
import com.vnrseeds.samadhan.parser.departmentparser.DepartmentResponse;
import com.vnrseeds.samadhan.parser.issuegroupparser.IssueGroupResponse;
import com.vnrseeds.samadhan.parser.locationparser.LocationData;
import com.vnrseeds.samadhan.parser.locationparser.LocationResponse;
import com.vnrseeds.samadhan.parser.notificationuserparser.Datum;
import com.vnrseeds.samadhan.parser.notificationuserparser.NotificationUserResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushNotificationActivity extends AppCompatActivity {

    private static final String TAG = "PushNotificationActivity";
    private static final String SELECT = "Select";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private List<LocationData> location;
    private String selloc_id="";
    private ArrayList<String> deptlist = new ArrayList();
    private ArrayList<String> sectionslist = new ArrayList<>();
    private String seldept_id;
    private List<Data> departments;
    private String custodian_id="";
    private List<CustodianData> custodians;
    private String[] custodianlist = new String[0];
    private EditText et_subject;
    private EditText et_notification_desc;
    private CheckBox cb_notice;
    private LinearLayout ll_serviceType;
    private RadioGroup rg_serviceType;
    private LinearLayout ll_hide_content;
    private TextView tv_spinnerLocation;
    private TextView tv_spinnerDepartment;
    private TextView tv_spinnercustodian;
    private boolean[] selectedcustodian;
    private final ArrayList<Integer> custodianArray=new ArrayList<>();
    private String[] locationlist = new String[0];
    private boolean[] selectedlocations;
    private final ArrayList<Integer> locationArray = new ArrayList<>();
    private String[] departmentlist = new String[0];
    private boolean[] selectedDepartments;
    private final ArrayList<Integer> departmentsArray = new ArrayList<>();
    private List<Datum> usersListData;
    private LinearLayout ll_assetCategory;
    private LinearLayout ll_aplications;
    private TextView tv_spinnerassetCategory;
    private String[] categorylist = new String[0];
    private boolean[] selectedcategory;
    private final ArrayList<Integer> categoryArray=new ArrayList<>();
    private String category_id="";
    private List<com.vnrseeds.samadhan.parser.assetcategoryparser.Data> assetCategory;
    private TextView tv_spinnerApplication;
    private String[] applicationlist=new String[0];
    private boolean[] selectedapplication;
    private final ArrayList<Integer> applicationArray=new ArrayList<>();
    private List<com.vnrseeds.samadhan.parser.applicationlistparser.Datum> applicationListData;
    private String serviceType="Hardware";
    private TextView tv_spinnerIssueGroup;
    private String[] issuegrouplist = new String[0];
    private boolean[] selectedissuegroup;
    private final ArrayList<Integer> issuegroupArray=new ArrayList<>();
    private String issueGroup_id="";
    private List<com.vnrseeds.samadhan.parser.issuegroupparser.Datum> issueGroupListData;
    private TextView tv_affectedFrom;
    private TextView tv_affectedTo;
    private TextView tv_visibleFrom;
    private TextView tv_visibleTo;
    private Button button_submit;
    private int consider_notice=0;
    private TextView tv_affectedFromTime;
    private TextView tv_affectedToTime;
    private TextView tv_visibleFromTime;
    private TextView tv_visibleToTime;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(PushNotificationActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Push Notification");

        back_nav = findViewById(R.id.back_nav);
        et_subject = findViewById(R.id.et_subject);
        et_notification_desc = findViewById(R.id.et_notification_desc);
        cb_notice = findViewById(R.id.cb_notice);
        ll_serviceType = findViewById(R.id.ll_serviceType);
        ll_hide_content = findViewById(R.id.ll_hide_content);
        rg_serviceType = findViewById(R.id.rg_serviceType);
        ll_assetCategory = findViewById(R.id.ll_assetCategory);
        ll_aplications = findViewById(R.id.ll_aplications);
        tv_spinnerLocation = findViewById(R.id.tv_spinnerLocation);
        tv_spinnerDepartment = findViewById(R.id.tv_spinnerDepartment);
        tv_spinnercustodian = findViewById(R.id.tv_spinnercustodian);
        tv_spinnerassetCategory = findViewById(R.id.tv_spinnerassetCategory);
        tv_spinnerApplication = findViewById(R.id.tv_spinnerApplication);
        tv_spinnerIssueGroup = findViewById(R.id.tv_spinnerIssueGroup);

        tv_affectedFrom = findViewById(R.id.tv_affectedFrom);
        tv_affectedTo = findViewById(R.id.tv_affectedTo);
        tv_visibleFrom = findViewById(R.id.tv_visibleFrom);
        tv_visibleTo = findViewById(R.id.tv_visibleTo);
        tv_affectedFromTime = findViewById(R.id.tv_affectedFromTime);
        tv_affectedToTime = findViewById(R.id.tv_affectedToTime);
        tv_visibleFromTime = findViewById(R.id.tv_visibleFromTime);
        tv_visibleToTime = findViewById(R.id.tv_visibleToTime);

        button_submit = findViewById(R.id.button_submit);
    }

    @SuppressLint("LongLogTag")
    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushNotificationActivity.this, NotificationListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getLocation();
        getCategoryList();
        cb_notice.setOnClickListener(view -> {
            if (cb_notice.isChecked()){
                consider_notice=1;
                ll_hide_content.setVisibility(View.VISIBLE);
            }else {
                consider_notice=0;
                ll_hide_content.setVisibility(View.GONE);
            }
        });
        rg_serviceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb.getText().equals("Hardware")){
                    ll_assetCategory.setVisibility(View.VISIBLE);
                    ll_aplications.setVisibility(View.GONE);
                    issuegrouplist = new String[0];
                    issuegroupArray.clear();
                    tv_spinnerIssueGroup.setText("");
                    applicationlist = new String[0];
                    applicationArray.clear();
                    tv_spinnerApplication.setText("");
                    serviceType = "Hardware";
                    getCategoryList();
                }else{
                    ll_assetCategory.setVisibility(View.GONE);
                    ll_aplications.setVisibility(View.VISIBLE);
                    issuegrouplist = new String[0];
                    issuegroupArray.clear();
                    tv_spinnerIssueGroup.setText("");
                    categorylist = new String[0];
                    categoryArray.clear();
                    tv_spinnerassetCategory.setText("");
                    serviceType = "Software";
                    getApplicationList();
                }
            }
        });
        tv_affectedFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(PushNotificationActivity.this,"0",null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_affectedFrom.setText(date);
                    }
                });
            }
        });
        tv_affectedFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();
                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int ampm = c.get(Calendar.AM_PM);
                Log.e("Time", String.valueOf(ampm));
                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(PushNotificationActivity.this,
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
                                tv_affectedFromTime.setText(hourOfDay + ":" + minute);

                            }
                        }, hour, minute,  false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });
        tv_affectedTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(PushNotificationActivity.this,"0",null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_affectedTo.setText(date);
                    }
                });
            }
        });
        tv_affectedToTime.setOnClickListener(new View.OnClickListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(PushNotificationActivity.this,
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
                                tv_affectedToTime.setText(hourOfDay + ":" + minute);

                            }
                        }, hour, minute,  false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });
        tv_visibleFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(PushNotificationActivity.this,"0",null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_visibleFrom.setText(date);
                    }
                });
            }
        });
        tv_visibleFromTime.setOnClickListener(new View.OnClickListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(PushNotificationActivity.this,
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
                                tv_visibleFromTime.setText(hourOfDay + ":" + minute);

                            }
                        }, hour, minute,  false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });
        tv_visibleTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(PushNotificationActivity.this,"0",null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_visibleTo.setText(date);
                    }
                });
            }
        });
        tv_visibleToTime.setOnClickListener(new View.OnClickListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(PushNotificationActivity.this,
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
                                tv_visibleToTime.setText(hourOfDay + ":" + minute);

                            }
                        }, hour, minute,  false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });
        tv_spinnerLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PushNotificationActivity.this);
                builder.setTitle("Select Location");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(locationlist, selectedlocations, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            locationArray.add(i);
                            Collections.sort(locationArray);
                        } else {
                            locationArray.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        tv_spinnerDepartment.setText("");
                        tv_spinnercustodian.setText("");
                        for (int j = 0; j < locationArray.size(); j++) {
                            stringBuilder.append(locationlist[locationArray.get(j)]);
                            if (j != locationArray.size() - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        selloc_id = "";
                        for (int k = 0; k < locationArray.size(); k++) {
                            int ind = locationArray.get(k);
                            if (selloc_id.equalsIgnoreCase("")) {
                                selloc_id = String.valueOf(location.get(ind).getLocationId());
                            } else {
                                selloc_id = selloc_id + "," + location.get(ind).getLocationId();
                            }
                        }
                        getDeptList(selloc_id);
                        tv_spinnerLocation.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selectedlocations.length; j++) {
                            selectedlocations[j] = false;
                            locationArray.clear();
                            tv_spinnerLocation.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        tv_spinnerDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PushNotificationActivity.this);
                builder.setTitle("Select Department");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(departmentlist, selectedDepartments, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            departmentsArray.add(i);
                            Collections.sort(departmentsArray);
                        } else {
                            departmentsArray.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        tv_spinnercustodian.setText("");
                        for (int j = 0; j < departmentsArray.size(); j++) {
                            stringBuilder.append(departmentlist[departmentsArray.get(j)]);
                            if (j != departmentsArray.size() - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        seldept_id="";
                        for (int k = 0; k < departmentsArray.size(); k++) {
                            int ind = departmentsArray.get(k);
                            if (seldept_id.equalsIgnoreCase("")) {
                                seldept_id = String.valueOf(departments.get(ind).getDepartmentId());
                            } else {
                                seldept_id = seldept_id + "," + departments.get(ind).getDepartmentId();
                            }
                        }
                        getCustodianList(selloc_id,seldept_id);
                        tv_spinnerDepartment.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selectedDepartments.length; j++) {
                            selectedDepartments[j] = false;
                            departmentsArray.clear();
                            tv_spinnerDepartment.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        tv_spinnercustodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PushNotificationActivity.this);
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
                        custodian_id = "";
                        for (int k = 0; k < custodianArray.size(); k++) {
                            int ind = custodianArray.get(k);
                            if (custodian_id.equalsIgnoreCase("")) {
                                custodian_id = String.valueOf(usersListData.get(ind).getUserId());
                            } else {
                                custodian_id = custodian_id + "," + usersListData.get(ind).getUserId();
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

        tv_spinnerassetCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PushNotificationActivity.this);
                builder.setTitle("Select Asset Category");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(categorylist, selectedcategory, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            categoryArray.add(i);
                            Collections.sort(categoryArray);
                        } else {
                            categoryArray.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        tv_spinnerIssueGroup.setText("");
                        for (int j = 0; j < categoryArray.size(); j++) {
                            stringBuilder.append(categorylist[categoryArray.get(j)]);
                            if (j != categoryArray.size() - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        category_id = "";
                        for (int k = 0; k < categoryArray.size(); k++) {
                            int ind = categoryArray.get(k);
                            if (category_id.equalsIgnoreCase("")) {
                                category_id = String.valueOf(assetCategory.get(ind).getAcId());
                            } else {
                                category_id = category_id + "," + assetCategory.get(ind).getAcId();
                            }
                        }
                        issuegrouplist = new String[0];
                        issuegroupArray.clear();
                        getIssueGroup(category_id);
                        tv_spinnerassetCategory.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selectedcategory.length; j++) {
                            selectedcategory[j] = false;
                            categoryArray.clear();
                            tv_spinnerassetCategory.setText("");
                            issuegrouplist = new String[0];
                            issuegroupArray.clear();
                            tv_spinnerIssueGroup.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        tv_spinnerApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PushNotificationActivity.this);
                builder.setTitle("Select Applications");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(applicationlist, selectedapplication, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            applicationArray.add(i);
                            Collections.sort(applicationArray);
                        } else {
                            applicationArray.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        tv_spinnerIssueGroup.setText("");
                        for (int j = 0; j < applicationArray.size(); j++) {
                            stringBuilder.append(applicationlist[applicationArray.get(j)]);
                            if (j != applicationArray.size() - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        category_id = "";
                        for (int k = 0; k < applicationArray.size(); k++) {
                            int ind = applicationArray.get(k);
                            if (category_id.equalsIgnoreCase("")) {
                                category_id = String.valueOf(applicationListData.get(ind).getApplicationId());
                            } else {
                                category_id = category_id + "," + applicationListData.get(ind).getApplicationId();
                            }
                        }
                        issuegrouplist = new String[0];
                        issuegroupArray.clear();
                        getIssueGroup(category_id);
                        tv_spinnerApplication.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selectedcategory.length; j++) {
                            selectedcategory[j] = false;
                            applicationArray.clear();
                            tv_spinnerApplication.setText("");
                            issuegrouplist = new String[0];
                            issuegroupArray.clear();
                            tv_spinnerIssueGroup.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        tv_spinnerIssueGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PushNotificationActivity.this);
                builder.setTitle("Select Issue Group");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(issuegrouplist, selectedissuegroup, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            issuegroupArray.add(i);
                            Collections.sort(issuegroupArray);
                        } else {
                            issuegroupArray.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < issuegroupArray.size(); j++) {
                            stringBuilder.append(issuegrouplist[issuegroupArray.get(j)]);
                            if (j != issuegroupArray.size() - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        issueGroup_id = "";
                        for (int k = 0; k < issuegroupArray.size(); k++) {
                            int ind = issuegroupArray.get(k);
                            if (issueGroup_id.equalsIgnoreCase("")) {
                                issueGroup_id = String.valueOf(issueGroupListData.get(ind).getIgId());
                            } else {
                                issueGroup_id = issueGroup_id + "," + issueGroupListData.get(ind).getIgId();
                            }
                        }
                        Log.e("Issue ID:",issueGroup_id);
                        tv_spinnerIssueGroup.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selectedissuegroup.length; j++) {
                            selectedissuegroup[j] = false;
                            issuegroupArray.clear();
                            tv_spinnerIssueGroup.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        button_submit.setOnClickListener(view -> {
            String subject = et_subject.getText().toString().trim();
            String notification_desc = et_notification_desc.getText().toString().trim();
            String affectedFrom = tv_affectedFrom.getText().toString().trim();
            String affectedFromTime = tv_affectedFromTime.getText().toString().trim();
            String affectedTo = tv_affectedTo.getText().toString().trim();
            String affectedToTime = tv_affectedToTime.getText().toString().trim();
            String visibleFrom = tv_visibleFrom.getText().toString().trim();
            String visibleFromTime = tv_visibleFromTime.getText().toString().trim();
            String visibleTo = tv_visibleTo.getText().toString().trim();
            String visibleToTime = tv_visibleToTime.getText().toString().trim();
            String notification_id="";

            if (subject.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Enter subject");
            }else if (notification_desc.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Enter discription");
            }else if (consider_notice==1 && category_id.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select asset category/Applications");
            }else if (consider_notice==1 && affectedFrom.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select affected from date");
            }else if (consider_notice==1 && affectedFromTime.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select affected from time");
            }else if (consider_notice==1 && affectedTo.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select affected to time");
            }else if (consider_notice==1 && affectedToTime.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select affected to date");
            }else if (consider_notice==1 && visibleFrom.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select visible from date");
            }else if (consider_notice==1 && visibleFromTime.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select visible from time");
            }else if (consider_notice==1 && visibleTo.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select visible to date");
            }else if (consider_notice==1 && visibleToTime.isEmpty()){
                Utils.getInstance().showAlert(PushNotificationActivity.this, "Select visible to time");
            }else{
                Log.e(TAG,notification_id+"="+selloc_id+"="+seldept_id+"="+custodian_id+"="+subject+"="+notification_desc+"="+consider_notice+"="+serviceType+"="+category_id+"="+issueGroup_id+"="+affectedFrom+" "+affectedFromTime+"="+affectedTo+" "+affectedToTime+"="+visibleFrom+" "+visibleFromTime+"="+visibleTo+" "+visibleToTime);
                submitNotification(notification_id,selloc_id,seldept_id,custodian_id,subject,notification_desc,consider_notice,serviceType,category_id,issueGroup_id,affectedFrom+" "+affectedFromTime,affectedTo+" "+affectedToTime,visibleFrom+" "+visibleFromTime,visibleTo+" "+visibleToTime);
            }
        });
    }

    private void submitNotification(String notification_id, String selloc_id, String seldept_id, String custodian_id, String subject, String notification_desc, int consider_notice, String serviceType, String category_id, String issueGroup_id, String affectedFrom, String affectedTo, String visibleFrom, String visibleTo) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getNotificationSubmitInfo("application/json", "Bearer "+token, notification_id, selloc_id, seldept_id, custodian_id, subject, notification_desc, consider_notice, serviceType, category_id, issueGroup_id, affectedFrom, affectedTo, visibleFrom, visibleTo);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    assert submitSuccessResponse != null;
                    if (submitSuccessResponse.getStatus()) {
                        Intent intent = new Intent(PushNotificationActivity.this, MainActivity.class);
                        Toast.makeText(PushNotificationActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(PushNotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(PushNotificationActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SubmitSuccessResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void getIssueGroup(String serviceType_id) {
        Log.e(TAG, token+"="+serviceType+"="+serviceType_id);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<IssueGroupResponse> call = apiInterface.getIssueGroupInfo("application/json", "Bearer "+token, serviceType,serviceType_id);
        call.enqueue(new Callback<IssueGroupResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<IssueGroupResponse> call, @NotNull Response<IssueGroupResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    IssueGroupResponse issueGroupResponse = response.body();
                    assert issueGroupResponse != null;
                    issueGroupListData = issueGroupResponse.getData();
                    ArrayList<String> issuegrouplist1 = new ArrayList<>();
                    for (com.vnrseeds.samadhan.parser.issuegroupparser.Datum obj:issueGroupListData){
                        issuegrouplist1.add(obj.getIgName());
                    }
                    Log.e(TAG, String.valueOf(issuegrouplist1));
                    issuegrouplist = issuegrouplist1.toArray(new String[0]);
                    selectedissuegroup = new boolean[issuegrouplist.length];
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(PushNotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(PushNotificationActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<IssueGroupResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void getApplicationList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<ApplicationResponseParser> call = apiInterface.getAssetApplicationInfo("application/json", "Bearer "+token);
        call.enqueue(new Callback<ApplicationResponseParser>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<ApplicationResponseParser> call, @NotNull Response<ApplicationResponseParser> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    ApplicationResponseParser applicationResponseParser = response.body();
                    assert applicationResponseParser != null;
                    applicationListData = applicationResponseParser.getData();
                    ArrayList<String> applicationlist1 = new ArrayList<>();
                    for (com.vnrseeds.samadhan.parser.applicationlistparser.Datum obj:applicationListData){
                        applicationlist1.add(obj.getApplicationName());
                    }
                    //Log.e(TAG, String.valueOf(categorylist));
                    applicationlist = applicationlist1.toArray(new String[0]);
                    selectedapplication = new boolean[applicationlist.length];
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(PushNotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(PushNotificationActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<ApplicationResponseParser> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void getCategoryList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<AssetCategoryListResponse> call = apiInterface.getAssetCategoryInfo("application/json", "Bearer "+token, "1", "", "");
        call.enqueue(new Callback<AssetCategoryListResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<AssetCategoryListResponse> call, @NotNull Response<AssetCategoryListResponse> response) {
                if(response.isSuccessful()){
                    customProgressDialogue.cancel();
                    AssetCategoryListResponse assetCategoryListResponse = response.body();
                    assert assetCategoryListResponse != null;
                    assetCategory = assetCategoryListResponse.getData();
                    ArrayList<String> categorylist1 = new ArrayList<>();
                    for (com.vnrseeds.samadhan.parser.assetcategoryparser.Data obj:assetCategory){
                        categorylist1.add(obj.getAcName());
                    }
                    //Log.e(TAG, String.valueOf(categorylist));
                    categorylist = categorylist1.toArray(new String[0]);
                    selectedcategory = new boolean[categorylist.length];
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(PushNotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(PushNotificationActivity.this, msg);
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

    private void getCustodianList(String selloc_id,String seldept_ids) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<NotificationUserResponse> call = apiInterface.getUsersInfo("application/json", "Bearer " + token, selloc_id, seldept_ids,"");
        call.enqueue(new Callback<NotificationUserResponse>() {
            @Override
            public void onResponse(@NotNull Call<NotificationUserResponse> call, @NotNull Response<NotificationUserResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    NotificationUserResponse notificationUserResponse = response.body();
                    assert notificationUserResponse != null;
                    usersListData = notificationUserResponse.getData();
                    ArrayList<String> custodianlist1 = new ArrayList<>();
                    for (Datum obj : usersListData) {
                        custodianlist1.add(obj.getUserName());
                    }

                    custodianlist = custodianlist1.toArray(new String[0]);
                    selectedcustodian = new boolean[custodianlist.length];

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(PushNotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(PushNotificationActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<NotificationUserResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @SuppressLint("LongLogTag")
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
                    ArrayList<String> departmentlist1 = new ArrayList<>();
                    for (Data obj : departments) {
                        departmentlist1.add(obj.getDepartmentName());
                    }

                    departmentlist = departmentlist1.toArray(new String[0]);
                    selectedDepartments = new boolean[departmentlist.length];

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(PushNotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(PushNotificationActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<DepartmentResponse> call, @NotNull Throwable t) {
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
                    assert locationResponse != null;
                    location = locationResponse.getData();
                    ArrayList<String> locationlist1 = new ArrayList<>();
                    for (LocationData obj : location) {
                        locationlist1.add(obj.getLocationName());
                    }

                    locationlist = locationlist1.toArray(new String[0]);
                    selectedlocations = new boolean[locationlist.length];

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(PushNotificationActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(PushNotificationActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PushNotificationActivity.this, NotificationListActivity.class);
        startActivity(intent);
        finish();
    }
}