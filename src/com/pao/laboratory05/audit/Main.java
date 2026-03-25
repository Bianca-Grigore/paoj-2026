package com.pao.laboratory05.audit;


import java.util.Scanner;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit"
 *
 * Extinde soluția de la Exercise 3 cu un sistem de audit bazat pe record.
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService angajatService = AngajatService.getInstance();
        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("4. Afiseaza audit log");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");
            int optiune = scanner.nextInt();
            scanner.nextLine();
            switch (optiune) {
                case 1:
                    System.out.print("Nume: ");
                    String nume = scanner.nextLine();

                    System.out.print("Salariu: ");
                    double salariu = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("Nume departament: ");
                    String nume_departament = scanner.nextLine();
                    System.out.println("Locatie departament: ");
                    String locatie_dep = scanner.nextLine();
                    Departament dep = new Departament(nume_departament, locatie_dep);

                    angajatService.addAngajat(new Angajat(nume, dep, salariu));
                    break;

                case 2:
                    angajatService.listBySalary();
                    break;
                case 3:
                    System.out.println("Nume departament: ");
                    String numeDept = scanner.nextLine();
                    angajatService.findByDepartament(numeDept);
                    break;

                case 4:
                    System.out.println("Afisare audit log: ");
                    angajatService.printAuditLog();
                    break;

                case 0:
                    System.out.println("La revedere!");
                    return;
                default:
                    System.out.println("Opțiune invalidă. Încearcă din nou.");
            }
        }

    }
}
