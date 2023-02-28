package com.vnrseeds.samadhan.ticketsystem;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.addassetforms.Utility;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.AppConfig;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketResolveFragment extends Fragment {

    private static final String TAG = "TicketResolveFragment";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private EditText et_message;
    private Button button_finalsubmit;
    private TicketDetailsPojo ticketDetailsPojo;
    private TextView tv_more;
    private RadioGroup rg_addToAsset;
    private RadioGroup rg_physicalVisit;
    private TextView tv_visit_date;
    private String addToAsset = "0";
    private String physicalVisit = "0";
    private LinearLayout ll_visitDate;
    private LinearLayout ll_issuephoto;
    private ImageView iv_issuephoto;
    private TextView tv_issuephoto;

    private static final int PERMISSION_CODE = 1234;
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    private String path_billcopy;
    private final List<MultipartBody.Part> list = new ArrayList<>();
    private File BILL_COPY;
    private Uri image_uri;
    private String userChoosenTask;
    private LinearLayout ll_addToAsset;
    private LinearLayout ll_physicalVisit;
    private File Fname1;
    private Uri imag1;
    private int IMAGE_REQUEST1 = 1;
    private RadioButton rb_yes;
    private RadioButton rb_no;
    private RadioButton rb_visitYes;
    private RadioButton rb_visitNo;

    public TicketResolveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_resolve, container, false);
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
        et_message = view.findViewById(R.id.et_message);
        button_finalsubmit = view.findViewById(R.id.button_finalsubmit);
        TextView tv_ticketdate = view.findViewById(R.id.tv_ticketdate);
        TextView tv_ticketno = view.findViewById(R.id.tv_ticketno);
        TextView tv_ticket_title = view.findViewById(R.id.tv_ticket_title);
        TextView tv_priority = view.findViewById(R.id.tv_priority);
        TextView et_resolved_date = view.findViewById(R.id.et_resolved_date);
        LinearLayout ll_resolved_date = view.findViewById(R.id.ll_resolved_date);
        tv_more = view.findViewById(R.id.tv_more);
        rg_addToAsset = view.findViewById(R.id.rg_addToAsset);
        rb_yes = view.findViewById(R.id.rb_yes);
        rb_no = view.findViewById(R.id.rb_no);
        rg_physicalVisit = view.findViewById(R.id.rg_physicalVisit);
        rb_visitYes = view.findViewById(R.id.rb_visitYes);
        rb_visitNo = view.findViewById(R.id.rb_visitNo);
        tv_visit_date = view.findViewById(R.id.tv_visit_date);
        ll_visitDate = view.findViewById(R.id.ll_visitDate);
        ll_issuephoto = view.findViewById(R.id.ll_issuephoto);
        iv_issuephoto = view.findViewById(R.id.iv_issuephoto);
        tv_issuephoto = view.findViewById(R.id.tv_issuephoto);
        ll_addToAsset = view.findViewById(R.id.ll_addToAsset);
        ll_physicalVisit = view.findViewById(R.id.ll_physicalVisit);

        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
        if (ticketDetailsPojo.getIssueTitle() != null) {
            if (ticketDetailsPojo.getIssueTitle().length()>30) {
                tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle().substring(0, 30) + "...");
            }else {
                tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
            }
        } else {
            if (ticketDetailsPojo.getTicketIssueOther()!=null) {
                if (ticketDetailsPojo.getTicketIssueOther().length() > 30) {
                    tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther().substring(0, 30) + "...");
                } else {
                    tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
                }
            }
            //tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
        }

        tv_ticketno.setText(ticketDetailsPojo.getTicketNo());
        tv_priority.setText(ticketDetailsPojo.getPriority());

        if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")) {
            tv_priority.setTextColor(Color.parseColor("#FE5247"));
        } else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")) {
            tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
        } else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")) {
            tv_priority.setTextColor(Color.parseColor("#FF8744"));
        } else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")) {
            tv_priority.setTextColor(Color.parseColor("#8FFF36"));
        }

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
            button_finalsubmit.setVisibility(View.GONE);
            tv_issuephoto.setVisibility(View.GONE);
            ll_resolved_date.setVisibility(View.VISIBLE);
            et_resolved_date.setText(ticketDetailsPojo.getTicketResolveDate());
            et_message.setText(ticketDetailsPojo.getTicketResolveDescription());
            et_message.setEnabled(false);
            if (ticketDetailsPojo.getTicketIsAddToAsset().equalsIgnoreCase("1")){
                rb_yes.setChecked(true);
            }else {
                rb_no.setChecked(true);
            }
            rb_yes.setEnabled(false);
            rb_no.setEnabled(false);

            if (ticketDetailsPojo.getTicketIsSiteVisit().equalsIgnoreCase("1")){
                rb_visitYes.setChecked(true);
                ll_visitDate.setVisibility(View.VISIBLE);
                if (ticketDetailsPojo.getTicketEstimatedHandleDate()!=null && !ticketDetailsPojo.getTicketEstimatedHandleDate().equalsIgnoreCase("")) {
                    String[] dateTime = ticketDetailsPojo.getTicketEstimatedHandleDate().split(" ");
                    int monthNumber1 =0;
                    Log.e("Month", dateTime[1]);
                    try {
                        monthNumber1 = getNumberFromMonthName(dateTime[1], Locale.ENGLISH);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tv_visit_date.setText(dateTime[0] + "-" + monthNumber1 + "-" + dateTime[2]);

                    tv_visit_date.setEnabled(false);
                }
            }else {
                rb_visitNo.setChecked(true);
                ll_visitDate.setVisibility(View.GONE);
            }
            rb_visitYes.setEnabled(false);
            rb_visitNo.setEnabled(false);

            if (ticketDetailsPojo.getTicketResolveFile()!=null) {
                ll_issuephoto.setVisibility(View.VISIBLE);
                Glide.with(getActivity())
                        .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + ticketDetailsPojo.getTicketResolveFile()))
                        .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                        .into(iv_issuephoto);
            }
        } else {
            button_finalsubmit.setVisibility(View.VISIBLE);
            tv_issuephoto.setVisibility(View.VISIBLE);
            ll_resolved_date.setVisibility(View.GONE);
            et_message.setEnabled(true);
        }
        if (ticketDetailsPojo.getTicketSiteVisitDate() != null && !ticketDetailsPojo.getTicketSiteVisitDate().equalsIgnoreCase("")) {
            //tv_visit_date.setText(ticketDetailsPojo.getTicketSiteVisitDate());
            String[] dateTime = ticketDetailsPojo.getTicketSiteVisitDate().split(" ");
            int monthNumber1 = 0;
            Log.e("Month", dateTime[1]);
            try {
                monthNumber1 = getNumberFromMonthName(dateTime[1], Locale.ENGLISH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tv_visit_date.setText(dateTime[0] + "-" + monthNumber1 + "-" + dateTime[2]);
        }

        if (ticketDetailsPojo.getAssetIsByod().equalsIgnoreCase("1")) {
            ll_addToAsset.setVisibility(View.GONE);
            ll_physicalVisit.setVisibility(View.GONE);
        }else {
            if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")) {
                ll_addToAsset.setVisibility(View.GONE);
                ll_physicalVisit.setVisibility(View.GONE);
            } else {
                ll_addToAsset.setVisibility(View.VISIBLE);
                ll_physicalVisit.setVisibility(View.VISIBLE);
            }
        }
        rg_addToAsset.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = view.findViewById(i);
                if (rb.getText().toString().equalsIgnoreCase("Yes")) {
                    addToAsset = "1";
                } else {
                    addToAsset = "0";
                }
            }
        });

        rg_physicalVisit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = view.findViewById(i);
                if (rb.getText().toString().equalsIgnoreCase("Yes")) {
                    physicalVisit = "1";
                    ll_visitDate.setVisibility(View.VISIBLE);

                } else {
                    physicalVisit = "0";
                    ll_visitDate.setVisibility(View.GONE);
                }
            }
        });
    }

    private void init() {
        button_finalsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString().trim();
                String visit_date = tv_visit_date.getText().toString().trim();
                if (message.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter resolution note", Toast.LENGTH_LONG).show();
                }else if (physicalVisit.equalsIgnoreCase("1") && visit_date.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Select visit date", Toast.LENGTH_LONG).show();
                } else {
                    confirmAlert(message);
                }
            }
        });

        tv_issuephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved") || !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
            tv_visit_date.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SimpleDateFormat")
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {
                    String[] raiseDt = ticketDetailsPojo.getTicketDate().split(" ");
                    String dayDifference="0";
                    int monthNumber1 = 0;
                    Log.e("Month", raiseDt[1]);
                    try {
                        monthNumber1 = getNumberFromMonthName(raiseDt[1], Locale.ENGLISH);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String raisedDate = monthNumber1 + "/" + raiseDt[0] + "/" + raiseDt[2];
                    try {
                        String CurrentDate= new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
                        String FinalDate= raisedDate;
                        Date date1;
                        Date date2;
                        SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
                        date1 = dates.parse(CurrentDate);
                        date2 = dates.parse(FinalDate);
                        long difference = Math.abs(date1.getTime() - date2.getTime());
                        long differenceDates = difference / (24 * 60 * 60 * 1000);
                        dayDifference = Long.toString(-differenceDates);
                        Log.e("Days", dayDifference);
                    } catch (Exception exception) {
                        Toast.makeText(getActivity(), "Unable to find difference", Toast.LENGTH_SHORT).show();
                    }

                    Utils.getInstance().showDatePicker(getActivity(), dayDifference, "0", new DateCommunicator() {
                        @Override
                        public void getDate(String date) {
                            tv_visit_date.setText(date);
                        }
                    });
                }
            });
        }

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
                TextView tv_astype = dialog.findViewById(R.id.tv_astype);
                TextView tv_assignto = dialog.findViewById(R.id.tv_assignto);
                tv_ticketID.setText("Ticket " + ticketDetailsPojo.getTicketNo());
                tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
                tv_priority.setText(ticketDetailsPojo.getPriority());
                if (ticketDetailsPojo.getIssueTitle() != null) {
                    tv_ticket_title.setText(ticketDetailsPojo.getIssueTitle());
                } else {
                    tv_ticket_title.setText(ticketDetailsPojo.getTicketIssueOther());
                }
                tv_ticket_desc.setText(ticketDetailsPojo.getIssueDesc());
                tv_custodian.setText(ticketDetailsPojo.getCustodianName());
                tv_ticketStatus.setText(ticketDetailsPojo.getTicketStatus());
                tv_category.setText(ticketDetailsPojo.getAssetCategory());
                tv_astype.setText(ticketDetailsPojo.getServiceType());
                tv_assignto.setText(ticketDetailsPojo.getAssignTo());

                if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")) {
                    tv_priority.setTextColor(Color.parseColor("#FE5247"));
                } else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")) {
                    tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
                } else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")) {
                    tv_priority.setTextColor(Color.parseColor("#FF8744"));
                } else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")) {
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

    public String getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        assert createdConvertedDate != null;
        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            assert todayWithZeroTime != null;
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }

        Calendar eCal = Calendar.getInstance();
        assert expireCovertedDate != null;
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ("" + (int) dayCount + " Days");
    }

    public long Daybetween(String date1,String date2,String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.ENGLISH);
        Date Date1 = null,Date2 = null;
        try{
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        assert Date2 != null;
        assert Date1 != null;
        return (Date2.getTime() - Date1.getTime())/(24*60*60*1000);
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
                    if (result) {
                        cameraIntent();
                    }

                } else if (items[item].equals("Choose from gallery")) {
                    userChoosenTask = "Choose from gallery";
                    if (result) {
                        galleryIntent();
                    }

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
            if (ActivityCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(getContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        ll_issuephoto.setVisibility(View.VISIBLE);
        iv_issuephoto.setImageURI(image_uri);
        //Log.e(TAG, String.valueOf(image_uri));
        path_billcopy = getPath(image_uri);
        //mArrayUri.add(image_uri);
        list.add(prepareFilePart("ticket_resolve_file", Uri.parse(path_billcopy)));
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        Bitmap selectedBitmap = null;
        if (data != null) {
            try {
                image_uri = data.getData();
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ll_issuephoto.setVisibility(View.VISIBLE);
        iv_issuephoto.setImageBitmap(bm);
        assert bm != null;
        String absolutePath = saveImage(bm);
        list.add(prepareFilePart("ticket_resolve_file", Uri.parse(absolutePath)));
        /*assert bm != null;
        path_billcopy = getPath(image_uri);
        Log.e(TAG, String.valueOf(image_uri));
        list.add(prepareFilePart("ticket_raise_files", image_uri));*/
    }

    public String getPath(Uri image_uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(image_uri, projection, null, null, null);
        getActivity().startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
            MediaScannerConnection.scanFile(getContext(), new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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

    @SuppressLint("SetTextI18n")
    private void confirmAlert(String message) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_confirmalert);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        Button cancel = dialog.findViewById(R.id.no_cancel);
        Button submit = dialog.findViewById(R.id.yes_submit);
        final TextView tvmessage = dialog.findViewById(R.id.tv_message);
        final TextView alerttitle = dialog.findViewById(R.id.tv_alerttitle);
        /*String msg1 = "Please note that once you click 'YES' button";
        String msg2 = "you cannot edit target details";
        String msg3 = "Are you sure want to continue!";
        message.setText(msg1+"\n"+msg2+"\n"+msg3);*/
        alerttitle.setText("Review before Submission");
        tvmessage.setText("Are you sure want to mark resolve!");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finalSubmit(message);
            }
        });
    }

    private void finalSubmit(String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        RequestBody action1 = RequestBody.create(MediaType.parse("text/plain"), "Resolve");
        RequestBody ticketID = RequestBody.create(MediaType.parse("text/plain"), ticketDetailsPojo.getTicketId());
        RequestBody message1 = RequestBody.create(MediaType.parse("text/plain"), message);
        RequestBody addToAsset1 = RequestBody.create(MediaType.parse("text/plain"), addToAsset);
        RequestBody physicalVisit1 = RequestBody.create(MediaType.parse("text/plain"), physicalVisit);
        String visit_date = tv_visit_date.getText().toString().trim();
        RequestBody visit_date1 = RequestBody.create(MediaType.parse("text/plain"), visit_date);
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateResolvedInfo("application/json", "Bearer " + token, action1, ticketID, message1, addToAsset1, physicalVisit1, visit_date1, list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        //Utils.getInstance().showAlert(getActivity(), submitSuccessResponse.getMessage());
                        Toast.makeText(getActivity(), submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), TicketsListActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
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
            public void onFailure(@NonNull Call<SubmitSuccessResponse> call, @NonNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
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


}