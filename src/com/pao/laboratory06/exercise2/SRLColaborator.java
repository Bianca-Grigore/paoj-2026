package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends Colaborator implements PersoanaJuridica {

    private double cheltuieliLunare;

    public SRLColaborator() {

    }

    @Override
    public void citeste(Scanner in){
        super.citeste(in);
        this.cheltuieliLunare = in.nextDouble();
        }

    @Override
    public String tipContract(){
        return TipColaborator.SRL.name();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        return (venitBrutLunar - cheltuieliLunare) * 12 * 0.84;
    }

    public SRLColaborator(String nume, String prenume, double venit_brut_lunar, double cheltuieliLunare){
        super(nume, prenume, venit_brut_lunar);
        this.cheltuieliLunare = cheltuieliLunare;
    }

    @Override
    public String toString(){
        return String.format(java.util.Locale.US, "SRL: %s %s, venit net anual: %.2f lei", nume, prenume, calculeazaVenitNetAnual());
    }
}
