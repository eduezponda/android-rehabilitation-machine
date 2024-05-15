package com.example.rehabilitationequipmentuserapp;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.rehabilitationequipmentuserapp.Models.InterestPoint;
import com.example.rehabilitationequipmentuserapp.Models.MachineStatus;
import com.example.rehabilitationequipmentuserapp.Models.UserStatus;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {
    private UserStatus userStatus;
    private ArrayList<UserStatus> statusArray;
    private List<InterestPoint> pointList = new ArrayList<>();
    private DataInitializationListener dataInitializationListener;

    public interface DataInitializationListener {
        void onDataInitialized();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(UserStatus.class);
        ParseObject.registerSubclass(MachineStatus.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        //initData();
        userStatus = new UserStatus();
        statusArray = new ArrayList<>();
        getFewLatestUserStatus();

        getFewLatestUserStatus(10, new StatusCallback() {
            @Override
            public void onCallback(ArrayList<UserStatus> status) {

            }
        });
    }


    /*private void initData()
    {
        for(int i= 0; i<3; i++)
        {
            userStatus = new UserStatus();
            userStatus.init("nombre", i);
            saveUserStatusToBack4App();

        }
    }*/

    public void setDataInitializationListener(DataInitializationListener listener) {
        this.dataInitializationListener = listener;
    }

    public UserStatus getUserStatus() {return userStatus;}

    public void saveUserStatus(Bitmap image, String name, int duration, String bodyPart, String exerciseMode, int intensity, String idSupervisor, String comment) {
        userStatus = new UserStatus();

        userStatus.setName(name);
        userStatus.setDuration(duration);
        userStatus.setBodyPartFocus(bodyPart);
        userStatus.setExerciseMode(exerciseMode);
        userStatus.setIntensity(intensity);
        userStatus.setIdSupervisor(idSupervisor);
        userStatus.setComments(comment);

        InterestPoint aInterestPoint;

        aInterestPoint = new InterestPoint();
        aInterestPoint.setData(userStatus.toArray());
        aInterestPoint.setImage(image);
        pointList.add(0, aInterestPoint);

        if (pointList.size() > 10) {
            pointList.remove(10);
        }

        saveUserStatusToBack4App();
    }

    public void updateUserStatus(int index, Bitmap image, String name, int duration, String bodyPart, String exerciseMode, int intensity, String idSupervisor, String comment) {
        getFewLatestUserStatus(10, new StatusCallback() {
            @Override
            public void onCallback(ArrayList<UserStatus> userStatusList) {
                userStatus = userStatusList.get(index);

                if(name!=""){userStatus.setName(name);}
                userStatus.setDuration(duration);
                if(bodyPart!=""){userStatus.setBodyPartFocus(bodyPart);}
                userStatus.setExerciseMode(exerciseMode);
                userStatus.setIntensity(intensity);
                if(idSupervisor!=""){userStatus.setIdSupervisor(idSupervisor);}
                if(comment!=""){userStatus.setComments(comment);}

                pointList.get(index).setData(userStatus.toArray());
                pointList.get(index).setImage(image);

                saveUserStatusToBack4App();
            }
        });
    }

    public void saveUserStatusToBack4App() {
        userStatus.saveInBackground(e -> {
            if (e != null) {
                Log.e("Parse Save Error", "Failed to save userStatus", e);
            }
        });
    }

    private void getFewLatestUserStatus() {
        ParseQuery<UserStatus> query = ParseQuery.getQuery(UserStatus.class);
        query.orderByDescending("createdAt");
        query.getFirstInBackground((userStatus, e) -> {
            if (e == null) {
                this.userStatus = userStatus;
            } else {
                Log.e("Parse Query Error", "Failed to fetch latest userStatus", e);
            }
        });
    }

    public interface StatusCallback {
        void onCallback(ArrayList<UserStatus> statusArray);
    }

    public interface MachineCallback {
        void onCallback(ArrayList<MachineStatus> statusArray);
    }

    public void getFewLatestUserStatus(int limit, StatusCallback callback) {
        ParseQuery<UserStatus> query = ParseQuery.getQuery(UserStatus.class);
        query.orderByDescending("createdAt").setLimit(limit);  // Cambiar el limite para mostrar más elementos en la lista de histórico de pedidos
        query.findInBackground((statusArrayList, e) -> {
            if (e == null) {
                callback.onCallback(new ArrayList<>(statusArrayList));
                this.statusArray = new ArrayList<UserStatus>(statusArrayList);
            }
        });
    }

    public void getMachine(MachineCallback callback) {
        ParseQuery<MachineStatus> query = ParseQuery.getQuery(MachineStatus.class);
        query.whereEqualTo("orderId", userStatus.getId());
        query.orderByDescending("createdAt").setLimit(1);
        query.findInBackground((machineArrayList, e) -> {
            if (e == null) {
                callback.onCallback(new ArrayList<>(machineArrayList));
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

    public void initializeList(ArrayList<ArrayList<String>> data, ArrayList<Bitmap> images) {

        InterestPoint aInterestPoint;
        clear();

        for (int i = 0; i < data.size(); i++) {
            aInterestPoint = new InterestPoint();
            aInterestPoint.setData(data.get(i));
            aInterestPoint.setImage(images.get(i));
            pointList.add(i,aInterestPoint);
        }

        if (dataInitializationListener != null) {
            dataInitializationListener.onDataInitialized();
        }
    }
}

