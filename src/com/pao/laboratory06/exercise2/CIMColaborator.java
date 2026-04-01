package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends Colaborator implements PersoanaFizica {
    private boolean are_bonus = false;

    public CIMColaborator(String nume, String prenume, double venit_brut_lunar, boolean are_bonus) {
        super(nume, prenume, venit_brut_lunar);
        this.are_bonus = are_bonus;
    }

    public CIMColaborator() {
        super();
    }


    @Override
    public double calculeazaVenitNetAnual() {
        double venit_net_anual = venit_brut_lunar * 12 * 0.55;
        if(are_bonus){
            venit_net_anual += venit_net_anual * 0.10;
        }
        return venit_net_anual;
    }

    @Override
    public String toString(){
        return String.format(java.util.Locale.US, "CIM: %s %s, venit net anual: %.2f lei", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public void citeste(Scanner in) {
        super.citeste(in); // Citește Nume, Prenume, Salariu

        // Verificăm dacă mai există text pe linie pentru bonusul opțional
        if (in.hasNext()) {
            String bonusStr = in.next();
            this.are_bonus = bonusStr.equalsIgnoreCase("DA");
        } else {
            this.are_bonus = false; // Default NU conform cerinței
        }
    }

    @Override
    public String tipContract() {
        return TipColaborator.CIM.name();
    }

    @Override
    public boolean areBonus() {
        return are_bonus;
    }
}
