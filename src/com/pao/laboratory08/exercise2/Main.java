package com.pao.laboratory08.exercise2;

import java.io.*;
import java.util.*;
import com.pao.laboratory08.exercise1.Student;
import com.pao.laboratory08.exercise1.Adresa;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește pragul de vârstă din stdin cu Scanner
        // 3. Filtrează studenții cu varsta >= prag
        // 4. Scrie filtrații în "rezultate.txt" cu BufferedWriter
        // 5. Afișează sumarul la consolă

        //System.out.println("TODO: implementează exercițiul 2");

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

        System.out.println("Introduceți pragul de vârstă:");
        Scanner scanner = new Scanner(System.in);
        int varsta = Integer.parseInt(scanner.nextLine());

        List<Student> studentiFiltrati = studenti.stream().filter(student -> student.getVarsta() >= varsta).toList();
        try(BufferedWriter fout = new BufferedWriter(new FileWriter("rezultate.txt"))){
            for(Student s: studentiFiltrati){
                fout.write(s.toString());
                fout.newLine();
            }
        }catch(IOException e){
            System.out.println("Eroare la scriere " + e.getMessage());
        }

        System.out.println("Filtru varsta >= " + varsta);
        System.out.println();
        System.out.println("Rezultate: " + studentiFiltrati.size() + " studenti");
        for(Student s : studentiFiltrati){
            System.out.println(s);
        }
        System.out.println();
        System.out.println("Scris in rezultate.txt");
    }
}

