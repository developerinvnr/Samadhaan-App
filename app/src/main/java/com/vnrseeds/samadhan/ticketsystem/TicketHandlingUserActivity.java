package com.vnrseeds.samadhan.ticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TicketHandlingUserActivity extends AppCompatActivity {

    private CustomProgressDialogue customProgressDialogue;
    private ImageButton back_nav;
    private String token;
    private BottomNavigationView bn_view;
    private TicketDetailsPojo ticketDetailsPojo;
    private RoleResponse roleResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_handling_user);
        //Objects.requireNonNull(getSupportActionBar()).hide();
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

    private void setTheme() {
        SharedPreferences.getInstance(getApplicationContext());
        Utils.getInstance(getApplicationContext());
        Utils.getInstance().initConnectionDetector();
        customProgressDialogue = new CustomProgressDialogue(TicketHandlingUserActivity.this);
        token = SharedPreferences.getInstance().getString(SharedPreferences.KEY_TOKEN);
        bn_view = findViewById(R.id.bn_view);
        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
    }

    private void init(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }

        /*back_nav.setOnClickListener(view -> {
            if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
                Intent intent = new Intent(TicketHandlingUserActivity.this, TicketHistoryListActivity.class);
                startActivity(intent);
                finish();

            }else {
                Intent intent = new Intent(TicketHandlingUserActivity.this, TicketsListActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        bn_view.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            int id = item.getItemId();
            if (id==R.id.nav_details){
                loadFragment(new DetailsFragment(), true);
            }else if (id==R.id.nav_intnotes){
                loadFragment(new AddUserNotesFragment(), false);
            }else if (id==R.id.nav_closeticket){
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed")) {
                    loadFragment(new TicketCloseFragment(), false);
                }else {
                    Toast.makeText(TicketHandlingUserActivity.this, "Ticket not resolved", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        });
        bn_view.setSelectedItemId(R.id.nav_details);
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
        inflater.inflate(R.menu.menu_user, menu);
        //MenuItem action_itn = menu.findItem(R.id.action_itn);
        MenuItem action_itnlist = menu.findItem(R.id.action_itnlist);
        //MenuItem action_srn = menu.findItem(R.id.action_srn);
        MenuItem action_srnlist = menu.findItem(R.id.action_srnlist);
        MenuItem action_assignLogs = menu.findItem(R.id.action_assignLogs);
        if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Software")) {
            //action_itn.setVisible(false);
            action_itnlist.setVisible(false);
            //action_srn.setVisible(false);
            action_srnlist.setVisible(false);
        }else if (ticketDetailsPojo.getAssetIsByod().equalsIgnoreCase("1")){
            //action_itn.setVisible(false);
            action_itnlist.setVisible(false);
            action_srnlist.setVisible(false);
        }else {
            if (ticketDetailsPojo.getLocationIsBaseLocation().equalsIgnoreCase("0")) {
                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Reopen") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")) {
                    //action_itn.setVisible(true);
                    action_itnlist.setVisible(true);
                    //action_srn.setVisible(true);
                    action_srnlist.setVisible(true);
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
                    action_itnlist.setVisible(true);
                    action_srnlist.setVisible(true);
                    //action_itn.setVisible(false);
                    //action_srn.setVisible(false);
                }else {
                    //action_itn.setVisible(false);
                    action_itnlist.setVisible(false);
                    //action_srn.setVisible(false);
                    action_srnlist.setVisible(false);
                }
            }else {
                //action_itn.setVisible(false);
                action_itnlist.setVisible(false);
                //action_srn.setVisible(false);
                action_srnlist.setVisible(false);
            }
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(TicketHandlingUserActivity.this, TicketsListActivity.class);
                startActivity(intent);
                finish();
                return true;
            /*case R.id.action_itn:
                if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                    if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Reopen") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")) {
                        Intent intent1 = new Intent(TicketHandlingUserActivity.this, InternalTransferNoteActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }
                return true;*/
            case R.id.action_itnlist:
                if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
                    if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                        Intent intent2 = new Intent(TicketHandlingUserActivity.this, ITNListActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                }
                return true;
            case R.id.action_srnlist:
                if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
                    if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                        Intent intent2 = new Intent(TicketHandlingUserActivity.this, SRNListActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                }
                return true;
            /*case R.id.action_srn:
                if (!ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") && !ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")) {
                    if (ticketDetailsPojo.getServiceType().equalsIgnoreCase("Hardware")) {
                        Intent intent2 = new Intent(TicketHandlingUserActivity.this, ServiceRepaireNoteActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                }
                return true;
            case R.id.action_assignLogs:
                Intent intent2 = new Intent(TicketHandlingUserActivity.this, AssignHistoryActivity.class);
                startActivity(intent2);
                finish();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            Intent intent = new Intent(TicketHandlingUserActivity.this, TicketHistoryListActivity.class);
            startActivity(intent);
            finish();

        }else {
            Intent intent = new Intent(TicketHandlingUserActivity.this, TicketsListActivity.class);
            startActivity(intent);
            finish();
        }
    }
}