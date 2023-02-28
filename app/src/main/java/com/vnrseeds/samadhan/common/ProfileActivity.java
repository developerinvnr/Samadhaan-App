package com.vnrseeds.samadhan.common;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vnrseeds.samadhan.MainActivity;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.AppConfig;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private CircleImageView user_profile_photo;
    private ImageButton back_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(ProfileActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        back_nav = findViewById(R.id.back_nav);
        toolbar_title.setText("Profile");
        final TextView user_profile_name = findViewById(R.id.user_profile_name);
        final TextView user_dept = findViewById(R.id.user_dept);
        final TextView mobilenoTv = findViewById(R.id.mobilenoTv);
        final TextView emailTv = findViewById(R.id.emailTv);
        final TextView tv_company = findViewById(R.id.tv_company);
        final TextView tv_department = findViewById(R.id.tv_department);
        final TextView tv_location = findViewById(R.id.tv_location);
        final TextView tv_city = findViewById(R.id.tv_city);
        final TextView tv_district = findViewById(R.id.tv_district);
        final TextView tv_state = findViewById(R.id.tv_state);
        final TextView tv_country = findViewById(R.id.tv_country);
        final TextView tv_reportinto = findViewById(R.id.tv_reportinto);
        user_profile_photo = findViewById(R.id.user_profile_photo);

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
        //user_profile_photo.setImageBitmap(BitmapFactory.decodeFile(AppConfig.BASE_IMAGE_URL + "uploads/users/photos/" + user.getUser_photo()));
        Picasso.get().load(AppConfig.BASE_IMAGE_URL + "uploads/users/photos/" + user.getUser_photo()).placeholder(R.drawable.profile_man).into(user_profile_photo);
    }

    private void init(){
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}