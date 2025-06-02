package com.dynoware.cargosafe.trips.domain.model.aggregates;

import com.dynoware.cargosafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.dynoware.cargosafe.trips.domain.model.commands.CreateExpenseCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Expense extends AuditableAbstractAggregateRoot<Expense> {

    // ✅ QUITAR: El @Id ya está en AuditableAbstractAggregateRoot
    // No redefinir id aquí

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

    protected Expense() {}

    public Expense(CreateExpenseCommand command) {
        this.fuelAmount = command.fuelAmount();
        this.fuelDescription = command.fuelDescription();
        this.viaticsAmount = command.viaticsAmount();
        this.viaticsDescription = command.viaticsDescription();
        this.tollsAmount = command.tollsAmount();
        this.tollsDescription = command.tollsDescription();
    }

    public Expense(Long id) {
        super(id); // ✅ CAMBIO: Usar el constructor padre
    }
}