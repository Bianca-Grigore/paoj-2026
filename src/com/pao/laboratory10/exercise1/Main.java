package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        Locale.US.setDefault(Locale.US);
        //   ENQUEUE id suma data tip   → addLast  (niciun output)
        //   DEQUEUE                    → removeFirst sau "Coada goala."
        //                                format: "Procesat: [id] data tip: suma RON"
        //   PUSH id suma data tip      → addFirst  (niciun output)
        //   POP                        → removeFirst sau "Coada goala."
        //                                format: "Extras: [id] data tip: suma RON"
        //   REMOVE_DEBIT               → Iterator.remove() pe toate DEBIT
        //                                afișează "Eliminat N tranzactii DEBIT."
        //   REMOVE_BELOW threshold     → Iterator.remove() pe suma < threshold
        //                                afișează "Eliminat N tranzactii sub threshold RON."
        //   PRINT                      → afișează toate, câte una pe linie
        //   SIZE                       → "Dimensiune coada: N"
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-10 CREDIT: 500.00 RON

        LinkedList<Tranzactie> linkedList = new LinkedList<Tranzactie>();
        String[] comanda;
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            comanda = scanner.nextLine().trim().split(" ");
            switch(comanda[0]){
                case "ENQUEUE" -> {
                    Tranzactie tranzactie = new Tranzactie(Integer.parseInt(comanda[1]), Double.parseDouble(comanda[2]), comanda[3], TipTranzactie.valueOf(comanda[4]));
                    linkedList.addLast(tranzactie);
                }
                case "DEQUEUE" -> {
                    if(linkedList.isEmpty()){
                        System.out.println("Coada goala.");
                    }
                    else{
                        System.out.println("Procesat: " + linkedList.getFirst().toString());
                        linkedList.removeFirst();
                    }
                }
                case "PUSH" -> {
                    linkedList.addFirst(new Tranzactie(Integer.parseInt(comanda[1]), Double.parseDouble(comanda[2]), comanda[3], TipTranzactie.valueOf(comanda[4])));
                }
                case "POP" -> {
                    if(linkedList.isEmpty()){
                        System.out.println("Coada goala.");
                    }
                    else{
                        System.out.println("Extras: " + linkedList.getFirst().toString());
                        linkedList.removeFirst();
                        }

                }

                case "REMOVE_DEBIT" -> {
                    int N=0;
                    Iterator<Tranzactie> itr = linkedList.iterator();
                    while (itr.hasNext()) {
                        Tranzactie t = itr.next();
                        if (t.getTip() == TipTranzactie.DEBIT) {
                            itr.remove();
                            N+=1;
                        }
                    }
                    System.out.println("Eliminat " + N + " tranzactii DEBIT.");

                }

                case "REMOVE_BELOW" -> {
                    double threshold = Double.parseDouble(comanda[1]);
                    int N=0;
                    Iterator<Tranzactie> itr = linkedList.iterator();
                    while (itr.hasNext()) {
                        Tranzactie t = itr.next();
                        if (t.getSuma() < threshold) {
                            itr.remove();
                            N+=1;
                        }
                    }
                    System.out.println("Eliminat " + N + " tranzactii sub " + String.format("%.2f", threshold) + " RON.");
                }
                case "PRINT" -> {
                    for(Tranzactie t : linkedList){
                        System.out.println(t.toString());
                    }
                }
                case "SIZE" -> {
                    System.out.println("Dimensiune coada: " + linkedList.size());
                }
            }
        }

    }
}
