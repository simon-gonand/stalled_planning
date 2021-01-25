package com.Cale_Planning;

import com.mindfusion.common.DateTime;

import java.sql.*;

public class MSAccessBase {
    // Chemin de la base
    private String path;

    // Nom d'utilisateur
    private String user;

    // Mot de passe
    private String password;

    // Connection vers la base
    private Connection connection;


    /* Constructeur */
    public MSAccessBase(String path) {
        this.path = path;
        connect();
    }


    /*
     *Connection à la base
     *@return : true si la connexion est réussie, false si échouée
     */
    private boolean connect() {
        try {
            // Chargement du driver ODBC
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Connexion à la base
            String connectionString = "jdbc:ucanaccess://" + path;
            connection = DriverManager.getConnection(connectionString);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Problème avec le driver ODBC");
            return false;
        }
        catch (SQLException e) {
            System.err.println("Impossible de se connecter à la base " + e.getErrorCode() + " " + e.getMessage());
            return false;
        }
        return true;
    }


    /*
     *Déconnexion de la base
     *@return : true si la déconnexion est réussie, false sinon
     */
    public boolean disconnect() {
        try {
            connection.close();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    /*
     *Envoi d'une requête de sélection
     *@param : sql
     *@return : result
     */
    public ResultSet SQLSelect(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        return result;

    }

    /*
     *Envoi d'une requête de mise à jour (insert, update, delete)
     *@param : sql
     */
    public void SQLUpdate(String sql, Object... variables) throws SQLException {
    PreparedStatement pstmt = connection.prepareStatement(sql);
    for (int i = 0; i < variables.length; ++i){
        if (variables[i] instanceof String)
            pstmt.setString(i+1, (String) variables[i]);
        else if (variables[i] instanceof Float)
            pstmt.setFloat(i+1, (Float) variables[i]);
        else if (variables[i] instanceof Integer)
            pstmt.setInt(i+1, (Integer) variables[i]);
        else if (variables[i] instanceof java.util.Date) {
            Date date = new Date(((java.util.Date) variables[i]).getTime());
            pstmt.setDate(i + 1, date);
        }
        else if (variables[i] instanceof DateTime){
            DateTime dateTime = (DateTime) variables[i];
            pstmt.setDate(i + 1, new Date(dateTime.toJavaCalendar().getTimeInMillis()));
        }
        else if (variables[i] instanceof Boolean)
            pstmt.setBoolean(i+1, (Boolean) variables[i]);
    }
    int nrows = pstmt.executeUpdate();
    }
}

