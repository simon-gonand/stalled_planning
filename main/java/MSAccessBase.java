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
    }


    /*
     *Connection à la base
     *@return : true si la connexion est réussie, false si échouée
     */
    public boolean connect() {
        try {
            // Chargement du driver ODBC
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Connexion à la base
            String connectionString = "jdbc:ucanaccess://" + path;
            connection = DriverManager.getConnection(connectionString);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Problème avec le driver ODBC");
            return false;
        }
        catch (SQLException e) {
            System.out.println("Impossible de se connecter à la base " + e.getErrorCode() + " " + e.getMessage());
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
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
            return result;
        }
        catch (SQLException e) {
            System.out.println("Select error: " + e.getMessage());
            statement.close();
            return null;
        }
    }

    /*
     *Envoi d'une requête de mise à jour (insert, update, delete)
     *@param : sql
     */
    public void SQLUpdate(String sql) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            statement.close();
            System.out.println("Update error " + e.getMessage());
        }
    }
}
