package com.pao.laboratory09.exercise3;

import java.util.Locale;

class ATMThread extends Thread {
    private int idAtm;
    private CoadaTranzactii coada;

    public ATMThread(int idAtm, CoadaTranzactii coada) {
        this.idAtm = idAtm;
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {

                int idTranzactie = (idAtm * 1000) + i;
                double suma = 10.0 + (Math.random() * 90.0);
                Tranzactie t = new Tranzactie(idTranzactie, suma, "30-04-2026");
                coada.adauga(t, "ATM-" + idAtm);
                System.out.printf(Locale.US, "[ATM-%d] trimite: Tranzactie #%d %.2f RON%n", idAtm, t.id, t.suma);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("[ATM-" + idAtm + "] a fost întrerupt.");
            Thread.currentThread().interrupt();
        }
    }
}
