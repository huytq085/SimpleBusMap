package com.quanghuy.busmap.entity;

/**
 * Created by Huy on 3/22/2018.
 */

public class Route {
    private int code;
    private String name;
    private String routeAB;
    private String routeBA;
    private String agencies;
    private String type;
    private String distance;
    private String vehicleType;
    private String time;
    private float basicPrice;
    private float studentPrice;
    private float monthlyPrice;
    private String totalTrip;
    private String tripTime;
    private String tripSpacing;

    public Route(int code, String name, String routeAB, String routeBA, String agencies, String type, String distance, String vehicleType, String time, float basicPrice, float studentPrice, float monthlyPrice, String totalTrip, String tripTime, String tripSpacing) {
        this.code = code;
        this.name = name;
        this.routeAB = routeAB;
        this.routeBA = routeBA;
        this.agencies = agencies;
        this.type = type;
        this.distance = distance;
        this.vehicleType = vehicleType;
        this.time = time;
        this.basicPrice = basicPrice;
        this.studentPrice = studentPrice;
        this.monthlyPrice = monthlyPrice;
        this.totalTrip = totalTrip;
        this.tripTime = tripTime;
        this.tripSpacing = tripSpacing;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteAB() {
        return routeAB;
    }

    public void setRouteAB(String routeAB) {
        this.routeAB = routeAB;
    }

    public String getRouteBA() {
        return routeBA;
    }

    public void setRouteBA(String routeBA) {
        this.routeBA = routeBA;
    }

    public String getAgencies() {
        return agencies;
    }

    public void setAgencies(String agencies) {
        this.agencies = agencies;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(float basicPrice) {
        this.basicPrice = basicPrice;
    }

    public float getStudentPrice() {
        return studentPrice;
    }

    public void setStudentPrice(float studentPrice) {
        this.studentPrice = studentPrice;
    }

    public float getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(float monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getTotalTrip() {
        return totalTrip;
    }

    public void setTotalTrip(String totalTrip) {
        this.totalTrip = totalTrip;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public String getTripSpacing() {
        return tripSpacing;
    }

    public void setTripSpacing(String tripSpacing) {
        this.tripSpacing = tripSpacing;
    }

}
