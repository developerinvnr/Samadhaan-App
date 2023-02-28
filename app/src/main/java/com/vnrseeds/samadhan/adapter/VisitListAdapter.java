package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.sitevisitparser.SiteVisit;
import com.vnrseeds.samadhan.parser.ticketslistparser.TicketDetailsPojo;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;
import com.vnrseeds.samadhan.ticketsystem.VisitListActivity;

import java.util.List;

public class VisitListAdapter extends RecyclerView.Adapter<VisitListAdapter.MyViewHolder> {

    private final Context context;
    private final List<SiteVisit> visitListData;
    private final VisitListAdapter.VisitListAdapterListener listener;
    private int index;

    public VisitListAdapter(Context context, List<SiteVisit> visitListData, VisitListAdapter.VisitListAdapterListener listener) {
        this.context = context;
        this.visitListData = visitListData;
        this.listener = (VisitListAdapterListener) listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout ll_main,ll_cancel;
        private final TextView tv_ticketno,tv_visitDate,tv_visitDiscription,tv_visitBy,tv_edit_visit,tv_cancel_visit,tv_buttonTitle,tv_visitStatus;
        private final CardView cv_received;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            ll_cancel = itemView.findViewById(R.id.ll_cancel);
            tv_ticketno = itemView.findViewById(R.id.tv_ticketno);
            tv_visitDate = itemView.findViewById(R.id.tv_visitDate);
            tv_visitDiscription = itemView.findViewById(R.id.tv_visitDiscription);
            tv_visitBy = itemView.findViewById(R.id.tv_visitBy);
            tv_edit_visit = itemView.findViewById(R.id.tv_edit_visit);
            tv_cancel_visit = itemView.findViewById(R.id.tv_cancel_visit);
            tv_visitStatus = itemView.findViewById(R.id.tv_visitStatus);
            tv_buttonTitle = itemView.findViewById(R.id.tv_buttonTitle);
            cv_received = itemView.findViewById(R.id.cv_received);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //send selected contact in callback
                    listener.onContactSelected(visitListData.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public VisitListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_visit_list, parent, false);
        return new VisitListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final SiteVisit siteVisit =visitListData.get(position);
        TicketDetailsPojo ticketDetailsPojo = (TicketDetailsPojo) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_TICKET_OBJ, TicketDetailsPojo.class);
        holder.tv_ticketno.setText(ticketDetailsPojo.getTicketNo());
        holder.tv_visitDate.setText(siteVisit.getTsvVisitedAt());
        holder.tv_visitDiscription.setText(siteVisit.getTsvDescription());
        holder.tv_visitStatus.setText(siteVisit.getTsvStatus());
        holder.tv_visitBy.setText(siteVisit.getTsvVisitedByName());

        if (siteVisit.getTsvStatus().equalsIgnoreCase("Scheduled") || siteVisit.getTsvStatus().equalsIgnoreCase("Rescheduled")){
            holder.ll_cancel.setVisibility(View.VISIBLE);
            holder.cv_received.setVisibility(View.VISIBLE);
            holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
        }else {
            holder.ll_cancel.setVisibility(View.GONE);
            holder.cv_received.setVisibility(View.GONE);
            holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
        }

        holder.cv_received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                ((VisitListActivity) context).setConfirmed(visitListData.get(index));
            }
        });

        holder.tv_edit_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                ((VisitListActivity) context).editVisit(visitListData.get(index));
            }
        });

        holder.tv_cancel_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                ((VisitListActivity) context).cancelVisit(visitListData.get(index));
            }
        });
    }

    @Override
    public int getItemCount() {
        return visitListData.size();
    }

    public interface VisitListAdapterListener {
        void onContactSelected(SiteVisit siteVisit);
    }

}
