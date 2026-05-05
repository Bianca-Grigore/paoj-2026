package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        ArrayList<Tranzactie> tranzactii = new ArrayList<>(Arrays.asList(

                new Tranzactie(1, 5500.00, "2024-04-05", TipTranzactie.CREDIT, "RO12INGB0000111122223333"),
                new Tranzactie(2, 250.75,  "2024-04-08", TipTranzactie.DEBIT,  "RO98BTRL1234567890123456"),
                new Tranzactie(3, 100.00,  "2024-04-12", TipTranzactie.DEBIT,  "RO45BRDE9876543210987654"),
                new Tranzactie(4, 300.50,  "2024-04-18", TipTranzactie.CREDIT, "RO12INGB0000111122223333"),

                new Tranzactie(5, 80.00,   "2024-05-02", TipTranzactie.DEBIT,  "RO22RNCB1111222233334444"),
                new Tranzactie(6, 5500.00, "2024-05-05", TipTranzactie.CREDIT, "RO12INGB0000111122223333"),
                new Tranzactie(7, 1200.00, "2024-05-10", TipTranzactie.DEBIT,  "RO99TREZ0000000000000000"),
                new Tranzactie(8, 45.99,   "2024-05-22", TipTranzactie.DEBIT,  "RO98BTRL1234567890123456"),

                new Tranzactie(9, 5500.00, "2024-06-05", TipTranzactie.CREDIT, "RO12INGB0000111122223333"),
                new Tranzactie(10, 450.00, "2024-06-15", TipTranzactie.DEBIT,  "RO45BRDE9876543210987654")
        ));

        System.out.println("\t1) Lista tuturor tranzacțiilor CREDIT");
        List<Tranzactie> tranzactiiFiltrate = new ArrayList<>();
        tranzactiiFiltrate = tranzactii.stream().filter(tranzactie -> tranzactie.getTip() == TipTranzactie.CREDIT).toList();
        for(Tranzactie t : tranzactiiFiltrate) {
            System.out.println(t);
        }
       System.out.println("2) Total procesat: X.XX RON");
        System.out.println("Total procesat: " + tranzactii.stream().mapToDouble(Tranzactie::getSuma).sum() + " RON");

        System.out.println("3) Per luna: ");
        Map<String, Double> sumaPerLuna = tranzactii.stream().collect(
                Collectors.groupingBy( t -> t.getData().substring(0, 7),
                        TreeMap::new, Collectors.summingDouble(Tranzactie::getSuma)));

        sumaPerLuna.forEach((luna, suma) -> System.out.println("Per luna: " + luna + ": " + String.format(Locale.US, "%.2f", suma) + " RON"));

        System.out.println("4) Top 3 tranzactii: + 3 linii");
        List<Tranzactie> list = tranzactii.stream().sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed()).limit(3).toList();
        for(Tranzactie t : list) {
            System.out.println(t);
        }

        System.out.println("5) Conturi sursa unice: [CONT_A, CONT_B, ...]");
        System.out.println("Conturi sursa unice: " + tranzactii.stream().map(Tranzactie::getContSursa).distinct().toList());

        System.out.println("6) Suma medie: X.XX RON");
        OptionalDouble avg = tranzactii.stream().mapToDouble(Tranzactie::getSuma).average();
        if(avg.isPresent()){
            System.out.println("Suma medie: " + String.format(Locale.US, "%.2f", avg.getAsDouble()) + " RON");
        }
        else
            System.out.println("Suma medie: 0.00 RON");

        System.out.println("7) EXTRAS DE CONT - yyyy-MM: N tranzactii, total: X.XX RON per lună");
        Map<String, List<Tranzactie>> extraseDeCont = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.toList()
                ));

        extraseDeCont.forEach((luna, listaTranzactii) -> {

            int nrTranzactii = listaTranzactii.size();
            double sumaTotala = listaTranzactii.stream()
                    .mapToDouble(Tranzactie::getSuma)
                    .sum();

            System.out.println(String.format(Locale.US,
                    "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON",
                    luna, nrTranzactii, sumaTotala));
        });
    }
}
