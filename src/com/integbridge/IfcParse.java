package com.integbridge;

import com.apstex.ifc2x3toolbox.ifc2x3.*;
import com.apstex.ifc2x3toolbox.ifcmodel.IfcModel;

import java.util.*;

/**
 * Beim Erstellen der Klasse wird eine Datenbankverbindung und ein IfcModel übergeben.
 * Die Methode createProperty_ObjectMap() erzeugt intern eine Map mit allen IfcObjekten und ihren verknüpften Eigenschaften.
 * Die Mehtode transmitToDB() schreibt die IfcObjekte in die Datenbank.
 *
 */
public class IfcParse {
    private SQLConnection sqlConnection;
    private IfcModel ifcModel;
    private Collection<IfcObject> ifcObjects;
    private HashMap<String, HashSet<IfcPropertySingleValue>> ifcObject_IfcPropertySingleValue_Map;
    private String tableName = "Bauteile";


    private HashMap<String, HashSet<IfcPhysicalSimpleQuantity>> object_QuantityMap;
    // private Collection<IfcTypeObject> ifcobjectTypes;
    //  private HashMap<String, HashSet<IfcPropertySetDefinition>> objectType_propertySetMap;

    /**
     * Der Konstruktor benötigt die Verbindung zum lokalen SQL-Server mit Hilfe des SQLConnection-Objektes und ein Ifc-Model,
     * dessen Daten ausgelesen unf übertragen werden sollen. Mit der Methode transmitToDB können alle Objekte mit ihren respektiven Properties in
     * eine SQL-Datenbank per JDBC übermittelt werden. Mit der Methode createObject_PropertyMap werden alle IfcObjekte aus der Ifc_datei ausgelesen un
     * in der privaten HashMap @ifcObject_IfcPropertySingleValue_Map gespeichert.
     *
     * @param sqlConnection Verbindung zum lokalen SQL-Server
     * @param ifcModel Ifc-Modell-Objekt
     * @throws Exception
     */
    public IfcParse(SQLConnection sqlConnection, IfcModel ifcModel) throws Exception {
        this.sqlConnection = sqlConnection;
        this.ifcModel = ifcModel;
        sqlConnection.con = sqlConnection.getConnectionToPostGres();
        createProperty_ObjectMap();
    }

    public void transmitToDB() throws Exception {

        //This is the root Entity
        //IfcProject ifcRoot = ifcModel.getIfcProject();
        //System.out.println(ifcRoot.getStepLine());
        /*
        System.out.println(ifcRoot.getClass().getÜbergeordnetes_StepBauteil());
        System.out.println(ifcRoot.getClass().getCanonicalName());
        System.out.println(ifcRoot.getClass().getPackage().getÜbergeordnetes_StepBauteil());
        System.out.println(ifcRoot.getClass().getÜbergeordnetes_StepBauteil().replace(ifcRoot.getClass().getPackage().getÜbergeordnetes_StepBauteil() + ".Ifc", ""));
        */
        //This is a collection with all instances of IFC Entities in the model
        /*Collection<IfcObject> ifcObjects = ifcModel.getCollection(IfcObject.class);
        for (IfcObject obj : ifcObjects) {
            if (obj.getÜbergeordnetes_StepBauteil() != null) System.out.println((obj.getÜbergeordnetes_StepBauteil().getDecodedValue()));
        }*/
        /*for (ClassInterface I : ifcObjects) {
            System.out.println(I.getStepLineNumber());
        */

        // Hier wird der Zusammenhang zwischen ObjektTyp und Objekt dargestellt
        //ifcobjectTypes = ifcModel.getCollection(IfcTypeObject.class);
        /*for (IfcTypeObject objType : ifcobjectTypes) {
            if (objType.getÜbergeordnetes_StepBauteil() != null);

            for (IfcRelDefinesByType ifcRelDefinesByType : objType.getObjectTypeOf_Inverse())  {
                System.out.println(ifcRelDefinesByType.getRelatingType().getClassName() + " is der Typ von:  " + ifcRelDefinesByType.getRelatedObjects());
            }
        }*/

        //Hier werden die einzelnen PropertySets den ObjektTypen zugeordnet
        /*objectType_propertySetMap = new HashMap<>();

        for (IfcTypeObject objType : ifcobjectTypes) { //gehe alle ObjektTypen durch
            if (objType.getÜbergeordnetes_StepBauteil() != null) ;
            String key = objType.getClassName() + objType.getStepLineNumber();

            for (IfcPropertySetDefinition propertySet : objType.getHasPropertySets()) {//gehe alle PropertySetDefinitionen durch
                if (!objectType_propertySetMap.keySet().contains(key)) {
                    objectType_propertySetMap.put(key, new HashSet<IfcPropertySetDefinition>()); //Erstelle Map mit ObjektTyp und PropertysetMap
                }
                objectType_propertySetMap.get(key).add(propertySet); //Füge die PropertySets dem jeweiligen ObjektTyp hinzu
            }
        }
        */

        createTable();
        createColumns();
        fillColumns();
    }

