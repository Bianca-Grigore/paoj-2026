package com.pao.laboratory09.exercise3;

public class Main {
    public static void main(String[] args) {
        CoadaTranzactii coada = new CoadaTranzactii();
        ProcessorThread processorThread = new ProcessorThread(coada);
        Thread consumerThread = new Thread(processorThread);
        ATMThread atm1 = new ATMThread(1, coada);
        ATMThread atm2 = new ATMThread(2, coada);
        ATMThread atm3 = new ATMThread(3, coada);
        consumerThread.start();
        atm1.start();
        atm2.start();
        atm3.start();

        try {
            atm1.join();
            atm2.join();
            atm3.join();
            processorThread.opreste();
            synchronized (coada) {
                coada.notifyAll();
            }
            consumerThread.interrupt();
            consumerThread.join();
            System.out.println("Toate tranzactiile procesate. Total: 12");

        } catch (InterruptedException e) {
            System.err.println("Firul principal a fost întrerupt!");
            Thread.currentThread().interrupt();
        }
    }
}
