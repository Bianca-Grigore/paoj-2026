package com.pao.proiect.tema.model;

public final class DeliveryPerson extends User {
    private VehicleType vehicleType;
    private boolean isAvailable;
    private double rating;
    private int totalDeliveries;

    public DeliveryPerson(String name, String email, String phoneNum, String password, VehicleType vehicleType, boolean available) {
        super(name, email, phoneNum, password);
        this.vehicleType = vehicleType;
        this.isAvailable = available;
        this.rating = 0.0;
        this.totalDeliveries = 0;
    }

    public DeliveryPerson() {

    }

    public void incrementDeliveries(){
        this.totalDeliveries += 1;
    }

    public void setRating(double number){
        if(this.totalDeliveries == 0){
            this.rating = number;
        }
        else{
            this.rating = ((this.rating * this.totalDeliveries) + number) / (this.totalDeliveries + 1);
        }
    }

    public void setAvailable(boolean a){
        this.isAvailable = a;
    }

    public void setVehicleType(VehicleType v){
        this.vehicleType = v;
    }

    public double getRating() {
        return rating;
    }

    public int getTotalDeliveries() {
        return totalDeliveries;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String getRole() {
        return "Delivery";
    }

    @Override
    public String toString(){
        return super.toString() + " vehicle type: " + vehicleType + ", rating: " + rating + ", total deliveries: " + totalDeliveries;
    }
    public void setTotalDeliveries(int totalDeliveries) {
        this.totalDeliveries = totalDeliveries;
    }
}