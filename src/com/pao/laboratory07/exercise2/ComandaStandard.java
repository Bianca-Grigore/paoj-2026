package com.pao.laboratory07.exercise2;

public non-sealed class ComandaStandard extends Comanda{
    public ComandaStandard(String nume, double pret){
        super(nume, pret);
    }

    @Override
    public double pretFinal() {
        return pret;
    }
}
