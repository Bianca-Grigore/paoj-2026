package com.pao.laboratory07.exercise2;

import com.pao.laboratory07.exercise1.OrderState;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected double pret;
    protected OrderState state;
    protected Comanda(String nume, double pret)
    {
        this.nume = nume;
        this.pret = pret;
        this.state = OrderState.PLACED;
    }

    protected Comanda() {
        this.state = OrderState.PLACED;
    }

    public Comanda(String nume) {
        this.nume = nume;
        this.state = OrderState.PLACED;
    }

    public abstract double pretFinal();
    public String descriere(){
        if(this instanceof ComandaStandard){
            return String.format("STANDARD: %s, pret: %.2f lei [%s]", this.nume, this.pretFinal(), this.state);
        }
        else if(this instanceof ComandaRedusa c){
            return String.format("DISCOUNTED: %s, pret: %.2f lei (-%d%%) [%s]", this.nume, this.pretFinal(), c.getDiscount(), this.state);
        }
        else if(this instanceof ComandaGratuita){
            return String.format("GIFT: %s, gratuit [%s]", this.nume, this.state);
        }
        return "Nu exista clasa";
    }
}
