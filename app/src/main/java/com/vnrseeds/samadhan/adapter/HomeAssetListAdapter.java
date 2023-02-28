package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.pojo.AssetListPojo;

import java.util.ArrayList;

public class HomeAssetListAdapter extends RecyclerView.Adapter<HomeAssetListAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<AssetListPojo> contactList;
    private ArrayList<AssetListPojo> contactListFiltered;
    private final HomeAssetListAdapter.HomeAssetListAdapterListener listener;
    private int index;



    public HomeAssetListAdapter(Context context, ArrayList<AssetListPojo> contactList, HomeAssetListAdapter.HomeAssetListAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_asseticon;
        private final TextView tv_assetID,tv_assetcategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_asseticon = itemView.findViewById(R.id.iv_asseticon);
            tv_assetID = itemView.findViewById(R.id.tv_assetID);
            tv_assetcategory = itemView.findViewById(R.id.tv_assetcategory);

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
    public HomeAssetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_home_assetlist, parent, false);
        return new HomeAssetListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomeAssetListAdapter.MyViewHolder holder, int position) {
        final AssetListPojo assetListPojo=contactListFiltered.get(position);

        if (assetListPojo.getAc_name().equalsIgnoreCase("Desktop")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.desktop);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Access Point")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.access_point);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Amplifier")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.amplifier);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Barcode Scanner")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.barcode_scanner);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Biometric Attendance")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.biometric_attendance);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Camera")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.camera);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Desk Phone")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.desk_phone);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Digital Video Recorder")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.digital_video_recorder);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("EPABX")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.epabx);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Firewall")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.firewall);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Hard Disk Drive")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.storage_device);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Laptop")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.laptop);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Microphone")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.microphone);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Mobile")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.mobile);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Modem")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.modem);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Monitor")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.desktop);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Network Video Recorder")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.network_video_recorder);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Printer")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.printer);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Rack Enclosure")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.rack_enclosure);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Router")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.router);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Scanner")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.scanner);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Server")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.server);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("SFP Transceiver")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.sfp_transreceiver);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Speaker")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.speaker);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("SSD Drive")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.storage_device);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Storage Device")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.storage_device);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Switch")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.resource_switch);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Tablet")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.tablet);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Television")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.television);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Thin Client")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.thin_client);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("UPS")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.ups);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Wireless Controller")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.wireless_controller);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Wireless RF Device")){
            holder.iv_asseticon.setBackgroundResource(R.drawable.wireless_rf_device);
        }else {
            holder.iv_asseticon.setBackgroundResource(R.drawable.desktop);
        }

        holder.iv_asseticon.setColorFilter(Color.parseColor("#49454F"));
        /*Glide.with(context)
                .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/asset_category/"+assetListPojo.getAc_icon()))
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(holder.iv_asseticon);*/

        //Uri uri = Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/asset_category/"+assetListPojo.getAc_icon());

        holder.tv_assetcategory.setText(assetListPojo.getAc_name());
        holder.tv_assetcategory.setSelected(true);
        if (assetListPojo.getAssetIsByod().equalsIgnoreCase("0")) {
            holder.tv_assetID.setText(assetListPojo.getAsset_code());
        }else {
            holder.tv_assetID.setText("BYOD");
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public interface HomeAssetListAdapterListener {
        void onContactSelected(AssetListPojo assetListPojo);
    }
}
