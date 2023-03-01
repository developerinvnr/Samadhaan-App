package com.vnrseeds.samadhan.ticketsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.parser.ticketviewparser.RaiseData;
import com.vnrseeds.samadhan.parser.ticketviewparser.TicketViewResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketHandlingActivity extends AppCompatActivity {

    private static final String TAG = "TicketHandlingActivity";
    private BottomNavigationView bn_view;
    private String token;
    private CustomProgressDialogue customProgressDialogue;
    private ImageButton back_nav;
    private TicketDetailsPojo ticketDetailsPojo;
    private RoleResponse roleResponse;
    private Toolbar toolbar;
    private User userData;
    private RaiseData ticketData;

    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_handling);
        //toolbar = findViewById(R.id.custom_toolbar);
        //setSupportActionBar(toolbar);
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#EAEAD2"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setTitle("Ticket Handling");
        setTheme();
        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }

    @SuppressLint("SetTextI18n")
    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(TicketHandlingActivity.this);
        //TextView toolbar_title = findViewById(R.id.toolbar_title);
        //back_nav = findViewById(R.id.back_nav);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        //toolbar_title.setText("Ticket Handling");
        bn_view = findViewById(R.id.bn_view);
        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        //getReply(ticketDetailsPojo.getTicketId());
        userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
    }

    private void init(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }
       /* back_nav.setOnClickListener(view -> {
            Intent intent = new Intent(TicketHandlingActivity.this, TicketsListActivity.class);
            startActivity(intent);
            finish();
        });*/
        bn_view.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            int id = item.getItemId();
            if (id==R.id.nav_details){
                loadFragment(new DetailsFragment(), true);
            }else if (id==R.id.nav_handling){
                loadFragment(new TicketHandlingFragment(), false);
            }else if (id==R.id.nav_intnotes){
                loadFragment(new InternalNotesFragment(), false);
            }else if (id==R.id.nav_resolved){
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
                    loadFragment(new TicketResolveFragment(), false);
                }else {
                    Toast.makeText(TicketHandlingActivity.this, "Diagnosis not done for this ticket", Toast.LENGTH_LONG).show();
                }
            }else if (id==R.id.nav_closeticket){
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")) {
                    //Log.e("User:", ticketDetailsPojo.getTicket_user_id()+","+userData.getUser_id());
                    if (ticketDetailsPojo.getTicket_raised_by_id().equalsIgnoreCase(userData.getUser_id())){
                        loadFragment(new TicketCloseFragment(), false);
                    }else{
                        Toast.makeText(TicketHandlingActivity.this, "Service provider can not close this ticket", Toast.LENGTH_LONG).show();
                    }
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed")){
                    loadFragment(new TicketCloseFragment(), false);
                }else {
                    Toast.makeText(TicketHandlingActivity.this, "Ticket not resolved", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        });
        bn_view.setSelectedItemId(R.id.nav_details);
    }

    private void getReply(String ticketId) {
        Log.e("Ticket ID : ", ticketId);
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
                    ticketData = ticketViewResponse.getData().getRaiseData();
                }else {
                    customProgressDialogue.cancel();
                    if (response.errorBody() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(TextStreamsKt.readText(response.errorBody().charStream()));
                            String msg = jsonObj.getString("message");
                            Toast.makeText(TicketHandlingActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Utils.getInstance().showAlert(TicketHandlingActivity.this, msg);
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
                Toast.makeText(TicketHandlingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        /*if (flag)
            ft.add(R.id.container, fragment);
        else*/
            ft.replace(R.id.container, fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        //MenuItem action_itn = menu.findItem(R.id.action_itn);
        MenuItem action_itnlist = menu.findItem(R.id.action_itnlist);
        MenuItem action_srnlist = menu.findItem(R.id.action_srnlist);
        //MenuItem action_srn = menu.findItem(R.id.action_srn);
        MenuItem action_visit = menu.findItem(R.id.action_visit);
        MenuItem action_addToAsset = menu.findItem(R.id.action_addToAsset);
        if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")) {
            //action_itn.setVisible(false);
            action_itnlist.setVisible(false);
            action_srnlist.setVisible(false);
            //action_srn.setVisible(false);
            action_addToAsset.setVisible(false);
            action_visit.setVisible(false);
        }else {
            if (ticketDetailsPojo.getLocationIsBaseLocation().equalsIgnoreCase("0")) {
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Assigned") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Open")) {
                    action_itnlist.setVisible(false);
                    action_srnlist.setVisible(false);
                }else {
                    action_itnlist.setVisible(true);
                    action_srnlist.setVisible(true);
                }
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Reopen") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")) {
                    //action_itn.setVisible(true);
                    action_addToAsset.setVisible(true);
                    //action_srn.setVisible(true);
                }else {
                    //action_itn.setVisible(false);
                    //action_itnlist.setVisible(false);
                    action_addToAsset.setVisible(false);
                    //action_srnlist.setVisible(false);
                    //action_srn.setVisible(false);
                }
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
                    //action_itn.setVisible(true);
                }
                action_visit.setVisible(true);
            }else {
                //action_itn.setVisible(false);
                action_itnlist.setVisible(false);
                action_visit.setVisible(false);
            }
            if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Assigned") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Open")) {
                action_addToAsset.setVisible(true);
            }

            if (ticketDetailsPojo.getAssetIsByod().equalsIgnoreCase("1")){
                //action_itn.setVisible(false);
                action_itnlist.setVisible(false);
                action_srnlist.setVisible(false);
                //action_srn.setVisible(false);
                action_addToAsset.setVisible(false);
                action_visit.setVisible(false);
            }
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
                    Intent intent = new Intent(TicketHandlingActivity.this, TicketHistoryListActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Intent intent = new Intent(TicketHandlingActivity.this, TicketsListActivity.class);
                    startActivity(intent);
                    finish();
                }
                /*Intent intent = new Intent(TicketHandlingActivity.this, TicketsListActivity.class);
                startActivity(intent);
                finish();*/
                return true;
            /*case R.id.action_itn:
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Reopen") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")) {
                    if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")){
                        Intent intent1 = new Intent(TicketHandlingActivity.this, InternalTransferNoteActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }
                return true;
            case R.id.action_srn:
                if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")) {
                    if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")){
                        Intent intent1 = new Intent(TicketHandlingActivity.this, ServiceRepaireNoteActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }
                return true;*/
            case R.id.action_srnlist:
                if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
                    if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                        Intent intent4 = new Intent(TicketHandlingActivity.this, SRNListActivity.class);
                        startActivity(intent4);
                        finish();
                    }
                }
                return true;
            case R.id.action_visit:
                if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
                    if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                        Intent intent2 = new Intent(TicketHandlingActivity.this, VisitListActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                }
                return true;
            case R.id.action_itnlist:
                if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
                    if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                        Intent intent3 = new Intent(TicketHandlingActivity.this, ITNListActivity.class);
                        startActivity(intent3);
                        finish();
                    }
                }
                return true;
            case R.id.action_addToAsset:
                    if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Reopen") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")) {
                        if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware") && (ticketDetailsPojo.getTicketServiceTypeCurrentStatus().equalsIgnoreCase("Deployed") || ticketDetailsPojo.getTicketServiceTypeCurrentStatus().equalsIgnoreCase("Workshop"))) {
                            Intent intent3 = new Intent(TicketHandlingActivity.this, AddToAssetActivity.class);
                            startActivity(intent3);
                            finish();
                        } else {
                            Toast.makeText(TicketHandlingActivity.this, "Asset is not deployed or not in workshop", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(TicketHandlingActivity.this, "You can add or remove asset after diagnosis", Toast.LENGTH_SHORT).show();
                    }
                return true;
            case R.id.action_assignLogs:
                Intent intent2 = new Intent(TicketHandlingActivity.this, AssignHistoryActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            Intent intent = new Intent(TicketHandlingActivity.this, TicketHistoryListActivity.class);
            startActivity(intent);
            finish();

        }else {
            Intent intent = new Intent(TicketHandlingActivity.this, TicketsListActivity.class);
            startActivity(intent);
            finish();
        }
    }
}