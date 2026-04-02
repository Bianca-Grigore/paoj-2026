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

        //ref la PersoanaJuridica fara telefon adaugat ca sa exemplific mai tarziu cazul de false

        PlataOnlineSMS referintaPJ = new PersoanaJuridica("SC FIRMA SRL", "firma");

        System.out.println("\n====Metode accesare prin referintaPJ (cele din PlataOnlineSMS si PlataOnline)====\n");

        referintaPJ.autentificare("scfirma", "parolafirma");

        if(referintaPJ.trimiteSMS("Salut, aceasta este o notificare pentru plata online.")){
            System.out.println("\nSMS trimis\n");
        }        else
            System.out.println("\nSMS nu a fost trimis (nu exista numar de telefon)\n");

        if(referintaPJ.trimiteSMS(""))
            System.out.println("\nSMS trimis\n");
        else
            System.out.println("\nSMS nu a fost trimis (mesaj invalid)\n");

        System.out.println("Consultare sold " + referintaPJ.consultareSold());

        if(referintaPJ.efectuarePlata(2500.00))
            System.out.println("\nPlata a fost efectuata cu succes.\n");
        else
            System.out.println("\nPlata nu a fost efectuata.\n");

        System.out.println("\n==== Demonstrare stocare mesaje cu succes ====\n");

        PlataOnlineSMS referintaPjValida = new PersoanaJuridica("SC altafirma SRL", "alta", "0799888777");

        if(referintaPjValida.trimiteSMS("mesaj 1")) {
            System.out.println("SMS trimis cu succes");
        }
        if(referintaPjValida.trimiteSMS("mesaj 2")) {
            System.out.println("SMS trimis cu succes");
        }
        if(referintaPjValida.trimiteSMS("mesaj 3")) {
            System.out.println("SMS trimis cu succes");
        }

        if(referintaPjValida.trimiteSMS("")) {
            System.out.println("SMS trimis cu succes");
        }
        else
            System.out.println("Nu are mesaj si nu va aparea in lista");

        if (referintaPjValida instanceof PersoanaJuridica) {
            PersoanaJuridica downcast = (PersoanaJuridica) referintaPjValida;
            System.out.println("Mesajele stocate in lista firmei sunt:");
            System.out.println(downcast.getSmsTrimise());
        }
    }
}
