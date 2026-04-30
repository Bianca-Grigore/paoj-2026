package com.pao.laboratory09.exercise3;

import java.util.LinkedList;
import java.util.Queue;

class CoadaTranzactii {
    private final int capacitateMaxima = 5;
    private final Queue<Tranzactie> banda = new LinkedList<>();

    public synchronized void adauga(Tranzactie t, String atmNume) throws InterruptedException {

        while (banda.size() == capacitateMaxima) {
            System.out.println("[" + atmNume + "] astept loc...");
            wait();
        }
        banda.add(t);
        notifyAll();
    }

    public synchronized Tranzactie extrage() throws InterruptedException {
        while (banda.isEmpty()) {
            wait();
        }
        Tranzactie t = banda.poll();
        notifyAll();

        return t;
    }
}
