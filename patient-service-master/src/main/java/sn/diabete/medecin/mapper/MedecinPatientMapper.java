package sn.diabete.medecin.mapper;

import org.mapstruct.*;
import sn.diabete.medecin.entity.MedecinPatient;
import sn.diabete.medecin.dto.MedecinPatientDTO;
import sn.diabete.medecin.dto.CreateAttributionRequest;

@Mapper(componentModel = "spring")
public interface MedecinPatientMapper {

    MedecinPatientDTO toDto(MedecinPatient entity);

    MedecinPatient toEntity(MedecinPatientDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateAttribution", expression = "java(java.time.LocalDate.now())")
    MedecinPatient fromCreateRequest(CreateAttributionRequest req);
}
