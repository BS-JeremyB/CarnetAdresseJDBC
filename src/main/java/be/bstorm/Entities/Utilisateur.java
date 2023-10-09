package be.bstorm.Entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString @EqualsAndHashCode
public class Utilisateur {
    public Utilisateur(String nom, String mail) {
        this.nom = nom;
        this.mail = mail;
    }

    private int id;
    private String nom;
    private  String mail;
}
