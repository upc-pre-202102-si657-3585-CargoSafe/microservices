package com.dynoware.cargosafe.requestservice.request.domain.services;


import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import com.dynoware.cargosafe.requestservice.request.domain.model.queries.GetAllRequestServiceQuery;
import com.dynoware.cargosafe.requestservice.request.domain.model.queries.GetRequestServiceByIdQuery;

import java.util.List;
import java.util.Optional;

public interface RequestServiceQueryService {
    Optional<RequestService> handle(GetRequestServiceByIdQuery query);
    List<RequestService> handle(GetAllRequestServiceQuery query);
    List<RequestService> handleByUserId(Long userId);
}