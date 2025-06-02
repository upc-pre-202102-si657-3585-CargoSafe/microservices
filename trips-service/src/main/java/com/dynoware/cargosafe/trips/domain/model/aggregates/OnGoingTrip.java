package com.dynoware.cargosafe.trips.domain.model.aggregates;

import com.dynoware.cargosafe.trips.domain.model.commands.CreateOnGoingTripCommand;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "onGoingTrips")
public class OnGoingTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @Column(name = "speed", nullable = false)
    private Integer speed;

    @Column(name = "distance", nullable = false)
    private Integer distance;

    public OnGoingTrip(){
        this.latitude = 0.0f;
        this.longitude = 0.0f;
        this.speed = 0;
        this.distance = 0;
    }

    public OnGoingTrip(Float latitude, Float longitude, Integer speed, Integer distance) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.distance = distance;
    }

    public OnGoingTrip(CreateOnGoingTripCommand command) {
        this();
        this.latitude = command.latitude();
        this.longitude = command.longitude();
        this.speed = command.speed();
        this.distance = command.distance();
    }

    public OnGoingTrip updateInformation(Float latitude, Float longitude, Integer speed, Integer distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.distance = distance;
        return  this;
    }

    public OnGoingTrip(Long id) {
        this.id = id;
    }
}
