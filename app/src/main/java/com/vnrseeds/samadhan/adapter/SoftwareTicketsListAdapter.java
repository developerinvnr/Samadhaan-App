package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.pojo.SoftwareTicketsListPojo;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.ticketsystem.TicketsListActivity;
import com.vnrseeds.samadhan.utils.AppConfig;

import java.util.ArrayList;

public class SoftwareTicketsListAdapter extends RecyclerView.Adapter<SoftwareTicketsListAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private ArrayList<SoftwareTicketsListPojo> contactList;
    private ArrayList<SoftwareTicketsListPojo> contactListFiltered;
    private SoftwareTicketsListAdapter.SoftwareTicketsListAdapterListener listener;
    private int index;

    public SoftwareTicketsListAdapter(Context context, ArrayList<SoftwareTicketsListPojo> contactList, SoftwareTicketsListAdapter.SoftwareTicketsListAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_assignto,tv_priority,tv_ticket_title,tv_date,tv_edit_ticket,tv_cancel_ticket,tv_ticketStatus;
        private final LinearLayout ll_main,ll_editTicket,ll_raisedfor,ll_visitDate;
        private final ImageView iv_assetImage;
        private final TextView tv_timeToResolve,tv_lastComment,tv_ticketID,tv_assetname,tv_raisedBy,tv_visitdate,tv_raisedFor;
        private final CardView cv_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            tv_assetname = itemView.findViewById(R.id.tv_assetname);
            tv_assignto = itemView.findViewById(R.id.tv_assignto);
            tv_priority = itemView.findViewById(R.id.tv_priority);
            tv_ticket_title = itemView.findViewById(R.id.tv_ticket_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_ticketStatus = itemView.findViewById(R.id.tv_ticket_status);
            tv_edit_ticket = itemView.findViewById(R.id.tv_edit_ticket);
            ll_editTicket = itemView.findViewById(R.id.ll_editTicket);
            tv_cancel_ticket = itemView.findViewById(R.id.tv_cancel_ticket);
            iv_assetImage = itemView.findViewById(R.id.iv_assetImage);
            tv_timeToResolve = itemView.findViewById(R.id.tv_timeToResolve);
            tv_lastComment = itemView.findViewById(R.id.tv_lastComment);
            tv_ticketID = itemView.findViewById(R.id.tv_ticketID);
            tv_raisedBy = itemView.findViewById(R.id.tv_raisedBy);
            tv_visitdate = itemView.findViewById(R.id.tv_visitdate);
            tv_raisedFor = itemView.findViewById(R.id.tv_raisedFor);
            ll_raisedfor = itemView.findViewById(R.id.ll_raisedfor);
            ll_visitDate = itemView.findViewById(R.id.ll_visitDate);
            cv_status = itemView.findViewById(R.id.cv_status);

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
    public SoftwareTicketsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tickets_list_view, parent, false);
        return new SoftwareTicketsListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SoftwareTicketsListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final SoftwareTicketsListPojo softwareTicketsListPojo=contactListFiltered.get(position);

        Glide.with(context)
                .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/application_softwares/"+softwareTicketsListPojo.getTicketServiceTypeIcon()))
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(holder.iv_assetImage);
        holder.ll_visitDate.setVisibility(View.GONE);
        if (softwareTicketsListPojo.getRaiseBy().equalsIgnoreCase(softwareTicketsListPojo.getBehalfRaiseBy())){
            holder.ll_raisedfor.setVisibility(View.GONE);
        }else {
            holder.ll_raisedfor.setVisibility(View.VISIBLE);
            holder.tv_raisedFor.setText(softwareTicketsListPojo.getRaiseBy());
        }

        if (softwareTicketsListPojo.getServiceType().equalsIgnoreCase("Hardware")){
            holder.tv_assetname.setText(softwareTicketsListPojo.getTicketAssetAcName());
        }else{
            holder.tv_assetname.setText(softwareTicketsListPojo.getAssetCategory());
        }
        User userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        if (userData.getUser_id().equalsIgnoreCase(softwareTicketsListPojo.getTicket_raised_by_id()) || userData.getUser_id().equalsIgnoreCase(softwareTicketsListPojo.getTicket_user_id())){
            holder.ll_editTicket.setVisibility(View.VISIBLE);
        }else{
            holder.ll_editTicket.setVisibility(View.GONE);
        }
        holder.tv_ticketID.setText("Ticket - "+softwareTicketsListPojo.getTicketNo());
        holder.tv_priority.setText(softwareTicketsListPojo.getPriority());

        if (softwareTicketsListPojo.getTicketIssueOther()!=null && softwareTicketsListPojo.getIssueTitle()!=null){
            holder.tv_ticket_title.setText(softwareTicketsListPojo.getIssueTitle()+","+softwareTicketsListPojo.getTicketIssueOther());
        }else if (softwareTicketsListPojo.getIssueTitle()!=null){
            holder.tv_ticket_title.setText(softwareTicketsListPojo.getIssueTitle());
        }else if (softwareTicketsListPojo.getTicketIssueOther()!=null){
            holder.tv_ticket_title.setText(softwareTicketsListPojo.getTicketIssueOther());
        }
        holder.tv_date.setText(softwareTicketsListPojo.getTicketDate());
        holder.tv_timeToResolve.setText(softwareTicketsListPojo.getTicketEstimatedHandleDate());
        holder.tv_raisedBy.setText(softwareTicketsListPojo.getBehalfRaiseBy());
        //holder.iv_assetImage.setColorFilter(Color.parseColor("#c3c148"));
        if (softwareTicketsListPojo.getTicketReplyDescription()!=null && !softwareTicketsListPojo.getTicketReplyDescription().equalsIgnoreCase("")){
            holder.tv_lastComment.setText(softwareTicketsListPojo.getTicketReplyDescription());
        }

        if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Open")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#FFCA43"));
        }else if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Assigned")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#3f51b5"));
        }else if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#FE5247"));
            //holder.cv_status.getBackground().setTint(Color.parseColor("#FE5247"));
        }else if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Work in Progress")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#FE5247"));
        }else if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#519F40"));
        }else if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Closed")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#837D8C"));
        }else if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#00BCD4"));
        }else if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Reopen")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#FC39AE"));
        }
        holder.tv_ticketStatus.setText(softwareTicketsListPojo.getTicketStatus());


        if (softwareTicketsListPojo.getAssignTo()!=null){
            String[] name = softwareTicketsListPojo.getAssignTo().split(" ");
            holder.tv_assignto.setText(name[0]);
        }else {
            holder.tv_assignto.setText("");
        }

        if (softwareTicketsListPojo.getPriority().equalsIgnoreCase("High")){
            holder.tv_priority.setTextColor(Color.parseColor("#FE5247"));
        }else if (softwareTicketsListPojo.getPriority().equalsIgnoreCase("Critical")){
            holder.tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
        }else if (softwareTicketsListPojo.getPriority().equalsIgnoreCase("Medium")){
            holder.tv_priority.setTextColor(Color.parseColor("#FF8744"));
        }else if (softwareTicketsListPojo.getPriority().equalsIgnoreCase("Low")){
            holder.tv_priority.setTextColor(Color.parseColor("#8FFF36"));
        }

        if (softwareTicketsListPojo.getSrno() % 2 == 0){
            //holder.ll_main.setBackgroundColor(Color.parseColor("#EAEAD3"));
            holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
        }else {
            holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
        }
        if (softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Open")){
            holder.tv_edit_ticket.setVisibility(View.VISIBLE);
        }else {
            holder.tv_edit_ticket.setVisibility(View.GONE);
        }
        if ((softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Open") || softwareTicketsListPojo.getTicketStatus().equalsIgnoreCase("Assigned")) && softwareTicketsListPojo.getTicketReopenNumber().equalsIgnoreCase("0")){
            holder.tv_cancel_ticket.setVisibility(View.VISIBLE);
        }else {
            holder.tv_cancel_ticket.setVisibility(View.GONE);
        }
        holder.tv_edit_ticket.setOnClickListener(v -> {
            index = position;
            ((TicketsListActivity) context).editTicketSW(contactListFiltered.get(index));
        });
        holder.tv_cancel_ticket.setOnClickListener(view -> {
            index = position;
            ((TicketsListActivity) context).cancelTicketSW(contactListFiltered.get(index));
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
                    ArrayList<SoftwareTicketsListPojo> filteredList = new ArrayList<>();
                    for (SoftwareTicketsListPojo row : contactList) {
                        if (row.getTicketStatus().toLowerCase().contains(charString.toLowerCase()) || row.getServiceType().toLowerCase().contains(charString.toLowerCase()) || row.getAssetCategory().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getRaiseBy().toLowerCase().contains(charString.toLowerCase()) || row.getPriority().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<SoftwareTicketsListPojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface SoftwareTicketsListAdapterListener {
        void onContactSelected(SoftwareTicketsListPojo softwareTicketsListPojo);
    }
}
