package com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates;

import com.dynoware.cargosafe.tripsservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateExpenseCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Expense extends AuditableAbstractAggregateRoot<Expense> {

    @Column(nullable = false)
    private int fuelAmount;

    @Column(nullable = false)
    private String fuelDescription;

    @Column(nullable = false)
    private int viaticsAmount;

    @Column(nullable = false)
    private String viaticsDescription;

    @Column(nullable = false)
    private int tollsAmount;

    @Column(nullable = false)
    private String tollsDescription;

    protected Expense() {
    }

    public Expense(CreateExpenseCommand command) {
        this.fuelAmount = command.fuelAmount();
        this.fuelDescription = command.fuelDescription();
        this.viaticsAmount = command.viaticsAmount();
        this.viaticsDescription = command.viaticsDescription();
        this.tollsAmount = command.tollsAmount();
        this.tollsDescription = command.tollsDescription();
    }

    public Expense(Long id) {
        super(id);
    }
}