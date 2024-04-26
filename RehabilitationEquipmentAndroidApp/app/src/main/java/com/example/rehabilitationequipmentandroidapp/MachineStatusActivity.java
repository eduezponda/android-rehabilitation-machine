package com.example.rehabilitationequipmentandroidapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rehabilitationequipmentandroidapp.Models.MachineStatus;

public class MachineStatusActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private MyApp App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_machine_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        App = (MyApp) getApplication();

        ImageView historyButton, editButton;

        historyButton = findViewById(R.id.imgHistory);
        editButton = findViewById(R.id.imgEdit);
        progressBar = findViewById(R.id.progressBar);

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistoryClicked();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUIWithData();
    }

    @SuppressLint("SetTextI18n")
    private void updateUIWithData() {
        MachineStatus machineStatus = App.getMachineStatus();

        ((TextView) findViewById(R.id.tvIdValue)).setText(String.valueOf(machineStatus.getId()));

        progressBar.setProgress(machineStatus.getBatteryStatus());

        ((TextView) findViewById(R.id.tvTypeValue)).setText(machineStatus.getType());

        ((TextView) findViewById(R.id.tvStatusValue)).setText(machineStatus.getStatus());
        ((TextView) findViewById(R.id.tvPowerConsumptionValue)).setText(machineStatus.getPowerConsumption() + "W");
        ((TextView) findViewById(R.id.tvOperatingTemperatureValue)).setText(machineStatus.getOperatingTemperature() + "º");
        ((TextView) findViewById(R.id.tvRuntimeHoursValue)).setText(machineStatus.getRuntimeHours() + "h");

        ((TextView) findViewById(R.id.tvHeartRateValue)).setText(machineStatus.getHeartRate() + " bpm");
        ((TextView) findViewById(R.id.tvBloodPressureValue)).setText(machineStatus.getBloodPressure());
        ((TextView) findViewById(R.id.tvOxygenSaturationValue)).setText(machineStatus.getOxygenSaturation() + "%");
    }

    private void onEditClicked() {
        finish();
    }

    private void onHistoryClicked() {
        Intent intent = new Intent(MachineStatusActivity.this, HistoryActivity.class);

        startActivity(intent);
    }
}