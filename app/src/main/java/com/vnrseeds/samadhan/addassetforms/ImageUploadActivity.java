package com.vnrseeds.samadhan.addassetforms;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.utils.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ImageUploadActivity extends AppCompatActivity {
    private RelativeLayout rl_bill_copy;
    private String param, path_billcopy;
    private Context mContext = ImageUploadActivity.this;
    private final int GALLERY = 1, CAMERA = 0;
    private ImageView imageview_billcopy, btn_bill_reset;
    private File BILL_COPY;
    private List<MultipartBody.Part> list = new ArrayList<>();
    private Button btn_send;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_upload);
        rl_bill_copy = findViewById(R.id.rl_bill_copy);
        imageview_billcopy = findViewById(R.id.imageview_billcopy);
        btn_bill_reset = findViewById(R.id.btn_bill_reset);
        progressDialog = new ProgressDialog(mContext);
        rl_bill_copy.setOnClickListener(v -> {
            param = "billcopy";
            chooseImage();
        });
        btn_bill_reset.setOnClickListener(v -> {
            path_billcopy = null;
            imageview_billcopy.setImageResource(R.drawable.ic_baseline_camera_blue);
            btn_bill_reset.setVisibility(View.GONE);
        });
        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(v -> {
            submitAsset();
        });
    }

    private void submitAsset() {
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        /*RequestBody value = RequestBody.create(MediaType.parse("text/plain"), "assest_req");
        RequestBody empid = RequestBody.create(MediaType.parse("text/plain"), model.getEmpid());
        RequestBody AssetNId = RequestBody.create(MediaType.parse("text/plain"), AssetId);*/

        ApiInterface getResponse = retrofit.create(ApiInterface.class);
        Call<String> call = getResponse.submitAsset("application/json", "Bearer " + "0xJc6mRZ13PifgRKd2ikXf20Ighxuq7HYziYVtEQ", list);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    Log.e("TAG", "RESPONSE :" + jsonObject);
                    String Code = jsonObject.getString("Code");
                    String msg = jsonObject.getString("msg");
                    if (Code.equals("300")) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ImageUploadActivity.this);
                        alert.setMessage(msg)

                                .setPositiveButton("OK", (dialog1, which) -> {

                                });

                        AlertDialog alert1 = alert.create();
                        alert1.setIcon(R.mipmap.ic_appicon);
                        alert1.setTitle(R.string.app_name);
                        alert1.show();
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ImageUploadActivity.this);
                        alert.setMessage(msg)

                                .setPositiveButton("OK", (dialog1, which) -> {
                                });

                        AlertDialog alert1 = alert.create();
                        alert1.setIcon(R.mipmap.ic_appicon);
                        alert1.setTitle(R.string.app_name);
                        alert1.show();
                    }
                    Log.e("TAG", "Response " + jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "ERROR " + e);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "InsideUpload" + t.toString());
            }
        });

    }

    private void chooseImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Choose a Media");

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, CAMERA);

            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, GALLERY);//one can be replaced with any action code

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {

            return;
        }

        if (requestCode == GALLERY) {
            if (param.equalsIgnoreCase("billcopy")) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                        imageview_billcopy.setImageBitmap(bitmap);
                        btn_bill_reset.setVisibility(View.VISIBLE);
                        path_billcopy = saveImage(bitmap);
                        list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
        if (requestCode == CAMERA) {
            if (param.equalsIgnoreCase("billcopy")) {
                if (data != null) {

                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageview_billcopy.setImageBitmap(bitmap);
                    btn_bill_reset.setVisibility(View.VISIBLE);
                    path_billcopy = saveImage(bitmap);
                    list.add(prepareFilePart("asset_image", Uri.parse(path_billcopy)));

                }
            }
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
            if (param.equalsIgnoreCase("billcopy")) {
                BILL_COPY = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
                BILL_COPY.createNewFile();
                FileOutputStream fo = new FileOutputStream(BILL_COPY);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(mContext,
                        new String[]{BILL_COPY.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.e("TAG", "File Saved::--->" + BILL_COPY.getAbsolutePath());

                return BILL_COPY.getAbsolutePath();
            }

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