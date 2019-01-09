package com.supercanvasser.bean;

public class Location{

    Integer id;

    Integer number;

    String street;

    String unit;

    String city;

    String state;

    Integer zip;

    Double longitude;

    Double latitude;

    public Location() {
    }

    /**
     * Constructor for location without longitude and latitude
     * @param id
     * @param number
     * @param street
     * @param unit
     * @param city
     * @param state
     * @param zip
     */
    public Location(Integer id, Integer number, String street, String unit, String city, String state, Integer zip) {
        this(id, number, street, unit, city, state, zip, null, null);
    }

    /**
     * Constructor for location with longitude and latitude
     * @param id
     * @param number
     * @param street
     * @param unit
     * @param city
     * @param state
     * @param zip
     * @param longitude
     * @param latitude
     */
    public Location(Integer id, Integer number, String street, String unit, String city,
                    String state, Integer zip, Double longitude, Double latitude) {
        this.id = id;
        this.number = number;
        this.street = street;
        this.unit = unit;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(number);
        builder.append(", ");
        builder.append(street);
        builder.append(", ");
        if(unit != null) {
            builder.append(unit);
            builder.append(", ");
        }
        builder.append(city);
        builder.append(", ");
        builder.append(state);
        builder.append(", ");
        builder.append(zip);
        return builder.toString();
    }
}
