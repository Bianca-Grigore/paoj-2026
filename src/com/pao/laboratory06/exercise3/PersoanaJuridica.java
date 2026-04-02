package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS{

    List<String> smsTrimise = new ArrayList<>();

    public PersoanaJuridica(String nume, String prenume) {
        super(nume, prenume);
    }

    public PersoanaJuridica(String nume, String prenume, String telefon) {
        super(nume, prenume, telefon);
    }

    public List<String> getSmsTrimise(){
        return smsTrimise;
    }

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (mesaj == null || mesaj.trim().isEmpty()) {
            return false;
        }

        if (getTelefon() != null && !getTelefon().trim().isEmpty()) {
            smsTrimise.add(mesaj);
            return true;
        }

        return false;
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
        return 0;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        return suma > 0;
    }

}