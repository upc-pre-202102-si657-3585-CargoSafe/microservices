package com.dynoware.cargosafe.trips.domain.model.aggregates;

import com.dynoware.cargosafe.trips.domain.model.commands.CreateDriverCommand;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Entity
@Table(name = "drivers")

public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "license", nullable = false)
    private String license;

    @Column(name = "contact_num", nullable = false)
    private String contactNum;

    @Column(name = "url_photo",nullable = false)
    private String urlPhoto;

    public Driver(){
        this.name = Strings.EMPTY;
        this.dni = Strings.EMPTY;
        this.license = Strings.EMPTY;
        this.contactNum = Strings.EMPTY;
        this.urlPhoto   = Strings.EMPTY;
    }

    public Driver(String name, String dni, String license, String contactNum, String urlPhoto) {
        this();
        this.name = name;
        this.dni = dni;
        this.license = license;
        this.contactNum = contactNum;
        this.urlPhoto = urlPhoto;
    }

    public Driver(CreateDriverCommand command) {
        this();
        this.name = command.name();
        this.dni = command.dni();
        this.license = command.license();
        this.contactNum = command.contactNum();
        this.urlPhoto = command.urlPhoto();
    }

    public Driver updateInformation(String name,String dni, String license, String contactNum, String urlPhoto) {
        this.name = name;
        this.dni = dni;
        this.license = license;
        this.contactNum = contactNum;
        this.urlPhoto = urlPhoto;
        return this;
    }

    public Driver(Long id) {
        this.id = id;
    }

}
