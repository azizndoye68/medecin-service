package sn.diabete.medecin.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedecinPatientDTO {
    private Long id;
    private Long medecinId;
    private Long patientId;
    private LocalDate dateAttribution;
}
