package com.vnrseeds.samadhan.addassetforms;

import static androidx.core.util.PatternsCompat.IP_ADDRESS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vnrseeds.samadhan.CropperActivity;
import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Antivirus;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Brand;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.DropDownResponse;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Location;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.NetworkType;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.OperatingSystem;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Processor;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Ram;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.RamCapacity;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Software;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Vendor;
import com.vnrseeds.samadhan.parser.assetdataparser.AssetData;
import com.vnrseeds.samadhan.parser.assetdataparser.AssetDataResponse;
import com.vnrseeds.samadhan.parser.assetdataparser.KeyboardBrandModel;
import com.vnrseeds.samadhan.parser.assetdataparser.MonitorBrandModel;
import com.vnrseeds.samadhan.parser.assetdataparser.MouseBrandModel;
import com.vnrseeds.samadhan.parser.brandmodelparser.BrandModelListResponse;
import com.vnrseeds.samadhan.parser.brandmodelparser.CpuModelsData;
import com.vnrseeds.samadhan.parser.brandmodelparser.Data;
import com.vnrseeds.samadhan.parser.brandmodelparser.KeyboardModelsData;
import com.vnrseeds.samadhan.parser.brandmodelparser.MonitorModelsData;
import com.vnrseeds.samadhan.parser.brandmodelparser.MouseModelsData;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianData;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianResponse;
import com.vnrseeds.samadhan.parser.departmentparser.DepartmentResponse;
import com.vnrseeds.samadhan.parser.sectionparser.SectionData;
import com.vnrseeds.samadhan.parser.sectionparser.SectionResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.UserPermissions;
import com.vnrseeds.samadhan.utils.Utils;

