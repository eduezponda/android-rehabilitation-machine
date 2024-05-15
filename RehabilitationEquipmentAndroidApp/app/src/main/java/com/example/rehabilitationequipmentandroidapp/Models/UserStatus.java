package com.example.rehabilitationequipmentandroidapp.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Arrays;

@ParseClassName("UserStatus")
public class UserStatus extends ParseObject {
    public static final String KEY_ID = "sessionId";
    public static final String KEY_NAME = "sessionName";
    public static final String KEY_DURATION = "sessionDuration";
    public static final String KEY_BODY_PART = "sessionBodyPart";
    public static final String KEY_EXERCISE_MODE = "sessionExerciseMode";
    public static final String KEY_INTENSITY = "sessionIntensity";
    public static final String KEY_ID_SUPERVISOR = "sessionIdSupervisor";
    public static final String KEY_COMMENT = "sessionComment";
    public static final String KEY_MACHINE_ID = "sessionMachineId";

    public UserStatus() {
        super();
    }

    public void init(String name, int Id) {
        setId(Id);
        setMachineId(-1);
        setName(name); // Initializing name with the provided value
        setDuration(45); // Example duration in minutes
        setBodyPartFocus("Arm"); // Example body part
        setExerciseMode("Moderate Impact"); // Example exercise mode
        setIntensity(3); // Example intensity on a scale of 1-5
        setIdSupervisor("Supervisor X"); // Example supervisor ID
        setComments("No significant changes observed during the session."); // Example comment
    }

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public int getId() {
        return getInt(KEY_ID);
    }

    public void setId(int id) {
        put(KEY_ID, id);
    }

    public int getMachineId() {
        return getInt(KEY_MACHINE_ID);
    }

    public void setMachineId(int id) {
        put(KEY_MACHINE_ID, id);
    }

    public String getBodyPartFocus() {
        return getString(KEY_BODY_PART);
    }

    public void setBodyPartFocus(String bodyPart) {
        put(KEY_BODY_PART, bodyPart);
    }

    public int getDuration() {
        return getInt(KEY_DURATION);
    }

    public void setDuration(int duration) {
        put(KEY_DURATION, duration);
    }

    public String getExerciseMode() {
        return getString(KEY_EXERCISE_MODE);
    }

    public void setExerciseMode(String exerciseMode) {
        put(KEY_EXERCISE_MODE, exerciseMode);
    }

    public int getIntensity() {
        return getInt(KEY_INTENSITY);
    }

    public void setIntensity(int intensity) {
        put(KEY_INTENSITY, intensity);
    }

    public String getIdSupervisor() {
        return getString(KEY_ID_SUPERVISOR);
    }

    public void setIdSupervisor(String idSupervisor) {
        put(KEY_ID_SUPERVISOR, idSupervisor);
    }

    public String getComments() {
        return getString(KEY_COMMENT);
    }

    public void setComments(String comment) {
        put(KEY_COMMENT, comment);
    }

    public ArrayList<String> toArray()
    {
        return (new ArrayList<String>(Arrays.asList(getExerciseMode(), "Body Part: " + getBodyPartFocus() + " and Intensity: "+ getIntensity(), getName())));
    }
}
