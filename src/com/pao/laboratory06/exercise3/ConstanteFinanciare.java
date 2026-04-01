package com.pao.laboratory06.exercise3;

public enum ConstanteFinanciare {

    TVA(0.19),
    SALARIU_MINIM(4050.00),
    COTA_IMPOZIT(0.10),
    COST_SMS_CONFIRMARE(0.05),
    COMISION_TRANZACTIE(1.5),
    LIMITA_ZILNICA_PF(10000.0),
    LIMITA_ZILNICA_PJ(50000.0);

    private final double VALOARE;

    ConstanteFinanciare(double valoare) {
        this.VALOARE = valoare;
    }

    public double getValoare(){
        return VALOARE;
    }
}
