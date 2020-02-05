package com.integbridge;

import java.util.Map;

public class Enums {

    private static Double [] wbTemplate = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};

    public static final String [] kappenbetonEnum = {
            "C25/30"
    };

    public static final String[] betongueteEnum = {
            "C20/25",
            "C25/30",
            "C30/37",
            "C35/45",
            "C40/50",
            "C45/55",
            "C50/60",
            "C55/67",
    };
    public static final String[] korrosionsschutzEnum = {
            "organischer Korrosionsschutz C3",
            "Feuerverzinkung 300 µm",
    };
    public static final String[] richtzeichnungEnum = {
            "Kap 1 Blatt 1",
            "Kap 1_Blatt 3",
            "Kap 2_Blatt 1",
            "Kap 2_Blatt 3",
            "Kap 3_Blatt 1",
            "Kap 3_Blatt 3",
            "Kap 4",
            "Kap 6",
            "Kap 7",
            "Kap 8",
            "Kap 12",
            "Kap 20",
    };
    public static final String[] stahlgueteEnum = {
            "S275N",
            "S275NL",
            "S355N",
            "S355NL",
            "S420N",
            "S420NL",
            "S460N",
            "S460NL"
    };

    public static final String[] stahlprofilEnum = {
            "HL1000A",
            "HEB1000"
    };
    public static final Map<String, Double> profil_Flaeche = Map.of("HL1000A", 0.40885, "HEB1000", 0.400); // Fläche in m^2

    public static final Map<String, Double> profil_Umfang = Map.of("HL1000A", 3.1, "HEB1000", 2.81); //Umfang in m

    public static final String [] phaseEnum = {
            "A1-A3: Herstellung",
            "A4: Transport",
            "A5: Einbau",
            "B1: Nutzung",
            "B2: Wartung",
            "B3: Instandsetzung",
            "B4: Austausch",
            "B5: Verbesserung",
            "C1: Entsorgung",
            "C2: Transport",
            "C3: Abfallbehandlung",
            "C4: Deponierung: Abfallbehandlung",
            "D: Recycling"
    };

    public static final String [] wirkungsIndikatorenEnum = {
            "GWP",
            "ODP",
            "POCP",
            "AP",
            "EP",
            "ADPE",
            "ADPF"
    };

    public static Double [] getWbTemplate() {
        return wbTemplate;
    }


    /**
     * Checks if an Array is populated with data or not
     * @param arrayToCheck Double[] Array
     * @return Returns true if the Array has only 0.0 Values
     */
    public static boolean checkIfEmpty(Double[] arrayToCheck) {

        for (int i = 0; i< arrayToCheck.length; i++)
        {
            if (arrayToCheck[i] != 0.0)
            {
                return false;
            }
        }
        return true;
    }

}
