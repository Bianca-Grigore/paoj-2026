package com.pao.laboratory10.exercise2;
import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON
        Scanner scanner = new Scanner(System.in);
        ArrayList<Tranzactie> tranzactii = new ArrayList<>();

        int N = Integer.parseInt(scanner.nextLine());
        String[] atribute;
        for(int i=0; i<N;i++){
            atribute=scanner.nextLine().trim().split(" ");
            tranzactii.add(new Tranzactie(Integer.parseInt(atribute[0]), Double.parseDouble(atribute[1]), atribute[2], TipTranzactie.valueOf(atribute[3])));
        }
        String[] comanda;
        while(scanner.hasNext()){
            comanda = scanner.nextLine().trim().split(" ");
            switch (comanda[0]){
                case "UNIQUE_IDS" -> {
                    LinkedHashSet<Integer> IDs = new LinkedHashSet<>();
                    for(Tranzactie t : tranzactii){
                        IDs.add(t.getId());
                    }
                    System.out.println("IDs unice (" + IDs.size() + "): " + IDs);
                }
                case "MONTHLY_REPORT" -> {
                    TreeMap<String, double[]> raportLunar = new TreeMap<>();
                    for (Tranzactie t : tranzactii){
                        String data = t.getData().substring(0,7);

                        raportLunar.putIfAbsent(data, new double[2]);

                        double[] sume = raportLunar.get(data);
                        if(t.getTip().equals(TipTranzactie.CREDIT)){
                            sume[0] += t.getSuma();
                        } else {
                            sume[1] += t.getSuma();

                        }
                    }
                    for(Map.Entry<String, double[]> entry : raportLunar.entrySet()){
                        String luna = entry.getKey();
                        double credit = entry.getValue()[0];
                        double debit = entry.getValue()[1];
                        System.out.println(String.format(java.util.Locale.US,
                                "%s: CREDIT %.2f RON, DEBIT %.2f RON",
                                luna, credit, debit));
                    }
                }

                case "TOP" -> {
                    int n= Integer.parseInt(comanda[1]);
                    ArrayList<Tranzactie> copyTranzactii = new ArrayList<>(tranzactii);

                    Collections.sort(copyTranzactii, Comparator.comparingDouble(Tranzactie::getSuma));
                    for(int i=0; i<n && i<copyTranzactii.size(); i++){
                        System.out.println(copyTranzactii.get(i));
                    }
                }

                case "SORT_ASC" -> {
                    Collections.sort(tranzactii, Comparator.comparingDouble(Tranzactie::getSuma));
                    for(Tranzactie t : tranzactii){
                        System.out.println(t);
                    }
                }

                case "SORT_DESC" -> {
                    Collections.sort(tranzactii, Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                    for(Tranzactie t : tranzactii){
                        System.out.println(t);
                    }
                }

                case "REVERSE" -> {
                    Collections.reverse(tranzactii);
                    for(Tranzactie t : tranzactii){
                        System.out.println(t);
                    }
                }

                case "MIN_MAX" -> {
                    System.out.println("MIN: " + Collections.min(tranzactii, Comparator.comparingDouble(Tranzactie::getSuma)));
                    System.out.println("MAX: " + Collections.max(tranzactii, Comparator.comparingDouble(Tranzactie::getSuma)));
                }

                case "CME_DEMO" -> {
                    try{
                        for(Tranzactie t : tranzactii)
                            tranzactii.remove(t);
                    }catch(ConcurrentModificationException e){
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                }
            }
        }
    }
}
