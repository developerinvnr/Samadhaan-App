package com.vnrseeds.samadhan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.parser.notificationlistparser.Datum;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private final List<Datum> notificationList;
    private List<Datum> notificationListFiltered;
    private final NotificationListAdapter.NotificationAdapterListener listener;

    public NotificationListAdapter(Context context, List<Datum> notificationList, NotificationListAdapter.NotificationAdapterListener listener) {
        this.context = context;
        this.notificationList = notificationList;
        this.notificationListFiltered = notificationList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout ll_main;
        private final TextView tv_dateTime,tv_desc,tv_notification_title;
        private final ImageView imageView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            tv_dateTime = itemView.findViewById(R.id.tv_dateTime);
            tv_notification_title = itemView.findViewById(R.id.tv_notification_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            imageView2 = itemView.findViewById(R.id.imageView2);

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
    public NotificationListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification_list, parent, false);
        return new NotificationListAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotificationListAdapter.MyViewHolder holder, int position) {
        final Datum notificationData=notificationListFiltered.get(position);
        holder.tv_dateTime.setText(notificationData.getNotificationCreatedAt());
        holder.tv_notification_title.setText(notificationData.getNotificationSubject());
        if (notificationData.getNotificationMessage().length()>20) {
            holder.tv_desc.setText(notificationData.getNotificationMessage().substring(0, 20) + "...");
        }else {
            holder.tv_desc.setText(notificationData.getNotificationMessage());
        }

            if (notificationData.getNotificationIsViewed()!=null && notificationData.getNotificationIsViewed().equalsIgnoreCase("1")) {
                holder.imageView2.setBackgroundResource(R.drawable.drafts_icon);
                holder.ll_main.setBackgroundResource(R.drawable.card_background_lighttoolbar);
            } else {
                holder.imageView2.setBackgroundResource(R.drawable.mail_icon);
                holder.ll_main.setBackgroundResource(R.drawable.ticket_list_background);
            }

    }

    @Override
    public int getItemCount() {
        return notificationListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    notificationListFiltered = notificationList;
                } else {
                    ArrayList<Datum> filteredList = new ArrayList<>();
                    for (Datum row : notificationList) {
                        if (row.getNotificationMessage().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    notificationListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = notificationListFiltered;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notificationListFiltered = (ArrayList<Datum>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface NotificationAdapterListener {
        void onContactSelected(Datum notificationData);
    }
}
