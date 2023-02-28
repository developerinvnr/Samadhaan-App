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
import com.vnrseeds.samadhan.parser.roleparser.RoleResponse;
import com.vnrseeds.samadhan.pojo.AssetListPojo;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.ticketsystem.TicketAssetListActivity;

import java.util.ArrayList;

public class TicketAssetListAdapter extends RecyclerView.Adapter<TicketAssetListAdapter.MyViewHolder> implements Filterable {
    private final Context context;
    private final ArrayList<AssetListPojo> contactList;
    private ArrayList<AssetListPojo> contactListFiltered;
    private final TicketAssetListAdapter.TicketAssetListAdapterListener listener;
    private int index;

    public TicketAssetListAdapter(Context context, ArrayList<AssetListPojo> contactList, TicketAssetListActivity listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
        setHasStableIds(true);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_assetname,tv_assetType,tv_assetCode,tv_assetbrand,tv_assetmodel, tv_serialno,
                tv_location,tv_dept,tv_section,lable_assetType,lable_assetCode,tv_assetIsByod;
        private final LinearLayout ll_main,ll_deptInfo,ll_custodian,ll_software,ll_hardware;
        private final CardView cv_ticket_raise;
        private final ImageView iv_assetImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_assetCode = itemView.findViewById(R.id.tv_assetCode);
            tv_assetname = itemView.findViewById(R.id.tv_assetname);
            tv_assetIsByod = itemView.findViewById(R.id.tv_assetIsByod);
            tv_assetType = itemView.findViewById(R.id.tv_assetType);
            lable_assetType = itemView.findViewById(R.id.lable_assetType);
            lable_assetCode = itemView.findViewById(R.id.lable_assetCode);
            tv_assetbrand = itemView.findViewById(R.id.tv_assetbrand);
            tv_assetmodel = itemView.findViewById(R.id.tv_assetmodel);
            tv_serialno = itemView.findViewById(R.id.tv_serialno);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_dept = itemView.findViewById(R.id.tv_dept);
            tv_section = itemView.findViewById(R.id.tv_section);
            ll_main = itemView.findViewById(R.id.ll_main);
            ll_deptInfo = itemView.findViewById(R.id.ll_deptInfo);
            ll_custodian = itemView.findViewById(R.id.ll_custodian);
            ll_software = itemView.findViewById(R.id.ll_software);
            ll_hardware = itemView.findViewById(R.id.ll_hardware);
            cv_ticket_raise = itemView.findViewById(R.id.cv_ticket_raise);
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
    public TicketAssetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ticketassetlist, parent, false);

        return new TicketAssetListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TicketAssetListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final AssetListPojo assetListPojo =contactListFiltered.get(position);
        holder.tv_assetCode.setText(assetListPojo.getAsset_code());
        holder.tv_assetname.setText(assetListPojo.getAc_name());
        holder.tv_assetbrand.setText(assetListPojo.getBrand_name());
        holder.tv_assetmodel.setText(assetListPojo.getModel_name());
        holder.tv_assetType.setText(assetListPojo.getAsset_type());
        holder.lable_assetType.setText("Asset Type");
        holder.lable_assetCode.setText("Asset Code");
        holder.tv_serialno.setText(assetListPojo.getAsset_serial_no());
        holder.tv_location.setText(assetListPojo.getLocation_name());
        holder.tv_dept.setText(assetListPojo.getDepartment_name());
        holder.tv_section.setText(assetListPojo.getDs_name());
        holder.ll_hardware.setVisibility(View.VISIBLE);
        RoleResponse roleResponse = (RoleResponse) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_ROLES_OBJ, RoleResponse.class);
        /*if (roleResponse.getData().isEmpty() || (roleResponse.getData().contains("CUSTODIAN") && roleResponse.getData().size()==1)){
            holder.ll_deptInfo.setVisibility(View.GONE);
            holder.ll_custodian.setVisibility(View.GONE);
        }else {
            holder.ll_deptInfo.setVisibility(View.VISIBLE);
            holder.ll_custodian.setVisibility(View.VISIBLE);
        }*/
        if (assetListPojo.getAssetIsByod().equalsIgnoreCase("1")){
            holder.tv_assetIsByod.setVisibility(View.VISIBLE);
            holder.tv_assetIsByod.setText("(BYOD)");
        }
        if (assetListPojo.getSrno() % 2 == 0){
            //holder.ll_main.setBackgroundColor(Color.parseColor("#EAEAD3"));
            holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
            holder.cv_ticket_raise.setCardBackgroundColor(Color.parseColor("#DDDB91"));
        }else {
            holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
            holder.cv_ticket_raise.setCardBackgroundColor(Color.parseColor("#CFCC57"));
        }

        if (assetListPojo.getAc_name().equalsIgnoreCase("Desktop")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Access Point")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.access_point);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Amplifier")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.amplifier);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Barcode Scanner")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.barcode_scanner);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Biometric Attendance")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.biometric_attendance);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Camera")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.camera);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Desk Phone")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desk_phone);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Digital Video Recorder")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.digital_video_recorder);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("EPABX")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.epabx);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Firewall")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.firewall);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Hard Disk Drive")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Laptop")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.laptop);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Microphone")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.microphone);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Mobile")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.mobile);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Modem")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.modem);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Monitor")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Network Video Recorder")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.network_video_recorder);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Printer")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.printer);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Rack Enclosure")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.rack_enclosure);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Router")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.router);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Scanner")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.scanner);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Server")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.server);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("SFP Transceiver")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.sfp_transreceiver);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Speaker")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.speaker);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("SSD Drive")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Storage Device")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.storage_device);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Switch")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.resource_switch);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Tablet")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.tablet);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Television")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.television);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Thin Client")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.thin_client);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("UPS")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.ups);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Wireless Controller")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.wireless_controller);
        }else if (assetListPojo.getAc_name().equalsIgnoreCase("Wireless RF Device")){
            holder.iv_assetImage.setBackgroundResource(R.drawable.wireless_rf_device);
        }else {
            holder.iv_assetImage.setBackgroundResource(R.drawable.desktop);
        }


        /*Glide.with(context)
                .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/asset_category/"+assetListPojo.getAc_icon()))
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(holder.iv_assetImage);*/

        holder.cv_ticket_raise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                ((TicketAssetListActivity) context).raiseTicketHW(contactListFiltered.get(index));
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
                    ArrayList<AssetListPojo> filteredList = new ArrayList<>();
                    for (AssetListPojo row : contactList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getAc_name().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<AssetListPojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface TicketAssetListAdapterListener {
        void onContactSelected(AssetListPojo userAsset);
    }
}
