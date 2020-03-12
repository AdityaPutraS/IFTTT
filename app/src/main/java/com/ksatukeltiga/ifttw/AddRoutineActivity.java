package com.ksatukeltiga.ifttw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                            FragmentManager fragMan = getFragmentManager();
                            FragmentTransaction fragTransaction = fragMan.beginTransaction();
                            Fragment timerFragment = new TimerFragment();
                            fragTransaction.add(R.id.conditionContainer, timerFragment , "timerFragment");
                            fragTransaction.commit();
                            Toast.makeText(parent.getContext(), "Timer Condition", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(parent.getContext(), "Sensor Condition", Toast.LENGTH_SHORT).show();
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

                switch (position) {
                    case 0:
                        Toast.makeText(parent.getContext(), "Spinner item 1!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(parent.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(parent.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                        break;
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
                saveRoutine();
                finish();
            }
        });
    }

    private void saveRoutine() {
        RoutineRepository routineRepository = new RoutineRepository(getApplicationContext());
        Spinner conditionSpinner = findViewById(R.id.conditionSpinner);
        Spinner actionSpinner = findViewById(R.id.actionSpinner);
        String kondisiString = conditionSpinner.getSelectedItem().toString();
        String aksiString = actionSpinner.getSelectedItem().toString();
        ConditionModule kondisi = new TimerModule(new Date(), false, getApplicationContext());
        ActionModule aksi = new NotifyModule(kondisiString, aksiString);
        routineRepository.insertRoutine(kondisi, aksi);

    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String item = parent.getItemAtPosition(position).toString();
//
//        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
