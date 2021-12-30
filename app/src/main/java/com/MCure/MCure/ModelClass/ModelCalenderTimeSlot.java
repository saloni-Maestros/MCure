package com.MCure.MCure.ModelClass;

public class ModelCalenderTimeSlot {
    String id = "";
    String times = "";
    public boolean clicked = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDr_status() {
        return dr_status;
    }

    public void setDr_status(String dr_status) {
        this.dr_status = dr_status;
    }

    String dr_status = "";
}
