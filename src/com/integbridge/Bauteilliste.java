package com.integbridge;

import com.apstex.ifc2x3toolbox.ifc2x3.IfcPropertySingleValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.integbridge.Enums.profil_Flaeche;
import static com.integbridge.Enums.profil_Umfang;

public class Bauteilliste {
    private ArrayList<Bauteil> bauteilliste = new ArrayList<>();
    private String[] mapping_array =
            {
                    "Step-Bauteil",  //[0]
                    "Familie",          //[1]
                    "Typ",              //[2]
                    "Höhe",             //[3]
                    "Identifikation",   //[4]
                    "Länge",            //[5)
                    "halbe Breite",     //[6]
                    "Breite",           //[7]
                    "Bewehrungsgehalt", //[8]
                    "Volumen",          //[9]
                    "Kategorie",        //[10]
                    "Höhe oben",        //[11]
                    "Höhe unten",       //[12]
                    "Korrosionsschutz", //[13]
                    "Stahlgüte",        //[14)
                    "Betongüte",        //[15]
                    "Kopfbolzendübel pro Meter",    //[16]
                    "Stahlprofil",                  //[17]
                    "Richtzeichnung",               //[18]
                    "Kappenüberstand",              //[19]
                    "Kappenhöhe Straße",            //[20]
                    "Kappenneigung Oberseite",      //[21]
                    "Abdichtung",       //[22]
                    "Kappenlänge",      //[23]
                    "Kappenbreite",     //[24]
                    "Tiefe",            //[25]
                    "Fläche" };         //[26]


