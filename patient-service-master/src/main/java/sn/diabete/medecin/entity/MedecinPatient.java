package sn.diabete.medecin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "medecin_patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedecinPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long medecinId; // ID du médecin

    @Column(nullable = false)
    private Long patientId; // ID du patient (provenant du microservice Patient)

    @Column(nullable = false)
    private LocalDate dateAttribution = LocalDate.now();
}
