package sn.diabete.medecin.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAttributionRequest {
    @NotNull(message = "medecinId est requis")
    private Long medecinId;

    @NotNull(message = "patientId est requis")
    private Long patientId;
}
