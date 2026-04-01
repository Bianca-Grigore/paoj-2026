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

    @Override
    public boolean trimiteSMS(String mesaj) {
        if(getTelefon() != null){
            smsTrimise.add(mesaj);
            return true;
        }
        return false;
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
}