    /**Die Attribute_Value Paare (gespeichert in einem IfcSinglePropertyValue, werden auf die Instanzvariablen der Bauteilklasse gemapped
     * um später weiterverarbeitet zu werden.
     *
     * @param ifcObject_IfcPropertySingleValue_Map
     */
    public Bauteilliste(HashMap<String, HashSet<IfcPropertySingleValue>> ifcObject_IfcPropertySingleValue_Map) {
        int bauteilNumber = 0;
        for (String key : ifcObject_IfcPropertySingleValue_Map.keySet()) {
            for (IfcPropertySingleValue attr_value_pair : ifcObject_IfcPropertySingleValue_Map.get(key)) {
                String attr = attr_value_pair.getName().toString();

                if (contains(attr_value_pair, "Identifikation")) // Überprüft ob Bauteil vorliegt, ist indem die Existenz einer Identifikationsnummer überprüft wird.
                {
                    Bauteil temp = new Bauteil();

                    //[0]
                    temp.setStepBauteil(key);
                    //[1]
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[1]))
                        {
                            temp.setFamilie_grobelement(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    //[2]
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[2]))
                        {
                            temp.setTyp(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    //[3]
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[3]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setHoehe(foo);
                        }
                    }
                    //[4]
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[4]))
                        {
                            int foo = Integer.parseInt(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setId(foo);
                        }
                    }
                    //[5]
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[5]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setLaenge(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[6]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setHalbe_Breite(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[7]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setBreite(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[8]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setBewehrungsgehalt(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[9]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setVolumen(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[10]))
                        {
                            temp.setKategorie(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[11]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setHoehe_oben(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[12]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setHoehe_unten(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[13]))
                        {
                            temp.setKorrosionsschutz(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[14]))
                        {
                            temp.setStahlguete(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[15]))
                        {
                            temp.setBetonguete(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[16]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setKopfbolzenduebel_pro_meter(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[17]))
                        {
                            temp.setStahlprofil(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[18]))
                        {
                            temp.setRichtzeichnung(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[19]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setKappenueberstand(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[20]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setKappenhoehe_straße(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[21]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setKappenneigung_oberseite(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[22]))
                        {
                            temp.setAbdichtung(ifcPropertySingleValue.getNominalValue().toString());
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[23]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setKappenlaenge(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[24]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setKappenbreite(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[25]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setKappentiefe(foo);
                        }
                    }
                    for (IfcPropertySingleValue ifcPropertySingleValue : ifcObject_IfcPropertySingleValue_Map.get(key))
                    {
                        if (contains(ifcPropertySingleValue, mapping_array[26]))
                        {
                            double foo = Double.parseDouble(ifcPropertySingleValue.getNominalValue().toString());
                            temp.setKappenflaeche(foo);
                        }
                    }

                    //interne Berechnung von Mengen mittels gegebener Informationen
                    try {if ((Integer.parseInt(Integer.toString(temp.getId()).substring(0, 5)) - 11101) == 0) {
                        Double y = (((temp.getLaenge() * temp.getKopfbolzenduebel_pro_meter()) * 0.0005));
                        y = Double.parseDouble(Double.toString(y).substring(0, 6));
                        temp.setGewicht_KopfBolzenduebel(y);
                        temp.setOberflaeche_Stahl(round((temp.getLaenge() * profil_Umfang.get(temp.getStahlprofil())), 2));
                        temp.setMenge_gewicht(profil_Flaeche.get(temp.getStahlprofil()) * temp.getLaenge() * 7.85);
                    }
                    else if ((Integer.parseInt(Integer.toString(temp.getId()).substring(0, 5)) - 11102) == 0) {
                        temp.setBewehrungsmenge(round(temp.getBewehrungsgehalt() / 100 * temp.getVolumen() * 7.850, 2));
                    }else if ((Integer.parseInt(Integer.toString(temp.getId()).substring(0, 5)) - 11103) == 0) {
                        temp.setBewehrungsmenge(round(temp.getBewehrungsgehalt() / 100 * temp.getVolumen() * 7.850, 2));
                    }else if ((Integer.parseInt(Integer.toString(temp.getId()).substring(0, 5)) - 12104) == 0) {
                        temp.setBewehrungsmenge(round(temp.getBewehrungsgehalt() / 100 * temp.getVolumen() * 7.850, 2));
                    }} catch (Exception e) {}

                    bauteilliste.add(bauteilNumber, temp);
                    bauteilNumber++;
                }
            }
        }
    }

    /**Checks if the IfcPropertysinglevalue Instance with the name "pname" contains a value
     *
     * @param ifcp IfcPropertySingleValue Instance
     * @param pname Name of the Property
     * @return
     */
    private boolean contains(IfcPropertySingleValue ifcp, String pname)
    {
        if (ifcp.getName().toString().equals(pname)  && !ifcp.getNominalValue().toString().isBlank())
        {
            return true;
        }
        else{
            return false;
        }
    }
    public int getNumberOfDistinctCategories()
    {
        int count =0;
        List <String> distinctCategoryNames = new ArrayList<>();
        for (Bauteil b : bauteilliste) {
            //Checks if the dynamic arrayList distinctCategoryNames contains the category of the current Bauteil
            if (!distinctCategoryNames.contains(b.getKategorie())){
                distinctCategoryNames.add(count, b.getKategorie());
                count++;
            }
        }
        return count;
    }

    /**Gibt eine Arraylist zurück mit den Namen der vorkommenden Kategorien der Bauteile
     *
     * @return Arraylist < String >
     */
    public ArrayList getListOfDistinctCategories()
    {
        int count =0;
        ArrayList <String> distinctCategoryNames = new ArrayList<>();
        for (Bauteil b : bauteilliste) {
            //Checks if the dynamic arrayList distinctCategoryNames contains the category of the current Bauteil
            if (!distinctCategoryNames.contains(b.getKategorie())){
                distinctCategoryNames.add(count, b.getKategorie());
                count++;
            }
        }
        return distinctCategoryNames;
    }

    public ArrayList<Bauteil> getBauteilliste() {
        return bauteilliste;
    }

    public String[] getMapping_array() {
        return mapping_array;
    }

    public static Double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

