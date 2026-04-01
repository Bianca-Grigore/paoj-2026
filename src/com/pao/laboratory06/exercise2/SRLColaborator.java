
package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends Colaborator implements PersoanaJuridica {

    private double cheltuieli_lunare;

    public SRLColaborator() {

    }

    @Override
    public void citeste(Scanner in){
        super.citeste(in);
        this.cheltuieli_lunare = in.nextDouble();
        }

    @Override
    public String tipContract(){
        return TipColaborator.SRL.name();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venit_net_anual = (venit_brut_lunar - cheltuieli_lunare) * 12 * 0.84;
        return venit_net_anual;
    }

    public SRLColaborator(String nume, String prenume, double venit_brut_lunar, double cheltuieli_lunare){
        super(nume, prenume, venit_brut_lunar);
        this.cheltuieli_lunare = cheltuieli_lunare;
    }

    @Override
    public String toString(){
        return String.format(java.util.Locale.US, "SRL: %s %s, venit net anual: %.2f lei", nume, prenume, calculeazaVenitNetAnual());
    }
}
