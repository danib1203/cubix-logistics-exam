package hu.cubix.logistics.dto;

import hu.cubix.logistics.model.Section;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class TransportPlanDto {
    private Long id;
    private long expectedIncome;
    private List<SectionDto> sections;

}
