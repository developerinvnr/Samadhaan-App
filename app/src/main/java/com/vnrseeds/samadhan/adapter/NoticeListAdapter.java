package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.noticeparser.Datum;
import com.vnrseeds.samadhan.pojo.NoticeListPojo;

import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.MyViewHolder>{
    private Context context;
    private final List<NoticeListPojo> notificationList;
    private List<NoticeListPojo> notificationListFiltered;
    private final NoticeListAdapter.NoticeAdapterListener listener;

    public NoticeListAdapter(Context context, List<NoticeListPojo> notificationList, NoticeListAdapter.NoticeAdapterListener listener) {
        this.context = context;
        this.notificationList = notificationList;
        this.notificationListFiltered = notificationList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_description,tv_subject,affected_fr,affected_to;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_subject = itemView.findViewById(R.id.tv_subject);
            affected_fr = itemView.findViewById(R.id.affected_fr);
            affected_to = itemView.findViewById(R.id.affected_to);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //send selected contact in callback
                    listener.onContactSelected(notificationListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public NoticeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_noticelist_home, parent, false);
        return new NoticeListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoticeListAdapter.MyViewHolder holder, int position) {
        final NoticeListPojo noticeData=notificationListFiltered.get(position);
        holder.tv_subject.setText(noticeData.getNotificationSubject());
        holder.tv_description.setText(noticeData.getNotificationMessage());
        holder.affected_fr.setText("Affected from "+noticeData.getNotificationAffectedFrom());
        holder.affected_to.setText("Affected to "+noticeData.getNotificationAffectedTo());
    }

    @Override
    public int getItemCount() {
        return notificationListFiltered.size();
    }

    public interface NoticeAdapterListener {
        void onContactSelected(NoticeListPojo datum);
    }

}
