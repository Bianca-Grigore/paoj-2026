package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer>{

    public Inginer(String nume, String prenume, double salariu) {
        super(nume, prenume, salariu);
    }

    public Inginer(String nume, String prenume, String telefon, double salariu) {
        super(nume, prenume, telefon, salariu);
    }

    @Override
    public void autentificare(String user, String parola) {

    }

    @Override
    public double consultareSold() {
        return 0;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        return false;
    }

    @Override
    public int compareTo(Inginer other) {
        return this.nume.compareTo(other.nume);
    }
}
