package sn.diabete.medecin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "medecins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    @Column(nullable = false, unique = true, updatable = false)
    private String numeroProfessionnel;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexe sexe;

    private String specialite;

    private String adresse;

    private String ville;

    private String region;

    private LocalDate dateEnregistrement = LocalDate.now();

    @PrePersist
    public void prePersist() {
        if (dateEnregistrement == null) {
            dateEnregistrement = LocalDate.now();
        }
    }

}
