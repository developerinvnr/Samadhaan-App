package com.vnrseeds.samadhan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.vnrseeds.samadhan.common.ChangePasswordActivity;
import com.vnrseeds.samadhan.common.HelpActivity;
import com.vnrseeds.samadhan.common.LoginActivity;
import com.vnrseeds.samadhan.common.ProfileActivity;
import com.vnrseeds.samadhan.communicator.alertCommunicator;
import com.vnrseeds.samadhan.notifications.NotificationListActivity;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.notificationlistparser.Datum;
import com.vnrseeds.samadhan.parser.notificationlistparser.NotificationListResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.AppConfig;
import com.vnrseeds.samadhan.utils.BadgeDrawable;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private String token;
    //private final CustomProgressDialogue customProgressDialogue = new CustomProgressDialogue(MainActivity.this);
    private NavigationView navigationView;
    private List<Object> roleList = new ArrayList<>();
    private ArrayList<Datum> notificationList = new ArrayList<>();
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_menu);
        FloatingActionButton fab = findViewById(R.id.fab);
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);*/
        //getNotificationList();
        navigationView = findViewById(R.id.nav_view);
        TextView app_version = findViewById(R.id.app_version);
        app_version.setText(BuildConfig.VERSION_NAME);
        //Passing each menu ID as a set of Ids because each
        //menu should be considered as top level destinations.
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logout();
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_changepass).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_help).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_gallery).setOnMenuItemClickListener(menuItem -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        });

        //navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(toolbar, navController, mAppBarConfiguration);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_home){
                    toolbar.setNavigationIcon(R.drawable.menu);
                }
            }
        });

        //ImageView imageView = drawer.findViewById(R.id.imageView);
        CircleImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        TextView tv_username = navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        TextView tv_email = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
        User userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        Picasso.get().load(AppConfig.BASE_IMAGE_URL + "uploads/users/photos/" + userData.getUser_photo()).placeholder(R.drawable.profile_man).into(imageView);
        tv_email.setText(userData.getUser_email());
        tv_username.setText(userData.getUser_name());

        if (Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.darkCard));
        }
    }

    private void logout() {
        SharedPreferences.getInstance().clearSharedPref();
        Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
        loginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginActivity);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        getNotificationList(icon);
        Log.e(TAG, String.valueOf(count));
        /*if (count!=0) {
            setBadgeCount(this, icon, String.valueOf(count));
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            finish();
            Intent intent = new Intent(MainActivity.this, NotificationListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBadgeCount(Context context, LayerDrawable icon, String count) {
        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void getNotificationList(LayerDrawable icon) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        //customProgressDialogue.show();
        Call<NotificationListResponse> call = apiInterface.getNotifications("application/json", "Bearer "+token);
        call.enqueue(new Callback<NotificationListResponse>() {
            @SuppressLint({"NotifyDataSetChanged", "LongLogTag"})
            @Override
            public void onResponse(@NotNull Call<NotificationListResponse> call, @NotNull Response<NotificationListResponse> response) {
                if (response.isSuccessful()){
                    //customProgressDialogue.cancel();
                    NotificationListResponse notificationListResponse = response.body();
                    assert notificationListResponse != null;
                    notificationList = notificationListResponse.getData();
                    Log.e(TAG, String.valueOf(notificationList));
                    for (Datum obj:notificationList){
                        if (obj.getNotificationIsViewed()!=null) {
                            if (obj.getNotificationIsViewed().equalsIgnoreCase("0")) {
                                count++;
                            }
                        }
                    }
                    if (count!=0) {
                        setBadgeCount(MainActivity.this, icon, String.valueOf(count));
                    }
                    //Log.e(TAG, String.valueOf(count));
                }else {
                    //customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(MainActivity.this, msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NotNull Call<NotificationListResponse> call, @NotNull Throwable t) {
                //customProgressDialogue.cancel();
                Log.e(TAG, "RetrofitError : " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isOpen()){
            drawer.close();
        }else {
            Utils.getInstance().showYesNOAlert(MainActivity.this, getString(R.string.alert_exit), new alertCommunicator() {
                @Override
                public void onClickPositiveBtn() {
                    finish();
                }

                @Override
                public void onClickNegativrBtn() {

                }
            });
            //super.onBackPressed();
        }
    }

}