package sn.diabete.medecin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.diabete.medecin.dto.*;
import sn.diabete.medecin.service.MedecinPatientService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/attributions")
@RequiredArgsConstructor
public class MedecinPatientController {

    private final MedecinPatientService service;

    @PostMapping
    public ResponseEntity<MedecinPatientDTO> assignPatient(@Valid @RequestBody CreateAttributionRequest request) {
        MedecinPatientDTO dto = service.assignPatient(request);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/medecin/{medecinId}")
    public ResponseEntity<List<MedecinPatientDTO>> getPatients(@PathVariable Long medecinId) {
        return ResponseEntity.ok(service.getPatientsByMedecin(medecinId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedecinPatientDTO>> getMedecins(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getMedecinsByPatient(patientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribution(
            @PathVariable Long id,
            @RequestParam(required = false) Long requesterMedecinId,
            @RequestParam(required = false, defaultValue = "false") boolean isAdmin) {

        // plus tard on remplacera requesterMedecinId / isAdmin par des valeurs tirées du token JWT
        service.removeAttribution(id, requesterMedecinId, isAdmin);
        return ResponseEntity.noContent().build();
    }
}
