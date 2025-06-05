package  com.dynoware.cargosafe.iamservice.iam.interfaces.rest.transform;

import com.dynoware.cargosafe.iamservice.iam.domain.model.commands.SignInCommand;
import com.dynoware.cargosafe.iamservice.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.username(), resource.password());
    }
}
