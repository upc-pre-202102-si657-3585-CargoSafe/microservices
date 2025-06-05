package  com.dynoware.cargosafe.iamservice.iam.domain.services;

import com.dynoware.cargosafe.iamservice.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
