package com.vnrseeds.samadhan.addassetforms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vnrseeds.samadhan.R;

import java.util.Objects;

public class EPBXActivity extends AppCompatActivity {

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
    private LinearLayout ll_mictype;
    private LinearLayout ll_intercom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_p_b_x);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setTheme();
        init();
    }

    private void setTheme() {
        TextView tv_category = findViewById(R.id.tv_category);
        String category = getIntent().getStringExtra("category");
        tv_category.setText(category);

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
        ll_mictype = findViewById(R.id.ll_mictype);
        ll_intercom = findViewById(R.id.ll_intercom);
        if (category.equalsIgnoreCase("EPBX")){
            ll_intercom.setVisibility(View.VISIBLE);
        }

        if (category.equalsIgnoreCase("Speaker")){
            techinfo_form.setVisibility(View.GONE);
            techinfo_tab.setVisibility(View.GONE);
            nwinfo_form.setVisibility(View.GONE);
            nwinfo_tab.setVisibility(View.GONE);
            swinfo_form.setVisibility(View.GONE);
            swinfo_tab.setVisibility(View.GONE);
        }

    }

    private void init(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorToolbarDark));
        }

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
    }
}