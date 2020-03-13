package com.ksatukeltiga.ifttw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerFragment extends Fragment {
    public static final int REQUEST_CODE_DATE = 135170;
    public static final int REQUEST_CODE_TIME = 135171;
    TextView dateText, timeText;
    String selectedDate, selectedTime;

    public TimerFragment() {}

    public static TimerFragment newInstance() {
        TimerFragment timerFragment = new TimerFragment();
        return timerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        dateText = view.findViewById(R.id.dateText);
        timeText = view.findViewById(R.id.timeText);
        dateText.setText(new SimpleDateFormat("EEE, MMM d yyyy", Locale.ENGLISH).format(new Date()));
        timeText.setText(new SimpleDateFormat("KK:mm aa", Locale.ENGLISH).format(new Date()));
        final FragmentManager fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                // set the targetFragment to receive the results, specifying the request code
                newFragment.setTargetFragment(TimerFragment.this, REQUEST_CODE_DATE);
                // show the datePicker
                newFragment.show(fm, "datePicker");
            }
        });

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                // set the targetFragment to receive the results, specifying the request code
                newFragment.setTargetFragment(TimerFragment.this, REQUEST_CODE_TIME);
                // show the datePicker
                newFragment.show(fm, "timePicker");
            }
        });
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == REQUEST_CODE_DATE) {
                selectedDate = data.getStringExtra("selectedDate");
                dateText.setText(selectedDate);
            }
            else{
                selectedTime = data.getStringExtra("selectedTime");
                timeText.setText(selectedTime);
            }
        }
    }
}