    /**Es wird eine Tabelle mit dem String tableName erstellt.
     *
     * @throws Exception
     */
    private void createTable() throws Exception {
        sqlConnection.createTable(sqlConnection.con, tableName);
    }

    /**Diese Methode schreibt alle AttributsNamen/SingleProperyValues als Spalten in die Tabelle die mit der Methode
     * createTable() erstellt wurde.
     *
     * @throws Exception
     */
    private void createColumns() throws Exception {
        sqlConnection.addColumnPrimaryKey(sqlConnection.con, tableName, "Step-Bauteil");
        for (String element : ifcObject_IfcPropertySingleValue_Map.keySet()) {
            for (IfcPropertySingleValue attribut : ifcObject_IfcPropertySingleValue_Map.get(element)) {
                if (!attribut.getNominalValue().toString().isBlank() && !attribut.getNominalValue().toString().contentEquals("0") && !attribut.getNominalValue().toString().contentEquals("0.0")) { //Erstellt den Eintrag nur,wenn nicht leer oder WhiteSpace enthalten
                    sqlConnection.addColumn(sqlConnection.con, tableName, attribut.getName().toString());
                }
            }
        }
    }

    /**In diesem Schritt werden die Spalten jedes Bauteils mit respektiven Eigenschaften gefüllt. Ist kein Wert vorhanden
     * wird ein Null-Wert eingetragen.
     *
     * @throws Exception
     */
    private void fillColumns() throws Exception {
        for (String element : ifcObject_IfcPropertySingleValue_Map.keySet()) {
            sqlConnection.insertValue(sqlConnection.con, tableName, "Step-Bauteil", element);
            for (IfcPropertySingleValue attribut : ifcObject_IfcPropertySingleValue_Map.get(element)) {
                if (!attribut.getNominalValue().toString().isBlank() && !attribut.getNominalValue().toString().contentEquals("0") && !attribut.getNominalValue().toString().contentEquals("0.0")) { //Erstellt den Eintrag nur wenn nicht leer oder WhiteSpace enthalten
                    sqlConnection.updateValues(sqlConnection.con, tableName, attribut.getName().toString(), attribut.getNominalValue().toString(), "Step-Bauteil", element);
                }
            }
        }
    }

