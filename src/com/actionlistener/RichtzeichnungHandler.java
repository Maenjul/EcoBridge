package com.actionlistener;

import com.integbridge.Bauteil;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RichtzeichnungHandler implements ItemListener {

    private Bauteil bauteil;
    public  RichtzeichnungHandler (Bauteil bauteil) {
        this.bauteil = bauteil;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JComboBox comboBox;
        if (e.getStateChange() == ItemEvent.SELECTED) {
            comboBox = (JComboBox)  e.getItemSelectable();
            bauteil.setRichtzeichnung((String) comboBox.getSelectedItem());
            System.out.println(bauteil.getRichtzeichnung());
        }
    }
}
