package com.pao.laboratory05.audit;

public class Angajat implements Comparable<Angajat>{
    String nume;
    Departament departament;
    double salariu;

    public Angajat(String nume, Departament departament, double salariu){
        this.nume = nume;
        this.departament = departament;
        this.salariu = salariu;
    }


    @Override
    public int compareTo(Angajat other){
        return this.nume.compareTo(other.nume);
    }

    public Double getSalariu(){
        return this.salariu;
    }

    public String getNume() {
        return this.nume;
    }

    public Departament getDepartament(){
        return this.departament;
    }
}

