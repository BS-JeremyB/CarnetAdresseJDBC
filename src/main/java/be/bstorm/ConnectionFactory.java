package be.bstorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {

    //URL de connection à la base de donnée /!\ la base de donnée doit être déjà créé
    private final static String URL = "jdbc:postgresql://localhost:5432/CarnetAdresse";

    //User ("postgres" par défaut sur postgres)
    private final static String USER = "postgres";

    //Mot de passe de postgres (initié lors de l'installation)
    private final static String PASSWORD = "postgres";


    //Methode static qui renvoie une connection (session)
    public static Connection CreateConnection() throws SQLException{
        //utilisation de la méthode getConnection de Java.sql qui prends l'url, le user, le password
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
