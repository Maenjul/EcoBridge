package com.integbridge;

import com.treeergebnisbilanz.TreeTableMain;
import com.apstex.ifc2x3toolbox.ifcmodel.IfcModel;

import java.io.File;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws Exception{
        File file = new File(("D:\\Studium\\Master\\MasterArbeit\\Integbridge-Revit\\Integbridge 3.ifc"));
        IfcModel ifcModel = new IfcModel();
        ifcModel.readStepFile(file);
        SQLConnection sqlConnection = new SQLConnection();
        sqlConnection.setCredentials("jdbc:postgresql://localhost:5433/integbridge", "postgres", "integbridge");
        IfcParse ifcParse = new IfcParse(sqlConnection, ifcModel);
        ifcParse.createProperty_ObjectMap();
        Bauteilliste bauteilliste = new Bauteilliste(ifcParse.getIfcObject_IfcPropertySingleValue_Map());

        ErgebnisPopulation ende = new ErgebnisPopulation(bauteilliste,sqlConnection);
        ArrayList <GrobElement> letzteListe = ende.getGrobElementListe();


        new TreeTableMain(letzteListe).setVisible(true);

    }
}