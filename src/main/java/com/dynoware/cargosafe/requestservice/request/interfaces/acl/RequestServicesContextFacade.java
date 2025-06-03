package com.dynoware.cargosafe.requestservice.request.interfaces.acl;

import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import java.util.List;
import java.util.Optional;

public interface RequestServicesContextFacade {
    RequestService createRequestService(RequestService requestService);
    RequestService updateRequestService(Long id, RequestService requestService);
    RequestService updateRequestServiceStatus(Long id, Long statusId);
    Optional<RequestService> getRequestServiceById(Long id);
    List<RequestService> getAllRequestServices();
    void deleteRequestService(Long id);
    List<RequestService> getRequestServicesByUserId(Long userId);
}
