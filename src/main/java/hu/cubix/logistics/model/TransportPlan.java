package hu.cubix.logistics.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class TransportPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long expectedIncome;

    @OneToMany(mappedBy = "transportPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections;

    public TransportPlan(long expectedIncome, List<Section> sections) {
        this.expectedIncome = expectedIncome;
        this.sections = new ArrayList<>();

        for (Section section : sections) {
            section.setTransportPlan(this);
            section.setOrder(this.sections.size()+1);
            this.sections.add(section);
        }
    }

    public TransportPlan() {
    }
}
