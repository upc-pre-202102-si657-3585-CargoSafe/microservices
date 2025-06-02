package com.dynoware.cargosafe.companieservice.companies.domain.model.aggregates;

import com.dynoware.cargosafe.companieservice.companies.domain.model.commands.CreateCompanieCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Entity
public class Companie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String companieName;

    public Companie() {
        this.companieName = Strings.EMPTY;
    }

    public Companie(CreateCompanieCommand command) {
        super();
        this.companieName = command.companieName();
    }
}
