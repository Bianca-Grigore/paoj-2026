package com.pao.laboratory07.exercise2;

public non-sealed class ComandaRedusa extends Comanda {
   private int discountProcent;

    public ComandaRedusa(String nume, double pret, int discountProcent) {
        super(nume, pret);
        this.discountProcent = discountProcent;
    }

    @Override
    public double pretFinal() {
        return pret * (1 - discountProcent / 100.0);
    }

    public int getDiscount(){
        return discountProcent;
    }
}
