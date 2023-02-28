package com.vnrseeds.samadhan.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.pojo.SoftwareListPojo;
import com.vnrseeds.samadhan.utils.AppConfig;

import java.util.List;

public class HomeSoftwareListAdapter extends RecyclerView.Adapter<HomeSoftwareListAdapter.MyViewHolder>{
    private final Context context;
    private final List<SoftwareListPojo> softwaredata;
    private final HomeSoftwareListAdapter.HomeSoftwareListAdapterListener listener;
    private int index;

    public HomeSoftwareListAdapter(Context context, List<SoftwareListPojo> softwaredata, HomeSoftwareListAdapter.HomeSoftwareListAdapterListener listener) {
        this.context = context;
        this.softwaredata = softwaredata;
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
                    listener.onContactSelected(softwaredata.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public HomeSoftwareListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_home_assetlist, parent, false);
        return new HomeSoftwareListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSoftwareListAdapter.MyViewHolder holder, int position) {
        final SoftwareListPojo softwareListPojo=softwaredata.get(position);
        //holder.ticket_no.setText("Ticket "+userAsset.getTicketNo());
        holder.tv_assetcategory.setText(softwareListPojo.getApplication_name());
        holder.tv_assetcategory.setSelected(true);
        holder.tv_assetID.setText(softwareListPojo.getApplication_id());
        holder.tv_assetID.setVisibility(View.GONE);

        Glide.with(context)
                .load(Uri.parse(AppConfig.BASE_IMAGE_URL + "uploads/application_softwares/"+softwareListPojo.getApplication_icon()))
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(holder.iv_asseticon);
    }

    @Override
    public int getItemCount() {
        return softwaredata.size();
    }

    public interface HomeSoftwareListAdapterListener {
        void onContactSelected(SoftwareListPojo softwareListPojo);
    }
}
