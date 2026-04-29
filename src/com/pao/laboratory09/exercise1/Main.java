package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);

        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1

        File fisier = new File(OUTPUT_FILE);
        fisier.getParentFile().mkdirs();

        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        List<Tranzactie> tranzactii = new ArrayList<>();
        for (int i = 0; i < n; i++) {

            String[] date = scanner.nextLine().trim().split(" ");

            int id = Integer.parseInt(date[0]);
            double suma = Double.parseDouble(date[1]);
            String data = date[2];
            String contSursa = date[3];
            String contDestinatie = date[4];

            TipTranzactie tip = TipTranzactie.valueOf(date[5].toUpperCase());

            tranzactii.add(new Tranzactie(id, suma, data, contSursa, contDestinatie, tip));
        }

        for (Tranzactie t : tranzactii) {
            t.setNote("procesat");
        }

// 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(tranzactii);
            //System.out.println("Tranzacțiile au fost serializate în " + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("Eroare la serializare: " + e.getMessage());
        }

        List<Tranzactie> tranzactiiDeserialized = new ArrayList<>();

// 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            tranzactiiDeserialized = (List<Tranzactie>) ois.readObject();
            //System.out.println("Tranzacțiile au fost deserializate din " + OUTPUT_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Eroare la deserializare: " + e.getMessage());
        }

// 5. Procesează comenzile din stdin până la EOF:

        while (scanner.hasNextLine()) {
            String[] comanda = scanner.nextLine().trim().split(" ");

            switch (comanda[0]) {
                case "LIST" -> {
                    for (Tranzactie t : tranzactiiDeserialized) {
                        System.out.println(t);
                    }
                }

                case "FILTER" -> {
                    String prefix = comanda[1];
                    boolean gasit = false;
                    for (Tranzactie t : tranzactiiDeserialized) {
                        if (t.getData().startsWith(prefix)) {
                            System.out.println(t);
                            gasit = true;
                        }
                    }
                    if (!gasit)
                        System.out.println("Niciun rezultat.");
                }

                case "NOTE" -> {
                    int id = Integer.parseInt(comanda[1]);
                    tranzactiiDeserialized.stream().filter(t -> t.getId() == id).findFirst().ifPresentOrElse(
                            t -> System.out.println("NOTE[" + id + "]: " + t.getNote()),
                            () -> System.out.println("NOTE[" + id + "]: not found")
                    );

                }

            }
        }
    }
}
