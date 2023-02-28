package com.vnrseeds.samadhan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.ticketviewparser.AssignLog;

import java.util.List;

public class AssignListAdapter extends RecyclerView.Adapter<AssignListAdapter.MyViewHolder>{
    private Context context;
    private List<AssignLog> contactList;

    public AssignListAdapter(Context context, List<AssignLog> contactList) {
        this.context = context;
        this.contactList = contactList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout ll_main;
        private final TextView tv_ticketno,tv_assignDate,tv_assignBy,tv_assignTo,tv_assignDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            tv_ticketno = itemView.findViewById(R.id.tv_ticketno);
            tv_assignDate = itemView.findViewById(R.id.tv_assignDate);
            tv_assignBy = itemView.findViewById(R.id.tv_assignBy);
            tv_assignTo = itemView.findViewById(R.id.tv_assignTo);
            tv_assignDesc = itemView.findViewById(R.id.tv_assignDesc);
        }
    }

    @NonNull
    @Override
    public AssignListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_assign_logs, parent, false);
        return new AssignListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignListAdapter.MyViewHolder holder, int position) {
        AssignLog assignLog = contactList.get(position);
        holder.tv_assignDate.setText(assignLog.getAssignDate());
        holder.tv_assignBy.setText(assignLog.getAssignCreatedBy());
        holder.tv_assignTo.setText(assignLog.getAssignTo());
        holder.tv_assignDesc.setText(assignLog.getAssignDescription());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
