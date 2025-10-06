package sn.diabete.medecin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.diabete.medecin.entity.MedecinPatient;

import java.util.List;
import java.util.Optional;

public interface MedecinPatientRepository extends JpaRepository<MedecinPatient, Long> {
    List<MedecinPatient> findByMedecinId(Long medecinId);
    List<MedecinPatient> findByPatientId(Long patientId);
    Optional<MedecinPatient> findByMedecinIdAndPatientId(Long medecinId, Long patientId);
}

