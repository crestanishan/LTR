package com.nishan.tasker.Database;

/**
 * Created by NaRan on 8/18/17 at 20:16.
 */

public class Task {

    private String lat;
    private String log;
    private String date;
    private String time;
    private String taskDetails;

    public Task() {
    }

    public Task(String lat, String log, String date, String time, String taskDetails) {
        this.lat = lat;
        this.log = log;
        this.date = date;
        this.time = time;
        this.taskDetails = taskDetails;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }
}
