package com.integbridge;

import com.apstex.ifc2x3toolbox.demo.structureviewer.IfcTreeView;
import com.apstex.ifc2x3toolbox.ifcmodel.IfcModel;
import com.apstex.step.parser.util.ProgressEvent;
import com.apstex.step.parser.util.StepParserProgressListener;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private IfcModel ifcModel = null;
    private JProgressBar progressBar = null;
    private IfcTreeView treeView = null;
    private JTextPane infoPane = null;
    private JScrollPane scrollPane = null;
    private JMenuItem saveItem = null;
    private JMenuItem transmitIfc = null;
    private SQLConnection sqlConnection = null;
    private String url = "jdbc:postgresql://localhost:5433/integbridge";
    private String user = "postgres";
    private String password = "integbridge";
    private int connectionState = 0; // Eventuell noch den Status der Verbindung anzeigen
    private int fileLoadedState = 0;


    /**
     * Constructs a new User Interface Object object.
     */
    public GUI()
    {
        initComponents();
    }

    /**
     * initialize the application components
     */
    private void initComponents() {

        // Create new IfcModel
        ifcModel = new IfcModel();
        // init the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(1400, 600);
        // set MenuBar
        this.setJMenuBar(getApplicationMenuBar());
        // set ProgressBar
        this.add(getApplicationProgressBar(), BorderLayout.SOUTH);
        // set TreeView
        this.add(treeView = new IfcTreeView(), BorderLayout.CENTER);
        // set InfoPane
        this.add(getInfoPane(), BorderLayout.WEST);
        // set title of Frame
        this.setTitle("Integbridge-Ifc-Ökobilanzchecker");
        // set frame to visible
        this.setIconImage(new ImageIcon("icons/ecobridge.jpg").getImage());
        this.setVisible(true);

        // Sets the default sqlConnection Credentials
        sqlConnection = new SQLConnection();
        sqlConnection.setCredentials(url, user, password);
    }

    private JScrollPane getInfoPane() {
        infoPane = new JTextPane();
        infoPane.setEditable(false);
        //StyledDocument doc = infoPane.getStyledDocument();
        //addStylesToDocument(doc);
        scrollPane = new JScrollPane();
        scrollPane.setMinimumSize(new Dimension(300, 400));
        scrollPane.setMaximumSize(new Dimension(300, 400));
        scrollPane.setPreferredSize(new Dimension(300, 400));
        scrollPane.setViewportView(infoPane);
        return scrollPane;
    }

    private JProgressBar getApplicationProgressBar() {
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(600, 20));
        progressBar.setStringPainted(true);
        return progressBar;
    }

    private JMenuBar getApplicationMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Datei");
        menuBar.add(fileMenu);
        fileMenu.setMnemonic('d');

        JMenuItem openItem = new JMenuItem("Lade Ifc-Datei");
        openItem.setMnemonic('l');
        openItem.setToolTipText("Wähle eine lokal gespeicherte Ifc-Datei deiner Brücke aus");
//		openItem.setIcon(new ImageIcon(new ImageIcon(openImageUrl).getImage()
//				.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        openItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadFile();
            }
        });
        fileMenu.add(openItem);

        saveItem = new JMenuItem("save (not working)");
        saveItem.setEnabled(false);
        saveItem.setMnemonic('o');
