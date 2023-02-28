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
import com.vnrseeds.samadhan.parser.loginparser.User;
import com.vnrseeds.samadhan.parser.ticketviewparser.InternalNoteLog;
import com.vnrseeds.samadhan.sessionmanager.SharedPreferences;

import java.util.List;

public class TicketViewInternalNotesAdapter extends RecyclerView.Adapter<TicketViewInternalNotesAdapter.MyViewHolder> {
    private Context context;
    private List<InternalNoteLog> contactList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_name,tv_date,tv_message;
        private final LinearLayout ll_main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_main = itemView.findViewById(R.id.ll_main);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_message = itemView.findViewById(R.id.tv_message);
        }
    }

    public TicketViewInternalNotesAdapter(Context context, List<InternalNoteLog> ticketViewArray) {
        this.context = context;
        this.contactList = ticketViewArray;
    }

    @NonNull
    @Override
    public TicketViewInternalNotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ticket_view, parent, false);
        return new TicketViewInternalNotesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewInternalNotesAdapter.MyViewHolder holder, int position) {
        final InternalNoteLog internalNoteLog =contactList.get(position);
        holder.tv_name.setText(internalNoteLog.getTlCreatedBy());
        holder.tv_date.setText(internalNoteLog.getTlCreatedDate());
        holder.tv_message.setText(internalNoteLog.getTlMessage());

        User userData = (User) SharedPreferences.getInstance().getObject(SharedPreferences.KEY_LOGIN_OBJ, User.class);
        if (userData.getUser_id().equalsIgnoreCase(internalNoteLog.getTlCreatedById())){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(100, 30, 0, 0);

            holder.ll_main.setLayoutParams(layoutParams);
        }else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 30, 100, 0);
            holder.ll_main.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public interface TicketViewAdapterListener {
    }
}
