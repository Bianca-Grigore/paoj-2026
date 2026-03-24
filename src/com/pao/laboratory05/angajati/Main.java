package com.pao.laboratory05.angajati;

import java.util.Scanner;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
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

                    AngajatService.getInstance().addAngajat(new Angajat(nume, dep, salariu));
                    break;

                case 2:
                    AngajatService.getInstance().listBySalary();
                    break;
                case 3:
                    System.out.println("Nume departament: ");
                    String numeDept = scanner.nextLine();
                    AngajatService.getInstance().findByDepartament(numeDept);
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