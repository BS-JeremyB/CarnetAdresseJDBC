package be.bstorm;

import be.bstorm.Entities.Utilisateur;
import be.bstorm.Repositories.Impl.CrudRepositoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CrudRepositoryImpl connCrud = new CrudRepositoryImpl();
        String email;
        String nom;
        String choix;
        int id;

        do{

            System.out.println("Veuillez faire un choix : \n" +
                    "1. Ajouter un contact \n" +
                    "2. Modifier un contact \n" +
                    "3. Supprimer un contact\n" +
                    "4. Parcourir les contacts \n" +
                    "5. Afficher un contact \n" +
                    "6. Ajouter un lot de contacts \n" +
                    "7. Afficher un contact via son email \n" +
                    "0. Quitter");

            choix = sc.nextLine();

            switch (choix){
                case "1" :
                    System.out.println("Veuillez entrer un nom :");
                    nom = sc.nextLine();
                    System.out.println("Veuillez entrer un email : ");
                    email = sc.nextLine();

                    connCrud.create(new Utilisateur(nom,email));
                    break;
                case "2" :
                    System.out.println("Veuillez entrer l'indice du contact à modifier");
                    id = sc.nextInt();
                    System.out.println("Veuillez entrer un nom :");
                    sc.nextLine();
                    nom = sc.nextLine();
                    System.out.println("Veuillez entrer un email : ");
                    email = sc.nextLine();
                    connCrud.update(id,nom,email);
                    break;
                case "3" :
                    System.out.println("Veuillez entrer l'indice du contact à supprimer");
                    id = sc.nextInt();
                    sc.nextLine();
                    connCrud.delete(id);
                    break;
                case "4" :
                    List<Utilisateur> utilisateurs = connCrud.getALL();
                    utilisateurs.forEach(u-> System.out.println(u.toString()));
                    break;
                case "5" :
                    System.out.println("Veuillez entrer l'indice du contact");
                    id = sc.nextInt();
                    sc.nextLine();
                    Utilisateur utilisateur = connCrud.getById(id);
                    System.out.println(utilisateur.toString());
                    break;
                case "6" :
                    List<Utilisateur> lotUtilisateur = new ArrayList<>();
                    for(int i = 0; i < 3; i++){
                        System.out.println("Veuillez entrer le nom du contact N°"+(i+1));
                        nom = sc.nextLine();
                        System.out.println("Veuillez entrer le email du contact N°"+(i+1));
                        email= sc.nextLine();

                        lotUtilisateur.add(new Utilisateur(nom,email));
                    }

                    int[] rowAffected = connCrud.createBatch(lotUtilisateur);

                    System.out.println(Arrays.toString(rowAffected));

                    break;
                case "7" :
                    System.out.println("Veuillez entrer l'email du contact à trouver");
                    email = sc.nextLine();
                    utilisateur = connCrud.getByMail(email);
                    System.out.println(utilisateur.toString());
                    break;
                case "0" :
                    break;
                default:
                    System.out.println("Choix invalide");
                    break;
            }


        }while (!choix.equals("0"));

        System.out.println("Bonne journée.");
    }
}

