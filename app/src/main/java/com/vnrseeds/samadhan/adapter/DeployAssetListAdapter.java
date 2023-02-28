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
import com.vnrseeds.samadhan.addassetforms.DeployAssetListActivity;
import com.vnrseeds.samadhan.pojo.DeployableAssetListPojo;

import java.util.ArrayList;

public class DeployAssetListAdapter extends RecyclerView.Adapter<DeployAssetListAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private ArrayList<DeployableAssetListPojo> contactList;
    private ArrayList<DeployableAssetListPojo> contactListFiltered;
    private DeployAssetListAdapter.DeployAssetListAdapterListener listener;
    private int index;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_assetCode,tv_assetname,tv_assetType;
        private final LinearLayout ll_main;
        private final ImageView iv_assetImage;
        private final CardView cv_ticket_raise;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_assetCode = itemView.findViewById(R.id.tv_assetCode);
            tv_assetname = itemView.findViewById(R.id.tv_assetname);
            tv_assetType = itemView.findViewById(R.id.tv_assetType);
            iv_assetImage = itemView.findViewById(R.id.iv_assetImage);
            cv_ticket_raise = itemView.findViewById(R.id.cv_ticket_raise);
            /*tv_location = itemView.findViewById(R.id.tv_location);
            tv_dept = itemView.findViewById(R.id.tv_dept);
            tv_section = itemView.findViewById(R.id.tv_section);
            tv_custodian = itemView.findViewById(R.id.tv_custodian);*/
            ll_main = itemView.findViewById(R.id.ll_main);

            iv_assetImage.setColorFilter(Color.parseColor("#c3c148"));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public DeployAssetListAdapter(Context context, ArrayList<DeployableAssetListPojo> contactList, DeployAssetListAdapter.DeployAssetListAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeployAssetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_assetlist, parent, false);

        return new DeployAssetListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeployAssetListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final DeployableAssetListPojo deployableAssetListPojo =contactListFiltered.get(position);
        holder.tv_assetCode.setText(deployableAssetListPojo.getAssetCode());
        holder.tv_assetname.setText(deployableAssetListPojo.getAcName());
        holder.tv_assetType.setText(deployableAssetListPojo.getAcName());
        /*holder.tv_location.setText(assetListData.getLocationName());
        holder.tv_dept.setText(assetListData.getDepartmentName());
        holder.tv_section.setText(assetListData.getDsName());
        holder.tv_custodian.setText(assetListData.getCustodianName());*/

        if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Desktop")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Access Point")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.access_point);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Amplifier")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.amplifier);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Barcode Scanner")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.barcode_scanner);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Biometric Attendance")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.biometric_attendance);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Camera")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.camera);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Desk Phone")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desk_phone);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Digital Video Recorder")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.digital_video_recorder);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("EPABX")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.epabx);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Firewall")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.firewall);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Hard Disk Drive")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Laptop")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.laptop);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Microphone")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.microphone);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Mobile")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.mobile);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Modem")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.modem);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Monitor")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Network Video Recorder")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.network_video_recorder);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Printer")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.printer);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Rack Enclosure")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.rack_enclosure);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Router")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.router);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Scanner")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.scanner);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Server")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.server);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("SFP Transceiver")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.sfp_transreceiver);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Speaker")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.speaker);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("SSD Drive")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Storage Device")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Switch")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.resource_switch);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Tablet")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.tablet);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Television")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.television);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Thin Client")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.thin_client);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("UPS")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.ups);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Wireless Controller")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.wireless_controller);
        }else if (deployableAssetListPojo.getAcName().equalsIgnoreCase("Wireless RF Device")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.wireless_rf_device);
        }else {
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }

        if (deployableAssetListPojo.getSrno() % 2 == 0){
            //holder.ll_main.setBackgroundColor(Color.parseColor("#EAEAD3"));
            holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
            //holder.cv_ticket_raise.setCardBackgroundColor(Color.parseColor(""));
        }else {
            holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
        }

        holder.cv_ticket_raise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                ((DeployAssetListActivity) context).deployAsset(contactListFiltered.get(index));
            }
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
                    ArrayList<DeployableAssetListPojo> filteredList = new ArrayList<>();
                    for (DeployableAssetListPojo row : contactList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getAcName().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<DeployableAssetListPojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface DeployAssetListAdapterListener {
        void onContactSelected(DeployableAssetListPojo deployableAssetListPojo);
    }
}
