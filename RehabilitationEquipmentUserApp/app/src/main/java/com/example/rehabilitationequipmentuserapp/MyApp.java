package com.example.rehabilitationequipmentuserapp;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.rehabilitationequipmentuserapp.Models.InterestPoint;
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
    private Bitmap photo;

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(UserStatus.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        //initData();
        userStatus = new UserStatus();
        statusArray = new ArrayList<>();
        getFewLatestUserStatus();

        getFewLatestUserStatus(new StatusCallback() {
            @Override
            public void onCallback(ArrayList<UserStatus> status) {}
        });
    }

    /*private void initData() {
        userStatus = new userStatus();
        userStatus.init("5Rehabili3841", 80, "Kinesioterapia", "working",200, 20, 9, 150, "120/80", 97);
    }*/
    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void saveUserStatus(String name, int duration, String bodyPart, String exerciseMode, int intensity, String idSupervisor, String comment) {
        UserStatus userStatus = new UserStatus();

        userStatus.init(name);  // Asumiendo que el nombre se proporciona en algún lugar del contexto
        userStatus.setDuration(duration);
        userStatus.setBodyPartFocus(bodyPart);
        userStatus.setExerciseMode(exerciseMode);
        userStatus.setIntensity(intensity);
        userStatus.setIdSupervisor(idSupervisor);
        userStatus.setComments(comment);

        saveUserStatusToBack4App();
        statusArray.remove(0);
        statusArray.add(userStatus);
    }
    public void savePhoto(Bitmap photo) {
        this.photo = photo;
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

    public void getFewLatestUserStatus(StatusCallback callback) {
        ParseQuery<UserStatus> query = ParseQuery.getQuery(UserStatus.class);
        query.orderByDescending("createdAt").setLimit(3);  // Cambiar el limite para mostrar más elementos en la lista de histórico de pedidos
        query.findInBackground((statusArrayList, e) -> {
            if (e == null) {
                callback.onCallback(new ArrayList<>(statusArrayList));
                this.statusArray = new ArrayList<UserStatus>(statusArrayList);
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

