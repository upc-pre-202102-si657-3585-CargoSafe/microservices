package com.dynoware.cargosafe.companieservice.companies.application.internal.commandservices;

import com.dynoware.cargosafe.companieservice.companies.domain.model.aggregates.Companie;
import com.dynoware.cargosafe.companieservice.companies.domain.model.commands.CreateCompanieCommand;
import com.dynoware.cargosafe.companieservice.companies.domain.model.commands.DeleteCompanieCommand;
import com.dynoware.cargosafe.companieservice.companies.domain.services.CompanieCommandService;
import com.dynoware.cargosafe.companieservice.companies.infrastructure.persistence.jpa.repositories.CompanieRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanieCommandServiceImpl implements CompanieCommandService {
    private final CompanieRepository repository;

    public CompanieCommandServiceImpl(CompanieRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(CreateCompanieCommand command) {
        var companie = new Companie(command);
        try {
            repository.save(companie);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving the companie: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public void handle(DeleteCompanieCommand command) {
        if(!repository.existsById(command.id()))
            throw new IllegalArgumentException("Companie with id %s not found".formatted(command.id()));
        try {
            repository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting companie card: %s".formatted(e.getMessage()));
        }
    }
}
