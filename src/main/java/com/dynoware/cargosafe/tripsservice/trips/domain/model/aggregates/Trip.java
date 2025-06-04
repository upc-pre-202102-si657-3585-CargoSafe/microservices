package com.dynoware.cargosafe.trips.domain.model.aggregates;

import com.dynoware.cargosafe.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.dynoware.cargosafe.trips.domain.model.commands.CreateTripCommand;
import com.dynoware.cargosafe.trips.domain.model.commands.UpdateTripCommand;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Trip extends AuditableAbstractAggregateRoot<Trip> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String unloadDirection;

    @Column(nullable = false)
    private String unloadLocation;

    @Column(nullable = false)
    private String unloadDate;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "alert_id", nullable = false)
    private Alert alert;

    @ManyToOne
    @JoinColumn(name = "ongoing_trip_id", nullable = false)
    private OnGoingTrip ongoingTrip;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    // ✅ CAMBIO: En lugar de User entity, solo guarda el userId
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private int numberPackages;

    @Column(nullable = false)
    private String holderName;

    @Column(nullable = false)
    private String destinationDate;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private String destinationAddress;

    @Column(nullable = false)
    private String loadDetail;

    @Column(nullable = false)
    private String pickupAddress;

    public Trip(CreateTripCommand command) {
        this.name = command.name();
        this.type = command.type();
        this.weight = command.weight();
        this.unloadDirection = command.unloadDirection();
        this.unloadLocation = command.unloadLocation();
        this.unloadDate = command.unloadDate();
        this.expense = new Expense(command.expenseId());
        this.alert = new Alert(command.alertId());
        this.ongoingTrip = new OnGoingTrip(command.ongoingTripId());
        this.vehicle = new Vehicle(command.vehicleId());
        this.driver = new Driver(command.driverId());
        this.userId = command.userId(); // ✅ CAMBIO: Solo el ID
        this.destination = command.destination();
        this.department = command.department();
        this.district = command.district();
        this.country = command.country();
        this.numberPackages = command.numberPackages();
        this.holderName = command.holderName();
        this.destinationDate = command.destinationDate();
        this.totalAmount = command.totalAmount();
        this.destinationAddress = command.destinationAddress();
        this.loadDetail = command.loadDetail();
        this.pickupAddress = command.pickupAddress();
    }

    public void updateTrip(UpdateTripCommand command) {
        this.name = command.name();
        this.type = command.type();
        this.weight = command.weight();
        this.unloadDirection = command.unloadDirection();
        this.unloadLocation = command.unloadLocation();
        this.unloadDate = command.unloadDate();
        this.expense = new Expense(command.expenseId());
        this.alert = new Alert(command.alertId());
        this.ongoingTrip = new OnGoingTrip(command.ongoingTripId());
        this.vehicle = new Vehicle(command.vehicleId());
        this.driver = new Driver(command.driverId());
        this.userId = command.userId(); // ✅ CAMBIO: Solo el ID
        this.destination = command.destination();
        this.department = command.department();
        this.district = command.district();
        this.country = command.country();
        this.numberPackages = command.numberPackages();
        this.holderName = command.holderName();
        this.destinationDate = command.destinationDate();
        this.totalAmount = command.totalAmount();
        this.destinationAddress = command.destinationAddress();
        this.loadDetail = command.loadDetail();
        this.pickupAddress = command.pickupAddress();
    }

    // ✅ AGREGAR: Getter para userId
    public Long getUserId() {
        return userId;
    }
}