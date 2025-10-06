package sn.diabete.medecin.dto;

import lombok.Data;
import sn.diabete.medecin.entity.Sexe;

import java.time.LocalDate;

@Data
public class MedecinRequest {
    private Long utilisateurId;
    private String prenom;
    private String nom;
    private String telephone;
    private LocalDate dateNaissance;
    private Sexe sexe;
    private String specialite;
    private String adresse;
    private String ville;
    private String region;
}
