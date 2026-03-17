package com.pao.laboratory03.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentService {
    private static StudentService instance;
    private final List<Student> students = new ArrayList<>();

    private StudentService(){}
    public static StudentService getInstance(){
        if(instance == null){
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age){
        for(Student s: students){
            if(s.getName().equalsIgnoreCase(name)){
                throw new RuntimeException("Studentul '" + name + "' exista deja.");
            }
        }
        students.add(new Student(name, age));
    }
    public Student findByName(String name){
        for(Student s : students){
            if(s.getName().equalsIgnoreCase(name)){
                return s;
            }
        }
        throw new StudentNotFoundException("Studentul nu exista.");
    }

    public void addGrade(String studentName, Subject subject, double grade){
        Student student = findByName(studentName);
        student.addGrade(subject, grade);
    }

    public void printAllStudents(){
        if(students.isEmpty()){
            System.out.println("Nu exista studenti.");
            return;
        }
        int cnt =1;
        for( Student s : students){
            System.out.println(cnt + ". " + s + " ");
            for(Map.Entry<Subject, Double> entry : s.getGrades().entrySet()){
                System.out.println(entry.getKey().name() + " = " + entry.getValue());
            }
            cnt++;
        }
    }

    public void printTopStudents(){
        List<Student> lista_sortata = new ArrayList<>(students);
        lista_sortata.sort((a,b) ->Double.compare(b.getAverage(), a.getAverage()));
        int i=1;
        for(Student s : lista_sortata){
            System.out.printf("%d. %s media: %.2f%n", i, s.getName(), s.getAverage());
            i++;
        }
    }

    Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> rez = new HashMap<>();
        for (Subject s : Subject.values()) {
            double suma = 0;
            int cnt =0;
            for(Student stud : students ){
                Double nota = stud.getGrades().get(s);
                if(nota != null ) {
                    cnt += 1;
                    suma+=nota;
                }
            }
            if(cnt > 0){
                rez.put(s, suma/cnt);
            }
        }
        return rez;
    }
    }

