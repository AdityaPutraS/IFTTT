package com.ksatukeltiga.ifttw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class AddRoutineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);

        Spinner conditionSpinner = findViewById(R.id.conditionSpinner);
        Spinner actionSpinner = findViewById(R.id.actionSpinner);

        conditionSpinner.setOnItemSelectedListener(this);
        actionSpinner.setOnItemSelectedListener(this);

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
                startActivity(new Intent(AddRoutineActivity.this, MainActivity.class));
            }
        });
    }

    private void saveRoutine() {
        RoutineRepository routineRepository = new RoutineRepository(getApplicationContext());
        Spinner conditionSpinner = findViewById(R.id.conditionSpinner);
        Spinner actionSpinner = findViewById(R.id.actionSpinner);
        String kondisiString = conditionSpinner.getSelectedItem().toString();
        String aksiString = actionSpinner.getSelectedItem().toString();
        ConditionModule kondisi = new GyroscopeModule(20, true, getApplicationContext());
        ActionModule aksi = new WifiModule("off");
        routineRepository.insertRoutine(kondisi, aksi);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
