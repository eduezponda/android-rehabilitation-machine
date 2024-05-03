package com.example.rehabilitationequipmentandroidapp;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.Arrays;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rehabilitationequipmentandroidapp.Models.InterestPoint;
import com.example.rehabilitationequipmentandroidapp.Models.MachineStatus;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.parse.GetDataCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ImageView image;
    private MyApp App;
    private BarChart barChart1, barChart2, barChart3;
    private PieChart pieChart;
    private ListView listView;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 123;
    static final int REQUEST_IMAGE_CAPTURE = 22;
    private ArrayAdapter<InterestPoint> interestPointsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        App = (MyApp) getApplication();

        ImageView returnButton, cameraButton;

        image = findViewById(R.id.imgPhoto);
        returnButton = findViewById(R.id.imgReturn);
        cameraButton = findViewById(R.id.imgCamera);
        listView = findViewById(R.id.listView);

        interestPointsAdapter = new ArrayAdapter<InterestPoint>(this, R.layout.row_layout, R.id.listText, App.getPoints());
        listView.setAdapter(interestPointsAdapter);

        App.getMachineStatus().getPhoto(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    image.setImageBitmap(bitmap);
                }
            }
        });

        barChart1 = findViewById(R.id.barChart);
        barChart2 = findViewById(R.id.barChart2);
        barChart3 = findViewById(R.id.barChart3);
        pieChart = findViewById(R.id.pieChart);

        setupBarChart(barChart1);
        setupBarChart(barChart2);
        setupBarChart(barChart3);
        setupPieChart();

        updateCharts();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturnClicked();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
                else {
                    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
                    }
                    else {
                        openCamera();
                    }
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
                }
                else {
                    openCamera();
                }
            }
        }
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    private void setupBarChart(BarChart bar) {
        bar.getDescription().setEnabled(false);
        bar.setDrawGridBackground(false);
    }

    private void setupPieChart() {
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
    }

    public void updateCharts() {
        App.get3LatestMachineStatus(new MyApp.StatusCallback() {
            @Override
            public void onCallback(ArrayList<MachineStatus> status) {
                ArrayList<Integer> list1 = new ArrayList<>();
                list1.add(status.get(0).getBatteryStatus());
                list1.add(status.get(1).getBatteryStatus());
                list1.add(status.get(2).getBatteryStatus());

                ArrayList<Integer> list2 = new ArrayList<>();
                list2.add((int) status.get(0).getPowerConsumption());
                list2.add((int) status.get(1).getPowerConsumption());
                list2.add((int) status.get(2).getPowerConsumption());

                ArrayList<Integer> list3 = new ArrayList<>();
                list3.add((int) status.get(0).getOperatingTemperature());
                list3.add((int) status.get(1).getOperatingTemperature());
                list3.add((int) status.get(2).getOperatingTemperature());


                updateChart(barChart1, "Battery Status", list1);
                updateChart(barChart2, "Power Consumption", list2);
                updateChart(barChart3, "Operating Temperature", list3);

                List<PieEntry> pieEntries = new ArrayList<>();

                pieEntries.add(new PieEntry(status.get(0).getRuntimeHours(), "D.1"));
                pieEntries.add(new PieEntry(status.get(1).getRuntimeHours(), "D.2"));
                pieEntries.add(new PieEntry(status.get(2).getRuntimeHours(), "D.3"));

                PieDataSet pieDataSet = new PieDataSet(pieEntries, "Runtime Hours");
                pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.invalidate();

                App.initializeList(new ArrayList<String>(Arrays.asList(status.get(0).getUsersData(), status.get(1).getUsersData(), status.get(2).getUsersData())));
                interestPointsAdapter.notifyDataSetChanged();
            }
        });
    }

    public void updateChart(BarChart bar, String Datos, ArrayList<Integer> status) {
        List<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, status.get(0)));
        barEntries.add(new BarEntry(2, status.get(1)));
        barEntries.add(new BarEntry(3, status.get(2)));

        BarDataSet barDataSet = new BarDataSet(barEntries, Datos);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData barData = new BarData(barDataSet);
        bar.setData(barData);
        bar.invalidate();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(imageBitmap);
                App.getMachineStatus().setPhoto(imageBitmap);
                App.savePhoto(imageBitmap);
            }
        }
    }

    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
    private void onReturnClicked() {
        finish();
    }
}