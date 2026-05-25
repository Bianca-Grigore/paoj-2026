package com.pao.proiect.tema.model;


public non-sealed class FoodItem extends MenuItem {
    private double weightGrams;
    private Spiciness spicinessLevel;
    private CourseType courseType;

    public FoodItem(String name, double calories, double price, String description, int estimatedTime, boolean isVegan, boolean isVegetarian, double weightGrams, Spiciness spicinessLevel, CourseType courseType){
        super(name, calories, price, description, estimatedTime, isVegan, isVegetarian);
        this.weightGrams = weightGrams;
        this.spicinessLevel = spicinessLevel;
        this.courseType = courseType;
    }

    public FoodItem(String name, double price, boolean isVegan, boolean isVegetarian, double weightGrams, Spiciness spicinessLevel, CourseType courseType){
        super(name, price, isVegan, isVegetarian);
        this.weightGrams = weightGrams;
        this.spicinessLevel = spicinessLevel;
        this.courseType = courseType;
    }

    public double getCaloriesPer100(){
        if(this.weightGrams <= 0){
            return 0;
        }
        return (this.calories/this.weightGrams) * 100;
    }

    @Override
    public String toString(){
        return super.toString() + ", grams: " + weightGrams + ", spiciness: " + spicinessLevel + ", course type: " + courseType;
    }


    public double getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(double weightGrams) {
        this.weightGrams = weightGrams;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public Spiciness getSpicinessLevel() {
        return spicinessLevel;
    }

    public void setSpicinessLevel(Spiciness spicinessLevel) {
        this.spicinessLevel = spicinessLevel;
    }
}