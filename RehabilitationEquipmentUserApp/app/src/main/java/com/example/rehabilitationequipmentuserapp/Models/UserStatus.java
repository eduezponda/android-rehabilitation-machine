package com.example.rehabilitationequipmentuserapp.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("MachineStatus")
public class UserStatus extends ParseObject {
    public static final String KEY_NAME = "sessionName";
    public static final String KEY_DURATION = "sessionDuration";
    public static final String KEY_BODY_PART = "sessionBodyPart";
    public static final String KEY_EXERCISE_MODE = "sessionExerciseMode";
    public static final String KEY_INTENSITY = "sessionIntensity";
    public static final String KEY_ID_SUPERVISOR = "sessionIdSupervisor";
    public static final String KEY_COMMENT = "sessionComment";

    public UserStatus() {
        super();
    }

    public void init(String name) {
        setName(name);
    }

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public String getBodyPartFocus() {
        return getString(KEY_BODY_PART);
    }

    public void setBodyPartFocus(String bodyPart) {
        put(KEY_BODY_PART, bodyPart);
    }

    public String getDuration() {
        return getString(KEY_DURATION);
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
}
