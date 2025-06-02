package com.dynoware.cargosafe.companieservice.companies.domain.services;

import com.dynoware.cargosafe.companieservice.companies.domain.model.commands.CreateCompanieCommand;
import com.dynoware.cargosafe.companieservice.companies.domain.model.commands.DeleteCompanieCommand;

public interface CompanieCommandService {
    void handle(CreateCompanieCommand command);
    void handle(DeleteCompanieCommand command);
}
