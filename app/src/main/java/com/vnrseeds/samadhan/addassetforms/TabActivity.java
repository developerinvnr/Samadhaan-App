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
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Brand;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.DropDownResponse;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Location;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.NetworkType;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.OperatingSystem;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Processor;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.RamCapacity;
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

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "TabActivity";
    private static final String SELECT = "select";
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
    private DropDownResponse dropDownResponse;
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private String ac_id;
    private String ac_shcode;

    //Primary Info
    private Spinner spinner_brand;
    private Spinner spinner_model;
    private Spinner spinner_astype;
    private EditText et_srno;
    private List<Brand> brands;
    private ArrayList<String> brandmodelslist;
    private String brand_id;
    private String model_id;
    private List<Data> brandmodels;
    //Technical Info
    private Spinner spinner_processor;
    private Spinner spinner_camera;
    private Spinner spinner_ramcapacity;
    private EditText et_storagecapacity;
    private EditText et_sdcardsize;
    private EditText et_imeino1;
    private EditText et_imeino2;
    private Spinner spinner_ownedby;
    private Spinner spinner_simoperator;
    private EditText et_simno;
    private EditText et_mobileno;
    private EditText et_screensize;
    private EditText et_color;
    private List<Processor> processor;
    private List<RamCapacity> ramcapacity;
    private String processor_id="";
    private String ramcapacity_id="";
    private LinearLayout ll_simownedby;
    private LinearLayout ll_simno;
    //Purchase Info
    private TextView tv_purchasedate;
    private TextView tv_warrantyupto;
    private EditText et_price;
    private EditText et_invoiceno;
    private Spinner spinner_vender;
    private List<Vendor> vendor;
    private Spinner spinner_eol;
    private TextView tv_eolwarranty_date;
    private LinearLayout ll_eolupto;
    //Deployment Info
    private TextView tv_installationdate;
    private Spinner spinner_installationloc;
    private Spinner spinner_department;
    private Spinner spinner_section;
    private Spinner spinner_custodian;
    private TextView tv_spinnercustodian;
    private List<Location> location;
    private ArrayList<String> deptlist;
    private ArrayList<String> sectionslist;
    private String[] custodianlist;
    boolean[] selectedcustodian;
    ArrayList<Integer> custodianArray = new ArrayList<>();
    private EditText et_precise_loc;
    private String custodian_id;
    private List<CustodianData> custodians;
    private String selloc_id;
    private String seldept_id;
    private String selsection_id;
    private CpuModelsData modelsData;
    //Network Info
    private EditText et_maethernet;
    private EditText et_mawifi;
    private EditText et_ipaddress;
    private EditText et_subnetmask;
    private EditText et_gateway;
    private Spinner spinner_connectiontype;
    private LinearLayout ll_ipaddress;
    private LinearLayout ll_subnetmask;
    private LinearLayout ll_getway;
    private Spinner spinner_networktype;
    private String nw_id="";
    private List<NetworkType> networktype;
    private final String regex = "^([0-9A-Fa-f]{2}[:-])"
            + "{5}([0-9A-Fa-f]{2})|"
            + "([0-9a-fA-F]{4}\\."
            + "[0-9a-fA-F]{4}\\."
            + "[0-9a-fA-F]{4})$";

    private EditText et_remark;
    private Button bt_submit;
    private RadioGroup radiostatus;
    private String vendor_id;
    private SaveAsset saveAsset;
    private List<com.vnrseeds.samadhan.parser.departmentparser.Data> departments;
    private List<SectionData> sections;
    private final String[] yesNoList = {"select", "Yes", "No"};
    //Software Info
    private List<OperatingSystem> operatingSystem;
    private Spinner spinner_ostype;
    private String os_id="";

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
        setContentView(R.layout.activity_tab);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    private void setTheme() {
        TextView tv_category = findViewById(R.id.tv_category);
        String category = getIntent().getStringExtra("category");
        ac_shcode = getIntent().getStringExtra("ac_shcode");
        tv_category.setText(category);
        ac_id = getIntent().getStringExtra("ac_id");

        dropDownResponse = (DropDownResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ADD_ASDD_OBJ, DropDownResponse.class);
        customProgressDialogue = new CustomProgressDialogue(TabActivity.this);
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
        techinfo_tab = findViewById(R.id.techinfo_tab);
        purinfo_tab = findViewById(R.id.purinfo_tab);
        depinfo_tab = findViewById(R.id.depinfo_tab);
        nwinfo_tab = findViewById(R.id.nwinfo_tab);
        swinfo_tab = findViewById(R.id.swinfo_tab);

        spinner_processor = findViewById(R.id.spinner_processor);
        spinner_camera = findViewById(R.id.spinner_camera);
        spinner_ramcapacity = findViewById(R.id.spinner_ramcapacity);
        et_storagecapacity = findViewById(R.id.et_storagecapacity);
        et_sdcardsize = findViewById(R.id.et_sdcardsize);
        et_imeino1 = findViewById(R.id.et_imeino1);
        et_imeino2 = findViewById(R.id.et_imeino2);
        spinner_ownedby = findViewById(R.id.spinner_ownedby);
        spinner_simoperator = findViewById(R.id.spinner_simoperator);
        et_simno = findViewById(R.id.et_simno);
        et_mobileno = findViewById(R.id.et_mobileno);
        et_screensize = findViewById(R.id.et_screensize);
        et_color = findViewById(R.id.et_color);

        ll_ipaddress = findViewById(R.id.ll_ipaddress);
        ll_subnetmask = findViewById(R.id.ll_subnetmask);
        ll_getway = findViewById(R.id.ll_getway);
        ll_eolupto = findViewById(R.id.ll_eolupto);

        ll_simownedby = findViewById(R.id.ll_simownedby);
        ll_simno = findViewById(R.id.ll_simno);

        spinner_brand = findViewById(R.id.spinner_brand);
        spinner_model = findViewById(R.id.spinner_model);
        et_srno = findViewById(R.id.et_srno);
        et_invoiceno = findViewById(R.id.et_invoiceno);
        et_price = findViewById(R.id.et_price);
        et_maethernet = findViewById(R.id.et_maethernet);
        et_mawifi = findViewById(R.id.et_mawifi);
        et_ipaddress = findViewById(R.id.et_ipaddress);
        et_subnetmask = findViewById(R.id.et_subnetmask);
        et_gateway = findViewById(R.id.et_gateway);
        et_remark = findViewById(R.id.et_remark);
        et_precise_loc = findViewById(R.id.et_precise_loc);

        spinner_eol = findViewById(R.id.spinner_eol);
        spinner_vender = findViewById(R.id.spinner_vender);
        spinner_installationloc = findViewById(R.id.spinner_installationloc);
        spinner_department = findViewById(R.id.spinner_department);
        spinner_section = findViewById(R.id.spinner_section);
        spinner_custodian = findViewById(R.id.spinner_custodian);
        spinner_networktype = findViewById(R.id.spinner_networktype);
        spinner_ostype = findViewById(R.id.spinner_ostype);

        tv_purchasedate = findViewById(R.id.tv_purchasedate);
        tv_warrantyupto = findViewById(R.id.tv_warrantyupto);
        tv_eolwarranty_date = findViewById(R.id.tv_eolwarranty_date);
        tv_installationdate = findViewById(R.id.tv_installationdate);
        tv_spinnercustodian = findViewById(R.id.tv_spinnercustodian);
        bt_submit = findViewById(R.id.bt_submit);
        radiostatus = findViewById(R.id.radiostatus);

        //Add Photo
        iv_srnophoto = findViewById(R.id.iv_srnophoto);
        iv_assetphoto = findViewById(R.id.iv_assetphoto);
        button_srnophoto = findViewById(R.id.button_srnophoto);
        button_assetphoto = findViewById(R.id.button_assetphoto);

        if (ac_shcode.equalsIgnoreCase("TBLT")){
            ll_simownedby.setVisibility(View.GONE);
            ll_simno.setVisibility(View.GONE);
        }

        spinner_astype = findViewById(R.id.spinner_astype);
        String[] assetTypeList = {"select", "Individual", "Shared", "Group"};
        //sp_assettype.setAdapter(Utils.getInstance().spinnerAdapter(AddAssetActivity.this, Arrays.asList(assetTypeList)));
        ArrayAdapter<String> assettypeAdapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, assetTypeList);
        assettypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_astype.setAdapter(assettypeAdapter);

        spinner_connectiontype = findViewById(R.id.spinner_connectiontype);
        String[] conTypeList = {"select", "Manual", "DHCP"};
        ArrayAdapter<String> contypeAdapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, conTypeList);
        contypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_connectiontype.setAdapter(contypeAdapter);

        ArrayAdapter<String> eolAdapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, yesNoList);
        eolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_eol.setAdapter(eolAdapter);

        processor = dropDownResponse.getData().getProcessorList();
        ArrayList<String> processorlist = new ArrayList<>();
        processorlist.add(SELECT);
        for (Processor obj : processor) {
            processorlist.add(obj.getProcessorName());
        }
        ArrayAdapter<String> processorbrandadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, processorlist);
        processorbrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_processor.setAdapter(processorbrandadapter);

        ramcapacity = dropDownResponse.getData().getRamCapacityList();
        ArrayList<String> ramcapacitylist = new ArrayList<>();
        ramcapacitylist.add(SELECT);
        for (RamCapacity obj : ramcapacity) {
            ramcapacitylist.add(obj.getRcCapacity());
        }
        ArrayAdapter<String> ramcapacitylisadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, ramcapacitylist);
        ramcapacitylisadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ramcapacity.setAdapter(ramcapacitylisadapter);

        List<String> cameralist = dropDownResponse.getData().getCameraAvailableList();
        ArrayAdapter<String> cameraadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, cameralist);
        cameraadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_camera.setAdapter(cameraadapter);

        if (!ac_shcode.equalsIgnoreCase("TBLT")) {
            List<String> simownedbylist = dropDownResponse.getData().getOwnedByList();
            ArrayAdapter<String> simownedbyadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, simownedbylist);
            simownedbyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_ownedby.setAdapter(simownedbyadapter);

            List<String> simbrandlist = dropDownResponse.getData().getsIMBrandList();
            ArrayAdapter<String> simbrandadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, simbrandlist);
            simbrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_simoperator.setAdapter(simbrandadapter);
        }

        brands = dropDownResponse.getData().getBrandList();
        ArrayList<String> brandlist = new ArrayList<>();
        brandlist.add(SELECT);
        for (Brand obj : brands) {
            brandlist.add(obj.getBrandName());
        }
        ArrayAdapter<String> cpubrandadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, brandlist);
        cpubrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_brand.setAdapter(cpubrandadapter);

        brandmodelslist = new ArrayList<>();
        brandmodelslist.add(SELECT);
        ArrayAdapter<String> cpubranmodeldadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, brandmodelslist);
        cpubranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_model.setAdapter(cpubranmodeldadapter);

        vendor = dropDownResponse.getData().getVendorList();
        ArrayList<String> vendorlist = new ArrayList<>();
        vendorlist.add(SELECT);
        for (Vendor obj : vendor) {
            vendorlist.add(obj.getVendorName());
        }
        ArrayAdapter<String> vendorlistadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, vendorlist);
        vendorlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vender.setAdapter(vendorlistadapter);

        location = dropDownResponse.getData().getLocationList();
        ArrayList<String> locationlist = new ArrayList<>();
        locationlist.add(SELECT);
        for (Location obj : location) {
            locationlist.add(obj.getLocationName());
        }
        ArrayAdapter<String> locationlistadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, locationlist);
        locationlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_installationloc.setAdapter(locationlistadapter);

        deptlist = new ArrayList<>();
        deptlist.add(SELECT);
        ArrayAdapter<String> deptadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, deptlist);
        deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_department.setAdapter(deptadapter);

        sectionslist = new ArrayList<>();
        sectionslist.add(SELECT);
        ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, sectionslist);
        sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_section.setAdapter(sectionadapter);

        operatingSystem = dropDownResponse.getData().getOperatingSystemList();
        ArrayList<String> oslist = new ArrayList<>();
        oslist.add(SELECT);
        for (OperatingSystem obj : operatingSystem) {
            oslist.add(obj.getOsName());
        }
        ArrayAdapter<String> oslistadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, oslist);
        oslistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ostype.setAdapter(oslistadapter);
    }

    private void init(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabActivity.this, AssetCategorySelectionActivity.class);
                startActivity(intent);
                finish();
            }
        });
        pinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.VISIBLE);
                techinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.GONE);
                nwinfo_form.setVisibility(View.GONE);
                swinfo_form.setVisibility(View.GONE);
            }
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

        tv_spinnercustodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TabActivity.this);
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

        spinner_astype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String assettype = spinner_astype.getSelectedItem().toString();
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
        spinner_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Brand> brandlist = dropDownResponse.getData().getBrandList();
                if (i > 0) {
                    brand_id = String.valueOf(brandlist.get(i - 1).getBrandId());
                    String product = "cpu";
                    brandmodelslist.clear();
                    brandmodelslist.add(SELECT);
                    getCPUModel(brand_id, product);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    model_id = String.valueOf(brandmodels.get(i-1).getBmId());
                    //Toast.makeText(AddAssetActivity.this, cpumodel_id, Toast.LENGTH_SHORT).show();
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
        spinner_installationloc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    selloc_id = String.valueOf(location.get(i - 1).getLocationId());
                    deptlist.clear();
                    getDeptList(selloc_id);
                    deptlist.add(SELECT);
                    String assettype = spinner_astype.getSelectedItem().toString();
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
                    String assettype = spinner_astype.getSelectedItem().toString();
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
        spinner_ostype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        tv_purchasedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(TabActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_purchasedate.setText(date);
                    }
                });
            }
        });
        tv_warrantyupto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(TabActivity.this,null,null, new DateCommunicator() {
                    @Override
                    public void getDate(String date) {
                        tv_warrantyupto.setText(date);
                    }
                });
            }
        });
        tv_eolwarranty_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(TabActivity.this,null,null, new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(TabActivity.this,null,null, new DateCommunicator() {
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
                String asset_type = spinner_astype.getSelectedItem().toString();
                String brand = spinner_brand.getSelectedItem().toString();
                String model = spinner_model.getSelectedItem().toString();
                String srno = et_srno.getText().toString().trim();
                //Tech Conf
                String camtype = "";
                String camera = "";
                String cameraavailable = spinner_camera.getSelectedItem().toString();
                String storage = et_storagecapacity.getText().toString().trim();
                String sdcardsize = et_sdcardsize.getText().toString().trim();
                String imeino1 = et_imeino1.getText().toString().trim();
                String imeino2 = et_imeino2.getText().toString().trim();
                String ownedby = "";
                String simoperator = "";
                if (!ac_shcode.equalsIgnoreCase("TBLT")) {
                    ownedby = spinner_ownedby.getSelectedItem().toString();
                    simoperator = spinner_simoperator.getSelectedItem().toString();
                }
                String simno = et_simno.getText().toString().trim();
                String mobileno = et_mobileno.getText().toString().trim();
                String screensize = et_screensize.getText().toString().trim();
                String color = et_color.getText().toString().trim();
                //Purchase Info
                String purchasedate = tv_purchasedate.getText().toString().trim();
                String invoiceno = et_invoiceno.getText().toString().trim();
                String cpuwarrantyupto = tv_warrantyupto.getText().toString().trim();
                String eol = spinner_eol.getSelectedItem().toString();
                String eolwarranty_date = tv_eolwarranty_date.getText().toString();
                String price = et_price.getText().toString().trim();
                String vender = spinner_vender.getSelectedItem().toString();
                //Dept Info
                String installationdate = tv_installationdate.getText().toString().trim();
                String precise_loc = et_precise_loc.getText().toString().trim();
                String installationloc = spinner_installationloc.getSelectedItem().toString();
                String department = spinner_department.getSelectedItem().toString();
                String section = spinner_section.getSelectedItem().toString();
                //String custodian = spinner_custodian.getSelectedItem().toString();
                //NW Info
                String maethernet = et_maethernet.getText().toString().trim();
                String mawifi = et_mawifi.getText().toString().trim();
                String connectiontype = spinner_connectiontype.getSelectedItem().toString();
                String ipaddress = et_ipaddress.getText().toString().trim();
                String subnetmask = et_subnetmask.getText().toString().trim();
                String gateway = et_gateway.getText().toString().trim();
                //SW Info

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

                if (asset_type.equalsIgnoreCase("") || asset_type.equalsIgnoreCase("select")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select asset type");
                } else if (brand.equalsIgnoreCase(SELECT) || brand.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select brand");
                } else if (model.equalsIgnoreCase(SELECT) || model.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select model");
                } else if (srno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter serial number");
                } else if (processor_id.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select processor");
                } else if (cameraavailable.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select Camera");
                } else if (ramcapacity_id.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select ram capacity");
                } else if (storage.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter storage");
                } else if (imeino1.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter IMEI-1 No.");
                } else if (imeino2.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter IMEI-2 No.");
                } else if (!ac_shcode.equalsIgnoreCase("TBLT") && ownedby.equalsIgnoreCase("Office") && simno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter sim number");
                } else if (!ac_shcode.equalsIgnoreCase("TBLT") && ownedby.equalsIgnoreCase("Office") && mobileno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter mobile number");
                } else if (screensize.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter screen size");
                } else if (color.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter color");
                } else if (purchasedate.equalsIgnoreCase("select date") || purchasedate.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select invoice date");
                } else if (invoiceno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter invoice number");
                } else if (cpuwarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select warranty date");
                } else if (eol.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select IS end of life (EOL)");
                } else if (eolwarranty_date.equalsIgnoreCase("select date") && eol.equalsIgnoreCase("Yes")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select EOL date");
                } else if (price.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Enter price");
                } else if (vender.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select vendor");
                } else if (installationdate.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select Installation Date");
                } else if (installationloc.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select Installation location");
                } else if (department.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select Installation department");
                } else if (section.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select section");
                } else if (cust_id==null) {
                    Utils.getInstance().showAlert(TabActivity.this, "Select custodian");
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
                    String monitorbrand_id="";
                    String monitormodel_id="";
                    String keybrand_id="";
                    String keyboardmodel_id="";
                    String mousebrand_id="";
                    String mousemodel_id="";
                    String monitorscrsize = "";
                    String monitorsrno="";
                    String keyboardtype="";
                    String keyboardsrno="";
                    String mousetype="";
                    String mousesrno="";
                    String ramtype_id="";
                    String hddtype="";
                    String hddingb="";
                    String sddingb="";
                    String monitorwarrantyupto="";
                    String keywarrantyupto="";
                    String mousewarrantyupto="";
                    int osislic=0;
                    int officeval = 0;
                    int officeupdateval = 0;
                    String antivirus_id="";
                    String avsrno = "", avinstall_date="", avrenew_date="", browsername="", officename="", sw_id="",
                            officeversion="", asset_hdd_storage="", asset_ssd_storage="", hddingb_sec="", sddingb_sec="", asset_hdd_storage2="", asset_ssd_storage2="", racksize="";
                    String channel="", printertype="", printerfunction="", cartridgenumber="", brcdconnection_type="", dphn_contype="", deskphonetype="", mic_contype="";
                    String asset_id = "";
                    saveAsset = new SaveAsset(ac_id, asset_id, asset_type, brand_id, model_id, srno, monitorbrand_id, monitormodel_id, monitorsrno, keyboardtype, keybrand_id,
                            keyboardmodel_id, keyboardsrno, mousetype, mousebrand_id, mousemodel_id, mousesrno, processor_id, ramtype_id, ramcapacity_id, hddtype, hddingb, sddingb,
                            purchasedate, invoiceno, vendor_id, price, cpuwarrantyupto, monitorwarrantyupto, keywarrantyupto, mousewarrantyupto, eolvalue, eolwarranty_date,
                            installationdate, selloc_id, seldept_id, selsection_id, cust_id, nw_id, maethernet, mawifi, connectiontype, ipaddress, subnetmask, gateway,
                            os_id, osislic, antivirus_id, avsrno, avinstall_date, avrenew_date, browsername, officeval, officename, officeupdateval, sw_id, remark, statusval,
                            officeversion, asset_hdd_storage, asset_ssd_storage, hddingb_sec, sddingb_sec, asset_hdd_storage2, asset_ssd_storage2, precise_loc, monitorscrsize,
                            racksize, camtype, camera, channel, storage, printertype, printerfunction, cartridgenumber, brcdconnection_type, dphn_contype, deskphonetype, mic_contype,
                            cameraavailable, sdcardsize,imeino1,imeino2,ownedby,simoperator,simno,mobileno,screensize,color,0, "speakertype",
                            "noofmic", "switchtype", "switchporttype","switchlanport", "switchfiberport",
                            "speed", "speedunit", "poewattage", "tvtype", "noofhdmiports", 0,
                            "tvscreensize", "upscategory", "upscategoryunit", "availableport", "portinuse",
                            "eth_ports", "fo_ports", "ssid", 0);

                    submitAssetForm(ac_id, asset_id, asset_type, brand_id, model_id, srno,
                            purchasedate, invoiceno, vendor_id, price, cpuwarrantyupto, eolvalue, eolwarranty_date,
                            installationdate, selloc_id, seldept_id, selsection_id, cust_id, remark, statusval,
                            precise_loc, processor_id, ramtype_id, ramcapacity_id, storage, cameraavailable, sdcardsize, imeino1, imeino2, ownedby, simoperator,
                            simno, mobileno, screensize, color, os_id);
                }
            }
        });
    }

    private void submitAssetForm(String ac_id, String asset_id, String asset_type, String brand_id, String model_id,
                                 String srno, String purchasedate, String invoiceno, String vendor_id, String price,
                                 String cpuwarrantyupto, int eolvalue, String eolwarranty_date,
                                 String installationdate, String selloc_id, String seldept_id,
                                 String selsection_id, String cust_id, String remark, int statusval,
                                 String precise_loc, String processor_id, String ramtype_id, String ramcapacity_id,
                                 String storage, String cameraavailable, String sdcardsize, String imeino1,
                                 String imeino2, String ownedby, String simoperator, String simno, String mobileno,
                                 String screensize, String color, String os_id) {
        RequestBody asset_ac_id = RequestBody.create(MediaType.parse("text/plain"), ac_id);
        RequestBody asset_id1 = RequestBody.create(MediaType.parse("text/plain"), asset_id);
        RequestBody asset_type1 = RequestBody.create(MediaType.parse("text/plain"), asset_type);
        RequestBody brand_id1 = RequestBody.create(MediaType.parse("text/plain"), brand_id);
        RequestBody model_id1 = RequestBody.create(MediaType.parse("text/plain"), model_id);
        RequestBody srno1 = RequestBody.create(MediaType.parse("text/plain"), srno);
        RequestBody purchasedate1 = RequestBody.create(MediaType.parse("text/plain"), purchasedate);
        RequestBody invoiceno1 = RequestBody.create(MediaType.parse("text/plain"), invoiceno);
        RequestBody vendor_id1 = RequestBody.create(MediaType.parse("text/plain"), vendor_id);
        RequestBody price1 = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody cpuwarrantyupto1 = RequestBody.create(MediaType.parse("text/plain"), cpuwarrantyupto);
        RequestBody eolvalue1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(eolvalue));
        RequestBody eolwarranty_date1 = RequestBody.create(MediaType.parse("text/plain"), eolwarranty_date);
        RequestBody installationdate1 = RequestBody.create(MediaType.parse("text/plain"), installationdate);
        RequestBody selloc_id1 = RequestBody.create(MediaType.parse("text/plain"), selloc_id);
        RequestBody seldept_id1 = RequestBody.create(MediaType.parse("text/plain"), seldept_id);
        RequestBody selsection_id1 = RequestBody.create(MediaType.parse("text/plain"), selsection_id);
        RequestBody cust_id1 = RequestBody.create(MediaType.parse("text/plain"), cust_id);
        RequestBody remark1 = RequestBody.create(MediaType.parse("text/plain"), remark);
        RequestBody statusval1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(statusval));
        RequestBody precise_loc1 = RequestBody.create(MediaType.parse("text/plain"), precise_loc);
        RequestBody simoperator1 = RequestBody.create(MediaType.parse("text/plain"), simoperator);
        RequestBody simno1 = RequestBody.create(MediaType.parse("text/plain"), simno);
        RequestBody mobileno1 = RequestBody.create(MediaType.parse("text/plain"), mobileno);
        RequestBody processor_id1 = RequestBody.create(MediaType.parse("text/plain"), processor_id);
        RequestBody ramtype_id1 = RequestBody.create(MediaType.parse("text/plain"), ramtype_id);
        RequestBody ramcapacity_id1 = RequestBody.create(MediaType.parse("text/plain"), ramcapacity_id);
        RequestBody storage1 = RequestBody.create(MediaType.parse("text/plain"), storage);
        RequestBody cameraavailable1 = RequestBody.create(MediaType.parse("text/plain"), cameraavailable);
        RequestBody sdcardsize1 = RequestBody.create(MediaType.parse("text/plain"), sdcardsize);
        RequestBody imeino11 = RequestBody.create(MediaType.parse("text/plain"), imeino1);
        RequestBody imeino22 = RequestBody.create(MediaType.parse("text/plain"), imeino2);
        RequestBody ownedby1 = RequestBody.create(MediaType.parse("text/plain"), ownedby);
        RequestBody screensize1 = RequestBody.create(MediaType.parse("text/plain"), screensize);
        RequestBody color1 = RequestBody.create(MediaType.parse("text/plain"), color);
        RequestBody os_id1 = RequestBody.create(MediaType.parse("text/plain"), os_id);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTabSubmitInfo("application/json", "Bearer " + token, asset_ac_id, asset_id1, asset_type1, brand_id1, model_id1, srno1, purchasedate1, invoiceno1, vendor_id1, price1, cpuwarrantyupto1, eolvalue1, eolwarranty_date1, installationdate1, selloc_id1, seldept_id1, selsection_id1, cust_id1, precise_loc1, remark1, statusval1, simoperator1, simno1, mobileno1, processor_id1, ramtype_id1, ramcapacity_id1, storage1, cameraavailable1, sdcardsize1, imeino11, imeino22, ownedby1, screensize1, color1, os_id1, list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(TabActivity.this, MainActivity.class);
                        Toast.makeText(TabActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TabActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TabActivity.this, msg);
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
                Toast.makeText(TabActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TabActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TabActivity.this, msg);
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

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TabActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TabActivity.this, msg);
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

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TabActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TabActivity.this, msg);
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

                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_department.setAdapter(deptadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TabActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TabActivity.this, msg);
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
                    brandmodels = brandModelListResponse.getData();

                    for (Data obj : brandmodels) {
                        brandmodelslist.add(obj.getBmName());
                        modelsData = new CpuModelsData(obj.getBmId(), obj.getBmName());
                    }
                    //SharedPreferences.getInstance().storeObject(SharedPreferences.KEY_CPUMODELS_OBJ, cpuModelsData);
                    ArrayAdapter<String> branmodeldadapter = new ArrayAdapter<>(TabActivity.this, android.R.layout.simple_spinner_item, brandmodelslist);
                    branmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_model.setAdapter(branmodeldadapter);


                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TabActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TabActivity.this, msg);
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

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TabActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(TabActivity.this);

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
            if (new UserPermissions().checkPermission(TabActivity.this, Manifest.permission.CAMERA)) {
                Toast.makeText(TabActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TabActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
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
    }

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
            MediaScannerConnection.scanFile(TabActivity.this, new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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