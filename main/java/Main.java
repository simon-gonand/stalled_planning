import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        MSAccessBase database = new MSAccessBase("src/main/resources/Database.accdb");
        database.connect();
        try {
            ResultSet resultSet = database.SQLSelect("SELECT * FROM Adherent");
            while (resultSet.next()){
                System.out.println("---------------- Adherent n° " + resultSet.getInt("ID") + " ----------------");
                System.out.println(resultSet.getString("Genre") + " " + resultSet.getString("Prenom") + " " +
                        resultSet.getString("Nom"));
                System.out.println("Né le " + resultSet.getDate("Date_Naissance").toString());
                System.out.println("Adresse : " + resultSet.getString("Rue") + " " + resultSet.getInt("CodePostal") +
                        " " + resultSet.getString("Ville"));
                System.out.println("Email : " + resultSet.getString("Email"));
                System.out.println("Téléphone : " + resultSet.getString("Telephone"));
                System.out.println("Portable : " + resultSet.getString("Portable"));
                System.out.println("Admis en : " + resultSet.getString("Date_adhesion"));
                System.out.println("Commentaire : " + resultSet.getString("Com"));
                ResultSet bateauResult = database.SQLSelect("SELECT Nom FROM Bateau WHERE Proprietaire = " + resultSet.getString("ID"));
                bateauResult.next();
                System.out.println("Bateau : " + bateauResult.getString("Nom"));
                System.out.println();
            }
        } catch (SQLException e){
            System.out.println("Query exception n° " + e.getErrorCode() + " Message: " + e.getMessage());
        }
    }
}
