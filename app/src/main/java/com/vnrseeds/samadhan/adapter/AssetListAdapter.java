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
import com.vnrseeds.samadhan.parser.assetlistparser.AssetListPojo;

import java.util.ArrayList;

public class AssetListAdapter extends RecyclerView.Adapter<AssetListAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private ArrayList<AssetListPojo> contactList;
    private ArrayList<AssetListPojo> contactListFiltered;
    private AssetListAdapter.AssetListAdapterListener listener;
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

    public AssetListAdapter(Context context, ArrayList<AssetListPojo> contactList, AssetListAdapter.AssetListAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AssetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_assetlist, parent, false);
        return new AssetListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final AssetListPojo assetListData =contactListFiltered.get(position);
        holder.tv_assetCode.setText(assetListData.getAssetCode());
        holder.tv_assetname.setText(assetListData.getAcName());
        holder.tv_assetType.setText(assetListData.getAssetType());
        /*holder.tv_location.setText(assetListData.getLocationName());
        holder.tv_dept.setText(assetListData.getDepartmentName());
        holder.tv_section.setText(assetListData.getDsName());
        holder.tv_custodian.setText(assetListData.getCustodianName());*/

        if (assetListData.getSrno() % 2 == 0){
            //holder.ll_main.setBackgroundColor(Color.parseColor("#EAEAD3"));
            holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
            //holder.cv_ticket_raise.setCardBackgroundColor(Color.parseColor(""));
        }else {
            holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
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
                    contactListFiltered = contactList;
                } else {
                    ArrayList<AssetListPojo> filteredList = new ArrayList<>();
                    for (AssetListPojo row : contactList) {
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
                contactListFiltered = (ArrayList<AssetListPojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface AssetListAdapterListener {
        void onContactSelected(AssetListPojo assetListPojo);
    }
}
