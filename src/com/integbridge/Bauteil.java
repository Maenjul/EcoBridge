package com.integbridge;

public class Bauteil {
    private String stepBauteil;                 //[0]
    private String familie_grobelement;         //[1]
    private String typ;                         //[2]
    private Double hoehe;                       //[3]
    private Integer id;                         //[4]
    private Double laenge;                       //[5]
    private Double halbe_Breite;                //[6]
    private Double breite;                      //[7]
    private Double bewehrungsgehalt;            //[8]
    private Double volumen;                     //[9]
    private String kategorie;                   //[10]
    private Double hoehe_oben;                  //[11]
    private Double hoehe_unten;                 //[12]
    private String korrosionsschutz;            //[13]
    private String stahlguete;                   //[14]
    private String betonguete;                   //[15]
    private Double kopfbolzenduebel_pro_meter;   //[16]
    private String stahlprofil;                 //[17]

    private String richtzeichnung;              //[18]
    private Double kappenueberstand;             //[19]
    private Double kappenhoehe_strasse;           //[20]
    private Double kappenneigung_oberseite;     //[21]
    private String abdichtung;                  //[22]
    private Double kappenlaenge;                 //[23]
    private Double kappenbreite;                //[24]
    private Double kappentiefe;                 //[25]
    private Double kappenflaeche;                //[26]

    private Double menge_gewicht;
    private Double gewicht_KopfBolzenduebel;
    private Double oberflaeche_Stahl;
    private Double bewehrungsmenge;

    public Bauteil () {}

    public void setBewehrungsmenge(Double bewehrungsmenge) {
        this.bewehrungsmenge = bewehrungsmenge;
    }

    public void setOberflaeche_Stahl(Double oberflaeche_Stahl) {
        this.oberflaeche_Stahl = oberflaeche_Stahl;
    }

    public void setGewicht_KopfBolzenduebel(Double gewicht_KopfBolzenduebel) {
        this.gewicht_KopfBolzenduebel = gewicht_KopfBolzenduebel;
    }

    public void setMenge_gewicht(Double menge_gewicht) {
        this.menge_gewicht = menge_gewicht;
    }

    public void setStepBauteil(String stepBauteil) {
        this.stepBauteil = stepBauteil;
    }

    public void setFamilie_grobelement(String familie_grobelement) {
        this.familie_grobelement = familie_grobelement;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void setHoehe(Double hoehe) {
        this.hoehe = hoehe;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLaenge(Double laenge) {
        this.laenge = laenge;
    }

    public void setHalbe_Breite(Double halbe_Breite) {
        this.halbe_Breite = halbe_Breite;
    }

    public void setBreite(Double breite) {
        this.breite = breite;
    }

    public void setBewehrungsgehalt(Double bewehrungsgehalt) {
        this.bewehrungsgehalt = bewehrungsgehalt;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public void setHoehe_oben(Double hoehe_oben) {
        this.hoehe_oben = hoehe_oben;
    }

    public void setHoehe_unten(Double hoehe_unten) {
        this.hoehe_unten = hoehe_unten;
    }

    public void setKorrosionsschutz(String korrosionsschutz) {
        this.korrosionsschutz = korrosionsschutz;
    }

    public void setStahlguete(String stahlguete) {
        this.stahlguete = stahlguete;
    }

    public void setBetonguete(String betonguete) {
        this.betonguete = betonguete;
    }

    public void setKopfbolzenduebel_pro_meter(Double kopfbolzenduebel_pro_meter) {
        this.kopfbolzenduebel_pro_meter = kopfbolzenduebel_pro_meter;
    }

    public void setStahlprofil(String stahlprofil) {
        this.stahlprofil = stahlprofil;
    }

    public void setRichtzeichnung(String richtzeichnung) {
        this.richtzeichnung = richtzeichnung;
    }

    public void setKappenueberstand(Double kappenueberstand) {
        this.kappenueberstand = kappenueberstand;
    }

    public void setKappenhoehe_straße(Double kappenhoehe_straße) {
        this.kappenhoehe_strasse = kappenhoehe_straße;
    }

    public void setKappenneigung_oberseite(Double kappenneigung_oberseite) {
        this.kappenneigung_oberseite = kappenneigung_oberseite;
    }

    public void setAbdichtung(String abdichtung) {
        this.abdichtung = abdichtung;
    }

    public void setKappenlaenge(Double kappenlaenge) {
        this.kappenlaenge = kappenlaenge;
    }

    public void setKappenbreite(Double kappenbreite) {
        this.kappenbreite = kappenbreite;
    }

    public void setKappentiefe(Double kappentiefe) {
        this.kappentiefe = kappentiefe;
    }

    public void setKappenflaeche(Double kappenflaeche) {
        this.kappenflaeche = kappenflaeche;
    }


    public Double getBewehrungsmenge() {
        return bewehrungsmenge;
    }

    public Double getGewicht_KopfBolzenduebel() {
        return gewicht_KopfBolzenduebel;
    }

    public Double getMenge_gewicht() {
        return menge_gewicht;
    }

    public Double getOberflaeche_Stahl() {
        return oberflaeche_Stahl;
    }

    public String getStepBauteil() {
        return stepBauteil;
    }

    public String getFamilie_grobelement() {
        return familie_grobelement;
    }

    public String getTyp() {
        return typ;
    }

    public Double getHoehe() {
        return hoehe;
    }

    public Integer getId() {
        return id;
    }

    public Double getLaenge() {
        return laenge;
    }

    public Double getHalbe_Breite() {
        return halbe_Breite;
    }

    public Double getBreite() {
        return breite;
    }

    public Double getBewehrungsgehalt() {
        return bewehrungsgehalt;
    }

    public Double getVolumen() {
        return volumen;
    }

    public String getKategorie() {
        return kategorie;
    }

    public Double getHoehe_oben() {
        return hoehe_oben;
    }

    public Double getHoehe_unten() {
        return hoehe_unten;
    }

    public String getKorrosionsschutz() {
        return korrosionsschutz;
    }

    public String getStahlguete() {
        return stahlguete;
    }

    public String getBetonguete() {
        return betonguete;
    }

    public Double getKopfbolzenduebel_pro_meter() {
        return kopfbolzenduebel_pro_meter;
    }

    public String getStahlprofil() {
        return stahlprofil;
    }

    public String getRichtzeichnung() {
        return richtzeichnung;
    }

    public Double getKappenueberstand() {
        return kappenueberstand;
    }

    public Double getKappenhoehe_straße() {
        return kappenhoehe_strasse;
    }

    public Double getKappenneigung_oberseite() {
        return kappenneigung_oberseite;
    }

    public String getAbdichtung() {
        return abdichtung;
    }

    public Double getKappenlaenge() {
        return kappenlaenge;
    }

    public Double getKappenbreite() {
        return kappenbreite;
    }

    public Double getKappentiefe() {
        return kappentiefe;
    }

    public Double getKappenflaeche() {
        return kappenflaeche;
    }
}



