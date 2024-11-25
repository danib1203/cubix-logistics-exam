package hu.cubix.logistics.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Address address;

    private LocalDateTime plannedTime;


    public Milestone(Address address, LocalDateTime plannedTime) {
        this.address = address;
        this.plannedTime = plannedTime;
    }

    public Milestone() {
    }

}
