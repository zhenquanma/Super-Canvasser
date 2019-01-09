package com.supercanvasser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.supercanvasser.bean.Location;
import com.supercanvasser.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CampaignDTO {

    //added
    private String campaignID;
    //added
    private String campaignName;
    //added
    private String questions;

    @JsonFormat(timezone = "EST")
    private Date startDate;

    @JsonFormat(timezone = "EST")
    private Date endDate;

    private Double visitDuration;

    private String talkingPoints;

    private String locations;

    private List<String> managers;

    private List<String> canvassers;



    public CampaignDTO() {
    }

    public CampaignDTO(String campaignId, String campaignName, String questions, Date startDate, Date endDate, Double visitDuration, String talkingPoints, String locations, List<String> managers, List<String> canvassers) {
        this.campaignID = campaignId;
        this.campaignName = campaignName;
        this.questions = questions;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visitDuration = visitDuration;
        this.talkingPoints = talkingPoints;
        this.locations = locations;
        this.managers = managers;
        this.canvassers = canvassers;
    }

    public String getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(String campaignId) {
        this.campaignID = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getVisitDuration() {
        return visitDuration;
    }

    public void setVisitDuration(Double visitDuration) {
        this.visitDuration = visitDuration;
    }

    public String getTalkingPoints() {
        return talkingPoints;
    }

    public void setTalkingPoints(String talkingPoints) {
        this.talkingPoints = talkingPoints;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public List<String> getManagers() {
        return managers;
    }

    public void setManagers(List<String> managers) {
        this.managers = managers;
    }

    public List<String> getCanvassers() {
        return canvassers;
    }

    public void setCanvassers(List<String> canvassers) {
        this.canvassers = canvassers;
    }
}
