package be.bstorm.Repositories;

import be.bstorm.Entities.Utilisateur;

import java.util.List;

public interface CrudRepository {

    List<Utilisateur> getALL();
    Utilisateur getById(int id);
    void create(Utilisateur utilisateur);
    Utilisateur update(int id, String nom, String email);
    void delete(int id);
    int[] createBatch(List<Utilisateur> utilisateurs);
    Utilisateur getByMail(String mail);





}
