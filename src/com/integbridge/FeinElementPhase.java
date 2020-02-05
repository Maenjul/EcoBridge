package com.integbridge;

import java.util.ArrayList;

import static com.integbridge.Enums.getWbTemplate;

public class FeinElementPhase {

    private String uebergeordnetes_StepBauteil;

    private String bezeichnung;
    private Integer zyklus;
    private String phase;
    private Integer menge;
    private String einheit;

    private Double [] wirkungsbilanz;


    public FeinElementPhase( String phase, Integer zyklus, ArrayList<FeinElementPhase> folgeElemente, Double[] wirkungsbilanz) {

        this.phase = phase;
        this.zyklus = zyklus;

        this.wirkungsbilanz = getWbTemplate().clone();
        for (int i=0; i<getWbTemplate().length ; i++) {
            this.wirkungsbilanz [i] += wirkungsbilanz[i];
        }
    }


    public String getUebergeordnetes_StepBauteil() {
        return uebergeordnetes_StepBauteil;
    }

    public void setUebergeordnetes_StepBauteil(String übergeordnetes_StepBauteil) {
        this.uebergeordnetes_StepBauteil = übergeordnetes_StepBauteil;
    }


    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Integer getZyklus() {
        return zyklus;
    }

    public void setZyklus(Integer zyklus) {
        this.zyklus = zyklus;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getMenge() {
        return menge;
    }

    public void setMenge(Integer menge) {
        this.menge = menge;
    }

    public String getEinheit() {
        return einheit;
    }

    public void setEinheit(String einheit) {
        this.einheit = einheit;
    }

    public Double[] getWirkungsbilanz() {
        return wirkungsbilanz;
    }

    public void setWirkungsbilanz(Double[] wirkungsbilanz) {
        this.wirkungsbilanz = wirkungsbilanz;
    }
}
