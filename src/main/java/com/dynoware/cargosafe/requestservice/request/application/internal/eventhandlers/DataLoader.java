package com.dynoware.cargosafe.requestservice.request.application.internal.eventhandlers;


import com.dynoware.cargosafe.requestservice.request.domain.model.entities.Status;
import com.dynoware.cargosafe.requestservice.request.domain.model.valueobjects.StatusName;
import com.dynoware.cargosafe.requestservice.request.infrastructure.persistence.jpa.repositories.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class DataLoader implements CommandLineRunner {
    private final StatusRepository statusRepository;

    public DataLoader(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (StatusName name : StatusName.values()) {
            Status status = new Status();
            status.setName(name);
            statusRepository.save(status);
        }
    }
}