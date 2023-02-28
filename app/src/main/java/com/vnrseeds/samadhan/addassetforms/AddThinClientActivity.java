package com.vnrseeds.samadhan.addassetforms;

import static androidx.core.util.PatternsCompat.IP_ADDRESS;

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
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Brand;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.DropDownResponse;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Location;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.NetworkType;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Ram;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.RamCapacity;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Vendor;
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

public class AddThinClientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "AddAssetActivity";
    private LinearLayout primeinfo_form;
    private LinearLayout techinfo_form;
    private LinearLayout purinfo_form;
    private LinearLayout depinfo_form;
    private LinearLayout nwinfo_form;
    private LinearLayout swinfo_form;
    private LinearLayout purinfo_tab;
    private LinearLayout pinfo_tab;
    private LinearLayout depinfo_tab;
    private LinearLayout nwinfo_tab;
    private CustomProgressDialogue customProgressDialogue;
    private DropDownResponse dropDownResponse;
    private String token;
    private ImageButton back_nav;
    private String ac_id;

    private final String[] keyMouseTypeList = {"select", "Wire", "USB", "Wireless"};
    private final String[] yesNoList = {"select", "Yes", "No"};
    private final String SELECT = "select";
    private Spinner sp_assettype;
    private Spinner spinner_keyboardtype;
    private Spinner spinner_mousetype;
    private Spinner spinner_connectiontype;
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
    private List<Brand> brands;
    private Spinner spinner_eol;
    private Spinner spinner_vender;
    private Spinner spinner_installationloc;
    private Spinner spinner_department;
    private Spinner spinner_section;
    //private Spinner spinner_custodian;
    private Spinner spinner_networktype;
    private List<Ram> ram;
    private List<RamCapacity> ramcapacity;
    private List<Vendor> vendor;
    private List<Location> location;
    private List<NetworkType> networktype;
    private String[] custodianlist;
    private List<Data> cpubrandmodels;
    private List<com.vnrseeds.samadhan.parser.departmentparser.Data> departments;
    private String selloc_id;
    private String seldept_id;
    private List<SectionData> sections;
    private String selsection_id;
    private List<CustodianData> custodians;
    private TextView tv_purchasedate;
    private TextView tv_monitorwarrantyupto;
    private TextView tv_keywarrantyupto;
    private TextView tv_mousewarrantyupto;
    private TextView tv_cpuwarrantyupto;
    private TextView tv_eolwarranty_date;
    private TextView tv_installationdate;
    private LinearLayout ll_ipaddress;
    private LinearLayout ll_subnetmask;
    private LinearLayout ll_getway;
    private TextView tv_spinnercustodian;
    boolean[] selectedcustodian;
    ArrayList<Integer> custodianArray = new ArrayList<>();
    private EditText et_remark;
    private EditText et_invoiceno;
    private EditText et_price;
    private EditText et_maethernet;
    private EditText et_mawifi;
    private Button bt_submit;
    private EditText et_ipaddress;
    private EditText et_subnetmask;
    private EditText et_gateway;
    private String cpubrand_id;
    private String monitorbrand_id;
    private String keybrand_id;
    private String mousebrand_id;
    private ArrayList<String> cpubrandmodelslist;
    private ArrayList<String> keybrandmodelslist;
    private ArrayList<String> monitorbrandmodelslist;
    private ArrayList<String> mousebrandmodelslist;
    private CpuModelsData cpuModelsData;
    private MonitorModelsData monitorModelsData;
    private String cpumodel_id;
    private KeyboardModelsData keyboardModelsData;
    private MouseModelsData mouseModelsData;
    private String monitormodel_id;
    private String keyboardmodel_id;
    private String mousemodel_id;
    private String processor_id="";
    private String ramtype_id="";
    private String ramcapacity_id="";
    private String vendor_id;
    private String custodian_id;
    private String nw_id;
    private RadioGroup radiostatus;
    private final String regex = "^([0-9A-Fa-f]{2}[:-])"
            + "{5}([0-9A-Fa-f]{2})|"
            + "([0-9a-fA-F]{4}\\."
            + "[0-9a-fA-F]{4}\\."
            + "[0-9a-fA-F]{4})$";

    private LinearLayout ll_avdates;
    private LinearLayout ll_avsrno;
    private LinearLayout ll_eolupto;
    private Spinner spinner_custodian;
    private ArrayList<String> deptlist;
    private ArrayList<String> sectionslist;
    private EditText et_precise_loc;
    private EditText et_monitorscrsize;
    private SaveAsset saveAsset;

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
        setContentView(R.layout.activity_add_thin_client);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();

    }

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        dropDownResponse = (DropDownResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ADD_ASDD_OBJ, DropDownResponse.class);
        customProgressDialogue = new CustomProgressDialogue(AddThinClientActivity.this);
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

        pinfo_tab = findViewById(R.id.pinfo_tab);
        purinfo_tab = findViewById(R.id.purinfo_tab);
        depinfo_tab = findViewById(R.id.depinfo_tab);
        nwinfo_tab = findViewById(R.id.nwinfo_tab);
        ll_ipaddress = findViewById(R.id.ll_ipaddress);
        ll_subnetmask = findViewById(R.id.ll_subnetmask);
        ll_getway = findViewById(R.id.ll_getway);
        ll_avdates = findViewById(R.id.ll_avdates);
        ll_avsrno = findViewById(R.id.ll_avsrno);
        ll_eolupto = findViewById(R.id.ll_eolupto);
        TextView tv_category = findViewById(R.id.tv_category);
        String category = getIntent().getStringExtra("category");
        String ac_shcode = getIntent().getStringExtra("ac_shcode");
        tv_category.setText(category);
        ac_id = getIntent().getStringExtra("ac_id");
        //Toast.makeText(AddAssetActivity.this, ac_id, Toast.LENGTH_SHORT).show();
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
        et_invoiceno = findViewById(R.id.et_invoiceno);
        et_price = findViewById(R.id.et_price);
        et_maethernet = findViewById(R.id.et_maethernet);
        et_mawifi = findViewById(R.id.et_mawifi);
        et_ipaddress = findViewById(R.id.et_ipaddress);
        et_subnetmask = findViewById(R.id.et_subnetmask);
        et_gateway = findViewById(R.id.et_gateway);
        et_precise_loc = findViewById(R.id.et_precise_loc);
        et_monitorscrsize = findViewById(R.id.et_monitorscrsize);
        et_remark = findViewById(R.id.et_remark);

        spinner_eol = findViewById(R.id.spinner_eol);
        spinner_vender = findViewById(R.id.spinner_vender);
        spinner_installationloc = findViewById(R.id.spinner_installationloc);
        spinner_department = findViewById(R.id.spinner_department);
        spinner_section = findViewById(R.id.spinner_section);
        spinner_networktype = findViewById(R.id.spinner_networktype);
        spinner_custodian = findViewById(R.id.spinner_custodian);

        tv_purchasedate = findViewById(R.id.tv_purchasedate);
        tv_monitorwarrantyupto = findViewById(R.id.tv_monitorwarrantyupto);
        tv_keywarrantyupto = findViewById(R.id.tv_keywarrantyupto);
        tv_mousewarrantyupto = findViewById(R.id.tv_mousewarrantyupto);
        tv_cpuwarrantyupto = findViewById(R.id.tv_cpuwarrantyupto);
        tv_eolwarranty_date = findViewById(R.id.tv_eolwarranty_date);
        tv_installationdate = findViewById(R.id.tv_installationdate);
        tv_spinnercustodian = findViewById(R.id.tv_spinnercustodian);
        bt_submit = findViewById(R.id.bt_submit);
        radiostatus = findViewById(R.id.radiostatus);

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

        sp_assettype = findViewById(R.id.spinner_astype);
        String[] assetTypeList = {"select", "Individual", "Shared", "Group"};
        //sp_assettype.setAdapter(Utils.getInstance().spinnerAdapter(AddAssetActivity.this, Arrays.asList(assetTypeList)));
        ArrayAdapter<String> assettypeAdapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, assetTypeList);
        assettypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_assettype.setAdapter(assettypeAdapter);

        spinner_keyboardtype = findViewById(R.id.spinner_keyboardtype);
        ArrayAdapter<String> keytypeAdapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, keyMouseTypeList);
        keytypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_keyboardtype.setAdapter(keytypeAdapter);

        spinner_mousetype = findViewById(R.id.spinner_mousetype);
        ArrayAdapter<String> mousetypeAdapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, keyMouseTypeList);
        mousetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mousetype.setAdapter(mousetypeAdapter);

        spinner_connectiontype = findViewById(R.id.spinner_connectiontype);
        String[] conTypeList = {"Manual"};
        ArrayAdapter<String> contypeAdapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, conTypeList);
        contypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_connectiontype.setAdapter(contypeAdapter);

        ArrayAdapter<String> eolAdapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, yesNoList);
        eolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_eol.setAdapter(eolAdapter);

        brands = dropDownResponse.getData().getBrandList();
        ArrayList<String> brandlist = new ArrayList<>();
        brandlist.add(SELECT);
        for (Brand obj : brands) {
            brandlist.add(obj.getBrandName());
        }
        ArrayAdapter<String> cpubrandadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, brandlist);
        cpubrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cpubrand.setAdapter(cpubrandadapter);

        ArrayAdapter<String> monitorbrandadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, brandlist);
        monitorbrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_monitorbrand.setAdapter(monitorbrandadapter);

        ArrayAdapter<String> keyboardbrandadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, brandlist);
        keyboardbrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_keyboardbrand.setAdapter(keyboardbrandadapter);

        ArrayAdapter<String> mousebrandadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, brandlist);
        mousebrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mousebrand.setAdapter(mousebrandadapter);

        vendor = dropDownResponse.getData().getVendorList();
        ArrayList<String> vendorlist = new ArrayList<>();
        vendorlist.add(SELECT);
        for (Vendor obj : vendor) {
            vendorlist.add(obj.getVendorName());
        }
        ArrayAdapter<String> vendorlistadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, vendorlist);
        vendorlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vender.setAdapter(vendorlistadapter);

        location = dropDownResponse.getData().getLocationList();
        ArrayList<String> locationlist = new ArrayList<>();
        locationlist.add(SELECT);
        for (Location obj : location) {
            locationlist.add(obj.getLocationName());
        }
        ArrayAdapter<String> locationlistadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, locationlist);
        locationlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_installationloc.setAdapter(locationlistadapter);

        networktype = dropDownResponse.getData().getNetworkTypeList();
        ArrayList<String> networktypelist = new ArrayList<>();
        networktypelist.add(SELECT);
        for (NetworkType obj : networktype) {
            networktypelist.add(obj.getNtName());
        }
        ArrayAdapter<String> networktypelistadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, networktypelist);
        networktypelistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_networktype.setAdapter(networktypelistadapter);

        cpubrandmodelslist = new ArrayList<>();
        cpubrandmodelslist.add(SELECT);
        ArrayAdapter<String> cpubranmodeldadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, cpubrandmodelslist);
        cpubranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cpumodel.setAdapter(cpubranmodeldadapter);

        monitorbrandmodelslist = new ArrayList<>();
        monitorbrandmodelslist.add(SELECT);
        ArrayAdapter<String> monitorbranmodeldadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, monitorbrandmodelslist);
        monitorbranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_monitormodel.setAdapter(monitorbranmodeldadapter);

        keybrandmodelslist = new ArrayList<>();
        keybrandmodelslist.add(SELECT);
        ArrayAdapter<String> keybranmodeldadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, keybrandmodelslist);
        keybranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_keyboardmodel.setAdapter(keybranmodeldadapter);

        mousebrandmodelslist = new ArrayList<>();
        mousebrandmodelslist.add(SELECT);
        ArrayAdapter<String> mousebranmodeldadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, mousebrandmodelslist);
        mousebranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mousemodel.setAdapter(mousebranmodeldadapter);

        deptlist = new ArrayList<>();
        deptlist.add(SELECT);
        ArrayAdapter<String> deptadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, deptlist);
        deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_department.setAdapter(deptadapter);

        sectionslist = new ArrayList<>();
        sectionslist.add(SELECT);
        ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, sectionslist);
        sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_section.setAdapter(sectionadapter);
    }

    private void init() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddThinClientActivity.this, AssetCategorySelectionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv_spinnercustodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddThinClientActivity.this);
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

        pinfo_tab.setOnClickListener(view -> {
            primeinfo_form.setVisibility(View.VISIBLE);
            purinfo_form.setVisibility(View.GONE);
            depinfo_form.setVisibility(View.GONE);
            nwinfo_form.setVisibility(View.GONE);
        });

        purinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.VISIBLE);
                depinfo_form.setVisibility(View.GONE);
                nwinfo_form.setVisibility(View.GONE);
            }
        });

        depinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.VISIBLE);
                nwinfo_form.setVisibility(View.GONE);
            }
        });

        nwinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.GONE);
                nwinfo_form.setVisibility(View.VISIBLE);
            }
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

        spinner_monitormodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    monitormodel_id = String.valueOf(cpubrandmodels.get(i-1).getBmId());
                    //Toast.makeText(AddAssetActivity.this, monitormodel_id, Toast.LENGTH_SHORT).show();
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
                    keyboardmodel_id = String.valueOf(cpubrandmodels.get(i-1).getBmId());
                    //Toast.makeText(AddAssetActivity.this, keyboardmodel_id, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_keyboardtype.setOnItemSelectedListener(AddThinClientActivity.this);
        spinner_mousetype.setOnItemSelectedListener(AddThinClientActivity.this);
        spinner_mousemodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    mousemodel_id = String.valueOf(cpubrandmodels.get(i-1).getBmId());
                    //Toast.makeText(AddAssetActivity.this, mousemodel_id, Toast.LENGTH_SHORT).show();
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
        spinner_connectiontype.setOnItemSelectedListener(AddThinClientActivity.this);
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
                Utils.getInstance().showDatePicker(AddThinClientActivity.this,null,null, new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(AddThinClientActivity.this,null,null, new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(AddThinClientActivity.this,null,null,new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(AddThinClientActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_mousewarrantyupto.setText(date);
                    }
                });
            }
        });
        tv_cpuwarrantyupto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(AddThinClientActivity.this,null,null, new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(AddThinClientActivity.this,null,null, new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(AddThinClientActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_installationdate.setText(date);
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
                String monitorbrand = spinner_monitorbrand.getSelectedItem().toString();
                String monitormodel = spinner_monitormodel.getSelectedItem().toString();
                String monitorsrno = et_monitorsrno.getText().toString().trim();
                String keyboardtype = spinner_keyboardtype.getSelectedItem().toString();
                String keyboardbrand = spinner_keyboardbrand.getSelectedItem().toString();
                String keyboardmodel = spinner_keyboardmodel.getSelectedItem().toString();
                String keyboardsrno = et_keyboardsrno.getText().toString().trim();
                String mousetype = spinner_mousetype.getSelectedItem().toString();
                String mousebrand = spinner_mousebrand.getSelectedItem().toString();
                String mousemodel = spinner_mousemodel.getSelectedItem().toString();
                String mousesrno = et_mousesrno.getText().toString().trim();
                //Tech Conf
                String processor = "";
                String ramcapacity = "";
                String ramtype = "";
                String hddtype = "";
                String hddingb = "";
                String hddingb_sec = "";
                String sddingb = "";
                String sddingb_sec = "";
                //Purchase Info
                String purchasedate = tv_purchasedate.getText().toString().trim();
                String invoiceno = et_invoiceno.getText().toString().trim();
                String monitorwarrantyupto = tv_monitorwarrantyupto.getText().toString().trim();
                String keywarrantyupto = tv_keywarrantyupto.getText().toString().trim();
                String mousewarrantyupto = tv_mousewarrantyupto.getText().toString().trim();
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
                String operatinSystem = "";
                String osis_licenced = "";
                String antivirus = "";
                String avinstall_date = "";
                String avrenew_date = "";
                String avsrno = "";
                String spinnerbrowser = "";
                String office = "";
                String spinnerofficetype = "";
                String officeversion = "";
                String officelicence = "";
                String spinnerothersw = "";
                String remark = et_remark.getText().toString().trim();

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
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select asset type");
                } else if (cpubrand.equalsIgnoreCase(SELECT) || cpubrand.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select CPU brand");
                } else if (cpumodel.equalsIgnoreCase(SELECT) || cpumodel.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select CPU model");
                } else if (cpusrno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Enter CPU serial number");
                } else if (monitorbrand.equalsIgnoreCase(SELECT) || monitorbrand.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select monitor brand");
                } else if (monitorscrsize.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Enter monitor size");
                } else if (monitormodel.equalsIgnoreCase(SELECT) || monitormodel.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select monitor model");
                } else if (monitorsrno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Enter monitor serial number");
                } else if (keyboardtype.equalsIgnoreCase(SELECT) || keyboardtype.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select keyboard type");
                } else if (keyboardbrand.equalsIgnoreCase(SELECT) || keyboardbrand.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select keyboard brand");
                } else if (keyboardmodel.equalsIgnoreCase(SELECT) || keyboardmodel.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select keyboard model");
                } else if (keyboardsrno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Enter keyboard serial number");
                } else if (mousetype.equalsIgnoreCase(SELECT) || mousetype.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select mouse type");
                } else if (mousebrand.equalsIgnoreCase(SELECT) || mousebrand.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select mouse brand");
                } else if (mousemodel.equalsIgnoreCase(SELECT) || mousemodel.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select mouse model");
                } else if (mousesrno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Enter mouse serial number");
                } else if (purchasedate.equalsIgnoreCase("select date") || purchasedate.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select invoice date");
                } else if (invoiceno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Enter invoice number");
                } else if (monitorwarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select monitor warranty date");
                } else if (keywarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select keyboard warranty date");
                } else if (mousewarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select mouse warranty date");
                } else if (cpuwarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select CPU warranty date");
                } else if (eol.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select IS end of life (EOL)");
                } else if (eolwarranty_date.equalsIgnoreCase("select date") && eol.equalsIgnoreCase("Yes")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select EOL date");
                } else if (price.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Enter price");
                } else if (vender.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select vendor");
                } else if (installationdate.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select Installation Date");
                } else if (installationloc.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select Installation location");
                } else if (department.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select Installation department");
                } else if (section.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select section");
                } else if (cust_id==null) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select custodian");
                } else if (networktype.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select networktype");
                } else if (!maethernet.equalsIgnoreCase("") && !matcher1.matches()) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Invalid MAC address ethernet");
                } else if (!mawifi.equalsIgnoreCase("") && !matcher2.matches()) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Invalid MAC address WiFi");
                } else if (connectiontype.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Select connection type");
                } else if (connectiontype.equalsIgnoreCase("Manual") && (ipaddress.equalsIgnoreCase("") || gateway.equalsIgnoreCase("") || subnetmask.equalsIgnoreCase(""))) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Enter IP address, subnet mask and gatway");
                } else if (!ipaddress.equalsIgnoreCase("") && !matcher.matches()) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Invalid IP address");
                } else if (!subnetmask.equalsIgnoreCase("") && !matcherSubnet.matches()) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Invalid subnet mask");
                } else if (!gateway.equalsIgnoreCase("") && !matchergateway.matches()) {
                    Utils.getInstance().showAlert(AddThinClientActivity.this, "Invalid gateway ip");
                } else {
                    int eolvalue = 0;
                    if (eol.equalsIgnoreCase("Yes")) {
                        eolvalue = 1;
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
                    String os_id="";
                    int osislic=0;
                    int officeval=0;
                    int officeupdateval=0;
                    String antivirus_id="";
                    String browsername="", officename="", sw_id="", racksize="";
                    String asset_id="", asset_hdd_storage="", asset_ssd_storage="", asset_hdd_storage2="", asset_ssd_storage2="";
                    String camtype="", camera="", channel="", storage="", printertype="", printerfunction="", cartridgenumber="", brcdconnection_type="", dphn_contype="", deskphonetype="", mic_contype="";
                    String cameraavailable="", sdcardsize="",imeino1="",imeino2="",ownedby="",simoperator="",simno="",mobileno="",screensize="";
                    saveAsset = new SaveAsset(ac_id, asset_id, asset_type, cpubrand_id, cpumodel_id, cpusrno, monitorbrand_id, monitormodel_id, monitorsrno, keyboardtype, keybrand_id,
                            keyboardmodel_id, keyboardsrno, mousetype, mousebrand_id, mousemodel_id, mousesrno, processor_id, ramtype_id, ramcapacity_id, hddtype, hddingb, sddingb,
                            purchasedate, invoiceno, vendor_id, price, cpuwarrantyupto, monitorwarrantyupto, keywarrantyupto, mousewarrantyupto, eolvalue, eolwarranty_date,
                            installationdate, selloc_id, seldept_id, selsection_id, cust_id, nw_id, maethernet, mawifi, connectiontype, ipaddress, subnetmask, gateway,
                            os_id, osislic, antivirus_id, avsrno, avinstall_date, avrenew_date, browsername, officeval, officename, officeupdateval, sw_id, remark, statusval,
                            officeversion, asset_hdd_storage, asset_ssd_storage, hddingb_sec, sddingb_sec, asset_hdd_storage2, asset_ssd_storage2, precise_loc, monitorscrsize,
                            racksize, camtype, camera, channel, storage, printertype, printerfunction, cartridgenumber, brcdconnection_type, dphn_contype, deskphonetype, mic_contype,
                            cameraavailable, sdcardsize,imeino1,imeino2,ownedby,simoperator,simno,mobileno,screensize,"", 0, "speakertype", "noofmic", "switchtype", "switchporttype",
                            "switchlanport", "switchfiberport", "speed", "speedunit", "poewattage", "tvtype", "noofhdmiports", 0, "tvscreensize", "upscategory", "upscategoryunit",
                             "availableport", "portinuse", "eth_ports", "fo_ports", "ssid", 0);

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

    private void submitAssetForm(String asset_type, String cpubrand_id, String cpumodel_id, String cpusrno, String monitorbrand_id, String monitormodel_id, String monitorsrno, String keyboardtype, String keybrand_id, String keyboardmodel_id, String keyboardsrno, String mousetype, String mousebrand_id, String mousemodel_id, String mousesrno, String processor_id, String ramtype_id, String ramcapacity_id, String hddtype, String hddingb, String sddingb, String purchasedate, String invoiceno, String vendor_id, String price, String cpuwarrantyupto, String monitorwarrantyupto, String keywarrantyupto, String mousewarrantyupto, int eolvalue, String eolwarranty_date, String installationdate, String selloc_id, String seldept_id, String selsection_id, String custodian_id, String nw_id, String maethernet, String mawifi, String connectiontype, String ipaddress, String subnetmask, String gateway, String os_id, int osislic, String antivirus_id, String avsrno, String avinstall_date, String avrenew_date, String browsername, int officeval, String officename, int officeupdateval, String sw_id, String remark, int statusval, String officeversion, String hddingb_sec, String sddingb_sec, String precise_loc, String monitorscrsize) {
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
        RequestBody custodian_id1 = RequestBody.create(MediaType.parse("text/plain"), custodian_id);
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
        RequestBody asset_hdd_storage1 = RequestBody.create(MediaType.parse("text/plain"), "asset_hdd_storage");
        RequestBody asset_ssd_storage1 = RequestBody.create(MediaType.parse("text/plain"), "asset_ssd_storage");
        RequestBody asset_hdd_storage21 = RequestBody.create(MediaType.parse("text/plain"), "asset_hdd_storage2");
        RequestBody asset_ssd_storage21 = RequestBody.create(MediaType.parse("text/plain"), "asset_ssd_storage2");
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getSubmitInfo("application/json", "Bearer " + token, ac_id1, asset_type1, cpubrand_id1, cpumodel_id1,
                cpusrno1, monitorbrand_id1, monitormodel_id1, monitorsrno1, keyboardtype1, keybrand_id1,
                keyboardmodel_id1, keyboardsrno1, mousetype1, mousebrand_id1, mousemodel_id1, mousesrno1, processor_id1, ramtype_id1, ramcapacity_id1, hddtype1, hddingb1, sddingb1,
                purchasedate1, invoiceno1, vendor_id1, price1, cpuwarrantyupto1, monitorwarrantyupto1, keywarrantyupto1, mousewarrantyupto1, eolvalue1, eolwarranty_date1,
                installationdate1, selloc_id1, seldept_id1, selsection_id1, custodian_id1, nw_id1, maethernet1, mawifi1, connectiontype1, ipaddress1, subnetmask1, gateway1,
                os_id1, osislic1, antivirus_id1, avsrno1, avinstall_date1, avrenew_date1, browsername1, officeval1, officename1, officeupdateval1, sw_id1, remark1, statusval1, officeversion1,
                asset_hdd_storage1, asset_ssd_storage1, hddingb_sec1, sddingb_sec1, asset_hdd_storage21, asset_ssd_storage21, precise_loc1, monitorscrsize1, list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(AddThinClientActivity.this, MainActivity.class);
                        Toast.makeText(AddThinClientActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddThinClientActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddThinClientActivity.this, msg);
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
                Toast.makeText(AddThinClientActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddThinClientActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddThinClientActivity.this, msg);
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

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddThinClientActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddThinClientActivity.this, msg);
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

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddThinClientActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddThinClientActivity.this, msg);
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

                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_department.setAdapter(deptadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddThinClientActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddThinClientActivity.this, msg);
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
                    cpubrandmodels = brandModelListResponse.getData();

                    if (product.equalsIgnoreCase("cpu")) {
                        for (Data obj : cpubrandmodels) {
                            cpubrandmodelslist.add(obj.getBmName());
                            cpuModelsData = new CpuModelsData(obj.getBmId(), obj.getBmName());
                        }
                        //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_CPUMODELS_OBJ, cpuModelsData);
                        ArrayAdapter<String> cpubranmodeldadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, cpubrandmodelslist);
                        cpubranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_cpumodel.setAdapter(cpubranmodeldadapter);
                    } else if (product.equalsIgnoreCase("keyboard")) {

                        for (Data obj : cpubrandmodels) {
                            keybrandmodelslist.add(obj.getBmName());
                            keyboardModelsData = new KeyboardModelsData(obj.getBmId(), obj.getBmName());
                        }

                        ArrayAdapter<String> keybranmodeldadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, keybrandmodelslist);
                        keybranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_keyboardmodel.setAdapter(keybranmodeldadapter);
                    } else if (product.equalsIgnoreCase("monitor")) {

                        for (Data obj : cpubrandmodels) {
                            monitorbrandmodelslist.add(obj.getBmName());
                            monitorModelsData = new MonitorModelsData(obj.getBmId(), obj.getBmName());
                        }
                        ArrayAdapter<String> monitorbranmodeldadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, monitorbrandmodelslist);
                        monitorbranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_monitormodel.setAdapter(monitorbranmodeldadapter);
                    } else if (product.equalsIgnoreCase("mouse")) {

                        for (Data obj : cpubrandmodels) {
                            mousebrandmodelslist.add(obj.getBmName());
                            mouseModelsData = new MouseModelsData(obj.getBmId(), obj.getBmName());
                        }

                        ArrayAdapter<String> mousebranmodeldadapter = new ArrayAdapter<>(AddThinClientActivity.this, android.R.layout.simple_spinner_item, mousebrandmodelslist);
                        mousebranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_mousemodel.setAdapter(mousebranmodeldadapter);
                    }

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(AddThinClientActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(AddThinClientActivity.this, msg);
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

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AddThinClientActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(AddThinClientActivity.this);

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
            if (new UserPermissions().checkPermission(AddThinClientActivity.this, Manifest.permission.CAMERA)) {
                Toast.makeText(AddThinClientActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddThinClientActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
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
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_monitor_serial_no_image", Uri.parse(path_billcopy)));
        }else if (ImageString.equalsIgnoreCase("keyboardsrnophoto")) {
            iv_keysrnophoto.setImageBitmap(bm);
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_keyboard_serial_no_image", Uri.parse(path_billcopy)));

        }else if (ImageString.equalsIgnoreCase("mousesrnophoto")) {
            iv_mousesrnophoto.setImageBitmap(bm);
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_mouse_serial_no_image", Uri.parse(path_billcopy)));

        }else if (ImageString.equalsIgnoreCase("assetphoto")) {
            iv_assetphoto.setImageBitmap(bm);
            assert bm != null;
            path_billcopy = saveImage(bm);
            list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));
        }

        /*if (ImageString.equalsIgnoreCase("srnophoto")) {
            srnoImageString = getStringFromBitmap(bm);
            iv_srnophoto.setImageBitmap(bm);
        }else if (ImageString.equalsIgnoreCase("assetphoto")){
            assetImageString = getStringFromBitmap(bm);
            iv_assetphoto.setImageBitmap(bm);
        }*/
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
            MediaScannerConnection.scanFile(AddThinClientActivity.this, new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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