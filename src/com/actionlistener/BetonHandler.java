package com.actionlistener;

import com.integbridge.Bauteil;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static com.integbridge.Enums.betongueteEnum;

public class BetonHandler implements ItemListener {

    private Bauteil bauteil;
    public BetonHandler (Bauteil bauteil) {
        this.bauteil = bauteil;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox comboBox;
        if (e.getStateChange() == ItemEvent.SELECTED) {
            comboBox = (JComboBox)  e.getItemSelectable();

            //Check if in Database HAHAAAAAA
            if (((String) comboBox.getSelectedItem()).equals(betongueteEnum[0])) {JOptionPane.showMessageDialog( null,"Achtung! \n C20/25 nicht in der Datenbank vorhanden");};
            if (((String) comboBox.getSelectedItem()).equals(betongueteEnum[3])) {JOptionPane.showMessageDialog( null,"Achtung! \n C35/45 nicht in der Datenbank vorhanden");};
            if (((String) comboBox.getSelectedItem()).equals(betongueteEnum[4])) {JOptionPane.showMessageDialog( null,"Achtung! \n C40/50 nicht in der Datenbank vorhanden");};
            if (((String) comboBox.getSelectedItem()).equals(betongueteEnum[5])) {JOptionPane.showMessageDialog( null,"Achtung! \n C45/50 nicht in der Datenbank vorhanden");};
            if (((String) comboBox.getSelectedItem()).equals(betongueteEnum[6])) {JOptionPane.showMessageDialog( null,"Achtung! \n C50/60 nicht in der Datenbank vorhanden");};
            if (((String) comboBox.getSelectedItem()).equals(betongueteEnum[7])) {JOptionPane.showMessageDialog( null,"Achtung! \n C55/67 nicht in der Datenbank vorhanden");};
            if (((String) comboBox.getSelectedItem()).equals(betongueteEnum[1])) {bauteil.setBetonguete((String) comboBox.getSelectedItem());};
            if (((String) comboBox.getSelectedItem()).equals(betongueteEnum[2])) {bauteil.setBetonguete((String) comboBox.getSelectedItem());};
            System.out.println(bauteil.getBetonguete());
        }
    }
}
