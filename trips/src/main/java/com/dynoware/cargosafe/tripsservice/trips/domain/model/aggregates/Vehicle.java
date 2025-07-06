package com.dynoware.cargosafe.tripsservice.trips.domain.model.aggregates;

import com.dynoware.cargosafe.tripsservice.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.dynoware.cargosafe.tripsservice.trips.domain.model.commands.CreateVehicleCommand;
import io.jsonwebtoken.lang.Strings;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "vehicles")
public class Vehicle extends AuditableAbstractAggregateRoot<Vehicle> {

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "plate", nullable = false)
    private String plate;

    @Column(name = "max_load", nullable = false)
    private Float maxLoad;

    @Column(name = "volume", nullable = false)
    private Float volume;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    public Vehicle() {
        this.model = Strings.EMPTY;
        this.plate = Strings.EMPTY;
        this.maxLoad = 0.0f;
        this.volume = 0.0f;
        this.photoUrl = Strings.EMPTY;
    }

    public Vehicle(String model, String plate, Float maxLoad, Float volume, String photoUrl) {
        this();
        this.model = model;
        this.plate = plate;
        this.maxLoad = maxLoad;
        this.volume = volume;
        this.photoUrl = photoUrl;
    }

    public Vehicle(CreateVehicleCommand command) {
        this();
        this.model = command.model();
        this.plate = command.plate();
        this.maxLoad = command.maxLoad();
        this.volume = command.volume();
        this.photoUrl = command.photoUrl();
    }

    public Vehicle updateVehicle(String model, String plate, Float maxLoad, Float volume, String photoUrl) {
        this.model = model;
        this.plate = plate;
        this.maxLoad = maxLoad;
        this.volume = volume;
        this.photoUrl = photoUrl;
        return this;
    }

    public Vehicle(Long id) {
        super(id);
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setMaxLoad(Float maxLoad) {
        this.maxLoad = maxLoad;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}