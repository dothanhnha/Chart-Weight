package com.example.weightchart;

import java.util.Date;

public class WeightData {
    private double bmi;

    private Date collectedTime;

    private double fat;

    private int height;

    private int measurerCode;

    private double muscle;

    private double water;

    private double weight;

    public WeightData(double bmi, Date collectedTime, double fat, int height, int measurerCode, double muscle, double water, double weight) {
        this.bmi = bmi;
        this.collectedTime = collectedTime;
        this.fat = fat;
        this.height = height;
        this.measurerCode = measurerCode;
        this.muscle = muscle;
        this.water = water;
        this.weight = weight;
    }

    public WeightData(double bmi, Date collectedTime, double fat, double muscle, double water, double weight) {
        this.bmi = bmi;
        this.collectedTime = collectedTime;
        this.fat = fat;
        this.muscle = muscle;
        this.water = water;
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public Date getCollectedTime() {
        return collectedTime;
    }

    public void setCollectedTime(Date collectedTime) {
        this.collectedTime = collectedTime;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMeasurerCode() {
        return measurerCode;
    }

    public void setMeasurerCode(int measurerCode) {
        this.measurerCode = measurerCode;
    }

    public double getMuscle() {
        return muscle;
    }

    public void setMuscle(double muscle) {
        this.muscle = muscle;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
