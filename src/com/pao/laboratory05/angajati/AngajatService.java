package com.pao.laboratory05.angajati;
import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati = new Angajat[0];
    private AngajatService() {
        this.angajati = new Angajat[0];
    }

        private static class Holder {
            private static final AngajatService INSTANCE = new AngajatService();
        }

        public static AngajatService getInstance(){
            return AngajatService.Holder.INSTANCE;
        }

        public void addAngajat(Angajat a){
            System.arraycopy(angajati, 0, angajati = new Angajat[angajati.length + 1], 0, angajati.length - 1);
            angajati[angajati.length - 1] = a;
            System.out.println("Angajat adaugat: " + a.getNume());
        }

        public void printAll(){
        for (Angajat a : angajati){
            System.out.println(a);
        }
        }
        public void listBySalary(){
            Angajat[] copy = angajati.clone();
            Arrays.sort(copy, (a1, a2) -> Double.compare(a2.getSalariu(), a1.getSalariu()));
            for (Angajat a : copy){
                System.out.println(a);
            }

        }

    public void findByDepartament(String numeDept){
        boolean gasit = false;
        for(Angajat angajat : angajati ){
            if (angajat.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println(angajat);
                gasit = true;
            }
        }
        if(!gasit)
            System.out.println("Niciun angajat in departamentul " + numeDept);

    }
}