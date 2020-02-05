package com.treeergebnisbilanz;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.TreePath;

/**
 * Da bei Java Swing auch die GUI-Komponenten immer noch ein Model benötigten, welches ungleich dem eigentlich Datenmodell ist,
 * wird jetzt noch eine Klasse MyTreeTableModelAdapter, welche von AbstractTableModel erbt, angelegt.
 * Diese Klasse wird später in der Klasse MyTreeTable als Model für die JTable verwendet.
 * Wenn die TreeTable später nach Werten fragt, die angezeigt werden sollen, muß unterschieden werden,
 * ob die angefragten Werte vom Tree oder direkt von Datenmodell MyAbstractTreeTableModel geliefert werden können.
 * Außerdem wird in der Klasse noch der TreeExpansionListener erzeugt und registriert. Dieser reagiert auf Klicks im Baum und sorgt dafüf,
 * daß der Baum auf- und zugeklappt wird.
 */
public class MyTreeTableModelAdapter extends AbstractTableModel {

    JTree tree;
    MyAbstractTreeTableModel treeTableModel;

    public MyTreeTableModelAdapter(MyAbstractTreeTableModel treeTableModel, JTree tree) {
        this.tree = tree;
        this.treeTableModel = treeTableModel;

        tree.addTreeExpansionListener(new TreeExpansionListener() {
            public void treeExpanded(TreeExpansionEvent event) {
                fireTableDataChanged();
            }

            public void treeCollapsed(TreeExpansionEvent event) {
                fireTableDataChanged();
            }
        });
    }



    public int getColumnCount() {
        return treeTableModel.getColumnCount();
    }

    public String getColumnName(int column) {
        return treeTableModel.getColumnName(column);
    }

    public Class<?> getColumnClass(int column) {
        return treeTableModel.getColumnClass(column);
    }

    public int getRowCount() {
        return tree.getRowCount();
    }

    protected Object nodeForRow(int row) {
        TreePath treePath = tree.getPathForRow(row);
        return treePath.getLastPathComponent();
    }

    public Object getValueAt(int row, int column) {
        return treeTableModel.getValueAt(nodeForRow(row), column);
    }

    public boolean isCellEditable(int row, int column) {
        return treeTableModel.isCellEditable(nodeForRow(row), column);
    }

    public void setValueAt(Object value, int row, int column) {
        treeTableModel.setValueAt(value, nodeForRow(row), column);
    }
}
