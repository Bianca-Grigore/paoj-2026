package com.pao.laboratory07.exercise3;

import com.pao.laboratory07.exercise1.OrderState;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected double pret;
    protected OrderState state;
    protected String client;

    protected Comanda(String nume, double pret, String client)
    {
        this.nume = nume;
        this.pret = pret;
        this.client = client;
        this.state = OrderState.PLACED;
    }

    protected Comanda(String client) {
        this.client = client;
        this.state = OrderState.PLACED;
    }

    protected Comanda(String nume, String client) {
        this.nume = nume;
        this.client = client;
        this.state = OrderState.PLACED;
    }

    public abstract double pretFinal();
    public String descriere(){
        if(this instanceof ComandaStandard){
            return String.format("STANDARD: %s, pret: %.2f lei [%s] - client: %s", this.nume, this.pretFinal(), this.state, this.client);
        }
        else if(this instanceof ComandaRedusa c){
            return String.format("DISCOUNTED: %s, pret: %.2f lei (-%d%%) [%s] - client: %s", this.nume, this.pretFinal(), c.getDiscount(), this.state, this.client);
        }
        else if(this instanceof ComandaGratuita){
            return String.format("GIFT: %s, gratuit [%s] - client: %s", this.nume, this.state, this.client);
        }
        return "Nu exista clasa";
    }

    public String getClient(){
        return client;
    }
}