//		saveItem.setIcon(new ImageIcon(new ImageIcon(saveImageUrl).getImage()
//				.getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        saveItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        fileMenu.add(saveItem);

        //Im Menüpunkt Datenbankverbindung werden die Benutzerdaten abgefragt um eine Verbindung mit dem
        //lokalen Postgresserver herzustellen
        JMenu dataBaseConnection = new JMenu("Datenbankverbindung");
        dataBaseConnection.setMnemonic('D');
        JMenuItem initiateCredentials = new JMenuItem("Zugangsdaten");
        initiateCredentials.setMnemonic('N');
        initiateCredentials.setToolTipText("Lege die Zugangsdaten zum lokalen SQL-Server fest");
        initiateCredentials.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JDialog eingabeDialog = new JDialog();
                eingabeDialog.setLayout(new GridLayout (4,2));
                eingabeDialog.setSize(500, 170);
                eingabeDialog.setTitle("Verbindungsdaten");
                eingabeDialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
                JButton acceptButton = new JButton("Übernehme Nutzderdaten");

                //"jdbc:postgresql://localhost:5432/IntegBridge", "postgres", "P1m7s4i2u4u2a.,-"
                JLabel urlLabel = new JLabel("Url:");
                JLabel userLabel = new JLabel("User:");
                JLabel passwordLabel = new JLabel("Password:");
                JTextField urlEingabe = new JTextField(url,20);
                JTextField userEingabe = new JTextField(user,20);
                JTextField passwordEingabe = new JTextField(password,20);

                acceptButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setCredentials(urlEingabe, userEingabe, passwordEingabe, eingabeDialog);
                    }
                });

                eingabeDialog.add(urlLabel);
                eingabeDialog.add(urlEingabe);
                eingabeDialog.add(userLabel);
                eingabeDialog.add(userEingabe);
                eingabeDialog.add(passwordLabel);
                eingabeDialog.add(passwordEingabe);
                eingabeDialog.add(acceptButton);
                eingabeDialog.setVisible(true);
            }
        });
        menuBar.add(dataBaseConnection);
        dataBaseConnection.add(initiateCredentials);

        JMenuItem initiateConnection = new JMenuItem("Verbindung herstellen");
        initiateConnection.setMnemonic('V');
        initiateConnection.setToolTipText("Stellt die Verbindung zum lokalen PostgreSQL-Datenbankserver her, " +
                "falls die Zugangsdaten korrekt sind.");
        initiateConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initiateConnection();
            }
        });
        dataBaseConnection.add(initiateConnection);

        transmitIfc = new JMenuItem("Übermittle Ifc-Daten");
        transmitIfc.setEnabled(true);
        transmitIfc.setToolTipText("Schreibt alle vorhandenen Bauteile in die Datenbank, für jedes IfcObjekt wird ein Eintrag erstellt");
        transmitIfc.setMnemonic('I');



        transmitIfc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmConnectionAndFile();
            }
        });

        dataBaseConnection.add(transmitIfc);

        JMenu infoMenu = new JMenu("Info");
        infoMenu.setMnemonic('I');
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setMnemonic('A');
        aboutMenuItem.setToolTipText("Wer ist denn für dieses geile Projekt überhaupt verantwortlich?");
