package com.supercanvasser.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {

    @JsonProperty("QuestionId")
    Integer id;

    @JsonProperty("Question")
    String content;

    public Question() {
    }

    public Question(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content +
                '}';
    }
}
