package com.example.assignment3;

public class Company {
    /*
        • Name & Logo image (Name should be creative, logo does not need to be original)
        • Address consisting of Street name, city, province, postal code (fake information allowed)
        • Stats of number of cars sold & total profit (calculated by adding prices of sold vehicles)

     */

    private String image;
    private String name;
    private String address;
    private int carsSold;
    private double totalProfit;

    public Company() {
    }

    public Company(String image, String name, String address, int carsSold, double totalProfit) {
        setImage(image);
        setName(name);
        setAddress(address);
        setCarsSold(carsSold);
        setTotalProfit(totalProfit);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCarsSold() {
        return carsSold;
    }

    public void setCarsSold(int carsSold) {
        this.carsSold = carsSold;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return "Company{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", carsSold=" + carsSold +
                ", totalProfit=" + totalProfit +
                '}';
    }

    protected String writeToFile(){
        return String.format("%s,%s,%s,%d,%.2f", this.image, this.name, this.address, this.carsSold, this.totalProfit);
    }

    public boolean getMatch(String text){
        text = text.toLowerCase();

        return name.toLowerCase().contains(text) || address.toLowerCase().contains(text);
    }
}
