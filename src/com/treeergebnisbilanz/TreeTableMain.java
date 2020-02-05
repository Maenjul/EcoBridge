package com.treeergebnisbilanz;

import com.integbridge.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import static com.integbridge.Enums.getWbTemplate;


/**
 * In einer main-Klasse TreeTableMain kann jetzt ein Datenmodell mit Knoten erstellt werden.
 * Die Klasse MyTreeTable, die für die Darstellung verantwortlich ist, wird in den nächsten Abschnitten beschrieben.
 * In einem realen Anwendungsfall würde man die Datenstruktur höchstwahrscheinlich nicht in einer Methode createDataStructure zu Beginn komplett erstellen,
 * sondern beispielsweise die Daten bei Bedarf zu Laufzeit, aus einer Datenbank auslesen.
 */
public class TreeTableMain extends JFrame {

    static ArrayList<GrobElement> grobElementListe;

    public TreeTableMain(ArrayList<GrobElement> grobElementListe) {

        super("Auswertung Ökobilanz");
        this.grobElementListe = grobElementListe;


        setLayout(new GridLayout(0, 1));

        MyAbstractTreeTableModel treeTableModel = new MyDataModel(createDataStructure());

        MyTreeTable myTreeTable = new MyTreeTable(treeTableModel);

        Container cPane = getContentPane();

        cPane.add(new JScrollPane(myTreeTable));

        setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        setLocationRelativeTo(null);

    }

    private static MyDataNode createDataStructure() {


        List<MyDataNode> grobElementKnoten = new ArrayList<>();
        for (GrobElement grobElement : grobElementListe) //stahlträger, ortbeton, fertigteil, kappe
        {
            List<MyDataNode> grobElementInstanzKnoten = new ArrayList<>();
            for (GrobElementInstanz grobElementInstanz : grobElement.getGrobElementInstanzArrayList()) // Instanzen der Träger, Kappen etc
            {
                List<MyDataNode> feinElementKnoten = new ArrayList<>();
                for (FeinElement feinElement : grobElementInstanz.getZugehoerigeFeinElemente()) // FeinElemente  der Instanzen (Stahltprofil, Korrosionschutz...)
                {
                    List<MyDataNode> phasenKnoten = new ArrayList<>();
                    for(FeinElementPhase feinElementPhase : feinElement.getFeinElementPhasen()) // Instanzen/Phasen  der Feinelemente wie HErstellung von Stahlprofil..)
                    {
                        phasenKnoten.add(new MyDataNode("Phase", feinElementPhase.getPhase(), feinElementPhase.getWirkungsbilanz(), null));
                    }
                    feinElementKnoten.add(new MyDataNode(feinElement.getBezeichnung(), "Position Kumuliert", feinElement.getWirkungsbilanz_kumuliert(), phasenKnoten));
                }
                grobElementInstanzKnoten.add(new MyDataNode(grobElementInstanz.getStepBauteil(), "Bauteil_Kumuliert", grobElementInstanz.getWirkungsbilanz_kumuliert(), feinElementKnoten));
            }
            grobElementKnoten.add(new MyDataNode(grobElement.getBezeichnung(),"Kategorie_Kumuliert",grobElement.getWirkungsbilanz_kumuliert(),grobElementInstanzKnoten));
        }
        //Erstelle Root Node Gesamtbauwerk
        MyDataNode root = new MyDataNode("Gesamtbauwerk", "", getWbTemplate(), grobElementKnoten);

        return root;

    }

    public static void main(final String[] args) throws Exception {

        Runnable gui = new Runnable() {



            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new TreeTableMain(grobElementListe).setVisible(true);
            }
        };
        SwingUtilities.invokeLater(gui);
    }
}
