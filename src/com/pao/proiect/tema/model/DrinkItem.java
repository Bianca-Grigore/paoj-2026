package com.pao.proiect.tema.model;

public non-sealed class DrinkItem extends MenuItem {
    private boolean containsAlcohol;
    private double alcoholPercentage;
    private int volumeMl;
    private boolean isHot;

    public DrinkItem(String name, double calories, double price, String description, int estimatedTime, boolean isVegan, boolean isVegetarian, boolean containsAlcohol, int volumeMl,  boolean isHot, double alcoholPercentage){
        super(name, calories, price, description, estimatedTime, isVegan, isVegetarian);
       this.containsAlcohol = containsAlcohol;
       this.volumeMl = volumeMl;
       this.isHot= isHot;
       this.alcoholPercentage = containsAlcohol ? alcoholPercentage : 0.0;
    }

    public DrinkItem(String name, double price, boolean isVegan, boolean isVegetarian, boolean containsAlcohol, int volumeMl,  boolean isHot, double alcoholPercentage ){
        super(name, price, isVegan, isVegetarian);
        this.containsAlcohol = containsAlcohol;
        this.volumeMl = volumeMl;
        this.isHot = isHot;
        this.alcoholPercentage = containsAlcohol ? alcoholPercentage : 0.0;
    }

    public boolean requiresId(){
        return containsAlcohol;
    }

    public double getAlcoholPercentage(){
        if(!this.containsAlcohol || this.alcoholPercentage <= 0){
            return 0.0;
        }
        return (this.volumeMl * this.alcoholPercentage) / 1000.0;
    }

    public boolean addIced(){
        if(this.isHot){
            this.isHot = false;
            this.name = "Iced " + this.name;
            return true;
        }
        return false;
    }

    public void upsize(int extra){
        if(extra > 0){
            double p = (double) (this.volumeMl + extra)/this.volumeMl;
            this.volumeMl += extra;
            this.calories += p;
            this.price *= (1+((p-1)*0.8));
            System.out.println("Drink upsized by " + extra + "ml. New volume: " + this.volumeMl + "ml, new calories: " + this.calories + ", new price: " + this.price);
        }
    }

    @Override
    public String toString(){
        return super.toString() + ", volume: " + volumeMl + ", alcohol percentage: " + alcoholPercentage + "%, hot drink: " + isHot;
    }
    public void setAlcoholPercentage(double alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public boolean isContainsAlcohol() {
        return containsAlcohol;
    }

    public void setContainsAlcohol(boolean containsAlcohol) {
        this.containsAlcohol = containsAlcohol;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        this.volumeMl = volumeMl;
    }
}
