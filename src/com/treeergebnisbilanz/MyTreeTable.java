package com.treeergebnisbilanz;

import java.awt.Dimension;

import javax.swing.JTable;


/**
 * Nachdem das Datenmodell, die Hilfskomponenten und die Main-Klasse stehen, fehlt jetzt noch die eigentliche TreeTable.
 * Dazu wird die Klasse MyTreeTable angelegt. Diese erbt von JTable. Da bei Java Mehrfachvererbung nicht möglich ist,
 * wird die Tree-Komponente über eine Assoziation in der Klasse MyTreeTableCellRenderer eingebunden.
 * Das Datenmodell MyAbstractTreeTableModel wird sowohl an den MyTreeTableCellRenderer (Tree) als auch an das Object MyTreeTableModelAdapter (Table) übergeben.
 * Als Modell wird die Klasse MyTreeTableModelAdapter gesetzt. Für das gleichzeitige Selektieren von Tree und Table wird das MyTreeTableSelectionModel
 * für Tree und Table verwendet. Dann muß noch ein Standard-Renderer für den Tree festgelegt und ein Default-Editor für die Table gesetzt werden.
 */
public class MyTreeTable extends JTable {

    private MyTreeTableCellRenderer tree;


    public MyTreeTable(MyAbstractTreeTableModel treeTableModel) {
        super();

        // JTree erstellen.
        tree = new MyTreeTableCellRenderer(this, treeTableModel);

        // Modell setzen.
        super.setModel(new MyTreeTableModelAdapter(treeTableModel, tree));

        // Gleichzeitiges Selektieren fuer Tree und Table.
        MyTreeTableSelectionModel selectionModel = new MyTreeTableSelectionModel();
        tree.setSelectionModel(selectionModel); //For the tree
        setSelectionModel(selectionModel.getListSelectionModel()); //For the table


        // Renderer fuer den Tree.
        setDefaultRenderer(MyTreeTableModel.class, tree);
        // Editor fuer die TreeTable
        setDefaultEditor(MyTreeTableModel.class, new MyTreeTableCellEditor(tree, this));

        // Kein Grid anzeigen.
        setShowGrid(true);

        // Keine Abstaende.
        setIntercellSpacing(new Dimension(0, 0));

    }
}