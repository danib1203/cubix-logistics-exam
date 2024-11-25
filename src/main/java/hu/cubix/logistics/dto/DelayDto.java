package hu.cubix.logistics.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DelayDto {
    @NotNull
    private Long milestoneId;
    @Positive
    private int delayInMinutes;
}
