package com.vnrseeds.samadhan.addassetforms;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.vnrseeds.samadhan.AssetCategorySelectionActivity;
import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Brand;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.DropDownResponse;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Location;
import com.vnrseeds.samadhan.parser.addassetdropdownparser.Vendor;
import com.vnrseeds.samadhan.parser.assetdataparser.AssetData;
import com.vnrseeds.samadhan.parser.assetdataparser.AssetDataResponse;
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

public class EditTelevisionActivity extends AppCompatActivity {
    private static final String TAG = "EditTelevisionActivity";
    private static final String SELECT = "select";
    private LinearLayout primeinfo_form;
    private LinearLayout techinfo_form;
    private LinearLayout purinfo_form;
    private LinearLayout depinfo_form;
    private LinearLayout purinfo_tab;
    private LinearLayout pinfo_tab;
    private LinearLayout techinfo_tab;
    private LinearLayout depinfo_tab;

    private DropDownResponse dropDownResponse;
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private ImageButton back_nav;
    private String ac_id;
    //Primary Info
    private Spinner spinner_brand;
    private Spinner spinner_model;
    private Spinner spinner_astype;
    private EditText et_srno;
    private List<Brand> brands;
    private ArrayList<String> brandmodelslist = new ArrayList<>();;
    private String brand_id;
    private String model_id;
    private List<Data> brandmodels;
    //Technical Info
    private RadioButton radio_va;
    private RadioButton radio_kva;
    private LinearLayout ll_upscategory;
    private LinearLayout ll_teltype;
    private LinearLayout ll_remoteyesno;
    private EditText et_noofhdmiports;
    private EditText et_screensize;
    private EditText et_upscategory;
    private Spinner spinner_tvtype;
    private Spinner spinner_remote;
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
    private ArrayList<String> sectionslist = new ArrayList<>();;
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

    private EditText et_remark;
    private Button bt_submit;
    private RadioGroup radiostatus;
    private String vendor_id;
    private SaveAsset saveAsset;
    private List<com.vnrseeds.samadhan.parser.departmentparser.Data> departments;
    private List<SectionData> sections;
    private final String[] yesNoList = {"select", "Yes", "No"};
    private String asset_id;
    private AssetDataResponse assetDataResponse;
    private AssetData assetdata;
    private final ArrayList<String> assetTypeList = new ArrayList<>();
    private String cust_id = null;
    private String ac_shcode;
    private RadioButton rb_active;
    private RadioButton rb_inactive;
    private String nw_id="";

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
        setContentView(R.layout.activity_edit_television);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    private void setTheme() {
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.add_asset);
        TextView tv_category = findViewById(R.id.tv_category);
        String category = getIntent().getStringExtra("category");
        tv_category.setText(category);
        ac_shcode = getIntent().getStringExtra("ac_shcode");
        tv_category.setText(category);
        asset_id = getIntent().getStringExtra("ac_id");
        dropDownResponse = (DropDownResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ADD_ASDD_OBJ, DropDownResponse.class);
        customProgressDialogue = new CustomProgressDialogue(EditTelevisionActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        back_nav = findViewById(R.id.back_nav);

        primeinfo_form = findViewById(R.id.pinfo_form);
        techinfo_form = findViewById(R.id.tech_form);
        purinfo_form = findViewById(R.id.purinfo_form);
        depinfo_form = findViewById(R.id.depinfo_form);

        pinfo_tab = findViewById(R.id.pinfo_tab);
        techinfo_tab = findViewById(R.id.techinfo_tab);
        purinfo_tab = findViewById(R.id.purinfo_tab);
        depinfo_tab = findViewById(R.id.depinfo_tab);

        ll_eolupto = findViewById(R.id.ll_eolupto);

        spinner_brand = findViewById(R.id.spinner_brand);
        spinner_model = findViewById(R.id.spinner_model);
        et_srno = findViewById(R.id.et_srno);
        et_invoiceno = findViewById(R.id.et_invoiceno);
        et_price = findViewById(R.id.et_price);
        et_remark = findViewById(R.id.et_remark);
        et_precise_loc = findViewById(R.id.et_precise_loc);

        //Technical Info
        radio_va = findViewById(R.id.radio_va);
        radio_kva = findViewById(R.id.radio_kva);
        spinner_tvtype = findViewById(R.id.spinner_tvtype);
        spinner_remote = findViewById(R.id.spinner_remote);
        et_noofhdmiports = findViewById(R.id.et_noofhdmiports);
        et_screensize = findViewById(R.id.et_screensize);
        et_upscategory = findViewById(R.id.et_upscategory);
        ll_upscategory = findViewById(R.id.ll_upscategory);
        ll_teltype = findViewById(R.id.ll_teltype);
        ll_remoteyesno = findViewById(R.id.ll_remoteyesno);

        spinner_eol = findViewById(R.id.spinner_eol);
        spinner_vender = findViewById(R.id.spinner_vender);
        spinner_installationloc = findViewById(R.id.spinner_installationloc);
        spinner_department = findViewById(R.id.spinner_department);
        spinner_section = findViewById(R.id.spinner_section);
        spinner_custodian = findViewById(R.id.spinner_custodian);

        tv_purchasedate = findViewById(R.id.tv_purchasedate);
        tv_warrantyupto = findViewById(R.id.tv_warrantyupto);
        tv_eolwarranty_date = findViewById(R.id.tv_eolwarranty_date);
        tv_installationdate = findViewById(R.id.tv_installationdate);
        tv_spinnercustodian = findViewById(R.id.tv_spinnercustodian);
        bt_submit = findViewById(R.id.bt_submit);
        radiostatus = findViewById(R.id.radiostatus);
        spinner_astype = findViewById(R.id.spinner_astype);
        rb_active = findViewById(R.id.rb_active);
        rb_inactive = findViewById(R.id.rb_inactive);

        //Add Photo
        iv_srnophoto = findViewById(R.id.iv_srnophoto);
        iv_assetphoto = findViewById(R.id.iv_assetphoto);
        button_srnophoto = findViewById(R.id.button_srnophoto);
        button_assetphoto = findViewById(R.id.button_assetphoto);

        if (ac_shcode.equalsIgnoreCase("UPSY")){
            ll_teltype.setVisibility(View.GONE);
            ll_remoteyesno.setVisibility(View.GONE);
            ll_upscategory.setVisibility(View.VISIBLE);
        }
        if (ac_shcode.equalsIgnoreCase("TELV")){
            ll_teltype.setVisibility(View.VISIBLE);
            ll_remoteyesno.setVisibility(View.VISIBLE);
            ll_upscategory.setVisibility(View.GONE);
        }

    }