    public void createProperty_ObjectMap () {
        ifcObjects = ifcModel.getCollection(IfcObject.class);  //Alle Ifc-Objekte

        // In diesen HashMaps werden alle Objekte gespeichert und mit all Ihren Eigenschaften gemapped
        ifcObject_IfcPropertySingleValue_Map = new HashMap<>();
        object_QuantityMap = new HashMap<>();

        for (IfcObject obj : ifcObjects) {  //Gehe Alle Objekte durch


            String key = obj.getClassName() + obj.getStepLineNumber(); //Erstelle einen key für dieses Objekt mit seinem Namen und seiner SetpLineNumber
            if (obj.getIsDefinedBy_Inverse() != null) { //Überprüfe ob das Objekt Relationen besitzt
                for (IfcRelDefines relation : obj.getIsDefinedBy_Inverse()) { //Gehe jede Relation des Objekts durch

                    if (relation instanceof IfcRelDefinesByType) {  //Falls Relation eine RelDefinesByType ist
                        IfcRelDefinesByType relDefinesByType = (IfcRelDefinesByType) relation; //Caste die Relation in RelDefinesByType
                        if (relDefinesByType.getRelatingType() != null) { //
                            IfcTypeObject typeObject = relDefinesByType.getRelatingType(); //Hole den ObjektTypen des aktuellen Objekts

                            for (IfcPropertySetDefinition propertySetDef : typeObject.getHasPropertySets()) {//gehe alle PropertySetDefinitionen durch
                                if (propertySetDef instanceof IfcPropertySet) {
                                    IfcPropertySet propset = (IfcPropertySet) propertySetDef;
                                    for (IfcProperty property : propset.getHasProperties()) {  //Gehe alle Properties des PropertySets durch
                                        IfcPropertySingleValue propertySingleValue = (IfcPropertySingleValue) property;
                                        if (!ifcObject_IfcPropertySingleValue_Map.keySet().contains(key)) {
                                            ifcObject_IfcPropertySingleValue_Map.put(key, new HashSet<IfcPropertySingleValue>()); //Erstelle Map mit Objekt und PropertysetMap
                                        }
                                        ifcObject_IfcPropertySingleValue_Map.get(key).add(propertySingleValue); //Füge die PropertySets dem jeweiligen Objekt hinzu
                                    }
                                }
                            }
                        }
                    }

                    else if (relation instanceof IfcRelDefinesByProperties) {    //Falls Relation eine RelDefinesbyProperties Entität ist
                        IfcRelDefinesByProperties relDefinesByProperties = (IfcRelDefinesByProperties) relation;
                        IfcPropertySetDefinition propertySetDef = relDefinesByProperties.getRelatingPropertyDefinition(); //Hole die PropertySetDefinition

                        if (propertySetDef instanceof  IfcPropertySet) {
                            IfcPropertySet propset = (IfcPropertySet) propertySetDef; //caste Diese in PropertySet

                            for (IfcProperty property : propset.getHasProperties()) {  //Gehe alle Properties des PropertySets durch
                                IfcPropertySingleValue propertySingleValue = (IfcPropertySingleValue) property;
                                if (!ifcObject_IfcPropertySingleValue_Map.keySet().contains(key)) {
                                    ifcObject_IfcPropertySingleValue_Map.put(key, new HashSet<IfcPropertySingleValue>()); //Erstelle Map mit Objekt und PropertysetMap
                                }
                                ifcObject_IfcPropertySingleValue_Map.get(key).add(propertySingleValue); //Füge den Propertyvalue dem aktuellen Objekt hinzu
                            }
                        }
                        // Im folgenden findet die Zuweisung der Quantities zu den Objekten und die Speicherung in der QuantityHashmap statt.

                            /* if (propertySetDef instanceof IfcElementQuantity) {    // Hier wird geschaut ob QuantityProperties vorliegen
                                IfcElementQuantity quantitySet = (IfcElementQuantity) propertySetDef;
                                for (IfcPhysicalQuantity quantity : quantitySet.getQuantities()) {  //Hier holen wir nun von den ElementQuantities jedes einzelne PhysicalQuantity
                                    if (quantity instanceof IfcPhysicalSimpleQuantity) {
                                        IfcPhysicalSimpleQuantity physicalSimpleQuantity = (IfcPhysicalSimpleQuantity) quantity;
                                        if (!object_QuantityMap.keySet().contains((key))) {
                                            object_QuantityMap.put(key, new HashSet<IfcPhysicalSimpleQuantity>()); // Erstelle Map mit Objekt und QuantityMap
                                        }
                                        object_QuantityMap.get(key).add(physicalSimpleQuantity); //Füge die Quantities dem jeweiligen Objekt hinzu
                                    }
                                }
                            }*/
                    }
                }
            }
        }
    }

    public HashMap<String, HashSet<IfcPropertySingleValue>> getIfcObject_IfcPropertySingleValue_Map() {
        return ifcObject_IfcPropertySingleValue_Map;
    }
}
