package com.pao.laboratory09.exercise3;

import java.util.Locale;

class ProcessorThread implements Runnable {
    private volatile boolean activ = true;
    private CoadaTranzactii coada;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    public void opreste() {
        this.activ = false;
    }

    @Override
    public void run() {
        try {
            while (activ) {
                Tranzactie t = coada.extrage();
                System.out.printf(Locale.US, "[Processor] Factura #%d - %.2f RON | %s%n", t.id, t.suma, t.data);
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            System.out.println("[Processor] s-a închis.");
            Thread.currentThread().interrupt();
        }
    }
}