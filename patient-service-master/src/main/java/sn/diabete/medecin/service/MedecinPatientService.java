package sn.diabete.medecin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.diabete.medecin.entity.MedecinPatient;
import sn.diabete.medecin.repository.MedecinPatientRepository;
import sn.diabete.medecin.dto.*;
import sn.diabete.medecin.mapper.MedecinPatientMapper;
import sn.diabete.medecin.exception.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedecinPatientService {

    private final MedecinPatientRepository repo;
    private final MedecinPatientMapper mapper; // si tu utilises MapStruct

    public MedecinPatientDTO assignPatient(CreateAttributionRequest req) {
        // check doublon
        repo.findByMedecinIdAndPatientId(req.getMedecinId(), req.getPatientId())
                .ifPresent(mp -> { throw new DuplicateResourceException("Ce patient est déjà attribué à ce médecin."); });

        MedecinPatient entity = mapper.fromCreateRequest(req);
        MedecinPatient saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public List<MedecinPatientDTO> getPatientsByMedecin(Long medecinId) {
        return repo.findByMedecinId(medecinId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MedecinPatientDTO> getMedecinsByPatient(Long patientId) {
        return repo.findByPatientId(patientId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void removeAttribution(Long id, Long requesterMedecinId, boolean isAdmin) {
        MedecinPatient mp = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attribution introuvable"));

        if (!isAdmin) {
            // si ce n'est pas un admin, vérifier que le medecin demandeur est bien le propriétaire
            if (!mp.getMedecinId().equals(requesterMedecinId)) {
                throw new UnauthorizedActionException("Vous ne pouvez pas supprimer cette attribution");
            }
        }

        repo.deleteById(id);
    }
}
