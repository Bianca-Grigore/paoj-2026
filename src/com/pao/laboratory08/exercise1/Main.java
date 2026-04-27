package com.pao.laboratory08.exercise1;

import com.pao.test.IOTest;

import java.io.*;
import java.util.*;


public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează

        List<Student> studenti = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){
            String linie;
            while((linie = br.readLine()) != null){
                String[] date = linie.split(",");
                if(date.length == 4){
                    Student student = new Student(date[0], Integer.parseInt(date[1]), new Adresa(date[2], date[3]));
                    studenti.add(student);
                }
            }

        }catch(IOException e){
            System.out.println("Eroare la citirea fișierului: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String[] comanda = scanner.nextLine().split(" ");
        switch (comanda[0]){
            case "PRINT" ->{
                for(Student s : studenti){
                    System.out.println(s);
                }
            }
            case "SHALLOW" ->{
                String numeStudent = comanda[1];
                studenti.stream().filter(student -> student.getNume().equals(numeStudent)).findFirst().ifPresentOrElse(student ->
                {
                   try {
                       Student clona = (Student) student.clone();
                       clona.getAdresa().setOras("MODIFICAT");
                       System.out.println("Original: " + student);
                       System.out.println("Clona: " + clona);
                   }catch(CloneNotSupportedException e){
                       System.out.println("Eroare la clonare: " + e.getMessage());
                   }
                }, () -> System.out.println("Studentul nu a fost găsit."));
            }

            case "DEEP" -> {
                String numeStudent = comanda[1];
                studenti.stream().filter(student -> student.getNume().equals(numeStudent)).findFirst().ifPresentOrElse(student ->
                {
                    try {
                        Student clona = (Student) student.deepclone();
                        clona.getAdresa().setOras("MODIFICAT");

                        System.out.println("Original: " + student);
                        System.out.println("Clona: " + clona);

                    }catch(CloneNotSupportedException e){
                        System.out.println("Eroare la clonare: " + e.getMessage());
                    }
                }, () -> System.out.println("Studentul nu a fost găsit."));
            }

        }



}
}
