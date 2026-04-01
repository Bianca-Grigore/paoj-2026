package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieli_lunare;
    private final double salariu_minim_brut = 48600.00 ;

    public PFAColaborator(String nume, String prenume, double venit_brut_lunar, double cheltuieli_lunare) {
        super(nume, prenume, venit_brut_lunar);
        this.cheltuieli_lunare= cheltuieli_lunare;
    }

    public PFAColaborator() {
    }

    @Override
    public void citeste(Scanner in) {
        super.citeste(in);
        this.cheltuieli_lunare = in.nextDouble();
    }

    @Override
    public String tipContract(){
        return TipColaborator.PFA.name();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venit_net = (venit_brut_lunar - cheltuieli_lunare) * 12;
        double impozit_venit = 0.10 * venit_net;
        double CASS = 0;
        double CAS = 0;

        if (venit_net < 6 * salariu_minim_brut) {
            CASS = 0.10 * (6 * salariu_minim_brut);
        } else if (venit_net >= 6 * salariu_minim_brut && venit_net <= 72 * salariu_minim_brut) {
            CASS = 0.10 * venit_net;
        } else if (venit_net > 72 * salariu_minim_brut) {
            CASS = 0.10 * (72 * salariu_minim_brut);
        }

        if (venit_net < 12 * salariu_minim_brut) {
            CAS = 0;
        } else if (venit_net >= 12 * salariu_minim_brut && venit_net <= 24 * salariu_minim_brut) {
            CAS = 0.25 * (12 * salariu_minim_brut);
        } else if (venit_net > 24 * salariu_minim_brut) {
            CAS = 0.25 * (24 * salariu_minim_brut);
        }

        return venit_net - impozit_venit - CASS - CAS;
    }

    @Override
    public String toString(){
        return String.format(java.util.Locale.US, "PFA: %s %s, venit net anual: %.2f lei", nume, prenume, calculeazaVenitNetAnual());
    }


}

