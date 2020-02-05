package com.integbridge;

import java.util.ArrayList;

import static com.integbridge.Enums.getWbTemplate;

public class FeinElement {

    private String uebergeordnetes_StepBauteil;
    private Integer id;
    private String bezeichnung;
    private Integer zyklus;
    private Integer menge;
    private String einheit;

    private Double [] wirkungsbilanz_kumuliert;
    private ArrayList<FeinElementPhase> feinElementPhasen;

    public FeinElement(ArrayList<FeinElementPhase> feinElementPhasen, String bezeichnung) {
        this.bezeichnung = bezeichnung;
        this.feinElementPhasen = feinElementPhasen;
        this.wirkungsbilanz_kumuliert = getWbTemplate().clone();

        for (FeinElementPhase fE : feinElementPhasen) {
            for(int i = 0; i< getWbTemplate().length; i++)
                wirkungsbilanz_kumuliert[i] += fE.getWirkungsbilanz()[i];
        }
    }

    public ArrayList<FeinElementPhase> getFeinElementPhasen() {
        return feinElementPhasen;
    }

    public Double[] getWirkungsbilanz_kumuliert() {
        return wirkungsbilanz_kumuliert;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }
}
