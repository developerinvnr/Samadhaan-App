package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketsListPojo;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.ticketsystem.TicketsListActivity;

import java.util.ArrayList;

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.MyViewHolder> implements Filterable {
    private final Context context;
    private final ArrayList<TicketsListPojo> contactList;
    private ArrayList<TicketsListPojo> contactListFiltered;
    private final TicketsListAdapter.TicketsListAdapterListener listener;
    private int index;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_assignto,tv_priority,tv_ticket_title,tv_date,tv_edit_ticket,tv_cancel_ticket;
        private final LinearLayout ll_main,ll_editTicket,ll_raisedfor;
        private final ImageView iv_assetImage,iv_visitdate;
        private final TextView tv_timeToResolve,tv_lastComment,tv_ticketID,tv_assetname,tv_raisedBy,tv_assetIsByod;
        private final TextView tv_visitdate,tv_ticket_status,tv_raisedFor;
        private final CardView cv_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            tv_assetname = itemView.findViewById(R.id.tv_assetname);
            tv_assignto = itemView.findViewById(R.id.tv_assignto);
            tv_priority = itemView.findViewById(R.id.tv_priority);
            tv_ticket_title = itemView.findViewById(R.id.tv_ticket_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_ticket_status = itemView.findViewById(R.id.tv_ticket_status);
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
            cv_status = itemView.findViewById(R.id.cv_status);
            tv_assetIsByod = itemView.findViewById(R.id.tv_assetIsByod);
            iv_visitdate = itemView.findViewById(R.id.iv_visitdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });

        }
    }

    public TicketsListAdapter(Context context, ArrayList<TicketsListPojo> contactList, TicketsListAdapter.TicketsListAdapterListener listener) {
        //contactListFiltered = new ArrayList<>();
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        //this.contactListFiltered.addAll(contactList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tickets_list_view, parent, false);
        return new TicketsListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull TicketsListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final TicketsListPojo ticketsListPojo = contactListFiltered.get(position);

        if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Desktop")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Access Point")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.access_point);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Amplifier")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.amplifier);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Barcode Scanner")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.barcode_scanner);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Biometric Attendance")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.biometric_attendance);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Camera")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.camera);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Desk Phone")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desk_phone);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Digital Video Recorder")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.digital_video_recorder);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("EPABX")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.epabx);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Firewall")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.firewall);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Hard Disk Drive")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Laptop")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.laptop);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Microphone")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.microphone);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Mobile")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.mobile);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Modem")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.modem);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Monitor")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Network Video Recorder")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.network_video_recorder);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Printer")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.printer);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Rack Enclosure")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.rack_enclosure);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Router")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.router);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Scanner")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.scanner);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Server")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.server);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("SFP Transceiver")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.sfp_transreceiver);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Speaker")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.speaker);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("SSD Drive")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Storage Device")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Switch")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.resource_switch);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Tablet")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.tablet);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Television")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.television);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Thin Client")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.thin_client);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("UPS")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.ups);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Wireless Controller")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.wireless_controller);
        }else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Wireless RF Device")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.wireless_rf_device);
        }else {
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }

        if (ticketsListPojo.getRaiseBy().equalsIgnoreCase(ticketsListPojo.getBehalfRaiseBy())){
            holder.ll_raisedfor.setVisibility(View.GONE);
        }else {
            holder.ll_raisedfor.setVisibility(View.VISIBLE);
            holder.tv_raisedFor.setText(ticketsListPojo.getRaiseBy());
        }

        if (ticketsListPojo.getAssetIsByod().equalsIgnoreCase("1")){
            holder.tv_assetIsByod.setText("(BYOD)");
        }else {
            holder.tv_assetIsByod.setVisibility(View.VISIBLE);
        }

        if (ticketsListPojo.getServiceType().equalsIgnoreCase("Hardware")){
            holder.tv_assetname.setText(ticketsListPojo.getTicketAssetAcName());
        }else{
            holder.tv_assetname.setText(ticketsListPojo.getAssetCategory());
        }
        User userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        //Log.e("Id:", userData.getUser_id()+"="+ticketsListPojo.getTicket_raised_by_id());
        if (userData.getUser_id().equalsIgnoreCase(ticketsListPojo.getTicket_raised_by_id()) || userData.getUser_id().equalsIgnoreCase(ticketsListPojo.getTicket_user_id())){
            //Log.e("Status:", ticketsListPojo.getTicketStatus());
            if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Open")) {
                holder.ll_editTicket.setVisibility(View.VISIBLE);
            }else {
                holder.ll_editTicket.setVisibility(View.GONE);
            }
        }else{
            holder.ll_editTicket.setVisibility(View.GONE);
        }
        holder.tv_ticketID.setText("Ticket - "+ticketsListPojo.getTicketNo());
        holder.tv_priority.setText(ticketsListPojo.getPriority());
        if (ticketsListPojo.getTicketIssueOther()!=null && ticketsListPojo.getIssueTitle()!=null){
            holder.tv_ticket_title.setText(ticketsListPojo.getIssueTitle()+","+ticketsListPojo.getTicketIssueOther());
        }else if (ticketsListPojo.getIssueTitle()!=null){
            if (ticketsListPojo.getIssueTitle().length()>12) {
                holder.tv_ticket_title.setText(ticketsListPojo.getIssueTitle().substring(0, 12) + "...");
            }else {
                holder.tv_ticket_title.setText(ticketsListPojo.getIssueTitle());
            }
        }else if (ticketsListPojo.getTicketIssueOther()!=null){
            if (ticketsListPojo.getTicketIssueOther().length()>12) {
                holder.tv_ticket_title.setText(ticketsListPojo.getTicketIssueOther().substring(0, 12) + "...");
            }else {
                holder.tv_ticket_title.setText(ticketsListPojo.getTicketIssueOther());
            }
        }
        holder.tv_date.setText(ticketsListPojo.getTicketDate());
        holder.tv_timeToResolve.setText(ticketsListPojo.getTicketEstimatedHandleDate());
        //holder.tv_raisedBy.setText(ticketsListPojo.getRaiseBy());
        holder.iv_assetImage.setColorFilter(Color.parseColor("#c3c148"));
        if (ticketsListPojo.getTicketReplyDescription()!=null && !ticketsListPojo.getTicketReplyDescription().equalsIgnoreCase("")){
            if (ticketsListPojo.getTicketReplyDescription().length()>12) {
                holder.tv_lastComment.setText(ticketsListPojo.getTicketReplyDescription().substring(0, 12) + "...");
            }else {
                holder.tv_lastComment.setText(ticketsListPojo.getTicketReplyDescription());
            }
        }

        if (ticketsListPojo.getTicketSiteVisitDate()!=null && !ticketsListPojo.getTicketSiteVisitDate().equalsIgnoreCase("")){
            holder.tv_visitdate.setText(ticketsListPojo.getTicketSiteVisitDate());
            holder.iv_visitdate.setVisibility(View.VISIBLE);
        }else {
            holder.tv_visitdate.setText("No visit scheduled");
            holder.iv_visitdate.setVisibility(View.GONE);
        }


        if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Open")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#FFCA43"));
        }else if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Assigned")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#3f51b5"));
        }else if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#FE5247"));
        }else if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Work in Progress")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#FE5247"));
        }else if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#519F40"));
        }else if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Closed")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#837D8C"));
        }else if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Withdraw")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#00BCD4"));
        }else if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Reopen")){
            holder.cv_status.setCardBackgroundColor(Color.parseColor("#FC39AE"));
        }
        holder.tv_ticket_status.setText(ticketsListPojo.getTicketStatus());

        if (ticketsListPojo.getAssignTo()!=null){
            String[] name = ticketsListPojo.getAssignTo().split(" ");
            holder.tv_assignto.setText(name[0]);
        }else {
            holder.tv_assignto.setText("");
        }

        if (ticketsListPojo.getRaiseBy()!=null){
            String[] name = ticketsListPojo.getBehalfRaiseBy().split(" ");
            holder.tv_raisedBy.setText(name[0]);
        }else {
            holder.tv_raisedBy.setText("");
        }

        if (ticketsListPojo.getPriority().equalsIgnoreCase("High")){
            holder.tv_priority.setTextColor(Color.parseColor("#FE5247"));
        }else if (ticketsListPojo.getPriority().equalsIgnoreCase("Critical")){
            holder.tv_priority.setTextColor(Color.parseColor("#FF1C1C"));
        }else if (ticketsListPojo.getPriority().equalsIgnoreCase("Medium")){
            holder.tv_priority.setTextColor(Color.parseColor("#FF8744"));
        }else if (ticketsListPojo.getPriority().equalsIgnoreCase("Low")){
            holder.tv_priority.setTextColor(Color.parseColor("#2A4C10"));
        }

        if (ticketsListPojo.getSrno() % 2 == 0){
            //holder.ll_main.setBackgroundColor(Color.parseColor("#EAEAD3"));
            holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
        }else {
            holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
        }
        /*if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Open")){
            holder.tv_edit_ticket.setVisibility(View.GONE);
        }else {
            holder.tv_edit_ticket.setVisibility(View.VISIBLE);
        }*/
        if ((ticketsListPojo.getTicketStatus().equalsIgnoreCase("Open") || ticketsListPojo.getTicketStatus().equalsIgnoreCase("Assigned")) && ticketsListPojo.getTicketReopenNumber().equalsIgnoreCase("0")){
            holder.tv_cancel_ticket.setVisibility(View.VISIBLE);
        }else {
            holder.tv_cancel_ticket.setVisibility(View.GONE);
        }
        holder.tv_edit_ticket.setOnClickListener(v -> {
            index = position;
            ((TicketsListActivity) context).editTicket(contactListFiltered.get(index));
        });
        holder.tv_cancel_ticket.setOnClickListener(view -> {
            index = position;
            ((TicketsListActivity) context).cancelTicket(contactListFiltered.get(index));
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
                    ArrayList<TicketsListPojo> filteredList = new ArrayList<>();
                    for (TicketsListPojo row : contactList) {
                        if (row.getTicketStatus().toLowerCase().contains(charString.toLowerCase()) || row.getServiceType().equalsIgnoreCase(charString.toLowerCase()) || row.getTicketAssetAcName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getRaiseBy().toLowerCase().contains(charString.toLowerCase()) || row.getPriority().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }
                Log.e("List:", String.valueOf(contactListFiltered));

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<TicketsListPojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface TicketsListAdapterListener {
        void onContactSelected(TicketsListPojo ticketsListPojo);
    }
}
