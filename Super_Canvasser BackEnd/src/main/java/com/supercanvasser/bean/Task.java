package com.supercanvasser.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Task implements Serializable {

    Integer id;

    String taskName;

    Integer canvasserId;

    Date date;

    Double duration;

    Integer campaignId;

    Boolean status; //Unfinished is false, finished is true

    List<Location> locations;

    List<Location> visitedLocation;

    List<Location> unvisitedLocation;

    public Task() {
    }

    public Task(Integer id, String taskName, Integer canvasserId, Date date, Double duration, Integer campaignId, Boolean status,
                List<Location> locations, List<Location> visitedLocation, List<Location> unvisitedLocation) {
        this.id = id;
        this.taskName = taskName;
        this.canvasserId = canvasserId;
        this.date = date;
        this.duration = duration;
        this.campaignId = campaignId;
        this.status = status;
        this.locations = locations;
        this.visitedLocation = visitedLocation;
        this.unvisitedLocation = unvisitedLocation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getCanvasserId() {
        return canvasserId;
    }

    public void setCanvasserId(Integer canvasserId) {
        this.canvasserId = canvasserId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getVisitedLocation() {
        return visitedLocation;
    }

    public void setVisitedLocation(List<Location> visitedLocation) {
        this.visitedLocation = visitedLocation;
    }

    public List<Location> getUnvisitedLocation() {
        return unvisitedLocation;
    }

    public void setUnvisitedLocation(List<Location> unvisitedLocation) {
        this.unvisitedLocation = unvisitedLocation;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", canvasserId=" + canvasserId +
                ", date=" + date +
                ", duration=" + duration +
                ", campaignId=" + campaignId +
                ", status=" + status +
                ", locations=" + locations +
                '}';
    }
}
