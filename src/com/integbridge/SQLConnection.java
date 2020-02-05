package com.integbridge;

import java.sql.*;

import static com.integbridge.Enums.getWbTemplate;


public class SQLConnection {
    public Connection con = null;
    public String url = "jdbc:postgresql://localhost:5433/integbridge";
    public String user = "postgres";
    public String password = "integbridge";
    public int state;


    public SQLConnection () {}

    /** Diese Methode erzeugt eine Tabelle mit dem Namen "tableName" (falls diese noch nicht existiert)
     * in der SQL-Datenbank aus der SQL-Verbindung "con".
     *
     * @param connection Connection: Verbindung zu einer SQL-Datenbank
     * @param tableName String: Name der zu erstellenden Tabelle
     * @throws Exception
     */
    public void createTable(Connection connection, String tableName) throws Exception{
        try{
            PreparedStatement create = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName +"();");
            create.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Mit der folgenden Methode wird in die bestehende Tabelle eine Spalte hinzugefügt
     * @param tableName Name der Tabelle
     * @param columnName Name der hinzuzufügenden Spalte
     * @param connection die Verbindung zum Server
     * */
    public void addColumn(Connection connection, String tableName, String columnName) throws Exception{
        try{
            PreparedStatement alter = connection.prepareStatement
                    ("ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + "\"" + columnName + "\"" + " VARCHAR(150);"
                    );
            alter.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Mit der folgenden Methode wird in die bestehende Tabelle eine Spalte hinzugefügt
     * @param tableName Name der Tabelle
     * @param columnName Name der hinzuzufügenden Spalte
     * @param connection die Verbindung zum Server
     * */
    public void addColumnPrimaryKey(Connection connection, String tableName, String columnName) {
        try{
            PreparedStatement alter = connection.prepareStatement
                    ("ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + "\"" + columnName + "\"" + " VARCHAR(100);"
                    );
            alter.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Fügt in die eingegebene Tabelle in die jeweilige Spalte den respektiven Wert ein.
     * @param tableName Tabelle
     * @param column Spalte
     * @param Value Weert
     */
    public void insertValue(Connection connection, String tableName, String column, String Value) {
        try{
            PreparedStatement insert = connection.prepareStatement
                    ("INSERT INTO " + tableName + " ("+"\"" +column+"\"" +") " + "VALUES " + "('"+Value+"')" + " ON CONFLICT DO NOTHING"
                    );
            insert.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Fügt in die eingegebene Tabelle in die jeweilige Spalte den respektiven Wert ein in die Reihe mit dem Wert des keys.
     * @param tableName Tabelle
     * @param column Spalte
     * @param value Weert
     * @param key Abfrage nach einer bestimmten Reihe
     * @param keyValue Wenn der Key diesen Wert hat dann wird in diese Reihe der neue Attributweert hinzugefügt
     */
    public void updateValues(Connection connection, String tableName, String column, String value, String key, String keyValue) {
        try{
            PreparedStatement insert = connection.prepareStatement
                    ("UPDATE " + tableName + " \n" +
                            "SET "   + "\""+column+"\"" + " = " +  "'" + value + "'" + "\n" +
                            "WHERE "  + "\""+key+"\"" + " = " + "'" + keyValue + "'"
                    );
            insert.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This Method will execute a Query looking for the Wirkungsbilanz of the input ID
     * @param id: Id des Feinelements/Postion für die Wirkungsbilanzquery als integer
     * */
    public Double[] queryWbint(int id) {
        Double [] wb_aus_db = getWbTemplate().clone();
        try {
            String [] wirkungsindikator = {"gwp", "odp", "pocp","ap","ep","adpe", "adpf", "pene", "pe"};
            for(int i=0; i<9;i++) {
                PreparedStatement query = this.con.prepareStatement("SELECT (" + wirkungsindikator[i] + ").zahl FROM \"Ressourcen\".wirkungsbilanz WHERE id = " + id + ";");
                //queryWbint.setInt(1,2);
                ResultSet rs = query.executeQuery();

                while (rs.next()) {
                    Double zahl = rs.getBigDecimal("zahl").doubleValue();
                    wb_aus_db[i] = zahl;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return wb_aus_db;
    }

    /** This Method will execute a Query looking for the Wirkungsbilanz of the input ID
     * @param id: Id des Feinelements/Postion für die Wirkungsbilanzquery als String
     * */
    public Double[] queryWbString(String id) {
        Double [] wb_aus_db = getWbTemplate().clone();
        try {
            String [] wirkungsindikator = {"gwp", "odp", "pocp","ap","ep","adpe", "adpf", "pene", "pe"};
            for(int i=0; i<9;i++) {
                PreparedStatement query = this.con.prepareStatement("SELECT (" + wirkungsindikator[i] + ").zahl FROM \"Ressourcen\".wirkungsbilanz WHERE id = " + id + ";");
                //queryWbint.setInt(1,2);
                ResultSet rs = query.executeQuery();

                while (rs.next()) {
                    Double zahl = rs.getBigDecimal("zahl").doubleValue();
                    wb_aus_db[i] = zahl;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return wb_aus_db;
    }



    /**Diese Methode übernimmt und speichert lediglich die Userdaten, stellt aber noch KEINE Verbindung her
     */
    public void setCredentials (String url, String user, String password){
        this.url=url;
        this.user=user;
        this.password=password;
    }

    /** This method returns a Connection to a postgresSQLServer if successful,
     * otherwise it will  print a Connection Error to the Console
     * @author  Manu G
     * @version 1.0
     * @since 2019-07-12
     */
    public Connection getConnectionToPostGres(){

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            //"jdbc:postgresql://localhost:5433/integbridge", "postgres", "integbridge"
            if (conn != null) {
                state = 1;
                //System.out.println("Connected to the database!");
            } else {
                state = 0;
                //System.out.println("Failed to establish con!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
