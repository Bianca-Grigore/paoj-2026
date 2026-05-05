package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;

public class Tranzactie {
    private int id;
    private double suma;
    private String data;
    private TipTranzactie tip;
    private String contSursa;

    public Tranzactie(int id, double suma, String data, TipTranzactie tip, String contSursa) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.tip = tip;
        this.contSursa = contSursa;
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

    public String getContSursa() {
        return contSursa;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + data + " " + tip + ": " + String.format(java.util.Locale.US, "%.2f", suma) + " RON, cont sursa: " + contSursa;
    }
}