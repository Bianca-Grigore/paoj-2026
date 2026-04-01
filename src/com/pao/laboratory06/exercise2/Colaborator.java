package com.pao.laboratory06.exercise2;

import java.util.Locale;
import java.util.Scanner;

public abstract class Colaborator implements IOperatiiCitireScriere {
    String nume;
    String prenume;
    double venit_brut_lunar;

    public Colaborator() {
    }
    public Colaborator(String nume, String prenume, double venit){
        this.nume = nume;
        this.prenume = prenume;
        this.venit_brut_lunar = venit;
    }

    @Override
    public void citeste(Scanner in) {
        // next() citește un singur cuvânt și sare peste spațiile albe
        this.nume = in.next();
        this.prenume = in.next();
        // nextDouble() citește valoarea numerică
        this.venit_brut_lunar = in.nextDouble();
    }

    @Override
    public void afiseaza(){
        System.out.printf(Locale.US, "%s: %s %s, venit net anual: %.2f lei\n",
                tipContract(), nume, prenume, calculeazaVenitNetAnual());
    }

    public abstract double calculeazaVenitNetAnual();


}
