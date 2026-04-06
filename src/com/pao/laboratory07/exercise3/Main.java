package com.pao.laboratory07.exercise3;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();
        int nrStandard = 0, nrDiscounted = 0, nrGift = 0;
        double sumaStandard = 0, sumaDiscounted = 0;

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine().trim();
            String[] lista = line.split(" ");
            if (lista[0].equals("STANDARD")) {
                String nume = lista[1];
                double pret = Double.parseDouble(lista[2]);
                String client = lista[3];
                Comanda c = new ComandaStandard(nume, pret, client);
                comenzi.add(c);
                nrStandard++;
                sumaStandard += c.pretFinal();
            }
            else
                if (lista[0].equals("DISCOUNTED")) {
                String nume = lista[1];
                double pret = Double.parseDouble(lista[2]);
                int discount = Integer.parseInt(lista[3]);
                String client = lista[4];
                Comanda c = new ComandaRedusa(nume, pret, discount, client);
                comenzi.add(c);
                nrDiscounted++;
                sumaDiscounted += c.pretFinal();
            }
                else
                if (lista[0].equals("GIFT")) {
                String nume = lista[1];
                String client = lista[2];
                Comanda c = new ComandaGratuita(nume, client);
                comenzi.add(c);
                nrGift++;
            }
        }
        for( Comanda c : comenzi){
            System.out.println(c.descriere());
        }

        while(true){
            String linie = scanner.nextLine().trim();
            if(linie.isEmpty()){
                continue;
            }
            String[] task = linie.split(" ");
            String c = task[0];
            switch (c){
                case "STATS":
                    Map<String, Double> mediiPreturiFinale = comenzi.stream().collect(Collectors.groupingBy(
                            a -> {
                                if (a instanceof ComandaStandard) {
                                    return "STANDARD";
                                } else if (a instanceof ComandaRedusa) {
                                    return "DISCOUNTED";
                                } else if (a instanceof ComandaGratuita) {
                                    return "GIFT";
                                }
                                return "UNKNOWN";
                            },
                            Collectors.averagingDouble(a->a.pretFinal())
                    ));
                    System.out.println("\n--- STATS ---");
                    mediiPreturiFinale.forEach((tip, medie) -> System.out.printf(Locale.US, " %s: medie %.2f lei\n", tip, medie));
                    break;

                case "FILTER":
                    List<Comanda> comenziFiltrate = comenzi.stream()
                            .filter(com -> com.pretFinal() >= Double.parseDouble(task[1])).toList();

                    System.out.println("\n---FILTER---");
                    for (Comanda comanda: comenziFiltrate){
                        System.out.println(comanda.descriere());

                    }
                    break;

                case "SORT": comenzi.sort(Comparator.comparing(Comanda::getClient).thenComparing(Comanda::pretFinal));
                    System.out.println("\n---SORT---");
                for( Comanda com : comenzi){
                        System.out.println(com.descriere());
                    }
                    break;

                case "SPECIAL":
                    List<Comanda> comenziSpeciale = comenzi.stream().filter(com -> com instanceof ComandaRedusa cr &&  cr.getDiscount() > 15).toList();
                    System.out.println("\n---SPECIAL---");
                    for (Comanda comanda: comenziSpeciale){
                        System.out.println(comanda.descriere());

                    }
                    break;

                case "QUIT":
                    break;
            }
        }
    }
}
