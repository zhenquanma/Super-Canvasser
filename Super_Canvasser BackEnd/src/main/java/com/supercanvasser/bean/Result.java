package com.supercanvasser.bean;

import java.util.List;

public class Result {

    Integer id;

    Boolean isSpoke;

    Integer rating;

    List<QuestionAnswer> questionAnswers;

    String briefNote;

    Location location;

    public Result() {
    }

    public Result(Integer id, Boolean isSpoke, Integer rating, List<QuestionAnswer> questionAnswers, String briefNote, Location location) {
        this.id = id;
        this.isSpoke = isSpoke;
        this.rating = rating;
        this.questionAnswers = questionAnswers;
        this.briefNote = briefNote;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSpoke() {
        return isSpoke;
    }

    public void setSpoke(Boolean spoke) {
        isSpoke = spoke;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public String getBriefNote() {
        return briefNote;
    }

    public void setBriefNote(String briefNote) {
        this.briefNote = briefNote;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", isSpoke=" + isSpoke +
                ", rating=" + rating +
                ", questionAnswers=" + questionAnswers +
                ", briefNote='" + briefNote + '\'' +
                ", location=" + location +
                '}';
    }
}