import org.jetbrains.annotations.NotNull;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAssetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "EditAssetActivity";
    private static final String SELECT = "select";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private LinearLayout primeinfo_form;
    private LinearLayout techinfo_form;
    private LinearLayout purinfo_form;
    private LinearLayout depinfo_form;
    private LinearLayout nwinfo_form;
    private LinearLayout swinfo_form;
    private LinearLayout pinfo_tab;
    private LinearLayout techinfo_tab;
    private LinearLayout purinfo_tab;
    private LinearLayout depinfo_tab;
    private LinearLayout nwinfo_tab;
    private LinearLayout swinfo_tab;
    private LinearLayout ll_hddingb;
    private LinearLayout ll_hddingb_sec;
    private LinearLayout ll_ssdingb;
    private LinearLayout ll_ssdingb_sec;
    private LinearLayout ll_ipaddress;
    private LinearLayout ll_subnetmask;
    private LinearLayout ll_getway;
    private String ac_id;
    private Spinner spinner_cpubrand;
    private Spinner spinner_cpumodel;
    private Spinner spinner_monitorbrand;
    private Spinner spinner_monitormodel;
    private Spinner spinner_keyboardbrand;
    private Spinner spinner_keyboardmodel;
    private Spinner spinner_mousebrand;
    private Spinner spinner_mousemodel;
    private EditText et_cpusrno;
    private EditText et_monitorsrno;
    private EditText et_keyboardsrno;
    private EditText et_mousesrno;
    private EditText et_hddingb;
    private EditText et_hddingb_sec;
    private EditText et_sddingb;
    private EditText et_sddingb_sec;
    private EditText et_invoiceno;
    private EditText et_price;
    private EditText et_maethernet;
    private EditText et_mawifi;
    private EditText et_avsrno;
    private EditText et_officeversion;
    private EditText et_ipaddress;
    private EditText et_subnetmask;
    private EditText et_gateway;
    private EditText et_remark;
    private Spinner spinner_processor;
    private Spinner spinner_ramcapacity;
    private Spinner spinner_ramtype;
    private Spinner spinner_eol;
    private Spinner spinner_vender;
    private Spinner spinner_installationloc;
    private Spinner spinner_department;
    private Spinner spinner_section;
    private Spinner spinner_networktype;
    private Spinner spinner_os;
    private Spinner spinner_antivirus;
    private Spinner spinner_officetype;

    private TextView tv_purchasedate;
    private TextView tv_monitorwarrantyupto;
    private TextView tv_keywarrantyupto;
    private TextView tv_mousewarrantyupto;
    private TextView tv_cpuwarrantyupto;
    private TextView tv_eolwarranty_date;
    private TextView tv_installationdate;
    private TextView tv_avinstall_date;
    private TextView tv_avrenew_date;
    private TextView tv_spinnerbrowser;
    private TextView tv_spinnercustodian;
    boolean[] selectedbrowser;
    ArrayList<Integer> browserArray = new ArrayList<>();
    boolean[] selectedcustodian;
    private String[] custodianlist;
    ArrayList<Integer> custodianArray = new ArrayList<>();
    private String[] softwarelist;
    private TextView tv_spinnerothersw;
    ArrayList<Integer> softwareArray = new ArrayList<>();
    boolean[] selectedsoftware;

    private String cpubrand_id;
    private String monitorbrand_id;
    private String keybrand_id;
    private String mousebrand_id;
    private final ArrayList<String> cpubrandmodelslist = new ArrayList<>();
    private final ArrayList<String> keybrandmodelslist = new ArrayList<>();
    private final ArrayList<String> monitorbrandmodelslist = new ArrayList<>();
    private final ArrayList<String> mousebrandmodelslist = new ArrayList<>();
    private CpuModelsData cpuModelsData;
    private MonitorModelsData monitorModelsData;
    private String cpumodel_id;
    private KeyboardModelsData keyboardModelsData;
    private MouseModelsData mouseModelsData;
    private String monitormodel_id;
    private String keyboardmodel_id;
    private String mousemodel_id;
    private String processor_id;
    private String ramtype_id;
    private String ramcapacity_id;
    private String vendor_id;
    private String custodian_id;
    private String nw_id;
    private String os_id;
    private String antivirus_id;
    private RadioGroup radiostatus;
    private final String regex = "^([0-9A-Fa-f]{2}[:-])"
            + "{5}([0-9A-Fa-f]{2})|"
            + "([0-9a-fA-F]{4}\\."
            + "[0-9a-fA-F]{4}\\."
            + "[0-9a-fA-F]{4})$";
    private Button bt_submit;
    private Spinner sp_assettype;
    private Spinner spinner_keyboardtype;
    private Spinner spinner_mousetype;
    private Spinner spinner_hddtype;
    private Spinner spinner_connectiontype;
    private Spinner spinner_osis_licenced;
    private Spinner spinner_office;
    private Spinner spinner_officelicence;
    private AssetData assetdata = new AssetData();
    private final ArrayList<String> assetTypeList = new ArrayList<>();
    private ArrayList<String> cpubrandlist;
    private Object dropDownList;
    private List<Brand> brands;
    private List<Processor> processor;
    private List<Ram> ram;
    private List<RamCapacity> ramcapacity;
    private List<Vendor> vendor;
    private List<Location> location;
    private List<NetworkType> networktype;
    private List<OperatingSystem> operatingSystem;
    private List<Antivirus> antivirus;
    private String[] browserlist;
    private List<Software> software;
    private List<CustodianData> custodians;
    private List<SectionData> sections;
    private List<com.vnrseeds.samadhan.parser.departmentparser.Data> departments;
    private List<Data> cpubrandmodels;
    private String selsection_id;
    private String seldept_id;
    private String selloc_id;
    private LinearLayout ll_avsrno;
    private LinearLayout ll_avdates;
    private DropDownResponse dropDownResponse;
    private final ArrayList<String> sectionslist = new ArrayList<>();
    private Spinner spinner_custodian;
    private AssetDataResponse assetDataResponse;
    private RadioButton radio_hdd_gb, radio_hdd_tb, radio_ssd_gb, radio_ssd_tb;
    private RadioButton radio_hdd_gb2, radio_hdd_tb2, radio_ssd_gb2, radio_ssd_tb2;
    private String asset_ssd_storage = "", asset_hdd_storage = "";
    private String asset_ssd_storage2 = "", asset_hdd_storage2 = "";
    private LinearLayout ll_eolupto;
    private LinearLayout ll_officetype;
    private LinearLayout ll_officeversion;
    private String sw_id = null;
    private String browsername;
    private String cust_id = null;
    private String asset_id;
    private RadioButton rb_active;
    private RadioButton rb_inactive;
    private EditText et_precise_loc;
    private EditText et_monitorscrsize;
    private String ac_shcode;
    private LinearLayout ll_monitorbrand;
    private LinearLayout ll_keyboard;
    private LinearLayout ll_keyboard2;
    private LinearLayout ll_mouse;
    private LinearLayout ll_mouse2;
    private LinearLayout ll_monitorwarranty;
    private LinearLayout ll_mousewarranty;
    private LinearLayout ll_monitor;

    //Add Photo
    private ImageView iv_srnophoto;
    private Button button_srnophoto;
    private String ImageString="";
    private String userChoosenTask;
    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    private String srnoImageString="";
    private ImageView iv_assetphoto;
    private Button button_assetphoto;
    private String assetImageString="";
    private int PIC_CROP=1;
    ActivityResultLauncher<String> mGetContent;
    private RequestBody requestSrnoFile;
    private RequestBody requestAssetFile;
    private MultipartBody.Part srnoimagebody;
    private MultipartBody.Part assetimagebody;
    private String path_billcopy;
    private List<MultipartBody.Part> list = new ArrayList<>();
    private File BILL_COPY;
    private ImageView iv_monitorsrnophoto;
    private ImageView iv_keysrnophoto;
    private ImageView iv_mousesrnophoto;
    private Button button_monitorsrnophoto;
    private Button button_keysrnophoto;
    private Button button_mousesrnophoto;
    private Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asset);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //Log.e(TAG, "selectedbrowser :");
        setTheme();
        init();
    }

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(EditAssetActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.edit_asset);
        back_nav = findViewById(R.id.back_nav);
        primeinfo_form = findViewById(R.id.pinfo_form);
        techinfo_form = findViewById(R.id.tech_form);
        purinfo_form = findViewById(R.id.purinfo_form);
        depinfo_form = findViewById(R.id.depinfo_form);
        nwinfo_form = findViewById(R.id.nw_form);
        swinfo_form = findViewById(R.id.sw_form);
        radio_hdd_gb = findViewById(R.id.radio_hdd_gb);
        radio_hdd_tb = findViewById(R.id.radio_hdd_tb);
        radio_ssd_gb = findViewById(R.id.radio_ssd_gb);
        radio_ssd_tb = findViewById(R.id.radio_ssd_tb);
        radio_hdd_gb2 = findViewById(R.id.radio_hdd_gb2);
        radio_hdd_tb2 = findViewById(R.id.radio_hdd_tb2);
        radio_ssd_gb2 = findViewById(R.id.radio_ssd_gb2);
        radio_ssd_tb2 = findViewById(R.id.radio_ssd_tb2);
        rb_active = findViewById(R.id.rb_active);
        rb_inactive = findViewById(R.id.rb_inactive);

        pinfo_tab = findViewById(R.id.pinfo_tab);
        techinfo_tab = findViewById(R.id.techinfo_tab);
        purinfo_tab = findViewById(R.id.purinfo_tab);
        depinfo_tab = findViewById(R.id.depinfo_tab);
        nwinfo_tab = findViewById(R.id.nwinfo_tab);
        swinfo_tab = findViewById(R.id.swinfo_tab);
        ll_hddingb = findViewById(R.id.ll_hddingb);
        ll_hddingb_sec = findViewById(R.id.ll_hddingb_sec);
        ll_ssdingb = findViewById(R.id.ll_ssdingb);
        ll_ssdingb_sec = findViewById(R.id.ll_ssdingb_sec);
        ll_ipaddress = findViewById(R.id.ll_ipaddress);
        ll_subnetmask = findViewById(R.id.ll_subnetmask);
        ll_getway = findViewById(R.id.ll_getway);
        ll_avsrno = findViewById(R.id.ll_avsrno);
        ll_avdates = findViewById(R.id.ll_avdates);
        ll_eolupto = findViewById(R.id.ll_eolupto);
        ll_officetype = findViewById(R.id.ll_officetype);
        ll_officeversion = findViewById(R.id.ll_officeversion);
        TextView tv_category = findViewById(R.id.tv_category);
        String category = getIntent().getStringExtra("category");
        ac_shcode = getIntent().getStringExtra("ac_shcode");
        tv_category.setText(category);
        asset_id = getIntent().getStringExtra("ac_id");
        //Toast.makeText(EditAssetActivity.this, ac_id, Toast.LENGTH_SHORT).show();
        spinner_cpubrand = findViewById(R.id.spinner_cpubrand);
        spinner_cpumodel = findViewById(R.id.spinner_cpumodel);
        spinner_monitorbrand = findViewById(R.id.spinner_monitorbrand);
        spinner_monitormodel = findViewById(R.id.spinner_monitormodel);
        spinner_keyboardbrand = findViewById(R.id.spinner_keyboardbrand);
        spinner_keyboardmodel = findViewById(R.id.spinner_keyboardmodel);
        spinner_mousebrand = findViewById(R.id.spinner_mousebrand);
        spinner_mousemodel = findViewById(R.id.spinner_mousemodel);
        et_cpusrno = findViewById(R.id.et_cpusrno);
        et_monitorsrno = findViewById(R.id.et_monitorsrno);
        et_keyboardsrno = findViewById(R.id.et_keyboardsrno);
        et_mousesrno = findViewById(R.id.et_mousesrno);
        et_hddingb = findViewById(R.id.et_hddingb);
        et_hddingb_sec = findViewById(R.id.et_hddingb_sec);
        et_sddingb = findViewById(R.id.et_sddingb);
        et_sddingb_sec = findViewById(R.id.et_sddingb_sec);
        et_invoiceno = findViewById(R.id.et_invoiceno);
        et_price = findViewById(R.id.et_price);
        et_maethernet = findViewById(R.id.et_maethernet);
        et_mawifi = findViewById(R.id.et_mawifi);
        et_avsrno = findViewById(R.id.et_avsrno);
        et_officeversion = findViewById(R.id.et_officeversion);
        et_ipaddress = findViewById(R.id.et_ipaddress);
        et_subnetmask = findViewById(R.id.et_subnetmask);
        et_gateway = findViewById(R.id.et_gateway);
        et_remark = findViewById(R.id.et_remark);
        et_precise_loc = findViewById(R.id.et_precise_loc);
        et_monitorscrsize = findViewById(R.id.et_monitorscrsize);

        spinner_processor = findViewById(R.id.spinner_processor);
        spinner_ramcapacity = findViewById(R.id.spinner_ramcapacity);
        spinner_ramtype = findViewById(R.id.spinner_ramtype);
        spinner_eol = findViewById(R.id.spinner_eol);
        spinner_vender = findViewById(R.id.spinner_vender);
        spinner_installationloc = findViewById(R.id.spinner_installationloc);
        spinner_department = findViewById(R.id.spinner_department);
        spinner_section = findViewById(R.id.spinner_section);
        spinner_networktype = findViewById(R.id.spinner_networktype);
        spinner_os = findViewById(R.id.spinner_os);
        spinner_antivirus = findViewById(R.id.spinner_antivirus);
        spinner_officetype = findViewById(R.id.spinner_officetype);
        spinner_custodian = findViewById(R.id.spinner_custodian);

        tv_purchasedate = findViewById(R.id.tv_purchasedate);
        tv_monitorwarrantyupto = findViewById(R.id.tv_monitorwarrantyupto);
        tv_keywarrantyupto = findViewById(R.id.tv_keywarrantyupto);
        tv_mousewarrantyupto = findViewById(R.id.tv_mousewarrantyupto);
        tv_cpuwarrantyupto = findViewById(R.id.tv_cpuwarrantyupto);
        tv_eolwarranty_date = findViewById(R.id.tv_eolwarranty_date);
        tv_installationdate = findViewById(R.id.tv_installationdate);
        tv_avinstall_date = findViewById(R.id.tv_avinstall_date);
        tv_avrenew_date = findViewById(R.id.tv_avrenew_date);
        tv_spinnerbrowser = findViewById(R.id.tv_spinnerbrowser);
        tv_spinnercustodian = findViewById(R.id.tv_spinnercustodian);
        tv_spinnerothersw = findViewById(R.id.tv_spinnerothersw);
        bt_submit = findViewById(R.id.bt_submit);
        radiostatus = findViewById(R.id.radiostatus);
        sp_assettype = findViewById(R.id.spinner_astype);
        spinner_keyboardtype = findViewById(R.id.spinner_keyboardtype);
        spinner_mousetype = findViewById(R.id.spinner_mousetype);
        spinner_hddtype = findViewById(R.id.spinner_hddtype);
        spinner_connectiontype = findViewById(R.id.spinner_connectiontype);
        spinner_osis_licenced = findViewById(R.id.spinner_osis_licenced);
        spinner_office = findViewById(R.id.spinner_office);
        spinner_officelicence = findViewById(R.id.spinner_officelicence);

        ll_monitorbrand = findViewById(R.id.ll_monitorbrand);
        ll_keyboard = findViewById(R.id.ll_keyboard);
        ll_keyboard2 = findViewById(R.id.ll_keyboard2);
        ll_mouse = findViewById(R.id.ll_mouse);
        ll_mouse2 = findViewById(R.id.ll_mouse2);
        ll_monitorwarranty = findViewById(R.id.ll_monitorwarranty);
        ll_mousewarranty = findViewById(R.id.ll_mousewarranty);
        ll_monitor = findViewById(R.id.ll_monitor);

        //Add Photo
        iv_srnophoto = findViewById(R.id.iv_srnophoto);
        iv_monitorsrnophoto = findViewById(R.id.iv_monitorsrnophoto);
        iv_keysrnophoto = findViewById(R.id.iv_keysrnophoto);
        iv_mousesrnophoto = findViewById(R.id.iv_mousesrnophoto);
        iv_assetphoto = findViewById(R.id.iv_assetphoto);
        button_srnophoto = findViewById(R.id.button_srnophoto);
        button_monitorsrnophoto = findViewById(R.id.button_monitorsrnophoto);
        button_keysrnophoto = findViewById(R.id.button_keysrnophoto);
        button_mousesrnophoto = findViewById(R.id.button_mousesrnophoto);
        button_assetphoto = findViewById(R.id.button_assetphoto);

        if (ac_shcode.equalsIgnoreCase("LAPT")){
            ll_monitorbrand.setVisibility(View.GONE);
            ll_keyboard.setVisibility(View.GONE);
            ll_keyboard2.setVisibility(View.GONE);
            ll_mouse.setVisibility(View.GONE);
            ll_mouse2.setVisibility(View.GONE);
            ll_monitorwarranty.setVisibility(View.GONE);
            ll_mousewarranty.setVisibility(View.GONE);
            ll_monitor.setVisibility(View.GONE);
        }

    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }

        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAssetActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        pinfo_tab.setOnClickListener(view -> {
            primeinfo_form.setVisibility(View.VISIBLE);
            techinfo_form.setVisibility(View.GONE);
            purinfo_form.setVisibility(View.GONE);
            depinfo_form.setVisibility(View.GONE);
            nwinfo_form.setVisibility(View.GONE);
            swinfo_form.setVisibility(View.GONE);
        });

        techinfo_tab.setOnClickListener(view -> {
            primeinfo_form.setVisibility(View.GONE);
            techinfo_form.setVisibility(View.VISIBLE);
            purinfo_form.setVisibility(View.GONE);
            depinfo_form.setVisibility(View.GONE);
            nwinfo_form.setVisibility(View.GONE);
            swinfo_form.setVisibility(View.GONE);
        });

        purinfo_tab.setOnClickListener(view -> {
            primeinfo_form.setVisibility(View.GONE);
            techinfo_form.setVisibility(View.GONE);
            purinfo_form.setVisibility(View.VISIBLE);
            depinfo_form.setVisibility(View.GONE);
            nwinfo_form.setVisibility(View.GONE);
            swinfo_form.setVisibility(View.GONE);
        });

        depinfo_tab.setOnClickListener(view -> {
            primeinfo_form.setVisibility(View.GONE);
            techinfo_form.setVisibility(View.GONE);
            purinfo_form.setVisibility(View.GONE);
            depinfo_form.setVisibility(View.VISIBLE);
            nwinfo_form.setVisibility(View.GONE);
            swinfo_form.setVisibility(View.GONE);
        });

        nwinfo_tab.setOnClickListener(view -> {
            primeinfo_form.setVisibility(View.GONE);
            techinfo_form.setVisibility(View.GONE);
            purinfo_form.setVisibility(View.GONE);
            depinfo_form.setVisibility(View.GONE);
            nwinfo_form.setVisibility(View.VISIBLE);
            swinfo_form.setVisibility(View.GONE);
        });

        swinfo_tab.setOnClickListener(view -> {
            primeinfo_form.setVisibility(View.GONE);
            techinfo_form.setVisibility(View.GONE);
            purinfo_form.setVisibility(View.GONE);
            depinfo_form.setVisibility(View.GONE);
            nwinfo_form.setVisibility(View.GONE);
            swinfo_form.setVisibility(View.VISIBLE);
        });

        button_srnophoto.setOnClickListener(view -> {
            ImageString = "srnophoto";
            //mGetContent.launch("image/*");
            selectImage();
        });
        button_monitorsrnophoto.setOnClickListener(view -> {
            ImageString = "monitorsrnophoto";
            //mGetContent.launch("image/*");
            selectImage();
        });
        button_keysrnophoto.setOnClickListener(view -> {
            ImageString = "keyboardsrnophoto";
            //mGetContent.launch("image/*");
            selectImage();
        });
        button_mousesrnophoto.setOnClickListener(view -> {
            ImageString = "mousesrnophoto";
            //mGetContent.launch("image/*");
            selectImage();
        });
        button_assetphoto.setOnClickListener(view -> {
            ImageString = "assetphoto";
            //mGetContent.launch("image/*");
            selectImage();
        });

        mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Intent intent = new Intent(EditAssetActivity.this, CropperActivity.class);
                intent.putExtra("DATA", result.toString());
                startActivityForResult(intent,101);
            }
        });

        getAssetData(asset_id);
        tv_spinnerbrowser.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(EditAssetActivity.this);
            builder.setTitle("Select browsers");
            builder.setCancelable(false);
            builder.setMultiChoiceItems(browserlist, selectedbrowser, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                    if (b) {
                        browserArray.add(i);
                        Collections.sort(browserArray);
                    } else {
                        //browserList.remove(i);
                        browserArray.remove(Integer.valueOf(i));
                    }
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int j = 0; j < browserArray.size(); j++) {
                        stringBuilder.append(browserlist[browserArray.get(j)]);
                        if (j != browserArray.size() - 1) {
                            stringBuilder.append(",");
                        }
                    }

                    tv_spinnerbrowser.setText(stringBuilder.toString());
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
                    for (int j = 0; j < selectedbrowser.length; j++) {
                        selectedbrowser[j] = false;
                        browserArray.clear();
                        tv_spinnerbrowser.setText("");
                    }
                }
            });
            builder.show();
        });

        tv_spinnerothersw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAssetActivity.this);
                builder.setTitle("Select other softwares");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(softwarelist, selectedsoftware, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            softwareArray.add(i);
                            Collections.sort(softwareArray);
                        } else {
                            //officetypeList.remove(i);
                            softwareArray.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < softwareArray.size(); j++) {
                            stringBuilder.append(softwarelist[softwareArray.get(j)]);
                            if (j != softwareArray.size() - 1) {
                                stringBuilder.append(",");
                            }
                        }

                        tv_spinnerothersw.setText(stringBuilder.toString());
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
                        for (int j = 0; j < selectedsoftware.length; j++) {
                            selectedsoftware[j] = false;
                            softwareArray.clear();
                            tv_spinnerothersw.setText("");
                        }
                    }
                });
                builder.show();
            }
        });

        tv_spinnercustodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAssetActivity.this);
                builder.setTitle("Select Custodians");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(custodianlist, selectedcustodian, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            custodianArray.add(i);
                            Collections.sort(custodianArray);
                        } else {
                            //officetypeList.remove(i);
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

        sp_assettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String assettype = sp_assettype.getSelectedItem().toString();
                if (assettype.equalsIgnoreCase("Individual")) {
                    spinner_custodian.setVisibility(View.VISIBLE);
                    tv_spinnercustodian.setVisibility(View.GONE);
                } else {
                    spinner_custodian.setVisibility(View.GONE);
                    tv_spinnercustodian.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //spinner_cpubrand.setOnItemSelectedListener(AddAssetActivity.this);
        spinner_cpumodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0) {
                    cpumodel_id = String.valueOf(cpubrandmodels.get(i-1).getBmId());
                    Toast.makeText(EditAssetActivity.this, cpumodel_id, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_monitormodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    monitormodel_id = String.valueOf(cpubrandmodels.get(i - 1).getBmId());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_keyboardmodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    keyboardmodel_id = String.valueOf(cpubrandmodels.get(i - 1).getBmId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_keyboardtype.setOnItemSelectedListener(EditAssetActivity.this);
        spinner_mousetype.setOnItemSelectedListener(EditAssetActivity.this);
        spinner_mousemodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    mousemodel_id = String.valueOf(cpubrandmodels.get(i - 1).getBmId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_eol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String eol = spinner_eol.getSelectedItem().toString();
                if (eol.equalsIgnoreCase("Yes")) {
                    ll_eolupto.setVisibility(View.VISIBLE);
                } else if (eol.equalsIgnoreCase("No")) {
                    ll_eolupto.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_connectiontype.setOnItemSelectedListener(EditAssetActivity.this);
        spinner_osis_licenced.setOnItemSelectedListener(EditAssetActivity.this);
        spinner_office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String office = spinner_office.getSelectedItem().toString();
                if (office.equalsIgnoreCase("Yes")) {
                    ll_officetype.setVisibility(View.VISIBLE);
                    ll_officeversion.setVisibility(View.VISIBLE);
                } else if (office.equalsIgnoreCase("No")) {
                    ll_officetype.setVisibility(View.GONE);
                    ll_officeversion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_officelicence.setOnItemSelectedListener(EditAssetActivity.this);
        spinner_custodian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    custodian_id = String.valueOf(custodians.get(i - 1).getCustodianId());
                    Toast.makeText(EditAssetActivity.this, custodian_id, Toast.LENGTH_SHORT).show();
                }else {
                    custodian_id = String.valueOf(custodians.get(i).getCustodianId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_networktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    nw_id = String.valueOf(networktype.get(i - 1).getNtId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_os.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    os_id = operatingSystem.get(i - 1).getOsId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_antivirus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String av = spinner_antivirus.getSelectedItem().toString();
                if (av.equalsIgnoreCase(SELECT)) {
                    ll_avsrno.setVisibility(View.GONE);
                    ll_avdates.setVisibility(View.GONE);
                } else {
                    ll_avsrno.setVisibility(View.VISIBLE);
                    ll_avdates.setVisibility(View.VISIBLE);
                }
                if (i > 0) {
                    antivirus_id = antivirus.get(i - 1).getAntivirusId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_vender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    vendor_id = vendor.get(i - 1).getVendorId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_ramtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    ramtype_id = ram.get(i - 1).getRamId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_ramcapacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    ramcapacity_id = ramcapacity.get(i - 1).getRcId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_processor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    processor_id = processor.get(i - 1).getProcessorId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_cpubrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Brand> brandlist = dropDownResponse.getData().getBrandList();
                if (i > 0) {
                    cpubrand_id = String.valueOf(brandlist.get(i - 1).getBrandId());
                    String product = "cpu";
                    cpubrandmodelslist.clear();
                    cpubrandmodelslist.add(SELECT);
                    getCPUModel(cpubrand_id, product);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_monitorbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Brand> brandlist = dropDownResponse.getData().getBrandList();
                if (i > 0) {
                    monitorbrand_id = String.valueOf(brandlist.get(i - 1).getBrandId());
                    String product = "monitor";
                    monitorbrandmodelslist.clear();
                    monitorbrandmodelslist.add(SELECT);
                    getCPUModel(monitorbrand_id, product);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_keyboardbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Brand> brandlist = dropDownResponse.getData().getBrandList();
                if (i > 0) {
                    keybrand_id = String.valueOf(brandlist.get(i - 1).getBrandId());
                    String product = "keyboard";
                    keybrandmodelslist.clear();
                    keybrandmodelslist.add(SELECT);
                    getCPUModel(keybrand_id, product);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_mousebrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Brand> brandlist = dropDownResponse.getData().getBrandList();
                if (i > 0) {
                    mousebrand_id = String.valueOf(brandlist.get(i - 1).getBrandId());
                    String product = "mouse";
                    mousebrandmodelslist.clear();
                    mousebrandmodelslist.add(SELECT);
                    getCPUModel(mousebrand_id, product);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_hddtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String hddtype = spinner_hddtype.getSelectedItem().toString();
                if (hddtype.equalsIgnoreCase("HDD")) {
                    ll_hddingb.setVisibility(View.VISIBLE);
                    ll_hddingb_sec.setVisibility(View.GONE);
                    ll_ssdingb.setVisibility(View.GONE);
                    ll_ssdingb_sec.setVisibility(View.GONE);
                } else if (hddtype.equalsIgnoreCase("SSD")) {
                    ll_hddingb.setVisibility(View.GONE);
                    ll_hddingb_sec.setVisibility(View.GONE);
                    ll_ssdingb.setVisibility(View.VISIBLE);
                    ll_ssdingb_sec.setVisibility(View.GONE);
                } else if (hddtype.equalsIgnoreCase("Combination")) {
                    ll_hddingb.setVisibility(View.VISIBLE);
                    ll_ssdingb.setVisibility(View.VISIBLE);
                    ll_hddingb_sec.setVisibility(View.GONE);
                    ll_ssdingb_sec.setVisibility(View.GONE);
                } else if (hddtype.equalsIgnoreCase("HDD Dual")) {
                    ll_hddingb.setVisibility(View.VISIBLE);
                    ll_ssdingb.setVisibility(View.GONE);
                    ll_hddingb_sec.setVisibility(View.VISIBLE);
                    ll_ssdingb_sec.setVisibility(View.GONE);
                } else if (hddtype.equalsIgnoreCase("SSD Dual")) {
                    ll_hddingb.setVisibility(View.GONE);
                    ll_ssdingb.setVisibility(View.VISIBLE);
                    ll_hddingb_sec.setVisibility(View.GONE);
                    ll_ssdingb_sec.setVisibility(View.VISIBLE);
                } else {
                    ll_hddingb.setVisibility(View.GONE);
                    ll_hddingb_sec.setVisibility(View.GONE);
                    ll_ssdingb.setVisibility(View.GONE);
                    ll_ssdingb_sec.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_installationloc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selloc_id = String.valueOf(location.get(i - 1).getLocationId());
                    getDeptList(selloc_id);
                    String assettype = sp_assettype.getSelectedItem().toString();
                    if (!assettype.equalsIgnoreCase("Individual") && !assettype.equalsIgnoreCase(SELECT)) {
                        getCustodianList_loc(selloc_id);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    seldept_id = String.valueOf(departments.get(i - 1).getDepartmentId());
                    getSectionList(selloc_id, seldept_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selsection_id = String.valueOf(sections.get(i - 1).getDsId());
                    String assettype = sp_assettype.getSelectedItem().toString();
                    if (assettype.equalsIgnoreCase("Individual")) {
                        getCustodianList(selsection_id);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_connectiontype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String contype = spinner_connectiontype.getSelectedItem().toString();
                if (contype.equalsIgnoreCase("Manual")) {
                    ll_ipaddress.setVisibility(View.VISIBLE);
                    ll_subnetmask.setVisibility(View.VISIBLE);
                    ll_getway.setVisibility(View.VISIBLE);
                } else {
                    ll_ipaddress.setVisibility(View.GONE);
                    ll_subnetmask.setVisibility(View.GONE);
                    ll_getway.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        tv_purchasedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_purchasedate.setText(date);
                    }
                });
            }
        });
        tv_monitorwarrantyupto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_monitorwarrantyupto.setText(date);
                    }
                });
            }
        });
        tv_keywarrantyupto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_keywarrantyupto.setText(date);
                    }
                });
            }
        });
        tv_mousewarrantyupto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_mousewarrantyupto.setText(date);
                    }
                });
            }
        });
        tv_cpuwarrantyupto.setOnClickListener(view -> Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
            @Override
            public void getDate(String date) {
                tv_cpuwarrantyupto.setText(date);
            }
        }));
        tv_eolwarranty_date.setOnClickListener(view -> Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
            @Override
            public void getDate(String date) {
                tv_eolwarranty_date.setText(date);
            }
        }));
        tv_installationdate.setOnClickListener(view -> Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
            @Override
            public void getDate(String date) {
                tv_installationdate.setText(date);
            }
        }));
        tv_avinstall_date.setOnClickListener(view -> Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
            @Override
            public void getDate(String date) {
                tv_avinstall_date.setText(date);
            }
        }));
        tv_avrenew_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(EditAssetActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_avrenew_date.setText(date);
                    }
                });
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Primary Info
                String asset_type = sp_assettype.getSelectedItem().toString();
                String cpubrand = spinner_cpubrand.getSelectedItem().toString();
                String cpumodel = spinner_cpumodel.getSelectedItem().toString();
                String cpusrno = et_cpusrno.getText().toString().trim();
                String monitorscrsize = et_monitorscrsize.getText().toString().trim();

                String monitorbrand = "";
                String monitormodel = "";
                String monitorsrno = "";
                String keyboardtype = "";
                String keyboardbrand = "";
                String keyboardmodel = "";
                String keyboardsrno = "";
                String mousetype = "";
                String mousebrand = "";
                String mousemodel = "";
                String mousesrno = "";
                if (ac_shcode.equalsIgnoreCase("DSKT")) {
                    monitorbrand = spinner_monitorbrand.getSelectedItem().toString();
                    monitormodel = spinner_monitormodel.getSelectedItem().toString();
                    monitorsrno = et_monitorsrno.getText().toString().trim();
                    keyboardtype = spinner_keyboardtype.getSelectedItem().toString();
                    keyboardbrand = spinner_keyboardbrand.getSelectedItem().toString();
                    keyboardmodel = spinner_keyboardmodel.getSelectedItem().toString();
                    keyboardsrno = et_keyboardsrno.getText().toString().trim();
                    mousetype = spinner_mousetype.getSelectedItem().toString();
                    mousebrand = spinner_mousebrand.getSelectedItem().toString();
                    mousemodel = spinner_mousemodel.getSelectedItem().toString();
                    mousesrno = et_mousesrno.getText().toString().trim();
                }
                //Tech Conf
                String processor = spinner_processor.getSelectedItem().toString();
                String ramcapacity = spinner_ramcapacity.getSelectedItem().toString();
                String ramtype = spinner_ramtype.getSelectedItem().toString();
                String hddtype = spinner_hddtype.getSelectedItem().toString();
                String hddingb = et_hddingb.getText().toString().trim();
                String hddingb_sec = et_hddingb_sec.getText().toString().trim();
                String sddingb = et_sddingb.getText().toString().trim();
                String sddingb_sec = et_sddingb_sec.getText().toString().trim();
                //Purchase Info
                String purchasedate = tv_purchasedate.getText().toString().trim();
                String invoiceno = et_invoiceno.getText().toString().trim();
                String monitorwarrantyupto = "";
                String keywarrantyupto = "";
                String mousewarrantyupto = "";
                if (ac_shcode.equalsIgnoreCase("DSKT")) {
                    monitorwarrantyupto = tv_monitorwarrantyupto.getText().toString().trim();
                    keywarrantyupto = tv_keywarrantyupto.getText().toString().trim();
                    mousewarrantyupto = tv_mousewarrantyupto.getText().toString().trim();
                }
                String cpuwarrantyupto = tv_cpuwarrantyupto.getText().toString().trim();
                String eol = spinner_eol.getSelectedItem().toString();
                String eolwarranty_date = tv_eolwarranty_date.getText().toString();
                //Dept Info
                String price = et_price.getText().toString().trim();
                String vender = spinner_vender.getSelectedItem().toString();
                String installationdate = tv_installationdate.getText().toString().trim();
                String precise_loc = et_precise_loc.getText().toString().trim();
                String installationloc = spinner_installationloc.getSelectedItem().toString();
                String department = spinner_department.getSelectedItem().toString();
                String section = spinner_section.getSelectedItem().toString();
                //String custodian = spinner_custodian.getSelectedItem().toString();
                //NW Info
                String networktype = spinner_networktype.getSelectedItem().toString();
                String maethernet = et_maethernet.getText().toString().trim();
                String mawifi = et_mawifi.getText().toString().trim();
                String connectiontype = spinner_connectiontype.getSelectedItem().toString();
                String ipaddress = et_ipaddress.getText().toString().trim();
                String subnetmask = et_subnetmask.getText().toString().trim();
                String gateway = et_gateway.getText().toString().trim();
                //SW Info
                String operatinSystem = spinner_os.getSelectedItem().toString();
                String osis_licenced = spinner_osis_licenced.getSelectedItem().toString();
                String antivirus = spinner_antivirus.getSelectedItem().toString();
                String avinstall_date = tv_avinstall_date.getText().toString();
                String avrenew_date = tv_avrenew_date.getText().toString();
                String avsrno = et_avsrno.getText().toString().trim();
                String spinnerbrowser = tv_spinnerbrowser.getText().toString().trim();
                String office = spinner_office.getSelectedItem().toString();
                String spinnerofficetype = spinner_officetype.getSelectedItem().toString();
                String officeversion = et_officeversion.getText().toString().trim();
                String officelicence = spinner_officelicence.getSelectedItem().toString();
                String spinnerothersw = tv_spinnerothersw.getText().toString().trim();
                String remark = et_remark.getText().toString().trim();
                /*ArrayList<Integer> cust_id = new ArrayList<>();
                for (int i=0;i<custodianArray.size();i++){
                    int ind = custodianArray.get(i);
                    cust_id.add(custodians.get(ind).getCustodianId());
                }*/
                //ArrayList<Integer> custodianArray_unique = custodianArray;
                cust_id=null;
                if (asset_type.equalsIgnoreCase("Individual")){
                    cust_id=custodian_id;
                }else {
                    for (int i = 0; i < custodianArray.size(); i++) {
                        int ind = custodianArray.get(i);
                        if (cust_id == null) {
                            cust_id = String.valueOf(custodians.get(ind).getCustodianId());
                        } else {
                            cust_id = cust_id + "," + custodians.get(ind).getCustodianId();
                        }
                        //sw_id.add(software.get(ind).getSoftwareId());
                    }
                }
                Matcher matcher = IP_ADDRESS.matcher(ipaddress);
                Matcher matcherSubnet = IP_ADDRESS.matcher(subnetmask);
                Matcher matchergateway = IP_ADDRESS.matcher(gateway);

                Pattern p = Pattern.compile(regex);
                Matcher matcher1 = p.matcher(maethernet);
                Matcher matcher2 = p.matcher(mawifi);

                if (asset_type.equalsIgnoreCase("") || asset_type.equalsIgnoreCase("select")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select asset type");
                } else if (cpubrand.equalsIgnoreCase(SELECT) || cpubrand.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select CPU brand");
                } else if (cpumodel.equalsIgnoreCase(SELECT) || cpumodel.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select CPU model");
                } else if (cpusrno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter CPU serial number");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (monitorbrand.equalsIgnoreCase(SELECT) || monitorbrand.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select monitor brand");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (monitormodel.equalsIgnoreCase(SELECT) || monitormodel.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select monitor model");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (monitorsrno.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter monitor serial number");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (keyboardtype.equalsIgnoreCase(SELECT) || keyboardtype.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select keyboard type");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (keyboardbrand.equalsIgnoreCase(SELECT) || keyboardbrand.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select keyboard brand");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (keyboardmodel.equalsIgnoreCase(SELECT) || keyboardmodel.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select keyboard model");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (keyboardsrno.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter keyboard serial number");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (mousetype.equalsIgnoreCase(SELECT) || mousetype.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select mouse type");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (mousebrand.equalsIgnoreCase(SELECT) || mousebrand.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select mouse brand");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (mousemodel.equalsIgnoreCase(SELECT) || mousemodel.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select mouse model");
                } else if (!ac_shcode.equalsIgnoreCase("LAPT") && (mousesrno.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter mouse serial number");
                } else if (processor.equalsIgnoreCase(SELECT) || processor.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select processor brand");
                } else if (ramcapacity.equalsIgnoreCase(SELECT) || ramcapacity.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select ram capacity");
                } else if (ramtype.equalsIgnoreCase(SELECT) || ramtype.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select ram type");
                } else if (hddtype.equalsIgnoreCase(SELECT) || hddtype.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select HDD type");
                } else if (hddtype.equalsIgnoreCase("HDD") && hddingb.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter HDD (in GB)");
                } else if (hddtype.equalsIgnoreCase("SSD") && sddingb.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter SSD (in GB)");
                } else if (hddtype.equalsIgnoreCase("Combination") && (sddingb.equalsIgnoreCase("") || hddingb.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter HDD (in GB) and SSD (in GB)");
                } else if (purchasedate.equalsIgnoreCase("select date") || purchasedate.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select invoice date");
                } else if (invoiceno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter invoice number");
                } else if (monitorwarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select monitor warranty date");
                } else if (keywarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select keyboard warranty date");
                } else if (mousewarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select mouse warranty date");
                } else if (cpuwarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select CPU warranty date");
                } else if (eol.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select IS end of life (EOL)");
                } else if (eol.equalsIgnoreCase("Yes") && eolwarranty_date.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select EOL date");
                } else if (price.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter price");
                } else if (vender.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select vendor");
                } else if (installationdate.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select Installation Date");
                } else if (installationloc.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select Installation location");
                } else if (department.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select Installation department");
                } else if (section.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select section");
                } else if (cust_id == null) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select custodian");
                } else if (networktype.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select networktype");
                } else if (!maethernet.equalsIgnoreCase("") && !matcher1.matches()) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Invalid MAC address ethernet");
                } else if (!mawifi.equalsIgnoreCase("") && !matcher2.matches()) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Invalid MAC address WiFi");
                } else if (connectiontype.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select connection type");
                } else if (connectiontype.equalsIgnoreCase("Manual") && (ipaddress.equalsIgnoreCase("") || gateway.equalsIgnoreCase("") || subnetmask.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter IP address, subnet mask and gatway");
                } else if (!ipaddress.equalsIgnoreCase("") && !matcher.matches()) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Invalid IP address");
                } else if (!subnetmask.equalsIgnoreCase("") && !matcherSubnet.matches()) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Invalid subnet mask");
                } else if (!gateway.equalsIgnoreCase("") && !matchergateway.matches()) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Invalid gateway ip");
                } else if (operatinSystem.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select operating system");
                } else if (osis_licenced.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select OS update");
                } else if (antivirus.equalsIgnoreCase("Yes") && avinstall_date.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select antivirus install date");
                } else if (antivirus.equalsIgnoreCase("Yes") && avrenew_date.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select antivirus renewal date");
                } else if (antivirus.equalsIgnoreCase("Yes") && avsrno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Enter antivirus serial number");
                } else if (spinnerbrowser.equalsIgnoreCase("select") || spinnerbrowser.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select browser");
                } else if (office.equalsIgnoreCase("Yes") && spinnerofficetype.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select Office type");
                } else if (office.equalsIgnoreCase("Yes") && officeversion.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select office version");
                } else if (office.equalsIgnoreCase("Yes") && officelicence.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select office update");
                } else if (spinnerothersw.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditAssetActivity.this, "Select other softwares");
                } else {
                    if (hddtype.equalsIgnoreCase("Combination")) {
                        if (radio_hdd_gb.isChecked()) {
                            asset_hdd_storage = "GB";
                        } else {
                            asset_hdd_storage = "TB";
                        }
                        if (radio_ssd_gb.isChecked()) {
                            asset_ssd_storage = "GB";
                        } else {
                            asset_ssd_storage = "TB";
                        }
                    } else if (hddtype.equalsIgnoreCase("HDD")) {
                        if (radio_hdd_gb.isChecked()) {
                            asset_hdd_storage = "GB";
                        } else {
                            asset_hdd_storage = "TB";
                        }
                    } else if (hddtype.equalsIgnoreCase("SSD")) {
                        if (radio_ssd_gb.isChecked()) {
                            asset_ssd_storage = "GB";
                        } else {
                            asset_ssd_storage = "TB";
                        }
                    } else if (hddtype.equalsIgnoreCase("HDD Dual")) {
                        if (radio_hdd_gb.isChecked()) {
                            asset_hdd_storage = "GB";
                        } else {
                            asset_hdd_storage = "TB";
                        }
                        if (radio_hdd_gb2.isChecked()) {
                            asset_hdd_storage2 = "GB";
                        } else {
                            asset_hdd_storage2 = "TB";
                        }
                    } else if (hddtype.equalsIgnoreCase("SSD Dual")) {
                        if (radio_ssd_gb.isChecked()) {
                            asset_ssd_storage = "GB";
                        } else {
                            asset_ssd_storage = "TB";
                        }
                        if (radio_ssd_gb2.isChecked()) {
                            asset_ssd_storage2 = "GB";
                        } else {
                            asset_ssd_storage2 = "TB";
                        }
                    }

                    browsername = spinnerbrowser;
                    //String[] officename = spinnerofficetype.split(",");
                    String officename = spinnerofficetype;
                    sw_id=null;
                    for (int i = 0; i < softwareArray.size(); i++) {
                        int ind = softwareArray.get(i);
                        if (sw_id == null) {
                            sw_id = software.get(ind).getSoftwareId();
                        } else {
                            sw_id = sw_id + "," + software.get(ind).getSoftwareId();
                        }
                        //sw_id.add(software.get(ind).getSoftwareId());
                    }

                    int eolvalue = 0;
                    if (eol.equalsIgnoreCase("Yes")) {
                        eolvalue = 1;
                    }
                    int osislic = 0;
                    if (osis_licenced.equalsIgnoreCase("Yes")) {
                        osislic = 1;
                    }
                    int officeval = 0;
                    if (office.equalsIgnoreCase("Yes")) {
                        officeval = 1;
                    }
                    int officeupdateval = 0;
                    if (officelicence.equalsIgnoreCase("Yes")) {
                        officeupdateval = 1;
                    }
                    int rdstatus = radiostatus.getCheckedRadioButtonId();
                    RadioButton rdsamp = findViewById(rdstatus);
                    String status = rdsamp.getText().toString();
                    int statusval;
                    if (status.equalsIgnoreCase("In-Active")) {
                        statusval = 0;
                    } else {
                        statusval = 1;
                    }

                    submitAssetForm(asset_type, cpubrand_id, cpumodel_id, cpusrno, monitorbrand_id, monitormodel_id, monitorsrno, keyboardtype, keybrand_id,
                            keyboardmodel_id, keyboardsrno, mousetype, mousebrand_id, mousemodel_id, mousesrno, processor_id, ramtype_id, ramcapacity_id, hddtype, hddingb, sddingb,
                            purchasedate, invoiceno, vendor_id, price, cpuwarrantyupto, monitorwarrantyupto, keywarrantyupto, mousewarrantyupto, eolvalue, eolwarranty_date,
                            installationdate, selloc_id, seldept_id, selsection_id, cust_id, nw_id, maethernet, mawifi, connectiontype, ipaddress, subnetmask, gateway,
                            os_id, osislic, antivirus_id, avsrno, avinstall_date, avrenew_date, browsername, officeval, officename, officeupdateval, sw_id, remark, statusval, officeversion, hddingb_sec, sddingb_sec, precise_loc, monitorscrsize);
                }
            }
        });
    }

    private void submitAssetForm(String asset_type, String cpubrand_id, String cpumodel_id, String cpusrno, String monitorbrand_id, String monitormodel_id, String monitorsrno, String keyboardtype, String keybrand_id, String keyboardmodel_id, String keyboardsrno, String mousetype, String mousebrand_id, String mousemodel_id, String mousesrno, String processor_id, String ramtype_id, String ramcapacity_id, String hddtype, String hddingb, String sddingb, String purchasedate, String invoiceno, String vendor_id, String price, String cpuwarrantyupto, String monitorwarrantyupto, String keywarrantyupto, String mousewarrantyupto, int eolvalue, String eolwarranty_date, String installationdate, String selloc_id, String seldept_id, String selsection_id, String cust_id, String nw_id, String maethernet, String mawifi, String connectiontype, String ipaddress, String subnetmask, String gateway, String os_id, int osislic, String antivirus_id, String avsrno, String avinstall_date, String avrenew_date, String browsername, int officeval, String officename, int officeupdateval, String sw_id, String remark, int statusval, String officeversion, String hddingb_sec, String sddingb_sec, String precise_loc, String monitorscrsize) {
        RequestBody asset_type1 = RequestBody.create(MediaType.parse("text/plain"), asset_type);
        RequestBody cpubrand_id1 = RequestBody.create(MediaType.parse("text/plain"), cpubrand_id);
        RequestBody cpumodel_id1 = RequestBody.create(MediaType.parse("text/plain"), cpumodel_id);
        RequestBody cpusrno1 = RequestBody.create(MediaType.parse("text/plain"), cpusrno);
        RequestBody monitorbrand_id1 = RequestBody.create(MediaType.parse("text/plain"), monitorbrand_id);
        RequestBody monitormodel_id1 = RequestBody.create(MediaType.parse("text/plain"), monitormodel_id);
        RequestBody monitorsrno1 = RequestBody.create(MediaType.parse("text/plain"), monitorsrno);
        RequestBody keyboardtype1 = RequestBody.create(MediaType.parse("text/plain"), keyboardtype);
        RequestBody keybrand_id1 = RequestBody.create(MediaType.parse("text/plain"), keybrand_id);
        RequestBody keyboardmodel_id1 = RequestBody.create(MediaType.parse("text/plain"), keyboardmodel_id);
        RequestBody keyboardsrno1 = RequestBody.create(MediaType.parse("text/plain"), keyboardsrno);
        RequestBody mousetype1 = RequestBody.create(MediaType.parse("text/plain"), mousetype);
        RequestBody mousebrand_id1 = RequestBody.create(MediaType.parse("text/plain"), mousebrand_id);
        RequestBody mousemodel_id1 = RequestBody.create(MediaType.parse("text/plain"), mousemodel_id);
        RequestBody mousesrno1 = RequestBody.create(MediaType.parse("text/plain"), mousesrno);
        RequestBody processor_id1 = RequestBody.create(MediaType.parse("text/plain"), processor_id);
        RequestBody ramtype_id1 = RequestBody.create(MediaType.parse("text/plain"), ramtype_id);
        RequestBody ramcapacity_id1 = RequestBody.create(MediaType.parse("text/plain"), ramcapacity_id);
        RequestBody hddtype1 = RequestBody.create(MediaType.parse("text/plain"), hddtype);
        RequestBody hddingb1 = RequestBody.create(MediaType.parse("text/plain"), hddingb);
        RequestBody sddingb1 = RequestBody.create(MediaType.parse("text/plain"), sddingb);
        RequestBody purchasedate1 = RequestBody.create(MediaType.parse("text/plain"), purchasedate);
        RequestBody invoiceno1 = RequestBody.create(MediaType.parse("text/plain"), invoiceno);
        RequestBody vendor_id1 = RequestBody.create(MediaType.parse("text/plain"), vendor_id);
        RequestBody price1 = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody cpuwarrantyupto1 = RequestBody.create(MediaType.parse("text/plain"), cpuwarrantyupto);
        RequestBody monitorwarrantyupto1 = RequestBody.create(MediaType.parse("text/plain"), monitorwarrantyupto);
        RequestBody keywarrantyupto1 = RequestBody.create(MediaType.parse("text/plain"), keywarrantyupto);
        RequestBody mousewarrantyupto1 = RequestBody.create(MediaType.parse("text/plain"), mousewarrantyupto);
        RequestBody eolvalue1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(eolvalue));
        RequestBody eolwarranty_date1 = RequestBody.create(MediaType.parse("text/plain"), eolwarranty_date);
        RequestBody installationdate1 = RequestBody.create(MediaType.parse("text/plain"), installationdate);
        RequestBody selloc_id1 = RequestBody.create(MediaType.parse("text/plain"), selloc_id);
        RequestBody seldept_id1 = RequestBody.create(MediaType.parse("text/plain"), seldept_id);
        RequestBody selsection_id1 = RequestBody.create(MediaType.parse("text/plain"), selsection_id);
        RequestBody custodian_id1 = RequestBody.create(MediaType.parse("text/plain"), cust_id);
        RequestBody nw_id1 = RequestBody.create(MediaType.parse("text/plain"), nw_id);
        RequestBody maethernet1 = RequestBody.create(MediaType.parse("text/plain"), maethernet);
        RequestBody mawifi1 = RequestBody.create(MediaType.parse("text/plain"), mawifi);
        RequestBody connectiontype1 = RequestBody.create(MediaType.parse("text/plain"), connectiontype);
        RequestBody ipaddress1 = RequestBody.create(MediaType.parse("text/plain"), ipaddress);
        RequestBody subnetmask1 = RequestBody.create(MediaType.parse("text/plain"), subnetmask);
        RequestBody gateway1 = RequestBody.create(MediaType.parse("text/plain"), gateway);
        RequestBody os_id1 = RequestBody.create(MediaType.parse("text/plain"), os_id);
        RequestBody osislic1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(osislic));
        RequestBody antivirus_id1 = RequestBody.create(MediaType.parse("text/plain"), antivirus_id);
        RequestBody avsrno1 = RequestBody.create(MediaType.parse("text/plain"), avsrno);
        RequestBody avinstall_date1 = RequestBody.create(MediaType.parse("text/plain"), avinstall_date);
        RequestBody avrenew_date1 = RequestBody.create(MediaType.parse("text/plain"), avrenew_date);
        RequestBody browsername1 = RequestBody.create(MediaType.parse("text/plain"), browsername);
        RequestBody officeval1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(officeval));
        RequestBody officename1 = RequestBody.create(MediaType.parse("text/plain"), officename);
        RequestBody officeupdateval1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(officeupdateval));
        RequestBody sw_id1 = RequestBody.create(MediaType.parse("text/plain"), sw_id);
        RequestBody remark1 = RequestBody.create(MediaType.parse("text/plain"), remark);
        RequestBody statusval1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(statusval));
        RequestBody officeversion1 = RequestBody.create(MediaType.parse("text/plain"), officeversion);
        RequestBody hddingb_sec1 = RequestBody.create(MediaType.parse("text/plain"), hddingb_sec);
        RequestBody sddingb_sec1 = RequestBody.create(MediaType.parse("text/plain"), sddingb_sec);
        RequestBody precise_loc1 = RequestBody.create(MediaType.parse("text/plain"), precise_loc);
        RequestBody monitorscrsize1 = RequestBody.create(MediaType.parse("text/plain"), monitorscrsize);
        RequestBody ac_id1 = RequestBody.create(MediaType.parse("text/plain"), ac_id);
        RequestBody asset_hdd_storage1 = RequestBody.create(MediaType.parse("text/plain"), asset_hdd_storage);
        RequestBody asset_ssd_storage1 = RequestBody.create(MediaType.parse("text/plain"), asset_ssd_storage);
        RequestBody asset_hdd_storage21 = RequestBody.create(MediaType.parse("text/plain"), asset_hdd_storage2);
        RequestBody asset_ssd_storage21 = RequestBody.create(MediaType.parse("text/plain"), asset_ssd_storage2);
        RequestBody asset_id1 = RequestBody.create(MediaType.parse("text/plain"), asset_id);
        RequestBody cust_id1 = RequestBody.create(MediaType.parse("text/plain"), cust_id);
        RequestBody asset_ac_id = RequestBody.create(MediaType.parse("text/plain"), assetdata.getAssetAcId());
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getUpdateInfo("application/json", "Bearer " + token, asset_id1, asset_type1, cpubrand_id1, cpumodel_id1,
                cpusrno1, monitorbrand_id1, monitormodel_id1, monitorsrno1, keyboardtype1, keybrand_id1,
                keyboardmodel_id1, keyboardsrno1, mousetype1, mousebrand_id1, mousemodel_id1, mousesrno1, processor_id1, ramtype_id1, ramcapacity_id1, hddtype1, hddingb1, sddingb1,
                purchasedate1, invoiceno1, vendor_id1, price1, cpuwarrantyupto1, monitorwarrantyupto1, keywarrantyupto1, mousewarrantyupto1, eolvalue1, eolwarranty_date1,
                installationdate1, selloc_id1, seldept_id1, selsection_id1, cust_id1, nw_id1, maethernet1, mawifi1, connectiontype1, ipaddress1, subnetmask1, gateway1,
                os_id1, osislic1, antivirus_id1, avsrno1, avinstall_date1, avrenew_date1, browsername1, officeval1, officename1, officeupdateval1, sw_id1, remark1, statusval1,
                officeversion1, asset_hdd_storage1, asset_ssd_storage1, hddingb_sec1, sddingb_sec1, asset_hdd_storage21, asset_ssd_storage21, asset_ac_id, precise_loc1, monitorscrsize1, list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(EditAssetActivity.this, MainActivity.class);
                        Toast.makeText(EditAssetActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
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
                Toast.makeText(EditAssetActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAssetData(String asset_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<AssetDataResponse> call = apiInterface.getAssetInfo("application/json", "Bearer " + token, asset_id);
        call.enqueue(new Callback<AssetDataResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<AssetDataResponse> call, @NotNull Response<AssetDataResponse> response) {
                customProgressDialogue.cancel();
                Toast.makeText(EditAssetActivity.this, "Success", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    assetDataResponse = response.body();
                    assert assetDataResponse != null;
                    assetdata = assetDataResponse.getData().getAssetData();
                    ac_id = assetdata.getAssetAcId();
                    et_cpusrno.setText(assetdata.getAssetSerialNo());
                    if (assetdata.getAssetMonitorSize() != null) {
                        et_monitorscrsize.setText(assetdata.getAssetMonitorSize());
                    }
                    String[] purdate = assetdata.getAssetInvoiceDate().split("-");
                    tv_purchasedate.setText(purdate[2] + "-" + purdate[1] + "-" + purdate[0]);
                    et_invoiceno.setText(assetdata.getAssetInvoiceNo());
                    et_price.setText(assetdata.getAssetPrice());
                    et_precise_loc.setText(assetdata.getAssetPreciseLocation());
                    String[] cpudate = assetdata.getAssetWarrantyUpto().split("-");
                    tv_cpuwarrantyupto.setText(cpudate[2] + "-" + cpudate[1] + "-" + cpudate[0]);
                    if (ac_shcode.equalsIgnoreCase("DSKT")){
                        et_keyboardsrno.setText(assetdata.getAssetKeyboardSerialNo());
                        et_monitorsrno.setText(assetdata.getAssetMonitorSerialNo());
                        et_mousesrno.setText(assetdata.getAssetMouseSerialNo());
                        String[] mondate = assetdata.getAssetMonitorWarrantyUpto().split("-");
                        tv_monitorwarrantyupto.setText(mondate[2] + "-" + mondate[1] + "-" + mondate[0]);
                        String[] keydate = assetdata.getAssetKeyboardWarrantyUpto().split("-");
                        tv_keywarrantyupto.setText(keydate[2] + "-" + keydate[1] + "-" + keydate[0]);
                        String[] mousedate = assetdata.getAssetMouseWarrantyUpto().split("-");
                        tv_mousewarrantyupto.setText(mousedate[2] + "-" + mousedate[1] + "-" + mousedate[0]);
                    }
                    if (assetdata.getAssetEolWarrantyUpto() != null) {
                        String[] eoldate = assetdata.getAssetEolWarrantyUpto().split("-");
                        tv_eolwarranty_date.setText(eoldate[2] + "-" + eoldate[1] + "-" + eoldate[0]);
                    } else {
                        tv_eolwarranty_date.setText("");
                    }
                    String[] installdate = assetdata.getAssetInstallationDate().split("-");
                    tv_installationdate.setText(installdate[2] + "-" + installdate[1] + "-" + installdate[0]);
                    et_maethernet.setText(assetdata.getAssetMacEthernetAddress());
                    et_mawifi.setText(assetdata.getAssetMacWifiAddress());
                    et_remark.setText(assetdata.getAssetRemarks());
                    if (assetdata.getAssetIpAddress() != null) {
                        et_ipaddress.setText(assetdata.getAssetIpAddress());
                    }
                    if (assetdata.getAssetSubnetMaskAddress() != null) {
                        et_subnetmask.setText(assetdata.getAssetSubnetMaskAddress());
                    }
                    if (assetdata.getAssetGatewayAddress() != null) {
                        et_gateway.setText(assetdata.getAssetGatewayAddress());
                    }
                    et_avsrno.setText(assetdata.getAssetAntivirusSerialNo());
                    if (assetdata.getAssetAntivirusInstallationDate() != null) {
                        String[] avinstalldate = assetdata.getAssetAntivirusInstallationDate().split("-");
                        tv_avinstall_date.setText(avinstalldate[2] + "-" + avinstalldate[1] + "-" + avinstalldate[0]);
                    } else {
                        tv_avinstall_date.setText("");
                    }
                    if (assetdata.getAssetAntivirusRenewalDate() != null) {
                        String[] avrenewdate = assetdata.getAssetAntivirusRenewalDate().split("-");
                        tv_avrenew_date.setText(avrenewdate[2] + "-" + avrenewdate[1] + "-" + avrenewdate[0]);
                    } else {
                        tv_avrenew_date.setText("");
                    }
                    custodian_id=assetdata.getAssetInstallationCustodianIds();
                    assetTypeList.add(assetdata.getAssetType());
                    //browserlist=assetdata.getAssetBrowsers().toArray(new String[0]);
                    if (assetdata.getAssetIsEol().equalsIgnoreCase("1")) {
                        ll_eolupto.setVisibility(View.VISIBLE);
                    } else {
                        ll_eolupto.setVisibility(View.GONE);
                    }
                    if (assetdata != null && !assetdata.getAssetType().equalsIgnoreCase("Individual")) {
                        assetTypeList.add("Individual");
                    }
                    if (assetdata != null && !assetdata.getAssetType().equalsIgnoreCase("Shared")) {
                        assetTypeList.add("Shared");
                    }
                    if (assetdata != null && !assetdata.getAssetType().equalsIgnoreCase("Group")) {
                        assetTypeList.add("Group");
                    }
                    ArrayAdapter<String> assettypeAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, assetTypeList);
                    assettypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_assettype.setAdapter(assettypeAdapter);

                    ArrayList<String> hddTypeList = new ArrayList<>();
                    hddTypeList.add(assetdata.getAssetHardDiskType());
                    if (!assetdata.getAssetHardDiskType().equalsIgnoreCase("HDD")) {
                        hddTypeList.add("HDD");
                    }
                    if (!assetdata.getAssetHardDiskType().equalsIgnoreCase("SSD")) {
                        hddTypeList.add("SSD");
                    }
                    if (!assetdata.getAssetHardDiskType().equalsIgnoreCase("Combination")) {
                        hddTypeList.add("Combination");
                    }
                    if (!assetdata.getAssetHardDiskType().equalsIgnoreCase("HDD Dual")) {
                        hddTypeList.add("HDD Dual");
                    }
                    if (!assetdata.getAssetHardDiskType().equalsIgnoreCase("SSD Dual")) {
                        hddTypeList.add("SSD Dual");
                    }
                    if (assetdata.getAssetHardDiskType().equalsIgnoreCase("HDD")) {
                        ll_hddingb.setVisibility(View.VISIBLE);
                        ll_ssdingb.setVisibility(View.GONE);
                        ll_hddingb_sec.setVisibility(View.GONE);
                        ll_ssdingb_sec.setVisibility(View.GONE);
                        et_hddingb.setText(assetdata.getAssetHddCapacity());
                    } else if (assetdata.getAssetHardDiskType().equalsIgnoreCase("SSD")) {
                        ll_hddingb.setVisibility(View.GONE);
                        ll_ssdingb.setVisibility(View.VISIBLE);
                        ll_hddingb_sec.setVisibility(View.GONE);
                        ll_ssdingb_sec.setVisibility(View.GONE);
                        et_sddingb.setText(assetdata.getAssetSsdCapacity());
                    } else if (assetdata.getAssetHardDiskType().equalsIgnoreCase("Combination")) {
                        ll_hddingb.setVisibility(View.VISIBLE);
                        ll_ssdingb.setVisibility(View.VISIBLE);
                        ll_hddingb_sec.setVisibility(View.GONE);
                        ll_ssdingb_sec.setVisibility(View.GONE);
                        et_hddingb.setText(assetdata.getAssetHddCapacity());
                        et_sddingb.setText(assetdata.getAssetSsdCapacity());
                    } else if (assetdata.getAssetHardDiskType().equalsIgnoreCase("HDD Dual")) {
                        ll_hddingb.setVisibility(View.VISIBLE);
                        ll_ssdingb.setVisibility(View.GONE);
                        ll_hddingb_sec.setVisibility(View.VISIBLE);
                        ll_ssdingb_sec.setVisibility(View.GONE);
                        et_hddingb.setText(assetdata.getAssetHddCapacity());
                        et_hddingb_sec.setText(assetdata.getAssetHddCapacitySecondary());
                    } else if (assetdata.getAssetHardDiskType().equalsIgnoreCase("SSD Dual")) {
                        ll_hddingb.setVisibility(View.GONE);
                        ll_ssdingb.setVisibility(View.VISIBLE);
                        ll_hddingb_sec.setVisibility(View.GONE);
                        ll_ssdingb_sec.setVisibility(View.VISIBLE);
                        et_sddingb.setText(assetdata.getAssetSsdCapacity());
                        et_sddingb_sec.setText(assetdata.getAssetSsdCapacitySecondary());
                    } else {
                        ll_hddingb.setVisibility(View.GONE);
                        ll_ssdingb.setVisibility(View.GONE);
                        ll_hddingb_sec.setVisibility(View.GONE);
                        ll_ssdingb_sec.setVisibility(View.GONE);
                    }
                    if (assetdata.getAssetHddStorage() != null) {
                        if (assetdata.getAssetHddStorage().equalsIgnoreCase("GB")) {
                            radio_hdd_gb.setChecked(true);
                            radio_hdd_tb.setChecked(false);
                        } else {
                            radio_hdd_tb.setChecked(true);
                            radio_hdd_gb.setChecked(false);
                        }
                    }
                    if (assetdata.getAssetHddStorageSecondary() != null) {
                        if (assetdata.getAssetHddStorageSecondary().equalsIgnoreCase("GB")) {
                            radio_hdd_gb2.setChecked(true);
                            radio_hdd_tb2.setChecked(false);
                        } else {
                            radio_hdd_tb2.setChecked(true);
                            radio_hdd_gb2.setChecked(false);
                        }
                    }
                    if (assetdata.getAssetSsdStorage() != null) {
                        if (assetdata.getAssetSsdStorage().equalsIgnoreCase("GB")) {
                            radio_ssd_gb.setChecked(true);
                            radio_ssd_tb.setChecked(false);
                        } else {
                            radio_ssd_gb.setChecked(false);
                            radio_ssd_tb.setChecked(true);
                        }
                    }
                    if (assetdata.getAssetSsdStorageSecondary() != null) {
                        if (assetdata.getAssetSsdStorageSecondary().equalsIgnoreCase("GB")) {
                            radio_ssd_gb2.setChecked(true);
                            radio_ssd_tb2.setChecked(false);
                        } else {
                            radio_ssd_gb2.setChecked(false);
                            radio_ssd_tb2.setChecked(true);
                        }
                    }
                    ArrayAdapter<String> hddtypeAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, hddTypeList);
                    hddtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_hddtype.setAdapter(hddtypeAdapter);

                    if (ac_shcode.equalsIgnoreCase("DSKT")) {
                        ArrayList<String> keyMouseTypeList = new ArrayList<>();
                        keyMouseTypeList.add(assetdata.getAssetKeyboardType());
                        if (!assetdata.getAssetKeyboardType().equalsIgnoreCase("Wire")) {
                            keyMouseTypeList.add("Wire");
                        }
                        if (!assetdata.getAssetKeyboardType().equalsIgnoreCase("USB")) {
                            keyMouseTypeList.add("USB");
                        }
                        if (!assetdata.getAssetKeyboardType().equalsIgnoreCase("Wireless")) {
                            keyMouseTypeList.add("Wireless");
                        }
                        ArrayAdapter<String> keytypeAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, keyMouseTypeList);
                        keytypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_keyboardtype.setAdapter(keytypeAdapter);

                        ArrayList<String> mouseTypeList = new ArrayList<>();
                        mouseTypeList.add(assetdata.getAssetMouseType());
                        if (!assetdata.getAssetMouseType().equalsIgnoreCase("Wire")) {
                            mouseTypeList.add("Wire");
                        }
                        if (!assetdata.getAssetMouseType().equalsIgnoreCase("USB")) {
                            mouseTypeList.add("USB");
                        }
                        if (!assetdata.getAssetMouseType().equalsIgnoreCase("Wireless")) {
                            mouseTypeList.add("Wireless");
                        }
                        ArrayAdapter<String> mousetypeAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, mouseTypeList);
                        mousetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_mousetype.setAdapter(mousetypeAdapter);
                    }

                    ArrayList<String> conTypeList = new ArrayList<>();
                    conTypeList.add(assetdata.getAssetConnectionType());
                    if (!assetdata.getAssetConnectionType().equalsIgnoreCase("Manual")) {
                        conTypeList.add("Manual");
                    }
                    if (!assetdata.getAssetConnectionType().equalsIgnoreCase("DHCP")) {
                        conTypeList.add("DHCP");
                    }
                    ArrayAdapter<String> contypeAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, conTypeList);
                    contypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_connectiontype.setAdapter(contypeAdapter);

                    ArrayList<String> yesNoList = new ArrayList<>();
                    if (assetdata.getAssetOsIsUpdated().equalsIgnoreCase("1")) {
                        yesNoList.add("Yes");
                    } else {
                        yesNoList.add("No");
                    }
                    //yesNoList.add(assetdata.getAssetOsIsUpdated());
                    if (!assetdata.getAssetOsIsUpdated().equalsIgnoreCase("1")) {
                        yesNoList.add("Yes");
                    }
                    if (!assetdata.getAssetOsIsUpdated().equalsIgnoreCase("0")) {
                        yesNoList.add("No");
                    }
                    ArrayAdapter<String> oslicenceAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, yesNoList);
                    oslicenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_osis_licenced.setAdapter(oslicenceAdapter);

                    ArrayList<String> yesNoList2 = new ArrayList<>();
                    if (assetdata.getAssetIsOffice().equalsIgnoreCase("1")) {
                        yesNoList2.add("Yes");
                        et_officeversion.setText(assetdata.getAssetOfficeVersion());
                    } else {
                        yesNoList2.add("No");
                    }
                    //yesNoList2.add(assetdata.getAssetIsOffice());
                    if (!assetdata.getAssetIsOffice().equalsIgnoreCase("1")) {
                        yesNoList2.add("Yes");
                    }
                    if (!assetdata.getAssetIsOffice().equalsIgnoreCase("0")) {
                        yesNoList2.add("No");
                    }
                    ArrayAdapter<String> officeAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, yesNoList2);
                    officeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_office.setAdapter(officeAdapter);

                    ArrayList<String> yesNoList3 = new ArrayList<>();
                    if (assetdata.getAssetOfficeIsUpdated() != null) {
                        if (assetdata.getAssetOfficeIsUpdated().equalsIgnoreCase("1")) {
                            yesNoList3.add("Yes");
                        } else {
                            yesNoList3.add("No");
                        }
                        //yesNoList3.add(assetdata.getAssetOfficeIsUpdated());
                        if (!assetdata.getAssetOfficeIsUpdated().equalsIgnoreCase("1")) {
                            yesNoList3.add("Yes");
                        }
                        if (!assetdata.getAssetOfficeIsUpdated().equalsIgnoreCase("0")) {
                            yesNoList3.add("No");
                        }
                    } else {
                        yesNoList3.add(SELECT);
                        yesNoList3.add("Yes");
                        yesNoList3.add("No");
                    }
                    ArrayAdapter<String> officelicenceAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, yesNoList3);
                    officelicenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_officelicence.setAdapter(officelicenceAdapter);

                    ArrayList<String> yesNoList4 = new ArrayList<>();
                    if (assetdata.getAssetIsEol().equalsIgnoreCase("1")) {
                        yesNoList4.add("Yes");
                    } else {
                        yesNoList4.add("No");
                    }
                    //yesNoList4.add(assetdata.getAssetIsEol());
                    if (!assetdata.getAssetIsEol().equalsIgnoreCase("1")) {
                        yesNoList4.add("Yes");
                    }
                    if (!assetdata.getAssetIsEol().equalsIgnoreCase("0")) {
                        yesNoList4.add("No");
                    }

                    if (assetdata.getAssetStatus().equalsIgnoreCase("1")) {
                        rb_active.setChecked(true);
                        rb_inactive.setChecked(false);
                    }else {
                        rb_active.setChecked(false);
                        rb_inactive.setChecked(true);
                    }

                    ArrayAdapter<String> eolAdapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, yesNoList4);
                    eolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_eol.setAdapter(eolAdapter);
                    getDropDownList();
                } else {
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AssetDataResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Toast.makeText(EditAssetActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getDropDownList() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<DropDownResponse> call = apiInterface.getDropDownInfo("application/json", "Bearer " + token, assetdata.getAssetAcId());
        call.enqueue(new Callback<DropDownResponse>() {
            @Override
            public void onResponse(@NotNull Call<DropDownResponse> call, @NotNull Response<DropDownResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    dropDownResponse = response.body();
                    SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_ADD_ASDD_OBJ, dropDownResponse);

                    assert dropDownResponse != null;
                    brands = dropDownResponse.getData().getBrandList();
                    ArrayList<String> cpubrandlist = new ArrayList<>();
                    cpubrand_id = assetdata.getAssetBrandId();
                    for (Brand obj : brands) {
                        if (assetdata.getAssetBrandId().equalsIgnoreCase(obj.getBrandId())) {
                            cpubrandlist.add(obj.getBrandName());
                        }
                    }
                    for (Brand obj : brands) {
                        cpubrandlist.add(obj.getBrandName());
                    }
                    ArrayAdapter<String> cpubrandadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, cpubrandlist);
                    cpubrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_cpubrand.setAdapter(cpubrandadapter);

                    List<Data> cpubrandmodels1 = assetDataResponse.getData().getBrandModelList();
                    cpubrandmodels = assetDataResponse.getData().getBrandModelList();

                    cpumodel_id = assetdata.getAssetModelId();
                    for (Data obj : cpubrandmodels1) {
                        if (assetdata.getAssetModelId().equalsIgnoreCase(String.valueOf(obj.getBmId()))) {
                            cpubrandmodelslist.add(obj.getBmName());
                        }
                    }
                    for (Data obj : cpubrandmodels1) {
                        cpubrandmodelslist.add(obj.getBmName());
                        cpuModelsData = new CpuModelsData(obj.getBmId(), obj.getBmName());
                    }
                    ArrayAdapter<String> cpubranmodeldadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, cpubrandmodelslist);
                    cpubranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_cpumodel.setAdapter(cpubranmodeldadapter);

                    if (ac_shcode.equalsIgnoreCase("DSKT")) {
                        ArrayList<String> monitorbrandlist = new ArrayList<>();
                        monitorbrand_id = assetdata.getAssetMonitorBrandId();
                        for (Brand obj : brands) {
                            if (assetdata.getAssetMonitorBrandId().equalsIgnoreCase(obj.getBrandId())) {
                                monitorbrandlist.add(obj.getBrandName());
                            }
                        }
                        for (Brand obj : brands) {
                            monitorbrandlist.add(obj.getBrandName());
                        }
                        ArrayAdapter<String> monitorbrandadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, monitorbrandlist);
                        monitorbrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_monitorbrand.setAdapter(monitorbrandadapter);

                        List<MonitorBrandModel> monitorbrandmodels1 = assetDataResponse.getData().getMonitorBrandModelList();
                        monitormodel_id = assetdata.getAssetMonitorModelId();
                        for (MonitorBrandModel obj : monitorbrandmodels1) {
                            if (assetdata.getAssetMonitorModelId().equalsIgnoreCase(String.valueOf(obj.getBmId()))) {
                                monitorbrandmodelslist.add(obj.getBmName());
                            }
                        }
                        for (MonitorBrandModel obj : monitorbrandmodels1) {
                            monitorbrandmodelslist.add(obj.getBmName());
                            monitorModelsData = new MonitorModelsData(obj.getBmId(), obj.getBmName());
                        }
                        ArrayAdapter<String> monitorbranmodeldadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, monitorbrandmodelslist);
                        monitorbranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_monitormodel.setAdapter(monitorbranmodeldadapter);

                        ArrayList<String> keybrandlist = new ArrayList<>();
                        keybrand_id = assetdata.getAssetKeyboardBrandId();
                        for (Brand obj : brands) {
                            if (assetdata.getAssetKeyboardBrandId().equalsIgnoreCase(obj.getBrandId())) {
                                keybrandlist.add(obj.getBrandName());
                            }
                        }
                        for (Brand obj : brands) {
                            keybrandlist.add(obj.getBrandName());
                        }
                        ArrayAdapter<String> keyboardbrandadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, keybrandlist);
                        keyboardbrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_keyboardbrand.setAdapter(keyboardbrandadapter);

                        List<KeyboardBrandModel> keyboardmodelslist = assetDataResponse.getData().getKeyboardBrandModelList();
                        keyboardmodel_id = assetdata.getAssetKeyboardModelId();
                        for (KeyboardBrandModel obj : keyboardmodelslist) {
                            if (assetdata.getAssetKeyboardModelId().equalsIgnoreCase(String.valueOf(obj.getBmId()))) {
                                keybrandmodelslist.add(obj.getBmName());
                            }
                        }
                        for (KeyboardBrandModel obj : keyboardmodelslist) {
                            keybrandmodelslist.add(obj.getBmName());
                        }
                        ArrayAdapter<String> keyboardmodeladapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, keybrandmodelslist);
                        keyboardmodeladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_keyboardmodel.setAdapter(keyboardmodeladapter);

                        ArrayList<String> mousebrandlist = new ArrayList<>();
                        mousebrand_id = assetdata.getAssetMouseBrandId();
                        for (Brand obj : brands) {
                            if (assetdata.getAssetMouseBrandId().equalsIgnoreCase(obj.getBrandId())) {
                                mousebrandlist.add(obj.getBrandName());
                            }
                        }
                        for (Brand obj : brands) {
                            mousebrandlist.add(obj.getBrandName());
                        }
                        ArrayAdapter<String> mousebrandadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, mousebrandlist);
                        mousebrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_mousebrand.setAdapter(mousebrandadapter);

                        List<MouseBrandModel> mousedmodelslist1 = assetDataResponse.getData().getMouseBrandModelList();
                        mousemodel_id = assetdata.getAssetMouseModelId();
                        for (MouseBrandModel obj : mousedmodelslist1) {
                            if (assetdata.getAssetMouseModelId().equalsIgnoreCase(String.valueOf(obj.getBmId()))) {
                                mousebrandmodelslist.add(obj.getBmName());
                            }
                        }
                        for (KeyboardBrandModel obj : keyboardmodelslist) {
                            mousebrandmodelslist.add(obj.getBmName());
                        }
                        ArrayAdapter<String> mousemodeladapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, mousebrandmodelslist);
                        mousemodeladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_mousemodel.setAdapter(mousemodeladapter);
                    }else {
                        monitorbrand_id = "";
                        monitormodel_id = "";
                        keybrand_id = "";
                        keyboardmodel_id = "";
                        mousebrand_id = "";
                        mousemodel_id = "";
                    }

                    processor = dropDownResponse.getData().getProcessorList();
                    ArrayList<String> processorlist = new ArrayList<>();
                    processor_id = assetdata.getAssetProcessorId();
                    for (Processor obj : processor) {
                        if (assetdata.getAssetProcessorId().equalsIgnoreCase(obj.getProcessorId())) {
                            processorlist.add(obj.getProcessorName());
                        }
                    }
                    for (Processor obj : processor) {
                        processorlist.add(obj.getProcessorName());
                    }
                    ArrayAdapter<String> processorbrandadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, processorlist);
                    processorbrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_processor.setAdapter(processorbrandadapter);

                    ram = dropDownResponse.getData().getRamList();
                    ArrayList<String> ramlist = new ArrayList<>();
                    ramtype_id = assetdata.getAssetRamId();
                    for (Ram obj : ram) {
                        if (assetdata.getAssetRamId().equalsIgnoreCase(obj.getRamId())) {
                            ramlist.add(obj.getRamName());
                        }
                    }
                    for (Ram obj : ram) {
                        ramlist.add(obj.getRamName());
                    }
                    ArrayAdapter<String> ramlisadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, ramlist);
                    ramlisadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_ramtype.setAdapter(ramlisadapter);

                    ramcapacity = dropDownResponse.getData().getRamCapacityList();
                    ArrayList<String> ramcapacitylist = new ArrayList<>();
                    ramcapacity_id = assetdata.getAssetRcId();
                    for (RamCapacity obj : ramcapacity) {
                        if (assetdata.getAssetRcId().equalsIgnoreCase(obj.getRcId())) {
                            ramcapacitylist.add(obj.getRcCapacity());
                        }
                    }
                    for (RamCapacity obj : ramcapacity) {
                        ramcapacitylist.add(obj.getRcCapacity());
                    }
                    ArrayAdapter<String> ramcapacitylisadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, ramcapacitylist);
                    ramcapacitylisadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_ramcapacity.setAdapter(ramcapacitylisadapter);

                    vendor = dropDownResponse.getData().getVendorList();
                    ArrayList<String> vendorlist = new ArrayList<>();
                    vendor_id = assetdata.getAssetVendorId();
                    for (Vendor obj : vendor) {
                        if (assetdata.getAssetVendorId().equalsIgnoreCase(obj.getVendorId())) {
                            vendorlist.add(obj.getVendorName());
                        }
                    }
                    for (Vendor obj : vendor) {
                        vendorlist.add(obj.getVendorName());
                    }
                    ArrayAdapter<String> vendorlistadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, vendorlist);
                    vendorlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_vender.setAdapter(vendorlistadapter);

                    selloc_id = assetdata.getAssetInstallationLocationId();
                    location = dropDownResponse.getData().getLocationList();
                    ArrayList<String> locationlist = new ArrayList<>();
                    selloc_id = assetdata.getAssetInstallationLocationId();
                    for (Location obj : location) {
                        if (assetdata.getAssetInstallationLocationId().equalsIgnoreCase(obj.getLocationId())) {
                            locationlist.add(obj.getLocationName());
                        }
                    }
                    for (Location obj : location) {
                        locationlist.add(obj.getLocationName());
                    }
                    ArrayAdapter<String> locationlistadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, locationlist);
                    locationlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_installationloc.setAdapter(locationlistadapter);

                    seldept_id = assetdata.getAssetInstallationDepartmentId();
                    getDeptListEdit(selloc_id);
                    /*List<Department> departments1 = assetDataResponse.getData().getDepartmentList();
                    //departments = assetDataResponse.getData().getDepartmentList();
                    ArrayList<String> deptlist = new ArrayList<>();
                    for (Department obj : departments1) {
                        if (assetdata.getAssetInstallationDepartmentId().equalsIgnoreCase(String.valueOf(obj.getDepartmentId()))) {
                            deptlist.add(obj.getDepartmentName());
                        }
                    }
                    for (Department obj : departments1) {
                        deptlist.add(obj.getDepartmentName());
                    }
                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_department.setAdapter(deptadapter);*/

                    getSectionListEdit(selloc_id, seldept_id);

                    selsection_id = assetdata.getAssetInstallationSectionId();
                    /*List<Section> sections1 = assetDataResponse.getData().getSectionList();

                    sectionslist.clear();
                    for (Section obj : sections1) {
                        if (assetdata.getAssetInstallationSectionId().equalsIgnoreCase(obj.getDsId())) {
                            sectionslist.add(obj.getDsName());
                        }
                    }
                    for (Section obj : sections1) {
                        sectionslist.add(obj.getDsName());
                    }

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);*/

                    networktype = dropDownResponse.getData().getNetworkTypeList();
                    ArrayList<String> networktypelist = new ArrayList<>();
                    nw_id = assetdata.getAssetNtId();
                    for (NetworkType obj : networktype) {
                        if (assetdata.getAssetNtId().equalsIgnoreCase(obj.getNtId())) {
                            networktypelist.add(obj.getNtName());
                        }
                    }
                    for (NetworkType obj : networktype) {
                        networktypelist.add(obj.getNtName());
                    }
                    ArrayAdapter<String> networktypelistadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, networktypelist);
                    networktypelistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_networktype.setAdapter(networktypelistadapter);

                    operatingSystem = dropDownResponse.getData().getOperatingSystemList();
                    ArrayList<String> oslist = new ArrayList<>();
                    os_id = assetdata.getAssetOsId();
                    for (OperatingSystem obj : operatingSystem) {
                        if (assetdata.getAssetOsId().equalsIgnoreCase(obj.getOsId())) {
                            oslist.add(obj.getOsName());
                        }
                    }
                    for (OperatingSystem obj : operatingSystem) {
                        oslist.add(obj.getOsName());
                    }
                    ArrayAdapter<String> oslistadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, oslist);
                    oslistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_os.setAdapter(oslistadapter);

                    antivirus = dropDownResponse.getData().getAntivirusList();
                    ArrayList<String> antiviruslist = new ArrayList<>();
                    antivirus_id = assetdata.getAssetAntivirusId();
                    if (antivirus_id != null) {
                        for (Antivirus obj : antivirus) {
                            if (antivirus_id.equalsIgnoreCase(obj.getAntivirusId())) {
                                antiviruslist.add(obj.getAntivirusName());
                            }
                        }
                    } else {
                        antiviruslist.add(SELECT);
                        antivirus_id = "";
                    }
                    for (Antivirus obj : antivirus) {
                        antiviruslist.add(obj.getAntivirusName());
                    }
                    ArrayAdapter<String> antiviruslistadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, antiviruslist);
                    antiviruslistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_antivirus.setAdapter(antiviruslistadapter);

                    tv_spinnerbrowser.setText(assetdata.getAssetBrowsers());
                    browserlist = dropDownResponse.getData().getBrowserList().toArray(new String[0]);
                    selectedbrowser = new boolean[browserlist.length];
                    String[] brlist = assetdata.getAssetBrowsers().split(",");

                    for (int i = 0; i < browserlist.length; i++) {
                        if (Arrays.asList(brlist).contains(browserlist[i])) {
                            selectedbrowser[i] = true;
                            browserArray.add(i);
                            //Log.e(TAG, "selectedbrowser :" + selectedbrowser[i]);
                        }
                    }


                    List<String> officetypelist = dropDownResponse.getData().getOfficeTypeList();

                    ArrayAdapter<String> officetypeadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, officetypelist);
                    officetypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_officetype.setAdapter(officetypeadapter);

                    //tv_spinnerothersw.setText(assetdata.getAssetOtherSoftwares());
                    //tv_spinnercustodian.setText(assetdata.getAssetInstallationCustodianIds());
                    String[] custid = assetdata.getAssetInstallationCustodianIds().split(",");
                    String custodianid = null;
                    String assettype = sp_assettype.getSelectedItem().toString();
                    if (assettype.equalsIgnoreCase("Individual")){
                        custodianid = assetdata.getAssetInstallationCustodianIds();
                        spinner_custodian.setVisibility(View.VISIBLE);
                        tv_spinnercustodian.setVisibility(View.GONE);
                    }

                    custodians = assetDataResponse.getData().getCustodianList();
                    String custname = "";

                    ArrayList<String> custodisnlist1 = new ArrayList<>();
                    ArrayList<String> custodisnlist2 = new ArrayList<>();
                    ArrayList<String> custodisnlistid = new ArrayList<>();
                    for (CustodianData obj : custodians) {
                        custodisnlist1.add(obj.getCustodianName());
                        custodisnlistid.add(String.valueOf(obj.getCustodianId()));
                        if (assettype.equalsIgnoreCase("Individual")) {
                            if (custodianid.equalsIgnoreCase(String.valueOf(obj.getCustodianId()))) {
                                custodisnlist2.add(obj.getCustodianName());
                            }
                        }
                    }
                    if (assettype.equalsIgnoreCase("Individual")) {
                        for (CustodianData obj : custodians) {
                            custodisnlist2.add(obj.getCustodianName());
                        }
                        ArrayAdapter<String> custodianapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, custodisnlist2);
                        custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_custodian.setAdapter(custodianapter);
                    }
                    custodianlist = custodisnlist1.toArray(new String[0]);
                    selectedcustodian = new boolean[custodianlist.length];
                    for (int i = 0; i < custodisnlistid.size(); i++) {
                        if (Arrays.asList(custid).contains(custodisnlistid.get(i))) {
                            selectedcustodian[i] = true;
                            custodianArray.add(i);
                            if (custname.equals("")) {
                                custname = custodians.get(i).getCustodianName();
                            } else {
                                custname = custname + "," + custodians.get(i).getCustodianName();
                            }
                            //Log.e(TAG, "selectedbrowser :" + selectedcustodian[i]);
                        }
                    }

                    tv_spinnercustodian.setText(custname);
                    String[] swid = assetdata.getAssetOtherSoftwares().split(",");
                    String swname = "";
                    software = dropDownResponse.getData().getSoftwareList();

                    ArrayList<String> softwarelist1 = new ArrayList<>();
                    ArrayList<String> softwarelistid = new ArrayList<>();
                    softwarelist = new String[0];
                    for (Software obj : software) {
                        softwarelist1.add(obj.getSoftwareName());
                        softwarelistid.add(obj.getSoftwareId());
                    }
                    softwarelist = softwarelist1.toArray(new String[0]);
                    selectedsoftware = new boolean[softwarelist.length];
                    for (int i = 0; i < softwarelistid.size(); i++) {
                        if (Arrays.asList(swid).contains(softwarelistid.get(i))) {
                            selectedsoftware[i] = true;
                            softwareArray.add(i);
                            if (swname.equals("")) {
                                swname = software.get(i).getSoftwareName();
                            } else {
                                swname = swname + "," + software.get(i).getSoftwareName();
                            }
                            //Log.e(TAG, "selectedbrowser :" + selectedsoftware[i]);
                        }
                    }
                    tv_spinnerothersw.setText(swname);
                    sw_id = assetdata.getAssetOtherSoftwares();
                    browsername = assetdata.getAssetBrowsers();
                    cust_id = assetdata.getAssetInstallationCustodianIds();

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<DropDownResponse> call, @NotNull Throwable t) {
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

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
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
                    custodians = custodianResponse.getData();
                    ArrayList<String> custodianlist1 = new ArrayList<>();
                    custodianlist = new String[0];
                    custodianlist1.add(SELECT);
                    for (CustodianData obj : custodians) {
                        custodianlist1.add(obj.getCustodianName());
                    }
                    custodianlist = custodianlist1.toArray(new String[0]);
                    selectedcustodian = new boolean[custodianlist.length];

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
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
                    sections.clear();
                    sections = sectionResponse.getData();
                    sectionslist.clear();
                    sectionslist.add(SELECT);
                    for (SectionData obj : sections) {
                        sectionslist.add(obj.getDsName());
                    }

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
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

    private void getSectionListEdit(String selloc_id, String seldept_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SectionResponse> call = apiInterface.getSectionInfo("application/json", "Bearer " + token, selloc_id, seldept_id);
        call.enqueue(new Callback<SectionResponse>() {
            @Override
            public void onResponse(@NotNull Call<SectionResponse> call, @NotNull Response<SectionResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SectionResponse sectionResponse = response.body();
                    sections = sectionResponse.getData();
                    sectionslist.clear();
                    for (SectionData obj : sections) {
                        if (assetdata.getAssetInstallationSectionId().equalsIgnoreCase(String.valueOf(obj.getDsId()))){
                            sectionslist.add(obj.getDsName());
                        }
                    }

                    for (SectionData obj : sections) {
                        sectionslist.add(obj.getDsName());
                    }

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
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

    private void getDeptList(String loc_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<DepartmentResponse> call = apiInterface.getDeptListInfo("application/json", "Bearer " + token, loc_id);
        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(@NotNull Call<DepartmentResponse> call, @NotNull Response<DepartmentResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    DepartmentResponse departmentResponse = response.body();
                    if (departments!=null) {
                        departments.clear();
                    }
                    departments = departmentResponse.getData();
                    ArrayList<String> deptlist = new ArrayList<>();
                    deptlist.add(SELECT);
                    for (com.vnrseeds.samadhan.parser.departmentparser.Data obj : departments) {
                        deptlist.add(obj.getDepartmentName());
                    }
                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_department.setAdapter(deptadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
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

    private void getDeptListEdit(String loc_id) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<DepartmentResponse> call = apiInterface.getDeptListInfo("application/json", "Bearer " + token, loc_id);
        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(@NotNull Call<DepartmentResponse> call, @NotNull Response<DepartmentResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    DepartmentResponse departmentResponse = response.body();
                    if (departments!=null) {
                        departments.clear();
                    }
                    departments = departmentResponse.getData();
                    ArrayList<String> deptlist = new ArrayList<>();
                    //deptlist.add(SELECT);
                    for (com.vnrseeds.samadhan.parser.departmentparser.Data obj : departments) {
                        if (assetdata.getAssetInstallationDepartmentId().equalsIgnoreCase(String.valueOf(obj.getDepartmentId()))) {
                            deptlist.add(obj.getDepartmentName());
                        }
                    }
                    for (com.vnrseeds.samadhan.parser.departmentparser.Data obj : departments) {
                        deptlist.add(obj.getDepartmentName());
                    }
                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_department.setAdapter(deptadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
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

    private void getCPUModel(String cpubrand_id, String product) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<BrandModelListResponse> call = apiInterface.getModelInfo("application/json", "Bearer " + token, ac_id, cpubrand_id);
        call.enqueue(new Callback<BrandModelListResponse>() {
            @Override
            public void onResponse(@NotNull Call<BrandModelListResponse> call, @NotNull Response<BrandModelListResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    BrandModelListResponse brandModelListResponse = response.body();
                    assert brandModelListResponse != null;
                    cpubrandmodels = brandModelListResponse.getData();
                    if (product.equalsIgnoreCase("cpu")) {
                        for (Data obj : cpubrandmodels) {
                            cpubrandmodelslist.add(obj.getBmName());
                            cpuModelsData = new CpuModelsData(obj.getBmId(), obj.getBmName());
                        }
                        //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_CPUMODELS_OBJ, cpuModelsData);
                        ArrayAdapter<String> cpubranmodeldadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, cpubrandmodelslist);
                        cpubranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_cpumodel.setAdapter(cpubranmodeldadapter);
                    } else if (product.equalsIgnoreCase("keyboard")) {
                        for (Data obj : cpubrandmodels) {
                            keybrandmodelslist.add(obj.getBmName());
                            keyboardModelsData = new KeyboardModelsData(obj.getBmId(), obj.getBmName());
                        }
                        ArrayAdapter<String> keybranmodeldadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, keybrandmodelslist);
                        keybranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_keyboardmodel.setAdapter(keybranmodeldadapter);
                    } else if (product.equalsIgnoreCase("monitor")) {

                        for (Data obj : cpubrandmodels) {
                            monitorbrandmodelslist.add(obj.getBmName());
                            monitorModelsData = new MonitorModelsData(obj.getBmId(), obj.getBmName());
                        }
                        ArrayAdapter<String> monitorbranmodeldadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, monitorbrandmodelslist);
                        monitorbranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_monitormodel.setAdapter(monitorbranmodeldadapter);
                    } else if (product.equalsIgnoreCase("mouse")) {

                        for (Data obj : cpubrandmodels) {
                            mousebrandmodelslist.add(obj.getBmName());
                            mouseModelsData = new MouseModelsData(obj.getBmId(), obj.getBmName());
                        }
                        ArrayAdapter<String> mousebranmodeldadapter = new ArrayAdapter<>(EditAssetActivity.this, android.R.layout.simple_spinner_item, mousebrandmodelslist);
                        mousebranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_mousemodel.setAdapter(mousebranmodeldadapter);
                    }

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditAssetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditAssetActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<BrandModelListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Click here to open camera", "Choose from Library", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditAssetActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditAssetActivity.this);

                if (items[item].equals("Click here to open camera")) {
                    userChoosenTask = "Click here to open camera";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
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
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (new UserPermissions().checkPermission(EditAssetActivity.this, Manifest.permission.CAMERA)) {
                Toast.makeText(EditAssetActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditAssetActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
                return;
            }
        }
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

        if (ImageString.equalsIgnoreCase("srnophoto")) {
            iv_srnophoto.setImageURI(image_uri);
            //path_billcopy = saveImage(thumbnail);
            path_billcopy = getPath(image_uri);
            list.add(prepareFilePart("asset_serial_no_image", Uri.parse(path_billcopy)));

        } else if (ImageString.equalsIgnoreCase("monitorsrnophoto")) {
            iv_monitorsrnophoto.setImageURI(image_uri);
            path_billcopy = getPath(image_uri);
            list.add(prepareFilePart("asset_monitor_serial_no_image", Uri.parse(path_billcopy)));
        }else if (ImageString.equalsIgnoreCase("keyboardsrnophoto")) {
            iv_keysrnophoto.setImageURI(image_uri);
            path_billcopy = getPath(image_uri);
            list.add(prepareFilePart("asset_keyboard_serial_no_image", Uri.parse(path_billcopy)));

        }else if (ImageString.equalsIgnoreCase("mousesrnophoto")) {
            iv_mousesrnophoto.setImageURI(image_uri);
            path_billcopy = getPath(image_uri);
            list.add(prepareFilePart("asset_mouse_serial_no_image", Uri.parse(path_billcopy)));

        }else if (ImageString.equalsIgnoreCase("assetphoto")) {
            iv_assetphoto.setImageURI(image_uri);
            path_billcopy = getPath(image_uri);
            list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));
        }
    }

    public String getPath(Uri image_uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(image_uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getStringFromBitmap(Bitmap bitmap) {
        String imageString = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
        return imageString;
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        Bitmap selectedBitmap = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (ImageString.equalsIgnoreCase("srnophoto")) {
            iv_srnophoto.setImageBitmap(bm);
            assert bm != null;
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_serial_no_image", Uri.parse(path_billcopy)));
        } else if (ImageString.equalsIgnoreCase("monitorsrnophoto")) {
            iv_monitorsrnophoto.setImageBitmap(bm);
            assert bm != null;
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_monitor_serial_no_image", Uri.parse(path_billcopy)));
        }else if (ImageString.equalsIgnoreCase("keyboardsrnophoto")) {
            iv_keysrnophoto.setImageBitmap(bm);
            assert bm != null;
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_keyboard_serial_no_image", Uri.parse(path_billcopy)));

        }else if (ImageString.equalsIgnoreCase("mousesrnophoto")) {
            iv_mousesrnophoto.setImageBitmap(bm);
            assert bm != null;
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_mouse_serial_no_image", Uri.parse(path_billcopy)));

        }else if (ImageString.equalsIgnoreCase("assetphoto")) {
            iv_assetphoto.setImageBitmap(bm);
            assert bm != null;
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==-1 && requestCode==101){
            String result = data.getStringExtra("RESULT");
            Uri resultUri=null;
            if (result!=null){
                resultUri=Uri.parse(result);
                //iv_srnophoto.setImageURI(resultUri);
                File file = new File(String.valueOf(resultUri));
                if (ImageString.equalsIgnoreCase("srnophoto")) {
                    *//*iv_srnophoto.setImageURI(resultUri);
                    //pass it like this
                    requestSrnoFile = RequestBody.create(MediaType.parse("image/*"), file);
                    // MultipartBody.Part is used to send also the actual file name
                    srnoimagebody = MultipartBody.Part.createFormData("asset_serial_no_image", file.getName(), requestSrnoFile);*//*

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_srnophoto.setImageBitmap(bitmap);
                    path_billcopy = saveImage(bitmap);
                    list.add(prepareFilePart("asset_serial_no_image", Uri.parse(path_billcopy)));

                }else if (ImageString.equalsIgnoreCase("monitorsrnophoto")) {
                    *//*iv_srnophoto.setImageURI(resultUri);
                    //pass it like this
                    requestSrnoFile = RequestBody.create(MediaType.parse("image/*"), file);
                    // MultipartBody.Part is used to send also the actual file name
                    srnoimagebody = MultipartBody.Part.createFormData("asset_serial_no_image", file.getName(), requestSrnoFile);*//*

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_monitorsrnophoto.setImageBitmap(bitmap);
                    path_billcopy = saveImage(bitmap);
                    list.add(prepareFilePart("asset_monitor_serial_no_image", Uri.parse(path_billcopy)));

                }else if (ImageString.equalsIgnoreCase("keyboardsrnophoto")) {
                    *//*iv_srnophoto.setImageURI(resultUri);
                    //pass it like this
                    requestSrnoFile = RequestBody.create(MediaType.parse("image/*"), file);
                    // MultipartBody.Part is used to send also the actual file name
                    srnoimagebody = MultipartBody.Part.createFormData("asset_serial_no_image", file.getName(), requestSrnoFile);*//*

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_keysrnophoto.setImageBitmap(bitmap);
                    path_billcopy = saveImage(bitmap);
                    list.add(prepareFilePart("asset_keyboard_serial_no_image", Uri.parse(path_billcopy)));

                }else if (ImageString.equalsIgnoreCase("mousesrnophoto")) {
                    *//*iv_srnophoto.setImageURI(resultUri);
                    //pass it like this
                    requestSrnoFile = RequestBody.create(MediaType.parse("image/*"), file);
                    // MultipartBody.Part is used to send also the actual file name
                    srnoimagebody = MultipartBody.Part.createFormData("asset_serial_no_image", file.getName(), requestSrnoFile);*//*

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_mousesrnophoto.setImageBitmap(bitmap);
                    path_billcopy = saveImage(bitmap);
                    list.add(prepareFilePart("asset_mouse_serial_no_image", Uri.parse(path_billcopy)));

                }else if (ImageString.equalsIgnoreCase("assetphoto")){
                    *//*iv_assetphoto.setImageURI(resultUri);
                    //File file = new File(String.valueOf(resultUri));
                    requestAssetFile = RequestBody.create(MediaType.parse("image/*"), file);
                    assetimagebody = MultipartBody.Part.createFormData("asset_image", file.getName(), requestAssetFile);*//*

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_assetphoto.setImageBitmap(bitmap);
                    path_billcopy = saveImage(bitmap);
                    list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));
                }
            }
        }
    }*/

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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
            MediaScannerConnection.scanFile(EditAssetActivity.this, new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}