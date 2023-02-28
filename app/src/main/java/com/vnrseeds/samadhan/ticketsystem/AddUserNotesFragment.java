package com.vnrseeds.samadhan.ticketsystem;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.TicketViewAdapter;
import com.vnrseeds.samadhan.addassetforms.Utility;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.parser.ticketviewparser.HandleReplyLog;
import com.vnrseeds.samadhan.parser.ticketviewparser.TicketViewResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kotlin.io.TextStreamsKt;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserNotesFragment extends Fragment implements TicketViewAdapter.TicketViewAdapterListener {
    private static final String TAG = "AddUserNotesFragment";
    private RecyclerView lv_commentlist;
    private Button button_addnotes;
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private TicketDetailsPojo ticketDetailsPojo;
    private TicketViewAdapter ticketViewAdapter;
    private List<HandleReplyLog> ticketViewArray = new ArrayList<>();
    private TextView tv_ticketdate;
    private TextView tv_ticketno;
    private TextView tv_ticket_title;
    private TextView tv_priority;
    private TextView tv_more;

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


    public AddUserNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user_notes, container, false);
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
        tv_ticketdate = view.findViewById(R.id.tv_ticketdate);
        tv_ticketno = view.findViewById(R.id.tv_ticketno);
        tv_ticket_title = view.findViewById(R.id.tv_ticket_title);
        tv_priority = view.findViewById(R.id.tv_priority);
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

        if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical") || ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
            tv_priority.setTextColor(Color.RED);
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Mediaum")){
            tv_priority.setTextColor(Color.parseColor("#FF9800"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
            tv_priority.setTextColor(Color.parseColor("#3F5F36"));
        }

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            button_addnotes.setVisibility(View.GONE);
        }else {
            button_addnotes.setVisibility(View.VISIBLE);
        }

        //ticketViewAdapter = new TicketViewAdapter(getContext(), ticketViewArray,AddUserNotesFragment.this);
        //lv_commentlist.setAdapter(ticketViewAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        lv_commentlist.setLayoutManager(mLayoutManager);
        lv_commentlist.setItemAnimator(new DefaultItemAnimator());
    }

    private void init() {
        getReply(ticketDetailsPojo.getTicketId());
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
                        String message = et_message.getText().toString();
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
                TextView tv_astype = dialog.findViewById(R.id.tv_astype);
                TextView tv_assignto = dialog.findViewById(R.id.tv_assignto);
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

                if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical") || ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
                    tv_priority.setTextColor(Color.RED);
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Mediaum")){
                    tv_priority.setTextColor(Color.parseColor("#FF9800"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low") || ticketDetailsPojo.getPriority().equalsIgnoreCase("Normal")){
                    tv_priority.setTextColor(Color.parseColor("#3F5F36"));
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