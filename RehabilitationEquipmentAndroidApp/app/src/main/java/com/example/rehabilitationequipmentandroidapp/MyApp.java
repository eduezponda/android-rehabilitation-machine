package com.example.rehabilitationequipmentandroidapp;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.rehabilitationequipmentandroidapp.Models.InterestPoint;
import com.example.rehabilitationequipmentandroidapp.Models.MachineStatus;
import com.example.rehabilitationequipmentandroidapp.Models.UserStatus;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyApp extends Application {
    private MachineStatus machineStatus;
    private List<InterestPoint> pointList = new ArrayList<>();
    private Bitmap photo;

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(MachineStatus.class);
        ParseObject.registerSubclass(UserStatus.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        //initData();
        machineStatus = new MachineStatus();
        getLatestMachineStatus();

        get3LatestMachineStatus( new StatusCallback() {
            @Override
            public void onCallback(ArrayList<MachineStatus> status) {}
        });
    }

    /*private void initData() {
        machineStatus = new MachineStatus();
        saveMachineStatus(2, 80, "working", -1, 200, 20, 9, 150, 97);
    }*/
    public MachineStatus getMachineStatus() {
        return machineStatus;
    }

    public void saveMachineStatus(int id, int batteryStatus, String status, int userId, double powerConsumption, double operatingTemperature, int runtimeHours, int heartRate, int oxygenSaturation) {
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
        machineStatus.setOrderId(userId);

        saveMachineStatusToBack4App();
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

    public interface UserCallback {
        void onCallback(ArrayList<UserStatus> userArray);
    }

    public void get3LatestMachineStatus(StatusCallback callback) {
        ParseQuery<MachineStatus> query = ParseQuery.getQuery(MachineStatus.class);
        query.orderByDescending("createdAt").setLimit(3);
        query.findInBackground((statusArrayList, e) -> {
            if (e == null) {
                callback.onCallback(new ArrayList<>(statusArrayList));
            }
        });
    }

    public void getOrders(UserCallback callback) {
        ParseQuery<UserStatus> query = ParseQuery.getQuery(UserStatus.class);
        query.orderByAscending("createdAt");
        query.findInBackground((list, e) -> {
            if (e == null) {
                ArrayList<UserStatus> userStatuses = new ArrayList<>();
                for (Object obj : list) {
                    if (obj instanceof UserStatus) {
                        userStatuses.add((UserStatus) obj);
                    } else {
                        Log.e("getOrders", "Received object is not of type UserStatus");
                    }
                }
                callback.onCallback(userStatuses);
            } else {
                Log.e("getOrders", "Error: " + e.getMessage());
            }
        });
    }

    public interface SetUserMachineCallback {
        void onCallback(UserStatus userStatus);
    }

    public void setMachineIdToItsOrder(int id, SetUserMachineCallback callback){
        ParseQuery<UserStatus> query = ParseQuery.getQuery(UserStatus.class);
        query.whereEqualTo("sessionId", id);
        query.orderByAscending("createdAt");
        query.findInBackground((list, e) -> {
            if (e == null) {
                UserStatus userStatus = list.get(0);
                callback.onCallback(userStatus);
            } else {
                Log.e("setMachineIdToItsOrder", "Error: " + e.getMessage());
            }
        });
    }
    public void saveUserStatusToBack4App(UserStatus userStatus) {
        userStatus.saveInBackground(e -> {
            if (e != null) {
                Log.e("Parse Save Error", "Failed to save userStatus", e);
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

