package com.ksatukeltiga.ifttw.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import 	androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ksatukeltiga.ifttw.conditionmodule.ConditionModule;
import com.ksatukeltiga.ifttw.fragment.EmailFragment;
import com.ksatukeltiga.ifttw.fragment.GyroscopeFragment;
import com.ksatukeltiga.ifttw.conditionmodule.GyroscopeModule;
import com.ksatukeltiga.ifttw.fragment.NotifyFragment;
import com.ksatukeltiga.ifttw.R;
import com.ksatukeltiga.ifttw.room.RoutineRepository;
import com.ksatukeltiga.ifttw.fragment.TimerFragment;
import com.ksatukeltiga.ifttw.conditionmodule.TimerModule;
import com.ksatukeltiga.ifttw.actionmodule.ActionModule;
import com.ksatukeltiga.ifttw.actionmodule.EmailModule;
import com.ksatukeltiga.ifttw.actionmodule.NotifyModule;
import com.ksatukeltiga.ifttw.actionmodule.WifiModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        Spinner conditionSpinner = findViewById(R.id.conditionSpinner);
        Spinner actionSpinner = findViewById(R.id.actionSpinner);

        conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout conditionContainer = parent.getRootView().findViewById(R.id.conditionContainer);
                if(conditionContainer != null)
                {
                    conditionContainer.removeAllViews();
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            FragmentManager fragMan = getSupportFragmentManager();
                            FragmentTransaction fragTransaction = fragMan.beginTransaction();
                            Fragment timerFragment = new TimerFragment();
                            fragTransaction.add(R.id.conditionContainer, timerFragment , "timerFragment");
                            fragTransaction.commit();
                            Toast.makeText(parent.getContext(), "Timer Condition", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            Fragment gyroscopeFragment = new GyroscopeFragment();
                            fragmentTransaction.add(R.id.conditionContainer, gyroscopeFragment, "gyroscopeFragment");
                            fragmentTransaction.commit();
                            Toast.makeText(parent.getContext(), "Gyroscope Sensor Condition", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        actionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout actionContainer = parent.getRootView().findViewById(R.id.actionContainer);
                if(actionContainer != null) {
                    actionContainer.removeAllViews();
                    FragmentManager fragMan = getSupportFragmentManager();
                    FragmentTransaction fragTransaction = fragMan.beginTransaction();
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            Fragment notifyFragment = new NotifyFragment();
                            fragTransaction.add(R.id.actionContainer, notifyFragment , "notifyFragment");
                            Toast.makeText(parent.getContext(), "Notify Action", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(parent.getContext(), "Turn On WiFi Action!", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(parent.getContext(), "Turn Off WiFi Action!", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Fragment emailFragment = new EmailFragment();
                            fragTransaction.add(R.id.actionContainer, emailFragment , "emailFragment");
                            Toast.makeText(parent.getContext(), "Email Action", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    fragTransaction.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        ArrayAdapter<CharSequence> conditionAdapter = ArrayAdapter.createFromResource(this,
                R.array.condition_module, R.layout.support_simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> actionAdapter = ArrayAdapter.createFromResource(this,
                R.array.action_module, R.layout.support_simple_spinner_dropdown_item);

        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        conditionSpinner.setAdapter(conditionAdapter);
        actionSpinner.setAdapter(actionAdapter);

        FloatingActionButton fab = findViewById(R.id.saveFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = false;
                try {
                    success = saveRoutine();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(success)
                {
                    finish();
                }else{
                    Toast.makeText(view.getContext(), "Failed save routine", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean saveRoutine() throws ParseException {
        RoutineRepository routineRepository = new RoutineRepository(getApplicationContext());
        Spinner conditionSpinner = findViewById(R.id.conditionSpinner);
        Spinner actionSpinner = findViewById(R.id.actionSpinner);
        String kondisiString = conditionSpinner.getSelectedItem().toString();
        String aksiString = actionSpinner.getSelectedItem().toString();
        ConditionModule kondisi = null;
        ActionModule aksi= null;

        // Create condition module
        if(kondisiString.equalsIgnoreCase("Timer")) {
            int[] dayIdArr = {R.id.sunday, R.id.monday, R.id.tuesday,
                    R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday};
            boolean[] repeated = new boolean[7];
            for (int i = 0; i < 7; i++) {
                ToggleButton temp = findViewById(dayIdArr[i]);
                repeated[i] = temp.isChecked();
            }

            TextView dateText = findViewById(R.id.dateText);
            TextView timeText = findViewById(R.id.timeText);
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("EEE, MMM d yyyy KK:mm aa");
            //Parsing the given String to Date object
            String dateTimeString = dateText.getText().toString() + " " + timeText.getText().toString();
            Date dateTime = dateTimeFormat.parse(dateTimeString);

            kondisi = new TimerModule(dateTime, repeated, getApplicationContext());
        } else if (kondisiString.equalsIgnoreCase("Gyroscope Sensor")) {
            EditText thresholdText = findViewById(R.id.thresholdEditText);
            double threshold = Double.parseDouble(thresholdText.getText().toString());
            Log.i("threshold", ""+threshold);
            kondisi = new GyroscopeModule(threshold,true, getApplicationContext());
        }

        // Create action module
        if(aksiString.equalsIgnoreCase("Notify Me")) {
            EditText judulEditText = findViewById(R.id.fromEditText);
            EditText bodyEditText = findViewById(R.id.toEditText);
            aksi = new NotifyModule(judulEditText.getText().toString(), bodyEditText.getText().toString());
        }else if(aksiString.equalsIgnoreCase("Send Email")) {
            String from = ((EditText) findViewById(R.id.fromEditText)).getText().toString();
            String to = ((EditText) findViewById(R.id.toEditText)).getText().toString();
            String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
            String body = ((EditText) findViewById(R.id.bodyEditText)).getText().toString();
            aksi = new EmailModule(title, body, from , to);
        } else if (aksiString.equalsIgnoreCase("Turn On WiFi")) {
            aksi = new WifiModule("on");
        } else if (aksiString.equalsIgnoreCase("Turn Off WiFi")) {
            aksi = new WifiModule("off");
        }

        // Insert routine to database
        if(kondisi != null && aksi != null) {
            if (kondisi instanceof GyroscopeModule) {
                ((GyroscopeModule) kondisi).setActionClass(aksi.getClass().getName());
                ((GyroscopeModule) kondisi).setBundle(aksi.getBundle());
            }
            routineRepository.insertRoutine(kondisi, aksi, getApplicationContext());
            return true;
        }

        // Default return gagal
        return false;
    }
}
