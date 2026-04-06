package com.pao.laboratory07.exercise3;

public non-sealed class ComandaStandard extends Comanda{
    public ComandaStandard(String nume, double pret, String client){
        super(nume, pret, client);
    }

    @Override
    public double pretFinal() {
        return pret;
    }
}