//		aboutMenuItem.setIcon(new ImageIcon(new ImageIcon(aboutImageUrl)
//				.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        aboutMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GUI.this, new String(
                                "IntegBridge ÖkoBilanzierung\nPrototyp\n\nCopyright: Karslruhere Institut für Technologie\nVAKA\nManuel Görthofer, Tim Zinke"),
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        infoMenu.add(aboutMenuItem);
        menuBar.add(infoMenu);
        return menuBar;
    }



    private void confirmConnectionAndFile () {
        if (connectionState == 0 && fileLoadedState == 0) {
            JOptionPane.showMessageDialog(null, "Lade zunächst eine Ifc-Datei und verbinde dich mit dem lokalen Datenbankserver");
        } else if (connectionState == 0 && fileLoadedState == 1) {
            JOptionPane.showMessageDialog(null, "Verbinde dich mit dem lokalen Datenbankserver");
        } else if (connectionState == 1 && fileLoadedState == 0) {
            JOptionPane.showMessageDialog(null, "Lade eine Ifc-Datei");
        } else if (connectionState == 1 && fileLoadedState == 1) {
            JOptionPane.showMessageDialog(null, "Gratuliere, das Programm tut was es soll!");
            try {
                IfcParse ifcParse = new IfcParse(sqlConnection, ifcModel);
                ifcParse.createProperty_ObjectMap();
                Bauteilliste bauteilliste = new Bauteilliste(ifcParse.getIfcObject_IfcPropertySingleValue_Map());
                new GUI_ParameterSetting(this, bauteilliste, sqlConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setCredentials(JTextField urlEingabe, JTextField userEingabe, JTextField passwordEingabe, JDialog eingabeDialog) {
        url = urlEingabe.getText();
        user = userEingabe.getText();
        password = passwordEingabe.getText();
        sqlConnection.setCredentials(url, user, password);
        eingabeDialog.dispose();
    }

    private void initiateConnection(){
        sqlConnection.con = sqlConnection.getConnectionToPostGres();
        connectionState = sqlConnection.state;
        if (connectionState == 0) {
            JOptionPane.showMessageDialog(null, "Verbindung konnte nicht hergestellt werden!", "Verbindung", 0);
        } else if (connectionState == 1){
            JOptionPane.showMessageDialog(null, "Verbindung hergestellt!", "Verbindung", 1);
        }
    }

    private void loadFile() {

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION)
        {

            final File file = fileChooser.getSelectedFile();
            ifcModel.addStepParserProgressListener(new StepParserProgressListener()
            {
                @Override
                public void progressActionPerformed(final ProgressEvent event) {
                    progressBar.setValue((int) event.getCurrentState());
                    progressBar.setStringPainted(true);
                    progressBar.setString(event.getMessage());
                }
            });

            new Thread(new Runnable()
            {

                @Override
                public void run() {
                    long startTime = System.currentTimeMillis();
                    try {
                        if(file.getAbsolutePath().endsWith("ifc"))
                        {ifcModel.readStepFile(file);
                            fileLoadedState = 1;}
                        else
                        {ifcModel.readZipFile(file);
                            fileLoadedState = 1;}
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(GUI.this,
                                e.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    long endTime = System.currentTimeMillis();
                    progressBar.setValue(0);
                    progressBar.setStringPainted(false);
                    treeView.setIfcModel(ifcModel);
                    saveItem.setEnabled(false);
                    String[] info = { "*************************\n", "Dateiname:\n",
                            file.getName() + "\n\n", "Ifc-Datei erfolgreich geladen\n",
                            "\n\n",
                            "Die Ifc-Daten können nun an die Datenbank zur Weiterverarbeitung übermittelt werden" + "\n",
                            "*************************\n\n" };
                    String[] initStyles = { "bold", "bold", "regular", "bold",
                            "regular", "bold", "regular", "bold" };
                    StyledDocument doc = infoPane.getStyledDocument();
                    try {
                        for (int i = info.length - 1; i >= 0; i--) {
                            doc.insertString(0, info[i],
                                    doc.getStyle(initStyles[i]));
                        }
                    }
                    catch (BadLocationException ble) {
                        System.err
                                .println("Couldn't insert initial text into text pane.");
                    }
                }
            }).start();
        }
    }



    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(GUI.this) == JFileChooser.APPROVE_OPTION)
        {

            final File file = fileChooser.getSelectedFile();
            ifcModel.addStepParserProgressListener(new StepParserProgressListener()
            {
                @Override
                public void progressActionPerformed(final ProgressEvent event) {
                    progressBar.setValue((int) event.getCurrentState());
                    progressBar.setStringPainted(true);
                    progressBar.setString(event.getMessage());
                }
            });

            new Thread(new Runnable()
            {

                @Override
                public void run() {
                    long startTime = System.currentTimeMillis();
                    try {
                        ifcModel.writeStepFile(file);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.currentTimeMillis();
                    progressBar.setValue(0);
                    GUI.this.setTitle(file.getName());
                    progressBar.setStringPainted(false);
                    treeView.setIfcModel(ifcModel);
                    String[] info = { "*************************\n", "Dateiname:\n",
                            file.getName() + "\n\n", "Saving time:\n",
                            (endTime - startTime) / 1000 + " seconds\n\n",
                            "Number of Objects:\n",
                            "" + ifcModel.getObjects().size() + "\n",
                            "*************************\n\n" };
                    String[] initStyles = { "bold", "bold", "regular", "bold",
                            "regular", "bold", "regular", "bold" };
                    StyledDocument doc = infoPane.getStyledDocument();
                    try {
                        for (int i = info.length - 1; i >= 0; i--) {
                            doc.insertString(0, info[i],
                                    doc.getStyle(initStyles[i]));
                        }
                    }
                    catch (BadLocationException ble) {
                        System.err
                                .println("Couldn't insert initial text into text pane.");
                    }
                }
            }).start();
        }
    }

    protected void addStylesToDocument(StyledDocument doc) {
        Style def = StyleContext.getDefaultStyleContext().getStyle(
                StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);
    }



}




