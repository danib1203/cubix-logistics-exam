package hu.cubix.logistics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MilestoneDto {

    private Long id;
    private AddressDto address;
    private LocalDateTime plannedTime;

}
