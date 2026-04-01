package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieliLunare;
    private final double salariuMinimBrut = 48600.00 ;

    public PFAColaborator(String nume, String prenume, double venit_brut_lunar, double cheltuieliLunare) {
        super(nume, prenume, venit_brut_lunar);
        this.cheltuieliLunare = cheltuieliLunare;
    }

    public PFAColaborator() {
    }

    @Override
    public void citeste(Scanner in) {
        super.citeste(in);
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public String tipContract(){
        return TipColaborator.PFA.name();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;
        double impozitVenit = 0.10 * venitNet;
        double CASS = 0;
        double CAS = 0;

        if (venitNet < 6 * salariuMinimBrut) {
            CASS = 0.10 * (6 * salariuMinimBrut);
        } else if (venitNet >= 6 * salariuMinimBrut && venitNet <= 72 * salariuMinimBrut) {
            CASS = 0.10 * venitNet;
        } else if (venitNet > 72 * salariuMinimBrut) {
            CASS = 0.10 * (72 * salariuMinimBrut);
        }

        if (venitNet < 12 * salariuMinimBrut) {
            CAS = 0;
        } else if (venitNet >= 12 * salariuMinimBrut && venitNet <= 24 * salariuMinimBrut) {
            CAS = 0.25 * (12 * salariuMinimBrut);
        } else if (venitNet > 24 * salariuMinimBrut) {
            CAS = 0.25 * (24 * salariuMinimBrut);
        }

        return venitNet - impozitVenit - CASS - CAS;
    }

    @Override
    public String toString(){
        return String.format(java.util.Locale.US, "PFA: %s %s, venit net anual: %.2f lei", nume, prenume, calculeazaVenitNetAnual());
    }


}

