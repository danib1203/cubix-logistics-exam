package hu.cubix.logistics.service;

import hu.cubix.logistics.model.*;
import hu.cubix.logistics.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class InitDbService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    MilestoneRepository milestoneRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    TransportPlanRepository transportPlanRepository;
    @Autowired
    LogisticsUserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void insertTestData() {
        Address address1 = new Address("hu", "Bekecsaba", "Fo utca", 5600, 1, 123, 321);
        Address address2 = new Address("hu", "Bekecsaba", "Fo utca", 5600, 1, 123, 321);
        addressRepository.save(address1);
        addressRepository.save(address2);

        Milestone milestone1 = new Milestone(address1, LocalDateTime.now());
        Milestone milestone2 = new Milestone(address2, LocalDateTime.now().plusDays(1));
        Milestone milestone3 = new Milestone(address2, LocalDateTime.now().plusDays(2));
        Milestone milestone4 = new Milestone(address2, LocalDateTime.now().plusDays(3));
        Milestone milestone5 = new Milestone(address2, LocalDateTime.now().plusDays(4));
        Milestone milestone6 = new Milestone(address2, LocalDateTime.now().plusDays(5));
        List<Milestone> milestones = Arrays.asList(
                milestone1, milestone2, milestone3, milestone4, milestone5, milestone6);
        milestoneRepository.saveAll(milestones);

        Section section1 = new Section(milestone1, milestone2);
        Section section2 = new Section(milestone3, milestone4);
        Section section3 = new Section(milestone5, milestone6);

        List<Section> sections1 = Arrays.asList(section1, section2);
        TransportPlan transportPlan1 = new TransportPlan(6300, sections1);

        List<Section> sections2 = List.of(section3);
        TransportPlan transportPlan2 = new TransportPlan(14300, sections2);

        transportPlanRepository.save(transportPlan1);
        transportPlanRepository.save(transportPlan2);
    }

    public void clearDB() {
        transportPlanRepository.deleteAll();
        sectionRepository.deleteAll();
        milestoneRepository.deleteAll();
        addressRepository.deleteAll();
    }

    public void insertTestUsers() {
        userRepository.save(new LogisticsUser("AddressManager", passwordEncoder.encode("pass"),
                Set.of("AddressManager")));
        userRepository.save(new LogisticsUser("TransportManager", passwordEncoder.encode("pass"),
                Set.of("TransportManager")));

    }
}
