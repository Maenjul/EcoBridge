package com.treeergebnisbilanz;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;

/**
 * Um das Aufklappen des Baumes zu ermöglichen, benötigt man einen AbstractCellEditor.
 * Deshalb legt man eine Klasse MyTreeTableCellEditor an, die AbstractCellEditor erweitert und das TableCellEditor-Interface implementiert.
 * Die einzige Funktion der Klasse MyTreeTableCellEditor ist das Weiterleiten eines Doppelklicks an den Baum.
 * In der Methode isCellEditable wird überprüft, ob die erste Spalte (column1) angeklickt wurde. Wenn das der Fall ist, wird ein Doppelklick an dem tree weitergeleitet,
 * so daß die ExpansionListener reagieren können.
 */
public class MyTreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JTree tree;
    private JTable table;

    public MyTreeTableCellEditor(JTree tree, JTable table) {
        this.tree = tree;
        this.table = table;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) {
        return tree;
    }

    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            int colunm1 = 0;
            MouseEvent me = (MouseEvent) e;
            int doubleClick = 2;
            MouseEvent newME = new MouseEvent(tree, me.getID(), me.getWhen(), me.getModifiers(), me.getX() - table.getCellRect(0, colunm1, true).x, me.getY(), doubleClick, me.isPopupTrigger());
            tree.dispatchEvent(newME);
        }
        return false;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

}