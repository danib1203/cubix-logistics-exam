package hu.cubix.logistics.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transport_plan_id", nullable = false)
    private TransportPlan transportPlan;

    @OneToOne
    @JoinColumn(name = "start_milestone_id", referencedColumnName = "id")
    private Milestone startMilestone;

    @OneToOne
    @JoinColumn(name = "end_milestone_id", referencedColumnName = "id")
    private Milestone endMilestone;

    @Column(name = "section_order", nullable = false)
    private int order;

    public Section(Milestone startMilestone, Milestone endMilestone) {
        this.startMilestone = startMilestone;
        this.endMilestone = endMilestone;
    }

    public Section() {

    }

    public void setTransportPlan(TransportPlan transportPlan) {
        this.transportPlan = transportPlan;
    }
}
