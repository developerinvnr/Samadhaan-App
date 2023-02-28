package com.vnrseeds.samadhan.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.vnrseeds.samadhan.R;
import com.vnrseeds.samadhan.broadcast.CheckInternetConnectionBroadcast;
import com.vnrseeds.samadhan.communicator.DateCommunicator;
import com.vnrseeds.samadhan.communicator.alertCommunicator;

import java.util.Calendar;
import java.util.List;

public class Utils {
    private Context _context;
    private static Utils sSharedPrefs;

    public Utils(Context context) {
        this._context = context;
    }

    public static Utils getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new Utils(context);
        }
        return sSharedPrefs;
    }

    public static Utils getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        //Option 1:
        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
        //Option 2:
        // Alternatively, you can create a new instance here
        // with something like this:
        // getInstance(MyCustomApplication.getAppContext());
    }

    private IntentFilter intentFilter;
    private CheckInternetConnectionBroadcast receiver;
    private Dialog dialog;
    private final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public void initConnectionDetector() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new CheckInternetConnectionBroadcast();
    }

    public void showAlert(Context context, String message) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_okalert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView text = dialog.findViewById(R.id.tv_message);
        text.setText(message);
        Button okButton = dialog.findViewById(R.id.okbtn);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void showYesNOAlert(Context context, String message, final alertCommunicator communicator) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_yesno_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // set the custom dialog components - text, image and button
        /*WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);*/

        TextView text = dialog.findViewById(R.id.tv_message);
        text.setText(message);
        Button dialogButton = dialog.findViewById(R.id.okbtn);
        Button cancelbtn = dialog.findViewById(R.id.cancelbtn);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.onClickPositiveBtn();
                dialog.cancel();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.onClickNegativrBtn();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public ArrayAdapter<String> spinnerAdapter(final Context context, List<String> dataArray) {

        return new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, dataArray) {

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView text = view.findViewById(android.R.id.text1);
                //text.setTextSize(15);
                //text.setTypeface(text.getTypeface(), Typeface.BOLD);
                //text.setTextColor(context.getResources().getColor(R.color.primary_text));
                return view;

            }

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                TextView text = view.findViewById(android.R.id.text1);
                //text.setTextSize(15);
                //text.setTypeface(text.getTypeface(), Typeface.BOLD);
               //text.setTextColor(context.getResources().getColor(R.color.primary_text));

                return view;

            }
        };
    }

    public void showDatePicker(Context context, String minDate, String maxDate, final DateCommunicator communicator) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        Log.e("MaxDate", ""+maxDate);
        Log.e("MinDate", ""+minDate);
        Calendar today = Calendar.getInstance();
        Calendar pastDate = (Calendar) today.clone();
        if (minDate!=null) {
            pastDate.add(Calendar.DATE, Integer.parseInt(minDate));
        }
        Calendar futureDate = (Calendar) today.clone();
        if (maxDate!=null) {
            futureDate.add(Calendar.DATE, Integer.parseInt(maxDate));
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        communicator.getDate(date);

                    }
                }, mYear, mMonth, mDay);
        if (maxDate!=null) {
            //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.getDatePicker().setMaxDate(futureDate.getTimeInMillis());
        }
        if (minDate!=null){
            //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.getDatePicker().setMinDate(pastDate.getTimeInMillis());
        }
        datePickerDialog.show();
    }
}
