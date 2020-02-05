package com.integbridge;

import java.util.ArrayList;

import static com.integbridge.Enums.getWbTemplate;

public class GrobElementInstanz {

    private String stepBauteil;
    private Integer id;
    private String bezeichnung;
    //private Integer zyklus;
    private Integer lebensdauer = 100;
    //private String phase;
    private Double [] wirkungsbilanz_kumuliert;

    private Double [] wirkungsbilanz_A1_A3;
    private Double [] wirkungsbilanz_A4;
    private Double [] wirkungsbilanz_A5;
    private Double [] wirkungsbilanz_B1;
    private Double [] wirkungsbilanz_B2;
    private Double [] wirkungsbilanz_B3;
    private Double [] wirkungsbilanz_B4;
    private Double [] wirkungsbilanz_B5;
    private Double [] wirkungsbilanz_C1;
    private Double [] wirkungsbilanz_C2;
    private Double [] wirkungsbilanz_C3;
    private Double [] wirkungsbilanz_C4;
    private Double [] wirkungsbilanz_D;

    private ArrayList <FeinElement> zugehoerigeFeinElemente;

    /**Konstruktor erzeugt Grobelement und kumulierte Wirkungsbilanz aus Liste der zugehörigen Feinelemente.
     *
     * @param zugehoerigeFeinElemente ArrayList mit FeinElementPhase Objekten
     */
    public GrobElementInstanz(ArrayList<FeinElement> zugehoerigeFeinElemente, String bezeichnung, Integer id, String stepBauteil) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.stepBauteil = stepBauteil;

        this.zugehoerigeFeinElemente = zugehoerigeFeinElemente;
        this.wirkungsbilanz_A1_A3 = getWbTemplate().clone();
        this.wirkungsbilanz_A4 = getWbTemplate().clone();
        this.wirkungsbilanz_A5 = getWbTemplate().clone();
        this.wirkungsbilanz_B1 = getWbTemplate().clone();
        this.wirkungsbilanz_B2 = getWbTemplate().clone();
        this.wirkungsbilanz_B3 = getWbTemplate().clone();
        this.wirkungsbilanz_B4 = getWbTemplate().clone();
        this.wirkungsbilanz_B5 = getWbTemplate().clone();
        this.wirkungsbilanz_C1 = getWbTemplate().clone();
        this.wirkungsbilanz_C2 = getWbTemplate().clone();
        this.wirkungsbilanz_C3 = getWbTemplate().clone();
        this.wirkungsbilanz_C4 = getWbTemplate().clone();
        this.wirkungsbilanz_D = getWbTemplate().clone();

        this.wirkungsbilanz_kumuliert = getWbTemplate().clone();

        for (FeinElement fE : zugehoerigeFeinElemente)
        {
            for (int i = 0; i<getWbTemplate().length; i++) {
                try {
                    this.wirkungsbilanz_kumuliert[i] += fE.getWirkungsbilanz_kumuliert()[i];
                } catch (Exception e) {}

                try {
                    this.wirkungsbilanz_A1_A3[i] += fE.getFeinElementPhasen().get(0).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_A4[i] += fE.getFeinElementPhasen().get(1).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_A5[i] += fE.getFeinElementPhasen().get(2).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_B1[i] += fE.getFeinElementPhasen().get(3).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_B2[i] += fE.getFeinElementPhasen().get(4).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_B3[i] += fE.getFeinElementPhasen().get(5).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_B4[i] += fE.getFeinElementPhasen().get(6).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_B5[i] += fE.getFeinElementPhasen().get(7).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_C1[i] += fE.getFeinElementPhasen().get(8).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_C2[i] += fE.getFeinElementPhasen().get(9).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_C3[i] += fE.getFeinElementPhasen().get(10).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_C4[i] += fE.getFeinElementPhasen().get(11).getWirkungsbilanz()[i];
                    this.wirkungsbilanz_D[i] += fE.getFeinElementPhasen().get(12).getWirkungsbilanz()[i];
                } catch (Exception e) {};
            }
        }
    }


    public ArrayList<FeinElement> getZugehoerigeFeinElemente() {
        return zugehoerigeFeinElemente;
    }

    public String getStepBauteil() {
        return stepBauteil;
    }

    public Integer getId() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public Integer getLebensdauer() {
        return lebensdauer;
    }

    public Double[] getWirkungsbilanz_kumuliert() {
        return wirkungsbilanz_kumuliert;
    }
}
