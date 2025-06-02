package com.dynoware.cargosafe.trips.application.internal.commandservices;

import com.dynoware.cargosafe.kafka.producer.TripsKafkaProducer;
import com.dynoware.cargosafe.trips.domain.model.aggregates.Driver;
import com.dynoware.cargosafe.trips.domain.model.commands.CreateDriverCommand;
import com.dynoware.cargosafe.trips.domain.model.commands.DeleteDriverCommand;
import com.dynoware.cargosafe.trips.domain.model.commands.UpdateDriverCommand;
import com.dynoware.cargosafe.trips.domain.services.DriverCommandService;
import com.dynoware.cargosafe.trips.infrastructure.persistence.jpa.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverCommandServiceImpl implements DriverCommandService {
    private final DriverRepository driverRepository;
    private final TripsKafkaProducer tripsKafkaProducer;

    public DriverCommandServiceImpl(DriverRepository driverRepository, TripsKafkaProducer tripsKafkaProducer) {
        this.driverRepository = driverRepository;
        this.tripsKafkaProducer = tripsKafkaProducer;
    }

    @Override
    public Long handle(CreateDriverCommand command) {
        if (driverRepository.existsByName(command.name())){
            throw new IllegalArgumentException("Driver with same name already exists");
        }
        var driver = new Driver(command);
        try {
            driverRepository.save(driver);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving driver: " + e.getMessage());
        }
        return driver.getId();
    }

    @Override
    public Optional<Driver> handle(UpdateDriverCommand command) {
        if (driverRepository.existsByNameAndIdIsNot(command.name(), command.id()))
            throw new IllegalArgumentException("Driver with same name already exists");
        var result = driverRepository.findById(command.id());
        if (result.isEmpty()) throw new IllegalArgumentException("Driver does not exist");
        var driverToUpdate = result.get();
        try {
            var updateDriver = driverRepository.save(driverToUpdate.updateInformation(command.name(), command.dni(), command.license(),command.contactNum(),command.urlPhoto()));
            return Optional.of(updateDriver);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating driver: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteDriverCommand command){
        if (!driverRepository.existsById(command.driverId())) {
            throw new IllegalArgumentException("Driver does not exist");
        }
        try {
            driverRepository.deleteById(command.driverId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting driver: " + e.getMessage());
        }
    }
}
