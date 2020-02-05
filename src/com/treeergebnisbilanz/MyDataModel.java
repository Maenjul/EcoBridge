package com.treeergebnisbilanz;

/**
 * In der Klasse MyDataModel wird das konkrete Datenmodell der Ansicht definiert. D.h. es werden die Spalten inklusive Datentyp festgelegt.
 * Außerdem enthält die Klasse die noch nicht implementierten Methoden des Interfaces TreeModel. Zu beachten ist dabei die Methode isCellEditable.
 * Diese muß den Wert true zurückliefern, damit die Listener auf ein treeExpanded oder treeCollapsed reagieren können.
 * Man könnte in dieser Methode auch nur für die erste Spalte (column) den Wert true zurückliefern und sonst den Wert false.
 */
public class MyDataModel extends MyAbstractTreeTableModel {
    // Spalten Name.
    static protected String[] columnNames = {
            "Hierarchie", "Phase im Lebenszyklus",
            "GWP\n" + "[kg CO2-\n" + "Äq./m2a]",
            "OPD\n" + "[kg R11-\n" + "Äq./m2a]",
            "POCP\n" + "[kg C2H4-\n" + "Äq./m2a]",
            "AP\n" + "[kg SO_(2)\n" + "-Äq.]",
            "EP\n" + "[kg PO_(4)^(3)\n" + "-Äq.]",
            "ADPE\n" +  "[kg Sb-\n" + "Äq.]",
            "ADPF\n" +"[MJ]",
            "PENE [MJ]",
            "PE [MJ]"};

    // Spalten Typen.
    static protected Class<?>[] columnTypes = { MyTreeTableModel.class, String.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class};

    public MyDataModel(MyDataNode rootNode) {
        super(rootNode);
        root = rootNode;
    }

    public Object getChild(Object parent, int index) {
        return ((MyDataNode) parent).getChildren().get(index);
    }


    public int getChildCount(Object parent) {
        return ((MyDataNode) parent).getChildren().size();
    }


    public int getColumnCount() {
        return columnNames.length;
    }


    public String getColumnName(int column) {
        return columnNames[column];
    }


    public Class<?> getColumnClass(int column) {
        return columnTypes[column];
    }

    public Object getValueAt(Object node, int column) {
        switch (column) {
            case 0:
                return ((MyDataNode) node).getHierarchie();
            case 1:
                return ((MyDataNode) node).getLebenszyklusphase();
            case 2:
                return ((MyDataNode) node).getGwp();
            case 3:
                return ((MyDataNode) node).getOdp();
            case 4:
                return ((MyDataNode) node).getPocp();
            case 5:
                return ((MyDataNode) node).getAp();
            case 6:
                return ((MyDataNode) node).getEp();
            case 7:
                return ((MyDataNode) node).getAdpe();
            case 8:
                return ((MyDataNode) node).getAdpf();
            case 9:
                return ((MyDataNode) node).getPene();
            case 10:
                return ((MyDataNode) node).getPe();

            default:
                break;
        }
        return null;
    }

    public boolean isCellEditable(Object node, int column) {
        return true; // Important to activate TreeExpandListener
    }

    public void setValueAt(Object aValue, Object node, int column) {
    }

}
