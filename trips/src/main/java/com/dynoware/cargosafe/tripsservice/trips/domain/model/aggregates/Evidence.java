package com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates;

import com.dynoware.cargosafe.tripsservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateEvidenceCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evidences")
public class Evidence extends AuditableAbstractAggregateRoot<Evidence> {

    @Column(nullable = false)
    private String link;

    @Column(name = "trip_id", nullable = false)
    private Long tripId;

    public Evidence(CreateEvidenceCommand command){
        this.link = command.link();
        this.tripId = command.tripId();
    }
}