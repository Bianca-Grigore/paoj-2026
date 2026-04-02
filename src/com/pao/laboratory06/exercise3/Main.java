package com.pao.laboratory06.exercise3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ArrayList<Inginer> ingineri = new ArrayList<Inginer>();

        ingineri.add(new Inginer("Popescu", "Ion", "0723456789", 5000.00));
        ingineri.add(new Inginer("Ionescu", "Maria", "0723456788", 8000.00));
        ingineri.add(new Inginer("Georgescu", "Andrei", "0723456787", 5500.00));
        ingineri.add(new Inginer("Vasilescu", "Elena", "0723456786", 6200.00));
        ingineri.add(new Inginer("Dumitrescu", "Cristian", "0723456785", 4800.00));

        System.out.println("====Creare + sortare ingineri====\n");

        System.out.println("Creare ingineri: \n");
        for (var inginer : ingineri){
            System.out.println(inginer);
        }

        System.out.println("\nSortare ingineri dupa nume: \n");

        Collections.sort(ingineri);
        for (var inginer : ingineri){
            System.out.println(inginer);
        }

        System.out.println("\n Sortare ingineri dupa salariu: \n");

        ingineri.sort(new ComparatorInginerSalariu());
        for (var inginer : ingineri){
            System.out.println(inginer);
        }

        System.out.println("\n====Accesul la un inginer prin PlataOnline====\n");

        PlataOnline referintaInginer = new Inginer("Marin", "Alexandru", "0723456784", 7000.00);

        System.out.println("Accesarea metodei autentificare buna: ");
        referintaInginer.autentificare("marinalex", "parola123");

        System.out.println("Accesarea metodei autentificare cu eroare: ");
        try {
            referintaInginer.autentificare("", "parola123");
        }catch (IllegalArgumentException e){
            System.out.println("\nEroare: " + e.getMessage());
        }

        System.out.println("\nAccesarea metodei consultareSold prin referinta PlataOnline: ");
        System.out.println(referintaInginer.consultareSold());
    try {
        referintaInginer.trimiteSMS("Salut, aceasta este o notificare pentru plata online.");
    }catch (UnsupportedOperationException e){
        System.out.println("\nEroare: " + e.getMessage());
    }
        boolean rez = referintaInginer.efectuarePlata(1500.00);
        if (rez)
            System.out.println("\nPlata a fost efectuata cu succes.");
        else
            System.out.println("\nPlata nu a fost efectuata.");

        System.out.println("\nMetoda consultare sold: ");
        System.out.println(referintaInginer.consultareSold());


        System.out.println("\n====Exemplificare inacesibilitate la metodele din Inginer: ====\n");
        //decomentarea liniilor de mai jos vor declansa erori pentru ca functiile nu apartin clasei PlataOnline, ci clasei Inginer, iar referinta este de tip PlataOnline
        //referintaInginer.getNume();
        //referintaInginer.getSalariu();


        System.out.println("\n====Afisare constante din enum====\n ");
        for (ConstanteFinanciare c : ConstanteFinanciare.values()) {
            System.out.println(c + " " + c.getValoare());
        }

        System.out.println("\n====Acces la PersoanaJuridica prin ref de tip PlataOnlineSMS====\n");
    }
}
