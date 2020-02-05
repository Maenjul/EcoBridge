package com.integbridge;

import java.util.ArrayList;

import static com.integbridge.Enums.*;

public class ErgebnisPopulation {
    SQLConnection sqlConnection;
    ArrayList<GrobElement> grobElementListe;

    int[] walzprofil_HL1000A_ids = {1110110010, 1110110020, 1110110030, 1110110040, 1110110050, 1110110060, 1110110070, 1110110080, 1110110090, 1110110011, 1110110012, 1110110013, 1110110014};
    int[] walzprofil_HEB1000_ids = {1110120010, 1110120020, 1110120030, 1110120040, 1110120050, 1110120060, 1110120070, 1110120080, 1110120090, 1110120011, 1110120012, 1110120013, 1110120014};
    int[] walzprofil_verzinkt_ids = {1110130010, 1110130020, 1110130030, 1110130040, 1110130050, 1110130060, 1110130070, 1110130080, 1110130090, 1110130011, 1110130012, 1110130013, 1110130014};
    int[] kopfbolzenduebel_ids = {1110101010, 1110101020, 1110101030, 1110101040, 1110101050, 1110101060, 1110101070, 1110101080, 1110101090, 1110101011, 1110101012, 1110101013, 1110101014};
    int[] korrosionsschutzsystem_ids = {1110100110, 1110100120, 1110100130, 1110101040, 1110100150, 1110100160, 1110100170, 1110100180, 1110100190, 1110100111, 1110100112, 1110100113, 1110100114};
    int[] beton_C30_37_ids = {1110220010, 1110220020, 1110220030, 1110220040, 1110220050, 1110220060, 1110220070, 1110220080, 1110220090, 1110220011, 1110220012, 1110220013, 1110220014};
    int[] bewehrungsstahl_ids = {1110201010, 1110201020, 1110201030, 1110201040, 1110201050, 1110201060, 1110201070, 1110201080, 1110201090, 1110201011, 1110201012, 1110201013, 1110201014};
    String [] beton_C25_30_ids = {"12104100010", "12104100020","12104100030","12104100040","12104100050","121041000160","12104100070","12104100080","12104100090","12104100011","12104100012","12104100013","12104100014"};
    String [] gelaender_ids = {"12104000110","12104000120","12104000130","12104000140","12104000150","12104000160","12104000170","12104000180","12104000190","12104000111","12104000112","12104000113","12104000114"};
    String [] abdichtung_ids = {"12104001010","12104001020","12104001030","12104001040","12104001050","12104001060","12104001070","12104001080","12104001090","12104001011","12104001012","12104001013","12104001014"};

    public ErgebnisPopulation(Bauteilliste bauteilliste, SQLConnection sqlConnection) {
        this.grobElementListe = new ArrayList<>();
        this.sqlConnection = sqlConnection;

        Double[] wb = getWbTemplate().clone();
        Double[] wb1 = getWbTemplate().clone();


        int stahltraeger = 0;
        int kappe = 0;
        int ortbetonplatte = 0;
        int fertigteilplatte = 0;
        ArrayList<GrobElementInstanz> stahltraegerInstanzliste = new ArrayList<>();
        ArrayList<GrobElementInstanz> kappenInstanzliste = new ArrayList<>();
        ArrayList<GrobElementInstanz> ortbetonInstanzliste = new ArrayList<>();
        ArrayList<GrobElementInstanz> fertigTeilInstanzliste = new ArrayList<>();

        for (Bauteil bauteil : bauteilliste.getBauteilliste()) {

            //Falls Bauteil Stahlträger ist
            if ((Integer.parseInt(Integer.toString(bauteil.getId()).substring(0, 5)) - 11101) == 0) {

                //Stahlprofil
                FeinElement stahlProfil = null;
                //Falls Stahlprofil feuerverzinkt ist
                if (bauteil.getKorrosionsschutz().equals(korrosionsschutzEnum[1])) {
                    ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, walzprofil_verzinkt_ids, "true", "true", bauteil.getMenge_gewicht(), 1);
                    stahlProfil = new FeinElement(phaseList, bauteil.getStahlprofil() + " feuerverzinkt");
                } else if (bauteil.getStahlprofil().equals(stahlprofilEnum[0])) {
                    ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, walzprofil_HL1000A_ids, "true", "true", bauteil.getMenge_gewicht(), 1);
                    stahlProfil = new FeinElement(phaseList, bauteil.getStahlprofil());
                } else if (bauteil.getStahlprofil().equals(stahlprofilEnum[1])) {
                    ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, walzprofil_HEB1000_ids, "true", "true", bauteil.getMenge_gewicht(), 1);
                    stahlProfil = new FeinElement(phaseList, bauteil.getStahlprofil());
                }

