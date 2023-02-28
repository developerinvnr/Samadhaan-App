package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.ticketviewparser.HandleReplyLog;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class TicketViewAdapter extends RecyclerView.Adapter<TicketViewAdapter.MyViewHolder> {
    private Context context;
    private List<HandleReplyLog> contactList;
    private List<String> imageList=new ArrayList<>();
    private int image_position=0;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_name,tv_date,tv_message,tv_dateTime,tv_logTitle;
        private final LinearLayout ll_main,ll_issuephoto,ll_nextPrev;
        private final ImageView iv_issuephoto,iv_previous,iv_next;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            ll_issuephoto = itemView.findViewById(R.id.ll_issuephoto);
            ll_nextPrev = itemView.findViewById(R.id.ll_nextPrev);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_dateTime = itemView.findViewById(R.id.tv_dateTime);
            tv_logTitle = itemView.findViewById(R.id.tv_logTitle);
            iv_issuephoto = itemView.findViewById(R.id.iv_issuephoto);
            iv_previous = itemView.findViewById(R.id.iv_previous);
            iv_next = itemView.findViewById(R.id.iv_next);
        }
    }

    public TicketViewAdapter(Context context, List<HandleReplyLog> ticketViewArray) {
        this.context = context;
        this.contactList = ticketViewArray;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public TicketViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ticket_view, parent, false);
        return new TicketViewAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TicketViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final HandleReplyLog handleReplyLog =contactList.get(position);

        if (handleReplyLog.getTicketStatus().equalsIgnoreCase("Estimated")){
            holder.tv_dateTime.setVisibility(View.VISIBLE);
            holder.ll_issuephoto.setVisibility(View.GONE);
            holder.tv_name.setText(handleReplyLog.getEstimatedCreatedBy());
            holder.tv_date.setText(handleReplyLog.getEstimatedCreatedDate());
            //holder.tv_date.setVisibility(View.GONE);
            holder.tv_logTitle.setText("Estimation:");
            if (handleReplyLog.getEstimatedDescription()==null){
                holder.tv_message.setVisibility(View.GONE);
            }else {
                holder.tv_message.setVisibility(View.VISIBLE);
                holder.tv_message.setText(handleReplyLog.getEstimatedDescription());
            }
            holder.tv_dateTime.setText("Estimated Date & Time To Resolve : "+handleReplyLog.getEstimatedDate());
        }else if (handleReplyLog.getTicketStatus().equalsIgnoreCase("Diagnosis")){
            holder.tv_logTitle.setText("Diagnosis:");
            holder.ll_issuephoto.setVisibility(View.GONE);
            holder.tv_name.setText(handleReplyLog.getHandleCreatedBy());
            holder.tv_date.setText(handleReplyLog.getHandleDate());
            holder.tv_message.setText(handleReplyLog.getHandleDescription());
            holder.tv_dateTime.setVisibility(View.GONE);
            if (handleReplyLog.getHandleRequestType()!=null || !handleReplyLog.getHandleRequestType().equalsIgnoreCase("")){
                if (handleReplyLog.getHandleRequestType().equalsIgnoreCase("Change Request")){
                    holder.tv_dateTime.setText("Request Type : "+handleReplyLog.getHandleRequestType()+" and Status : "+handleReplyLog.getHandleChangeRequestStatus());
                }else {
                    holder.tv_dateTime.setText("Request Type : "+handleReplyLog.getHandleRequestType());
                }
            }
            holder.tv_dateTime.setText("Request Type : "+handleReplyLog.getHandleRequestType()+" and Status : "+handleReplyLog.getHandleChangeRequestStatus());

        }else if (handleReplyLog.getTicketStatus().equalsIgnoreCase("Reply")){
            holder.tv_name.setText(handleReplyLog.getReplyCreatedBy());
            holder.tv_date.setText(handleReplyLog.getReplyDate());
            holder.tv_message.setText(handleReplyLog.getReplyDescription());
            holder.tv_logTitle.setText("Reply From User:");
            holder.tv_dateTime.setVisibility(View.GONE);
            imageList=handleReplyLog.getReplyFiles();
            //Log.e("Images List", String.valueOf(imageList.get(0)));
            if (!imageList.isEmpty()) {
                holder.ll_issuephoto.setVisibility(View.VISIBLE);
                holder.ll_nextPrev.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + imageList.get(0)))
                        .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                        .into(holder.iv_issuephoto);
            }
        }else if (handleReplyLog.getTicketStatus().equalsIgnoreCase("Reopen")){
            holder.ll_issuephoto.setVisibility(View.GONE);
            holder.tv_name.setText(handleReplyLog.getReopenCreatedBy());
            holder.tv_date.setText(handleReplyLog.getReopenDate());
            holder.tv_message.setText(handleReplyLog.getReopenDescription());
            holder.tv_logTitle.setText("Reopen:");
            holder.tv_dateTime.setVisibility(View.GONE);
        }else if (handleReplyLog.getTicketStatus().equalsIgnoreCase("Resolved")){
            holder.tv_dateTime.setVisibility(View.GONE);
            holder.tv_name.setText(handleReplyLog.getResolveCreatedBy());
            holder.tv_date.setText(handleReplyLog.getResolveDate());
            holder.tv_message.setText(handleReplyLog.getResolveDescription());
            holder.tv_logTitle.setText("Resolve Note:");
            if (handleReplyLog.getResolveFile()!=null || !handleReplyLog.getResolveFile().isEmpty()){
                holder.ll_issuephoto.setVisibility(View.VISIBLE);
                holder.ll_nextPrev.setVisibility(View.GONE);
                Glide.with(context)
                        .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + handleReplyLog.getResolveFile()))
                        .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                        .into(holder.iv_issuephoto);
            }else {
                holder.ll_issuephoto.setVisibility(View.GONE);
            }
        }else if (handleReplyLog.getTicketStatus().equalsIgnoreCase("Withdraw")){
            holder.tv_logTitle.setText("Withdraw Note:");
            holder.ll_issuephoto.setVisibility(View.GONE);
            holder.tv_name.setText(handleReplyLog.getWithdrawCreatedBy());
            holder.tv_date.setText(handleReplyLog.getWithdrawDate());
            holder.tv_message.setText(handleReplyLog.getWithdrawDescription());
            holder.tv_dateTime.setVisibility(View.GONE);
        }else if (handleReplyLog.getTicketStatus().equalsIgnoreCase("Closed")){
            holder.tv_logTitle.setText("Withdraw Note:");
            holder.ll_issuephoto.setVisibility(View.GONE);
            holder.tv_name.setText(handleReplyLog.getCloseCreatedBy());
            holder.tv_date.setText(handleReplyLog.getCloseDate());
            holder.tv_message.setText(handleReplyLog.getCloseDescription());
            holder.tv_dateTime.setVisibility(View.GONE);
        }

        User userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        if (userData.getUser_id().equalsIgnoreCase(handleReplyLog.getHandleCreatedById())){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = RelativeLayout.ALIGN_LEFT;
            layoutParams.setMargins(100, 30, 0, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }else if (userData.getUser_id().equalsIgnoreCase(handleReplyLog.getEstimatedCreatedById())){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = RelativeLayout.ALIGN_LEFT;
            layoutParams.setMargins(100, 30, 0, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }else if (userData.getUser_id().equalsIgnoreCase(handleReplyLog.getResolveCreatedById())){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = RelativeLayout.ALIGN_LEFT;
            layoutParams.setMargins(100, 30, 0, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }else if (userData.getUser_id().equalsIgnoreCase(handleReplyLog.getWithdrawCreatedById())){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = RelativeLayout.ALIGN_LEFT;
            layoutParams.setMargins(100, 30, 0, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }else if (userData.getUser_id().equalsIgnoreCase(handleReplyLog.getCloseCreatedById())){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = RelativeLayout.ALIGN_LEFT;
            layoutParams.setMargins(100, 30, 0, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = RelativeLayout.ALIGN_LEFT;
            layoutParams.setMargins(0, 30, 100, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }

        holder.iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image_position < imageList.size() - 1) {
                    // increase the position by 1
                    image_position++;
                    Glide.with(context)
                            .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + imageList.get(image_position)))
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(holder.iv_issuephoto);
                    //iv_issuephoto.setImageURI(imageList.get(position));
                } else {
                    Toast.makeText(context, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image_position > 0) {
                    //decrease the position by 1
                    image_position--;
                    Glide.with(context)
                            .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/tickets/" + imageList.get(image_position)))
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(holder.iv_issuephoto);
                }else {
                    Toast.makeText(context, "First Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface TicketViewAdapterListener {
    }
}
