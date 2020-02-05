package com.actionlistener;

import com.integbridge.Bauteil;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static com.integbridge.Enums.profil_Flaeche;
import static com.integbridge.Enums.profil_Umfang;
import static com.integbridge.GUI_ParameterSetting.round;

public class StahlprofilHandler implements ItemListener {

    private Bauteil bauteil;
    private JLabel menge;
    private JLabel oberfläche;

    public  StahlprofilHandler (Bauteil bauteil, JLabel menge, JLabel oberfläche) {
        this.bauteil = bauteil;
        this.menge = menge;
        this.oberfläche = oberfläche;

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox comboBox;
        if (e.getStateChange() == ItemEvent.SELECTED) {
            comboBox = (JComboBox)  e.getItemSelectable();
            bauteil.setStahlprofil((String) comboBox.getSelectedItem());
            System.out.println(bauteil.getStahlprofil());

            //Updated automatisch die Stahlmenge anhand der Profilfläche der Länge und der Dichte des Stahlbauteils
            bauteil.setMenge_gewicht(profil_Flaeche.get(bauteil.getStahlprofil())*bauteil.getLaenge()*7.85);
            menge.setText(round(bauteil.getMenge_gewicht(),2).toString());
            System.out.println(bauteil.getMenge_gewicht());

            //Updated automatisch die Oberfläche anhand des Umfangs und der Länge des Bauteils
            bauteil.setOberflaeche_Stahl(round((bauteil.getLaenge()*profil_Umfang.get(bauteil.getStahlprofil())),2));
            oberfläche.setText(bauteil.getOberflaeche_Stahl().toString());
        }
    }
}
