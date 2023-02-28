package com.vnrseeds.samadhan.addassetforms;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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

import com.vnrseeds.samadhan.AssetCategorySelectionActivity;
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
import com.vnrseeds.samadhan.parser.brandmodelparser.BrandModelListResponse;
import com.vnrseeds.samadhan.parser.brandmodelparser.CpuModelsData;
import com.vnrseeds.samadhan.parser.brandmodelparser.Data;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianData;
import com.vnrseeds.samadhan.parser.custodianparser.CustodianResponse;
import com.vnrseeds.samadhan.parser.departmentparser.DepartmentResponse;
import com.vnrseeds.samadhan.parser.sectionparser.SectionData;
import com.vnrseeds.samadhan.parser.sectionparser.SectionResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.postclasses.SaveAsset;
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

import static androidx.core.util.PatternsCompat.IP_ADDRESS;

public class LaptopActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "LaptopActivity";
    private LinearLayout primeinfo_form;
    private LinearLayout techinfo_form;
    private LinearLayout purinfo_form;
    private LinearLayout depinfo_form;
    private LinearLayout nwinfo_form;
    private LinearLayout swinfo_form;
    private LinearLayout purinfo_tab;
    private LinearLayout pinfo_tab;
    private LinearLayout techinfo_tab;
    private LinearLayout depinfo_tab;
    private LinearLayout nwinfo_tab;
    private LinearLayout swinfo_tab;
    private CustomProgressDialogue customProgressDialogue;
    private RadioButton radio_hdd_gb, radio_hdd_tb, radio_ssd_gb, radio_ssd_tb;
    private RadioButton radio_hdd_gb2, radio_hdd_tb2, radio_ssd_gb2, radio_ssd_tb2;
    private String asset_ssd_storage = "", asset_hdd_storage = "";
    private String asset_ssd_storage2 = "", asset_hdd_storage2 = "";

    private final String[] yesNoList = {"select", "Yes", "No"};
    private final String SELECT = "select";
    private Spinner sp_assettype;
    private Spinner spinner_hddtype;
    private Spinner spinner_connectiontype;
    private Spinner spinner_osis_licenced;
    private Spinner spinner_office;
    private Spinner spinner_officelicence;
    private Spinner spinner_cpubrand;
    private Spinner spinner_cpumodel;
    private EditText et_cpusrno;
    private List<Brand> brands;
    private String ac_id;
    private Spinner spinner_processor;
    private DropDownResponse dropDownResponse;
    private String token;
    private List<Processor> processor;
    private Spinner spinner_ramcapacity;
    private Spinner spinner_ramtype;
    private Spinner spinner_eol;
    private Spinner spinner_vender;
    private Spinner spinner_installationloc;
    private Spinner spinner_department;
    private Spinner spinner_section;
    //private Spinner spinner_custodian;
    private Spinner spinner_networktype;
    private Spinner spinner_os;
    private Spinner spinner_antivirus;
    private List<Ram> ram;
    private List<RamCapacity> ramcapacity;
    private List<Vendor> vendor;
    private List<Location> location;
    private List<NetworkType> networktype;
    private List<OperatingSystem> operatingSystem;
    private List<Antivirus> antivirus;
    private String[] browserlist;
    private String[] custodianlist;
    private List<Software> software;
    private ArrayList<String> cpubrandmodelslist;
    private List<Data> cpubrandmodels;
    private LinearLayout ll_hddingb;
    private LinearLayout ll_hddingb_sec;
    private LinearLayout ll_ssdingb;
    private LinearLayout ll_ssdingb_sec;
    private List<com.vnrseeds.samadhan.parser.departmentparser.Data> departments;
    private String selloc_id;
    private String seldept_id;
    private List<SectionData> sections;
    private String selsection_id;
    private List<CustodianData> custodians;
    private TextView tv_purchasedate;
    private TextView tv_cpuwarrantyupto;
    private TextView tv_eolwarranty_date;
    private TextView tv_installationdate;
    private TextView tv_avinstall_date;
    private TextView tv_avrenew_date;
    private LinearLayout ll_ipaddress;
    private LinearLayout ll_subnetmask;
    private LinearLayout ll_getway;
    private TextView tv_spinnerbrowser;
    private TextView tv_spinnercustodian;
    boolean[] selectedbrowser;
    ArrayList<Integer> browserArray = new ArrayList<>();
    boolean[] selectedcustodian;
    ArrayList<Integer> custodianArray = new ArrayList<>();
    private String[] softwarelist;
    private TextView tv_spinnerothersw;
    ArrayList<Integer> softwareArray = new ArrayList<>();
    boolean[] selectedsoftware;
    private EditText et_remark;
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
    private Button bt_submit;
    private EditText et_ipaddress;
    private EditText et_subnetmask;
    private EditText et_gateway;
    private String processor_id;
    private String ramtype_id;
    private String ramcapacity_id;
    private String vendor_id;
    private String custodian_id;
    private String nw_id;
    private String os_id;
    private String antivirus_id="";
    private RadioGroup radiostatus;
    private Spinner spinner_officetype;
    private final String regex = "^([0-9A-Fa-f]{2}[:-])"
            + "{5}([0-9A-Fa-f]{2})|"
            + "([0-9a-fA-F]{4}\\."
            + "[0-9a-fA-F]{4}\\."
            + "[0-9a-fA-F]{4})$";
    private ImageButton back_nav;
    private LinearLayout ll_avdates;
    private LinearLayout ll_avsrno;
    private LinearLayout ll_eolupto;
    private LinearLayout ll_officetype;
    private LinearLayout ll_officeversion;
    private Spinner spinner_custodian;
    private ArrayList<String> deptlist;
    private ArrayList<String> sectionslist;
    private String cpumodel_id;
    private String cpubrand_id;
    private CpuModelsData cpuModelsData;
    private SaveAsset saveAsset;
    private EditText et_precise_loc;

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
    private Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        dropDownResponse = (DropDownResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ADD_ASDD_OBJ, DropDownResponse.class);
        customProgressDialogue = new CustomProgressDialogue(LaptopActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.add_asset);
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
        ll_avdates = findViewById(R.id.ll_avdates);
        ll_avsrno = findViewById(R.id.ll_avsrno);
        ll_eolupto = findViewById(R.id.ll_eolupto);
        ll_officetype = findViewById(R.id.ll_officetype);
        ll_officeversion = findViewById(R.id.ll_officeversion);
        TextView tv_category = findViewById(R.id.tv_category);
        String category = getIntent().getStringExtra("category");
        tv_category.setText(category);
        ac_id = getIntent().getStringExtra("ac_id");
        //Toast.makeText(LaptopActivity.this, ac_id, Toast.LENGTH_SHORT).show();
        spinner_cpubrand = findViewById(R.id.spinner_cpubrand);
        spinner_cpumodel = findViewById(R.id.spinner_cpumodel);
        et_cpusrno = findViewById(R.id.et_cpusrno);
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

        spinner_processor = findViewById(R.id.spinner_processor);
        spinner_ramcapacity = findViewById(R.id.spinner_ramcapacity);
        spinner_ramtype = findViewById(R.id.spinner_ramtype);
        spinner_eol = findViewById(R.id.spinner_eol);
        spinner_vender = findViewById(R.id.spinner_vender);
        spinner_installationloc = findViewById(R.id.spinner_installationloc);
        spinner_department = findViewById(R.id.spinner_department);
        spinner_section = findViewById(R.id.spinner_section);
        //spinner_custodian = findViewById(R.id.spinner_custodian);
        spinner_networktype = findViewById(R.id.spinner_networktype);
        spinner_os = findViewById(R.id.spinner_os);
        spinner_antivirus = findViewById(R.id.spinner_antivirus);
        spinner_officetype = findViewById(R.id.spinner_officetype);
        spinner_custodian = findViewById(R.id.spinner_custodian);

        tv_purchasedate = findViewById(R.id.tv_purchasedate);
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

        //Add Photo
        iv_srnophoto = findViewById(R.id.iv_srnophoto);
        iv_assetphoto = findViewById(R.id.iv_assetphoto);
        button_srnophoto = findViewById(R.id.button_srnophoto);
        button_assetphoto = findViewById(R.id.button_assetphoto);

        sp_assettype = findViewById(R.id.spinner_astype);
        String[] assetTypeList = {"select", "Individual", "Shared", "Group"};
        //sp_assettype.setAdapter(Utils.getInstance().spinnerAdapter(AddAssetActivity.this, Arrays.asList(assetTypeList)));
        ArrayAdapter<String> assettypeAdapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, assetTypeList);
        assettypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_assettype.setAdapter(assettypeAdapter);

        spinner_hddtype = findViewById(R.id.spinner_hddtype);
        String[] hddTypeList = {"select", "HDD", "SSD", "BOTH", "HDD Dual", "SSD Dual"};
        ArrayAdapter<String> hddtypeAdapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, hddTypeList);
        hddtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hddtype.setAdapter(hddtypeAdapter);

        spinner_connectiontype = findViewById(R.id.spinner_connectiontype);
        String[] conTypeList = {"select", "Manual", "DHCP"};
        ArrayAdapter<String> contypeAdapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, conTypeList);
        contypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_connectiontype.setAdapter(contypeAdapter);

        spinner_osis_licenced = findViewById(R.id.spinner_osis_licenced);
        ArrayAdapter<String> oslicenceAdapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, yesNoList);
        oslicenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_osis_licenced.setAdapter(oslicenceAdapter);

        spinner_office = findViewById(R.id.spinner_office);
        ArrayAdapter<String> officeAdapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, yesNoList);
        officeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_office.setAdapter(officeAdapter);

        spinner_officelicence = findViewById(R.id.spinner_officelicence);
        ArrayAdapter<String> officelicenceAdapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, yesNoList);
        officelicenceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_officelicence.setAdapter(officelicenceAdapter);

        ArrayAdapter<String> eolAdapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, yesNoList);
        eolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_eol.setAdapter(eolAdapter);

        brands = dropDownResponse.getData().getBrandList();
        ArrayList<String> brandlist = new ArrayList<>();
        brandlist.add(SELECT);
        for (Brand obj : brands) {
            brandlist.add(obj.getBrandName());
        }
        ArrayAdapter<String> cpubrandadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, brandlist);
        cpubrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cpubrand.setAdapter(cpubrandadapter);

        cpubrandmodelslist = new ArrayList<>();
        cpubrandmodelslist.add(SELECT);
        ArrayAdapter<String> cpubranmodeldadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, cpubrandmodelslist);
        cpubranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cpumodel.setAdapter(cpubranmodeldadapter);

        processor = dropDownResponse.getData().getProcessorList();
        ArrayList<String> processorlist = new ArrayList<>();
        processorlist.add(SELECT);
        for (Processor obj : processor) {
            processorlist.add(obj.getProcessorName());
        }
        ArrayAdapter<String> processorbrandadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, processorlist);
        processorbrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_processor.setAdapter(processorbrandadapter);

        ram = dropDownResponse.getData().getRamList();
        ArrayList<String> ramlist = new ArrayList<>();
        ramlist.add(SELECT);
        for (Ram obj : ram) {
            ramlist.add(obj.getRamName());
        }
        ArrayAdapter<String> ramlisadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, ramlist);
        ramlisadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ramtype.setAdapter(ramlisadapter);

        ramcapacity = dropDownResponse.getData().getRamCapacityList();
        ArrayList<String> ramcapacitylist = new ArrayList<>();
        ramcapacitylist.add(SELECT);
        for (RamCapacity obj : ramcapacity) {
            ramcapacitylist.add(obj.getRcCapacity());
        }
        ArrayAdapter<String> ramcapacitylisadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, ramcapacitylist);
        ramcapacitylisadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ramcapacity.setAdapter(ramcapacitylisadapter);

        vendor = dropDownResponse.getData().getVendorList();
        ArrayList<String> vendorlist = new ArrayList<>();
        vendorlist.add(SELECT);
        for (Vendor obj : vendor) {
            vendorlist.add(obj.getVendorName());
        }
        ArrayAdapter<String> vendorlistadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, vendorlist);
        vendorlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vender.setAdapter(vendorlistadapter);

        location = dropDownResponse.getData().getLocationList();
        ArrayList<String> locationlist = new ArrayList<>();
        locationlist.add(SELECT);
        for (Location obj : location) {
            locationlist.add(obj.getLocationName());
        }
        ArrayAdapter<String> locationlistadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, locationlist);
        locationlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_installationloc.setAdapter(locationlistadapter);

        networktype = dropDownResponse.getData().getNetworkTypeList();
        ArrayList<String> networktypelist = new ArrayList<>();
        networktypelist.add(SELECT);
        for (NetworkType obj : networktype) {
            networktypelist.add(obj.getNtName());
        }
        ArrayAdapter<String> networktypelistadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, networktypelist);
        networktypelistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_networktype.setAdapter(networktypelistadapter);

        operatingSystem = dropDownResponse.getData().getOperatingSystemList();
        ArrayList<String> oslist = new ArrayList<>();
        oslist.add(SELECT);
        for (OperatingSystem obj : operatingSystem) {
            oslist.add(obj.getOsName());
        }
        ArrayAdapter<String> oslistadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, oslist);
        oslistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_os.setAdapter(oslistadapter);

        antivirus = dropDownResponse.getData().getAntivirusList();
        ArrayList<String> antiviruslist = new ArrayList<>();
        antiviruslist.add(SELECT);
        for (Antivirus obj : antivirus) {
            antiviruslist.add(obj.getAntivirusName());
        }
        ArrayAdapter<String> antiviruslistadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, antiviruslist);
        antiviruslistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_antivirus.setAdapter(antiviruslistadapter);

        browserlist = dropDownResponse.getData().getBrowserList().toArray(new String[0]);
        selectedbrowser = new boolean[browserlist.length];

        List<String> officetypelist = dropDownResponse.getData().getOfficeTypeList();
        ArrayAdapter<String> officetypeadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, officetypelist);
        officetypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_officetype.setAdapter(officetypeadapter);

        software = dropDownResponse.getData().getSoftwareList();
        ArrayList<String> softwarelist1 = new ArrayList<>();
        softwarelist = new String[0];
        for (Software obj : software) {
            softwarelist1.add(obj.getSoftwareName());
        }
        softwarelist = softwarelist1.toArray(new String[0]);
        selectedsoftware = new boolean[softwarelist.length];

        deptlist = new ArrayList<>();
        deptlist.add(SELECT);
        ArrayAdapter<String> deptadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, deptlist);
        deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_department.setAdapter(deptadapter);

        sectionslist = new ArrayList<>();
        sectionslist.add(SELECT);
        ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, sectionslist);
        sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_section.setAdapter(sectionadapter);
    }

    private void init(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaptopActivity.this, AssetCategorySelectionActivity.class);
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

        techinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                techinfo_form.setVisibility(View.VISIBLE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.GONE);
                nwinfo_form.setVisibility(View.GONE);
                swinfo_form.setVisibility(View.GONE);
            }
        });

        purinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                techinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.VISIBLE);
                depinfo_form.setVisibility(View.GONE);
                nwinfo_form.setVisibility(View.GONE);
                swinfo_form.setVisibility(View.GONE);
            }
        });

        depinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                techinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.VISIBLE);
                nwinfo_form.setVisibility(View.GONE);
                swinfo_form.setVisibility(View.GONE);
            }
        });

        nwinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                techinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.GONE);
                nwinfo_form.setVisibility(View.VISIBLE);
                swinfo_form.setVisibility(View.GONE);
            }
        });

        swinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                techinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.GONE);
                nwinfo_form.setVisibility(View.GONE);
                swinfo_form.setVisibility(View.VISIBLE);
            }
        });

        button_srnophoto.setOnClickListener(view -> {
            ImageString = "srnophoto";
            selectImage();
        });
        button_assetphoto.setOnClickListener(view -> {
            ImageString = "assetphoto";
            selectImage();
        });

        radio_hdd_gb.setChecked(true);
        radio_ssd_gb.setChecked(true);
        tv_spinnerothersw.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(LaptopActivity.this);
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
        });

        tv_spinnercustodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LaptopActivity.this);
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

        tv_spinnerbrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LaptopActivity.this);
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
            }
        });

         sp_assettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String assettype = sp_assettype.getSelectedItem().toString();
                if (assettype.equalsIgnoreCase("Individual")){
                    spinner_custodian.setVisibility(View.VISIBLE);
                    tv_spinnercustodian.setVisibility(View.GONE);
                }else {
                    spinner_custodian.setVisibility(View.GONE);
                    tv_spinnercustodian.setVisibility(View.VISIBLE);
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
        spinner_cpumodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    cpumodel_id = String.valueOf(cpubrandmodels.get(i-1).getBmId());
                    //Toast.makeText(AddAssetActivity.this, cpumodel_id, Toast.LENGTH_SHORT).show();
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
                if (eol.equalsIgnoreCase("Yes")){
                    ll_eolupto.setVisibility(View.VISIBLE);
                }else if (eol.equalsIgnoreCase("No")){
                    ll_eolupto.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_connectiontype.setOnItemSelectedListener(LaptopActivity.this);
        spinner_osis_licenced.setOnItemSelectedListener(LaptopActivity.this);
        spinner_office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String office = spinner_office.getSelectedItem().toString();
                if (office.equalsIgnoreCase("Yes")){
                    ll_officetype.setVisibility(View.VISIBLE);
                    ll_officeversion.setVisibility(View.VISIBLE);
                }else if (office.equalsIgnoreCase("No")){
                    ll_officetype.setVisibility(View.GONE);
                    ll_officeversion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_officelicence.setOnItemSelectedListener(LaptopActivity.this);
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
                    antivirus_id = "";
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
                } else if (hddtype.equalsIgnoreCase("BOTH")) {
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
                }else {
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
                    deptlist.clear();
                    getDeptList(selloc_id);
                    deptlist.add(SELECT);
                    String assettype = sp_assettype.getSelectedItem().toString();
                    if (!assettype.equalsIgnoreCase("Individual") && !assettype.equalsIgnoreCase(SELECT)) {
                        custodianArray.clear();
                        tv_spinnercustodian.setText("");
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
                    sectionslist.clear();
                    sectionslist.add(SELECT);
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
        spinner_custodian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                custodian_id = String.valueOf(custodians.get(i).getCustodianId());
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
                Utils.getInstance().showDatePicker(LaptopActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_purchasedate.setText(date);
                    }
                });
            }
        });
        tv_cpuwarrantyupto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(LaptopActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_cpuwarrantyupto.setText(date);
                    }
                });
            }
        });

        tv_eolwarranty_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(LaptopActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_eolwarranty_date.setText(date);
                    }
                });
            }
        });
        tv_installationdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(LaptopActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_installationdate.setText(date);
                    }
                });
            }
        });
        tv_avinstall_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(LaptopActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_avinstall_date.setText(date);
                    }
                });
            }
        });
        tv_avrenew_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(LaptopActivity.this,null,null, new DateCommunicator() {
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
                String cpuwarrantyupto = tv_cpuwarrantyupto.getText().toString().trim();;
                String eol = spinner_eol.getSelectedItem().toString();
                String eolwarranty_date = tv_eolwarranty_date.getText().toString();
                //Dept Info
                String price = et_price.getText().toString().trim();
                String precise_loc = et_precise_loc.getText().toString().trim();
                String vender = spinner_vender.getSelectedItem().toString();
                String installationdate = tv_installationdate.getText().toString().trim();
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
                String cust_id = null;
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
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select asset type");
                } else if (processor.equalsIgnoreCase(SELECT) || processor.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select processor brand");
                } else if (ramcapacity.equalsIgnoreCase(SELECT) || ramcapacity.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select ram capacity");
                } else if (ramtype.equalsIgnoreCase(SELECT) || ramtype.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select ram type");
                } else if (hddtype.equalsIgnoreCase(SELECT) || hddtype.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select HDD type");
                } else if (hddtype.equalsIgnoreCase("HDD") && hddingb.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter HDD (in GB)");
                }else if (hddtype.equalsIgnoreCase("HDD Dual") && (hddingb.equalsIgnoreCase("") || hddingb_sec.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter HDD (in GB)");
                } else if (hddtype.equalsIgnoreCase("SSD") && sddingb.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter SSD (in GB)");
                } else if (hddtype.equalsIgnoreCase("SSD Dual") && (sddingb.equalsIgnoreCase("") || sddingb_sec.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter SSD (in GB)");
                } else if (hddtype.equalsIgnoreCase("BOTH") && (sddingb.equalsIgnoreCase("") || hddingb.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter HDD (in GB) and SSD (in GB)");
                } else if (purchasedate.equalsIgnoreCase("select date") || purchasedate.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select invoice date");
                } else if (invoiceno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter invoice number");
                } else if (eol.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select IS end of life (EOL)");
                } else if (eolwarranty_date.equalsIgnoreCase("select date") && eol.equalsIgnoreCase("Yes")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select EOL date");
                } else if (price.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter price");
                } else if (vender.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select vendor");
                } else if (installationdate.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select Installation Date");
                } else if (installationloc.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select Installation location");
                } else if (department.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select Installation department");
                } else if (section.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select section");
                } else if (cust_id==null) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select custodian");
                } else if (networktype.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select networktype");
                } else if (!maethernet.equalsIgnoreCase("") && !matcher1.matches()) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Invalid MAC address ethernet");
                } else if (!mawifi.equalsIgnoreCase("") && !matcher2.matches()) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Invalid MAC address WiFi");
                } else if (connectiontype.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select connection type");
                } else if (connectiontype.equalsIgnoreCase("Manual") && (ipaddress.equalsIgnoreCase("") || gateway.equalsIgnoreCase("") || subnetmask.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter IP address, subnet mask and gatway");
                } else if (!ipaddress.equalsIgnoreCase("") && !matcher.matches()) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Invalid IP address");
                } else if (!subnetmask.equalsIgnoreCase("") && !matcherSubnet.matches()) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Invalid subnet mask");
                } else if (!gateway.equalsIgnoreCase("") && !matchergateway.matches()) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Invalid gateway ip");
                } else if (operatinSystem.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select operating system");
                } else if (osis_licenced.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select OS update");
                } else if (antivirus.equalsIgnoreCase("Yes") && avinstall_date.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select antivirus install date");
                } else if (antivirus.equalsIgnoreCase("Yes") && avrenew_date.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select antivirus renewal date");
                } else if (antivirus.equalsIgnoreCase("Yes") && avsrno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Enter antivirus serial number");
                } else if (spinnerbrowser.equalsIgnoreCase("select") || spinnerbrowser.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select browser");
                } else if (office.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select Office");
                }else if (office.equalsIgnoreCase("Yes") && spinnerofficetype.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select Office type");
                } else if (office.equalsIgnoreCase("Yes") && officeversion.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select office version");
                } else if (office.equalsIgnoreCase("Yes") && officelicence.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(LaptopActivity.this, "Select office update");
                } else {
                    if (hddtype.equalsIgnoreCase("BOTH")) {
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
                    }else if (hddtype.equalsIgnoreCase("SSD Dual")) {
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
                    String browsername = spinnerbrowser;
                    String officename = spinnerofficetype;
                    String sw_id = null;
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

                    String monitorbrand_id="";
                    String monitormodel_id="";
                    String keybrand_id="";
                    String keyboardmodel_id="";
                    String mousebrand_id="";
                    String mousemodel_id="";
                    String monitorscrsize = "";
                    String racksize="";
                    String camtype="", camera="", channel="", storage="", printertype="", printerfunction="", cartridgenumber="", brcdconnection_type="", dphn_contype="", deskphonetype="", mic_contype="";
                    String cameraavailable="", sdcardsize="",imeino1="",imeino2="",ownedby="",simoperator="",simno="",mobileno="",screensize="";
                    String asset_id = "";
                    saveAsset = new SaveAsset(ac_id, asset_id, asset_type, cpubrand_id, cpumodel_id, cpusrno, monitorbrand_id, monitormodel_id, monitorsrno, keyboardtype, keybrand_id,
                            keyboardmodel_id, keyboardsrno, mousetype, mousebrand_id, mousemodel_id, mousesrno, processor_id, ramtype_id, ramcapacity_id, hddtype, hddingb, sddingb,
                            purchasedate, invoiceno, vendor_id, price, cpuwarrantyupto, monitorwarrantyupto, keywarrantyupto, mousewarrantyupto, eolvalue, eolwarranty_date,
                            installationdate, selloc_id, seldept_id, selsection_id, cust_id, nw_id, maethernet, mawifi, connectiontype, ipaddress, subnetmask, gateway,
                            os_id, osislic, antivirus_id, avsrno, avinstall_date, avrenew_date, browsername, officeval, officename, officeupdateval, sw_id, remark, statusval,
                            officeversion, asset_hdd_storage, asset_ssd_storage, hddingb_sec, sddingb_sec, asset_hdd_storage2, asset_ssd_storage2, precise_loc, monitorscrsize,
                            racksize, camtype, camera, channel, storage, printertype, printerfunction, cartridgenumber, brcdconnection_type, dphn_contype, deskphonetype, mic_contype,
                            cameraavailable, sdcardsize,imeino1,imeino2,ownedby,simoperator,simno,mobileno,screensize,"", 0, "speakertype",
                            "noofmic", "switchtype", "switchporttype", "switchlanport", "switchfiberport",
                            "speed", "speedunit", "poewattage", "tvtype", "noofhdmiports", 0,
                            "tvscreensize", "upscategory", "upscategoryunit", "availableport", "portinuse",
                            "eth_ports", "fo_ports", "ssid", 0);

                    submitAssetForm(asset_type, cpubrand_id, cpumodel_id, cpusrno, monitorbrand_id, monitormodel_id, monitorsrno, keyboardtype, keybrand_id,
                            keyboardmodel_id, keyboardsrno, mousetype, mousebrand_id, mousemodel_id, mousesrno, processor_id, ramtype_id, ramcapacity_id, hddtype, hddingb, sddingb,
                            purchasedate, invoiceno, vendor_id, price, cpuwarrantyupto, monitorwarrantyupto, keywarrantyupto, mousewarrantyupto, eolvalue, eolwarranty_date,
                            installationdate, selloc_id, seldept_id, selsection_id, cust_id, nw_id, maethernet, mawifi, connectiontype, ipaddress, subnetmask, gateway,
                            os_id, osislic, antivirus_id, avsrno, avinstall_date, avrenew_date, browsername, officeval, officename, officeupdateval, sw_id, remark, statusval,
                            officeversion, hddingb_sec, sddingb_sec, precise_loc, monitorscrsize);
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
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getSubmitInfo("application/json", "Bearer " + token, ac_id1, asset_type1, cpubrand_id1, cpumodel_id1,
                cpusrno1, monitorbrand_id1, monitormodel_id1, monitorsrno1, keyboardtype1, keybrand_id1, keyboardmodel_id1, keyboardsrno1, mousetype1, mousebrand_id1,
                mousemodel_id1, mousesrno1, processor_id1, ramtype_id1, ramcapacity_id1, hddtype1, hddingb1, sddingb1, purchasedate1, invoiceno1, vendor_id1, price1,
                cpuwarrantyupto1, monitorwarrantyupto1, keywarrantyupto1, mousewarrantyupto1, eolvalue1, eolwarranty_date1, installationdate1, selloc_id1, seldept_id1,
                selsection_id1, custodian_id1, nw_id1, maethernet1, mawifi1, connectiontype1, ipaddress1, subnetmask1, gateway1, os_id1, osislic1, antivirus_id1, avsrno1,
                avinstall_date1, avrenew_date1, browsername1, officeval1, officename1, officeupdateval1, sw_id1, remark1, statusval1, officeversion1, asset_hdd_storage1,
                asset_ssd_storage1, hddingb_sec1, sddingb_sec1, asset_hdd_storage21, asset_ssd_storage21, precise_loc1, monitorscrsize1, list);

        //Call<SubmitSuccessResponse> call = apiInterface.getLaptopSubmitInfo("application/json", "Bearer " + token, saveAsset);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(LaptopActivity.this, MainActivity.class);
                        Toast.makeText(LaptopActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(LaptopActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(LaptopActivity.this, msg);
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
                Toast.makeText(LaptopActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    cpubrandmodels = brandModelListResponse.getData();

                    for (Data obj : cpubrandmodels) {
                        cpubrandmodelslist.add(obj.getBmName());
                        cpuModelsData = new CpuModelsData(obj.getBmId(), obj.getBmName());
                    }
                    //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_CPUMODELS_OBJ, cpuModelsData);
                    ArrayAdapter<String> cpubranmodeldadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, cpubrandmodelslist);
                    cpubranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_cpumodel.setAdapter(cpubranmodeldadapter);

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(LaptopActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(LaptopActivity.this, msg);
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
                    custodians = custodianResponse.getData();
                    ArrayList<String> custodianlist1 = new ArrayList<>();
                    custodianlist = new String[0];
                    for (CustodianData obj : custodians) {
                        custodianlist1.add(obj.getCustodianName());
                    }
                    custodianlist = custodianlist1.toArray(new String[0]);
                    selectedcustodian = new boolean[custodianlist.length];

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(LaptopActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(LaptopActivity.this, msg);
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
                    for (CustodianData obj : custodians) {
                        custodianlist1.add(obj.getCustodianName());
                    }
                    custodianlist = custodianlist1.toArray(new String[0]);
                    selectedcustodian = new boolean[custodianlist.length];

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(LaptopActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(LaptopActivity.this, msg);
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
                    sections = sectionResponse.getData();

                    for (SectionData obj : sections) {
                        sectionslist.add(obj.getDsName());
                    }

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(LaptopActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(LaptopActivity.this, msg);
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
                    departments = departmentResponse.getData();
                    for (com.vnrseeds.samadhan.parser.departmentparser.Data obj : departments) {
                        deptlist.add(obj.getDepartmentName());
                    }

                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(LaptopActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_department.setAdapter(deptadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(LaptopActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(LaptopActivity.this, msg);
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

    private void selectImage() {
        final CharSequence[] items = {"Click here to open camera", "Choose from Library", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LaptopActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(LaptopActivity.this);

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
            if (new UserPermissions().checkPermission(LaptopActivity.this, Manifest.permission.CAMERA)) {
                Toast.makeText(LaptopActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LaptopActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
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

        } else if (ImageString.equalsIgnoreCase("assetphoto")) {
            iv_assetphoto.setImageURI(image_uri);
            //path_billcopy = saveImage(thumbnail);
            path_billcopy = getPath(image_uri);
            list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));
        }

        //Toast.makeText(AddRetailerActivity.this, retImageString, Toast.LENGTH_SHORT).show();
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

        } else if (ImageString.equalsIgnoreCase("assetphoto")) {
            iv_assetphoto.setImageBitmap(bm);
            assert bm != null;
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));
        }

        if (ImageString.equalsIgnoreCase("srnophoto")) {
            srnoImageString = getStringFromBitmap(bm);
            iv_srnophoto.setImageBitmap(bm);
        }else if (ImageString.equalsIgnoreCase("assetphoto")){
            assetImageString = getStringFromBitmap(bm);
            iv_assetphoto.setImageBitmap(bm);
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
                    list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));

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
                    list.add(prepareFilePart("asset_serial_no_image", Uri.parse(path_billcopy)));
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
            MediaScannerConnection.scanFile(LaptopActivity.this, new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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