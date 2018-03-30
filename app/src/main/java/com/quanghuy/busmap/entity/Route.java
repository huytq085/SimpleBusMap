package com.quanghuy.busmap.entity;

import java.io.Serializable;

/**
 * Created by Huy on 3/22/2018.
 */

public class Route implements Serializable {
    private int code;
    private String name;
    private String routeAB;
    private String routeBA;
    private String agencies;
    private String type;
    private String distance;
    private String vehicleType;
    private String time;
    private int basicPrice;
    private int studentPrice;
    private int monthlyPrice;
    private String totalTrips;
    private String tripTime;
    private String tripSpacing;

    public Route() {
    }

    public Route(int code, String name, String routeAB, String routeBA, String agencies, String type, String distance, String vehicleType, String time, int basicPrice, int studentPrice, int monthlyPrice, String totalTrips, String tripTime, String tripSpacing) {
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
        this.totalTrips = totalTrips;
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

    public int getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(int basicPrice) {
        this.basicPrice = basicPrice;
    }

    public int getStudentPrice() {
        return studentPrice;
    }

    public void setStudentPrice(int studentPrice) {
        this.studentPrice = studentPrice;
    }

    public int getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(int monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(String totalTrips) {
        this.totalTrips = totalTrips;
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
