package com.ksatukeltiga.ifttw.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ksatukeltiga.ifttw.R;

public class NotifyFragment extends Fragment {
    TextView titleText, notificationText;
    String selectedTitle, selectedNotification;

    public NotifyFragment() {}

    public static NotifyFragment newInstance() {
        NotifyFragment notifyFragment = new NotifyFragment();
        return notifyFragment;
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
        View view = inflater.inflate(R.layout.fragment_notify, container, false);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
