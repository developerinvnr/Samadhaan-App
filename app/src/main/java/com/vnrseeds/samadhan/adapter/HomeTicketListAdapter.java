package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketsListPojo;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class HomeTicketListAdapter extends RecyclerView.Adapter<HomeTicketListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<TicketsListPojo> contactList;
    private ArrayList<TicketsListPojo> contactListFiltered;
    private HomeTicketListAdapter.TicketsListAdapterListener listener;
    private int index;

    public HomeTicketListAdapter(Context context, ArrayList<TicketsListPojo> contactList, HomeTicketListAdapter.TicketsListAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_issuetitle,ticket_no,ticket_date;
        private final StepView step_view;
        private final RadioButton rb_raise,rb_assign,rb_diagnosis,rb_resolved,rb_close;
        private final View view_assign,view_diagnosis,view_resolved,view_close;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_issuetitle = itemView.findViewById(R.id.tv_issuetitle);
            ticket_no = itemView.findViewById(R.id.ticket_no);
            ticket_date = itemView.findViewById(R.id.ticket_date);
            step_view = itemView.findViewById(R.id.step_view);

            rb_raise = itemView.findViewById(R.id.rb_raise);
            rb_assign = itemView.findViewById(R.id.rb_assign);
            rb_diagnosis = itemView.findViewById(R.id.rb_diagnosis);
            rb_resolved = itemView.findViewById(R.id.rb_resolved);
            rb_close = itemView.findViewById(R.id.rb_close);

            view_assign = itemView.findViewById(R.id.view_assign);
            view_diagnosis = itemView.findViewById(R.id.view_diagnosis);
            view_resolved = itemView.findViewById(R.id.view_resolved);
            view_close = itemView.findViewById(R.id.view_close);

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
    public HomeTicketListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_home_ticketlist, parent, false);
        return new HomeTicketListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull HomeTicketListAdapter.MyViewHolder holder, int position) {
        final TicketsListPojo ticketsListPojo=contactListFiltered.get(position);
        holder.ticket_no.setText("Ticket "+ticketsListPojo.getTicketNo());
        /*if (ticketsListPojo.getTicketIssueOther()!=null){
            holder.tv_issuetitle.setText(ticketsListPojo.getTicketIssueOther());
        }else{
            holder.tv_issuetitle.setText(ticketsListPojo.getIssueTitle());
        }*/
        if (ticketsListPojo.getTicketAssetAcName().equalsIgnoreCase("") || ticketsListPojo.getTicketAssetAcName()==null) {
            holder.tv_issuetitle.setText(ticketsListPojo.getAssetCategory());
        }else {
            holder.tv_issuetitle.setText(ticketsListPojo.getTicketAssetAcName());
        }

        holder.ticket_date.setText("Date "+ticketsListPojo.getTicketDate());

        if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Assigned")){
            holder.view_assign.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_assign.setChecked(true);
        }

        if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Diagnosis")){
            holder.view_assign.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_assign.setChecked(true);
            holder.view_diagnosis.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_diagnosis.setChecked(true);
        }

        if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Resolved")){
            holder.view_assign.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_assign.setChecked(true);
            holder.view_diagnosis.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_diagnosis.setChecked(true);
            holder.view_resolved.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_resolved.setChecked(true);
        }

        if (ticketsListPojo.getTicketStatus().equalsIgnoreCase("Closed")){
            holder.view_assign.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_assign.setChecked(true);
            holder.view_diagnosis.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_diagnosis.setChecked(true);
            holder.view_resolved.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_resolved.setChecked(true);
            holder.view_close.setBackgroundColor(Color.parseColor("#c3c148"));
            holder.rb_close.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    public interface TicketsListAdapterListener {
        void onContactSelected(TicketsListPojo ticketsListPojo);
    }
}
