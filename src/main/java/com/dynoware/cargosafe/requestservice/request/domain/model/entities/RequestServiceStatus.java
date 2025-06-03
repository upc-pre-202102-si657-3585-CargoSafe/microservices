package com.dynoware.cargosafe.requestservice.request.domain.model.entities;
import com.dynoware.cargosafe.requestservice.request.domain.model.aggregates.RequestService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class RequestServiceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_service_id")
    private RequestService requestService;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;



}