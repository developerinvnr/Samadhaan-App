package com.vnrseeds.samadhan.ui.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.AppConfig;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;
    private CircleImageView user_profile_photo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView user_profile_name = root.findViewById(R.id.user_profile_name);
        final TextView user_dept = root.findViewById(R.id.user_dept);
        final TextView mobilenoTv = root.findViewById(R.id.mobilenoTv);
        final TextView emailTv = root.findViewById(R.id.emailTv);
        final TextView tv_company = root.findViewById(R.id.tv_company);
        final TextView tv_department = root.findViewById(R.id.tv_department);
        final TextView tv_location = root.findViewById(R.id.tv_location);
        final TextView tv_city = root.findViewById(R.id.tv_city);
        final TextView tv_district = root.findViewById(R.id.tv_district);
        final TextView tv_state = root.findViewById(R.id.tv_state);
        final TextView tv_country = root.findViewById(R.id.tv_country);
        final TextView tv_reportinto = root.findViewById(R.id.tv_reportinto);
        user_profile_photo = root.findViewById(R.id.user_profile_photo);

        User user = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        user_profile_name.setText(user.getUser_name());
        mobilenoTv.setText(user.getUser_mobile());
        emailTv.setText(user.getUser_email());
        user_dept.setText(user.getDepartmentName());
        tv_company.setText(user.getCompanyName());
        tv_department.setText(user.getDepartmentName());
        tv_location.setText(user.getLocationName());
        tv_city.setText(user.getCityName());
        tv_district.setText(user.getDistrictName());
        tv_state.setText(user.getStateName());
        tv_country.setText(user.getCountryName());
        tv_reportinto.setText(user.getReportingName());
        user_profile_photo.setImageBitmap(BitmapFactory.decodeFile(AppConfig.BASE_IMAGE_URL + "uploads/users/photos/" + user.getUser_photo()));
        Picasso.get().load(AppConfig.BASE_IMAGE_URL + "uploads/users/photos/" + user.getUser_photo()).placeholder(R.drawable.person).into(user_profile_photo);

        user_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


        return root;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
}