    private void init(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTelevisionActivity.this, AssetCategorySelectionActivity.class);
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
            }
        });

        techinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                techinfo_form.setVisibility(View.VISIBLE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.GONE);
            }
        });

        purinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                techinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.VISIBLE);
                depinfo_form.setVisibility(View.GONE);
            }
        });

        depinfo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primeinfo_form.setVisibility(View.GONE);
                techinfo_form.setVisibility(View.GONE);
                purinfo_form.setVisibility(View.GONE);
                depinfo_form.setVisibility(View.VISIBLE);
            }
        });
        button_srnophoto.setOnClickListener(view -> {
            ImageString = "srnophoto";
            //mGetContent.launch("image/*");
            selectImage();
        });
        button_assetphoto.setOnClickListener(view -> {
            ImageString = "assetphoto";
            //mGetContent.launch("image/*");
            selectImage();
        });
        getAssetData(asset_id);
        tv_spinnercustodian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTelevisionActivity.this);
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
                if (i > 0) {
                    custodian_id = String.valueOf(custodians.get(i-1).getCustodianId());
                }else {
                    custodian_id = String.valueOf(custodians.get(i).getCustodianId());
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

        tv_purchasedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.getInstance().showDatePicker(EditTelevisionActivity.this,null,null, new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(EditTelevisionActivity.this,null,null, new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(EditTelevisionActivity.this,null,null, new DateCommunicator() {
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
                Utils.getInstance().showDatePicker(EditTelevisionActivity.this,null,null, new DateCommunicator() {
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
                String printertype = "";
                String printerfunction = "";
                String cartridgenumber = "";
                String sfpmode = "";
                String porttype = "";
                String speakertype = "";
                String noofmic = "";
                String switchtype = "";
                String switchporttype = "";
                String switchlanport = "";
                String switchfiberport = "";
                String speed = "";
                String poewattage = "";
                String tvtype = "";
                String remote = "";
                if (ac_shcode.equalsIgnoreCase("TELV")) {
                    tvtype = spinner_tvtype.getSelectedItem().toString();
                    remote = spinner_remote.getSelectedItem().toString();
                }
                String noofhdmiports = et_noofhdmiports.getText().toString().trim();
                String tvscreensize = et_screensize.getText().toString().trim();
                String upscategory = et_upscategory.getText().toString().trim();
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
                String maethernet = "";
                String mawifi = "";
                String connectiontype = "";
                String ipaddress = "";
                String subnetmask = "";
                String gateway = "";
                //SW Info

                String remark = et_remark.getText().toString().trim();
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
                    }
                }

                if (asset_type.equalsIgnoreCase("") || asset_type.equalsIgnoreCase("select")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select asset type");
                } else if (brand.equalsIgnoreCase(SELECT) || brand.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select brand");
                } else if (model.equalsIgnoreCase(SELECT) || model.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select model");
                } else if (srno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Enter serial number");
                } else if (ac_shcode.equalsIgnoreCase("TELV") && noofhdmiports.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Enter no. of HDMI ports");
                } else if (ac_shcode.equalsIgnoreCase("TELV") && tvscreensize.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Enter screen size");
                } else if (ac_shcode.equalsIgnoreCase("TELV") && remote.equalsIgnoreCase("select")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select remote");
                } else if (ac_shcode.equalsIgnoreCase("UPSY") && upscategory.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Enter UPS capacity");
                } else if (purchasedate.equalsIgnoreCase("select date") || purchasedate.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select invoice date");
                } else if (invoiceno.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Enter invoice number");
                } else if (cpuwarrantyupto.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select warranty date");
                } else if (eol.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select IS end of life (EOL)");
                } else if (eolwarranty_date.equalsIgnoreCase("select date") && eol.equalsIgnoreCase("Yes")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select EOL date");
                } else if (price.equalsIgnoreCase("")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Enter price");
                } else if (vender.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select vendor");
                } else if (installationdate.equalsIgnoreCase("select date")) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select Installation Date");
                } else if (installationloc.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select Installation location");
                } else if (department.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select Installation department");
                } else if (section.equalsIgnoreCase(SELECT)) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select section");
                } else if (cust_id==null) {
                    Utils.getInstance().showAlert(EditTelevisionActivity.this, "Select custodian");
                } else {
                    int eolvalue = 0;
                    if (eol.equalsIgnoreCase("Yes")) {
                        eolvalue = 1;
                    }
                    int remoteval = 0;
                    if (remote.equalsIgnoreCase("Yes")) {
                        remoteval = 1;
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

                    String speedunit="", upscategoryunit="";
                    if (ac_shcode.equalsIgnoreCase("UPSY")){
                        if (radio_va.isChecked()){
                            upscategoryunit="VA";
                        }else if (radio_kva.isChecked()){
                            upscategoryunit="KVA";
                        }
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
                    String processor_id="";
                    String ramtype_id="";
                    String ramcapacity_id="";
                    String hddtype="";
                    String hddingb="";
                    String sddingb="";
                    String monitorwarrantyupto="";
                    String keywarrantyupto="";
                    String mousewarrantyupto="";
                    String os_id="";
                    int osislic=0;
                    int officeval = 0;
                    int officeupdateval = 0;
                    String antivirus_id="";
                    String avsrno = "", avinstall_date="", avrenew_date="", browsername="", officename="", sw_id="",
                            officeversion="", asset_hdd_storage="", asset_ssd_storage="", hddingb_sec="", sddingb_sec="", asset_hdd_storage2="", asset_ssd_storage2="", racksize="";
                    String channel="", storage="", brcdconnection_type="", dphn_contype="", deskphonetype="", mic_contype="";
                    String cameraavailable="", sdcardsize="",imeino1="",imeino2="",ownedby="",simoperator="",simno="",mobileno="",screensize="";
                    String nw_id="";
                    saveAsset = new SaveAsset(ac_id, asset_id, asset_type, brand_id, model_id, srno, monitorbrand_id, monitormodel_id, monitorsrno, keyboardtype, keybrand_id,
                            keyboardmodel_id, keyboardsrno, mousetype, mousebrand_id, mousemodel_id, mousesrno, processor_id, ramtype_id, ramcapacity_id, hddtype, hddingb, sddingb,
                            purchasedate, invoiceno, vendor_id, price, cpuwarrantyupto, monitorwarrantyupto, keywarrantyupto, mousewarrantyupto, eolvalue, eolwarranty_date,
                            installationdate, selloc_id, seldept_id, selsection_id, cust_id, nw_id, maethernet, mawifi, connectiontype, ipaddress, subnetmask, gateway,
                            os_id, osislic, antivirus_id, avsrno, avinstall_date, avrenew_date, browsername, officeval, officename, officeupdateval, sw_id, remark, statusval,
                            officeversion, asset_hdd_storage, asset_ssd_storage, hddingb_sec, sddingb_sec, asset_hdd_storage2, asset_ssd_storage2, precise_loc, monitorscrsize,
                            racksize, camtype, camera, channel, storage, printertype, printerfunction, cartridgenumber, brcdconnection_type, dphn_contype, deskphonetype, mic_contype,
                            cameraavailable, sdcardsize,imeino1,imeino2,ownedby,simoperator,simno,mobileno,screensize,"", 0, speakertype, noofmic,
                            switchtype, switchporttype, switchlanport, switchfiberport, speed, speedunit, poewattage, tvtype, noofhdmiports, remoteval, tvscreensize, upscategory,
                            upscategoryunit, "availableport", "portinuse", "eth_ports", "fo_ports", "ssid", 0);

                    submitAssetForm(ac_id, asset_id, asset_type, brand_id, model_id, srno, purchasedate, invoiceno, vendor_id, price, cpuwarrantyupto, eolvalue, eolwarranty_date,
                            installationdate, selloc_id, seldept_id, selsection_id, cust_id, remark, statusval, precise_loc, tvtype, remoteval, noofhdmiports, tvscreensize, upscategory, upscategoryunit);
                }
            }
        });
    }

    private void submitAssetForm(String ac_id, String asset_id, String asset_type, String brand_id, String model_id, String srno, String purchasedate,
                                 String invoiceno, String vendor_id, String price, String cpuwarrantyupto, int eolvalue, String eolwarranty_date,
                                 String installationdate, String selloc_id, String seldept_id, String selsection_id, String cust_id,
                                 String remark, int statusval, String precise_loc, String tvtype, int remoteval, String noofhdmiports,
                                 String tvscreensize, String upscategory, String upscategoryunit) {
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
        RequestBody tvtype1 = RequestBody.create(MediaType.parse("text/plain"), tvtype);
        RequestBody remoteval1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(remoteval));
        RequestBody noofhdmiports1 = RequestBody.create(MediaType.parse("text/plain"), noofhdmiports);
        RequestBody tvscreensize1 = RequestBody.create(MediaType.parse("text/plain"), tvscreensize);
        RequestBody upscategory1 = RequestBody.create(MediaType.parse("text/plain"), upscategory);
        RequestBody upscategoryunit1 = RequestBody.create(MediaType.parse("text/plain"), upscategoryunit);
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getUpsSubmitInfo("application/json", "Bearer " + token, asset_ac_id, asset_type1, brand_id1, model_id1, srno1,
                purchasedate1, invoiceno1, vendor_id1, price1, cpuwarrantyupto1, eolvalue1, eolwarranty_date1, installationdate1, selloc_id1, seldept_id1, selsection_id1, cust_id1, remark1, statusval1, precise_loc1, tvtype1, remoteval1, noofhdmiports1,
                tvscreensize1, upscategory1, upscategoryunit1, list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(EditTelevisionActivity.this, MainActivity.class);
                        Toast.makeText(EditTelevisionActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
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
                Toast.makeText(EditTelevisionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditTelevisionActivity.this, "Success", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    assetDataResponse = response.body();
                    assert assetDataResponse != null;
                    assetdata = assetDataResponse.getData().getAssetData();
                    ac_id = assetdata.getAssetAcId();
                    et_srno.setText(assetdata.getAssetSerialNo());
                    String[] purdate = assetdata.getAssetInvoiceDate().split("-");
                    tv_purchasedate.setText(purdate[2] + "-" + purdate[1] + "-" + purdate[0]);
                    et_invoiceno.setText(assetdata.getAssetInvoiceNo());
                    et_price.setText(assetdata.getAssetPrice());
                    et_precise_loc.setText(assetdata.getAssetPreciseLocation());
                    if (assetdata.getAssetTvHdmiPorts()!=null) {
                        et_noofhdmiports.setText(assetdata.getAssetTvHdmiPorts());
                    }
                    if (assetdata.getAssetTvScreenSize()!=null){
                        et_screensize.setText(assetdata.getAssetTvScreenSize());
                    }
                    if (assetdata.getAssetUpsCapacity()!=null){
                        et_upscategory.setText(assetdata.getAssetUpsCapacity());
                    }
                    String[] cpudate = assetdata.getAssetWarrantyUpto().split("-");
                    tv_warrantyupto.setText(cpudate[2] + "-" + cpudate[1] + "-" + cpudate[0]);
                    if (assetdata.getAssetEolWarrantyUpto() != null) {
                        String[] eoldate = assetdata.getAssetEolWarrantyUpto().split("-");
                        tv_eolwarranty_date.setText(eoldate[2] + "-" + eoldate[1] + "-" + eoldate[0]);
                    } else {
                        tv_eolwarranty_date.setText("");
                    }
                    String[] installdate = assetdata.getAssetInstallationDate().split("-");
                    tv_installationdate.setText(installdate[2] + "-" + installdate[1] + "-" + installdate[0]);

                    et_remark.setText(assetdata.getAssetRemarks());

                    custodian_id=assetdata.getAssetInstallationCustodianIds();
                    assetTypeList.add(assetdata.getAssetType());

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
                    ArrayAdapter<String> assettypeAdapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, assetTypeList);
                    assettypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_astype.setAdapter(assettypeAdapter);

                    ArrayList<String> yesNoList4 = new ArrayList<>();
                    if (assetdata.getAssetIsEol().equalsIgnoreCase("1")) {
                        yesNoList4.add("Yes");
                    } else {
                        yesNoList4.add("No");
                    }

                    if (!assetdata.getAssetIsEol().equalsIgnoreCase("1")) {
                        yesNoList4.add("Yes");
                    }
                    if (!assetdata.getAssetIsEol().equalsIgnoreCase("0")) {
                        yesNoList4.add("No");
                    }
                    if (ac_shcode.equalsIgnoreCase("TELV")) {
                        ArrayList<String> remoteYesNoList = new ArrayList<>();
                        if (assetdata.getAssetTvIsRemote().equalsIgnoreCase("1")) {
                            remoteYesNoList.add("Yes");
                        } else {
                            remoteYesNoList.add("No");
                        }

                        if (!assetdata.getAssetTvIsRemote().equalsIgnoreCase("1")) {
                            remoteYesNoList.add("Yes");
                        }
                        if (!assetdata.getAssetIsEol().equalsIgnoreCase("0")) {
                            remoteYesNoList.add("No");
                        }
                        ArrayAdapter<String> remoteAdapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, remoteYesNoList);
                        remoteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_remote.setAdapter(remoteAdapter);
                    }

                    if (assetdata.getAssetStatus().equalsIgnoreCase("1")) {
                        rb_active.setChecked(true);
                        rb_inactive.setChecked(false);
                    }else {
                        rb_active.setChecked(false);
                        rb_inactive.setChecked(true);
                    }

                    if (assetdata.getAssetUpsCapacityUnit().equalsIgnoreCase("VA")) {
                        radio_va.setChecked(true);
                        radio_kva.setChecked(false);
                    }else {
                        radio_va.setChecked(false);
                        radio_kva.setChecked(true);
                    }

                    ArrayAdapter<String> eolAdapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, yesNoList4);
                    eolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_eol.setAdapter(eolAdapter);
                    getDropDownList();
                } else {
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AssetDataResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Toast.makeText(EditTelevisionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    brand_id = assetdata.getAssetBrandId();
                    for (Brand obj : brands) {
                        if (assetdata.getAssetBrandId().equalsIgnoreCase(obj.getBrandId())) {
                            cpubrandlist.add(obj.getBrandName());
                        }
                    }
                    for (Brand obj : brands) {
                        cpubrandlist.add(obj.getBrandName());
                    }
                    ArrayAdapter<String> cpubrandadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, cpubrandlist);
                    cpubrandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_brand.setAdapter(cpubrandadapter);

                    List<Data> cpubrandmodels1 = assetDataResponse.getData().getBrandModelList();
                    brandmodels = assetDataResponse.getData().getBrandModelList();
                    model_id = assetdata.getAssetModelId();
                    for (Data obj : cpubrandmodels1) {
                        if (assetdata.getAssetModelId().equalsIgnoreCase(String.valueOf(obj.getBmId()))) {
                            brandmodelslist.add(obj.getBmName());
                        }
                    }
                    for (Data obj : cpubrandmodels1) {
                        brandmodelslist.add(obj.getBmName());
                        modelsData = new CpuModelsData(obj.getBmId(), obj.getBmName());
                    }
                    ArrayAdapter<String> cpubranmodeldadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, brandmodelslist);
                    cpubranmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_model.setAdapter(cpubranmodeldadapter);

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
                    ArrayAdapter<String> vendorlistadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, vendorlist);
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
                    ArrayAdapter<String> locationlistadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, locationlist);
                    locationlistadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_installationloc.setAdapter(locationlistadapter);

                    seldept_id = assetdata.getAssetInstallationDepartmentId();
                    getDeptListEdit(selloc_id);
                    getSectionListEdit(selloc_id, seldept_id);
                    selsection_id = assetdata.getAssetInstallationSectionId();

                    if (ac_shcode.equalsIgnoreCase("TELV")) {
                        List<String> tvTypelist = dropDownResponse.getData().getTvTypeList();
                        ArrayList<String> tvTypelist1 = new ArrayList<>();
                        for (String obj : tvTypelist) {
                            if (assetdata.getAssetTvType().equalsIgnoreCase(obj)) {
                                tvTypelist1.add(obj);
                            }
                        }
                        for (String obj : tvTypelist) {
                            if (!assetdata.getAssetTvType().equalsIgnoreCase(obj)) {
                                tvTypelist1.add(obj);
                            }
                        }
                        ArrayAdapter<String> tvTypeAdapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, tvTypelist1);
                        tvTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_tvtype.setAdapter(tvTypeAdapter);
                    }

                    String[] custid = assetdata.getAssetInstallationCustodianIds().split(",");
                    String custodianid = null;
                    String assettype = spinner_astype.getSelectedItem().toString();
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
                        ArrayAdapter<String> custodianapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, custodisnlist2);
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
                        }
                    }

                    tv_spinnercustodian.setText(custname);
                    cust_id = assetdata.getAssetInstallationCustodianIds();

                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
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
                    custodians = custodianResponse.getData();
                    ArrayList<String> custodianlist1 = new ArrayList<>();
                    custodianlist = new String[0];
                    for (CustodianData obj : custodians) {
                        custodianlist1.add(obj.getCustodianName());
                    }
                    custodianlist = custodianlist1.toArray(new String[0]);
                    selectedcustodian = new boolean[custodianlist.length];

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
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

                    ArrayAdapter<String> custodianapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, custodianlist);
                    custodianapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_custodian.setAdapter(custodianapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
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

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
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
                    if (sectionslist!=null) {
                        sectionslist.clear();
                    }
                    for (SectionData obj : sections) {
                        if (assetdata.getAssetInstallationSectionId().equalsIgnoreCase(String.valueOf(obj.getDsId()))){
                            sectionslist.add(obj.getDsName());
                        }
                    }

                    for (SectionData obj : sections) {
                        sectionslist.add(obj.getDsName());
                    }

                    ArrayAdapter<String> sectionadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, sectionslist);
                    sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_section.setAdapter(sectionadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
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

                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_department.setAdapter(deptadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
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
                    for (com.vnrseeds.samadhan.parser.departmentparser.Data obj : departments) {
                        if (assetdata.getAssetInstallationDepartmentId().equalsIgnoreCase(String.valueOf(obj.getDepartmentId()))) {
                            deptlist.add(obj.getDepartmentName());
                        }
                    }
                    for (com.vnrseeds.samadhan.parser.departmentparser.Data obj : departments) {
                        deptlist.add(obj.getDepartmentName());
                    }
                    ArrayAdapter<String> deptadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, deptlist);
                    deptadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_department.setAdapter(deptadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
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
                    ArrayAdapter<String> branmodeldadapter = new ArrayAdapter<>(EditTelevisionActivity.this, android.R.layout.simple_spinner_item, brandmodelslist);
                    branmodeldadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_model.setAdapter(branmodeldadapter);
                } else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(EditTelevisionActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(EditTelevisionActivity.this, msg);
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

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditTelevisionActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditTelevisionActivity.this);

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
            if (new UserPermissions().checkPermission(EditTelevisionActivity.this, Manifest.permission.CAMERA)) {
                Toast.makeText(EditTelevisionActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditTelevisionActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
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
            MediaScannerConnection.scanFile(EditTelevisionActivity.this, new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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