                //Korrosionsschutz
                FeinElement korrosionSchutz = null;
                ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, korrosionsschutzsystem_ids, bauteil.getKorrosionsschutz(), korrosionsschutzEnum[0], bauteil.getOberflaeche_Stahl(), 1);
                //check ob leer
                if (phaseList != null) {
                    if (!phaseList.isEmpty()) {
                        korrosionSchutz = new FeinElement(phaseList, korrosionsschutzEnum[0]);

                    }
                }

                //Kopfbolzendübel
                FeinElement kopfbolzenduebel = null;
                phaseList = erstelleFeinElementPhase(bauteil, kopfbolzenduebel_ids, "true", "true", bauteil.getGewicht_KopfBolzenduebel(), 1000);
                //check ob leer
                if (phaseList != null) {
                    if (!phaseList.isEmpty()) {
                        kopfbolzenduebel = new FeinElement(phaseList, "Kopfbolzendübel");
                    }
                }
                ArrayList<FeinElement> feinElementList = new ArrayList<>();
                feinElementList.add(0, stahlProfil);
                if (korrosionSchutz != null) {
                    feinElementList.add(1, korrosionSchutz);
                    feinElementList.add(2, kopfbolzenduebel);
                } else {
                    feinElementList.add(1, kopfbolzenduebel);
                }

                GrobElementInstanz stahltraegerInstanz = new GrobElementInstanz(feinElementList, bauteil.getStahlprofil(), bauteil.getId(), bauteil.getStepBauteil());
                stahltraegerInstanzliste.add(stahltraeger, stahltraegerInstanz);
                stahltraeger++;
            }

            //Falls Bauteil Ortbetonplatte ist
            if ((Integer.parseInt(Integer.toString(bauteil.getId()).substring(0, 5)) - 11103) == 0) {

                //FeinElement BetonGüte
                FeinElement betonguete = null;
                if (bauteil.getBetonguete().equals(betongueteEnum[2])) {
                    ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, beton_C30_37_ids, "true", "true", bauteil.getVolumen(), 1);
                    betonguete = new FeinElement(phaseList, bauteil.getBetonguete());
                } else if (bauteil.getBetonguete().equals(betongueteEnum[1])) {
                    ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, beton_C25_30_ids, "true", "true", bauteil.getVolumen(), 1);
                    betonguete = new FeinElement(phaseList, bauteil.getBetonguete());
                }
                //FeinElement Bewehrungsstahl
                FeinElement bewehrungsstahl = null;
                ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, bewehrungsstahl_ids, "true", "true", bauteil.getBewehrungsmenge(), 1000);
                bewehrungsstahl = new FeinElement(phaseList, "Bewehrung");

                ArrayList<FeinElement> feinElementList = new ArrayList<>();
                feinElementList.add(0, betonguete);
                feinElementList.add(1, bewehrungsstahl);

                GrobElementInstanz ortbetonInstanz = new GrobElementInstanz(feinElementList, "Ortbetonplatte", bauteil.getId(), bauteil.getStepBauteil());
                ortbetonInstanzliste.add(ortbetonplatte, ortbetonInstanz);
                ortbetonplatte++;
            }

            //Falls Bauteil Fertigteilplatte ist
            if ((Integer.parseInt(Integer.toString(bauteil.getId()).substring(0, 5)) - 11102) == 0) {

                //FeinElement BetonGüte
                FeinElement betonguete = null;
                if (bauteil.getBetonguete().equals(betongueteEnum[2])) {
                    ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, beton_C30_37_ids, "true", "true", bauteil.getVolumen(), 1);
                    betonguete = new FeinElement(phaseList, bauteil.getBetonguete());
                } else if (bauteil.getBetonguete().equals(betongueteEnum[1])) {
                    ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, beton_C25_30_ids, "true", "true", bauteil.getVolumen(), 1);
                    betonguete = new FeinElement(phaseList, bauteil.getBetonguete());
                }
                //FeinElement Bewehrungsstahl
                FeinElement bewehrungsstahl = null;
                ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, bewehrungsstahl_ids, "true", "true", bauteil.getBewehrungsmenge(), 1000);
                bewehrungsstahl = new FeinElement(phaseList, "Bewehrung");

                ArrayList<FeinElement> feinElementList = new ArrayList<>();
                feinElementList.add(0, betonguete);
                feinElementList.add(1, bewehrungsstahl);

                GrobElementInstanz fertigteilInstanz = new GrobElementInstanz(feinElementList, "Fertigteilplatte", bauteil.getId(), bauteil.getStepBauteil());
                fertigTeilInstanzliste.add(fertigteilplatte, fertigteilInstanz);
                fertigteilplatte++;
            }

            //Falls Bauteil Kappe ist
            if ((Integer.parseInt(Integer.toString(bauteil.getId()).substring(0, 5)) - 12104) == 0) {

                //FeinElement BetonGüte
                FeinElement betonguete = null;
                ArrayList<FeinElementPhase> phaseList = erstelleFeinElementPhase(bauteil, beton_C25_30_ids, "true", "true", bauteil.getVolumen(), 1);
                betonguete = new FeinElement(phaseList, "C20/25");


                //FeinElement Bewehrungsstahl
                FeinElement bewehrungsstahl = null;
                phaseList = erstelleFeinElementPhase(bauteil, bewehrungsstahl_ids, "true", "true", bauteil.getBewehrungsmenge(), 1000);
                bewehrungsstahl = new FeinElement(phaseList, "Bewehrung");

                ArrayList<FeinElement> feinElementList = new ArrayList<>();
                feinElementList.add(0, betonguete);
                feinElementList.add(1, bewehrungsstahl);

                GrobElementInstanz kappenInstanz = new GrobElementInstanz(feinElementList, "Brückenkappe", bauteil.getId(), bauteil.getStepBauteil());
                kappenInstanzliste.add(kappe, kappenInstanz);
                kappe++;
            }
        }

        //Die GrobElementInstanzen werden ihrem jeweiligen abstrakten GrobElement (Stahlträger, Ortbetonplatte, Fertigteilplatte, Kappe) zugeordnet
        grobElementListe.add(new GrobElement("Stahlträger", stahltraegerInstanzliste));
        grobElementListe.add(new GrobElement("Fertigteilplatten", fertigTeilInstanzliste));
        grobElementListe.add(new GrobElement("Ortbetonplatten", ortbetonInstanzliste));
        grobElementListe.add(new GrobElement("Brückenkappen", kappenInstanzliste));

        /*
        Double [] wb = {0.1,0.1,0.1,0.1,0.0,0.1,0.1};
        Double [] wb1 = {0.1,0.0,0.0,0.008,0.1,0.123,0.01};

        //Unterstee Ebene FeinElementInstanzen mit Lebenszyklusphase und Wirkungsbilanz werden hier erstellt
        FeinElementPhase stahlprofil_A1_A3 = new FeinElementPhase( phaseEnum[0],33, null, wb);
        FeinElementPhase stahlprofil_A45 = new FeinElementPhase( phaseEnum[1],33, null, wb);
        FeinElementPhase stahlprofil_B1 = new FeinElementPhase( phaseEnum[2],33, null, wb);
        FeinElementPhase stahlprofil_B2= new FeinElementPhase(phaseEnum[3],33, null, wb);
        FeinElementPhase stahlprofil_B3= new FeinElementPhase( phaseEnum[4],33, null, wb);
        FeinElementPhase stahlprofil_B4= new FeinElementPhase( phaseEnum[5],33, null, wb);
        FeinElementPhase stahlprofil_B5= new FeinElementPhase( phaseEnum[6],33, null, wb);
        FeinElementPhase stahlprofil_C= new FeinElementPhase(phaseEnum[7],33, null, wb1);
        FeinElementPhase stahlprofil_D= new FeinElementPhase(phaseEnum[8],33, null, wb1);

        FeinElementPhase schutzmittel_A1_A3 = new FeinElementPhase(phaseEnum[0],33, null, wb);
        FeinElementPhase schutzmittel_A45 = new FeinElementPhase( phaseEnum[1],33, null, wb1);
        FeinElementPhase schutzmittel_B1 = new FeinElementPhase( phaseEnum[2],33, null, wb1);
        FeinElementPhase schutzmittel_B2= new FeinElementPhase( phaseEnum[3],33, null, wb1);
        FeinElementPhase schutzmittel_B3= new FeinElementPhase(phaseEnum[4],33, null, wb1);
        FeinElementPhase schutzmittel_B4= new FeinElementPhase( phaseEnum[5],33, null, wb1);
        FeinElementPhase schutzmittel_B5= new FeinElementPhase( phaseEnum[6],33, null, wb1);
        FeinElementPhase schutzmittel_C= new FeinElementPhase( phaseEnum[7],33, null, wb1);
        FeinElementPhase schutzmittel_D= new FeinElementPhase( phaseEnum[8],33, null, wb);

        //Zusammengefasst in FeinElementeInstanzListen
        ArrayList<FeinElementPhase> feinElementPhaseListeStahlprofil = new ArrayList<>(9);
        feinElementPhaseListeStahlprofil.add (0, stahlprofil_A1_A3);
        feinElementPhaseListeStahlprofil.add(1,stahlprofil_A45);
        feinElementPhaseListeStahlprofil.add(2,stahlprofil_B1);
        feinElementPhaseListeStahlprofil.add(3,stahlprofil_B2);
        feinElementPhaseListeStahlprofil.add(4,stahlprofil_B3);
        feinElementPhaseListeStahlprofil.add(5,stahlprofil_B4);
        feinElementPhaseListeStahlprofil.add(6,stahlprofil_B5);
        feinElementPhaseListeStahlprofil.add(7,stahlprofil_C);
        feinElementPhaseListeStahlprofil.add(8,stahlprofil_D);

        ArrayList<FeinElementPhase> feinElementPhaseListeSchutzmittel = new ArrayList<>(9);
        feinElementPhaseListeSchutzmittel.add(0,schutzmittel_A1_A3);
        feinElementPhaseListeSchutzmittel.add(1,schutzmittel_A45);
        feinElementPhaseListeSchutzmittel.add(2,schutzmittel_B1);
        feinElementPhaseListeSchutzmittel.add(3,schutzmittel_B2);
        feinElementPhaseListeSchutzmittel.add(4,schutzmittel_B3);
        feinElementPhaseListeSchutzmittel.add(5,schutzmittel_B4);
        feinElementPhaseListeSchutzmittel.add(6,schutzmittel_B5);
        feinElementPhaseListeSchutzmittel.add(7,schutzmittel_C);
        feinElementPhaseListeSchutzmittel.add(8,schutzmittel_D);

        //FeinElemente werden nun erstellt, die in ihrer private List FeinElementInstanzListe alle ihm zugeordneten Feinelement-Instanzen beinhaltet
        FeinElement stahlprofil1 = new FeinElement(feinElementPhaseListeStahlprofil, "Stahlprofil");
        FeinElement schutzmittel = new FeinElement(feinElementPhaseListeSchutzmittel, "Schutzmittel");

        // FeinElemnte werden nun zu Liste zusammengefasst entsprechend ihrer Zusammengehörigket
        ArrayList<FeinElement> stahltraegerZugehoerigeFeinElemente = new ArrayList<>();
        stahltraegerZugehoerigeFeinElemente.add(0, stahlprofil1);
        stahltraegerZugehoerigeFeinElemente.add(1, schutzmittel);

        // Der jeweiligen GrobElement Instanz wird nun die FeinElementListe zugeordnet
        GrobElementInstanz stahltraegerInstanz1 = new GrobElementInstanz(stahltraegerZugehoerigeFeinElemente, "Stahlträger1", 1, "step435");
        GrobElementInstanz stahltraegerInstanz2 = new GrobElementInstanz(stahltraegerZugehoerigeFeinElemente, "Stahlträger2", 1, "step768");

        //Die GrobelementeInstanzen werden in einer Liste zusammengefasst
        ArrayList<GrobElementInstanz> grobElementInstanzListe = new ArrayList<>();
        grobElementInstanzListe.add(0, stahltraegerInstanz1);
        grobElementInstanzListe.add(1, stahltraegerInstanz2);

        //Die GrobElementInstanzen werden ihrem jeweiligen abstrakten GrobElement (Stahlträger, Ortbetonplatte, Fertigteilplatte, Kappe) zugeordnet
        ArrayList<GrobElement> grobElementListe = new ArrayList<>();
        grobElementListe.add(new GrobElement("Stahlträger", grobElementInstanzListe));
        grobElementListe.add(new GrobElement("Ortbetonplatte", grobElementInstanzListe));
         */
    }

    private ArrayList<FeinElementPhase> erstelleFeinElementPhase(Bauteil bauteil, int[] ids, String abgleichSubjekt, String abgleichObjektEnum, Double referenzFluss, double faktor) {
        ArrayList<FeinElementPhase> tempPhaseListe = null; //Erstellt Phase
        if (abgleichSubjekt.equals(abgleichObjektEnum)) { //Abgleich von Bauteil
            tempPhaseListe = new ArrayList<>();           //Initialisiere Liste (Alle Elemente erstmal null)

            for (int j = 0; j < 13; j++) {
                Double[] dbWb = sqlConnection.queryWbint(ids[j]);

                //Bezogen auf den Referenzfluss
                for (int i = 0; i < 9; i++) {
                    //Endgültige Wirkungsbilanz von FeinElement berechnen
                    try {
                        dbWb[i] *= referenzFluss * faktor; //Gewicht in Tonnen Referenzfluss in Tonnen
                    } catch (Exception e) {dbWb[i] = 0.0;}
                }
                FeinElementPhase tempPhase = new FeinElementPhase( phaseEnum[j], 33, null, dbWb);
                tempPhaseListe.add(j, tempPhase);


            }
        }
        return tempPhaseListe;
    }

    private ArrayList<FeinElementPhase> erstelleFeinElementPhase(Bauteil bauteil, String[] ids, String abgleichSubjekt, String abgleichObjektEnum, Double referenzFluss, double faktor) {
        ArrayList<FeinElementPhase> tempPhaseListe = null; //Erstellt Phase
        if (abgleichSubjekt.equals(abgleichObjektEnum)) { //Abgleich von Bauteil
            tempPhaseListe = new ArrayList<>();           //Initialisiere Liste (Alle Elemente erstmal null)

            for (int j = 0; j < 13; j++) {
                Double[] dbWb = sqlConnection.queryWbString(ids[j]);

                //Bezogen auf den Referenzfluss
                for (int i = 0; i < getWbTemplate().length; i++) {
                    //Endgültige Wirkungsbilanz von FeinElement berechnen
                    try {
                        dbWb[i] *= referenzFluss * faktor; //Gewicht in Tonnen Referenzfluss in Tonnen
                    } catch (Exception e) {dbWb[i] = 0.0;}
                }
                FeinElementPhase tempPhase = new FeinElementPhase( phaseEnum[j], 33, null, dbWb);
                tempPhaseListe.add(j, tempPhase);
            }
        }

        return tempPhaseListe;
    }

    public ArrayList<GrobElement> getGrobElementListe() {
        return grobElementListe;
    }
}


