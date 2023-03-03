package com.vnrseeds.samadhan.ticketsystem;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.ITNListAdapter;
import com.vnrseeds.samadhan.addassetforms.Utility;
import com.vnrseeds.samadhan.parser.itnlistparser.ITNListResponse;
import com.vnrseeds.samadhan.parser.itnlistparser.Itn;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.test.FileDownloader;
import com.vnrseeds.samadhan.utils.AppConfig;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ITNListActivity extends AppCompatActivity implements ITNListAdapter.ITNListAdapterListener{

    private static final String TAG = "ITNListActivity";
    private CustomProgressDialogue customProgressDialogue;
    private ImageButton back_nav;
    private String token;
    private SearchView searchView;
    private RecyclerView lv_itnlist;
    private ITNListAdapter listAdapter;
    private List<Itn> itnListData = new ArrayList<>();
    private TicketDetailsPojo ticketDetailsPojo;


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
    private RoleResponse roleResponse;
    private FloatingActionButton btn_generateITN;
    private ImageView iv_noDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itnlist);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(ITNListActivity.this);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        btn_generateITN = findViewById(R.id.btn_generateITN);
        iv_noDataFound = findViewById(R.id.iv_noDataFound);
        toolbar_title.setText("ITN List");
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);

        //searchView = findViewById(R.id.searchView);
        lv_itnlist = findViewById(R.id.lv_itnlist);
        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") && ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
            btn_generateITN.setVisibility(View.GONE);
        }

    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
                    Intent intent = new Intent(ITNListActivity.this, TicketHandlingUserActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if ((roleResponse.getData().contains("HARDWARE_ENGINEER") || roleResponse.getData().contains("NETWORK_ENGINEER")) && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                        Intent intent = new Intent(ITNListActivity.this, TicketHandlingActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (roleResponse.getData().contains("SOFTWARE_ENGINEER") && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")) {
                        Intent intent = new Intent(ITNListActivity.this, TicketHandlingActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (roleResponse.getData().contains("HARDWARE_ENGINEER") || roleResponse.getData().contains("NETWORK_ENGINEER") || roleResponse.getData().contains("SOFTWARE_ENGINEER")) {
                        Intent intent = new Intent(ITNListActivity.this, TicketHandlingActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(ITNListActivity.this, TicketHandlingUserActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    /*Intent intent = new Intent(ITNListActivity.this, TicketHandlingActivity.class);
                    startActivity(intent);
                    finish();*/
                }
            }
        });

        btn_generateITN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ITNListActivity.this, InternalTransferNoteActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        getITNList();
    }

    private void getITNList() {
        Log.e("Ticket ID:", ticketDetailsPojo.getTicketId());
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<ITNListResponse> call = apiInterface.getITNList("application/json", "Bearer " + token,ticketDetailsPojo.getTicketId());
        call.enqueue(new Callback<ITNListResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<ITNListResponse> call, @NotNull Response<ITNListResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    itnListData.clear();
                    ITNListResponse itnListResponse = response.body();
                    assert itnListResponse != null;
                    itnListData = itnListResponse.getData().getItnList();
                    Log.e(TAG, String.valueOf(itnListData));

                    if (itnListResponse.getData().getCanCreateITN()==1){
                        btn_generateITN.setVisibility(View.VISIBLE);
                    }else {
                        btn_generateITN.setVisibility(View.GONE);
                    }

                    if (!itnListData.isEmpty()) {
                        iv_noDataFound.setVisibility(View.GONE);
                        listAdapter = new ITNListAdapter(ITNListActivity.this, itnListData, ITNListActivity.this);
                        lv_itnlist.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        lv_itnlist.setLayoutManager(mLayoutManager);
                        lv_itnlist.setItemAnimator(new DefaultItemAnimator());
                        lv_itnlist.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                    }else {
                        lv_itnlist.setVisibility(View.GONE);
                        iv_noDataFound.setVisibility(View.VISIBLE);
                        //Utils.getInstance().showAlert(ITNListActivity.this, "Data not found");
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(ITNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(ITNListActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ITNListResponse> call, @NotNull Throwable t) {
                customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onContactSelected(Itn datum) {

    }

    @SuppressLint("SetTextI18n")
    public void setReceived(Itn datum) {
        final Dialog dialog = new Dialog(ITNListActivity.this);
        dialog.setContentView(R.layout.custom_itn_receive_note);
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
        TextView tv_issuephoto = dialog.findViewById(R.id.tv_issuephoto);
        ll_issuephoto = dialog.findViewById(R.id.ll_issuephoto);
        iv_issuephoto = dialog.findViewById(R.id.iv_issuephoto);
        tv_lastlot.setText("Receive ITN");

        tv_issuephoto.setOnClickListener(view -> {
            selectImage();
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString();
                if (message.equalsIgnoreCase("")){
                    Toast.makeText(ITNListActivity.this, "Enter message", Toast.LENGTH_LONG).show();
                }else {
                    dialog.cancel();
                    updateReceive(datum.getItnId(),message);
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

    private void selectImage() {
        final CharSequence[] items = {"Click here to open camera", "Choose from gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ITNListActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ITNListActivity.this);

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
        path_billcopy = getPath(image_uri);
        Log.e(TAG, String.valueOf(image_uri));
        list.add(prepareFilePart("itn_received_file", Uri.parse(path_billcopy)));
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
        Bitmap bm = null;
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
        String absolutePath = saveImage(bm);
        list.add(prepareFilePart("itn_received_file", Uri.parse(absolutePath)));
        /*assert bm != null;
        path_billcopy = getPath(image_uri);
        Log.e(TAG, String.valueOf(image_uri));
        list.add(prepareFilePart("ticket_raise_files", image_uri));*/
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
            MediaScannerConnection.scanFile(ITNListActivity.this, new String[]{BILL_COPY.getPath()}, new String[]{"image/jpeg"}, null);
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

    public void updateReceive(Integer itnID, String message){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e("Receive Params", itnID+"="+message+"="+list);
        RequestBody itnID1 = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(itnID));
        RequestBody message1 = RequestBody.create(MediaType.parse("text/plain"), message);
        Call<SubmitSuccessResponse> call = apiInterface.setITNReceived("application/json", "Bearer " + token, itnID1, message1, list);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(ITNListActivity.this, ITNListActivity.class);
                        Toast.makeText(ITNListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(ITNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(ITNListActivity.this, msg);
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
            }
        });
    }

    public void viewPDF(Itn datum) {
        Log.e("ITN ID:", String.valueOf(datum.getItnId()));
        String file_url = AppConfig.BASE_IMAGE_URL + "uploads/itn/"+datum.getItnFile();
        Toast.makeText(ITNListActivity.this, file_url, Toast.LENGTH_SHORT).show();
        String filename = datum.getItnFile();
        String[] filename1 = filename.split("\\.");

        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(file_url);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(filename1[0]);
        request.setDescription("Downloading");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadmanager.enqueue(request);
        Log.e("Download PDF : ", String.valueOf(reference));
    }

    @SuppressLint("SetTextI18n")
    public void cancelITN(Itn datum) {
        final Dialog dialog = new Dialog(ITNListActivity.this);
        dialog.setContentView(R.layout.custom_itn_receive_note);
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
        TextView tv_issuephoto = dialog.findViewById(R.id.tv_issuephoto);
        TextInputLayout til_lable = dialog.findViewById(R.id.til_lable);
        ll_issuephoto = dialog.findViewById(R.id.ll_issuephoto);
        iv_issuephoto = dialog.findViewById(R.id.iv_issuephoto);
        tv_lastlot.setText("Cancel ITN");
        til_lable.setHint(R.string.cancel_note);

        tv_issuephoto.setVisibility(View.GONE);
        ll_issuephoto.setVisibility(View.GONE);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString().trim();
                if (message.equalsIgnoreCase("")){
                    Toast.makeText(ITNListActivity.this, "Enter message", Toast.LENGTH_LONG).show();
                }else {
                    dialog.cancel();
                    cancelReceive(datum.getItnId(),message);
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

    private void cancelReceive(Integer itnId, String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Log.e("Receive Params", itnId+"="+message);
        Call<SubmitSuccessResponse> call = apiInterface.setITNCancel("application/json", "Bearer " + token, itnId, message);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<SubmitSuccessResponse> call, @NotNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()){
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(ITNListActivity.this, ITNListActivity.class);
                        Toast.makeText(ITNListActivity.this, submitSuccessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(ITNListActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(ITNListActivity.this, msg);
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
            }
        });
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            Intent intent = new Intent(ITNListActivity.this, TicketHandlingUserActivity.class);
            startActivity(intent);
            finish();
        }else {
            if ((roleResponse.getData().contains("HARDWARE_ENGINEER") || roleResponse.getData().contains("NETWORK_ENGINEER")) && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                Intent intent = new Intent(ITNListActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }else if (roleResponse.getData().contains("SOFTWARE_ENGINEER") && ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")) {
                Intent intent = new Intent(ITNListActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }else if (roleResponse.getData().contains("HARDWARE_ENGINEER") || roleResponse.getData().contains("NETWORK_ENGINEER") || roleResponse.getData().contains("SOFTWARE_ENGINEER")) {
                Intent intent = new Intent(ITNListActivity.this, TicketHandlingActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(ITNListActivity.this, TicketHandlingUserActivity.class);
                startActivity(intent);
                finish();
            }
            /*Intent intent = new Intent(ITNListActivity.this, TicketHandlingActivity.class);
            startActivity(intent);
            finish();*/
        }
    }
}