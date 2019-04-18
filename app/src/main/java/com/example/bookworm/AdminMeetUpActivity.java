package com.example.bookworm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AdminMeetUpActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_meet_up);
        mDisplayDate = (TextView) findViewById(R.id.txt_Date);

        Button button = (Button) findViewById(R.id.btn_Time);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal  = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AdminMeetUpActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

//https://www.youtube.com/watch?v=hwe1abDO2Ag Accessed on: 18th April 2019


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);

                System.out.println("onDate: date:" + year +"/" + month + "/" + dayOfMonth);
            }
        };

    }

//https://www.youtube.com/watch?v=QMwaNN_aM3U Accessed on 18th April 2019

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.txt_Time);
        textView.setText( hourOfDay + ":" + minute );
    }
}
