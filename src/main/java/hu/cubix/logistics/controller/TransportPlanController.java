package hu.cubix.logistics.controller;

import hu.cubix.logistics.dto.DelayDto;
import hu.cubix.logistics.dto.TransportPlanDto;
import hu.cubix.logistics.mapper.TransportPlanMapper;
import hu.cubix.logistics.model.TransportPlan;
import hu.cubix.logistics.service.TransportPlanService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @Autowired
    TransportPlanService transportPlanService;

    @Autowired
    TransportPlanMapper transportPlanMapper;

    @PostMapping("/{id}/delay")
    public TransportPlanDto registerDelay(@RequestBody @Valid DelayDto delayDto,
                                          @PathVariable long id) {
        if (Objects.isNull(transportPlanService.findById(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transport plan not found");
        }
        TransportPlan transportPlan = transportPlanService.registerDelay(delayDto, id);
        return transportPlanMapper.entityToDto(transportPlan);
    }

    @GetMapping
    @Transactional
    public List<TransportPlanDto> getTransportPlans() {
        return transportPlanMapper.entitiesToDtos(transportPlanService.findAll());
    }
}
