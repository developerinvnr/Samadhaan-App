package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.itnlistparser.Itn;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.ticketsystem.ITNListActivity;

import java.util.ArrayList;
import java.util.List;

public class ITNListAdapter extends RecyclerView.Adapter<ITNListAdapter.MyViewHolder> implements Filterable {

    private final Context context;
    private final List<Itn> contactList;
    private List<Itn> contactListFiltered;
    private final ITNListAdapter.ITNListAdapterListener listener;
    private int index;

    public ITNListAdapter(Context context, List<Itn> contactList, ITNListActivity listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout ll_main;
        private final TextView tv_ticketno,tv_itnNo,tv_itnFrom,tv_itnTo,tv_itnStatus,tv_itnReceivedBy,tv_cancel_itn;
        private final CardView cv_received;
        private final ImageView iv_pdfview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            tv_ticketno = itemView.findViewById(R.id.tv_ticketno);
            tv_itnNo = itemView.findViewById(R.id.tv_itnNo);
            tv_itnFrom = itemView.findViewById(R.id.tv_itnFrom);
            tv_itnTo = itemView.findViewById(R.id.tv_itnTo);
            tv_itnStatus = itemView.findViewById(R.id.tv_itnStatus);
            tv_itnReceivedBy = itemView.findViewById(R.id.tv_itnReceivedBy);
            cv_received = itemView.findViewById(R.id.cv_received);
            iv_pdfview = itemView.findViewById(R.id.iv_pdfview);
            tv_cancel_itn = itemView.findViewById(R.id.tv_cancel_itn);

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
    public ITNListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_itn_list, parent, false);
        return new ITNListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ITNListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Itn itnList =contactListFiltered.get(position);
        User userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        RoleResponse roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        List<Object> roleList = new ArrayList<>();
        roleList=roleResponse.getData();
        holder.tv_ticketno.setText(itnList.getTicketCode());
        holder.tv_itnNo.setText(itnList.getItnCode());
        if (itnList.getItnReceivedBy()!=null) {
            holder.tv_itnStatus.setText("Received");
            holder.tv_itnReceivedBy.setText(itnList.getItnReceivedByName());
        }
        if (itnList.getItnFor().equalsIgnoreCase("User")) {
            holder.tv_itnFrom.setText(itnList.getTicketRaiseByName());
        }else{
            holder.tv_itnFrom.setText(itnList.getItnCreatedByName());
        }
        if (itnList.getItnTransferTo().equalsIgnoreCase("User")) {
            holder.tv_itnTo.setText(itnList.getTicketRaiseByName());
        }else if (itnList.getItnTransferTo().equalsIgnoreCase("Workshop")){
            holder.tv_itnTo.setText(itnList.getItnSendToName());
        }
        if (itnList.getItnFor().equalsIgnoreCase("User") && ((roleList.contains("CUSTODIAN") && roleList.size()==1) || roleList.isEmpty())){
            if (userData.getUser_id().equalsIgnoreCase(String.valueOf(itnList.getTicketRaiseBy()))) {
                holder.cv_received.setVisibility(View.GONE);
                //holder.tv_cancel_itn.setVisibility(View.GONE);
            }
        }else {
            holder.cv_received.setVisibility(View.VISIBLE);
            holder.tv_cancel_itn.setVisibility(View.VISIBLE);
        }

        if (itnList.getItnCancelByName()!=null){
            holder.tv_cancel_itn.setVisibility(View.GONE);
            holder.cv_received.setVisibility(View.GONE);
            holder.tv_itnStatus.setText("Cancelled");
        }

        if (itnList.getItnReceivedBy()!=null){
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
                ((ITNListActivity) context).setReceived(contactListFiltered.get(index));
            }
        });

        holder.iv_pdfview.setOnClickListener(view -> {
            index = position;
            ((ITNListActivity) context).viewPDF(contactListFiltered.get(index));
        });

        holder.tv_cancel_itn.setOnClickListener(view -> {
            index = position;
            ((ITNListActivity) context).cancelITN(contactListFiltered.get(index));
        });
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    ArrayList<Itn> filteredList = new ArrayList<>();
                    for (Itn row : contactList) {
                        if (row.getItnFor().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (List<Itn>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ITNListAdapterListener {
        void onContactSelected(Itn datum);
    }
}
