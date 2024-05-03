package com.example.rehabilitationequipmentuserapp.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("MachineStatus")
public class UserStatus extends ParseObject {
    public static final String KEY_ID = "machineId";
    //public static final String KEY_BATTERY_STATUS = "batteryStatus";
    //public static final String KEY_TYPE = "type";
    //public static final String KEY_STATUS = "status";
    //public static final String KEY_POWER_CONSUMPTION = "powerConsumption";
    //public static final String KEY_OPERATING_TEMPERATURE = "operatingTemperature";
    //public static final String KEY_RUNTIME_HOURS = "runtimeHours";
    public static final String KEY_HEART_RATE = "heartRate";
    public static final String KEY_BLOOD_PRESSURE = "bloodPressure";
    public static final String KEY_OXYGEN_SATURATION = "oxygenSaturation";
    //public static final String KEY_PHOTO = "photo";

    public UserStatus() {
        super();
    }
    public void init(String id, int heartRate, String bloodPressure, int oxygenSaturation) {
        setId(id);
        setHeartRate(heartRate);
        setBloodPressure(bloodPressure);
        setOxygenSaturation(oxygenSaturation);
    }

    public String getId() {
        return getString(KEY_BLOOD_PRESSURE);
    }

    public void setId(String id) {
        put(KEY_ID, id);
    }
    public int getHeartRate() {
        return getInt(KEY_HEART_RATE);
    }

    public void setHeartRate(int heartRate) {
        put(KEY_HEART_RATE, heartRate);
    }

    public String getBloodPressure() {
        return getString(KEY_BLOOD_PRESSURE);
    }

    public void setBloodPressure(String bloodPressure) {
        put(KEY_BLOOD_PRESSURE, bloodPressure);
    }

    public int getOxygenSaturation() {
        return getInt(KEY_OXYGEN_SATURATION);
    }

    public void setOxygenSaturation(int oxygenSaturation) {
        put(KEY_OXYGEN_SATURATION, oxygenSaturation);
    }

    public String getUsersData() {
        return "Heart Rate: " + Integer.toString(getHeartRate()) + ", Blood pressure: " + getBloodPressure() + ", Oxygen Saturation: " + Integer.toString(getOxygenSaturation());
    }
}
