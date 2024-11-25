package hu.cubix.logistics.controller;

import hu.cubix.logistics.NoSecurityConfiguration;
import hu.cubix.logistics.dto.DelayDto;
import hu.cubix.logistics.dto.MilestoneDto;
import hu.cubix.logistics.dto.TransportPlanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Import(NoSecurityConfiguration.class)
public class TransportPlanControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Description("Test delay register to start milestone of section with a valid input that " +
            "equals a 5 percent decrease of expected income")
    void testDelayToStartMilestoneWithValidInputOfFivePercentDecrease() {
        TransportPlanDto transportPlanDto = getTransportPlans().get(0);
        MilestoneDto milestoneDtoStart = transportPlanDto.getSections().get(0).getStartMilestone();
        MilestoneDto milestoneDtoEnd = transportPlanDto.getSections().get(0).getEndMilestone();
        DelayDto delayDto = setupDelayDto(30, milestoneDtoStart);
        long expectedIncomeBeforeDelay = transportPlanDto.getExpectedIncome();
        LocalDateTime plannedTimeOfStartMilestoneBeforeDelay = milestoneDtoStart.getPlannedTime();
        LocalDateTime plannedTimeOfEndMilestoneBeforeDelay = milestoneDtoEnd.getPlannedTime();

        TransportPlanDto transportPlanAfterDelay = registerDelay(transportPlanDto.getId(),
                delayDto);
        long expectedIncomeAfterDelay = transportPlanAfterDelay.getExpectedIncome();
        MilestoneDto startMilestoneAfterDelay =
                transportPlanAfterDelay.getSections().get(0).getStartMilestone();
        MilestoneDto endMilestoneAfterDelay =
                transportPlanAfterDelay.getSections().get(0).getEndMilestone();
        LocalDateTime plannedTimeOfStartMilestoneAfterDelay =
                startMilestoneAfterDelay.getPlannedTime();
        LocalDateTime plannedTimeOfEndMilestoneAfterDelay = endMilestoneAfterDelay.getPlannedTime();

        assertThat((expectedIncomeBeforeDelay * ((100 - 5)) / 100)).isEqualTo(expectedIncomeAfterDelay);
        assertThat(plannedTimeOfStartMilestoneBeforeDelay.plusMinutes(delayDto.getDelayInMinutes()))
                .isCloseTo(plannedTimeOfStartMilestoneAfterDelay, within(1, ChronoUnit.MINUTES));
        assertThat(plannedTimeOfEndMilestoneBeforeDelay.plusMinutes(delayDto.getDelayInMinutes()))
                .isCloseTo(plannedTimeOfEndMilestoneAfterDelay, within(1, ChronoUnit.MINUTES));
    }

    @Test
    @Description("Test delay register to start milestone of section with a valid input that " +
            "equals a 10 percent decrease of expected income")
    void testDelayToStartMilestoneWithValidInputOfTenPercentDecrease() {
        TransportPlanDto transportPlanDto = getTransportPlans().get(0);
        MilestoneDto milestoneDtoStart = transportPlanDto.getSections().get(0).getStartMilestone();
        MilestoneDto milestoneDtoEnd = transportPlanDto.getSections().get(0).getEndMilestone();
        DelayDto delayDto = setupDelayDto(60, milestoneDtoStart);
        long expectedIncomeBeforeDelay = transportPlanDto.getExpectedIncome();
        LocalDateTime plannedTimeOfStartMilestoneBeforeDelay = milestoneDtoStart.getPlannedTime();
        LocalDateTime plannedTimeOfEndMilestoneBeforeDelay = milestoneDtoEnd.getPlannedTime();

        TransportPlanDto transportPlanAfterDelay = registerDelay(transportPlanDto.getId(),
                delayDto);
        long expectedIncomeAfterDelay = transportPlanAfterDelay.getExpectedIncome();
        MilestoneDto startMilestoneAfterDelay =
                transportPlanAfterDelay.getSections().get(0).getStartMilestone();
        MilestoneDto endMilestoneAfterDelay =
                transportPlanAfterDelay.getSections().get(0).getEndMilestone();
        LocalDateTime plannedTimeOfStartMilestoneAfterDelay =
                startMilestoneAfterDelay.getPlannedTime();
        LocalDateTime plannedTimeOfEndMilestoneAfterDelay = endMilestoneAfterDelay.getPlannedTime();

        assertThat((expectedIncomeBeforeDelay * ((100 - 10)) / 100)).isEqualTo(expectedIncomeAfterDelay);
        assertThat(plannedTimeOfStartMilestoneBeforeDelay.plusMinutes(delayDto.getDelayInMinutes()))
                .isCloseTo(plannedTimeOfStartMilestoneAfterDelay, within(1, ChronoUnit.MINUTES));
        assertThat(plannedTimeOfEndMilestoneBeforeDelay.plusMinutes(delayDto.getDelayInMinutes()))
                .isCloseTo(plannedTimeOfEndMilestoneAfterDelay, within(1, ChronoUnit.MINUTES));
    }

    @Test
    @Description("Test delay register to end milestone of section with a valid input that " +
            "equals a 5 percent decrease of expected income")
    void testDelayToEndMilestoneWithValidInputOfFivePercentDecrease() {
        TransportPlanDto transportPlanDto = getTransportPlans().get(0);
        MilestoneDto milestoneDtoStart = transportPlanDto.getSections().get(0).getStartMilestone();
        MilestoneDto milestoneDtoEnd = transportPlanDto.getSections().get(0).getEndMilestone();
        DelayDto delayDto = setupDelayDto(31, milestoneDtoEnd);
        long expectedIncomeBeforeDelay = transportPlanDto.getExpectedIncome();
        LocalDateTime plannedTimeOfStartMilestoneBeforeDelay = milestoneDtoStart.getPlannedTime();
        LocalDateTime plannedTimeOfEndMilestoneBeforeDelay = milestoneDtoEnd.getPlannedTime();

        TransportPlanDto transportPlanAfterDelay = registerDelay(transportPlanDto.getId(),
                delayDto);
        long expectedIncomeAfterDelay = transportPlanAfterDelay.getExpectedIncome();
        LocalDateTime plannedTimeOfStartMilestoneAfterDelay = milestoneDtoStart.getPlannedTime();
        LocalDateTime plannedTimeOfEndMilestoneAfterDelay = milestoneDtoEnd.getPlannedTime();

        assertThat((expectedIncomeBeforeDelay * ((100 - 5)) / 100)).isEqualTo(expectedIncomeAfterDelay);
        assertThat(plannedTimeOfStartMilestoneBeforeDelay.plusMinutes(delayDto.getDelayInMinutes()))
                .isNotEqualTo(plannedTimeOfStartMilestoneAfterDelay);
        assertThat(plannedTimeOfEndMilestoneBeforeDelay.plusMinutes(delayDto.getDelayInMinutes()))
                .isNotEqualTo(plannedTimeOfEndMilestoneAfterDelay);
    }

    @Test
    @Description("Test that invalid transportPlan id throws a 404 NOT_FOUND error")
    void testInvalidTransportPlanId() {
        long invalidTransportPlanId = -1;
        TransportPlanDto transportPlanDto = getTransportPlans().get(0);
        MilestoneDto milestoneDtoStart = transportPlanDto.getSections().get(0).getStartMilestone();
        DelayDto delayDto = setupDelayDto(10, milestoneDtoStart);

        HttpStatusCode statusOfRegisterDelay = getStatusOfRegisterDelay(invalidTransportPlanId,
                delayDto);

        assertThat(statusOfRegisterDelay).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Description("Test that invalid milestone id throws a 400 BAD_REQUEST error")
    void testInvalidMilestoneId() {
        TransportPlanDto transportPlanDto = getTransportPlans().get(0);
        long transportPlanId = transportPlanDto.getId();
        MilestoneDto milestoneDtoStart = transportPlanDto.getSections().get(0).getStartMilestone();
        milestoneDtoStart.setId(-1L);
        DelayDto delayDto = setupDelayDto(10, milestoneDtoStart);

        HttpStatusCode statusOfRegisterDelay = getStatusOfRegisterDelay(transportPlanId, delayDto);

        assertThat(statusOfRegisterDelay).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private List<TransportPlanDto> getTransportPlans() {
        return webTestClient.get().uri("/api/transportPlans")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransportPlanDto.class)
                .returnResult()
                .getResponseBody();
    }

    private HttpStatusCode getStatusOfRegisterDelay(long id, DelayDto delayDto) {
        return webTestClient.post().uri("/api/transportPlans/{id}/delay", id)
                .bodyValue(delayDto)
                .exchange()
                .expectBody(TransportPlanDto.class)
                .returnResult()
                .getStatus();
    }

    private TransportPlanDto registerDelay(long id, DelayDto delayDto) {
        return webTestClient.post().uri("/api/transportPlans/{id}/delay", id)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransportPlanDto.class)
                .returnResult()
                .getResponseBody();
    }

    private DelayDto setupDelayDto(int delayInMinutes, MilestoneDto milestone) {
        long milestoneId = milestone.getId();
        DelayDto delayDto = new DelayDto();
        delayDto.setMilestoneId(milestoneId);
        delayDto.setDelayInMinutes(delayInMinutes);
        return delayDto;
    }
}
