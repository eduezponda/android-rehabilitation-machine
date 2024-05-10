package com.example.rehabilitationequipmentuserapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rehabilitationequipmentuserapp.Models.UserStatus;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity {
    MyApp App;
    private BarChart barChart;

    private TextView textViewNameData, textViewDurationData, textViewBodyPartFocusData,
                     textViewExerciseModeData, textViewIntensityData, textViewRehabilitationEquipmentIdData;
    private int index;
    private final int MAX = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        App = (MyApp) getApplication();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        index = bundle.getInt("position");

        App = (MyApp) getApplication();

        barChart = findViewById(R.id.barChart);

        setupBarChart(barChart);

        ImageView historyButton, editButton;

        historyButton = findViewById(R.id.imgHistory);
        editButton = findViewById(R.id.imgEdit);

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHistoryClicked();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEdit();
            }
        });

        Button buttonStatus = findViewById(R.id.buttonStatus);

        buttonStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRehabilitationEquipmentIdData = findViewById(R.id.textViewRehabilitationEquipmentIdData);
                textViewRehabilitationEquipmentIdData.setText("COGER DE LA API");
                //updateChart();
            }
        });

        updateData();
    }

    public void updateData() {
        App.getFewLatestUserStatus(MAX, new MyApp.StatusCallback() {
            @Override
            public void onCallback(ArrayList<UserStatus> status) {
                String name = status.get(index).getName();
                int duration = status.get(index).getDuration();
                String bodyPartFocus = status.get(index).getBodyPartFocus();
                String exerciseModeData = status.get(index).getExerciseMode();
                int intensity = status.get(index).getIntensity();

                textViewNameData = findViewById(R.id.textViewNameData);
                textViewDurationData = findViewById(R.id.textViewDurationData);
                textViewBodyPartFocusData = findViewById(R.id.textViewBodyPartFocusData);
                textViewExerciseModeData = findViewById(R.id.textViewExerciseModeData);
                textViewIntensityData = findViewById(R.id.textViewIntensityData);

                textViewNameData.setText(name);
                textViewDurationData.setText(String.valueOf(duration));
                textViewBodyPartFocusData.setText(bodyPartFocus);
                textViewExerciseModeData.setText(exerciseModeData);
                textViewIntensityData.setText(String.valueOf(intensity));

                updateChart(status);
            }
        });
    }

    public void updateChart(ArrayList<UserStatus> status) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(status.get(index).getDuration());
        list.add(status.get(index).getIntensity());

        List<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, list.get(0)));
        barEntries.add(new BarEntry(2, list.get(1)));

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate();
    }

    private void setupBarChart(BarChart bar) {
        bar.getDescription().setEnabled(false);
        bar.setDrawGridBackground(false);
    }

    private void openEdit() {
        Intent intent = new Intent(StatusActivity.this, SimulationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", index);

        startActivity(intent);
    }

    private void onHistoryClicked() {
        finish();
    }
}