package com.supercanvasser.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = { "handler" })
public class User{

    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private List<Date> freeDays;

    private List<Campaign> campaignList;


    public User() {
    }

    public User(String firstName, String lastName, String username, String password){
        this(null, firstName, lastName, username, password, null, null);
    }

    public User(Integer id, String firstName, String lastName, String username, String password){
        this(id, firstName, lastName, username, password, null, null);
    }

    public User(Integer id, String firstName, String lastName, String username, String password, List<Date> freeDays, List<Campaign> campaignList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.freeDays = freeDays;
        this.campaignList = campaignList;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Campaign> getCampaignList() {
        return campaignList;
    }

    public void setCampaignList(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    public List<Date> getFreeDays() {
        return freeDays;
    }

    public void setFreeDays(List<Date> freeDays) {
        this.freeDays = freeDays;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", freeDays=" + freeDays +
                ", campaignList=" + campaignList +
                '}';
    }
}
