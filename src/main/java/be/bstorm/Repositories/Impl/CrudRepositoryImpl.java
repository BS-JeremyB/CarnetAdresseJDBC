package be.bstorm.Repositories.Impl;

import be.bstorm.ConnectionFactory;
import be.bstorm.Entities.Utilisateur;
import be.bstorm.Repositories.CrudRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrudRepositoryImpl implements CrudRepository {

    @Override
    public List<Utilisateur> getALL(){

        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateur";

        try (
             //Permet de créer une session qui se ferme grace au try-catch-ressources
             Connection connection = ConnectionFactory.CreateConnection();
             //Ouvre une déclaration vers le serveur (préparation au passage d'une query)
             Statement statement = connection.createStatement();
             //Envoi de la Query DRL qui récupère un jeu de données placé dans le ResultSet
             ResultSet resultSet = statement.executeQuery(query)) {

            //Next() indique si l'enregistrement suivant existe, nous permettant d'iterer
            //sur les données du ResultSet
            while (resultSet.next()) {

                //Transfère des valeurs récupérées vers des variables /!\ au respect du type
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");

                //Création d'un utilisateur et ajout dans une liste
                Utilisateur utilisateur = new Utilisateur(id, nom, email);
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            throw new RuntimeException("erreur de connexion",e);
        }

        // la liste est retournée au Main
        return utilisateurs;
    }

    @Override
    public Utilisateur getById(int id) {

        //PreparedStatement, la variable qui sera passée dans la query est
        //temporairement indiqué par ?
        String query = "SELECT * FROM utilisateur WHERE id = ?";
        Utilisateur utilisateur = null;

        try (Connection connection = ConnectionFactory.CreateConnection();
             //Déclaration du preparedStatement, la query est a passer ici !
             PreparedStatement statement = connection.prepareStatement(query)) {

            //Ajout du paramètre à passer dans la query à la place du "?"
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String email = resultSet.getString("email");
                    utilisateur = new Utilisateur(id, nom, email);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("erreur de connexion",e);
        }

        //renvoi d'un utilisateur vers le Main
        return utilisateur;
    }

    @Override
    public void create(Utilisateur utilisateur) {
        String query = "INSERT INTO utilisateur (nom, email) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.CreateConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //passage de deux paramètre, 1 est le premier "?", 2 est le second "?"
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getMail());

            //ExecuteUpdate quand on insère, update, delete une ou plusieur lignes (DML)
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("erreur de connexion",e);
        }
    }

    @Override
    public Utilisateur update(int id, String nom, String email) {
        String query = "UPDATE utilisateur SET nom = ?, email = ? WHERE id = ?";

        try (Connection connection = ConnectionFactory.CreateConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, email);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            //Si le nombre de ligne affectée est supérieur à 0 alors
            //utilisation de la methode getById pour récuperer l'utilisateur à jour
            //et l'envoyer vers le Main
            if (rowsAffected > 0) {
                return getById(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("erreur de connexion",e);
        }

        return null;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM utilisateur WHERE id = ?";

        try (Connection connection = ConnectionFactory.CreateConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("erreur de connexion",e);
        }
    }
}
