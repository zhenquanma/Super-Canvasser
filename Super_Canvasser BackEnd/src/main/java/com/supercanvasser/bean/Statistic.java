package com.supercanvasser.bean;

public class Statistic {

    Double mean;

    Double stdev;

    public Statistic() {
    }

    public Statistic(Double mean, Double stdev) {
        this.mean = mean;
        this.stdev = stdev;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public Double getStdev() {
        return stdev;
    }

    public void setStdev(Double stdev) {
        this.stdev = stdev;
    }


    @Override
    public String toString() {
        return "Statistic{" +
                "mean=" + mean +
                ", stdev=" + stdev +
                '}';
    }
}
