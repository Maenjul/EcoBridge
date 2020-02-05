package com.treeergebnisbilanz;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;

/**
 * Da sich die TreeTable aus einer JTree-Komponente und einer JTable-Komponente zusammensetzt, muß dafür gesorgt werden,
 * daß bei der Selektion des Baums oder der Tabelle immer eine durchgehende Zeile markiert wird.
 * Um das zu gewährleisten erstellt man eine Klasse MyTreeTableSelectionModel, die das DefaultTreeSelectionModel erweitert.
 * Dieses SelectionModel wird später dem JTree und dem JTable zugewiesen.
 */
public class MyTreeTableSelectionModel extends DefaultTreeSelectionModel {

    public MyTreeTableSelectionModel() {
        super();

        getListSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
    }

    ListSelectionModel getListSelectionModel() {
        return listSelectionModel;
    }
}