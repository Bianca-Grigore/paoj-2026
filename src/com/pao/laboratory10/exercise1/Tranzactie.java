package com.pao.laboratory10.exercise1;

public class Tranzactie {
    private int id;
    private double suma;
    private String data;
    private TipTranzactie tip;

    public Tranzactie(int id, double suma, String data, TipTranzactie tip) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.tip = tip;
    }

    public String getData() {
        return data;
    }

    public int getId() {
        return id;
    }

    public double getSuma() {
        return suma;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + data + " " + tip + ": " + String.format(java.util.Locale.US, "%.2f", suma) + " RON";
    }
}