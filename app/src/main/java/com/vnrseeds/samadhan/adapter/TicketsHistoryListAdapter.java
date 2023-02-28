package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketsListPojo;
import com.vnrseeds.samadhan.utils.AppConfig;

import java.util.ArrayList;

public class TicketsHistoryListAdapter extends RecyclerView.Adapter<TicketsHistoryListAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<TicketsListPojo> ticketListArray;
    private ArrayList<TicketsListPojo> contactListFiltered;
    private TicketsHistoryListAdapter.TicketsHistoryListAdapterListener listener;
    private int index;

    public TicketsHistoryListAdapter(Context context, ArrayList<TicketsListPojo> ticketListArray, TicketsHistoryListAdapter.TicketsHistoryListAdapterListener listener) {
        this.context = context;
        this.ticketListArray = ticketListArray;
        this.contactListFiltered = ticketListArray;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout ll_main;
        private final TextView tv_assetname,tv_timeToResolve,tv_ticketno,tv_ticketDate,tv_resolvedBy,tv_ticketStatus,tv_ticketServiceType,tv_ticket_title;
        private final ImageView iv_assetImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            tv_assetname = itemView.findViewById(R.id.tv_assetname);
            tv_ticket_title = itemView.findViewById(R.id.tv_ticket_title);
            tv_timeToResolve = itemView.findViewById(R.id.tv_timeToResolve);
            tv_ticketno = itemView.findViewById(R.id.tv_ticketno);
            tv_ticketDate = itemView.findViewById(R.id.tv_ticketDate);
            tv_resolvedBy = itemView.findViewById(R.id.tv_resolvedBy);
            tv_ticketStatus = itemView.findViewById(R.id.tv_ticketStatus);
            tv_ticketServiceType = itemView.findViewById(R.id.tv_ticketServiceType);
            iv_assetImage = itemView.findViewById(R.id.iv_assetImage);

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
    public TicketsHistoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tickethistory_list, parent, false);
        return new TicketsHistoryListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TicketsHistoryListAdapter.MyViewHolder holder, int position) {
        final TicketsListPojo ticketsListPojo=contactListFiltered.get(position);
        if (ticketsListPojo.getServiceType().equalsIgnoreCase("Hardware")){
            holder.tv_assetname.setText(ticketsListPojo.getTicketAssetAcName());
        }else{
            holder.tv_assetname.setText(ticketsListPojo.getAssetCategory());
        }
        /*if (ticketsListPojo.getIssueName()!=null){
            holder.tv_ticket_title.setText(ticketsListPojo.getIssueName());
        }else {
            holder.tv_ticket_title.setText(ticketsListPojo.getIssueTitle());
        }*/
        if (ticketsListPojo.getTicketIssueOther()!=null && ticketsListPojo.getIssueTitle()!=null){
            holder.tv_ticket_title.setText(ticketsListPojo.getIssueTitle()+","+ticketsListPojo.getTicketIssueOther());
        }else if (ticketsListPojo.getIssueTitle()!=null){
            holder.tv_ticket_title.setText(ticketsListPojo.getIssueTitle());
        }else if (ticketsListPojo.getTicketIssueOther()!=null){
            holder.tv_ticket_title.setText(ticketsListPojo.getTicketIssueOther());
        }
        holder.tv_timeToResolve.setText(ticketsListPojo.getTicketEstimatedHandleDate());
        holder.tv_ticketno.setText(ticketsListPojo.getTicketNo());
        holder.tv_ticketDate.setText(ticketsListPojo.getTicketDate());
        holder.tv_resolvedBy.setText(ticketsListPojo.getTicketResolveBy());
        holder.tv_ticketStatus.setText(ticketsListPojo.getTicketStatus());
        holder.tv_ticketServiceType.setText(ticketsListPojo.getServiceType());
        //holder.iv_assetImage.setImageURI(null);
        if (ticketsListPojo.getServiceType().equalsIgnoreCase("Hardware")) {
            if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Desktop")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Access Point")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.access_point);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Amplifier")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.amplifier);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Barcode Scanner")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.barcode_scanner);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Biometric Attendance")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.biometric_attendance);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Camera")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.camera);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Desk Phone")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.desk_phone);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Digital Video Recorder")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.digital_video_recorder);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("EPABX")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.epabx);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Firewall")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.firewall);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Hard Disk Drive")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Laptop")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.laptop);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Microphone")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.microphone);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Mobile")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.mobile);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Modem")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.modem);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Monitor")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Network Video Recorder")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.network_video_recorder);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Printer")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.printer);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Rack Enclosure")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.rack_enclosure);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Router")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.router);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Scanner")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.scanner);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Server")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.server);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("SFP Transceiver")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.sfp_transreceiver);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Speaker")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.speaker);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("SSD Drive")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Storage Device")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Switch")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.resource_switch);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Tablet")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.tablet);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Television")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.television);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Thin Client")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.thin_client);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("UPS")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.ups);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Wireless Controller")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.wireless_controller);
            } else if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("Wireless RF Device")) {
                holder.iv_assetImage.setBackgroundResource(R.drawable.wireless_rf_device);
            }
        }else {
            Glide.with(context)
                    .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/application_softwares/"+ticketsListPojo.getTicketServiceTypeIcon()))
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .into(holder.iv_assetImage);
        }

        if (ticketsListPojo.getSrno() % 2 == 0){
            holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
        }else {
            holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
        }
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
                    contactListFiltered = ticketListArray;
                } else {
                    ArrayList<TicketsListPojo> filteredList = new ArrayList<>();
                    for (TicketsListPojo row : ticketListArray) {
                        //name match condition. this might differ depending on your requirement
                        //here we are looking for name or phone number match
                        if (row.getTicketStatus().toLowerCase().contains(charString.toLowerCase()) || row.getServiceType().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<TicketsListPojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface TicketsHistoryListAdapterListener {
        void onContactSelected(TicketsListPojo ticketsListPojo);
    }
}
