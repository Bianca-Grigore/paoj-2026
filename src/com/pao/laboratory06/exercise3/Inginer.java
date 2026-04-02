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
        if(user == null || user.trim().isEmpty() || parola == null || parola.trim().isEmpty()){
            throw new IllegalArgumentException("Campurile pentru user si parola nu trebuie sa fie null/goale.");
        }
        else
            System.out.println("Autentificare reusita pentru " + user);
    }

    @Override
    public double consultareSold() {
        return this.getSalariu();
    }

    @Override
    public boolean efectuarePlata(double suma) {
        return suma > 0;
    }

    @Override
    public int compareTo(Inginer other) {
        return this.nume.compareTo(other.nume);
    }

    @Override
    public String toString(){
        return "-" + getNume() + " " + getPrenume() + ", Telefon: " + (getTelefon() != null ? getTelefon() : "N/A") + ", Salariu: " + getSalariu();
    }
}