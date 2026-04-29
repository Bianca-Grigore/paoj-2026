package com.pao.laboratory09.exercise1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Tranzactie implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private double suma;
    private String data;
    private String contSursa;
    private String contDestinatie;
    private TipTranzactie tip;
    private transient String note;

    public Tranzactie(int id, double suma, String data, String contSursa, String contDestinatie, TipTranzactie tip) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.contSursa = contSursa;
        this.contDestinatie = contDestinatie;
        this.tip = tip;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString(){
        return "[" + id + "] " + data + " " + tip + ": " + String.format("%.2f", suma) + " RON | " + contSursa + " -> " + contDestinatie;
    }

    public String getData() {
        return data;
    }

    public int getId() {
        return id;
    }
}
