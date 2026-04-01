
package com.pao.laboratory06.exercise2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        in.useLocale(Locale.US);
        if (!in.hasNextInt()) return;

        int n = in.nextInt();
        in.nextLine();

        List<Colaborator> colaboratori = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String tip = in.next();
            Colaborator c = null;

            if (tip.equals(TipColaborator.CIM.name())) {
                c = new CIMColaborator();
            } else if (tip.equals(TipColaborator.PFA.name())) {
                c = new PFAColaborator();
            } else if (tip.equals(TipColaborator.SRL.name())) {
                c = new SRLColaborator();
            }

            if (c != null) {
                c.citeste(in);
                colaboratori.add(c);
            }
        }

        for (Colaborator c : colaboratori) {
            c.afiseaza();
        }

        System.out.println();

        colaboratori.sort(Comparator.comparingDouble(Colaborator::calculeazaVenitNetAnual).reversed());


        if (!colaboratori.isEmpty()) {
            System.out.print("Colaborator cu venit net maxim: ");
            colaboratori.get(0).afiseaza();
        }

        System.out.println("\nColaboratori persoane juridice:");

        for (Colaborator c : colaboratori) {
            if (c instanceof PersoanaJuridica) {
                c.afiseaza();
            }
        }

        System.out.println("\nSume și număr colaboratori pe tip:");

        for (TipColaborator tip : TipColaborator.values()) {

            double suma = colaboratori.stream()
                    .filter(c -> c.tipContract().equals(tip.name()))
                    .mapToDouble(Colaborator::calculeazaVenitNetAnual)
                    .sum();

            long count = colaboratori.stream()
                    .filter(c -> c.tipContract().equals(tip.name()))
                    .count();


            if (count != 0) {
                System.out.printf(Locale.US, "%s: suma = %.2f lei, număr = %d\n", tip.name(), suma, count);
            } else {
                System.out.printf(Locale.US, "%s: suma = nu lei, număr = null\n", tip.name());
            }
        }

        in.close();
    }
}


