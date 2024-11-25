package hu.cubix.logistics.service;

import hu.cubix.logistics.config.ExpectedIncomeDecreaseProperties;
import hu.cubix.logistics.dto.DelayDto;
import hu.cubix.logistics.model.Milestone;
import hu.cubix.logistics.model.Section;
import hu.cubix.logistics.model.TransportPlan;
import hu.cubix.logistics.repository.MilestoneRepository;
import hu.cubix.logistics.repository.TransportPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import hu.cubix.logistics.config.ExpectedIncomeDecreaseProperties.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class TransportPlanService {

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    private ExpectedIncomeDecreaseProperties config;

    @Transactional
    public TransportPlan registerDelay(DelayDto delayDto, long id) {
        TransportPlan transportPlan = findById(id);
        if (Objects.isNull(transportPlan))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No transport plan with id " + id);

        Milestone milestone = getMilestone(delayDto);
        Section section = findSectionByMilestone(milestone, transportPlan);
        boolean isStartMilestone = isStartMileStone(section, milestone);
        int delay = delayDto.getDelayInMinutes();

        if (isStartMilestone) {
            Milestone endMilestone = section.getEndMilestone();
            addDelay(endMilestone, delay);
            addDelay(milestone, delay);
        } else {
            List<Section> sections = transportPlan.getSections();
            int i = sections.indexOf(section);

            if (i == sections.size() - 1) {
                Milestone endMilestone = section.getEndMilestone();
                addDelay(endMilestone, delay);
            } else {
                Milestone startMilestone = sections.get(i + 1).getStartMilestone();
                addDelay(startMilestone, delay);
            }
        }
        return save(modifyExpectedIncome(delay, transportPlan));
    }


    @Transactional
    public TransportPlan create(TransportPlan transportPlan) {
        if (Objects.isNull(transportPlan)) {
            return null;
        }

        if (transportPlan.getId() != null && transportPlanRepository.existsById(transportPlan.getId())) {
            throw new IllegalArgumentException("TransportPlan with id " + transportPlan.getId() + " already " + "exists");
        }
        return save(transportPlan);
    }

    @Transactional
    public TransportPlan update(TransportPlan transportPlan) {
        if (Objects.isNull(transportPlan)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (findById(transportPlan.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "TransportPlan with id " + transportPlan.getId() + " not found");
        }
        return save(transportPlan);
    }

    @Transactional
    public List<TransportPlan> findAll() {
        return transportPlanRepository.findAll();
    }

    @Transactional
    public TransportPlan findById(long id) {
        return transportPlanRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(final long id) {
        transportPlanRepository.deleteById(id);
    }

    private TransportPlan save(TransportPlan transportPlan) {
        return transportPlanRepository.save(transportPlan);
    }

    private Milestone getMilestone(DelayDto delayDto) {
        Optional<Milestone> milestoneOptional =
                milestoneRepository.findById(delayDto.getMilestoneId());

        if (milestoneOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Milestone not found");
        }
        return milestoneOptional.get();
    }

    private Section findSectionByMilestone(Milestone milestone, TransportPlan transportPlan) {
        List<Section> sections = transportPlan.getSections();
        boolean isPartOfTransportPlan = sections.stream().anyMatch(t ->
                t.getEndMilestone().equals(milestone) || t.getStartMilestone().equals(milestone));
        if (!isPartOfTransportPlan) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Milestone is not part of " +
                    "the transport plan sections");
        }

        Optional<Section> optionalSection =
                sections.stream().filter(s ->
                                s.getStartMilestone().equals(milestone) ||
                                        s.getEndMilestone().equals(milestone))
                        .findFirst();
        return optionalSection.orElse(null);
    }

    private boolean isStartMileStone(Section section, Milestone milestone) {
        return section.getStartMilestone().equals(milestone);
    }

    private void addDelay(Milestone milestone, int delay) {
        LocalDateTime plannedTime = milestone.getPlannedTime();
        milestone.setPlannedTime(plannedTime.plusMinutes(delay));
    }

    private TransportPlan modifyExpectedIncome(int delay, TransportPlan transportPlan) {
        long expectedIncome = transportPlan.getExpectedIncome();
        TreeMap<Integer, Long> expectedIncomeDecreaseLimits =
                config.getExpectedIncomeDecrease();
        if (delay >= 120) {
            transportPlan.setExpectedIncome(expectedIncome * ((100 - expectedIncomeDecreaseLimits.get(120))) / 100);
        } else if (delay >= 60) {
            transportPlan.setExpectedIncome(expectedIncome * ((100 - expectedIncomeDecreaseLimits.get(60))) / 100);
        } else if (delay >= 30) {
            transportPlan.setExpectedIncome(expectedIncome * ((100 - expectedIncomeDecreaseLimits.get(30))) / 100);
        }
        return transportPlan;
    }

}
