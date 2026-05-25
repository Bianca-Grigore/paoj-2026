package com.pao.proiect.tema.model;

public abstract sealed class User permits Customer, DeliveryPerson, RestaurantAdmin {
    protected int id;
    protected String name;
    protected String email;
    protected String phoneNum;
    protected String password;
    private static int idNumber =0 ;

    public User(String name, String email, String phoneNum, String password) {
        this.id = idNumber;
        idNumber +=1;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
    }

    public User() {

    }

    public void updateInfo(String newEmail, String newPhoneNum){
        this.email = newEmail;
        this.phoneNum=newPhoneNum;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + " " + id + ", name:  " + name + ", email: " + email + ", phone number: " + phoneNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public abstract String getRole();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }
}