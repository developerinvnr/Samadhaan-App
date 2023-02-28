package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.parser.srnlistparser.Srn;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.ticketsystem.SRNListActivity;

import java.util.ArrayList;
import java.util.List;

public class SRNListAdapter extends RecyclerView.Adapter<SRNListAdapter.MyViewHolder>{
    private final Context context;
    private final List<Srn> contactList;
    private List<Srn> contactListFiltered;
    private final SRNListAdapter.SRNListAdapterListener listener;
    private int index;

    public SRNListAdapter(Context context, List<Srn> contactList, SRNListActivity listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout ll_main;
        private final TextView tv_ticketno,tv_srnNo,tv_itnFrom,tv_itnTo,tv_itnStatus,tv_itnReceivedBy,
                tv_cancel_itn,tv_srnReceivedAt;
        private final CardView cv_received;
        private final ImageView iv_pdfview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            tv_ticketno = itemView.findViewById(R.id.tv_ticketno);
            tv_srnNo = itemView.findViewById(R.id.tv_srnNo);
            tv_itnFrom = itemView.findViewById(R.id.tv_itnFrom);
            tv_itnTo = itemView.findViewById(R.id.tv_itnTo);
            tv_itnStatus = itemView.findViewById(R.id.tv_itnStatus);
            tv_itnReceivedBy = itemView.findViewById(R.id.tv_itnReceivedBy);
            cv_received = itemView.findViewById(R.id.cv_received);
            iv_pdfview = itemView.findViewById(R.id.iv_pdfview);
            tv_cancel_itn = itemView.findViewById(R.id.tv_cancel_itn);
            tv_srnReceivedAt = itemView.findViewById(R.id.tv_srnReceivedAt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public SRNListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_srn_list, parent, false);
        return new SRNListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SRNListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Srn srnList = contactListFiltered.get(position);
        RoleResponse roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        List<Object> roleList = new ArrayList<>();
        roleList=roleResponse.getData();
        holder.tv_ticketno.setText(srnList.getTicketCode());
        holder.tv_srnNo.setText(srnList.getSrnCode());
        if (srnList.getSrnScReceivedAt()!=null) {
            holder.tv_cancel_itn.setVisibility(View.GONE);
            holder.tv_itnStatus.setText("Received");
            holder.tv_itnReceivedBy.setText(srnList.getSrnScReceivedAt());
        }
        if (srnList.getSrnFor().equalsIgnoreCase("User")) {
            holder.tv_itnFrom.setText(srnList.getTicketRaiseByName());
        }else{
            holder.tv_itnFrom.setText(srnList.getSrnFromWorkshopName());
        }
        holder.tv_itnTo.setText(srnList.getVendorName());
        if (srnList.getSrnScReceivedAt()!=null) {
            holder.tv_srnReceivedAt.setText(srnList.getSrnReceivedAt());
        }

        if (srnList.getSrnCancelBy()!=null){
            holder.tv_cancel_itn.setVisibility(View.GONE);
            holder.cv_received.setVisibility(View.GONE);
            holder.tv_itnStatus.setText("Cancelled");
        }else if (srnList.getSrnReceivedBy()!=null){
            holder.tv_itnStatus.setText("Received");
        }

        /*if (srnList.getSrnFor().equalsIgnoreCase("User") && ((roleList.contains("CUSTODIAN") && roleList.size()==1) || roleList.isEmpty())){
            holder.cv_received.setVisibility(View.VISIBLE);
        }*/

        if (srnList.getSrnReceivedBy()!=null){
            //holder.ll_main.setBackgroundColor(Color.parseColor("#EAEAD3"));
            holder.cv_received.setVisibility(View.GONE);
            holder.tv_cancel_itn.setVisibility(View.GONE);
            holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
            holder.cv_received.setCardBackgroundColor(Color.parseColor("#DDDB91"));
        }else {
            holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
            holder.cv_received.setCardBackgroundColor(Color.parseColor("#CFCC57"));
        }

        holder.cv_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                ((SRNListActivity) context).setReceived(contactListFiltered.get(index));
            }
        });

        holder.iv_pdfview.setOnClickListener(view -> {
            index = position;
            ((SRNListActivity) context).viewPDF(contactListFiltered.get(index));
        });

        holder.tv_cancel_itn.setOnClickListener(view -> {
            index = position;
            ((SRNListActivity) context).cancelITN(contactListFiltered.get(index));
        });

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    public interface SRNListAdapterListener {
        void onContactSelected(Srn datum);
    }

}
