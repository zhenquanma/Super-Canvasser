package com.supercanvasser.bean;

import java.util.Date;
import java.util.List;

public class Campaign {

    private Integer id;

    private String campaignName;

    private Date startDate;

    private Date endDate;

    private Double visitDuration;

    private String talkingPoints;

    private List<User> managers;

    private List<User> canvassers;

    private List<Location> locations;

    private List<Result> results;

    private List<Question> questions;


    public Campaign() {
    }


    public Campaign(Integer id, String campaignName, Date startDate, Date endDate, Double visitDuration, String talkingPoints,
                    List<User> managers, List<User> canvassers, List<Location> locations,
                    List<Result> results, List<Question> questions) {
        this.id = id;
        this.campaignName = campaignName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visitDuration = visitDuration;
        this.talkingPoints = talkingPoints;
        this.managers = managers;
        this.canvassers = canvassers;
        this.locations = locations;
        this.results = results;
        this.questions = questions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
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

    public List<User> getManagers() { return managers; }

    public void setManagers(List<User> managers) { this.managers = managers; }

    public List<User> getCanvassers() { return canvassers; }

    public void setCanvassers(List<User> canvassers) { this.canvassers = canvassers; }

    public List<Location> getLocations() { return locations; }

    public void setLocations(List<Location> locations) { this.locations = locations; }

    public List<Result> getResults() { return results; }

    public void setResults(List<Result> results) { this.results = results; }

    public List<Question> getQuestions() { return questions; }

    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public String locationsToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Location l : this.locations){
            stringBuilder.append(l.toString());
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", visitDuration=" + visitDuration +
                ", talkingPoints='" + talkingPoints + '\'' +
                ", managers=" + managers +
                ", canvassers=" + canvassers +
                ", locations=" + locations +
                ", results=" + results +
                ", questions=" + questions +
                '}';
    }
}
