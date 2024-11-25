package hu.cubix.logistics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SectionDto {

    private Long id;
    private TransportPlanDto transportPlan;
    private MilestoneDto startMilestone;
    private MilestoneDto endMilestone;
    private int order;

}
