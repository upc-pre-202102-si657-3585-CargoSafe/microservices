package com.dynoware.cargosafe.requestservice.request.domain.services;


import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import com.dynoware.cargosafe.requestservice.request.domain.model.commands.CreateRequestServiceCommand;
import com.dynoware.cargosafe.requestservice.request.domain.model.commands.DeleteRequestServiceCommand;
import com.dynoware.cargosafe.requestservice.request.domain.model.commands.UpdateRequestServiceCommand;

public interface RequestServiceCommandService {
    RequestService handle(CreateRequestServiceCommand command);
    RequestService handle(UpdateRequestServiceCommand command);
    void handle(DeleteRequestServiceCommand command);
}