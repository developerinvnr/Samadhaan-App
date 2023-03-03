package com.vnrseeds.samadhan.ticketsystem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.adapter.TicketViewInternalNotesAdapter;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.parser.ticketviewparser.InternalNoteLog;
import com.vnrseeds.samadhan.parser.ticketviewparser.TicketViewResponse;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternalNotesFragment extends Fragment {
    private static final String TAG = "InternalNotesFragment";
    private RecyclerView lv_commentlist;
    private Button button_addnotes;
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private TicketDetailsPojo ticketDetailsPojo;
    private List<InternalNoteLog> ticketViewArray;
    private TicketViewInternalNotesAdapter ticketViewAdapter;
    private TextView tv_ticketdate;
    private TextView tv_ticketno;
    private TextView tv_ticket_title;
    private TextView tv_priority;
    private TextView tv_more;
    private CheckBox cb_show_to_user;
    private String show_to_user_flg="0";

    public InternalNotesFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_internal_notes, container, false);
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
        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        lv_commentlist = view.findViewById(R.id.lv_commentlist);
        button_addnotes = view.findViewById(R.id.button_addnotes);
        tv_ticketdate = view.findViewById(R.id.tv_ticketdate);
        tv_ticketno = view.findViewById(R.id.tv_ticketno);
        tv_ticket_title = view.findViewById(R.id.tv_ticket_title);
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
        tv_priority = view.findViewById(R.id.tv_priority);
        tv_more = view.findViewById(R.id.tv_more);

        tv_ticketdate.setText(ticketDetailsPojo.getTicketDate());
        tv_ticketno.setText(ticketDetailsPojo.getTicketNo());
        tv_priority.setText(ticketDetailsPojo.getPriority());

        if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
            tv_priority.setTextColor(Color.parseColor("#FE5247"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
            tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
            tv_priority.setTextColor(Color.parseColor("#FF8744"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
            tv_priority.setTextColor(Color.parseColor("#8FFF36"));
        }

        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            button_addnotes.setVisibility(View.GONE);
        }else {
            button_addnotes.setVisibility(View.VISIBLE);
        }

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
                TextInputLayout til_lable = dialog.findViewById(R.id.til_lable);
                CheckBox cb_show_to_user = dialog.findViewById(R.id.cb_show_to_user);
                til_lable.setHint("Internal Note");
                cb_show_to_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cb_show_to_user.isChecked()){
                            show_to_user_flg="1";
                        }else{
                            show_to_user_flg="0";
                        }
                    }
                });
                button_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = et_message.getText().toString().trim();
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


                if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Open")){
                    tv_ticketStatus.setBackgroundColor(Color.parseColor("#FFCA43"));
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Assigned")){
                    tv_ticketStatus.setBackgroundColor(Color.parseColor("#3f51b5"));
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")){
                    tv_ticketStatus.setBackgroundColor(Color.parseColor("#FE5247"));
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Work in Progress")){
                    tv_ticketStatus.setBackgroundColor(Color.parseColor("#FE5247"));
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
                    tv_ticketStatus.setBackgroundColor(Color.parseColor("#519F40"));
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed")){
                    tv_ticketStatus.setBackgroundColor(Color.parseColor("#837D8C"));
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
                    tv_ticketStatus.setBackgroundColor(Color.parseColor("#00BCD4"));
                }else if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Reopen")){
                    tv_ticketStatus.setBackgroundColor(Color.parseColor("#FC39AE"));
                }
                tv_ticketStatus.setText(ticketDetailsPojo.getTicketStatus());


                if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
                    tv_priority.setTextColor(Color.parseColor("#FE5247"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
                    tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
                    tv_priority.setTextColor(Color.parseColor("#FF8744"));
                }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
                    tv_priority.setTextColor(Color.parseColor("#8FFF36"));
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
                    ticketViewArray = ticketViewResponse.getData().getInternalNoteLogs();
                    ticketViewAdapter = new TicketViewInternalNotesAdapter(getContext(), ticketViewArray);
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
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateNoteInfo("application/json", "Bearer " + token, "Internal_Note", ticketDetailsPojo.getTicketId(), message, show_to_user_flg);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        getFragmentManager().beginTransaction().detach(new TicketHandlingFragment()).attach(new TicketHandlingFragment()).commit();
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
}