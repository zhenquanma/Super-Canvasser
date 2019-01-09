package com.supercanvasser.bean;

public class GlobleVariables {

    Integer id;

    Double duration;

    Double averageSpeed;

    public GlobleVariables() {
    }

    public GlobleVariables(Integer id, Double duration, Double averageSpeed) {
        this.id = id;
        this.duration = duration;
        this.averageSpeed = averageSpeed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
