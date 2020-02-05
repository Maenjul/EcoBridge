package com.integbridge;

import com.actionlistener.*;
import com.treeergebnisbilanz.TreeTableMain;
import com.apstex.ifc2x3toolbox.ifcmodel.IfcModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.integbridge.Enums.*;


public class GUI_ParameterSetting extends JFrame {

    private JScrollPane scrollPane;
    private GridBagConstraints contentPanelConstraints = new GridBagConstraints();
    private GridBagConstraints grobElementPanelConstraints = new GridBagConstraints();
    private GridBagConstraints feinElementPanelConstraints = new GridBagConstraints();

    private NumberFormat numF;

    private int gx = 0;
    private int gy = 0;
    private int hgap = 20;
    private int vgap = 20;

    private JPanel contentPanel;

    private JPanel grobElementPanel1;
    private JPanel grobElementPanel2;
    private JPanel grobElementPanel3;

    private JPanel feinElementPanel1;
    private JPanel feinElementPanel2;
    private JPanel feinElementPanel3;
    private JPanel feinElementPanel4;

    private Bauteilliste bauteilliste;
    private SQLConnection sqlConnection;


    public GUI_ParameterSetting(JFrame aFrame, Bauteilliste bauteilliste, SQLConnection sqlConnection) {

        this.bauteilliste =bauteilliste;
        this.sqlConnection=sqlConnection;

        this.setTitle("Parameter-Überprüfung");
        this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        this.setLayout(new GridLayout()); // Durch Setzen des Dialog Layouts als Grid wird ^der Jpanel automatisch immer an die Größe des Dialogs angepasst

        //Inputfeldformat für Kappenhöhe usw.
        numF = NumberFormat.getNumberInstance();
        numF.setMaximumIntegerDigits(3);
        numF.setMinimumIntegerDigits(1);

        //Initialisiere das GridbagLayout

        contentPanelConstraints.gridx = gx;
        contentPanelConstraints.gridy = gy;

        //Inititalisiere das MainPanel
        contentPanel = new JPanel();
        contentPanel.setOpaque(true);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(vgap, hgap, vgap, hgap));
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        JButton auswertung = new JButton("AUSWERTUNG");
        auswertung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ErgebnisPopulation ende = new ErgebnisPopulation(bauteilliste,sqlConnection);
                ArrayList<GrobElement> letzteListe = ende.getGrobElementListe();


