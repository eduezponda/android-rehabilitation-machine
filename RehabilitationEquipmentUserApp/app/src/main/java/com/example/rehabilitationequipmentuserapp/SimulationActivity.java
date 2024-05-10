package com.example.rehabilitationequipmentuserapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulationActivity extends AppCompatActivity {

    Spinner spinnerStatus;
    String selectedStatus = "working";
    MyApp App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simulation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<String> statusOptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.status_options)));
        statusOptions.add(0, getString(R.string.prompt_select_option));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        App = (MyApp) getApplication();

        spinnerStatus = findViewById(R.id.spinnerStatus);
        spinnerStatus.setAdapter(adapter);

        spinnerStatus.setSelection(0, false);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedStatus = parent.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se selecciona nada
            }
        });

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        ImageView returnButton;

        returnButton = findViewById(R.id.imgReturn);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    private void saveData() {
        String name =((EditText) findViewById(R.id.editTextId)).getText().toString();
        int duration = ((SeekBar) findViewById(R.id.seekBarBatteryStatus)).getProgress();
        String bodyPart =((EditText) findViewById(R.id.editTextType)).getText().toString();
        int intensity = ((SeekBar) findViewById(R.id.seekBarPowerConsumption)).getProgress();
        String exerciseMode = ((Spinner) findViewById(R.id.spinnerStatus)).getSelectedItem().toString();
        String idSupervisor =((EditText) findViewById(R.id.editTextRuntimeHours)).getText().toString();
        String comments =((EditText) findViewById(R.id.editTextBloodPressure)).getText().toString();

        ((MyApp) getApplication()).saveUserStatus(name, duration, bodyPart, exerciseMode, intensity, idSupervisor, comments);

        closeActivity();
    }

    private void closeActivity() {
        finish();
    }
}