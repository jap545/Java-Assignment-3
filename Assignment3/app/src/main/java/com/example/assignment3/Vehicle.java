package com.example.assignment3;

import java.util.Date;

public class Vehicle {
    /*
        • Make
        • Model
        • Condition
        • Engine Cylinders (v4, v6, etc)
        • Year
        • Number of Doors (2, 4, etc)
        • Price
        • Color
        • Image (thumbnail and fill-sized images)
        • Date Sold (can be blank)
     */

    private String image;
    private String make;
    private String model;
    private String condition;
    private int engineCylinders;
    private int year;
    private int numberOfDoors;
    private double price;
    private String color;
    private String dateSold;

    public Vehicle() {
    }

    public Vehicle(String image, String make, String model, String condition, int engineCylinders, int year, int numberOfDoors, double price, String color, Date dateSold) {
        setImage(image);
        setMake(make);
        setModel(model);
        setCondition(condition);
        setEngineCylinders(engineCylinders);
        setYear(year);
        setNumberOfDoors(numberOfDoors);
        setPrice(price);
        setColor(color);
        setDateSold(String.valueOf(dateSold));
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getEngineCylinders() {
        return engineCylinders;
    }

    public void setEngineCylinders(int engineCylinders) {
        this.engineCylinders = engineCylinders;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDateSold() {
        return dateSold;
    }

    public void setDateSold(String dateSold) {
        this.dateSold = dateSold;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "image='" + image + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", condition='" + condition + '\'' +
                ", engineCylinders=" + engineCylinders +
                ", year=" + year +
                ", numberOfDoors=" + numberOfDoors +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", dateSold=" + dateSold +
                '}';
    }

    protected String writeToFile(){
        return String.format("%s,%s,%s,%s,%d,%d,%d,%.2f,%s,%s", this.image, this.make, this.model, this.condition, this.engineCylinders, this.year, this.numberOfDoors,this.price, this.color,this.dateSold);
    }

    public boolean getMatch(String text) {
        text = text.toLowerCase();
        return make.toLowerCase().contains(text) || model.toLowerCase().contains(text) || condition.toLowerCase().contains(text) || color.toLowerCase().contains(text) || dateSold.toLowerCase().contains(text);
    }
}
