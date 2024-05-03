package com.example.rehabilitationequipmentandroidapp.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

@ParseClassName("MachineStatus")
public class MachineStatus extends ParseObject {
    public static final String KEY_ID = "machineId";
    public static final String KEY_BATTERY_STATUS = "batteryStatus";
    public static final String KEY_STATUS = "status";
    public static final String KEY_POWER_CONSUMPTION = "powerConsumption";
    public static final String KEY_OPERATING_TEMPERATURE = "operatingTemperature";
    public static final String KEY_RUNTIME_HOURS = "runtimeHours";
    public static final String KEY_HEART_RATE = "heartRate";
    public static final String KEY_OXYGEN_SATURATION = "oxygenSaturation";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_PHOTO = "photo";

    public MachineStatus() {
        super();
    }
    public void init(String id, int batteryStatus, String status, String userId, double powerConsumption, double operatingTemperature, int runtimeHours, int heartRate, int oxygenSaturation) {
        setId(id);
        setBatteryStatus(batteryStatus);
        setStatus(status);
        setPowerConsumption(powerConsumption);
        setOperatingTemperature(operatingTemperature);
        setRuntimeHours(runtimeHours);
        setHeartRate(heartRate);
        setOxygenSaturation(oxygenSaturation);
        setUserId(userId);
    }

    public String getId() {
        return getString(KEY_ID);
    }

    public void setId(String id) {
        put(KEY_ID, id);
    }

    public String getUserId() {
        return getString(KEY_USER_ID);
    }

    public void setUserId(String id) {
        put(KEY_USER_ID, id);
    }

    public int getBatteryStatus() {
        return getInt(KEY_BATTERY_STATUS);
    }

    public void setBatteryStatus(int batteryStatus) {
        put(KEY_BATTERY_STATUS, batteryStatus);
    }

    public String getStatus() {
        return getString(KEY_STATUS);
    }

    public void setStatus(String status) {
        put(KEY_STATUS, status);
    }

    public double getPowerConsumption() {
        return getDouble(KEY_POWER_CONSUMPTION);
    }

    public void setPowerConsumption(double powerConsumption) {
        put(KEY_POWER_CONSUMPTION, powerConsumption);
    }

    public double getOperatingTemperature() {
        return getDouble(KEY_OPERATING_TEMPERATURE);
    }

    public void setOperatingTemperature(double operatingTemperature) {
        put(KEY_OPERATING_TEMPERATURE, operatingTemperature);
    }

    public int getRuntimeHours() {
        return getInt(KEY_RUNTIME_HOURS);
    }

    public void setRuntimeHours(int runtimeHours) {
        put(KEY_RUNTIME_HOURS, runtimeHours);
    }

    public int getHeartRate() {
        return getInt(KEY_HEART_RATE);
    }

    public void setHeartRate(int heartRate) {
        put(KEY_HEART_RATE, heartRate);
    }

    public int getOxygenSaturation() {
        return getInt(KEY_OXYGEN_SATURATION);
    }

    public void setOxygenSaturation(int oxygenSaturation) {
        put(KEY_OXYGEN_SATURATION, oxygenSaturation);
    }

    public String getUsersData() {
        return "Heart Rate: " + getHeartRate() +  ", Oxygen Saturation: " + getOxygenSaturation();
    }

    public void setPhoto(Bitmap photo) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        ParseFile file = new ParseFile("photo.png", byteArray);

        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    put(KEY_PHOTO, file);
                }
            }
        });
    }

    public void getPhoto(GetDataCallback callback) {
        ParseFile file = getParseFile(KEY_PHOTO);
        if (file != null) {
            file.getDataInBackground(callback);
        }
    }
}
