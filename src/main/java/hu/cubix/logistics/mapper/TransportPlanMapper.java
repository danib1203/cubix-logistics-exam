package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.SectionDto;
import hu.cubix.logistics.dto.TransportPlanDto;
import hu.cubix.logistics.model.Section;
import hu.cubix.logistics.model.TransportPlan;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

    @Mapping(target = "sections", expression = "java(mapSections(transportPlan.getSections()))")
    TransportPlanDto entityToDto(TransportPlan transportPlan);

    @Mapping(target = "sections", expression = "java(mapSections(transportPlan.getSections()))")
    List<TransportPlanDto> entitiesToDtos(List<TransportPlan> transportPlan);


    TransportPlan dtoToEntity(TransportPlanDto transportPlanDto);

    List<TransportPlan> dtosToEntities(List<TransportPlanDto> transportPlanDtos);

    @Mapping(target = "transportPlan", ignore = true)
    SectionDto mapSection(Section section);

    @IterableMapping(elementTargetType = SectionDto.class)
    List<SectionDto> mapSections(List<Section> sections);
}
