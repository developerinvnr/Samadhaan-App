package com.vnrseeds.samadhan.ticketsystem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.submitsuccessparser.SubmitSuccessResponse;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.retrofit.ApiInterface;
import com.vnrseeds.samadhan.retrofit.RetrofitClient;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.CustomProgressDialogue;
import com.vnrseeds.samadhan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import kotlin.io.TextStreamsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketCloseFragment extends Fragment {

    private static final String TAG = "TicketCloseFragment";
    private CustomProgressDialogue customProgressDialogue;
    private String token;
    private Button button_reopen;
    private Button button_closeticket;
    private TicketDetailsPojo ticketDetailsPojo;
    private EditText et_message;

    private TextView tv_ticketdate;
    private TextView tv_ticketno;
    private TextView tv_ticket_title;
    private TextView tv_priority;
    private TextView tv_more;
    private String rating="";
    private ImageView iv_moodBadImage;
    private ImageView iv_neutralImage;
    private ImageView iv_satisfiedImage;
    private TextView tv_moodBad;
    private TextView tv_neutral;
    private TextView tv_satisfied;
    private LinearLayout ll_closedDetails;
    private TextView tv_closedDate;
    private TextView tv_closedBy;
    private LinearLayout ll_rating;

    public TicketCloseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_close, container, false);
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
        button_reopen = view.findViewById(R.id.button_reopen);
        button_closeticket = view.findViewById(R.id.button_closeticket);
        et_message = view.findViewById(R.id.et_message);
        tv_ticketdate = view.findViewById(R.id.tv_ticketdate);
        tv_ticketno = view.findViewById(R.id.tv_ticketno);
        tv_ticket_title = view.findViewById(R.id.tv_ticket_title);
        tv_priority = view.findViewById(R.id.tv_priority);
        tv_more = view.findViewById(R.id.tv_more);
        iv_moodBadImage = view.findViewById(R.id.iv_moodBadImage);
        tv_moodBad = view.findViewById(R.id.tv_moodBad);
        iv_neutralImage = view.findViewById(R.id.iv_neutralImage);
        tv_neutral = view.findViewById(R.id.tv_neutral);
        iv_satisfiedImage = view.findViewById(R.id.iv_satisfiedImage);
        tv_satisfied = view.findViewById(R.id.tv_satisfied);
        ll_closedDetails = view.findViewById(R.id.ll_closedDetails);
        tv_closedDate = view.findViewById(R.id.tv_closedDate);
        tv_closedBy = view.findViewById(R.id.tv_closedBy);
        ll_rating = view.findViewById(R.id.ll_rating);

        ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed")){
            ll_closedDetails.setVisibility(View.VISIBLE);
            tv_closedDate.setText(ticketDetailsPojo.getTicketCloseDate());
            tv_closedBy.setText(ticketDetailsPojo.getTicketCloseBy());

            if (ticketDetailsPojo.getTicketCloseRating().equalsIgnoreCase("") || ticketDetailsPojo.getTicketCloseRating()!=null){
                ll_rating.setVisibility(View.GONE);
            }else {
                ll_rating.setVisibility(View.VISIBLE);
            }
        }
        if (ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Closed") || ticketDetailsPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            et_message.setText(ticketDetailsPojo.getTicketCloseDescription());
            et_message.setEnabled(false);
            button_reopen.setVisibility(View.GONE);
            button_closeticket.setVisibility(View.GONE);
            iv_moodBadImage.setEnabled(false);
            iv_neutralImage.setEnabled(false);
            iv_satisfiedImage.setEnabled(false);
            if (ticketDetailsPojo.getTicketCloseRating()!=null && !ticketDetailsPojo.getTicketCloseRating().equalsIgnoreCase("")){
                if (ticketDetailsPojo.getTicketCloseRating().equalsIgnoreCase("Not Satisfied")){
                    iv_moodBadImage.setColorFilter(Color.parseColor("#C3C148"));
                    iv_neutralImage.setColorFilter(Color.parseColor("#49454F"));
                    iv_satisfiedImage.setColorFilter(Color.parseColor("#49454F"));

                    tv_moodBad.setTextColor(Color.parseColor("#C3C148"));
                    tv_neutral.setTextColor(Color.parseColor("#49454F"));
                    tv_satisfied.setTextColor(Color.parseColor("#49454F"));

                }else if (ticketDetailsPojo.getTicketCloseRating().equalsIgnoreCase("Neutral")){
                    iv_moodBadImage.setColorFilter(Color.parseColor("#49454F"));
                    iv_neutralImage.setColorFilter(Color.parseColor("#C3C148"));
                    iv_satisfiedImage.setColorFilter(Color.parseColor("#49454F"));

                    tv_moodBad.setTextColor(Color.parseColor("#49454F"));
                    tv_neutral.setTextColor(Color.parseColor("#C3C148"));
                    tv_satisfied.setTextColor(Color.parseColor("#49454F"));
                }else if (ticketDetailsPojo.getTicketCloseRating().equalsIgnoreCase("Very Satisfied")){
                    iv_moodBadImage.setColorFilter(Color.parseColor("#49454F"));
                    iv_neutralImage.setColorFilter(Color.parseColor("#49454F"));
                    iv_satisfiedImage.setColorFilter(Color.parseColor("#C3C148"));

                    tv_moodBad.setTextColor(Color.parseColor("#49454F"));
                    tv_neutral.setTextColor(Color.parseColor("#49454F"));
                    tv_satisfied.setTextColor(Color.parseColor("#C3C148"));

                }
            }
        }else {
            et_message.setEnabled(true);
            button_reopen.setVisibility(View.VISIBLE);
            button_closeticket.setVisibility(View.VISIBLE);
            iv_moodBadImage.setEnabled(true);
            iv_neutralImage.setEnabled(true);
            iv_satisfiedImage.setEnabled(true);
        }

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

        if (ticketDetailsPojo.getPriority().equalsIgnoreCase("High")){
            tv_priority.setTextColor(Color.parseColor("#FE5247"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Critical")){
            tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Medium")){
            tv_priority.setTextColor(Color.parseColor("#FF8744"));
        }else if (ticketDetailsPojo.getPriority().equalsIgnoreCase("Low")){
            tv_priority.setTextColor(Color.parseColor("#8FFF36"));
        }
    }

    private void init() {
       /* ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String rating = tv_rating.getText().toString().trim();
                if (v==0.0) {
                    tv_rating.setText("");
                }else {
                    tv_rating.setText(String.valueOf(v));
                }
            }
        });*/

        iv_moodBadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_moodBadImage.setColorFilter(Color.parseColor("#C3C148"));
                iv_neutralImage.setColorFilter(Color.parseColor("#49454F"));
                iv_satisfiedImage.setColorFilter(Color.parseColor("#49454F"));

                tv_moodBad.setTextColor(Color.parseColor("#C3C148"));
                tv_neutral.setTextColor(Color.parseColor("#49454F"));
                tv_satisfied.setTextColor(Color.parseColor("#49454F"));

                rating="Not Satisfied";
            }
        });

        iv_neutralImage.setOnClickListener(view -> {
            iv_moodBadImage.setColorFilter(Color.parseColor("#49454F"));
            iv_neutralImage.setColorFilter(Color.parseColor("#C3C148"));
            iv_satisfiedImage.setColorFilter(Color.parseColor("#49454F"));

            tv_moodBad.setTextColor(Color.parseColor("#49454F"));
            tv_neutral.setTextColor(Color.parseColor("#C3C148"));
            tv_satisfied.setTextColor(Color.parseColor("#49454F"));

            rating="Neutral";
        });

        iv_satisfiedImage.setOnClickListener(view -> {
            iv_moodBadImage.setColorFilter(Color.parseColor("#49454F"));
            iv_neutralImage.setColorFilter(Color.parseColor("#49454F"));
            iv_satisfiedImage.setColorFilter(Color.parseColor("#C3C148"));

            tv_moodBad.setTextColor(Color.parseColor("#49454F"));
            tv_neutral.setTextColor(Color.parseColor("#49454F"));
            tv_satisfied.setTextColor(Color.parseColor("#C3C148"));

            rating="Very Satisfied";
        });

        button_closeticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_message.getText().toString().trim();
                if (message.equalsIgnoreCase("")){
                    Toast.makeText(getActivity(), "Enter message", Toast.LENGTH_LONG).show();
                }else {
                    closeTicketSubmit(message,rating);
                }
            }
        });

        button_reopen.setOnClickListener(new View.OnClickListener() {
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
                tv_lastlot.setText("Re-open Ticket");
                cb_show_to_user.setVisibility(View.GONE);
                button_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = et_message.getText().toString();
                        if (message.equalsIgnoreCase("")){
                            Toast.makeText(getActivity(), "Enter message", Toast.LENGTH_LONG).show();
                        }else {
                            dialog.cancel();
                            submitReopen(message);
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

    private void submitReopen(String message) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTicketReopenInfo("application/json", "Bearer " + token, "Reopen", ticketDetailsPojo.getTicketId(), message);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        Intent intent = new Intent(getActivity(), TicketsListActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(), submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
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

    private void closeTicketSubmit(String message, String rating) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        customProgressDialogue.show();
        Call<SubmitSuccessResponse> call = apiInterface.getTicketUpdateCloseInfo("application/json", "Bearer " + token, "Close", ticketDetailsPojo.getTicketId(), rating, message);
        call.enqueue(new Callback<SubmitSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<SubmitSuccessResponse> call, @NonNull Response<SubmitSuccessResponse> response) {
                if (response.isSuccessful()) {
                    customProgressDialogue.cancel();
                    SubmitSuccessResponse submitSuccessResponse = response.body();
                    if (submitSuccessResponse != null) {
                        //Utils.getInstance().showAlert(getActivity(), submitSuccessResponse.getMessage());
                        Toast.makeText(getActivity(), submitSuccessResponse.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), TicketsListActivity.class);
                        startActivity(intent);
                        getActivity().finish();
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