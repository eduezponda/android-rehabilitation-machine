package com.example.rehabilitationequipmentuserapp;

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
    String selectedStatus = "idle";
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

        //App.savePhoto(BitmapFactory.decodeResource(getResources(), R.drawable.mistery_man));
        //App.getMachineStatus().setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.mistery_man));

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

        ImageView historyButton, statusButton;

        historyButton = findViewById(R.id.imgHistory);
        statusButton = findViewById(R.id.imgStatus);

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistoryClicked();
            }
        });

        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatus();
            }
        });
    }

    private void saveData() {
        String id = ((EditText) findViewById(R.id.editTextId)).getText().toString();
        String bloodPressure = ((EditText) findViewById(R.id.editTextBloodPressure)).getText().toString();


        //((MyApp) getApplication()).saveUserStatus(id, heartRate, bloodPressure,oxygenSaturation);

        openStatus();
    }

    private void openStatus() {
        /*Intent intent = new Intent(SimulationActivity.this, MachineStatusActivity.class);

        startActivity(intent);*/
    }

    private int parseIntOrDefault(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void onHistoryClicked() {
        /*Intent intent = new Intent(SimulationActivity.this, HistoryActivity.class);

        startActivity(intent);*/
    }
}