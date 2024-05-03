package com.example.rehabilitationequipmentandroidapp;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.rehabilitationequipmentandroidapp.Models.InterestPoint;
import com.example.rehabilitationequipmentandroidapp.Models.MachineStatus;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyApp extends Application {
    private MachineStatus machineStatus;
    private ArrayList<MachineStatus> statusArray;
    private List<InterestPoint> pointList = new ArrayList<>();
    private Bitmap photo;

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(MachineStatus.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        //initData();
        machineStatus = new MachineStatus();
        statusArray = new ArrayList<>();
        getLatestMachineStatus();

        get3LatestMachineStatus( new StatusCallback() {
            @Override
            public void onCallback(ArrayList<MachineStatus> status) {}
        });
    }

    /*private void initData() {
        machineStatus = new MachineStatus();
        machineStatus.init("5Rehabili3841", 80, 0, "working",200, 20, 9, 150, 97);
    }*/
    public MachineStatus getMachineStatus() {
        return machineStatus;
    }

    public void saveMachineStatus(String id, int batteryStatus, String status, String userId, double powerConsumption, double operatingTemperature, int runtimeHours, int heartRate, int oxygenSaturation) {
        machineStatus = new MachineStatus();

        machineStatus.setId(id);
        machineStatus.setBatteryStatus(batteryStatus);
        machineStatus.setStatus(status);
        machineStatus.setPowerConsumption(powerConsumption);
        machineStatus.setOperatingTemperature(operatingTemperature);
        machineStatus.setRuntimeHours(runtimeHours);
        machineStatus.setHeartRate(heartRate);
        machineStatus.setOxygenSaturation(oxygenSaturation);
        machineStatus.setPhoto(photo);
        machineStatus.setUserId(userId);

        saveMachineStatusToBack4App();
        statusArray.remove(0);
        statusArray.add(machineStatus);
    }
    public void savePhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void saveMachineStatusToBack4App() {
        machineStatus.saveInBackground(e -> {
            if (e != null) {
                Log.e("Parse Save Error", "Failed to save MachineStatus", e);
            }
        });
    }

    private void getLatestMachineStatus() {
        ParseQuery<MachineStatus> query = ParseQuery.getQuery(MachineStatus.class);
        query.orderByDescending("createdAt");
        query.getFirstInBackground((machineStatus, e) -> {
            if (e == null) {
                this.machineStatus = machineStatus;
            } else {
                Log.e("Parse Query Error", "Failed to fetch latest MachineStatus", e);
            }
        });
    }

    public interface StatusCallback {
        void onCallback(ArrayList<MachineStatus> statusArray);
    }

    public void get3LatestMachineStatus(StatusCallback callback) {
        ParseQuery<MachineStatus> query = ParseQuery.getQuery(MachineStatus.class);
        query.orderByDescending("createdAt").setLimit(3);
        query.findInBackground((statusArrayList, e) -> {
            if (e == null) {
                callback.onCallback(new ArrayList<>(statusArrayList));
                this.statusArray = new ArrayList<MachineStatus>(statusArrayList);
            }
        });
    }

    public List<InterestPoint> getPoints() {
        return pointList;
    }

    public InterestPoint getPointByIndex(int i) {
        return pointList.get(i);
    }

    public void addPoint(InterestPoint point) {
        pointList.add(point);
    }

    public void clear() {
        pointList.clear();
    }

    public void initializeList(ArrayList<String> data) {

        InterestPoint aInterestPoint;
        clear();

        for(int i=0; i<3; i++){
            aInterestPoint = new InterestPoint();
            aInterestPoint.setData(data.get(i));
            pointList.add(i,aInterestPoint);
        }
    }
}

