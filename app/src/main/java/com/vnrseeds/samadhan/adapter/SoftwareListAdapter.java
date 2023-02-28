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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.ticketassetparser.UserApplication;
import com.vnrseeds.samadhan.ticketsystem.TicketAssetListActivity;
import com.vnrseeds.samadhan.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class SoftwareListAdapter extends RecyclerView.Adapter<SoftwareListAdapter.MyViewHolder> implements Filterable {
    private final Context context;
    private final List<UserApplication> contactList;
    private List<UserApplication> contactListFiltered;
    private final SoftwareListAdapter.TicketSoftwareListAdapterListener listener;
    private int index;

    public SoftwareListAdapter(Context context, List<UserApplication> contactList, TicketAssetListActivity listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_assetname,lable_assetType,lable_assetCode,tv_assetType,tv_assetCode;
        private final LinearLayout ll_main,ll_brand,ll_deptInfo,ll_custodian,ll_software,ll_hardware;
        private final CardView cv_ticket_raise;
        private final ImageView iv_assetImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_assetname = itemView.findViewById(R.id.tv_assetname);
            tv_assetType = itemView.findViewById(R.id.tv_assetType);
            tv_assetCode = itemView.findViewById(R.id.tv_assetCode);
            lable_assetType = itemView.findViewById(R.id.lable_assetType);
            lable_assetCode = itemView.findViewById(R.id.lable_assetCode);
            ll_main = itemView.findViewById(R.id.ll_main);
            ll_brand = itemView.findViewById(R.id.ll_brand);
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
    public SoftwareListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ticketassetlist, parent, false);

        return new SoftwareListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SoftwareListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final UserApplication userApplication =contactListFiltered.get(position);
        holder.ll_brand.setVisibility(View.GONE);
        holder.ll_deptInfo.setVisibility(View.GONE);
        holder.ll_custodian.setVisibility(View.GONE);
        holder.ll_software.setVisibility(View.VISIBLE);
        holder.tv_assetname.setText(userApplication.getApplicationName());
        holder.lable_assetType.setText("Version");
        holder.lable_assetCode.setText("Application");
        holder.tv_assetType.setText(userApplication.getApplicationVersion());
        holder.tv_assetCode.setText(userApplication.getApplicationName());
        holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);

        Glide.with(context)
                .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/application_softwares/"+userApplication.getApplicationIcon()))
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(holder.iv_assetImage);

        holder.cv_ticket_raise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                ((TicketAssetListActivity) context).raiseTicketSW(contactListFiltered.get(index));
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
                    ArrayList<UserApplication> filteredList = new ArrayList<>();
                    for (UserApplication row : contactList) {
                        if (row.getApplicationName().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (List<UserApplication>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface TicketSoftwareListAdapterListener {
        void onContactSelected(UserApplication userApplication);
    }

}
