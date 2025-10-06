package sn.diabete.medecin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.diabete.medecin.dto.MedecinRequest;
import sn.diabete.medecin.dto.MedecinResponse;
import sn.diabete.medecin.entity.Medecin;
import sn.diabete.medecin.exception.BadRequestException;
import sn.diabete.medecin.exception.ResourceNotFoundException;
import sn.diabete.medecin.mapper.MedecinMapper;
import sn.diabete.medecin.repository.MedecinRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedecinService {

    private final MedecinRepository medecinRepository;

    // 🔹 Créer un médecin
    public MedecinResponse createMedecin(MedecinRequest request) {
        System.out.println("Utilisateur ID reçu : " + request.getUtilisateurId());
        if (request.getUtilisateurId() == null) {
            throw new BadRequestException("UtilisateurId est obligatoire pour créer un médecin");
        }

        if (medecinRepository.existsByTelephone(request.getTelephone())) {
            throw new BadRequestException("Le téléphone " + request.getTelephone() + " est déjà utilisé.");
        }

        // Mapper les données
        Medecin medecin = MedecinMapper.INSTANCE.toEntity(request);
        medecin.setUtilisateurId(request.getUtilisateurId());

        // Générer numéro professionnel unique
        String numeroProfessionnel = generateNumeroProfessionnel();
        medecin.setNumeroProfessionnel(numeroProfessionnel);

        Medecin savedMedecin = medecinRepository.save(medecin);
        return MedecinMapper.INSTANCE.toDto(savedMedecin);
    }


    private String generateNumeroProfessionnel() {
        String date = LocalDate.now().toString().replace("-", "");
        String suffix = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "MED-" + date + "-" + suffix;
    }

    // 🔹 Récupérer tous les médecins
    public List<MedecinResponse> getAllMedecins() {
        List<Medecin> medecins = medecinRepository.findAll();
        return MedecinMapper.INSTANCE.toDtoList(medecins);
    }

    // 🔹 Récupérer un médecin par ID
    public MedecinResponse getMedecinById(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé avec l'ID : " + id));
        return MedecinMapper.INSTANCE.toDto(medecin);
    }

    // 🔹 Récupérer un médecin par utilisateurId (important pour login)
    public MedecinResponse getMedecinByUtilisateurId(Long utilisateurId) {
        Medecin medecin = medecinRepository.findByUtilisateurId(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Médecin non trouvé pour utilisateurId : " + utilisateurId));
        return MedecinMapper.INSTANCE.toDto(medecin);
    }

    // 🔹 Mettre à jour un médecin
    public MedecinResponse updateMedecin(Long id, MedecinRequest request) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé avec l'ID : " + id));

        if (!medecin.getTelephone().equals(request.getTelephone()) &&
                medecinRepository.existsByTelephone(request.getTelephone())) {
            throw new BadRequestException("Le téléphone " + request.getTelephone() + " est déjà utilisé.");
        }

        medecin.setNom(request.getNom());
        medecin.setPrenom(request.getPrenom());
        medecin.setDateNaissance(request.getDateNaissance());
        medecin.setSexe(request.getSexe());
        medecin.setSpecialite(request.getSpecialite());
        medecin.setTelephone(request.getTelephone());
        medecin.setAdresse(request.getAdresse());
        medecin.setVille(request.getVille());
        medecin.setRegion(request.getRegion());

        Medecin updatedMedecin = medecinRepository.save(medecin);
        return MedecinMapper.INSTANCE.toDto(updatedMedecin);
    }

    // 🔹 Supprimer un médecin
    public void deleteMedecin(Long id) {
        Medecin medecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin non trouvé avec l'ID : " + id));
        medecinRepository.delete(medecin);
    }
}
