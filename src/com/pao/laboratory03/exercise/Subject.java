package com.pao.laboratory03.exercise;

public enum Subject {
    PAOJ("Programare avansata pe obiecte", 6),
    BD("Baze de date", 5),
    SO("Sisteme de operare", 5),
    RC("Retele de calculatoare", 4);

    String fullName;
    int credits;

    private Subject(String fullName, int credits) {
        this.fullName = fullName;
        this.credits = credits;
    }

    public String getFullName() {
        return fullName;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return name() + " (" + fullName + ", " + credits + " credire).";
    }
}
