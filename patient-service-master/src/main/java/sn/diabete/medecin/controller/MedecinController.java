package sn.diabete.medecin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.diabete.medecin.dto.MedecinRequest;
import sn.diabete.medecin.dto.MedecinResponse;
import sn.diabete.medecin.service.MedecinService;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@RequiredArgsConstructor
public class MedecinController {

    private final MedecinService medecinService;

    @PostMapping
    public ResponseEntity<MedecinResponse> createMedecin(@RequestBody MedecinRequest request) {
        MedecinResponse response = medecinService.createMedecin(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedecinResponse>> getAllMedecins() {
        return ResponseEntity.ok(medecinService.getAllMedecins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedecinResponse> getMedecinById(@PathVariable Long id) {
        return ResponseEntity.ok(medecinService.getMedecinById(id));
    }

    @GetMapping("/byUtilisateur/{utilisateurId}")
    public ResponseEntity<MedecinResponse> getMedecinByUtilisateurId(@PathVariable Long utilisateurId) {
        return ResponseEntity.ok(medecinService.getMedecinByUtilisateurId(utilisateurId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedecinResponse> updateMedecin(@PathVariable Long id, @RequestBody MedecinRequest request) {
        return ResponseEntity.ok(medecinService.updateMedecin(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedecin(@PathVariable Long id) {
        medecinService.deleteMedecin(id);
        return ResponseEntity.ok("Médecin supprimé avec succès.");
    }
}
