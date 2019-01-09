package com.supercanvasser.bean;

public class QuestionAnswer {

    Integer id;

    Question question;

    Location location;

    Boolean answer;

    public QuestionAnswer() {
    }

    public QuestionAnswer(Question question, Location location, Boolean answer) {
        this(null, question, location, answer);
    }

    public QuestionAnswer(Integer id, Question question, Location location, Boolean answer) {
        this.id = id;
        this.question = question;
        this.location = location;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "id=" + id +
                ", question=" + question +
                ", answer=" + answer +
                '}';
    }

}