                TreeTableMain ergebnisAuswertung = new TreeTableMain(letzteListe);
                ergebnisAuswertung.setVisible(true);
                ergebnisAuswertung.setAutoRequestFocus(true);

            }
        });


        /*
        contentPanelConstraints.ipady = 40;      //make this component tall
        contentPanelConstraints.weightx = 0.0;
        contentPanelConstraints.gridwidth = 3;
        contentPanelConstraints.gridx = 0;
        contentPanelConstraints.gridy = 1;
        contentPanel.add(kat2, contentPanelConstraints);

        JButton button5 = new JButton("5");
        contentPanelConstraints.ipady = 0;       //reset to default
        contentPanelConstraints.weighty = 1.0;   //request any extra vertical space
        contentPanelConstraints.anchor = GridBagConstraints.PAGE_END; //bottom of space
        contentPanelConstraints.insets = new Insets(10,0,0,0);  //top padding
        contentPanelConstraints.gridx = 1;       //aligned with button 2
        contentPanelConstraints.gridwidth = 2;   //2 columns wide
        contentPanelConstraints.gridy = 2;       //third row
        contentPanel.add(button5, contentPanelConstraints);
*/
        //Sortiert die Bauteile nach ihrer ID
        Collections.sort(bauteilliste.getBauteilliste(), new Comparator<Bauteil>() {
            public int compare(Bauteil self, Bauteil other) {
                return -1 * self.getId().compareTo(other.getId());
            }
        });
        createGrobElementSection();
        grobElementPanel2.add(auswertung);
        createFeinElementSection();
        insertBauteil1(bauteilliste);


        //Der contentPanel wird hier in den ScrollPane integriert. Deshalb wird nur der Scrollpane letzlich an den
        //Parent (Dialog) übergeben.
        scrollPane = new JScrollPane(contentPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane);

        this.setVisible(true);
    }

    private void createGrobElementSection() {
        //Initiiere ContenPanelLayout
        gx = 0;
        gy = 0;
        contentPanelConstraints.weightx = 1;
        contentPanelConstraints.weighty = 1;
        contentPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        contentPanelConstraints.gridy = gy;
        contentPanelConstraints.gridx = gx;

        //Erstellt die KategorienPanel. Layout ist Gridbag.
        grobElementPanel1 = new JPanel();
        grobElementPanel1.setOpaque(true);
        grobElementPanel1.setLayout(new GridBagLayout());
        grobElementPanel1.setBackground(Color.WHITE);
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.lightGray, Color.WHITE), "Brückenträger");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleColor(Color.LIGHT_GRAY);
        grobElementPanel1.setBorder(title);

        contentPanel.add(grobElementPanel1, contentPanelConstraints);
        gy++;
        contentPanelConstraints.gridy = gy;

        grobElementPanel2 = new JPanel();
        grobElementPanel2.setOpaque(true);
        grobElementPanel2.setLayout(new GridBagLayout());
        grobElementPanel2.setBackground(Color.WHITE);
        TitledBorder title2 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.lightGray, Color.WHITE), "");
        title2.setTitleJustification(TitledBorder.CENTER);
        title2.setTitleColor(Color.LIGHT_GRAY);
        grobElementPanel2.setBorder(title2);
        contentPanel.add(grobElementPanel2, contentPanelConstraints);
        gy++;
        contentPanelConstraints.gridy = gy;

        grobElementPanel3 = new JPanel();
        grobElementPanel3.setOpaque(true);
        grobElementPanel3.setLayout(new GridBagLayout());
        grobElementPanel3.setBackground(Color.WHITE);
        grobElementPanel3.setBorder(BorderFactory.createTitledBorder("Nicht zugeordnet"));
        contentPanel.add(grobElementPanel3, contentPanelConstraints);
        gy++;
        contentPanelConstraints.gridy = gy;
    }

    private void createFeinElementSection() {
        //Initiiere das Layout der grobElementPanels also der KAtegorien Täger und Kappen
        int column = 0;
        int row = 0;
        grobElementPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        grobElementPanelConstraints.gridy = row;
        grobElementPanelConstraints.gridx = column;
        grobElementPanelConstraints.weightx = 1;
        grobElementPanelConstraints.weighty = 1;
        grobElementPanelConstraints.insets.set(15, 0, 15, 0);


        //Bauteilgruppe 1, Stahlträger (ID: 11101)
        feinElementPanel1 = new JPanel();
        feinElementPanel1.setOpaque(true);
        feinElementPanel1.setLayout(new GridBagLayout());
        feinElementPanel1.setBackground(Color.WHITE);
        feinElementPanel1.setBorder(BorderFactory.createTitledBorder("Stahlträger"));
        grobElementPanel1.add(feinElementPanel1, grobElementPanelConstraints);
        row++;
        grobElementPanelConstraints.gridy = row;
        String[] stahltraegerattribute = {"Step-Bauteil", "Identifikation", "Profil", "Stahlgüte", "Menge [t]", "Korrosionsschutzsystem", "Oberfläche[m^2]", "Kopfbolzendübel[t]", "Länge[m]"};
        inititaFeinElementPanel(stahltraegerattribute, feinElementPanel1);

        //Bauteilgruppe 2, Fertigteilplatten  (ID: 11102)
        feinElementPanel2 = new JPanel();
        feinElementPanel2.setOpaque(true);
        feinElementPanel2.setLayout(new GridBagLayout());
        feinElementPanel2.setBackground(Color.WHITE);
        feinElementPanel2.setBorder(BorderFactory.createTitledBorder("Fertigteilplatten"));
        grobElementPanel1.add(feinElementPanel2, grobElementPanelConstraints);
        row++;
        grobElementPanelConstraints.gridy = row;
        String[] fertigteilAttribute = {"Step-Bauteil", "Identifikation", "Betongüte", "Betonvolumen [m^3]", "Bewehrungsmenge [t]"};
        inititaFeinElementPanel(fertigteilAttribute, feinElementPanel2);

        //Bauteilgruppe 3, Ortbetonplatte (ID: 11103)
        feinElementPanel3 = new JPanel();
        feinElementPanel3.setOpaque(true);
        feinElementPanel3.setLayout(new GridBagLayout());
        feinElementPanel3.setBackground(Color.WHITE);
        feinElementPanel3.setBorder(BorderFactory.createTitledBorder("Ortbetonplatte"));
        grobElementPanel1.add(feinElementPanel3, grobElementPanelConstraints);
        row++;
        grobElementPanelConstraints.gridy = row;
        String[] ortBetonAttribute = {"Step-Bauteil", "Identifikation", "Betongüte", "Betonvolumen [m^3]", "Bewehrungsmenge [t]"};
        inititaFeinElementPanel(ortBetonAttribute, feinElementPanel3);

        //Bauteilgruppe 4, Brückenkappe (ID: 11104)
        feinElementPanel4 = new JPanel();
        feinElementPanel4.setOpaque(true);
        feinElementPanel4.setLayout(new GridBagLayout());
        feinElementPanel4.setBackground(Color.WHITE);
        feinElementPanel4.setBorder(BorderFactory.createTitledBorder("Brückenkappe"));
        grobElementPanel1.add(feinElementPanel4, grobElementPanelConstraints);
        String[] kappenAttribute = {"Step-Bauteil", "Identifikation", "Richtzeichnung", "Betongüte", "Abdichtung", "Länge[m]", "Volumen [m^3]", "Kappentiefe [m]", "Kappenhöhe_Straße [m]", "Kappenneigung [°]", "Kappenbreite [m]", "Kappenüberstand", "Fläche"};
        inititaFeinElementPanel(kappenAttribute, feinElementPanel4);
    }

    private void inititaFeinElementPanel(String[] attribute, JPanel feinElementPanel) {
        //Initiiere das Layout der FeinElemente (Stahlträger, Betonplatten...)
        int column = 0;
        int row = 0;
        feinElementPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        feinElementPanelConstraints.gridy = row;
        feinElementPanelConstraints.gridx = column;
        feinElementPanelConstraints.fill = GridBagConstraints.BOTH;
        feinElementPanelConstraints.weightx = 1;
        feinElementPanelConstraints.weighty = 1;
        feinElementPanelConstraints.ipadx = 30;
        feinElementPanelConstraints.insets.set(5, 0, 5, 0);
        for (String string : attribute) {
            JLabel a = new JLabel(string);
            a.setBackground(Color.lightGray);
            a.setOpaque(true);
            a.setBorder(BorderFactory.createEtchedBorder());
            feinElementPanel.add(a, feinElementPanelConstraints);
            column++;
            feinElementPanelConstraints.gridx = column;
        }
    }

    private void insertBauteil1(Bauteilliste bauteilliste) {

        //Initiiere Grid
        int row = 1;
        for (Bauteil bauteil : bauteilliste.getBauteilliste()) {
            int column = 0;

            //Falls Bauteil ein Stahlträger ist
            if ((Integer.parseInt(Integer.toString(bauteil.getId()).substring(0, 5)) - 11101) == 0) {
                feinElementPanelConstraints.gridy = row;
                feinElementPanelConstraints.gridx = column;

                JLabel step = new JLabel(bauteil.getStepBauteil());
                step.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel1.add(step, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel id = new JLabel(bauteil.getId().toString());
                id.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel1.add(id, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;


                JComboBox profil = new JComboBox(stahlprofilEnum);
                profil.setSelectedItem(bauteil.getStahlguete());

                //menge Jlabel wird nun zuerst deklariert um nullpointerexception zu vermeiden
                JLabel menge = new JLabel();
                try {
                    bauteil.setMenge_gewicht(profil_Flaeche.get(bauteil.getStahlprofil()) * bauteil.getLaenge() * 7.85);
                    menge.setText(round(bauteil.getMenge_gewicht(), 2).toString());
                    menge.setBorder(BorderFactory.createEtchedBorder());
                } catch (Exception e) {
                    profil.setSelectedItem(null);
                    JOptionPane.showMessageDialog(null, "Stahlprofil von Bauteil " + bauteil.getStepBauteil() + " wurde nicht erkannt!", "Informationsmangel",JOptionPane.INFORMATION_MESSAGE);
                }

                //oberfläche wird nun zuerst deklariert um nullpointerexception zu vermeiden
                JLabel oberflaeche = new JLabel();
                try {
                    bauteil.setOberflaeche_Stahl(round((bauteil.getLaenge() * profil_Umfang.get(bauteil.getStahlprofil())), 2));
                    oberflaeche.setText(bauteil.getOberflaeche_Stahl().toString());
                    oberflaeche.setBorder(BorderFactory.createEtchedBorder());
                } catch (Exception e) {}


                profil.addItemListener(new StahlprofilHandler(bauteil, menge, oberflaeche));
                feinElementPanel1.add(profil, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JComboBox stahlguete = new JComboBox(stahlgueteEnum); //Erstelle StahlgüteKombobox
                stahlguete.setSelectedItem(bauteil.getStahlguete());
                stahlguete.addItemListener(new StahlgueteHandler(bauteil));
                feinElementPanel1.add(stahlguete, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                //Erst an dieser Stelle wird die menge dem Panel angehängt
                feinElementPanel1.add(menge, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JComboBox korrosion = new JComboBox(korrosionsschutzEnum); //Erstelle Korrosion KomboBox mit KorrosionEnum
                korrosion.setSelectedItem(bauteil.getKorrosionsschutz());
                korrosion.addItemListener(new KorrosionHandler(bauteil));
                feinElementPanel1.add(korrosion, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;


                feinElementPanel1.add(oberflaeche, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                Double y = (((bauteil.getLaenge() * bauteil.getKopfbolzenduebel_pro_meter()) * 0.0005));
                y = Double.parseDouble(Double.toString(y).substring(0, 5));
                bauteil.setGewicht_KopfBolzenduebel(y);
                JLabel duebelGewicht = new JLabel(bauteil.getGewicht_KopfBolzenduebel().toString());
                duebelGewicht.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel1.add(duebelGewicht, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel laenge = new JLabel(bauteil.getLaenge().toString());
                laenge.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel1.add(laenge, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;
            }
            row++;
        }
        row = 1;
        for (Bauteil bauteil : bauteilliste.getBauteilliste()) {
            int column = 0;
            //Falls Bauteil ein Fertigteilplatte ist
            if ((Integer.parseInt(Integer.toString(bauteil.getId()).substring(0, 5)) - 11102) == 0) {
                feinElementPanelConstraints.gridy = row;
                feinElementPanelConstraints.gridx = column;

                JLabel step = new JLabel(bauteil.getStepBauteil());
                step.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel2.add(step, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel id = new JLabel(bauteil.getId().toString());
                id.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel2.add(id, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JComboBox betonguete = new JComboBox(betongueteEnum);
                betonguete.setSelectedItem(bauteil.getBetonguete());
                betonguete.addItemListener(new BetonHandler(bauteil));
                feinElementPanel2.add(betonguete, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel volumen = new JLabel(round(bauteil.getVolumen(), 2).toString());
                volumen.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel2.add(volumen, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                bauteil.setBewehrungsmenge(round(bauteil.getBewehrungsgehalt() / 100 * bauteil.getVolumen() * 7.850, 2)); //Errechne bewehrungsmenge

                JLabel bewehrungsmenge = new JLabel(bauteil.getBewehrungsmenge().toString());
                bewehrungsmenge.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel2.add(bewehrungsmenge, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

            }
            row++;
        }
        row = 1;
        for (Bauteil bauteil : bauteilliste.getBauteilliste()) {
            int column = 0;
            //Falls Bauteil ein Ortbetonplatte ist
            if ((Integer.parseInt(Integer.toString(bauteil.getId()).substring(0, 5)) - 11103) == 0) {
                feinElementPanelConstraints.gridy = row;
                feinElementPanelConstraints.gridx = column;

                JLabel step = new JLabel(bauteil.getStepBauteil());
                step.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel3.add(step, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel id = new JLabel(bauteil.getId().toString());
                id.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel3.add(id, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JComboBox betonguete = new JComboBox(betongueteEnum);
                betonguete.setSelectedItem(bauteil.getBetonguete());
                betonguete.addItemListener(new BetonHandler(bauteil));
                feinElementPanel3.add(betonguete, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;


                JLabel volumen = new JLabel(round(bauteil.getVolumen(), 2).toString());
                volumen.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel3.add(volumen, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                bauteil.setBewehrungsmenge(round(bauteil.getBewehrungsgehalt() / 100 * bauteil.getVolumen() * 7.850, 2)); //Errechne bewehrungsmenge

                JLabel bewehrungsmenge = new JLabel(bauteil.getBewehrungsmenge().toString());
                bewehrungsmenge.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel3.add(bewehrungsmenge, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;
            }
            row++;
        }
        row = 1;
        for (Bauteil bauteil : bauteilliste.getBauteilliste()) {
            int column = 0;
            //Falls Bauteil eine Brückenkappe ist
            if ((Integer.parseInt(Integer.toString(bauteil.getId()).substring(0, 5)) - 12104) == 0) {
                feinElementPanelConstraints.gridy = row;
                feinElementPanelConstraints.gridx = column;

                JLabel step = new JLabel(bauteil.getStepBauteil());
                step.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel4.add(step, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel id = new JLabel(bauteil.getId().toString());
                id.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel4.add(id, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JComboBox richtzeichnung = new JComboBox(richtzeichnungEnum);
                richtzeichnung.setSelectedItem(bauteil.getRichtzeichnung());
                richtzeichnung.addItemListener(new RichtzeichnungHandler(bauteil));
                feinElementPanel4.add(richtzeichnung, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JComboBox betonguete = new JComboBox(kappenbetonEnum);
                betonguete.setSelectedItem(bauteil.getBetonguete());
                betonguete.addItemListener(new BetonHandler(bauteil));
                feinElementPanel4.add(betonguete, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel abdichtung = new JLabel(bauteil.getAbdichtung());
                abdichtung.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel4.add(abdichtung, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel laenge = new JLabel(round(bauteil.getLaenge(), 2).toString());
                laenge.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel4.add(laenge, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel volumen = new JLabel(round(bauteil.getVolumen(), 2).toString());
                volumen.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel4.add(volumen, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                try {bauteil.setBewehrungsmenge(round(bauteil.getBewehrungsgehalt() / 100 * bauteil.getVolumen() * 7.850, 2));}
                catch (Exception e) {bauteil.setBewehrungsmenge(0.0);}

                JFormattedTextField tiefe = new JFormattedTextField(numF);
                tiefe.setValue(round(bauteil.getKappentiefe(), 2));
                feinElementPanel4.add(tiefe, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JFormattedTextField hoehe = new JFormattedTextField(numF);
                hoehe.setValue(round(bauteil.getKappenhoehe_straße(), 2));
                feinElementPanel4.add(hoehe, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JFormattedTextField neigung = new JFormattedTextField(numF);
                neigung.setValue(round(bauteil.getKappenneigung_oberseite(), 2));
                feinElementPanel4.add(neigung, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JFormattedTextField breite = new JFormattedTextField(numF);
                breite.setValue(round(bauteil.getKappenbreite(), 2));
                feinElementPanel4.add(breite, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JFormattedTextField ueberstand = new JFormattedTextField(numF);
                ueberstand.setValue(round(bauteil.getKappenueberstand(), 2));
                feinElementPanel4.add(ueberstand, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

                JLabel flaeche = new JLabel(round(bauteil.getKappenflaeche(), 2).toString());
                flaeche.setBorder(BorderFactory.createEtchedBorder());
                feinElementPanel4.add(flaeche, feinElementPanelConstraints);
                column++;
                feinElementPanelConstraints.gridx = column;

            }
            row++;
        }
    }






    public static void main (String[] args) throws Exception {
        //D:\Studium\Master\MasterArbeit\Integbridge-Revit\Integbridge 2.ifc

        File file = new File(("D:\\Studium\\Master\\MasterArbeit\\Integbridge-Revit\\Integbridge 3.ifc"));
        IfcModel ifcModel = new IfcModel();
        ifcModel.readStepFile(file);
        SQLConnection sqlConnection = new SQLConnection();
        IfcParse ifcParse = new IfcParse(sqlConnection, ifcModel);
        ifcParse.createProperty_ObjectMap();
        Bauteilliste bauteilliste = new Bauteilliste(ifcParse.getIfcObject_IfcPropertySingleValue_Map());
        JFrame aFrame = new JFrame("TESTT");
        GUI_ParameterSetting start = new GUI_ParameterSetting(aFrame, bauteilliste,sqlConnection);
    }



    public static Double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
