package  com.dynoware.cargosafe.iamservice.iam.domain.services;

import com.dynoware.cargosafe.iamservice.iam.domain.model.entities.Role;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetAllRolesQuery;
import com.dynoware.cargosafe.iamservice.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}
