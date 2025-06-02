package com.dynoware.cargosafe.trips.domain.model.aggregates;

import com.dynoware.cargosafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.dynoware.cargosafe.trips.domain.model.commands.CreateAlertCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // ✅ CAMBIO: JPA Id, no Spring Data
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class Alert extends AuditableAbstractAggregateRoot<Alert> {

    // ✅ QUITAR: El @Id ya está en AuditableAbstractAggregateRoot
    // No necesitas redefinir id aquí

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date date;

    protected Alert() {}

    public Alert(CreateAlertCommand command) {
        this.title = command.title();
        this.description = command.description();
        this.date = command.date();
    }

    public Alert(Long id) {
        super(id); // ✅ CAMBIO: Usar el constructor padre
    }
}